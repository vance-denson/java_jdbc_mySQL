package com.controller;

import com.model.CustomerDA;
import com.model.CustomerModel;
import com.utils.DatabaseConnection;
import com.utils.Helpers;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


public class UpdateCustomerController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<String> countries = CustomerDA.getCountries();
            countryBox.setItems(countries);

        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public void setCustomerData(CustomerModel customer){
        countryBox.setItems(CustomerDA.getCountries());
        countryBox.setValue(customer.getCountry());
        showDivision();
        stateBox.setValue(CustomerDA.getDivisionName(customer.getDivision()));
        customerID.setText(customer.getId().toString());
        customerAddress.setText(customer.getAddress());
        customerName.setText(customer.getName());
        postCode.setText(customer.getZip());
        phoneNumber.setText(customer.getPhone());
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
    void updateCustomerSubmit(ActionEvent event) throws SQLException, IOException {

        //getCurrentUser -> add to updateCustomer() below

         try {
            ButtonType confirm = ButtonType.OK;
            ButtonType cancel = ButtonType.CANCEL;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you SURE you want to Update customer ?", confirm, cancel);
            Optional<ButtonType> splash = alert.showAndWait();
               if (splash.isPresent() && splash.get() == ButtonType.OK) {
                   CustomerDA.updateCustomer(parseInt(customerID.getText()), customerName.getText(), customerAddress.getText(), postCode.getText(), phoneNumber.getText(), stateBox.getValue());
                   changeScene(event, "CustomerView");
                }

        } catch(SQLException e){
            e.printStackTrace();
        } catch(IOException e){
             e.printStackTrace();
         }
    }

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
    void handleLogout(ActionEvent event) {

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
    private Button UpdateCustomerButton;

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

    @FXML
    private TextField customerID;



}
