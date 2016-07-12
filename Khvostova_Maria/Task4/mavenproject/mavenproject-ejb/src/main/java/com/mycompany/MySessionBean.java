/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.util.Map;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;


@Stateless
@Local
public class MySessionBean {
private CharacterParser ch =new CharacterParser();
    public Map<Character,Integer> parseString(String str) {
        return ch.processString(str);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
