/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;


import DatabaseConnection.BDConnector;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class CashInController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField AmountField;

    String PhNo;
    PreparedStatement pst;
    ResultSet rs;
    @FXML
    private ComboBox Bank;
    @FXML
    private JFXTextField ACNoField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PhNo=MobileBanking.phonNum;
        ObservableList <String> list;
        list = FXCollections.observableArrayList("Dutch-Bangla Bank","Sonali Bank","Grameen Bank","Janata Bank","Standard Chartered Bank","Prime Bank Limited","One Bank","Agrani Bank","BRAC Bank");
        Bank.setItems(list);
        
        NumberValidator numValidator=new NumberValidator();
        AmountField.getValidators().add(numValidator);
       numValidator.setMessage("add only amount digits");
       AmountField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(!t1){
                    AmountField.validate();
                    
                }else{
                    AmountField.setText("");
                }   
            }
       });
    }    


    @FXML
    private void SubmitBtnOnAction(ActionEvent event) {
        p:
        if(AmountField.getText().equals("") && ACNoField.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please Enter the Account Number and Amount First");
            alert.setContentText(null);
            alert.showAndWait(); 
        }
        else
        {
            double amount;
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
                if(rs.next()){
                    double userBal = rs.getDouble("Credit");
                    userBal+=amount;
                    String que="UPDATE user_information SET Credit=? where PhoneNumber=?";
                    pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                    pst.setDouble(1,userBal);
                    pst.setString(2,PhNo); 
                    int i= pst.executeUpdate();
                    if(i==1){
                                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                                    al.setTitle("Info");
                                    al.setHeaderText("Successfully Deposited.");
                                    al.setContentText(null);
                                    al.showAndWait();
                                    ACNoField.setText("");
                                    AmountField.setText("");
                                    dataBaseConn.dbCon.close();
                                }
                }
            } catch (SQLException ex) {
                Logger.getLogger(CashInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void backBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
            anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void BankName(ActionEvent event) {
    }

   
    
}
