package com.chargedot.replenishservice.util;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.druid.util.StringUtils;
import org.phprpc.util.AssocArray;
import org.phprpc.util.Cast;
import org.phprpc.util.PHPSerializer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sjt
 */
public class Unserialize {
    /**
     * php转Java
     * @param str
     * @return
     */
    public static Map<Object,Object> phpToJava(String str){
        str=str.substring(5,str.length()-1);
        str = str.replaceAll("[s|i]:\\d+:", "").replaceAll("N", "\"\"");
        String[] split = str.split(";");
        HashMap<Object,Object> map =new HashMap<Object, Object>();
        for (int i = 0; i < split.length; i=i+2) {
            if(i%2==0){
                map.put(split[i].replaceAll("\"", ""), split[i+1].replaceAll("\"", ""));
            }
        }
        return  map;
    }


    /**
     *  对php序列化的字符串，进行反序列化
     */
    public  static HashMap unserializePHP(String content){
        HashMap<Object,Object> map =new HashMap<Object, Object>();
        PHPSerializer p = new PHPSerializer();
        if (StringUtils.isEmpty(content)){
            return map;
        }
        try {
            AssocArray array = (AssocArray) p.unserialize(content.getBytes());
            return array.toHashMap();
        }catch (Exception e){
            System.out.println("反序列化PHParray: " + content + " 失败！！！" );
        }
        return map;
    }


    public static void main(String[] args) {
        String content = "a:4:{s:6:\"certId\";i:0;s:6:\"userId\";i:108212;s:9:\"timestamp\";d:1574998008.35971;s:7:\"open_id\";s:28:\"oCF-K5dpU6VPjcurfeRt5p6TI2RA\";}";
        unserializePHP(content);
    }

    /**
     *  对php序列化的字符串，进行反序列化
     */
    public static List<String> unserializePHParray(String content){
        List<String> list = new ArrayList<String>();
        PHPSerializer p = new PHPSerializer();
        if (StringUtils.isEmpty(content)){
            return list;}
        try {
            AssocArray array = (AssocArray) p.unserialize(content.getBytes());
            for (int i = 0; i < array.size(); i++) {
                String t = (String) Cast.cast(array.get(i), String.class);
                list.add(t);
            }
        }catch (Exception e){
            System.out.println("反序列化PHParray: " + content + " 失败！！！" );
        }
        return list;
    }

}
