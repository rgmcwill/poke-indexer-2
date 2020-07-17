package com.moss.poke.pokemon;

import java.util.ArrayList;

public class Pokemon
{
    String name;
    String adj;

    Type type_1;
    Type type_2;

    Ability[] abilities;

    ArrayList<Pokemon> evolutions;

    int catchRate;

    float maleRatio;
    float femaleRatio;// won't have a setter

    int hatchingSteps;//the max of the two numbers

    float height;
    float weight;

    int oldExpYield;
    int newExpYield;

    int levelingRate;//0-5 Fluctuating, slow, medium slow, medium fast, eradic

    int[] EVYield;

    int baseFriendship;

    public Pokemon()
    {
        abilities = new Ability[3];
        evolutions = new ArrayList<>();
        EVYield = new int[6];
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdj() {
        return this.adj;
    }

    public void setAdj(String adj) {
        this.adj = adj;
    }

    public Type getType_1() {
        return this.type_1;
    }

    public void setType_1(Type type_1) {
        this.type_1 = type_1;
    }

    public Type getType_2() {
        return this.type_2;
    }

    public void setType_2(Type type_2) {
        this.type_2 = type_2;
    }

    public Ability getAbility(int n) {
        return this.abilities[n];
    }

    public void setAbility(int n, Ability ability) {
        this.abilities[n] = ability;
    }

    //Methods for both full editing and getting as well as adding and reading indvidual
    public ArrayList<Pokemon> getEvolutions() {
        return this.evolutions;
    }

    public Pokemon getEvolution(int n)
    {
        return this.evolutions.get(n);
    }

    public void addEvolution(Pokemon pokemon) {
        this.evolutions.add(pokemon);
    }

    public int getCatchRate() {
        return this.catchRate;
    }

    public void setCatchRate(int catchRate) {
        this.catchRate = catchRate;
    }

    public float getMaleRatio() {
        return this.maleRatio;
    }

    public void setMaleRatio(float maleRatio) {
        this.maleRatio = maleRatio;
        this.femaleRatio = 100-maleRatio;
    }
    
    //Automaticly detirmind off of maleRatio
    public float getFemaleRatio()
    {
        return this.femaleRatio;
    }

    //Maximum of the range
    public int getHatchingSteps() {
        return this.hatchingSteps;
    }

    public void setHatchingSteps(int hatchingSteps) {
        this.hatchingSteps = hatchingSteps;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    //Null if V+
    public int getOldExpYield() {
        return this.oldExpYield;
    }

    public void setOldExpYield(int oldExpYield) {
        this.oldExpYield = oldExpYield;
    }

    //Use if V+
    public int getNewExpYield() {
        return this.newExpYield;
    }

    public void setNewExpYield(int newExpYield) {
        this.newExpYield = newExpYield;
    }

    public int getLevelingRate() {
        return this.levelingRate;
    }

    //0-Fluctuating, 1-Slow, 2-Medium Slow, 3-Medium Fast, 4-Fast, 5-Erratic
    public void setLevelingRate(int levelingRate) {
        this.levelingRate = levelingRate;
    }

    public int[] getEVYield() {
        return this.EVYield;
    }

    //0-Hp, 1-Atk, 2-Def, 3-Sp. Atk, 4-Sp. Def, 5-Speed
    public int getEVYeild(int n)
    {
        return this.EVYield[n];
    }

    public void setEVYield(int[] EVYield) {
        this.EVYield = EVYield;
    }

    //0-Hp, 1-Atk, 2-Def, 3-Sp. Atk, 4-Sp. Def, 5-Speed
    public void setEVYield(int n, int EV)
    {
        this.EVYield[n] = EV;
    }

    public int getBaseFriendship()
    {
        return this.baseFriendship;
    }

    public void setBaseFriendship(int baseFriendship)
    {
        this.baseFriendship = baseFriendship;
    }
}