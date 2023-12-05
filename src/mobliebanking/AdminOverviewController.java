/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class AdminOverviewController implements Initializable {

    @FXML
    private AreaChart<?, ?> lineChart;
    @FXML
    private NumberAxis y_axis;
    @FXML
    private CategoryAxis x_axis;

    @FXML
    private TextField days;
    
     ResultSet rs;
    @FXML
    private AnchorPane form;
    /**
     * Initializes the controller class.
     */
     
     private void chartShow(){
         
         
         //series.getChart().getData();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        XYChart.Series series=new XYChart.Series();
        try {
            dataBaseConn.Connection();
            LocalDate lc=LocalDate.now();
            System.out.println(lc);
            String q="SELECT * FROM `chart_data` WHERE `date`='"+lc+"'";
            Statement s = (Statement) dataBaseConn.dbCon.createStatement();
            rs=s.executeQuery(q);
          
            if(rs.next() == false)
            {
                String qu="INSERT INTO `chart_data`(`date`, `Amount`) VALUES ('"+lc+"','"+MobileBanking.profit+"')";
                s.executeUpdate(qu);
            }
            else
            {
                double d=MobileBanking.profit;
                d+=rs.getDouble("Amount");
                String que="UPDATE `chart_data` SET `Amount`="+d+" WHERE `date`='"+lc+"'";
                s.executeUpdate(que);
            }
            
            String query="SELECT * FROM ( SELECT * FROM chart_data ORDER BY date DESC LIMIT "+(Integer.parseInt(days.getText())+1)+") sub ORDER BY date ASC";
            Statement stmt = (Statement) dataBaseConn.dbCon.createStatement();
            rs= stmt.executeQuery(query);
            System.out.println(rs.next());
            while(rs.next())
            {
                String d= dateFormat.format(rs.getDate("date"));
                series.getData().add(new XYChart.Data<>(d,rs.getDouble("Amount")));
            }
            lineChart.getData().addAll(series);
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chartShow();
    }    

    
    @FXML
    private void daysTextFieldOnAction(ActionEvent event) {
        lineChart.setAnimated(true);
        lineChart.getData().clear();
        lineChart.setAnimated(false);
        chartShow();
    }

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Admin_Interface.fxml"));
          form.getChildren().setAll(pane);
    }
    
}
