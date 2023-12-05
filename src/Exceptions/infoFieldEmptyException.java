
package Exceptions;

import javafx.scene.control.Alert;

/**
 *
 * @author FUJITSU
 */
public class infoFieldEmptyException extends RuntimeException{

    public infoFieldEmptyException(String message1, String message2) {
        //super(message1);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(message1);
        alert.setContentText(message2);
        alert.showAndWait();
    }
    
}
