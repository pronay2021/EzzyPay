/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author FUJITSU
 */
public class WelcomeLoader extends Preloader{

    private Stage preloaderStage;
    private Scene scene;
    
    public WelcomeLoader() {
        
    }

    @Override
    public void init() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
        scene=new Scene(root);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage=primaryStage;
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();
    }
    
    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info)
    {
        if (info instanceof ProgressNotification) {
            WelcomeScreenController.label.setText("Loading "+((ProgressNotification) info).getProgress()*100 + "%");
//            System.out.println("Value@ :" + ((ProgressNotification) info).getProgress());
//            WelcomeScreenController.statProgressBar.setProgress(((ProgressNotification) info).getProgress());
        }
    }
    
    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            
            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                System.out.println("BEFORE_START");
                preloaderStage.hide();
                break;
        }
    }
    
}
