package com.chargedot.replenishservice.util;
import org.junit.jupiter.api.Test;

public class TimeCycle {
    public static String Transfor(String str) {
        str="3660";
        int seconds = Integer.parseInt(str);
        int temp=0;
        StringBuffer sb=new StringBuffer();
        temp = seconds/3600;
        sb.append((temp<10)?"0"+temp+":":""+temp+":");

        temp=seconds%3600/60;
        sb.append((temp<10)?"0"+temp+":":""+temp+":");

        temp=seconds%3600%60;
        sb.append((temp<10)?"0"+temp:""+temp);

        return sb.toString();
    }
}
