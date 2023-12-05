/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;


import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author FUJITSU
 */
public class MobileBanking extends Application {
    public static String phonNum;
    public static boolean isLoaded= false;
    public static Stage stage;
    public static double profit;
    public static double FoodProfit;
    public static double Electricityprofit;
    public static double Gasprofit;
    public static double Waterprofit;
    public static double Internetprofit;
    public static double TVprofit;
    public static double Educationprofit;
    public static double SendMoneyProfit;
    
  // private static final int COUNT_LIMIT = 10;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Mobile Banking");
        stage.setScene(scene);
        stage.show();
    }

//    @Override
//    public void init() throws Exception {       
//        
//        // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
//        for (int i = 1; i <= COUNT_LIMIT; i++) {
//            double progress =(double) i/10;
//            System.out.println("progress: " +  progress);            
//            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
//            Thread.sleep(2000);
//        }
//        
//    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        LauncherImpl.launchApplication(MobileBanking.class, WelcomeLoader.class, args);
        launch(args);
    }
    
}
