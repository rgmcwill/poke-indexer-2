package com.moss.poke;

import java.net.URLEncoder;
import java.util.List;

import com.moss.poke.scraper.AllPokeFromSite;
import com.moss.poke.scraper.PokeFromSite;

public class App 
{
    public static void main( String[] args )
    {
        // String searchUrl = "https://bulbapedia.bulbagarden.net/wiki/" + URLEncoder.encode(searchQuery, "UTF-8") + "_(Pok%C3%A9mon)";
        String searchUrl = "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_National_Pok%C3%A9dex_number";
        AllPokeFromSite allPokeFromSite = new AllPokeFromSite(searchUrl);
        List<String> allPoke = allPokeFromSite.getAllPokemon();
        System.out.println(allPoke);

        try {
            PokeFromSite poke = new PokeFromSite("https://bulbapedia.bulbagarden.net/wiki/" + URLEncoder.encode(allPoke.get(0), "UTF-8") + "_(Pok%C3%A9mon)");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
