/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class UserInterfaceController implements Initializable {

    List<String>listFile;
    @FXML
    private ImageView sendMoney;
    @FXML
    private ImageView mobileRecharge;
    @FXML
    private ImageView payBill;
    @FXML
    private ImageView cashIn;
    @FXML
    private ImageView userPic;
    @FXML
    private TextField creditField;
    @FXML
    private JFXButton logOutBn;
    @FXML
    private JFXButton RemoveACBtn;
    @FXML
    private JFXTextField userNameField;
    @FXML
    private AnchorPane anchorPane;
    
    private Image img;
    PreparedStatement pst;
    ResultSet rs;
    String pnum;
    public static String mail="hasanjobairul@gmail.com";
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listFile = new ArrayList<>();
        listFile.add("*.jpeg");
        listFile.add("*.png");
        listFile.add("*.jpg");
        
        pnum= MobileBanking.phonNum;
        
        BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        
        try {
            dataBaseConn.Connection();
        
            String query="select UserName , Credit , Image , Email from user_information where PhoneNumber=?";
            pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
            pst.setString(1,pnum);
            rs=pst.executeQuery();
            if(rs.next())
            {
                 // mail=rs.getString("Email");
                  userNameField.setText(rs.getString("UserName"));
                  creditField.setText(rs.getString("Credit"));
                  String filename =  rs.getString("Image");
                  img=new Image(new FileInputStream(filename));
                  userPic.setImage(img);
                  dataBaseConn.dbCon.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    

    @FXML
    private void userPicOnActionMouse(MouseEvent event) {
        FileChooser fc= new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Image Files",listFile));
        File f=fc.showOpenDialog(null);
        
        String filename =f.getAbsolutePath();
        if(f!=null){
            img=new Image(f.toURI().toString());
            userPic.setImage(img);
            
            BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
            filename=filename.replace("\\", "\\\\");
            try {
                dataBaseConn.Connection();
                String query="UPDATE `user_information` SET `Image`='"+filename+"' WHERE `PhoneNumber`='"+pnum+"'";
                pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
                pst.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @FXML
    private void logOutBnOnAtion(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
            anchorPane.getChildren().setAll(pane);

    }

    @FXML
    private void RemoveACBtnOnAction(ActionEvent event) throws IOException {
         BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        try {
            dataBaseConn.Connection();
            
            Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Remove Account");
                    a.setHeaderText("Really Want to Remove Your Account?");
                    a.setContentText("Please don't do that.You will be missed");
                    
                    ButtonType btnYes=new ButtonType("Yes");
                    ButtonType btnCancel=new ButtonType("Cancel");
                    a.getButtonTypes().setAll(btnYes,btnCancel);
                    Optional<ButtonType> res=a.showAndWait();
                    if(res.get() == btnYes){
                        
                       String query="DELETE FROM user_information WHERE PhoneNumber=?";
                       pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
                       pst.setString(1,pnum);
                       int i=pst.executeUpdate();
                       if(i==1){
                           
                            Parent pane = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                            anchorPane.getChildren().setAll(pane);
                            dataBaseConn.dbCon.close();
                       }
                        
                    }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void sendMoneyOnAction(MouseEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("sendMoney.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void mobileRechargeOnAction(MouseEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("MobileRecharge.fxml"));
        anchorPane.getChildren().setAll(pane);
    }
 
    @FXML
    private void payBillOnAction(MouseEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("BillPay.fxml"));
        anchorPane.getChildren().setAll(pane);
    }
    @FXML
    private void cashInOnAction(MouseEvent event) throws IOException {
         Parent pane = FXMLLoader.load(getClass().getResource("CashIn.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void foodOnAction(MouseEvent event) throws IOException {
         Parent pane = FXMLLoader.load(getClass().getResource("Food.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void cashOutOnAction(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information");
        alert.setHeaderText("You need Internet Connection and Email access for doing Cash-Out Operation.");
        ButtonType btnYes=new ButtonType("Yes");
                    ButtonType btnCancel=new ButtonType("Cancel");
                    alert.getButtonTypes().setAll(btnYes,btnCancel);
                    Optional<ButtonType> res=alert.showAndWait();
                    if(res.get() == btnYes){
                        Parent pane = FXMLLoader.load(getClass().getResource("cashOut.fxml"));
                        anchorPane.getChildren().setAll(pane);
                    }
        
    }
}
