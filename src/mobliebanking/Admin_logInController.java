/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */

public class Admin_logInController implements Initializable {

    @FXML
    private JFXTextField Pnum;
    @FXML
    private JFXPasswordField Ppass;
    @FXML
    private AnchorPane form;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logInBtnOnAction(ActionEvent event) throws IOException {
        
        String pnum=Pnum.getText();
       String pass=Ppass.getText(); 
       
       
       
        if(pnum.equals("")&&pass.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Number is not valid");
            alert.setContentText(null);
            alert.showAndWait();
        }
        else
        {

            try {
                BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
                dataBaseConn.Connection();
                
                
                String query="select * from info_admin where PhoneNumber=? and Password=?";
                PreparedStatement pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
                pst.setString(1,pnum);
                pst.setString(2, pass);  
                ResultSet rs = pst.executeQuery();
                if(rs.next())
                {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Login Successful.");
                alert.showAndWait();
                MobileBanking.phonNum=pnum;
                
                Parent pane = FXMLLoader.load(getClass().getResource("Admin_Interface.fxml"));
                form.getChildren().setAll(pane);
                dataBaseConn.dbCon.close();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Phone number or id Mismatch");
                    alert.setContentText("Please Enter Again.");
                    alert.showAndWait();
                    Pnum.setText("");
                    Ppass.setText("");
                }
                
               
            } catch (SQLException ex) {
               Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        }
    }

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        form.getChildren().setAll(pane);
    }

    
}
