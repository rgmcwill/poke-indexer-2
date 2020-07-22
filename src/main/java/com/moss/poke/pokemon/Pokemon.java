package com.moss.poke.pokemon;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Pokemon
{
    private HashMap<String, Object> poke = new LinkedHashMap<>();

    public Pokemon() {
    }

    public HashMap toHaspMap() {
        return poke;
    }

    public Object getName() {
        return poke.get("name");
    }

    public void setName(String name) {
        poke.put("name", name);
    }

    public Object getDexNumber() {
        return poke.get("dexNumber");
    }

    public void setDexNumber(Integer dexNumber) {
        poke.put("dexNumber", dexNumber);
    }

    public Object getAdj() {
        return poke.get("adj");
    }

    public void setAdj(String adj) {
        poke.put("adj", adj);
    }

    public Object getTypes() {
        return poke.get("types");
    }

    public void setTypes(List<String> types) {
        poke.put("types", types);
    }

    public Object getAbilities() {
        return poke.get("abilities");
    }

    public void setAbilities(List<List<String>> allAbilities) {
        HashMap<String, Object> abilities = new LinkedHashMap<>();
        abilities.put("abilities", allAbilities.get(0));
        abilities.put("hiddenAbilities", allAbilities.get(1));

        poke.put("abilities", abilities);
    }

    //Methods for both full editing and getting as well as adding and reading indvidual
    public Object getEvolutions() {
        return poke.get("evolutions");
    }

    public void setEvolutions(List<String> evolutions) {
        poke.put("evolutions", evolutions);
    }

    public Object getEggGroup() {
        return poke.get("eggGroup");
    }

    public void setEggGroup(List<String> eggGroup) {
        poke.put("eggGroup", eggGroup);
    }

    public Object getCatchRate() {
        return poke.get("catchRate");
    }

    public void setCatchRate(Integer catchRate) {
        poke.put("catchRate", catchRate);
    }

    public Object getMaleRatio() {
        return poke.get("maleRatio");
    }

    public Object getFemaleRatio() {
        return poke.get("femaleRatio");
    }

    public void setMaleRatio(Double maleRatio) {
        if (maleRatio != null) {
            poke.put("maleRatio", maleRatio);
            poke.put("femaleRatio", 100 - maleRatio);
        }
    }

    //Maximum of the range
    public Object getHatchingSteps() {
        return poke.get("hatchingSteps");
    }

    public void setHatchingSteps(Integer hatchingSteps) {
        poke.put("hatchingSteps", hatchingSteps);
    }

    public Object getHeight() {
        return poke.get("height");
    }

    public void setHeight(Double height) {
        poke.put("height", height);
    }

    public Object getWeight() {
        return poke.get("weight");
    }

    public void setWeight(Double weight) {
        poke.put("weight", weight);
    }

    //Null if V+
    // public int getOldExpYield() {
    //     return this.oldExpYield;
    // }

    // public void setOldExpYield(int oldExpYield) {
    //     this.oldExpYield = oldExpYield;
    // }

    //Use if V+
    public Object getExpYield() {
        return poke.get("expYield");
    }

    public void setExpYield(List<Integer> expYield) {
        poke.put("expYield", expYield);
    }

    public Object getLevelingRate() {
        return poke.get("levelingRate");
    }

    //0-Fluctuating, 1-Slow, 2-Medium Slow, 3-Medium Fast, 4-Fast, 5-Erratic Maybe
    public void setLevelingRate(String levelingRate) {
        poke.put("levelingRate", levelingRate);
    }

    public Object getEVYield() {
        return poke.get("evYield");
    }

    public void setEVYield(List<Integer> EVYield) {
        poke.put("evYield", EVYield);
    }

    public Object getBaseFriendship() {
        return poke.get("baseFriendship");
    }

    public void setBaseFriendship(Integer baseFriendship) {
        poke.put("baseFriendship", baseFriendship);
    }

    public Object getDexColor() {
        return poke.get("dexColor");
    }

    public void setDexColor(String dexColor) {
        poke.put("dexColor", dexColor);
    }
}