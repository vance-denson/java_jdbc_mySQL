package com.controller;

import com.model.AppointmentDA;
import com.model.CustomerDA;
import com.model.CustomerModel;
import com.utils.DatabaseConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import static java.time.LocalTime.parse;

public class AddAppointmentController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<String> contacts = AppointmentDA.getContacts();
            contactBox.setItems(contacts);
            ObservableList<String> customers = CustomerDA.getCustomerList();
            customerBox.setItems(customers);

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

    public boolean validInputs() {
        boolean valid = true;
        try {

        } catch (Error e){
            e.printStackTrace();
        }
        return valid;
    }


    @FXML
    void handleAddAppointment(ActionEvent event) throws SQLException, DateTimeParseException, IOException {
        try {
        ArrayList<String> errors = null;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

        String aDescription = aptDesc.getText();
        String aLocation = aptLocation.getText();
        String aType = aptType.getText();
        String aTitle = aptTitle.getText();
        String aStart = startTime.getText();
        String aEnd = endTime.getText();
        String aContact = contactBox.getValue();
        String aCustomer = customerBox.getValue();
        LocalDate appointmentDate = aptDate.getValue();
        Integer contactID = parseInt(aContact.split(",")[0]);
        Integer customerID = parseInt(aCustomer.split(",")[0]);
        LocalDateTime aptStartTime = LocalDateTime.of(appointmentDate, LocalTime.parse(aStart, format));
        LocalDateTime aptEndTime = LocalDateTime.of(appointmentDate, LocalTime.parse(aEnd, format));

            if (validInputs()) {
                try {
                    AppointmentDA.addAppointment(aTitle, aDescription, aLocation, aType, aptStartTime, aptEndTime, customerID, contactID);
                    changeScene(event, "AppointmentView");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Hello");
                alert.showAndWait();
                System.out.println(DatabaseConnection.getUserId());
            }

        } catch (DateTimeParseException e){
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert dateConfirm = new Alert(Alert.AlertType.CONFIRMATION, "You must use the correct date format");
            dateConfirm.showAndWait();
            return;
//            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }



    }

    @FXML
    void handleAppointmentReport(ActionEvent event) {

    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        try {
            changeScene(event, "AppointmentView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {

    }
    @FXML
    private Label sceneLabel;

    @FXML
    private Button reportButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField aptType;

    @FXML
    private TextField aptDesc;

    @FXML
    private TextField aptLocation;

    @FXML
    private TextField aptTitle;

    @FXML
    private ComboBox<String> contactBox;

    @FXML
    private DatePicker aptDate;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private ComboBox<String> customerBox;

    @FXML
    private TextField aptID;

}
