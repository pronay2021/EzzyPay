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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Pronay
 */
public class Profit_dataController implements Initializable {

    @FXML
    private TextField ElectricityText;
    @FXML
    private TextField GasText;
    @FXML
    private TextField WaterText;
    @FXML
    private TextField InternetText;
    @FXML
    private TextField TVText;
    @FXML
    private TextField EducationText;
    @FXML
    private TextField SendMoneyText;
    @FXML
    private TextField FoodText;
    @FXML
    private TextField TotalProfitText;
    
    PreparedStatement pst;
    ResultSet rs;
    @FXML
    private AnchorPane form;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        
        try {
            dataBaseConn.Connection();
            String query="SELECT Amount FROM profit_data WHERE ProfitSector =?";
            pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
            pst.setString(1,"Electricity");
            rs = pst.executeQuery();
          //  System.out.println(rs.next());
            
            if(rs.next())
            {
               ElectricityText.setText(Double.toString(rs.getDouble("Amount")));
            }
            
            
            pst.setString(1,"Gas");
            rs = pst.executeQuery();
          //  System.out.println(rs.next());
            
            if(rs.next())
            {
               GasText.setText(Double.toString(rs.getDouble("Amount")));
            }
            
            pst.setString(1,"Water");
            rs = pst.executeQuery();
          //  System.out.println(rs.next());
            
            if(rs.next())
            {
               WaterText.setText(Double.toString(rs.getDouble("Amount")));
            }
            
            
            pst.setString(1,"Internet");
            rs = pst.executeQuery();
          //  System.out.println(rs.next());
            
            if(rs.next())
            {
              InternetText.setText(Double.toString(rs.getDouble("Amount")));
            }
            
            pst.setString(1,"TV");
            rs = pst.executeQuery();
          //  System.out.println(rs.next());
            
            if(rs.next())
            {
               TVText.setText(Double.toString(rs.getDouble("Amount")));
            }
            
            
            pst.setString(1,"Education");
            rs = pst.executeQuery();
          //  System.out.println(rs.next());
            
            if(rs.next())
            {
               EducationText.setText(Double.toString(rs.getDouble("Amount")));
            }
            
            
            pst.setString(1,"SendMoney");
            rs = pst.executeQuery();
          //  System.out.println(rs.next());
            
            if(rs.next())
            {
               SendMoneyText.setText(Double.toString(rs.getDouble("Amount")));
            }
            
            pst.setString(1,"Food");
            rs = pst.executeQuery();
          //  System.out.println(rs.next());
            
            if(rs.next())
            {
               FoodText.setText(Double.toString(rs.getDouble("Amount")));
            }
            
            String q = "SELECT SUM(Amount) FROM profit_data";
             PreparedStatement pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(q);
           // pst.setString(1,"Electricity");
            ResultSet rs1   = pst1.executeQuery();
            if(rs1.next()){
            TotalProfitText.setText(rs1.getString("SUM(Amount)"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profit_dataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        
        Parent pane = FXMLLoader.load(getClass().getResource("Admin_Interface.fxml"));
            form.getChildren().setAll(pane);
    }
    
}
