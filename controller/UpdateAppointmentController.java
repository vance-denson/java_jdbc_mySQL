package com.controller;

import com.model.AppointmentDA;
import com.model.AppointmentModel;
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
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Integer.parseInt;

public class UpdateAppointmentController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contactBox.setItems(CustomerDA.getCustomerList());

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

    public void setAppointmentData(AppointmentModel appointment){

        try {


            DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("hh:mm");
            contactBox.setItems(CustomerDA.getCustomerList());
            customerBox.setItems(AppointmentDA.getContacts());
            contactBox.setValue(appointment.getContactId().toString());
            customerBox.setValue(appointment.getCustomerId().toString());
            aptID.setText(appointment.getId().toString());
            aptTitle.setText(appointment.getTitle());
            aptDesc.setText(appointment.getDescription());
            aptLocation.setText(appointment.getLocation());
            aptType.setText(appointment.getType());
            aptDate.setValue(appointment.getStartTime().toLocalDateTime().toLocalDate());
            startTime.setText(appointment.getStartTime().toLocalDateTime().format(dtFormat));
            endTime.setText(appointment.getEndTime().toLocalDateTime().format(dtFormat));
        } catch (Error e) {
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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleUpdateAppointment(ActionEvent event) {

        //getCurrentUser -> add to updateCustomer() below

        try {
            ButtonType confirm = ButtonType.OK;
            ButtonType cancel = ButtonType.CANCEL;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you SURE you want to Update Appointment ?", confirm, cancel);
            Optional<ButtonType> splash = alert.showAndWait();
            if (splash.isPresent() && splash.get() == ButtonType.OK) {
                DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("hh:mm");
//                LocalTime sTime = LocalTime.parse(startTime.getText(), dtFormat);
//                LocalTime eTime = LocalTime.parse(endTime.getText(), dtFormat);
//                LocalTime.of
                LocalTime sTime = LocalTime.of(parseInt(startTime.getText().split(":")[0]), parseInt(startTime.getText().split(":")[1]));
                LocalTime eTime = LocalTime.of(parseInt(endTime.getText().split(":")[0]), parseInt(endTime.getText().split(":")[1]));
                LocalDateTime start = LocalDateTime.of(aptDate.getValue(), sTime);
                LocalDateTime end = LocalDateTime.of(aptDate.getValue(), eTime);
                Integer updatedCustomer = parseInt(customerBox.getValue().split(",")[0]);
                Integer updatedContact = parseInt(contactBox.getValue().split(",")[0]);
                AppointmentDA.updateAppointment(parseInt(aptID.getText()), aptTitle.getText(), aptDesc.getText(), aptLocation.getText(), aptType.getText(), updatedContact, updatedCustomer, aptDate.getValue(), start, end, DatabaseConnection.getUserId());
//                changeScene(event, "AppointmentView");
            }

        } catch(SQLException e){
            e.printStackTrace();
        }


    }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
//        Portal.logout()
        try{
            changeScene(event, "LoginView");
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private Label sceneLabel;

    @FXML
    private Button reportButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button UpdateAppointmentButton;

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
    private ComboBox<String> customerBox;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextField aptID;

}
