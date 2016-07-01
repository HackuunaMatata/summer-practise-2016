package sheduler;

import java.util.ArrayList;

/**
 * Main class for project Task1.
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
public class CommandController {

    private ArrayList userPool  = null;
    private ArrayList eventPool = null;

    public static void main(String[] args) {
        Thread demon = new Thread(new Sheduler(), "thread2");
        demon.setDaemon(true);
        demon.start();
        try {
            for (int i = 0; i < 4; i++) {
                System.out.println("main thread");
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
