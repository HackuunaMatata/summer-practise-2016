/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.util.Map;
import java.util.TreeMap;


public class CharacterParser {
    public Map<Character,Integer> processString(String str){
        Map<Character,Integer> resultMap= new TreeMap<Character,Integer>();
        if(str!=null){
           char[] chars= str.toCharArray();
           for(char ch:chars){
               Integer charsCount=resultMap.get(ch);
               if(charsCount==null){
                   charsCount=0;
               }
               resultMap.put(ch, ++charsCount);
           }
        }
        return resultMap;
    }
}
