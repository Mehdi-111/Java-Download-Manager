/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadmanager;

import com.jfoenix.controls.JFXProgressBar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author thinkpro
 */
public class Controller implements Initializable  {
    
      @FXML
      private VBox tab;
      @FXML
      private TextField input;
      @FXML
      private TextField input1;
      @FXML  
      private AnchorPane anchorid ; 
    
   
    
      
      
      
     @FXML
     void setDirectory(){
         Stage stage = (Stage) anchorid.getScene().getWindow();
        final DirectoryChooser dirchoose = new DirectoryChooser();
        
        
           File file = dirchoose.showDialog(stage);
                 if(file != null) {
                   System.out.println(file.getAbsolutePath());
                   input1.setText(file.getAbsolutePath());
                  
                   
                                   }
                 else {
                      System.out.println("You failed !");
                 }
                
               
     }
    
    
     @FXML 
     void startDownload() throws IOException{
         
               
                
                FXMLDocumentController t1 = new FXMLDocumentController("","","");
                t1.setPath(input1.getText());
                t1.setFileUrl(input.getText());
                t1.start();
                // this.tab = t1.getVbox();
                HBox hbox = new HBox();
                hbox.setSpacing(20);
                hbox.setAlignment(Pos.CENTER);
        
                Button play = new Button("Play");
                Button pause = new Button("Pause");
                Button delete = new Button("Delete");


                hbox.getChildren().add(play);
                hbox.getChildren().add(pause);
                hbox.getChildren().add(delete);
                hbox.getChildren().add(new Label(t1.fileName));
                JFXProgressBar statusBars= new  JFXProgressBar();
                hbox.getChildren().add(statusBars);



                tab.getChildren().add(hbox);
      }
    
    

    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
    

        
    }    

    
    
}
