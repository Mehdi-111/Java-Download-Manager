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
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableFloatValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
public class Controller extends Observable implements Initializable  {
    
      @FXML
      private VBox tab;
      @FXML
      private TextField input;
      @FXML
      private TextField input1;
      @FXML  
      private AnchorPane anchorid ; 
    
       @FXML
       int progress = 0;
    
      
      
      
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
                 Thread t = new Thread(t1);
                t.start();
               
                
                // this.tab = t1.getVbox();
                HBox hbox = new HBox();
                hbox.setSpacing(20);
                hbox.setAlignment(Pos.CENTER);
        
                Button play = new Button("Play");
                play.setOnAction(new EventHandler<ActionEvent>() {
                    
                             @Override synchronized public void handle(ActionEvent e)     {
                             
                                  t1.resume();
                              
                     
                                Thread t = new Thread(t1);
           
                    
                                  t.start();
                                 
                           
                           
                          }
                      });
                Button pause = new Button("Pause");
                pause.setOnAction(new EventHandler<ActionEvent>() {
                    
                             @Override synchronized public void  handle(ActionEvent e)     {
                                       t1.pause();
                            
                                      t.stop();
                            
                                
                                 
                                  
                                         t1.run();
                              
                             
                              
                              
                           
                              
                          }
                      });
                Button delete = new Button("Delete");
                 delete.setOnAction(new EventHandler<ActionEvent>() {
                    
                             @Override synchronized public void  handle(ActionEvent e)     {
                                      System.out.println(t1.progress);
                              
                           
                              
                          }
                      });
                
  

                hbox.getChildren().add(play);
                hbox.getChildren().add(pause);
                hbox.getChildren().add(delete);
                Label label=  new Label("");
           //     label.textProperty().bind(t1.getFileName());
                hbox.getChildren().add(label);
                 ProgressBar statusBars= new ProgressBar();
                  hbox.getChildren().add(statusBars);
                statusBars.progressProperty().bind(new SimpleIntegerProperty(t1.getProgress()));
                
             //   statusBars.progressProperty().bind(t1.getProgress());
             //   statusBars.progressProperty().bind(t1.getProgress());
              //  statusBars.indeterminateProperty()
              
               



                tab.getChildren().add(hbox);
      }
    
      protected void stateChanged() {
		setChanged();
		notifyObservers();
	}

    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
    
                      

        
    }    

    
    
}
