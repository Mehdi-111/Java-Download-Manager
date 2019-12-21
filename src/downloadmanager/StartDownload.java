/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadmanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author thinkpro
 */
public class StartDownload extends Thread  {
    
    @FXML
    FXMLDocumentController t1 = new FXMLDocumentController();
    
    
    @FXML
    @Override
    public void run() {
        try {
               t1.downloadFile();
        }
        catch (IOException e) {
            System.out.println(e);
        } 
     
        
    }
     FXMLDocumentController controller = new FXMLDocumentController();
        Thread t = new Thread((Runnable) controller);
        
        
        void runit(){
            t.start();
        }
        
       

    
    
    
    
}
