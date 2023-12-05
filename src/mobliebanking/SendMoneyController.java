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
public class SendMoneyController implements Initializable {

    @FXML
    private TextField PhNumField;
    @FXML
    private TextField AmountField;
    
    String PhNo;
    PreparedStatement pst;
    ResultSet rs;
    @FXML
    private AnchorPane anchorPane;
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
        else if(PhNumField.getText().equals(PhNo)){
            Alert alrt = new Alert(Alert.AlertType.INFORMATION);
            alrt.setTitle("Info");
            alrt.setHeaderText("Invalid Request.");
            alrt.setContentText("You can Not send money to your personal Account");
            alrt.showAndWait();
            PhNumField.setText("");
            AmountField.setText("");
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
                    
                    String q="select * from user_information where PhoneNumber=?";
                    pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(q);
                    pst.setString(1,pnumber);
                    rs=pst.executeQuery();
                    if(rs.next())
                        {
                            if(userBal>=amount)
                            {
                                double charge = (amount*1.5)/100;
                                MobileBanking.SendMoneyProfit+=charge;
                                MobileBanking.profit+=charge;
                                double totalBill = (amount+charge);
                                userBal-=totalBill;
                                String que="UPDATE user_information SET Credit=? where PhoneNumber=?";
                                pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                                pst.setDouble(1,userBal);
                                pst.setString(2,PhNo); 
                                pst.executeUpdate();
                                
                                double receiverBal = rs.getDouble("Credit");
                                double receiverSetBal=receiverBal+amount;
                                //String qu="UPDATE user_information SET Credit=? where PhoneNumber=?";
                                pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                                pst.setDouble(1,receiverSetBal);
                                pst.setString(2,pnumber);
                                int i= pst.executeUpdate();
                                if(i==1){
                                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                                    al.setTitle("Info");
                                    al.setHeaderText("Send Successful.");
                                    al.setContentText("Send Amount: "+amount+"\nCharge: "+charge+"\nTotal Bill: "+totalBill);
                                    al.showAndWait();
                                    PhNumField.setText("");
                                    AmountField.setText("");
                                    dataBaseConn.dbCon.close();
                                }
                            }
                            else{
                                Alert a = new Alert(Alert.AlertType.WARNING);
                                a.setTitle("Warning");
                                a.setHeaderText("You have not Sufficient Balance");
                                a.setContentText(null);
                                a.showAndWait();
                            }
                            
                    }else{
                    
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setTitle("Warning");
                        a.setHeaderText("Invalid Number");
                        a.setContentText("User Not Found");
                        a.showAndWait();
                    
                    }
            }
            } catch (SQLException ex) {
                Logger.getLogger(SendMoneyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void backBtnInAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
        anchorPane.getChildren().setAll(pane);
    }
    
}
