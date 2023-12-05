/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.*;
import Exceptions.*;
import Exceptions.infoFieldEmptyException;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
//import com.pusiknas.web.util.EmailValidator;
import java.sql.ResultSet;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class RegistrationFormController implements Initializable {

    @FXML
    private JFXTextField Fname;
    @FXML
    private JFXTextField Lname;
    @FXML
    private JFXTextField Uname;
    @FXML
    private ComboBox<String> Gender;
    @FXML
    private JFXTextField Country;
    @FXML
    private TextArea Address;
    @FXML
    private JFXTextField Referance;
    @FXML
    private JFXPasswordField Password;
    @FXML
    private JFXPasswordField Cpassword;
    @FXML
    private DatePicker Bdate;
    @FXML
    private Button BackBtn;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private AnchorPane form;
    @FXML
    private JFXTextField Pnumber;
    @FXML
    private JFXTextField Email;
    private boolean c=true;
    public String phno;
    ResultSet rs;
    PreparedStatement pst;
    //boolean getPnumber;
    @FXML
    private JFXTextField color;

    public RegistrationFormController() {
    }

    public String getPnumber() {
        phno= Pnumber.getText();
        return phno;
    }
    
    void select(ActionEvent event) {
        //System.out.println(Bdate.getValue());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list=FXCollections.observableArrayList("Male","Female");
        Gender.setItems(list);
        
        getPnumber();
        NumberValidator numValidator=new NumberValidator();
        Pnumber.getValidators().add(numValidator);
       numValidator.setMessage("add only 11 digits of your phone number");
       Pnumber.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(!t1){
                    Pnumber.validate();
                    
                }else{
                    Pnumber.setText("");
                }   
            }
       });
        
       RegexValidator validate = new RegexValidator();
       validate.setRegexPattern("[A-Za-z\\s]+");
       Fname.setValidators(validate);
       Fname.getValidators().get(0).setMessage("Name Should be contain only Alphabets");
       Lname.setValidators(validate);
       Lname.getValidators().get(0).setMessage("Name Should be contain only Alphabets");
        Fname.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(!t1){
                    Fname.validate();
                }  
            }
       });
       
        Lname.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(!t1){
                    Lname.validate();
                }  
            }
       });
        
       RegexValidator valid = new RegexValidator();

        valid.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Email.setValidators(valid);
        Email.getValidators().get(0).setMessage("Email is not valid!");
        
        Email.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(!t1){
                    c= Email.validate();
                }  
            }
       });

    }    
    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
            form.getChildren().setAll(pane);
    }

    @FXML
    private void genderSelect(ActionEvent event) {
        String gender= Gender.getValue();
    }

    
    private void reset(){
        Fname.setText("");
        Lname.setText("");
        Email.setText("");
        Uname.setText("");
        Country.setText("");
        color.setText("");
        Pnumber.setText("");
        Address.setText("");
        Referance.setText("");
        Password.setText("");
        Cpassword.setText("");
        Bdate.setValue(null);
        Gender.setValue(null);
        
    }
    public static boolean isNumeric(String str) { 
        try {  
                Double.parseDouble(str);  
                return true;
        } catch(NumberFormatException e){  
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Number is not valid");
            alert.setContentText(null);
            alert.showAndWait();
            return false;
        }  
    }
    
    private boolean validateEmail(){
        try{
            if(!c){
                throw new EmailNotValidException("Invalid Email!!!","Please provide email properly.");
            }     
        }catch(EmailNotValidException e){
            return false;
        }
        return true;
    }
    private boolean validateFields(){
       try{
        if(Fname.getText().isEmpty() || Lname.getText().isEmpty() || Email.getText().isEmpty() || Uname.getText().isEmpty() || 
                Gender.getValue().isEmpty() || Country.getText().isEmpty() || color.getText().isEmpty() || Pnumber.getText().isEmpty() || Address.getText().isEmpty() ||
               Referance.getText().isEmpty() || Password.getText().isEmpty() || Bdate.getEditor().getText().isEmpty() || Cpassword.getText().isEmpty()){
            
            //Alert Message
            throw new infoFieldEmptyException("Information Missing!!!","Please provide all the informations properly.");
        }
       }catch(infoFieldEmptyException e){
           return false;
       }
        return true;
    }
    private boolean passCheck(){
        boolean correctPass=true;
        String p=Password.getText();
        String cp=Cpassword.getText();
        try{
        if (!(p.equals(cp))) {
                    throw new PasswordNotMatchException("password Mismatch","Please Confirm Password Again.");
        }
        }catch(PasswordNotMatchException e){
            Password.setText("");
            Cpassword.setText("");
            correctPass=false;  
        }
        return correctPass;
    }
    private boolean validatePnum(){
       if(Pnumber.getText().length()== 11)
        return true;
       else
         return false;
    }
    private boolean validateRef(){
         BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
         if(!(Referance.getText().equals("")))
         {
             try {
            dataBaseConn.Connection();
            String q="SELECT `Credit` FROM `user_information` WHERE `PhoneNumber`='"+Referance.getText()+"'";
            
            pst=(PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(q);
            rs= pst.executeQuery();
            if(rs.next())
            {
                double d=rs.getDouble("Credit"); 
                String qu="UPDATE `user_information` SET `Credit`= "+(d+50)+" WHERE `PhoneNumber`='"+Referance.getText()+"'";
                pst=(PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(qu);
                pst.executeUpdate();
                return true;
            }else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("This Reference user is not Found!\nPlease use a valid Reference.");
                alert.setContentText(null);
                alert.showAndWait();
                Referance.setText("");
                return false;
            }
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationFormController.class.getName()).log(Level.SEVERE, null, ex);
                    
            }
         }
         return false;
    }
    private void readInfoAndInsert( BDConnector dataBaseConn) throws  IOException{
        
        if(validateFields() && passCheck() && isNumeric(Pnumber.getText()) && validateEmail() && validatePnum() && validateRef()){
        String fName=Fname.getText();
        String lName=Lname.getText();
        String email=Email.getText();
        String uName=Uname.getText();
        String country=Country.getText();
        String c=color.getText();
        String pNumber=Pnumber.getText();
        String address=Address.getText();
        String referance=Referance.getText();
        String pass=Password.getText();
        String bDate = Bdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
         String uImg="C:\\Users\\FUJITSU\\Documents\\NetBeansProjects\\MoblieBanking\\src\\mobliebanking\\Images\\avater.png";
         uImg=uImg.replace("\\", "\\\\");

        Statement statement;
            try {
                statement = (Statement) dataBaseConn.dbCon.createStatement();
        
        String query="insert into user_information "+"values('"+fName+"','"+lName+"','"+email+"','"+uName+"','"+Gender.getValue()+"','"+
                country+"','"+pNumber+"','"+bDate+"','"+address+"','"+referance+"','"+pass+"',0.0,'"+uImg+"','"+c+"')";
        
            int executeUpdate;
            
                executeUpdate = statement.executeUpdate(query);
           
            if(executeUpdate==1)
            {
                MobileBanking.isLoaded=true;
                MobileBanking.phonNum=Pnumber.getText();
                Parent root = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
                Scene scene=new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                MobileBanking.stage.close();
                dataBaseConn.dbCon.close();
                
            }
             } catch (SQLException ex) {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("This Email or Password is already in use.");
                    alert.setContentText("Please use another Email/Password to Continue. Or Go to Login ");
                    alert.showAndWait();
                 Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("Go to Login?");
                    a.setContentText("Click yes to continue");
                    
                    ButtonType btnYes=new ButtonType("Yes");
                    ButtonType btnCancel=new ButtonType("Cancel");
                    a.getButtonTypes().setAll(btnYes,btnCancel);
                    Optional<ButtonType> res=a.showAndWait();
                    if(res.get() == btnYes){
                        Parent pane = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
                        form.getChildren().setAll(pane);
                    }
                    
                    
            }
        reset();
        }

    }
    @FXML
    private void signUpBtnOnAction(ActionEvent event) throws SQLException, IOException {
        
       
        //Database Work
        BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        dataBaseConn.Connection();
        
        readInfoAndInsert(dataBaseConn);
        
    }

    @FXML
    private void resetBtnOnAction(ActionEvent event) {
        reset();
    } 
}
