/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadmanager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Priority;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 *
 * @author thinkpro
 */
public class FXMLDocumentController implements Initializable, Runnable  {
    
     private String path;
    private ArrayList<File> files;
    private String[] extens;
  
   
    
    @FXML
    private VBox tab;
    

   @FXML
    private TextField input ;
     @FXML
    private TextField input1;
    
    @FXML 
    private AnchorPane anchorid ; 
    
   
   
      @FXML
      @Override
    public  void run()
                {
                    
                    Platform.runLater(() -> {
           try { 

        int BUFFER_SIZE = 4096;
    
         
          
         
        
         URL url = new URL(input.getText());
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = input.getText().substring(input.getText().lastIndexOf("/") + 1,
                        input.getText().length());
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
        String saveFilePath = input1.getText() + File.separator + fileName;
        // creating the Hbox
        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.setAlignment(Pos.CENTER);
        
        Button play = new Button("Play");
        Button pause = new Button("Pause");
        Button delete = new Button("Delete");


        hbox.getChildren().add(play);
        hbox.getChildren().add(pause);
        hbox.getChildren().add(delete);
        hbox.getChildren().add(new Label(fileName));
         JFXProgressBar statusBars= new  JFXProgressBar();
                hbox.getChildren().add(statusBars);

        
           tab.getChildren().add(hbox);
            // opens an output stream to save into file
            
            
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect(); }
     catch ( IOException e  ) {
         System.out.println(e);
     }
    });

                            }
  
    @FXML
    private void directoryChoose(ActionEvent e) {
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
   private void getRepoFiles (){
        path =input1.getText();
        files = new ArrayList<File>();

        File repo = new File (path);

        File[] fileList = repo.listFiles();
        
        
         for (int i = 0 ; i < fileList.length ; i++) {
                    System.out.println(fileList[i]);
}
} 
   
   
 
   
   
   
  
  
    @FXML
   void runit(){
    Thread t = new Thread(this);
    t.start();
   }
 
  
      @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }    

    
   
   
}
