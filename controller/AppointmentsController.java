package com.controller;

import com.model.AppointmentDA;
import com.model.AppointmentModel;
import com.model.CustomerDA;
import com.model.CustomerModel;
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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resources){ loadAppointments(); }

    public void loadAppointments() {
        try {
            ObservableList<AppointmentModel> appointments = AppointmentDA.getAllAppointments();
            aptId.setCellValueFactory(new PropertyValueFactory<>("id"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            location.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            start.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            end.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            AppointmentsTableView.setItems(appointments);
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
    void handleAddAppointment(ActionEvent event) throws IOException {
        try {
            changeScene(event, "AddAppointmentView");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleCustomerReport(ActionEvent event) {

    }

    @FXML
    void handleDeleteAppointment() throws SQLException {
        AppointmentModel selectedAppointment = AppointmentsTableView.getSelectionModel().getSelectedItem();
        try {
            if (selectedAppointment == null) {
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert proceed = new Alert(Alert.AlertType.WARNING, "No selected Appointment", clickOkay);
                proceed.showAndWait();
                return;
            } else {
                String selectedAppointmentType = AppointmentsTableView.getSelectionModel().getSelectedItem().getType();
                Integer selectedAppointmentID = AppointmentsTableView.getSelectionModel().getSelectedItem().getId();
                ButtonType confirm = ButtonType.OK;
                ButtonType cancel = ButtonType.CANCEL;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you SURE you want to cancel Appointment " + selectedAppointmentType + "?", confirm, cancel);
                Optional<ButtonType> splash = alert.showAndWait();
                if (splash.isPresent() && splash.get() == ButtonType.OK) {
                    AppointmentDA.deleteAppointment(selectedAppointmentID);
                    loadAppointments();
                    ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert deleteConfirm = new Alert(Alert.AlertType.CONFIRMATION, "You have removed Appointment: " + selectedAppointment.getId() + "  -  " + selectedAppointment.getTitle(), clickOkay);
                    deleteConfirm.showAndWait();
                }

            }

        } catch(Error e){
            e.printStackTrace();
        }

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
    void handleUpdateAppointment(ActionEvent event) throws IOException{
        AppointmentModel currentAppointment = AppointmentsTableView.getSelectionModel().getSelectedItem();

        try {
            if (currentAppointment == null) {
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an Appointment to update!", clickOkay);
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/UpdateAppointmentView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                UpdateAppointmentController controller = loader.getController();
                controller.setAppointmentData(currentAppointment);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void handleViewCustomers(ActionEvent event) throws IOException {
        try {
            changeScene(event, "CustomerView");
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private TableView<AppointmentModel> AppointmentsTableView;

    @FXML
    private Label sceneLabel;

    @FXML
    private Button appointmentButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<AppointmentModel, Integer> aptId;

    @FXML
    private TableColumn<AppointmentModel, String> title;

    @FXML
    private TableColumn<AppointmentModel, String> description;

    @FXML
    private TableColumn<AppointmentModel, String> location;

    @FXML
    private TableColumn<AppointmentModel, Integer> contactId;

    @FXML
    private TableColumn<AppointmentModel, String> type;

    @FXML
    private TableColumn<AppointmentModel, Timestamp> start;

    @FXML
    private TableColumn<AppointmentModel, Timestamp> end;

    @FXML
    private TableColumn<AppointmentModel, Integer> customerId;

    @FXML
    private TableColumn<AppointmentModel, Integer> userId;


    @FXML
    private Button AddAppointmentButton;

    @FXML
    private Button DeleteAppointmentButton;

    @FXML
    private Button UpdateAppointmentButton;

}
