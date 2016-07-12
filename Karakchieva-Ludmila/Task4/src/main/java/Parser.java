package main.java;

import java.util.Map;
import java.util.TreeMap;

public class Parser {
    public Map<Character,Integer> parserString(String str){
        Map<Character,Integer> map = new TreeMap<>();
        if(str!=null){
            char[] arr = str.toCharArray();
            for(char ch:arr){
                Integer count = map.get(ch);
                if(count == null){
                    count = 1;
                }
                map.put(ch,++count);
            }
        }
        return map;
    }
}
