/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class TVController implements Initializable {

    @FXML
    private ComboBox TV;
    @FXML
    private AnchorPane form;
    @FXML
    private TextField SubIdTextF;
    @FXML
    private TextField ContactNoTextF;
    @FXML
    private TextField AmountTextF;
    PreparedStatement pst;
    ResultSet rs;
    String PhNo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         PhNo=MobileBanking.phonNum;
       ObservableList <String> list;
        list = FXCollections.observableArrayList("AKash DTH","Rabbitholebd","Bumbellbee","Jashore City Cable");
        TV.setItems(list);
        // TODO
    }    

    private void tvBill(ActionEvent event) {
         String tv = (String) TV.getValue();
    }

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("BillPay.fxml"));
            form.getChildren().setAll(pane);
        
    }

    @FXML
    private void SendBtnOnAction(ActionEvent event) {
        
        if(SubIdTextF.getText().equals("") ||ContactNoTextF .getText().equals("")||AmountTextF .getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please enter all fields first");
            alert.setContentText(null);
            alert.showAndWait();
        }
          else
        {
            
            double amount= Double.parseDouble(AmountTextF.getText());
        
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
                                double charge = ((amount*1.5)/100)+10;
                                MobileBanking.TVprofit+=charge;
                                MobileBanking.profit+=charge;
                                double totalBill = (amount+charge);
                                userBal-=totalBill;
                                String que="UPDATE user_information SET Credit=? where PhoneNumber=?";
                                pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                                pst.setDouble(1,userBal);
                                pst.setString(2,PhNo); 
                               int i= pst.executeUpdate();
                                
                                if(i==1){
                                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                                    al.setTitle("Info");
                                    al.setHeaderText("Send Successful.");
                                    al.setContentText("Send Amount: "+amount+"\nCharge: "+charge+"\nTotal Bill: "+totalBill);
                                    al.showAndWait();
                                     SubIdTextF.setText("");
                                    ContactNoTextF.setText("");
                                    AmountTextF.setText("");
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
                            
            }
            } catch (SQLException ex) {
                Logger.getLogger(ElectricityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void S(ActionEvent event) {
    }

    
    
}
