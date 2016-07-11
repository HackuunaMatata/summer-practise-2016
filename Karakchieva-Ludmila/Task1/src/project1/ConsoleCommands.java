package project1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ConsoleCommands {

    static volatile ArrayList<User> users_ = new ArrayList<User>();
    private int count_;

    public ConsoleCommands() {
        count_ = 0;
    }

    public void Create(String name, TimeZone timezone, Status status) {
        User user = new User(name, timezone, status);
        users_.add(user);
        count_ = count_ + 1;
    }

    public void Modify(String name, TimeZone timezone, Status status) {
        int index = 0;
        boolean flag = false;
        while (index < count_) {
            if (users_.get(index).getName().equals(name)) {
                users_.get(index).ModifyUser(timezone, status);
                index = count_;
                flag = true;
            }
            index++;
        }
        if (flag == false) {
            System.out.println("Unknown user");
        }
    }

    public void AddEvent(String name, String text, Date date) {
        int index = 0;
        boolean flag = false;
        while (index < count_) {
            if (users_.get(index).getName().equals(name)) {
                users_.get(index).AddEventUser(text, date);
                index = count_;
                flag = true;
            }
            index++;
        }
        if (flag == false) {
            System.out.println("Unknown user");
        }
    }

    public void RemoveEvent(String name, String text) {
        int index = 0;
        boolean flag = false;
        while (index < count_) {
            if (users_.get(index).getName().equals(name)) {
                users_.get(index).RemoveEventUser(text);
                index = count_;
                flag = true;
            }
            index++;
        }
        if (flag == false) {
            System.out.println("Unknown user");
        }
    }

    public void AddRandomTimeEvent(String name, String text, Date dateFrom, Date dateTo) {
        int index = 0;
        boolean flag = false;
        Random random = new Random(System.currentTimeMillis());
        long time = random.nextInt((int) dateTo.getTime() - (int) dateFrom.getTime());
        while (index < count_) {
            if (users_.get(index).getName().equals(name)) {
                users_.get(index).AddEventUser(text, new Date(dateFrom.getTime() + time));
                index = count_;
                flag = true;
            }
            index++;
        }
        if (flag == false) {
            System.out.println("Unknown user");
        }
    }

    public void CloneEvent(String name, String text, String nameTo) {
        int index = 0;
        int flag = 0;
        int indexUserTo = 0;
        Date date = null;
        while (index < count_) {
            if (users_.get(index).getName().equals(name)) {
                date = users_.get(index).getDateEventUser(text);
                flag++;
            }
            if (users_.get(index).getName().equals(nameTo)) {
                indexUserTo = index;
                flag++;
            }
            if (flag == 2) {
                index = count_;
            }
            index++;
        }
        if (flag != 2) {
            System.out.println("Unknown user");
        }
        users_.get(indexUserTo).AddEventUser(text, date);
    }

    public void ShowInfo(String name) {
        int index = 0;
        while (index < count_) {
            if (users_.get(index).getName().equals(name)) {
                users_.get(index).ShowInfoUser();
                index = count_;
            }
            index++;
        }
    }

    public void StartScheduling() {
        SheduleCoordinator shedCoord = new SheduleCoordinator();
        shedCoord.start();
    }

    public static void main(String args[]) throws ParseException {
        ConsoleCommands console = new ConsoleCommands();
        console.runConsole();
    }

    public void runConsole() throws ParseException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            // System.out.print("command> ");
            // System.out.flush();

            String s;
            Vector<String> vector = new Vector<String>();

            s = scan.nextLine();
            int i = 0;
            StringTokenizer st = new StringTokenizer(s, "( \t\n\r,)");
            while (st.hasMoreTokens()) {
                vector.add(st.nextToken());
                i++;
            }
            switch (vector.elementAt(0)) {
                case ("Create"): {
                    TimeZone timezone = TimeZone.getTimeZone(vector.elementAt(2));
                    Status status = Status.PASSIVE;
                    if (vector.elementAt(3).equals("ACTIVE")) {
                        status = Status.ACTIVE;
                    } else if (vector.elementAt(3).equals("PASSIVE")) {
                        status = Status.PASSIVE;
                    } else {
                        System.out.println("Unknown status");
                    }
                    this.Create(vector.elementAt(1), timezone, status);
                    break;
                }

                case ("Modify"): {
                    TimeZone timezone = TimeZone.getTimeZone(vector.elementAt(2));
                    Status status = Status.ACTIVE;
                    if (vector.elementAt(3).equals("ACTIVE")) {
                        status = Status.ACTIVE;
                    } else if (vector.elementAt(3).equals("PASSIVE")) {
                        status = Status.PASSIVE;
                    } else {
                        System.out.println("Unknown status");
                    }
                    this.Modify(vector.elementAt(1), timezone, status);
                    break;
                }
                case ("AddEvent"): {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
                    Date date = dateFormat.parse(vector.elementAt(3));
                    this.AddEvent(vector.elementAt(1), vector.elementAt(2), date);
                    break;
                }
                case ("RemoveEvent"): {
                    this.RemoveEvent(vector.elementAt(1), vector.elementAt(2));
                    break;
                }
                case ("AddRandomTimeEvent"): {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
                    Date dateFrom = new Date();
                    Date dateTo = new Date();
                    dateFrom = new Date(dateFormat.parse(vector.elementAt(3)).getTime());
                    dateTo = new Date(dateFormat.parse(vector.elementAt(4)).getTime());
                    this.AddRandomTimeEvent(vector.elementAt(1), vector.elementAt(2), dateFrom, dateTo);
                    break;
                }
                case ("CloneEvent"): {
                    this.CloneEvent(vector.elementAt(1), vector.elementAt(2), vector.elementAt(3));
                    break;
                }
                case ("ShowInfo"): {
                    this.ShowInfo(vector.elementAt(1));
                    break;
                }
                case ("StartScheduling"): {
                    this.StartScheduling();
                    break;
                }
                default: {
                    System.out.println("Unknown command");
                }
            }
        }
    }
}
