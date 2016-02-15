package com.Economy;
//imports
import javafx.util.Pair;

import com.Economy.TimeThread;
import javafx.util.converter.IntegerStringConverter;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

    //fields
    static TimeThread _tmTimeThread;

    public interface MyCmd {
        void apply(String[] x);
    }
    public static void main(String[] args) {

        _tmTimeThread=new TimeThread();
        _tmTimeThread.start();


        //commands
        Hashtable<String,Pair<MyCmd, String>> commands=new Hashtable<String,Pair<MyCmd, String>>();
        commands.put("/help",
            new Pair<MyCmd, String>(
                (String[] b) -> {
            for (String key : commands.keySet()) {

                System.out.println(key+" : "+commands.get(key).getValue());
            }

        }, "Shows All Commands"));
        commands.put("/exit",
                new Pair<MyCmd, String>(
                        (String[] a1) -> {
                            _tmTimeThread._shut=true;
                            try {

                                _tmTimeThread.join(1000);

                            }
                            catch(Exception a2){}
                            System.out.println("Exiting... Please Standby");
                            System.exit(0);
                            }, "Exits Game"));
        commands.put("/time_change",
                new Pair<MyCmd, String>(
                        (String[] a4) -> {

                            if(a4.length != 2) {

                                System.out.println("ERROR[not enough arguments]");
                                return;

                            }
                            try {
                                float f=Float.parseFloat(a4[1]);
                                if(f<0) {

                                    System.out.println("ERROR[no time travel]");
                                    return;

                                }
                                _tmTimeThread._f = f;

                            }catch(Exception a2){

                                System.out.println("ERROR[argument type invalid]");
                                return;

                            }

                        }, "Speeds or Slows Time"));

        commands.put("/time_show",
                new Pair<MyCmd, String>(
                        (String[] b) -> {

                            System.out.println(_tmTimeThread._tm);

                        }, "Shows Time"));


        //input
        while(true) {
            Scanner input = new Scanner(System.in);
            System.out.print(">");
            String[] inpt = input.nextLine().split(" ");
            if (commands.containsKey(inpt[0])) {
                commands.get(inpt[0]).getKey().apply(inpt);
            } else {
                System.out.println("ERROR[command not found]");
            }
        }
    }
}
