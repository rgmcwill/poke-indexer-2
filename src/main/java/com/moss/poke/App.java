package com.moss.poke;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.moss.poke.pokemon.Pokemon;
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
        // System.out.println(allPoke);

        // String pokemon = allPoke.get((int) (Math.random() * (allPoke.size() - 0)) + 0);
        // pokemon = "Tapu Lele";
        // pokemon = "Mr. Mime";

        allPoke = new ArrayList<>();
        // allPoke.add("Tapu Lele");
        allPoke.add("Mr. Mime");
        allPoke.add("Mime Jr.");
        // allPoke.add("Grapploct");
        // allPoke.add("Calyrex");
        // allPoke.add("Regieleki");
        // allPoke.add("Regidrago");
        allPoke.add("Oddish");
        allPoke.add("Gloom");
        // allPoke.add("Eevee");
        // allPoke.add("Urshifu");
        allPoke.add("Wurmple");
        allPoke.add("Tyrogue");

        PokeFromSite poke = null;
        Pokemon pokemon = null;
        JSONObject allPokeObject = new JSONObject();

        for (String p : allPoke) {
            try {
                poke = new PokeFromSite(p);
                pokemon = new Pokemon();
            } catch(Exception e) {
                e.printStackTrace();
            }

            pokemon.setName(poke.getPokemon());
            pokemon.setDexNumber(poke.getDexNumb());
            pokemon.setTypes(poke.getTypes());
            pokemon.setAbilities(poke.getAbilities());
            pokemon.setMaleRatio(poke.getGenderRatio());
            pokemon.setCatchRate(poke.getCatchRate());
            pokemon.setEggGroup(poke.getEggGroup());
            pokemon.setHatchingSteps(poke.getHatchTime());
            pokemon.setHeight(poke.getHeight());
            pokemon.setWeight(poke.getWeight());
            pokemon.setExpYield(poke.getBaseExpYield());
            pokemon.setLevelingRate(poke.getLevelingRate());
            pokemon.setEVYield(poke.getEVYield());
            pokemon.setDexColor(poke.getDexColor());
            pokemon.setBaseFriendship(poke.getBaseFriendship());
            pokemon.setEvolutions(poke.getNextEvo());
            allPokeObject.put(pokemon.getDexNumber(), pokemon.toHaspMap());
        }
        writeJSONToFile(allPokeObject);
    }

    public static void writeJSONToFile(JSONObject jsonObject) { //Write JSON file
        try (FileWriter file = new FileWriter("./pokemon/Pokemon.json")) {

            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
