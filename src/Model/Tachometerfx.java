package Model;

/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
import eu.hansolo.medusa.FGauge;
import eu.hansolo.medusa.FGaugeBuilder;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.GaugeDesign;
import eu.hansolo.medusa.LcdDesign;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.util.Duration;
import java.util.Calendar;
import java.util.Random;
import javafx.animation.AnimationTimer;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Stop;

/**
 * A digital clock application that demonstrates JavaFX animation, images, and
 * effects.
 *
 * @see javafx.scene.effect.Glow
 * @see javafx.scene.shape.Polygon
 * @see javafx.scene.transform.Shear
 * @resource DigitalClock-background.
 */
public class Tachometerfx extends Application {
    private Tachometer Tach;

    private void init(Stage stage) {
        
         Group root = new Group();
        stage.setResizable(false);
        stage.setScene(new Scene(root, 500,500));
        // add background image
        //ImageView background = new ImageView(new Image(getClass().getResourceAsStream("DigitalClock-background.png")));
        // add digital clock
        Tach = new Tachometer();
        Tach.setLayoutX(0);
        Tach.setLayoutY(0);
        Tach.getTransforms().add(new Scale(0.83f, 0.83f, 0, 0));
        // add background and clock to sample
        root.getChildren().addAll( Tach);
       
    }

    public void play() {
        Tach.play();
    }


    /**
     * Clock made of 6 of the Digit classes for hours, minutes and seconds.
     */
    public static class Tachometer extends Parent {
        private static final Random RND = new Random();
      private long           lastTimerCall;
  
     private              Gauge  gauge1;
    private              FGauge fGauge;
 private AnimationTimer timer;
        public Tachometer() {
           gauge1 = GaugeBuilder.create()
                            .prefSize(500,500)                                                                  // Set the preferred size of the control
                            // Related to Foreground Elements
                            .foregroundBaseColor(Color.WHITE)                                                   // Defines a color for title, subtitle, unit, value, tick label, tick mark, major tick mark, medium tick mark and minor tick mark
                            // Related to Title Text
                            .title("Tachometer")                                                                     // Set the text for the title
                            // Related to Sub Title Text
                                                                                         // Set the text for the subtitle
                            // Related to Unit Text
                            .unit("RPM")                                                                       // Set the text for the unit
                            // Related to Value Text
                            .decimals(2)                                                                        // Set the number of decimals for the value/lcd text
                            // Related to LCD
                            .lcdVisible(true)                                                                   // Display a LCD instead of the plain value text
                            .lcdDesign(LcdDesign.STANDARD)                                                      // Set the design for the LCD
                            .lcdFont(Gauge.LcdFont.DIGITAL_BOLD)                                                      // Set the font for the LCD (STANDARD, LCD, DIGITAL, DIGITAL_BOLD, ELEKTRA)
                            // Related to scale
                            .scaleDirection(Gauge.ScaleDirection.CLOCKWISE)                                           // Define the direction of the Scale (CLOCKWISE, COUNTER_CLOCKWISE)
                            .minValue(0)                                                                        // Set the start value of the scale
                            .maxValue(30)                                                                      // Set the end value of the scale
                            .startAngle(320)                                                                    // Set the start angle of your scale (bottom -> 0, direction -> CCW)
                            .angleRange(280)                                                                    // Set the angle range of your scale starting from the start angle
                                                                              // Define the color for tick labels (overriden by tick label sections)
                                                                     // Define sections to color tick marks
                            // Related to Major Tick Marks
                            .majorTickMarksVisible(true)                                                        // Define if major tick marks should be visible
                            .majorTickMarkType(Gauge.TickMarkType.TRIANGLE)                                           // Define the tick mark type for major tick marks (LINE, DOT, TRIANGLE, TICK_LABEL)
                            // Related to Medium Tick Marks
                            .mediumTickMarksVisible(false)                                                      // Define if medium tick marks should be visible
                            .mediumTickMarkType(Gauge.TickMarkType.LINE)                                              // Define the tick mark type for medium tick marks (LINE, DOT, TRIANGLE)
                            // Related to Minor Tick Marks
                            .minorTickMarksVisible(true)                                                        // Define if minor tick marks should be visible
                            .minorTickMarkType(Gauge.TickMarkType.LINE)                                               // Define the tick mark type for minor tick marks (LINE, DOT, TRIANGLE)
                            // Related to LED
                            .ledVisible(false)                                                                  // Defines if the LED should be visible
                            .ledType(Gauge.LedType.STANDARD)                                                          // Defines the type of the LED (STANDARD, FLAT)
                            .ledColor(Color.rgb(255, 200, 0))                                                   // Defines the color of the LED
                            .ledBlinking(false)                                                                 // Defines if the LED should blink
                            // Related to Needle
                            .needleShape(Gauge.NeedleShape.ANGLED)                                                    // Defines the shape of the needle (ANGLED, ROUND, FLAT)
                            .needleSize(Gauge.NeedleSize.STANDARD)                                                    // Defines the size of the needle (THIN, STANDARD, THICK)
                            .needleColor(Color.CRIMSON)                                                         // Defines the color of the needle
                    
                           // Related to Needle behavior
                            .startFromZero(false)                                                               // Defines if the needle should start from the 0 value
                            .returnToZero(false)                                                                // Defines if the needle should always return to the 0 value (only makes sense when animated==true)
                            // Related to Knob
                            .knobType(Gauge.KnobType.METAL)                                                           // Defines the type for the center knob (STANDARD, PLAIN, METAL, FLAT)
                            .knobColor(Color.LIGHTGRAY)                                                         // Defines the color that should be used for the center knob
                            .interactive(false)                                                                 // Defines if it should be possible to press the center knob
                            .onButtonPressed(buttonEvent -> System.out.println("Knob pressed"))                 // Defines a handler that will be triggered when the center knob was pressed
                            .onButtonReleased(buttonEvent -> System.out.println("Knob released"))               // Defines a handler that will be triggered when the center knob was released
                            // Related to Threshold
                            .thresholdVisible(false)                                                            // Defines if the threshold indicator should be visible
                            .threshold(50)                                                                      // Defines the value for the threshold
                            .thresholdColor(Color.RED)                                                          // Defines the color for the threshold
                            .checkThreshold(true)                                                              // Defines if each value should be checked against the threshold
                            .onThresholdExceeded(thresholdEvent -> System.out.println("Threshold exceeded"))    // Defines a handler that will be triggered if checkThreshold==true and the threshold is exceeded
                            .onThresholdUnderrun(thresholdEvent -> System.out.println("Threshold underrun"))    // Defines a handler that will be triggered if checkThreshold==true and the threshold is underrun
                            // Related to Gradient Bar
                            .gradientBarEnabled(true) 
                           
                             // Defines if a gradient filled bar should be visible to visualize a range
                           .gradientBarStops(new Stop(10, Color.CYAN),
                                              new Stop(11, Color.GREEN),
                                              new Stop(25, Color.CYAN)
                                              //new Stop(0.25, Color.YELLOW),
                                              //new Stop(0.3, Color.RED)
                                             )
                          
                                                                                 
                            // Related to Value
                            .animated(true)                                                                     // Defines if the needle will be animated
                            .animationDuration(1000)                                                             // Defines the speed of the needle in milliseconds (10 - 10000 ms)
                            .build();

        /**
         *  Create a gauge with a frame and background that utilizes a Medusa gauge
         *  You can choose between the following GaugeDesigns
         *  - METAL
         *  - TILTED_GRAY
         *  - STEEL
         *  - BRASS
         *  - GOLD
         *  - BLACK_METAL
         *  - SHINY_METAL
         *  - ENZO
         *  - FLAT (when used one could set the frame color by calling GaugeDesign.FLAT.frameColor = Color.ORANGE;)
         *  - TRANSPARENT
         *
         *  and the following GaugeBackgrounds
         *  - DARK_GRAY
         *  - BEIGE
         *  - ANTHRACITE
         *  - LIGHT_GRAY
         *  - WHITE
         *  - BLACK
         *  - CARBON
         *  - TRANSPARENT
         */
        fGauge = FGaugeBuilder
            .create()
            .prefSize(500, 500)
            .gauge(gauge1)
            .gaugeDesign(GaugeDesign.METAL)
            .gaugeBackground(GaugeDesign.GaugeBackground.CARBON)
            .foregroundVisible(true)
            .build();
          
         getChildren().add(fGauge);
        }

        public void play() {
         lastTimerCall = System.nanoTime();
          timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > lastTimerCall + 3_000_000_000l) {
                    
                    fGauge.getGauge().setValue(RND.nextDouble() * gauge1.getRange() + gauge1.getMinValue());
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

