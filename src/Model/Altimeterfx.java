package Model;

/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
import eu.hansolo.medusa.FGauge;
import eu.hansolo.medusa.FGaugeBuilder;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.GaugeDesign;
import eu.hansolo.medusa.LcdDesign;
import java.util.Calendar;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A digital clock application that demonstrates JavaFX animation, images, and
 * effects.
 *
 * @see javafx.scene.effect.Glow
 * @see javafx.scene.shape.Polygon
 * @see javafx.scene.transform.Shear
 * @resource DigitalClock-background.
 */
public class Altimeterfx extends Application {
    private Faltimeter Faltimeter;

    private void init(Stage stage) {
        
         Group root = new Group();
        stage.setResizable(false);
        stage.setScene(new Scene(root, 500,500));
        // add background image
        //ImageView background = new ImageView(new Image(getClass().getResourceAsStream("DigitalClock-background.png")));
        // add digital clock
        Faltimeter = new Faltimeter();
        Faltimeter.setLayoutX(0);
        Faltimeter.setLayoutY(0);
        Faltimeter.getTransforms().add(new Scale(0.83f, 0.83f, 0, 0));
        // add background and clock to sample
        root.getChildren().addAll( Faltimeter);
       
    }

    public void play() {
        Faltimeter.play();
    }


    /**
     * Clock made of 6 of the Digit classes for hours, minutes and seconds.
     */
    public static class Faltimeter extends Parent {
        private static final Random RND = new Random();
      private long           lastTimerCall;
    private              Gauge  gauge;
     private              Gauge  gauge1;
    private              FGauge fGauge;
 private AnimationTimer timer;
        public Faltimeter() {
           gauge = GaugeBuilder.create()
                                                                                       // Defines if the LED should blink
                            .skinType(SkinType.SLIM)
                              .animated(true)
                              .maxValue(30000)
                              .decimals(0)
                              .unit("feet")
                              .title("Altimeter")
                                .gradientBarEnabled(true)
                              .gradientBarStops(new Stop(0.0, Color.LIME),
                                                new Stop(0.250, Color.YELLOW),
                                                new Stop(0.500, Color.ORANGE),
                                                new Stop(0.7500, Color.RED)
                                               )
                              .build();
          
         getChildren().add(gauge);
        }

        public void play() {
         lastTimerCall = System.nanoTime();
          timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > lastTimerCall + 3_000_000_000l) {
                    
                    gauge.setValue(RND.nextDouble() * gauge.getRange() + gauge.getMinValue());
                    lastTimerCall = now;
                }
               }
        };

    timer.start();
    }
}

    /**
     * Simple 7 segment LED style digit. It supports the numbers 0 through 9.
     */
  
    public double getSampleWidth() { return 480; }

    public double getSampleHeight() { return 412; }

    @Override public void start(Stage stage) {
        init(stage);
        stage.show();
        play();
        
    }
    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) { launch(args); }
}

