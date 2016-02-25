package com.Economy;
//imports
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import com.Economy.TimeThread;
import javafx.util.converter.IntegerStringConverter;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Main extends Application {

    //fields
    static TimeThread _tmTimeThread;
    //make window

    public void start(Stage theStage)
    {
        System.out.println("image = " +
                getClass().getResource("earth.png").toString());
        theStage.setTitle( "Timeline Example" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();


        Image earth = new Image(getClass().getResource("earth.png").toString(), true);
        Image sun = new Image(getClass().getResource("sun.png").toString(), true);
        Image space = new Image(getClass().getResource("space.png").toString(), true);


        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( earth, x, y );
                gc.drawImage( sun, 196, 196 );
            }
        }.start();

        theStage.show();
    }

    public interface MyCmd {
        void apply(String[] x);
    }
    public static void main(String[] args) {

        _tmTimeThread=new TimeThread();
        _tmTimeThread.start();

        launch(new String[0]);

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
