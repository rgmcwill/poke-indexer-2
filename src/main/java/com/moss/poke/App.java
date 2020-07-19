package com.moss.poke;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import com.moss.poke.scraper.AllPokeFromSite;
import com.moss.poke.scraper.PokeFromSite;

public class App 
{
    public static void main( String[] args )
    {
        String searchUrl = "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_National_Pok%C3%A9dex_number";
        AllPokeFromSite allPokeFromSite = new AllPokeFromSite(searchUrl);
        List<String> allPoke = allPokeFromSite.getAllPokemon();
        System.out.println(allPoke);

        // String pokemon = allPoke.get((int) (Math.random() * (allPoke.size() - 0)) + 0);
        // pokemon = "Tapu Lele";
        // pokemon = "Mr. Mime";

        PokeFromSite poke = null;

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
            System.out.println("");
        }
    }
}
