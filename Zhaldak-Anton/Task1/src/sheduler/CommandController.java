package sheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    // DESC: control CommandController life, listen stdin and call need functions.
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
                System.out.print("$: ");

                inputString = input.readLine();
                sCommand = this.getCommand(inputString);

                switch (sCommand) {
                    case "Create":
                        args = getArgs(inputString);  // get arguments
                        cCreate(args);                // call Create (name, timezone, state)
                        break;
                    case "Modify":
                        args = getArgs(inputString);  // get arguments
                        cModify(args);                // call Modify (name, timezone, state)
                        break;
                    case "AddEvent":

                        break;
                    case "RemoveEvent":

                        break;
                    case "AddRandomTimeEvent":

                        break;
                    case "CloneEvent":

                        break;
                    case "ShowInfo":
                        args = getArgs(inputString);  // get arguments
                        cShowInfo(args);              // call ShowInfo (name)
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
                        String strDate = "19.11.1995-18:22:21";
                        int timezone = -10;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
                        Date dateParse = new Date();
                        try {
                            dateParse = sdf.parse(strDate);
                            dateParse = new Date(dateParse.getTime() + (timezone * 1000 * 60 * 60)); //

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Formatter formatter = new Formatter();

                        formatter.format("%tT", dateParse);
                        System.out.println("Cur time: " + formatter);
                        System.out.println(dateParse.getTime());
                        formatter.close();
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


    // DESC: add user in user pool.
    // CONSOLE: Create (name, timezone, state)
    private int cCreate (String[] args)
    {
        // Check nums of input argument.
        if ( args == null || args.length != 3 ) {
            System.err.println("Wrong nums of input argument. Enter Help for more info.");
            return 1;
        }

        // Check format user name
        if ( !args[0].matches("\\w+") ) {
            System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
            return 2;
        }

        // Check format timezone
        if ( !args[1].matches("[+-]?[0-9]+") ) {
            System.err.println("Wrong timezone. Use - and numbers.");
            return 3;
        }

        // Check format user state
        if ( !args[2].matches("active|passive") ) {
            System.err.println("Wrong state. Use 'active' or 'passive'.");
            return 4;
        }

        String userName = args[0];
        int userTimezone = Integer.parseInt(args[1]);   // Don't check that -12 < timezone < 12
        boolean userState = (args[2].equals("active"));

        // Check existing user with userName name
        boolean isNameExist = false;
        // Find user and set flag isNameExist if user with this name already exist
        synchronized (userPool) {
            for (User user : userPool) {
                if (user.getName().equals(userName)) {
                    isNameExist = true;
                    break;
                }
            }
        }
        if ( isNameExist ) {
            // Error: user with enter name already exist.
            System.err.println("User with this name already exist.");
            return 5;
        }
        else {
            // Add new user
            synchronized (userPool) {
                userPool.add(new User(userName, userTimezone, userState));
            }
        }

        return 0;
    }


    // DESC: modify user info.
    // CONSOLE: Modify(name, timezone, active)
    private int cModify (String[] args)
    {
        // Check nums of input argument.
        if ( args == null || args.length != 3 ) {
            System.err.println("Wrong nums of input argument. Enter Help for more info.");
            return 1;
        }

        // Check format user name
        if ( !args[0].matches("\\w+") ) {
            System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
            return 2;
        }

        // Check format timezone
        if ( !args[1].matches("[+-]?[0-9]+") ) {
            System.err.println("Wrong timezone. Use - and numbers.");
            return 3;
        }

        // Check format user state
        if ( !args[2].matches("active|passive") ) {
            System.err.println("Wrong state. Use 'active' or 'passive'.");
            return 4;
        }

        String userName = args[0];
        int userTimezone = Integer.parseInt(args[1]);
        boolean userState = (args[2].equals("active"));

        // Check existing user with userName name
        boolean isNameExist = false;
        User userFound = null;
        // Find user and set flag isNameExist if user with this name already exist
        synchronized (userPool) {
            for (User user : userPool) {
                if (user.getName().equals(userName)) {
                    isNameExist = true;
                    userFound = user;
                    break;
                }
            }
        }
        if ( !isNameExist ) {
            // Error: user with enter name not found.
            System.err.println("User with this name not found.");
            return 5;
        }
        else {
            // Modify user information
            synchronized (userFound) {
                userFound.setTimezone(userTimezone);
                userFound.setState(userState);
            }
        }

        return 0;
    }


    // DESC: show user info.
    // CONSOLE: ShowInfo(name)
    private int cShowInfo (String[] args)
    {
        // Check nums of input argument.
        if ( args == null || args.length != 1 ) {
            System.err.println("Wrong nums of input argument. Enter Help for more info.");
            return 1;
        }

        // Check format user name
        if ( !args[0].matches("\\w+") ) {
            System.err.println("Wrong user name. Use only [a-zA-Z0-9].");
            return 2;
        }

        String userName = args[0];

        // Check existing user with userName name
        boolean isNameExist = false;
        User userFound = null;
        // Find user and set flag isNameExist if user with this name already exist
        synchronized (userPool) {
            for (User user : userPool) {
                if (user.getName().equals(userName)) {
                    isNameExist = true;
                    userFound = user;
                    break;
                }
            }
        }
        if ( !isNameExist ) {
            // Error: user with enter name not found.
            System.err.println("User with this name not found.");
            return 3;
        }
        else {
            // Modify user information
            synchronized (userFound) {
                // Print user name and timezone
                System.out.print(userFound.getName() + " " + userFound.getTimezone() + " ");
                // Print user state
                if ( userFound.isState() )
                    System.out.println("active");
                else
                    System.out.println("passive");

                // Print user events
            }
        }

        return 0;
    }

    // DESC: return name command from console input.
    private String getCommand (String inputString)
    {
        String[] words = inputString.trim().split("[ \\t\\(]+");
        return words[0];
    }

    // DESC: parse command arguments from console input. There are arguments into brackets () and
    //       split ','(comma) ' '(space) and '\t'(tab). Symbols into quotes are one string.
    private String[] getArgs(String inputString)
    {
        Matcher matcher = Pattern.compile("(?<=\\().*(?=\\))").matcher(inputString);

        if (!matcher.find()) return null;

        String stringInBrackets = inputString.substring(matcher.start(), matcher.end());
//        System.out.println(stringInBrackets);

        String pattern = "(\\d{2}\\.\\d{2}\\.\\d{4}\\-\\d{2}\\:\\d{2}:\\d{2})|([-+\\w]+)|(['\"].*['\"])";
        matcher = Pattern.compile(pattern).matcher(stringInBrackets);
        ArrayList<String> arrayList = new ArrayList<>(0);
        String temp = "";
        while (matcher.find()) {
            temp = stringInBrackets.substring(matcher.start(), matcher.end());
            arrayList.add(temp);
        }
        String[] args = new String[arrayList.size()];
        for (int i = 0; i < args.length; i++) {
            args[i] = arrayList.get(i);
//            System.out.println(args[i]);
        }
        return args;
    }

    // DESC: print help text in stdout.
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
