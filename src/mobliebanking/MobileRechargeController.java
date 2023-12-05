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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class MobileRechargeController implements Initializable {

    @FXML
    private TextField PhNumField;
    @FXML
    private TextField AmountField;
    @FXML
    private AnchorPane form;

    String PhNo;
    PreparedStatement pst;
    ResultSet rs;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PhNo=MobileBanking.phonNum;
    }    

    @FXML
    private void sendBtnOnAction(ActionEvent event) {
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
            double amount=0;
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
                    double userBal = rs.getDouble("Credit");
                    if(userBal>=amount)
                    {
                        userBal-=amount;
                        String que="UPDATE user_information SET Credit=? where PhoneNumber=?";
                        pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                        pst.setDouble(1,userBal);
                        pst.setString(2,PhNo); 
                       int i= pst.executeUpdate();
                        if(i==1){
                                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                                    al.setTitle("Info");
                                    al.setHeaderText("Send Successful.");
                                    al.setContentText(null);
                                    al.showAndWait();
                                    PhNumField.setText("");
                                    AmountField.setText("");
                                    dataBaseConn.dbCon.close();
                                }
                        
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
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
            form.getChildren().setAll(pane);
    }
    
}
