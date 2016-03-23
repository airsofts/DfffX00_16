/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.*;
import Model.Tachometerfx.Tachometer;
import com.javafx.experiments.importers.Importer3D;
import com.javafx.experiments.jfx3dviewer.AutoScalingGroup;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Marker;
import eu.hansolo.medusa.Section;
import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.geom.Angle;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JPanel;

import javax.swing.SwingUtilities;

/**
 *
 * @author Amine Djebbi
 */

@ViewController("Vue/ControlPanelDesigne.fxml")
public class ControlPanelDesign 
{
  Pane Pn1=new Pane();  

 
   //Composants des javaFX:
   

    @FXML
    private HBox HBox_pane_tachometre;
        
    @FXML
    private TextArea textArea1;
    
    @FXML
    private TextArea textArea2;

    @FXML
     @ActionTrigger("affiche")
    private Button btn_active;

    @FXML
    private ImageView problemIV111111;
   
    @FXML
    private ImageView problemIV11;

    @FXML
    private TextArea AlertMessageArea11;

    @FXML
    private ImageView problemIV1111;

    @FXML
    private Pane pane;

    @FXML
    private TextArea textArea21;

    @FXML
    public Pane pane3D;
    
    //--EIFIS Controls
    @FXML 
    Group MFD;
    
    @FXML 
    Pane EIFIS;
    
    //--MAP Controls
    @FXML 
    Pane MAP;
    
    @FXML
    Pane MAP1;
    
    //-----------------------Composants EIFIS
    private     Horizon horizon   = new Horizon();
    private Altimeterfx.Faltimeter  altimeter;
    private Tachometer     rpm;
    private  Speedfx.Speed  ssped ;
    private AirCompass     direction = new AirCompass();
    
    
    //-----------------------Composants Map
    static WorldWindowGLJPanel wwd; 
    public SwingNode swingNode= new SwingNode();
    
    //------------------------Composants JavaFx3D--------------------------
    private final Group root3D = new Group();
    private final Group root = new Group();
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Rotate cameraXRotate = new Rotate(20,0,0,0,Rotate.X_AXIS);
    private final Rotate cameraYRotate = new Rotate(20,0,0,0,Rotate.Y_AXIS);
    private Rotate yUpRotate = new Rotate(0,0,0,0,Rotate.X_AXIS);
    private final Rotate cameraLookXRotate = new Rotate(0,0,0,0,Rotate.X_AXIS);
    private final Rotate cameraLookZRotate = new Rotate(0,0,0,0,Rotate.Z_AXIS);
    //eurocopter
    //private final Translate cameraPosition = new Translate(0,-4,-50);
    private final Translate cameraPosition = new Translate(0,-4,-20);
    private AutoScalingGroup autoScalingGroup = new AutoScalingGroup(2);
    SubScene subSce =new SubScene(root3D, 335, 207,true,  SceneAntialiasing.BALANCED);
    
    public void init3DRendering()
    {
        //subSce.setLayoutX(0);
        //subSce.setLayoutY(50);
        
        /*p.setPrefSize(800, 600);
        p.setLayoutX(100);
        p.setLayoutY(100);
        p.getChildren().add(subSce);
        p.setStyle("-fx-background-color : black");*/
                
        
        pane3D.setStyle("-fx-background-color : black");
        pane3D.getChildren().add(subSce);
        
        //root.getChildren().add(p);
        //final Scene scene = new Scene(root,1366,768,true);
        //scene.setFill(Color.TRANSPARENT);
        //stage.initStyle(StageStyle.TRANSPARENT);
        
        //--Y is UP
        yUpRotate.setAngle(180);

        // CAMERA
        /*camera.getTransforms().addAll(
                cameraXRotate,
                cameraYRotate,
                cameraPosition,
                cameraLookXRotate,
                cameraLookZRotate);*/
        // CAMERA
        camera.getTransforms().addAll(
                //cameraXRotate,
                cameraYRotate,
                yUpRotate,
                cameraPosition);
                //cameraLookXRotate,
                //cameraLookZRotate);
        camera.setNearClip(0.1);
        camera.setFarClip(100);
        subSce.setCamera(camera);
        root3D.getChildren().addAll(camera, autoScalingGroup);

        // LOAD C130J Model
        try 
        {
            Node content;
            //content = Importer3D.load(ControlPanelDesign.class.getResource("eurocopter.obj").toExternalForm());
            content = Importer3D.load(ControlPanelDesign.class.getResource("uh60.obj").toExternalForm());  
            autoScalingGroup.getChildren().add(content);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(cameraYRotate.angleProperty(),0)),
                new KeyFrame(Duration.seconds(4), new KeyValue(cameraYRotate.angleProperty(),360))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);        
        timeline.play();

        Timeline timeline2 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(MFD.layoutXProperty(),10)),
                new KeyFrame(Duration.seconds(4), new KeyValue(MFD.layoutXProperty(),100))
        );
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.setAutoReverse(true);
        //timeline2.play();
        
        //stage.setScene(scene);
        //stage.show();

        // MEASURE FPS
        /*Timeline fpsTimeline = new Timeline(new KeyFrame(Duration.seconds(2), t -> System.out.println("fps = " + PerformanceTracker.getSceneTracker(scene).getInstantFPS())));
        fpsTimeline.setCycleCount(Timeline.INDEFINITE);
        fpsTimeline.play();*/
    }

    public void initEIFIS()
    {
        //--Horizon
        horizon.setPrefSize(260, 260);
        horizon.setLayoutX(230);
        horizon.setLayoutY(40);
        horizon.setPitch(10);
        //horizon.setRoll(25);
        EIFIS.getChildren().add(horizon);
        //--Altimeter
        
        altimeter = new Altimeterfx.Faltimeter();
        altimeter.setLayoutX(500);
        altimeter.setLayoutY(90);
        altimeter.getTransforms().add(new Scale(0.75f, 0.75f, 0, 0));
        EIFIS.getChildren().add(altimeter);
        altimeter.play();
        
       // tachometer
        rpm = new Tachometerfx.Tachometer() ;
        rpm.setLayoutX(48);
        rpm.setLayoutY(87);
        rpm.getTransforms().add(new Scale(0.35f, 0.35f, 0, 0));
        EIFIS.getChildren().add(rpm); 
        rpm.play();
        
        // Speed 
        
      
        ssped = new Speedfx.Speed();
        ssped.setLayoutX(550);
        ssped.setLayoutY(320);
        ssped.getTransforms().add(new Scale(0.35f, 0.35f, 0, 0));
        EIFIS.getChildren().add(ssped);
        ssped.play();
        //--Direction

        
        direction.setPrefSize(260, 260);
        direction.setLayoutX(230);
        direction.setLayoutY(300);
        EIFIS.getChildren().add(direction); 

    }
    
  /*  public void initMAP()
    {
        MAP1.getChildren().add(swingNode);
        SwingUtilities.invokeLater(() -> initializeWorldWind(swingNode)); 
    }*/
    
    public void initializeWorldWind( SwingNode swingNode) 
    { 
        //final WorldWindowGLCanvas wwd = new WorldWindowGLCanvas(); 
        wwd = new WorldWindowGLJPanel();  
        wwd.setPreferredSize(new java.awt.Dimension(620, 540));  
        final JPanel panel = new JPanel();  
        //panel.add(wwd);  
        wwd.setModel(new BasicModel());  
        swingNode.setContent(wwd);     
        System.out.println("msg...="+wwd.getModel().getGlobe().getElevation(Angle.fromDegrees(36.1258),Angle.fromDegrees(36.87144475) ));           
        //    while (true) {System.out.println("lon"+wwd.getCurrentPosition().longitude);} 
    }
  
    
    @FXML
    void e10404(ActionEvent event) {

    }

    @FXML
    void alertClicked(ActionEvent event) 
    {

    }
 
    @FXML
    void d71616(ActionEvent event) {

    }

 
    @FXML
    @ActionMethod("affiche")
    void btn_clock_aff() 
    {
        DigitalClock.Clock clock;
        clock = new DigitalClock.Clock(javafx.scene.paint.Color.LIME, javafx.scene.paint.Color.rgb(50,50,50));
        clock.setLayoutX(20);
        clock.setLayoutY(40);
        clock.getTransforms().add(new Scale(0.35f, 0.35f, 0, 0));
        pane.getChildren().add(clock);
        clock.play();
   
        initEIFIS();
        
      //  initMAP();
        
        init3DRendering();
        
        
    }

    
}
