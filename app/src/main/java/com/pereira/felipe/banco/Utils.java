package com.pereira.felipe.banco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {

    private  static List<Integer> Dice;

    public static int rollDice(){
        Dice = new ArrayList();
        for(int x = 2; x <= 12; x++){
            Dice.add(x);
        }
        Collections.shuffle(Dice);
        Random r = new Random();
        int index = r.nextInt(Dice.size());

        return Dice.get(index);


    }
}
