/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadmanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author thinkpro
 */
public class Controller implements Initializable  {
    
      @FXML
      VBox tab;
      @FXML
      private TextField input;
      @FXML
      private TextField input1;
      @FXML  
      private AnchorPane anchorid ; 
    
      FXMLDocumentController t1 = new FXMLDocumentController("","",tab);
   
    
     @FXML
     void setDirectory(){
        t1.directoryChoose(anchorid,input1);
        
     }
    
    
     @FXML 
     void startDownload() throws IOException{
         t1.setVbox(tab);
         t1.setPath(input1.getText());
         t1.setFileUrl(input.getText());
         t1.start();
       // this.tab = t1.getVbox();
      }
    
    

    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
    

        
    }    

    
    
}
