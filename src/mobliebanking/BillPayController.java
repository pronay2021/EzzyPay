/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author FUJITSU
 */
public class BillPayController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void electricityOnAction(MouseEvent event) throws IOException {
         Parent pane = FXMLLoader.load(getClass().getResource("Electricity.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void GasOnAction(MouseEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Gas.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void waterOnAction(MouseEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Water.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void internetOnAction(MouseEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Internet.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void tvOnAction(MouseEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("TV.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void educationOnAction(MouseEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Education.fxml"));
        anchorPane.getChildren().setAll(pane);
    }
    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
            anchorPane.getChildren().setAll(pane);
    }
}
