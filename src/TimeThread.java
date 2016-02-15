package com.Economy;

public class TimeThread extends Thread{

    int _tm;
    boolean _shut;
    float _f;
    public TimeThread(){

        _tm=0;
        _shut=false;
        _f=1;

    }
    public void tick() {

        _tm ++;

    }
    public void run(){

        System.out.println("Thread Successfully Started");

        try {

        while(!_shut){
            int fi=(int)_f;
            float dif=_f-fi;
            for (int i = 0; i < fi && !_shut; i++){
                Thread.sleep(1000);
            }
            Thread.sleep((long)(1000*dif));

            tick();
        }

        } catch(InterruptedException e) {

            System.out.println("sleep interrupted");
        }

        System.out.println("Thread Successfully Ended");

    }
}