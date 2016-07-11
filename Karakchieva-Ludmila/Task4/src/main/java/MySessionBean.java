package main.java;

import java.util.Map;

public class MySessionBean {
    private Parser parser = new Parser();
    public Map<Character,Integer> parserString(String str){
        return parser.parserString(str);
    }
}
