package com.moss.poke.pokemon;

import org.json.simple.JSONObject;
import java.util.HashMap;

public class JackMain 
{
    public static void main (String args[])
    {
        System.out.println("Hello my name is jack!");
        // Pokemon a = new Pokemon();
        // a.setName("Kingler");
        // a.setAdj("Pincer");
        
        // System.out.println(a.getName());
        // System.out.println(a.getAdj());

        JSONObject obj = new JSONObject();
        HashMap b = new HashMap<>();
        b.put("number",4);
        obj.put("name",b);
        System.out.println(obj);

    }
}