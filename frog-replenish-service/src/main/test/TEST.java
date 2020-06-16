import com.chargedot.replenishservice.util.Unserialize;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TEST {
    public static void main(String[] args) {
       String str="3660";
        int seconds = Integer.parseInt(str);
        int temp=0;
        StringBuffer sb=new StringBuffer();
        temp = seconds/3600;
        sb.append((temp<10)?"0"+temp+":":""+temp+":");

        temp=seconds%3600/60;
        sb.append((temp<10)?"0"+temp+":":""+temp+":");

        temp=seconds%3600%60;
        sb.append((temp<10)?"0"+temp:""+temp);

        System.out.println(sb.toString());


    }

}