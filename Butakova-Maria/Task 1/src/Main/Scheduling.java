package Main;

import java.util.Date;

public class Scheduling extends Thread{
    
    public Scheduling(){ }
    
    @Override
    public void run ()
    {
        while(true)
        {
            Date cur_date = new Date(System.currentTimeMillis());

            for(int i = 0; i < Main.users.size(); i ++)
            {
                if(Main.users.get(i).status)
                {
                    for(int j = 0; j < Main.users.get(i).us_event.size(); j++)
                        if((cur_date.getTime() - 300) < Main.users.get(i).us_event.get(j).time.getTime()
                                && Main.users.get(i).us_event.get(j).time.getTime() < (cur_date.getTime()+300))
                        {
                            System.out.println("---------------------------");
                            System.out.print(cur_date.toString() + '\t' + Main.users.get(i).name+
                                    '\t' + Main.users.get(i).us_event.get(j).text + '\n');
                        }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
  