/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import javafx.scene.control.Alert;

/**
 *
 * @author FUJITSU
 */
public class EmailNotValidException extends RuntimeException{

    public EmailNotValidException(String message1,String message2) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(message1);
        alert.setContentText(message2);
        alert.showAndWait();
    }
    
    
}
