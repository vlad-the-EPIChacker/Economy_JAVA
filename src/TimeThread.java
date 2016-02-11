package com.Economy;

public class TimeThread extends Thread{

    int _tm;
    boolean _shut;
    public TimeThread(){

        _tm=0;
        _shut=false;

    }
    public void tick() {

        _tm ++;

    }
    public void run(){

        System.out.println("Thread Successfully Started");

        try {

        while(!_shut){

            Thread.sleep(1000);
            tick();

        }

        } catch(InterruptedException e) {

            System.out.println("sleep interrupted");
        }

        System.out.println("Thread Successfully Ended");

    }
}