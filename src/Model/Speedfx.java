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
import eu.hansolo.medusa.Section;
import java.util.Calendar;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
public class Speedfx extends Application {
    private Speed sspeed;

    private void init(Stage stage) {
        
         Group root = new Group();
        stage.setResizable(false);
        stage.setScene(new Scene(root, 500,500));
        // add background image
        //ImageView background = new ImageView(new Image(getClass().getResourceAsStream("DigitalClock-background.png")));
        // add digital clock
        sspeed = new Speed();
        sspeed.setLayoutX(0);
        sspeed.setLayoutY(0);
        sspeed.getTransforms().add(new Scale(0.83f, 0.83f, 0, 0));
        // add background and clock to sample
        root.getChildren().addAll( sspeed);
       
    }

    public void play() {
        sspeed.play();
    }


    /**
     * Clock made of 6 of the Digit classes for hours, minutes and seconds.
     */
    public static class Speed extends Parent {
        private static final Random RND = new Random();
      private long           lastTimerCall;
  
     private              Gauge  gauge1;
    private              FGauge fGauge;
 private AnimationTimer timer;
        public Speed() {
           gauge1 = GaugeBuilder.create()
                             .skinType(SkinType.MODERN) 
                             .prefSize(400, 400)
                              // Related to scale
                            .scaleDirection(Gauge.ScaleDirection.CLOCKWISE)                                           // Define the direction of the Scale (CLOCKWISE, COUNTER_CLOCKWISE)
                            .minValue(0)                                                                        // Set the start value of the scale
                            .maxValue(160)                                                                      // Set the end value of the scale
                            .startAngle(320)                                                                    // Set the start angle of your scale (bottom -> 0, direction -> CCW)
                            .angleRange(280)   
                             .sections(
                                       new Section(45,140, "", Color.rgb(120,30 , 140 , 0.75)),
                                       new Section(120,160 , "", Color.rgb(204, 0, 0)))
                             .sectionTextVisible(true)
                             .title("SPEED")
                             .unit("km/h")
                             .threshold(120)
                             .thresholdVisible(true)
                             .animated(true)
                             .build();


         getChildren().add(gauge1);
        }

        public void play() {
         lastTimerCall = System.nanoTime();
          timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > lastTimerCall + 3_000_000_000l) {
                    
                    gauge1.setValue(RND.nextDouble() * gauge1.getRange() + gauge1.getMinValue());
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

