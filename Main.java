/*
* @author Vance Denson
* Main class of Scheduling Project
* */
package com;

import com.utils.DatabaseConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.utils.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

public class Main extends Application {

    /*
    * @param primaryStage : Init Stage
    * @throws : Exception
    * */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/view/LoginView.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root, 1200, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        if (System.getProperty("user.language").equals("fr")){
        Locale.setDefault(new Locale("french"));
//        }
//        else {
//            Locale.setDefault(new Locale("fr"));
//        }

//        final String userLanguage = System.getProperty("user.language");
//        final String userZoneId = ZoneId.systemDefault().toString();
//
//        System.out.println(userLanguage);
//        System.out.println(userZoneId);

        DatabaseConnection.startConnection(DatabaseConnection.username, DatabaseConnection.password);
        Application.launch(Main.class, args);

    }

}
