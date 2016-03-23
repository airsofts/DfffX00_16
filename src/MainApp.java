

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author amine
 */
import io.datafx.controller.flow.Flow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
      //To change body of generated methods, choose Tools | Templates.
       
        new Flow(ControlPanelDesign.class)
                . startInStage(primaryStage);
       
    }
    
     public static void main(String... args) {
        launch(args);
    }

    
}
