package com.Economy;
//imports
import javafx.util.Pair;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //commands
        Hashtable<String,Pair<Runnable, String>> commands=new Hashtable<String,Pair<Runnable, String>>();
        commands.put("/help",
            new Pair<Runnable, String>(
                () -> {
            for (String key : commands.keySet()) {

                System.out.println(key+" : "+commands.get(key).getValue());
            }

        }, "Shows All Commands"));
        //input
        Scanner input = new Scanner(System.in);
        System.out.print(">");
        String inpt = input.nextLine();
        if (commands.containsKey(inpt)) {
            commands.get(inpt).getKey().run();
        }
        else {
            System.out.println("ERROR[command not found]");
        }
    }
}
