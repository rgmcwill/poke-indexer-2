package com.moss.poke;

import com.moss.poke.pokemon.*;
import org.json.simple.JSONObject;
import java.util.HashMap;

public class PokeToJSON 
{
    String directory;

    public PokeToJSON()
    {
        directory = "\\JSON";
    }

    public String pokeToMap(Pokemon poke)
    {   
        HashMap toReturn = new HashMap();
        toReturn.put("name",poke.getName());
        toReturn.put("adj",poke.getAdj());
        toReturn.put("type_1",typeToMap(poke.getType_1()));
        toReturn.put("type_2",typeToMap(poke.getType_2()));
        toReturn.put("abilities",abilitiesToList(poke.getAbilities()));
        toReturn.put("evolutions",evolutionsToList(poke.getEvolutions()));
        toReturn.put("maleRatio",poke.get)
    }
}