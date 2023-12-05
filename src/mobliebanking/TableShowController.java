/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import DatabaseConnection.BDConnector;
import static com.mysql.jdbc.Messages.getString;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class TableShowController implements Initializable {

    
@FXML
private TableView<ModelTable> table;
    @FXML
    private TableColumn< ModelTable ,String> Fname;
    @FXML
    private TableColumn<ModelTable ,String> Lname;
    @FXML
    private TableColumn<ModelTable ,String> E;
    @FXML
    private TableColumn<ModelTable ,String> Uname;
    @FXML
    private TableColumn<ModelTable ,String> G;
    @FXML
    private TableColumn<ModelTable ,String> C;
    @FXML
    private TableColumn<ModelTable ,String> PNumber;
    @FXML
    private TableColumn<ModelTable ,Date> Bdate;
    @FXML
    private TableColumn<ModelTable ,String> Add;
    @FXML
    private TableColumn<ModelTable ,String> Ref;
    @FXML
    private TableColumn<ModelTable ,String> Pass;
    @FXML
    private TableColumn<ModelTable ,Double> Cre;
    @FXML
    private TableColumn<ModelTable ,String> SecurityQues;
    
    ObservableList<ModelTable>list = FXCollections.observableArrayList();
    ObservableList<ModelTable>l = FXCollections.observableArrayList();
    PreparedStatement pst;
    ResultSet rs;
    @FXML
    private ComboBox Field;
    @FXML
    private TextField Value;
    @FXML
    private TextField EnterPhone;

    private void read(){
        BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        try {
            dataBaseConn.Connection();
            String query="select * from user_information WHERE 1";
            pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
            
            rs = pst.executeQuery();
            System.out.println(rs.next());
            while(rs.next())
            {
               list.add(new ModelTable(rs.getString("FirstName"),rs.getString("LastName"),rs.getString("Email"),rs.getString("UserName"),rs.getString("Gender"),
                       rs.getString("Country"),rs.getString("PhoneNumber"),rs.getDate("BirthDate"),rs.getString("Address"),rs.getString("Reference"),rs.getString("Password"),
                       rs.getDouble("Credit"),rs.getString("SecurityQues")
               ));                         
                                
            }
                
            
        } catch (SQLException ex) {
            Logger.getLogger(TableShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setValue(){
        
        Fname.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("FirstName"));
        Lname.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("LastName"));
        E.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("Email"));
        Uname.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("UserName"));
        G.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("Gender"));
        C.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("Country"));
        PNumber.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("PhoneNumber"));
        Bdate.setCellValueFactory(new PropertyValueFactory<ModelTable ,Date>("BirthDate"));
        Add.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("Address"));
        Ref.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("Reference"));
        Pass.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("Password"));
        Cre.setCellValueFactory(new PropertyValueFactory<ModelTable ,Double>("Credit"));
        SecurityQues.setCellValueFactory(new PropertyValueFactory<ModelTable ,String>("SecurityQues"));
    }
            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        ObservableList <String> l;
        l = FXCollections.observableArrayList("PhoneNumber","Email","Address");
        Field.setItems(l);
        
//        read();
//        setValue();
//        table.setItems(list);
        
        
    }

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("Admin_Interface.fxml"));
       Scene scene = new Scene(root);
       MobileBanking.stage.setTitle("Mobile Banking");
        MobileBanking.stage.setScene(scene);
        MobileBanking.stage.show();
        Admin_InterfaceController.stage.close();
       
    }

    @FXML
    private void RemoveOnAction(ActionEvent event) {
        
        if(EnterPhone.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please enter the Phone Number first");
            alert.setContentText(null);
            alert.showAndWait();
        }
         else
        {
            String phnno=EnterPhone.getText();
            BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
            
            try {
                dataBaseConn.Connection();
                String query="select * from user_information WHERE `PhoneNumber`= '"+phnno+"'";
                 PreparedStatement pst1 = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
                // ResultSet rs1 = ;
            System.out.println(pst1.execute());
            if(pst1.execute())
            {
                 String q="DELETE FROM user_information WHERE PhoneNumber=?";
                 pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(q);
                 pst.setString(1,phnno);
                 int i=pst.executeUpdate();
                 if(i==1){
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info");
                    alert.setHeaderText("User Removed.");
                    alert.setContentText(null);
                    alert.showAndWait();
                    EnterPhone.setText("");
                 }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("This User is Not Found");
                alert.setContentText(null);
                alert.showAndWait();
                EnterPhone.setText("");
            }
            
            } catch (SQLException ex) {
                Logger.getLogger(TableShowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        if(Value.getText().equals("") ||Field.getValue().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please enter all fields first");
            alert.setContentText(null);
            alert.showAndWait();
        }
        else
        {
            
            BDConnector dataBaseConn=new BDConnector("root","","localhost:3306","mobile_bankingdb");
        try {
            
            String s= Value.getText();
            //String fName= (String) Field.getValue();
            dataBaseConn.Connection();
            String query="select * from user_information WHERE `"+Field.getValue()+"`= '"+s+"'";
            pst = (PreparedStatement) dataBaseConn.dbCon.clientPrepareStatement(query);
            
            rs = pst.executeQuery();
            System.out.println(rs.next());
            if(Field.getValue()=="PhoneNumber" || Field.getValue()=="Email")
            {
                list.add(new ModelTable(rs.getString("FirstName"),rs.getString("LastName"),rs.getString("Email"),rs.getString("UserName"),rs.getString("Gender"),
                       rs.getString("Country"),rs.getString("PhoneNumber"),rs.getDate("BirthDate"),rs.getString("Address"),rs.getString("Reference"),rs.getString("Password"),
                       rs.getDouble("Credit"),rs.getString("SecurityQues")
               ));
            }
            else
            {
                while(rs.next())
            {
               list.add(new ModelTable(rs.getString("FirstName"),rs.getString("LastName"),rs.getString("Email"),rs.getString("UserName"),rs.getString("Gender"),
                       rs.getString("Country"),rs.getString("PhoneNumber"),rs.getDate("BirthDate"),rs.getString("Address"),rs.getString("Reference"),rs.getString("Password"),
                       rs.getDouble("Credit"),rs.getString("SecurityQues")
               ));                         
                                
            }
            }
            
                
            
        } catch (SQLException ex) {
            Logger.getLogger(TableShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setValue();
        table.setItems(list);
        }
    }

    @FXML
    private void CField(ActionEvent event) {
        String fieldVal = (String) Field.getValue();
    }

    @FXML
    private void AllInfoShowOnAction(ActionEvent event) {
       read();
       setValue();
       table.setItems(list); 
    }
    
    
}
 
