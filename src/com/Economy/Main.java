package com.Economy;
//imports
import javafx.util.Pair;

import com.Economy.TimeThread;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

    //fields
    static TimeThread _tm;

    public static void main(String[] args) {

        _tm=new TimeThread();
        _tm.start();

        //commands
        Hashtable<String,Pair<Runnable, String>> commands=new Hashtable<String,Pair<Runnable, String>>();
        commands.put("/help",
            new Pair<Runnable, String>(
                () -> {
            for (String key : commands.keySet()) {

                System.out.println(key+" : "+commands.get(key).getValue());
            }

        }, "Shows All Commands"));
        commands.put("/exit",
                new Pair<Runnable, String>(
                        () -> {
                            _tm._shut=true;
                            try {

                                _tm.join(1000);

                            }
                            catch(Exception a){}
                            System.out.println("Exiting... Please Standby");
                            System.exit(0);
                            }, "Exits Game"));
        //input
        while(true) {
            Scanner input = new Scanner(System.in);
            System.out.print(">");
            String inpt = input.nextLine();
            if (commands.containsKey(inpt)) {
                commands.get(inpt).getKey().run();
            } else {
                System.out.println("ERROR[command not found]");
            }
        }
    }
}
