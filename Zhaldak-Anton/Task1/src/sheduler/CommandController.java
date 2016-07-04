package sheduler;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class for project Task1.<br>
 * This class control user input and execute valid command.
 *
 * <br>Allowd commands:
 * <ul><li>Create(name, timezone, active)</li>
 * <li>Modify(name, timezone, active)</li>
 * <li>AddEvent(name, text, datetime), where datetime = DD.MM.YYYY-HH24:Mi:SS</li>
 * <li>RemoveEvent(name, text)</li>
 * <li>AddRandomTimeEvent(name, text, dateFrom, dateTo)</li>
 * <li>CloneEvent(name, text, nameTo)</li>
 * <li>ShowInfo(name)</li>
 * <li>StartScheduling</li></ul>
 *
 */
public class CommandController implements Runnable {

    private final ArrayList<User> userPool = new ArrayList<User>(0); // pool of users

    public static void main(String[] args)
    {
        CommandController controller = new CommandController();
        Thread controllerThread = new Thread(controller, "Controller thread");
        controllerThread.start();
    }

    /**
     * DESC: control CommandController life, listen stdin and call need functions.
     */
    public void run()
    {
        int exitCode = -1; // for save exit code.
                           // If this not equals with -1, then exit from this function.
        // Create stdin listener
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String inputString = ""; // Save input string
        String sCommand = "";    // For save command name
        String[] args = null;    // For save arguments of input command
        try {
            // Infinite cycle for input text commands (listen stdin)
            while (true) {
                System.out.print("$: "); // !!!!!

                inputString = input.readLine();           // read input string
                sCommand = this.getCommand(inputString);  // get command name
                args = getArgs(inputString);              // get arguments

                switch (sCommand) {
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

                        break;
                    case "AddRandomTimeEvent":

                        break;
                    case "CloneEvent":

                        break;
                    case "ShowInfo":
                        cShowInfo(args);      // call ShowInfo (name)
                        break;
                    case "StartScheduling":

                        break;
                    case "Help":
                        Help();
                        break;
                    case "Exit":
                        exitCode = 0;
                        break;

                    case "test":

                        break;

                    default:
                        Help();
                }

                if (exitCode != -1) break; // Need exit from this function.
                                           // Maybe there was error or normal exit (if exitCode == 0).
            } // End infinite cycle
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(exitCode);
    }

    /**
     * DESC: add user in user pool.<br>
     * CONSOLE: <code>Create (name, timezone, state)</code>
     *
     * @param args Input arguments for function <code>Create</code>
     *
     * @return 0 - successful <br>
     *         1 - Wrong numbers of input argument<br>
     *         2 - Wrong user name<br>
     *         3 - Wrong timezone<br>
     *         4 - Wrong state<br>
     *         5 - User with this name already exist<br>
     */
    private int cCreate (String[] args)
    {
        // Check nums of input argument.
        if ( args == null || args.length != 3 )
        {
            System.err.println("Wrong nums of input argument. Enter Help for more info.");
            return 1;
        }

        // Check format user name
        if ( !args[0].matches("\\w+") )
        {
            System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
            return 2;
        }

        // Check format timezone
        if ( !args[1].matches("[+-]?[0-9]+") )
        {
            System.err.println("Wrong timezone. Use - and numbers.");
            return 3;
        }

        // Check format user state
        if ( !args[2].matches("active|passive") )
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

        if ( isUserExist )
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
     * DESC: modify user info.<br>
     * CONSOLE: <code>Modify (name, timezone, active)</code>
     *
     * @param args Input arguments for function <code>Create</code>
     *
     * @return 0 - successful <br>
     *         1 - Wrong numbers of input argument<br>
     *         2 - Wrong user name<br>
     *         3 - Wrong timezone<br>
     *         4 - Wrong state<br>
     *         5 - User with this name not found<br>
     */
    private int cModify (String[] args)
    {
        // Check nums of input argument.
        if ( args == null || args.length != 3 )
        {
            System.err.println("Wrong nums of input argument. Enter Help for more info.");
            return 1;
        }

        // Check format user name
        if ( !args[0].matches("\\w+") )
        {
            System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
            return 2;
        }

        // Check format timezone
        if ( !args[1].matches("[+-]?[0-9]+") )
        {
            System.err.println("Wrong timezone. Use '-' and numbers.");
            return 3;
        }

        // Check format user state
        if ( !args[2].matches("active|passive") )
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

        if ( !isUserExist )
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
                int diffTimezone = userTimezone - oldTimezone;
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
     * DESC: show user info.<br>
     * CONSOLE: <code>ShowInfo (name)</code>
     *
     * @param args Input arguments for function <code>Create</code>
     *
     * @return 0 - successful <br>
     *         1 - Wrong numbers of input argument<br>
     *         2 - Wrong user name<br>
     *         3 - User with this name not found<br>
     */
    private int cShowInfo (String[] args)
    {
        // Check nums of input argument.
        if ( args == null || args.length != 1 )
        {
            System.err.println("Wrong nums of input argument. Enter Help for more info.");
            return 1;
        }

        // Check format user name
        if ( !args[0].matches("\\w+") )
        {
            System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
            return 2;
        }

        String userName = args[0];

        // Find user and set flag isUserExist if user with this name already exist
        User userFound = getUser(userName);
        boolean isUserExist = (userFound != null);

        if ( !isUserExist )
        {
            // Error: user with enter name not found.
            System.err.println("User with this name not found.");
            return 3;
        }

        // Modify user information
        synchronized (userFound)
        {
            // Print user name and timezone
            System.out.print(userFound.getName() + " " + userFound.getTimezone() + " ");
            // Print user state
            if ( userFound.isState() )
                System.out.println("active");
            else
                System.out.println("passive");

            // Print user events
            for (Event event : userFound.getEventPool())
            {
                Formatter formatter = new Formatter();
                formatter.format("%1$td.%1$tm.%1$tY-%tT", event.getDate());
                System.out.println(formatter + " " + event.getText());
                formatter.close();
            }
        }

        return 0;
    }


    /**
     * DESC: add event for user.<br>
     * CONSOLE: <code>AddEvent (name, text, datetime)</code>
     *
     * @param args Input arguments for function <code>Create</code>
     *
     * @return 0 - successful <br>
     *         1 - Wrong numbers of input argument<br>
     *         2 - Wrong user name<br>
     *         3 - Wrong date format<br>
     *         4 - User with this name not found<br>
     *         5 - Invalid date<br>
     */
    private int cAddEvent (String[] args)
    {
        // Check nums of input argument.
        if ( args == null || args.length != 3 )
        {
            System.err.println("Wrong nums of input argument.");
            System.err.println("Enter text into quotes. Date format DD.MM.YYYY-hh:mm:ss");
            System.err.println("Enter Help for more info.");
            return 1;
        }

        // Check format user name.
        if ( !args[0].matches("\\w+") )
        {
            System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
            return 2;
        }

        // Check date format.
        // With this parser into getArgument method it's unreachable check because
        //    if the date does not match the required format then nums of input argument
        //    does not equals 3.
        //    (parse is invalid sometimes because find words but "ignore" separators)
        if ( !args[2].matches("\\d{2}\\.\\d{2}\\.\\d{4}\\-\\d{2}\\:\\d{2}:\\d{2}") )
        {
            System.err.println("Wrong date format. Date format DD.MM.YYYY-hh:mm:ss");
            return 3;
        }

        String userName  = args[0];
        String eventText = args[1];

        // Find user and set flag isUserExist if user with this name already exist
        User userFound = getUser(userName);
        boolean isUserExist = (userFound != null);

        if ( !isUserExist )
        {
            // Error: user with enter name not found.
            System.err.println("User with this name not found.");
            return 4;
        }

        // Check the validity of date and create date for user event.
        String stringDate = args[2];
        synchronized (userFound) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
                sdf.setLenient(false); // for auto check validity of date

                int userTimezone = userFound.getTimezone();           // get user timezone
                int addMilliSeconds = userTimezone * 60 * 60 * 1000;  // convert hours to milliseconds
                Date dateParse = sdf.parse(stringDate);               // parse input date
                                                                      // if date is invalid then auto create exception
                Date eventDate = new Date(dateParse.getTime() + addMilliSeconds);
                userFound.addEvent(new Event(eventText, eventDate));
            } catch (ParseException e) {
                System.err.println("Invalid date.");
                return 5;
            }
        }

        System.out.println(">> Add new event for " + userName);

        return 0;
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
     * DESC: parse command arguments from console input. There are arguments into <code>brackets '()'</code> and
     * split <code>','(comma)</code>, <code>' '(space)</code> and <code>'\t'(tab)</code>. Symbols into
     * quotes are one string.
     *
     * @param inputString Input string from console
     *
     * @return Array of strings
     */
    private String[] getArgs(String inputString)
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
     * @return <code>null</code> - if user not found or<br>
     *         <code>link</code> - on user object
     */
    private User getUser (String userName)
    {
        User userFound = null;
        synchronized (userPool) {
            for (User user : userPool) {
                if (user.getName().equals(userName)) {
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
    private static void Help()
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

/*
For test:
Create (user1, -2, passive)
Modify (user1, 0, active)
AddEvent(user1,"Hello world", 19.11.1995-18:11:11)
AddEvent(user1,"Hello world2", 19.11.1995-18:11:11)
ShowInfo(user1)
 */