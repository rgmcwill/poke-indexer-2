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

        String pokemon = allPoke.get((int) (Math.random() * (allPoke.size() - 0)) + 0);
        pokemon = "Charizard";
        System.out.println("------"+pokemon+"------");

        String siteUrl = null;

        try {
            siteUrl = "https://bulbapedia.bulbagarden.net/wiki/" + URLEncoder.encode((pokemon), "UTF-8") + "_(Pok%C3%A9mon)";
        }catch(Exception e){
            e.printStackTrace();
        }

        PokeFromSite poke = new PokeFromSite(siteUrl, pokemon);


    }
}
