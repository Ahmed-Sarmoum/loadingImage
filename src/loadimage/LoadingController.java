/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadimage;

import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Hamada
 */
public class LoadingController implements Initializable {

    public static String path = "path"; //Your Path file folder
    String fileName;
    @FXML
    private Text second;
    @FXML
    private Text noImage;
    @FXML
    private JFXTextField text;
    @FXML
    private ImageView image;
    ObservableList<String> t = FXCollections.observableArrayList();
    Notifications notification;
    
    
    @FXML
    void getText(ActionEvent event) throws FileNotFoundException, IOException {
        File file = new File(path);
  
            
            
            long startTime = System.nanoTime();
                listFilesForFolder(file);
            long endTime = System.nanoTime();

            double duration = (endTime - startTime);
            second.setText("Time : "+duration/1000000000+" second");

    }
    
    public String loadData(String path) {
        String s = FilenameUtils.getExtension(path+path);
        return s;
    }
    
    public void listFilesForFolder(final File folder)  {
     
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            String s = loadData(fileEntry.getName());
            if(s.equals("txt"))
                try {
                    if(!searchInFile(text.getText(),fileEntry.getName()).equals("no")){ 
                        noImage.setText("");
                        String v = FilenameUtils.removeExtension(searchInFile(text.getText(),fileEntry.getName())); 
                        Image i = new Image("img/"+v+".jpg");
                        image.setImage(i); 
                        break;
                    } else {
                            Image i = new Image("img/q.jpg");
                            image.setImage(i);
                            noImage.setText("No Image");
                    }
            } catch (IOException ex) {
            }
            
        }
        
       
    }
   
    
  System.err.println(fileName);
}

    public String searchInFile(String searchW,String t) throws FileNotFoundException, IOException {
        
         File f1=new File(path+"/"+t); //Creation of File Descriptor for input file
                String[] words=null;  //Intialize the word Array
                FileReader fr = new FileReader(f1);  //Creation of File Reader object
                BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
                String s;     
                String input=searchW;   // Input word to be searched
                int count=0;   //Intialize the word to zero
                while((s=br.readLine())!=null)   //Reading Content from the file
                {
                   words=s.split("\n");  //Split the word using space
                    for (String word : words) 
                    {
                           if (word.equals(input))   //Search for the given word
                           {
                             count++;    //If Present increase the count by one
                           }
                    }
                }

         fr.close();
         if(count == 1 ){
             return t;
         }else{
            return "no"; 
         }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
