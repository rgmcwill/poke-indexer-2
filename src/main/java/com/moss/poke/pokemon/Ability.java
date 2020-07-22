package com.moss.poke.pokemon;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Ability 
{
    private HashMap<String, Object> abil = new LinkedHashMap<>();
    String name;
    String desc;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}