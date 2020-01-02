/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadmanager;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;

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
      private JFXTextField fileNameInput;
      @FXML  
      private AnchorPane anchorid ; 
      @FXML  
      private JFXTimePicker timePicker ; 
      @FXML
      int progress = 0;
    
      Date date = new Date();
      int diff;
    
       
      
      
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
       void showTime(){
            Calendar calendar = Calendar.getInstance(); // gets current instance of the calendar
            SimpleDateFormat formatter = new SimpleDateFormat("HH");
            SimpleDateFormat formatter2 = new SimpleDateFormat("mm");
          
          //  System.out.println(Integer.valueOf(formatter.format(calendar.getTime())));
        //    System.out.println(Integer.valueOf(formatter2.format(calendar.getTime())));
          
          //  System.out.println( timePicker.getValue().getHour() );
           //  System.out.println( timePicker.getValue().getMinute());
             
             int pickerMinute = timePicker.getValue().getMinute();
             int pickerHour = timePicker.getValue().getHour();
             int minute = Integer.valueOf(formatter2.format(calendar.getTime()));
             int hour =  Integer.valueOf(formatter.format(calendar.getTime()));
             
             int diff  = (pickerHour - hour)*60 +Math.abs(pickerMinute-minute);
             this.diff = diff;
             
     System.out.println("Le delai est de " + diff);
      }
       
       
    
       @FXML
       void startAfter() {
      
           Timer timer = new java.util.Timer();
 
timer.schedule(new TimerTask() {
    public void run() {
         Platform.runLater(new Runnable() {
            public void run() {
                try {
                                                     startDownload();
                                                }
                                                catch(IOException e) {
                                                    System.out.println("Theres an error here");
                                                }
            }
        });
    }
}, this.diff*60000 );
    

    
   
        
    
           
         
               
   
                
        
       }
    
     @FXML 
     void startDownload() throws IOException{
         
         
           
                
                FXMLDocumentController t1 = new FXMLDocumentController("","");
               if (t1.cpt > 3 ) {
                   System.out.println("C'est complet sahbi");
                   return;
               }
                t1.setPath(input1.getText());
                t1.setFileUrl(input.getText());
                 Thread t = new Thread(t1);
                t.start();
               
                
                // this.tab = t1.getVbox();
                HBox hbox = new HBox();
                hbox.setSpacing(20);
                hbox.setAlignment(Pos.CENTER);
                
        
                Button play = new Button("Resume");
                play.setOnAction(new EventHandler<ActionEvent>() {
                    
                             @Override synchronized public void handle(ActionEvent e)     {
                             if (t1.etat != "FINISHED") {
                                     t1.resume();
                                     t1.etat2.set("Downloading");
                             }
                              
                              
                     
                                Thread t = new Thread(t1);
           
                    
                                  t.start();
                                      System.out.println(t1.cpt + " Est  reprend ");
                                 
                           
                           
                          }
                      });
                Button pause = new Button("Pause");
                pause.setOnAction(new EventHandler<ActionEvent>() {
                    
                             @Override synchronized public void  handle(ActionEvent e)     {
                                       t1.pause();
                                       t1.etat2.set("Paused");
                                       Thread t2 = new Thread(t1);
                                       t2.start();
                                      
                              
                             
                              
                              
                           
                              
                          }
                      });
                Button delete = new Button("Delete");
                 delete.setOnAction(new EventHandler<ActionEvent>() {
                    
                             @Override synchronized public void  handle(ActionEvent e)     {
                                      tab.getChildren().remove(hbox);
                                     t.stop();
                              
                           
                              
                          }
                      });
                
                    String FileName = fileNameInput.getText();
               
  

                    hbox.getChildren().add(play);
                    hbox.getChildren().add(pause);
                    hbox.getChildren().add(delete);
                    Label label=  new Label();
                   
               //     label.textProperty().bind(t1.getFileName());
                    hbox.getChildren().add(label);
                    ProgressBar statusBars= new ProgressBar();
                    hbox.getChildren().add(statusBars);
                    statusBars.progressProperty().bind(t1.progress);
                    Label label2=  new Label("Downloading");
                    label2.textProperty().bind(t1.etat2);
           
               

                    hbox.getChildren().add(label2);
                    


               
                
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

    private boolean typeof(String format) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
