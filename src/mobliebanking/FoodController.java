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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class FoodController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox Burger;
    @FXML
    private ComboBox Pasta;
    @FXML
    private ComboBox Pizza;
    @FXML
    private ComboBox Coffee;
    @FXML
    private ComboBox Pastry;
    @FXML
    private ComboBox Soup;
    @FXML
    private Button Total;
    
     @FXML
    private TextField bill;
     
     String PhNo;
    PreparedStatement pst;
    ResultSet rs;
    public int b;
    /**
     * Initializes the controller class.
     */
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        PhNo=MobileBanking.phonNum;
        
        ObservableList <String> list;
        list = FXCollections.observableArrayList("0","1","2","3","4","5");
        Burger.setItems(list);
        
        list = FXCollections.observableArrayList("0","1","2","3","4","5");
        Pasta.setItems(list);
      
        list = FXCollections.observableArrayList("0","1","2","3","4","5");
        Pizza.setItems(list);
        
        list = FXCollections.observableArrayList("0","1","2","3","4","5");
       Coffee.setItems(list);
        
        list = FXCollections.observableArrayList("0","1","2","3","4","5");
       Pastry.setItems(list);
        
        list = FXCollections.observableArrayList("0","1","2","3","4","5");
        Soup.setItems(list);
        
        
        
        
    }        


    @FXML
    private void TotalBill(ActionEvent event) {
        
        int a=0,c=0,d=0,e=0,f=0,g=0,h=0,i=0,j=0,k=0,l=0;
        
        if(!(Burger.getValue().equals(null)))
            a=Integer.parseInt(Burger.getValue().toString());
        if(!(Pasta.getValue().equals(null)))
            c=Integer.parseInt(Pasta.getValue().toString());
        if(!(Pizza.getValue().equals(null)))
            e=Integer.parseInt(Pizza.getValue().toString());
        if(!(Coffee.getValue().equals(null)))
            g=Integer.parseInt(Coffee.getValue().toString());
        if(!(Pastry.getValue().equals(null)))
            i=Integer.parseInt(Pastry.getValue().toString());
        if(!(Soup.getValue().equals(null)))
            k=Integer.parseInt(Soup.getValue().toString());
        
        b=(a*300)+(c*180)+(e*515)+(g*150)+(80*i)+(k*110);
         MobileBanking.FoodProfit+=(b*10)/100;
         MobileBanking.profit+=(b*10)/100;
        bill.setText(Integer.toString(b));
        
        /*b=180*c;
        bill.setText(Integer.toString(b));
        
       
        b=515*e;
        bill.setText(Integer.toString(b));
        
        
        b=150*g;
        bill.setText(Integer.toString(b));
        
        
        b=80*i;
        bill.setText(Integer.toString(b));
        
        
        b=110*k;
        bill.setText(Integer.toString(b));*/
        
    }
    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void PayBillOnAction(ActionEvent event) {
        
        if(b>0)
        {
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
                    if(userBal>=b)
                    {
                        userBal-=b;
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
                                    bill.setText("");
                                    Burger.setSelectionModel(null);
                                    Pasta.setSelectionModel(null);
                                    Pizza.setSelectionModel(null);
                                    Coffee.setSelectionModel(null);
                                    Pastry.setSelectionModel(null);
                                    Soup.setSelectionModel(null);

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
}
    
    

