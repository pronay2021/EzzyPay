/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pronay
 */
public class Admin_InterfaceController implements Initializable {

    @FXML
    private AnchorPane form;
    @FXML
    private Button userInfoBtn;
    
     public static Stage stage = new Stage();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BackBtnOnAction(ActionEvent event) throws IOException {
         Parent pane = FXMLLoader.load(getClass().getResource("Admin_logIn.fxml"));
            form.getChildren().setAll(pane);
    }

    @FXML
    private void mouseMove(MouseDragEvent event) {
    }

    @FXML
    private void mouseMove(MouseEvent event) {
        
//        Tooltip tt = new Tooltip();
//tt.setStyle("-fx-font: normal bold 4 Langdon; "
//    + "-fx-base: #AE3522; "
//    + "-fx-text-fill: orange;");
//userInfoBtn.setTooltip(tt);
    }

    @FXML
    private void ProgressChartOnAction(ActionEvent event) throws IOException {
        
        Parent pane = FXMLLoader.load(getClass().getResource("AdminOverview.fxml"));
            form.getChildren().setAll(pane);
    }

    @FXML
    private void profitChatOnAction(ActionEvent event) throws IOException {
        
         Parent pane = FXMLLoader.load(getClass().getResource("PieChart.fxml"));
            form.getChildren().setAll(pane);
    }

    @FXML
    private void userInfoBtnOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TableShow.fxml"));
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void ProfitAmountsOnAction(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("profit_data.fxml"));
            form.getChildren().setAll(pane);
    }

   
   
    
}
