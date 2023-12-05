/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class CashOutController implements Initializable {

    @FXML
    private TextField AmountField;
    @FXML
    private AnchorPane form;
    @FXML
    private TextField PhNumField;

    String PhNo;
    PreparedStatement pst;
    ResultSet rs;
    public static Stage stage;
    public static double amount;
    public static double userBal;
    
    @FXML
    private ComboBox Bank;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PhNo=MobileBanking.phonNum;
        
        ObservableList <String> list;
        list = FXCollections.observableArrayList("Dutch-Bangla Bank","Sonali Bank","Grameen Bank","Janata Bank","Standard Chartered Bank","Prime Bank Limited","One Bank","Agrani Bank","BRAC Bank");
        Bank.setItems(list);
    }    

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
            form.getChildren().setAll(pane);
    }

    private void OTPcheck() throws IOException{
        
        
        stage=new Stage();
        Parent root=FXMLLoader.load(getClass().getResource("OTP.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("OTP varification");
        stage.setScene(scene);
        stage.show();        
    }
    
    
    
    
    
    @FXML
    private void sendBtnOnAction(ActionEvent event) throws IOException {
       
        p:
        if(PhNumField.getText().equals("") || AmountField.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please Enter both Phone Number and Amount Field First");
            alert.setContentText(null);
            alert.showAndWait();
        }
        else
        {
            String pnumber=PhNumField.getText();
            amount=0;
            try{
                amount= Double.parseDouble(AmountField.getText());
            }catch(NumberFormatException e){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Warning");
                a.setHeaderText("Enter Valid Amount");
                a.setContentText(null);
                a.showAndWait();
                AmountField.setText("");
                break p;
            }
        
            BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
            try {
                dataBaseConn.Connection();
                String query="select * from user_information where PhoneNumber=?";
                pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
                pst.setString(1,PhNo); 
                rs=pst.executeQuery();
                if(rs.next())
                {
                    userBal = rs.getDouble("Credit");
                    
                    
                    if(userBal>=amount)
                    {
                        OTPcheck();
                    }
                    else
                    {
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setTitle("Warning");
                        a.setHeaderText("You have not Sufficient Balance");
                        a.setContentText(null);
                        a.showAndWait();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(SendMoneyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void BankName(ActionEvent event) {
    }
    
}
