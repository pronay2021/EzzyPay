/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class UserInterfaceController implements Initializable {

    @FXML
    private ImageView userPic;
    @FXML
    private Button LogOutBtn;
    @FXML
    private TextField CreditField;
    @FXML
    private Button RemoveAcBtn;
    @FXML
    private ImageView sendMoneyBtn;
    @FXML
    private ImageView CashInBtn;
    @FXML
    private ImageView MobileRechargeBtn;
    @FXML
    private ImageView FoodBtn;
    @FXML
    private ImageView PayBillBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void userPicOnAction(MouseEvent event) {
    }

    @FXML
    private void LogOutBtnAction(ActionEvent event) {
    }

    @FXML
    private void CreditFieldOnAction(ActionEvent event) {
    }

    @FXML
    private void RemoveACBtnOnAction(ActionEvent event) {
    }

    @FXML
    private void sendMoneyBtnOnAction(MouseEvent event) {
    }

    @FXML
    private void CashInBtnOnAction(MouseEvent event) {
    }

    @FXML
    private void MobileRechargeBtnOnAction(MouseEvent event) {
    }

    @FXML
    private void FoodBtn(MouseEvent event) {
    }

    @FXML
    private void FoodBtnOnAction(DragEvent event) {
    }

    @FXML
    private void CashOutBtn(MouseEvent event) {
    }

    @FXML
    private void CashOutBtnOnAction(DragEvent event) {
    }

    @FXML
    private void PayBillBtnOnAction(MouseEvent event) {
    }
    
}
