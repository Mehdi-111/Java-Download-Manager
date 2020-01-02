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
import java.util.Observable;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 *
 * @author thinkpro
 */
public class FXMLDocumentController extends Observable implements Runnable     {
    
                    private String path;
                    private ArrayList<File> files;
                    private String fileUrl;
                    StringProperty fileName;
                    String etat = "DOWNLOADING";
                    StringProperty etat2 ;
                    float downloaded;
                    DoubleProperty progress;
                    public static int cpt = 0;
            
                 

           
           
                        //constructor
                      FXMLDocumentController( String fileUrl ,String path) {
                          this.fileUrl = fileUrl;
                          this.path=path;
                          this.etat2= new SimpleStringProperty("Downloading");
                          progress = new SimpleDoubleProperty(0);
                          cpt++;
                       
                      }
   
           

                        public void setFileUrl(String fileUrl) {

                               this.fileUrl=fileUrl;

                           }

           
           
           
                    void setPath(String path) {

                           this.path= path;           

                                    }
                    String getFileUrl(){

                        return fileUrl;
                    }
            
          
            
            
            
     void downloadFile(String fileUrl, String path )
            throws IOException {
        String state= "DOWNLOADING";

        int BUFFER_SIZE = 4096;
        URL url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      

       // httpConn.setRequestProperty("Range",
            //                 "bytes=" + this.downloaded + "-");
    //    httpConn.connect();
     //   int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
     
            
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
            String byteRange = this.downloaded + "-" + contentLength;
	//    httpConn.setRequestProperty("Range", "bytes=" + byteRange);
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                  fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                
                }
            } else {
                // extracts file name from URL
               fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1,
                        fileUrl.length());
                 
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
            
          
 
            
            // opens input stream from the HTTP connection
             
                    InputStream inputStream = httpConn.getInputStream();
                    String saveFilePath =path + File.separator + fileName;
       
                    
                         // opens an output stream to save into file
            
            
                        FileOutputStream outputStream = new FileOutputStream(saveFilePath);
                        int bytesRead = -1;
                        byte[] buffer = new byte[BUFFER_SIZE];
                      
                        
                        
                        if (this.etat =="PAUSED") {
                            //    System.out.println("You paused it man !");
        }
                        
                        
                        while ((bytesRead = inputStream.read(buffer)) != -1 && this.etat== "DOWNLOADING") {
                            
                            
                            outputStream.write(buffer, 0, (int) bytesRead);
                                      this.downloaded += bytesRead;
                                  //    System.out.println(this.downloaded);
                                      
                                    
                                          this.progress.set((double)((downloaded / contentLength) *100));
                                        //  String aux = String.valueOf((downloaded / contentLength) *100); 
                             

                                    
                                   
                                       
                                      
                        }
                

                                outputStream.close();
                                inputStream.close();
                  if (etat == "DOWNLOADING") {
                      this.etat="FINISHED";
                     this.etat2.set("Finished");
                System.out.println("File downloaded"); }
           
            //   System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            
            httpConn.disconnect();
        }

   
  
  
     String directoryChoose(AnchorPane anchorid,TextField input1) {
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
                 return file.getAbsolutePath();
               
    }
   
   private void getRepoFiles (){
        path =this.path;
        files = new ArrayList<File>();

        File repo = new File (path);

        File[] fileList = repo.listFiles();
        
        
         for (int i = 0 ; i < fileList.length ; i++) {
                    System.out.println(fileList[i]);
}
} 
  /*public FloatProperty getProgress() {
      
      
   if (this.progress==null) {
       return new SimpleFloatProperty(0);
       
   }
  return this.progress;
  }
   
    */
   
 
  
   
   
   public void pause() {
       this.etat="PAUSED";
       this.stateChanged();
   }
    public void resume() {
       this.etat="DOWNLOADING";
       this.stateChanged();
   }
     @Override
     public void run() {
     try {
         this.downloadFile(this.fileUrl,this.path);
     }
     catch(IOException e) {
         System.out.println(e);
         
     }
   }
  
        protected void stateChanged() {
		setChanged();
		notifyObservers();
	}
	
      
   
}
