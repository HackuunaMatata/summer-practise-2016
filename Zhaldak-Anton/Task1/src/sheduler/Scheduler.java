package sheduler;


import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Class for control events. This class check event date every user and display event text if it's necessary.
 */
public class Scheduler implements Runnable
{

   private static long MISCALCULATION = 50;     // value of miscalculation (milliseconds)
   private static int  DELAY_AFTER_CHECK = 50;  // delay after chech user events
   private volatile List<User> userPool = null; // user pool

   /**
    * DESC: initialize scheduler
    *
    * @param userPool User pool for controlling
    */
   public Scheduler (List<User> userPool)
   {
      if (userPool == null)
      {
         System.err.println("Error of creating scheduler. UserPool is null");
         return;
      }

      this.userPool = userPool;
   }


   /**
    * DESC: control scheduler life.
    */
   public void run ()
   {
      // Some variables
      List<Event> eventPool = null;
      long currentDateMS = 0;
      long eventDateMS = 0;

      while (true)
      {
         for (User user : userPool)
         {
            synchronized (user)
            {
               if (!user.isState()) break;         // check user state
               eventPool = user.getEventPool();    // get user event pool
               for (Event userEvent : eventPool)
               {
                  currentDateMS = System.currentTimeMillis();   // get current date ms
                  eventDateMS = userEvent.getDate().getTime();  // get event date ms

                  // Note:
                  //    currentDate-miscalculatin < eventDate < currentDate+miscalculatin

                  // check on never come
                  if (eventDateMS < (currentDateMS - MISCALCULATION))
                  {
                     userEvent.clean(); // delete event
                     continue;
                  }

                  // Event pool is sorted. So next events will be future. So break but not continue.
                  if (eventDateMS > (currentDateMS + MISCALCULATION))
                     break;

                  // If execute Note (watch upper)
                  if (eventDateMS > (currentDateMS - MISCALCULATION)
                        && eventDateMS < (currentDateMS + MISCALCULATION))
                  {
                     // Print event
                     Date currentDate = new Date(System.currentTimeMillis());
                     Formatter formatter = new Formatter();
                     formatter.format("%1$td.%1$tm.%1$tY-%tT", currentDate);
                     System.out.println("\n" + formatter + " " + user.getName() + " " + userEvent.getText());
                     formatter.close();
                     userEvent.clean(); // clean this event
                  }
               }

               removeCleanEvents(eventPool); // remove clean events
            }
         }

         try { Thread.sleep(DELAY_AFTER_CHECK); }
         catch (InterruptedException e) { e.printStackTrace(); }
      }
   }


   /**
    * DESC: remove all clean events in event pool.
    *
    * @param eventPool Event pool for cleaning.
    */
   private void removeCleanEvents (List<Event> eventPool)
   {
      if (eventPool == null) return;

      synchronized (eventPool) {
         Iterator<Event> iterator = eventPool.iterator();

         while (iterator.hasNext())
            if ( iterator.next().isCleanEvent() )
               iterator.remove();
      }
   }
}
