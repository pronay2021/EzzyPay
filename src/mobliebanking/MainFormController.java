/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class MainFormController implements Initializable {

    @FXML
    private Button AdminFormBtn;
    @FXML
    private Button UserFormBtn;
    @FXML
    private AnchorPane form;
    @FXML
    private Label creatACLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AdminFormBtnOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Admin_logIn.fxml"));
         form.getChildren().setAll(root);
    }

    @FXML
    private void UserFormBtnOnAction(ActionEvent event) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
            form.getChildren().setAll(pane);

        } catch (IOException ex) {
            Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void creatACLabelOnAction(MouseEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("RegistrationForm.fxml"));
         form.getChildren().setAll(root);
    }

    @FXML
    private void AboutUsAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AboutUs.fxml"));
         form.getChildren().setAll(root);
    }
    
}
