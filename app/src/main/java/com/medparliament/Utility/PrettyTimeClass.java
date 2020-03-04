package com.medparliament.Utility;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Date;

public class PrettyTimeClass {
        public static String PrettyTime(long diff)
        {
            PrettyTime p = new PrettyTime();
//            System.out.println(p.format(new Date(diff)));
//            //prints: “moments from now”
            System.out.println(p.format(new Date(diff)));
            //prints: “10 minutes from now”
            return p.format(new Date(diff));
        }
    public String decorate(Duration duration, String time)
    {
        if(duration.isInPast())
            return time + " ago";
        else
            return time + " now";
    }

}
