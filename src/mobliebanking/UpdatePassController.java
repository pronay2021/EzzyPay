/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class UpdatePassController implements Initializable {

    @FXML
    private JFXPasswordField Upass;
    @FXML
    private JFXTextField Code;

    int OTP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Random rand= new Random();
        OTP=rand.nextInt(999999);
//        System.out.println(OTP);
        sendMail(ForgetPassController.mail,OTP);
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
    private void Save(ActionEvent event) throws SQLException, IOException {
        if(Integer.parseInt(Code.getText()) == OTP){
        String pnum=Upass.getText();
        //System.out.println(MobileBanking.phonNum);
         BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
                dataBaseConn.Connection();
                
                
                String query="UPDATE `user_information` SET `Password`=? WHERE `PhoneNumber`=?";
        PreparedStatement pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
                pst.setString(1,pnum);
                 pst.setString(2,MobileBanking.phonNum);
                 
        int rs = pst.executeUpdate();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Update Successful.");
                ForgetPassController.stage.close();
                alert.showAndWait();
                
                MobileBanking.stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
                Scene scene=new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                
                dataBaseConn.dbCon.close();
        }
    } 
    
}
