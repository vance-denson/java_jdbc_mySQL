package com.controller;

import com.model.CustomerDA;
import com.model.CustomerModel;
import com.utils.Patterns;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<String> countries = CustomerDA.getCountries();
            countryBox.setItems(countries);

        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public void changeScene(ActionEvent event, String newScene) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/view/" + newScene + ".fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @FXML
    void showDivision() {

        try {
            String selectedCountry = countryBox.getValue();

            if (selectedCountry.equals("U.S")) {
                ObservableList<String> usDivisions = CustomerDA.getDivisions(1);
                stateBox.setItems(usDivisions);
            } else if (selectedCountry.equals("UK")) {
                ObservableList<String> ukDivisions = CustomerDA.getDivisions(2);
                stateBox.setItems(ukDivisions);
            } else if (selectedCountry.equals("Canada")) {
                ObservableList<String> canDivisions = CustomerDA.getDivisions(3);
                stateBox.setItems(canDivisions);
            } else {
                stateBox.setItems(null);
            }
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddCustomer(ActionEvent event) throws SQLException, IOException {
        try {
            String name = customerName.getText();
            String address = customerAddress.getText();
            String post = postCode.getText();
            String phone = phoneNumber.getText();
            String country = countryBox.getValue();
            String division = stateBox.getValue();
            Integer divisionId = CustomerDA.getDivisionID(division);

            if (Patterns.containsNumber(name) || name.equals("")) {
                System.out.println("Invalid Name");
                return;
            }
            if (name.contains("V")) {
                System.out.println("Customer could not be added, try again.");
                return;
            }

            CustomerDA.addCustomer(name, address, post, phone, country, divisionId);
            System.out.println("New Customer added.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/CustomerView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    };

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        try {
            changeScene(event, "CustomerView");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleCustomerReport(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        try {
            changeScene(event, "LoginView");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleViewCustomerAppointment(ActionEvent event) {

    }


    @FXML
    private Label sceneLabel;

    @FXML
    private Button appointmentButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField customerAddress;

    @FXML
    private TextField postCode;

    @FXML
    private TextField customerName;

    @FXML
    private ComboBox<String> countryBox;

    @FXML
    private ComboBox<String> stateBox;

}
