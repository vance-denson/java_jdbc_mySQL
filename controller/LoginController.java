/*
* @author : Vance Denson
* Customer View
* */
package com.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;


public class LoginController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resources){
        String userLang = System.getProperty("user.language").toString();
        String userZone = ZoneId.systemDefault().toString();

        if (userLang.equals("en")){
            locationLabel.setText("English\n"+userZone);
        }
        if (userLang.equals("fr")){
            locationLabel.setText("French\n"+userZone);
        }

    }


    @FXML
    void handleLogin(Event event) throws IOException{
        System.out.print("handleLogin()");

        String user = userNameInput.getText();
        String pass = passwordInput.getText();

        boolean authorized = true; //DatabaseConnection.validateUser(user, pass);

        if (authorized) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/CustomerView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

    }
    /*
     * FXML initializations
     * */
    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField userNameInput;

    @FXML
    private Label sceneLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label locationLabel;

}
