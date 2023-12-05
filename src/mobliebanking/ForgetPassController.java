/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import java.sql.ResultSet;
import DatabaseConnection.BDConnector;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
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
 * @author FUJITSU
 */
public class ForgetPassController implements Initializable {

    @FXML
    private TextField Mail;
    @FXML
    private TextField Color;
    @FXML
    private Button VerifyBtn;
    @FXML
    private AnchorPane form;

    public static Stage stage = new Stage();
    public static String mail;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Mail(ActionEvent event) {
    }

    @FXML
    private void Color(ActionEvent event) {
    }

    @FXML
    private void VerifyBtnOnAction(ActionEvent event) throws IOException {
        String pnum=/*Mail.getText()*/"hasanjobairul@gmail.com";
       String pass=Color.getText(); 
       
       
       
        if(pnum.equals("")&&pass.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Mail is not valid");
            alert.setContentText(null);
            alert.showAndWait();
        }
        else
        {

            try {
                BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
                dataBaseConn.Connection();
                
                
                String query="select * from user_information where Email=? and SecurityQues=?";
                PreparedStatement pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
                pst.setString(1,pnum);
                pst.setString(2, pass);  
                ResultSet rs = pst.executeQuery();
                if(rs.next())
                {
                MobileBanking.phonNum=rs.getString("PhoneNumber");
                mail=pnum;
                Parent root = FXMLLoader.load(getClass().getResource("UpdatePass.fxml"));
                Scene scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
                dataBaseConn.dbCon.close();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Email or Info Mismatch");
                    alert.setContentText("Please Enter Again.");
                    alert.showAndWait();
                    Mail.setText("");
                    Color.setText("");
                }
                
               
            } catch (SQLException ex) {
               Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        }
    }

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
           form.getChildren().setAll(pane);
    }
    
}
  
