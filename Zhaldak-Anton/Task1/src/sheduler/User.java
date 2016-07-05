package sheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class for description users. <br> Every user has name, timezone and pool of events. User can be active or passive.
 * Scheduler handles the pool of events just for active users.
 */
public class User
{
   private String name = null;  // User name
   private int timezone = 0;     // User timezone
   private boolean state = false; // true - active, false - passive
   private List eventPool = null; // List of events (ArrayList class)


   /**
    * DISC: Create object for define user.
    *
    * @param name     User name
    * @param timezone User timezone
    * @param state    User state (active <code>[true]</code> or passive<code>[false]</code>)
    */
   public User (String name, int timezone, boolean state)
   {
      this.name = (name != null) ? name : "null-name";
      this.timezone = timezone;
      this.state = state;
      eventPool = new ArrayList(0);
   }

   /**
    * DISC: set user timezone.
    *
    * @param timezone New user timezone
    */
   public void setTimezone (int timezone)
   {
      this.timezone = timezone;
   }


   /**
    * DISC: set user state. If state equals TRUE then user is active, else - passive.
    *
    * @param state New user state
    */
   public void setState (boolean state)
   {
      this.state = state;
   }


   /**
    * DISC: add event in event pool.
    *
    * @param event Event for adding
    */
   public void addEvent (Event event)
   {
      if (event == null)
      {
         System.err.println("User.addEvent: event is null");
         return;
      }

      int insertIndex = getInsertPosition(event);

      if (insertIndex < 0)
      {
         System.err.println("Inner errors. Check User.getInsertPosition");
         return;
      }

      eventPool.add(insertIndex, event);
   }


   /**
    * DISC: return user name.
    *
    * @return User name
    */
   public String getName ()
   {
      return name;
   }


   /**
    * DISC: return user timezone.
    *
    * @return User timezone
    */
   public int getTimezone ()
   {
      return timezone;
   }


   /**
    * DISC: return user state.
    *
    * @return User state. If state is <code>true</code> then user is active, else user is passive.
    */
   public boolean isState ()
   {
      return state;
   }


   /**
    * DISC: return event pool of user
    *
    * @return Event pool of user
    */
   public List<Event> getEventPool ()
   {
      return eventPool;
   }


   /**
    * DISC: return number of position for insert in <code>event pool</code>. Try sort date event in ascending order (up)
    * and text event in descending order (down). For example:<br>
    * <pre><code>   19.11.1995-18:11:11 "Hello 1"</code><br></pre>
    * <pre><code>   19.11.1995-18:11:12 "Hello 2"</code><br></pre>
    * <pre><code>   19.11.1995-18:11:12 "Hello 1"</code><br></pre>
    *
    * @param event Event for insert
    *
    * @return Number of position for insert, or<br> -1 - input argument <code>event</code> is <code>null</code><br> -2 -
    * <code>event pool</code> is null -3 - position not found
    */
   private int getInsertPosition (Event event)
   {
      if (event == null) return -1;
      if (eventPool == null) return -2;

      Date date = event.getDate();

      // If eventPool is empty
      if (eventPool.size() == 0) return 0;

      // If event date less than first date
      Date selDate = ((Event) eventPool.get(0)).getDate();
      if (selDate.getTime() > date.getTime()) return 0;

      // If event date more than last date
      selDate = ((Event) eventPool.get(eventPool.size() - 1)).getDate();
      if (selDate.getTime() < date.getTime()) return eventPool.size();

      // Find place between first and last dates
      Date preSelDate = ((Event) eventPool.get(0)).getDate();

      String eventText = event.getText();
      String selText = ((Event) eventPool.get(0)).getText();

      // Check first date and event date on equal
      if (preSelDate.getTime() == date.getTime())
      {
         // Sort text event in descending order (down)
         if ((selText.compareTo(eventText)) > 0)
            return 1;
         else
            return 0;
      }

      // Check all of events
      for (int i = 1; i < eventPool.size(); i++)
      {
         // Nearby events
         preSelDate = ((Event) eventPool.get(i - 1)).getDate();
         selDate = ((Event) eventPool.get(i)).getDate();

         // Check equals
         if (selDate.getTime() == date.getTime())
         {
            selText = ((Event) eventPool.get(i)).getText();
            // Sort text event in descending order (down)
            if ((selText.compareTo(eventText)) > 0)
               return i + 1;
            else
               return i;
         }

         if (preSelDate.getTime() < date.getTime()
               && date.getTime() < selDate.getTime())
         {
            return i;
         }
      }

      System.err.println("User.getInsertPosition: position not found.");
      return -3;
   }
}
