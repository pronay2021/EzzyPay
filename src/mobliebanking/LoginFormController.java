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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class LoginFormController implements Initializable {

    @FXML
    private TextField PnumTextField;
    @FXML
    private Button logInBtn;
    @FXML
    private PasswordField passTextField;
    @FXML
    private AnchorPane form;
    
    PreparedStatement pst;
    ResultSet rs;
    @FXML
    private Label ForgotPass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    private void setString(int i, String mesg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
    @FXML
    private void logInBtnOnAction(ActionEvent event) throws IOException {
       String pnum=PnumTextField.getText();
       String pass=passTextField.getText(); 
       
       
       
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
                
                
                String query="select * from user_information where PhoneNumber=? and Password=?";
                pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
                pst.setString(1,pnum);
                pst.setString(2, pass);  
                rs=pst.executeQuery();
                if(rs.next())
                {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Login Successful.");
                alert.showAndWait();
                MobileBanking.phonNum=pnum;
                
                Parent root = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
                form.getChildren().setAll(root);
//                Scene scene=new Scene(root);
//                Stage stage = new Stage();
//                stage.setScene(scene);
//                stage.show();
//                MobileBanking.stage.close();
                dataBaseConn.dbCon.close();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Phone number or Password Mismatch");
                    alert.setContentText("Please Enter Again.");
                    alert.showAndWait();
                    PnumTextField.setText("");
                    passTextField.setText("");
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

    @FXML
    private void ForgotPassOnAction(MouseEvent event) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("ForgetPass.fxml"));
            form.getChildren().setAll(pane);

        } catch (IOException ex) {
            Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

