/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

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
public class OTPController implements Initializable {

    

    @FXML
    private JFXTextField OTPField;

    String PhNo;
    int OTP;
    double amount=CashOutController.amount;
    double userBal=CashOutController.userBal;
    PreparedStatement pst;
    ResultSet rs;
    //public static boolean check;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PhNo=MobileBanking.phonNum;
        
        Random rand= new Random();
        OTP=rand.nextInt(999999);
//        System.out.println(OTP);
        sendMail(UserInterfaceController.mail,OTP);
        
        
    } 
    
    private static Message prepareMessage(Session session, String ac,String recepient,int otp) {
        System.out.println("prepairing");
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(ac));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("OTP Checking");
            message.setText("Your 6 digit OTP is: "+otp+"\nDon't Share with Anybody");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(OTPController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static void sendMail(String recepient, int otp){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String ac="ezzypaybd@gmail.com";
        String pass="EzzyPay@03";
        
        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(ac,pass);
            }
        });
        
        Message message= prepareMessage(session,ac,recepient,otp);
        try {
            Transport.send(message);
            System.out.println("sent");
        } catch (MessagingException ex) {
            Logger.getLogger(OTPController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       

    @FXML
    private void VerifyBtnOnAction(ActionEvent event) throws SQLException {
        if(Integer.parseInt(OTPField.getText()) == OTP)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Successful");
            alert.setContentText(null);
            alert.showAndWait();
            
            
            BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
            dataBaseConn.Connection();
            userBal-=amount;
            String que="UPDATE user_information SET Credit= "+userBal+" where PhoneNumber='"+PhNo+"'";
            pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(que);
            int i= pst.executeUpdate();
            if(i==1){
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Info");
                al.setHeaderText("Amount is Transfered to the Bank Account");
                al.setContentText(null);
                al.showAndWait();

                dataBaseConn.dbCon.close();
                CashOutController.stage.close();
            }
            
            
        }else{
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setHeaderText("Unuccessful");
                        alert.setContentText(null);
                        alert.showAndWait();
        }
    }

    

   
    
}
