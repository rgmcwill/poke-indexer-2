package com.moss.poke.pokemon;

public class JackMain 
{
    public static void main (String args[])
    {
        System.out.println("Hello my name is jack!");
        Pokemon a = new Pokemon();
        a.setName("Kingler");
        a.setAdj("Pincer");
        
        System.out.println(a.getName());
        System.out.println(a.getAdj());
    }
}