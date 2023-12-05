/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import com.mysql.jdbc.Statement;
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
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class PieChartController implements Initializable {

    @FXML
    private PieChart pieChart;
    
    ResultSet rs;
    PreparedStatement pst;
    PreparedStatement pst1;
    ObservableList<PieChart.Data> pc;
    @FXML
    private AnchorPane form;
    /**
     * Initializes the controller class.
     */
    
    private void updateData(){
        BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        double val;
        try {
            dataBaseConn.Connection();
            String query="SELECT `Amount` FROM `profit_data` WHERE `ProfitSector`=?";
            pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
            
            pst.setString(1, "Electricity");
            rs=pst.executeQuery();
            System.out.println(rs.next());
              val= MobileBanking.Electricityprofit + rs.getDouble("Amount"); 
                String que="UPDATE profit_data SET Amount=? where ProfitSector=?";
                pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                pst1.setDouble(1, val);
                pst1.setString(2, "Electricity");
                pst1.executeUpdate();
                
                
          pst.setString(1, "Gas");
            rs=pst.executeQuery();
            System.out.println(rs.next());
            val= MobileBanking.Gasprofit + rs.getDouble("Amount"); 
                //String que="UPDATE profit_data SET Amount=? where ProfitSector=?";
                pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                pst1.setDouble(1, val);
                pst1.setString(2, "Gas");
                pst1.executeUpdate();
                
            pst.setString(1, "Water");
            rs=pst.executeQuery();
            System.out.println(rs.next());
            val= MobileBanking.Waterprofit + rs.getDouble("Amount"); 
                //String que="UPDATE profit_data SET Amount=? where ProfitSector=?";
                pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                pst1.setDouble(1, val);
                pst1.setString(2, "Water");
                pst1.executeUpdate();
                
                 pst.setString(1, "TV");
            rs=pst.executeQuery();
            System.out.println(rs.next());
            val= MobileBanking.TVprofit + rs.getDouble("Amount"); 
                //String que="UPDATE profit_data SET Amount=? where ProfitSector=?";
                pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                pst1.setDouble(1, val);
                pst1.setString(2, "TV");
                pst1.executeUpdate();
                
                
                 pst.setString(1, "Internet");
            rs=pst.executeQuery();
            System.out.println(rs.next());
            val= MobileBanking.Internetprofit + rs.getDouble("Amount"); 
                //String que="UPDATE profit_data SET Amount=? where ProfitSector=?";
                pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                pst1.setDouble(1, val);
                pst1.setString(2, "Internet");
                pst1.executeUpdate();
                
                 pst.setString(1, "Education");
            rs=pst.executeQuery();
            System.out.println(rs.next());
            val= MobileBanking.Educationprofit + rs.getDouble("Amount"); 
                //String que="UPDATE profit_data SET Amount=? where ProfitSector=?";
                pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                pst1.setDouble(1, val);
                pst1.setString(2, "Education");
                pst1.executeUpdate();
                
                 pst.setString(1, "SendMoney");
            rs=pst.executeQuery();
            System.out.println(rs.next());
            val= MobileBanking.SendMoneyProfit + rs.getDouble("Amount"); 
                //String que="UPDATE profit_data SET Amount=? where ProfitSector=?";
                pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                pst1.setDouble(1, val);
                pst1.setString(2, "SendMoney");
                pst1.executeUpdate();
                
                 pst.setString(1, "Food");
            rs=pst.executeQuery();
            System.out.println(rs.next());
            val= MobileBanking.FoodProfit + rs.getDouble("Amount"); 
                //String que="UPDATE profit_data SET Amount=? where ProfitSector=?";
                pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
                pst1.setDouble(1, val);
                pst1.setString(2, "Food");
                pst1.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PieChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void loadData(){
        BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        
        try {
             pc= FXCollections.observableArrayList();
            dataBaseConn.Connection();
            String query="SELECT * FROM `profit_data` WHERE 1";
            Statement s = (Statement) dataBaseConn.dbCon.createStatement();
            rs=s.executeQuery(query);
            
            
            while(rs.next())
            {
                
               pc.add(new PieChart.Data(rs.getString("ProfitSector"),rs.getDouble("Amount")));
              
                  
            } 
        } catch (SQLException ex) {
            Logger.getLogger(PieChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateData();
        loadData();
        pieChart.setData(pc);
        
        
    }    

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Admin_Interface.fxml"));
        form.getChildren().setAll(pane);
    }
    
}
