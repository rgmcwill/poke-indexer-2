package com.moss.poke.pokemon;

import java.util.HashMap;

//Considerd to be the attacking type
public class Type 
{
    String name;
    HashMap<String,Integer> effectiveness;

    public Type()
    {
        effectiveness = new HashMap<>();
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public HashMap<String,Integer> getEffectiveness()
    {
        return this.effectiveness;
    }

    public int against(String typeName)
    {
        return effectiveness.get(typeName);
    }

    public void addEffectiveness(String typeName,int effect)
    {
        effectiveness.put(typeName,effect);
    }
    

}