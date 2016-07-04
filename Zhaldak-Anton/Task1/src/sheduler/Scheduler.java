package sheduler;

/**
 * Class for control events. This class check event date
 * every user and display event text if it's necessary.
 */
public class Scheduler implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("I'm a demon");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
