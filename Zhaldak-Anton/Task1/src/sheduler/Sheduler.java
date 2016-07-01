package sheduler;

/**
 * This class check events into event pool and display event
 *
 */
public class Sheduler implements Runnable {
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
