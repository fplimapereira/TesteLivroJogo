package com.pereira.felipe.banco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {

    private  static List<Integer> DoubleDice;
    private  static List<Integer> Dice;

    private static Random r = new Random();

    public static List<Integer> createDoubleDice(){
        DoubleDice = new ArrayList();
        for(int x = 2; x <= 12; x++){
            DoubleDice.add(x);
        }
        Collections.shuffle(DoubleDice);
        return DoubleDice;
    }

    public static List<Integer> createDice(){
        Dice = new ArrayList();
        for(int x = 1; x <= 6; x++){
            Dice.add(x);
        }
        Collections.shuffle(Dice);
        return Dice;
    }

    public static int rollDice(){
        int index = r.nextInt(Dice.size());
        return Dice.get(index);
    }

    public static int rollDices(){
        int index = r.nextInt(DoubleDice.size());
        return DoubleDice.get(index);
    }
}
