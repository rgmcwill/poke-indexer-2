package com.moss.poke;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.moss.poke.scraper.AllPokeFromSite;
import com.moss.poke.scraper.PokeFromSite;

import org.json.simple.JSONObject;

public class App 
{
    public static void main( String[] args )
    {
        String searchUrl = "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_National_Pok%C3%A9dex_number";
        AllPokeFromSite allPokeFromSite = new AllPokeFromSite(searchUrl);
        List<String> allPoke = allPokeFromSite.getAllPokemon();
        // allPoke.remove("Inkay");
        // allPoke.remove("Malamar");
        // allPoke.remove("Rockruff");
        // allPoke.remove("Lycanroc");
        System.out.println(allPoke);

        // String pokemon = allPoke.get((int) (Math.random() * (allPoke.size() - 0)) + 0);
        // pokemon = "Tapu Lele";
        // pokemon = "Mr. Mime";

        allPoke = new ArrayList<>();
        // allPoke.add("Tapu Lele");
        allPoke.add("Mr. Mime");
        // allPoke.add("Mime Jr.");
        // allPoke.add("Grapploct");
        // allPoke.add("Calyrex");
        // allPoke.add("Regieleki");
        // allPoke.add("Regidrago");
        // allPoke.add("Oddish");
        // allPoke.add("Gloom");
        allPoke.add("Eevee");
        // allPoke.add("Urshifu");
        // allPoke.add("Wurmple");
        // allPoke.add("Tyrogue");
        // allPoke.add("Malamar");
        // allPoke.add("Inkay");
        // allPoke.add("Malamar");
        // allPoke.add("Rockruff");
        // allPoke.add("Lycanroc");
        // allPoke.add("Electivire");

        PokeFromSite poke = null;
        System.out.println("");

        for (String p : allPoke) {
            try {
                poke = new PokeFromSite(p);
            } catch(Exception e) {
                e.printStackTrace();
            }

            System.out.print(poke.getPokemon());
            System.out.print(" | ");
            System.out.print(poke.getDexNumb());
            System.out.print(" | ");
            System.out.print(poke.getTypes());
            System.out.print(" | ");
            System.out.print(poke.getAbilities());
            System.out.print(" | ");
            System.out.print(poke.getGenderRatio());
            System.out.print(" | ");
            System.out.print(poke.getCatchRate());
            System.out.print(" | ");
            System.out.print(poke.getEggGroup());
            System.out.print(" | ");
            System.out.print(poke.getHatchTime());
            System.out.print(" | ");
            System.out.print(poke.getHeight());
            System.out.print(" | ");
            System.out.print(poke.getWeight());
            System.out.print(" | ");
            System.out.print(poke.getBaseExpYield());
            System.out.print(" | ");
            System.out.print(poke.getLevelingRate());
            System.out.print(" | ");
            System.out.print(poke.getEVYield());
            System.out.print(" | ");
            System.out.print(poke.getDexColor());
            System.out.print(" | ");
            System.out.print(poke.getBaseFriendship());

            // Set<String> hs = poke.getNextEvo();
            // if (hs != null)
            //     allSpecialEvoReq.addAll(hs);
            System.out.println("");
            poke.getNextEvo();
            System.out.println("");
            System.out.println("");
        }

        // Pokemon a = new Pokemon();
        // a.setName("Kingler");
        // a.setAdj("Pincer");
        
        // System.out.println(a.getName());
        // System.out.println(a.getAdj());

        // JSONObject obj = new JSONObject();
        // JSONObject b = new JSONObject();
        // b.put("number",4);
        // ArrayList b = new ArrayList();
        // b.add("Hello");
        // b.add(1);
        // b.add("1");
        // obj.put("name",b);
        // System.out.println(obj);
    }
}
