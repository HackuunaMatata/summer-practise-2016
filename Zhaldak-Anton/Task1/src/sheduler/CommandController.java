package sheduler;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class for project Task1.<br> This class control user input and execute valid command.
 * <p>
 * <br>Allowd commands:
 * <ul>
 *    <li>Create(name, timezone, active)</li>
 *    <li>Modify(name, timezone, active)</li>
 *    <li>AddEvent(name, text, datetime), where datetime = DD.MM.YYYY-HH24:Mi:SS</li>
 *    <li>RemoveEvent(name, text)</li>
 *    <li>AddRandomTimeEvent(name, text, dateFrom, dateTo)</li>
 *    <li>CloneEvent(name, text, nameTo)</li>
 *    <li>ShowInfo(name)</li>
 *    <li>StartScheduling</li>
 * </ul>
 */
public class CommandController implements Runnable
{
   private final List<User> userPool = new ArrayList<User>(0); // pool of users
   private boolean isSchedulerRun = false;

   public static void main (String[] args)
   {
      CommandController controller = new CommandController();
      Thread controllerThread = new Thread(controller, "Controller thread");
      controllerThread.start();
   }

   /**
    * DESC: control CommandController life, listen stdin and call need functions.
    */
   public void run ()
   {
      int exitCode = -1; // for save exit code.
      // If this not equals with -1, then exit from this function.
      // Create stdin listener
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      String inputString = ""; // Save input string
      String sCommand = "";    // For save command name
      String[] args = null;    // For save arguments of input command
      try
      {
         // Infinite cycle for input text commands (listen stdin)
         while (true)
         {
            System.out.print("$: "); // !!!!!

            inputString = input.readLine();           // read input string
            sCommand = this.getCommand(inputString);  // get command name
            args = getArgs(inputString);              // get arguments

            switch (sCommand)
            {
               case "Create":
                  cCreate(args);        // call Create (name, timezone, state)
                  break;
               case "Modify":
                  cModify(args);        // call Modify (name, timezone, state)
                  break;
               case "AddEvent":
                  cAddEvent(args);      // call AddEvent (name, text, datetime)
                  break;
               case "RemoveEvent":
                  cRemoveEvent(args);   // call RemoveEvent (name, text)
                  break;
               case "AddRandomTimeEvent":
                  cAddRandomTimeEvent(args); // call AddRandomTimeEvent(name, text, dateFrom, dateTo)
                  break;
               case "CloneEvent":
                  cCloneEvent(args);    // call CloneEvent(name, text, nameTo)
                  break;
               case "ShowInfo":
                  cShowInfo(args);      // call ShowInfo (name)
                  break;
               case "StartScheduling":
                  cStartScheduling(args); // call StartScheduling
                  break;
               case "Help":
                  Help();
                  break;
               case "Exit":
                  exitCode = 0;
                  break;
               default:
                  Help();
            }

            if (exitCode != -1) break; // Need exit from this function.
                                       // Maybe there was error or normal exit (if exitCode == 0).
         }  // End infinite cycle
      } catch (IOException e)
      {
         e.printStackTrace();
      } finally
      {
         try
         {
            input.close();
         } catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      System.exit(exitCode);
   }

   /**
    * DESC: add user in user pool.<br> CONSOLE: <code>Create (name, timezone, state)</code>
    *
    * @param args Input arguments for function <code>Create</code>
    *
    * @return 0 - successful <br> 1 - Wrong numbers of input argument<br> 2 - Wrong user name<br> 3 - Wrong timezone<br>
    * 4 - Wrong state<br> 5 - User with this name already exist<br>
    */
   private int cCreate (String[] args)
   {
      // Check nums of input argument.
      if (args == null || args.length != 3)
      {
         System.err.println("Wrong nums of input argument. Enter Help for more info.");
         return 1;
      }

      // Check format user name
      if (!args[0].matches("\\w+"))
      {
         System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
         return 2;
      }

      // Check format timezone
      if (!args[1].matches("[+-]?[0-9]+"))
      {
         System.err.println("Wrong timezone. Use - and numbers.");
         return 3;
      }

      // Check format user state
      if (!args[2].matches("active|passive"))
      {
         System.err.println("Wrong state. Use 'active' or 'passive'.");
         return 4;
      }

      String userName = args[0];
      int userTimezone = Integer.parseInt(args[1]);   // Don't check that -12 < timezone < 12
      boolean userState = (args[2].equals("active"));

      // Find user and set flag isUserExist if user with this name already exist
      User userFound = getUser(userName);
      boolean isUserExist = (userFound != null);

      if (isUserExist)
      {
         // Error: user with enter name already exist.
         System.err.println("User with this name already exist.");
         return 5;
      }

      // Add new user
      synchronized (userPool)
      {
         userPool.add(new User(userName, userTimezone, userState));
      }

      System.out.println(">> Add new user " + userName);

      return 0;
   }


   /**
    * DESC: modify user info.<br> CONSOLE: <code>Modify (name, timezone, active)</code>
    *
    * @param args Input arguments for function <code>Modify</code>
    *
    * @return 0 - successful <br> 1 - Wrong numbers of input argument<br> 2 - Wrong user name<br> 3 - Wrong timezone<br>
    * 4 - Wrong state<br> 5 - User with this name not found<br>
    */
   private int cModify (String[] args)
   {
      // Check nums of input argument.
      if (args == null || args.length != 3)
      {
         System.err.println("Wrong nums of input argument. Enter Help for more info.");
         return 1;
      }

      // Check format user name
      if (!args[0].matches("\\w+"))
      {
         System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
         return 2;
      }

      // Check format timezone
      if (!args[1].matches("[+-]?[0-9]+"))
      {
         System.err.println("Wrong timezone. Use '-' and numbers.");
         return 3;
      }

      // Check format user state
      if (!args[2].matches("active|passive"))
      {
         System.err.println("Wrong state. Use 'active' or 'passive'.");
         return 4;
      }

      String userName = args[0];
      int userTimezone = Integer.parseInt(args[1]);
      boolean userState = (args[2].equals("active"));

      // Find user and set flag isUserExist if user with this name already exist
      User userFound = getUser(userName);
      boolean isUserExist = (userFound != null);

      if (!isUserExist)
      {
         // Error: user with enter name not found.
         System.err.println("User with this name not found.");
         return 5;
      }

      // Modify user information
      synchronized (userFound)
      {
         int oldTimezone = userFound.getTimezone();

         userFound.setTimezone(userTimezone);
         userFound.setState(userState);

         // Recalculate event date
         if (oldTimezone != userTimezone)
         {
            int diffTimezone = oldTimezone - userTimezone;
            long addMS = diffTimezone * 60 * 60 * 1000;
            long dateMS = 0;
            for (Event event : userFound.getEventPool())
            {
               dateMS = event.getDate().getTime();
               event.getDate().setTime(dateMS + addMS);
            }
         }
      }

      System.out.println(">> Modify info for " + userName);

      return 0;
   }


   /**
    * DESC: show user info.<br> CONSOLE: <code>ShowInfo (name)</code>
    *
    * @param args Input arguments for function <code>ShowInfo</code>
    *
    * @return 0 - successful <br> 1 - Wrong numbers of input argument<br> 2 - Wrong user name<br> 3 - User with this
    * name not found<br>
    */
   private int cShowInfo (String[] args)
   {
      // Check nums of input argument.
      if (args == null || args.length != 1)
      {
         System.err.println("Wrong nums of input argument. Enter Help for more info.");
         return 1;
      }

      // Check format user name
      if (!args[0].matches("\\w+"))
      {
         System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
         return 2;
      }

      String userName = args[0];

      // Find user and set flag isUserExist if user with this name already exist
      User userFound = getUser(userName);
      boolean isUserExist = (userFound != null);

      if (!isUserExist)
      {
         // Error: user with enter name not found.
         System.err.println("User with this name not found.");
         return 3;
      }

      // Modify user information
      synchronized (userFound)
      {
         // Print user name and timezone
         System.out.print(">> " + userFound.getName() + " " + userFound.getTimezone() + " ");
         // Print user state
         if (userFound.isState())
            System.out.println("active");
         else
            System.out.println("passive");

         // Print user events
         long userTimezoneMS = userFound.getTimezone() * 60 * 60 * 1000;
         for (Event event : userFound.getEventPool())
         {
            long eventDateMS = event.getDate().getTime();
//                Date userDateVeiw = new Date(eventDateMS + userTimezoneMS); // for print time regard user timezone
            Date userDateVeiw = new Date(eventDateMS);                    // for print time regard system time
            Formatter formatter = new Formatter();
            formatter.format("%1$td.%1$tm.%1$tY-%tT", userDateVeiw);
            System.out.println(">> " + formatter + " " + event.getText());
            formatter.close();
         }
      }

      return 0;
   }


   /**
    * DESC: add event for user.<br> CONSOLE: <code>AddEvent (name, text, datetime)</code>
    *
    * @param args Input arguments for function <code>AddEvent</code>
    *
    * @return 0 - successful <br> 1 - Wrong numbers of input argument<br> 2 - Wrong user name<br> 3 - Wrong date
    * format<br> 4 - User with this name not found<br> 5 - Invalid date<br>
    */
   private int cAddEvent (String[] args)
   {
      // Check nums of input argument.
      if (args == null || args.length != 3)
      {
         System.err.println("Wrong nums of input argument.");
         System.err.println("Enter text into quotes. Date format DD.MM.YYYY-hh:mm:ss");
         System.err.println("Enter Help for more info.");
         return 1;
      }

      // Check format user name.
      if (!args[0].matches("\\w+"))
      {
         System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
         return 2;
      }

      // Check date format.
      // With this parser into getArgument method it's unreachable check because
      //    if the date does not match the required format then nums of input argument
      //    does not equals 3.
      //    (parse is invalid sometimes because find words but "ignore" separators)
      if (!args[2].matches("\\d{2}\\.\\d{2}\\.\\d{4}\\-\\d{2}\\:\\d{2}:\\d{2}"))
      {
         System.err.println("Wrong date format. Date format DD.MM.YYYY-hh:mm:ss");
         return 3;
      }

      String userName = args[0];
      String eventText = args[1];

      // Find user and set flag isUserExist if user with this name already exist
      User userFound = getUser(userName);
      boolean isUserExist = (userFound != null);

      if (!isUserExist)
      {
         // Error: user with enter name not found.
         System.err.println("User with this name not found.");
         return 4;
      }

      // Check the validity of date and create date for user event.
      String stringDate = args[2];
      synchronized (userFound)
      {
         try
         {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
            sdf.setLenient(false); // for auto check validity of date

            int userTimezone = userFound.getTimezone();           // get user timezone
            int subMilliSeconds = userTimezone * 60 * 60 * 1000;  // convert hours to milliseconds
            Date dateParse = sdf.parse(stringDate);               // parse input date
            // if date is invalid then auto create exception
            Date eventDate = new Date(dateParse.getTime() - subMilliSeconds);
            userFound.addEvent(new Event(eventText, eventDate));
         } catch (ParseException e)
         {
            System.err.println("Invalid date.");
            return 5;
         }
      }

      System.out.println(">> Add new event for " + userName);

      return 0;
   }


   /**
    * DESC: remove event for user.<br> CONSOLE: <code>RemoveEvent(name, text)</code>
    *
    * @param args Input arguments for function <code>RemoveEvent</code>
    *
    * @return 0 - successful <br> 1 - Wrong numbers of input argument<br> 2 - Wrong user name<br> 3 - User with this
    * name not found<br> 4 - Event with this text not found<br>
    */
   private int cRemoveEvent (String[] args)
   {
      // Check nums of input argument.
      if (args == null || args.length != 2)
      {
         System.err.println("Wrong nums of input argument. Enter Help for more info.");
         return 1;
      }

      // Check format user name
      if (!args[0].matches("\\w+"))
      {
         System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
         return 2;
      }

      String userName = args[0];
      String eventText = args[1];

      // Find user and set flag isUserExist if user with this name already exist
      User userFound = getUser(userName);
      boolean isUserExist = (userFound != null);

      if (!isUserExist)
      {
         // Error: user with enter name not found.
         System.err.println("User with this name not found.");
         return 3;
      }

      synchronized (userFound)
      {
         int removeIndex = -1;
         List<Event> eventPool = userFound.getEventPool();
         for (Event event : eventPool)
         {
            if (event.getText().equals(eventText))
            {
               removeIndex = eventPool.indexOf(event);
               break;
            }
         }

         if (removeIndex < 0)
         {
            System.err.println("Event with this text not found.");
            return 4;
         }

         eventPool.remove(removeIndex);
      }

      System.out.println(">> Remove event completed.");

      return 0;
   }


   /**
    * DESC: add event in random time between dateFrom and dateTo.<br> CONSOLE: <code>AddRandomTimeEvent(name, text,
    * dateFrom, dateTo)</code>
    *
    * @param args Input arguments for function <code>AddRandomTimeEvent</code>
    *
    * @return 0 - successful <br> 1 - Wrong numbers of input argument<br> 2 - Wrong user name<br> 3 - Wrong date
    * format<br> 4 - User with this name not found<br> 5 - Invalid date<br> 6 - Inner error (<code>dateFrom</code> or
    * <code>dateTo</code> is <code>null</code>)<br>
    */
   private int cAddRandomTimeEvent (String[] args)
   {
      // Check nums of input argument.
      if (args == null || args.length != 4)
      {
         System.err.println("Wrong nums of input argument.");
         System.err.println("Date format DD.MM.YYYY-hh:mm:ss");
         System.err.println("Enter Help for more info.");
         return 1;
      }

      // Check format user name.
      if (!args[0].matches("\\w+"))
      {
         System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
         return 2;
      }

      // Check date format.
      // With this parser into getArgument method it's unreachable check because
      //    if the date does not match the required format then nums of input argument
      //    does not equals 4.
      //    (parse is invalid sometimes because find words but "ignore" separators)
      if (!args[2].matches("\\d{2}\\.\\d{2}\\.\\d{4}\\-\\d{2}\\:\\d{2}:\\d{2}")
            || !args[3].matches("\\d{2}\\.\\d{2}\\.\\d{4}\\-\\d{2}\\:\\d{2}:\\d{2}"))
      {
         System.err.println("Wrong date format. Date format DD.MM.YYYY-hh:mm:ss");
         return 3;
      }

      String userName = args[0];
      String eventText = args[1];
      String stringDateFrom = args[2];
      String stringDateTo = args[3];

      // Find user and set flag isUserExist if user with this name already exist
      User userFound = getUser(userName);
      boolean isUserExist = (userFound != null);

      if (!isUserExist)
      {
         // Error: user with enter name not found.
         System.err.println("User with this name not found.");
         return 4;
      }

      // Create date objects for getting long value
      Date dateFrom = null;
      Date dateTo = null;
      try
      {
         SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
         sdf.setLenient(false); // for auto check validity of date
         dateFrom = sdf.parse(stringDateFrom);
         dateTo = sdf.parse(stringDateTo);
      } catch (ParseException e)
      {
         System.err.println("Invalid date.");
         return 5;
      }

      if (dateFrom == null || dateTo == null)
      {
         System.err.println("cAddRandomTimeEvent: Inner error.");
         return 6;
      }

      // Generate random date between dateFrom and dateTo:
      //    setTime = baseTime + random*diffTime
      // ,where
      //    baseTime = min (dateFrom, dateTo)
      //    0.0 <= random <= 1.0
      //    diffTime = abs( dateFrom - dateTo )

      long dateFromMS = dateFrom.getTime();               // get dateFrom
      long dateToMS = dateTo.getTime();                 // get dateTo
      long setTime = Math.min(dateFromMS, dateToMS);      // get baseTime
      long diffTime = Math.abs(dateFromMS - dateToMS);    // get diffTime
      Random random = new Random(new Date().getTime());   // set seed for random
      double resRand = random.nextDouble();               // get random
      long addTime = (long) (resRand * (double) diffTime);// calculate (random*diffTime)
      setTime += addTime;                                 // get setTime

      synchronized (userFound)
      {
         int userTimezone = userFound.getTimezone();
         setTime -= (userTimezone * 60 * 60 * 1000);            // correct time regarding user timezone
         Date setDate = new Date(setTime);                // create date with setTime
         Event newEvent = new Event(eventText, setDate);
         userFound.addEvent(newEvent);
      }

      System.out.println(">> Add new event for " + userName + " in random time");

      return 0;
   }


   /**
    * DESC: clone user1 event for user2. Time of event1 and event2 equals regard server time.<br> CONSOLE:
    * <code>CloneEvent(name, text, nameTo)</code>
    *
    * @param args Input arguments for function <code>CloneEvent</code>
    *
    * @return 0 - successful <br> 1 - Wrong numbers of input argument<br> 2 - Wrong user name<br> 3 - UserFrom name not
    * found<br> 4 - UserTo name not found<br> 5 - Event in userFrom event pool not found<br>
    */
   private int cCloneEvent (String[] args)
   {
      // Check nums of input argument.
      if (args == null || args.length != 3)
      {
         System.err.println("Wrong nums of input argument. Enter Help for more info.");
         return 1;
      }

      // Check format user name
      if (!args[0].matches("\\w+") || !args[2].matches("\\w+"))
      {
         System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
         return 2;
      }

      String userNameFrom = args[0];
      String userNameTo = args[2];
      String eventText = args[1];

      // Check existing userFrom
      User userFrom = getUser(userNameFrom);
      boolean isUserExist = (userFrom != null);
      if (!isUserExist)
      {
         System.err.println("UserFrom name not found.");
         return 3;
      }

      // Check existing userTo
      User userTo = getUser(userNameTo);
      isUserExist = (userTo != null);
      if (!isUserExist)
      {
         System.err.println("UserTo name not found.");
         return 4;
      }

      synchronized (userFrom)
      {
         synchronized (userTo)
         {
            // Find event in userFrom event pool and clone event for userTo
            boolean isCloneEvent = false;
            List<Event> eventPoolUserFrom = userFrom.getEventPool();
            for (Event event : eventPoolUserFrom)
            {
               if (event.getText().equals(eventText))
               {
                  userTo.addEvent(event.clone());
                  isCloneEvent = true;
                  break;
               }
            }
            // Check calling event.clone()
            if (!isCloneEvent)
            {
               System.err.println("Event in userFrom event pool not found.");
               return 5;
            }
         }
      }

      System.out.println(">> Event clone from " + userNameFrom + " to " + userNameTo);

      return 0;
   }


   /**
    * DESC: start work scheduling thread.<br> CONSOLE: <code>StartScheduling</code>
    *
    * @param args Input arguments for function <code>StartScheduling</code>. <b>(Can null)</b>
    *
    * @return 0 - successful <br>
    *         1 - Scheduler already work <br>
    */
   private int cStartScheduling (String[] args)
   {
      if ( !isSchedulerRun )
      {
         isSchedulerRun = true;

         Thread schedulerThread = new Thread(new Scheduler(this.userPool), "Scheduler");
         schedulerThread.setDaemon(true);
         schedulerThread.start();

         System.out.println(">> Scheduler is start.");
         return 0;
      }

      System.err.println("Scheduler already work.");
      return 1;
   }


   /**
    * DESC: return name command from console input.
    *
    * @param inputString Input string from console
    *
    * @return Name of command
    */
   private String getCommand (String inputString)
   {
      String[] words = inputString.trim().split("[ \\t\\(]+");
      return words[0];
   }


   /**
    * DESC: parse command arguments from console input. There are arguments into <code>brackets '()'</code> and split
    * <code>','(comma)</code>, <code>' '(space)</code> and <code>'\t'(tab)</code>. Symbols into quotes are one string.
    *
    * @param inputString Input string from console
    *
    * @return Array of strings
    */
   private String[] getArgs (String inputString)
   {
      Matcher matcher = Pattern.compile("(?<=\\().*(?=\\))").matcher(inputString);

      if (!matcher.find()) return null;

      String stringInBrackets = inputString.substring(matcher.start(), matcher.end());

      String pattern = "(\\d{2}\\.\\d{2}\\.\\d{4}\\-\\d{2}\\:\\d{2}:\\d{2})|([-+\\w]+)|(['\"].*['\"])";
      matcher = Pattern.compile(pattern).matcher(stringInBrackets);
      ArrayList<String> arrayList = new ArrayList<>(0);
      String temp = "";

      while (matcher.find())
      {
         temp = stringInBrackets.substring(matcher.start(), matcher.end());
         arrayList.add(temp);
      }

      String[] args = new String[arrayList.size()];
      for (int i = 0; i < args.length; i++)
         args[i] = arrayList.get(i);

      return args;
   }


   /**
    * DESC: return user object by user name.
    *
    * @param userName User name
    *
    * @return <code>null</code> - if user not found or<br> <code>link</code> - on user object
    */
   private User getUser (String userName)
   {
      User userFound = null;
      synchronized (userPool)
      {
         for (User user : userPool)
         {
            if (user.getName().equals(userName))
            {
               userFound = user;
               break;
            }
         }
      }
      return userFound;
   }


   /**
    * DESC: print help text in stdout.
    */
   private static void Help ()
   {
      System.out.println("Wrong command.\nAllowed commands:" +
            "\n\tCreate(name, timezone, active)" +
            "\n\tModify(name, timezone, active)" +
            "\n\tAddEvent(name, text, datetime), where datetime = DD.MM.YYYY-HH24:Mi:SS" +
            "\n\tRemoveEvent(name, text)" +
            "\n\tAddRandomTimeEvent(name, text, dateFrom, dateTo)" +
            "\n\tCloneEvent(name, text, nameTo)" +
            "\n\tShowInfo(name)" +
            "\n\tStartScheduling" +
            "\n\tHelp" +
            "\n\tExit");
   }

}
