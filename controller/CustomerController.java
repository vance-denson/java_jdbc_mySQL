package com.controller;

import com.model.CustomerModel;
import com.model.CustomerDA;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable{


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomers();
    }

    public void loadCustomers(){
        try {
            ObservableList<CustomerModel> customers = CustomerDA.getCustomers();
            customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            postCode.setCellValueFactory(new PropertyValueFactory<>("zip"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            division.setCellValueFactory(new PropertyValueFactory<>("division"));
            CustomerTableView.setItems(customers);
        } catch (Error e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleLogout(ActionEvent event) throws IOException{


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/LoginView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("FXML Load Exception, thrown!");
        }

    }

    @FXML
    void handleAddCustomer(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/AddCustomerView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("FXML Load Exception, thrown!");
        }


    }

    @FXML
    void handleCustomerReport() {
        System.out.println("handleCustomerReport()");
    }

    @FXML
    void handleDeleteCustomer() throws SQLException {
        CustomerModel selectedCustomer = CustomerTableView.getSelectionModel().getSelectedItem();
        try {
            if (selectedCustomer == null) {
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert proceed = new Alert(Alert.AlertType.WARNING, "No selected Customer", clickOkay);
                proceed.showAndWait();
                return;
            } else {
                Integer selectedCustomerId = CustomerTableView.getSelectionModel().getSelectedItem().getId();
                ButtonType confirm = ButtonType.OK;
                ButtonType cancel = ButtonType.CANCEL;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you SURE you want to DELETE customer ?", confirm, cancel);
                Optional<ButtonType> splash = alert.showAndWait();
                if (splash.isPresent() && splash.get() == ButtonType.OK) {
                    CustomerDA.deleteCustomer(selectedCustomerId);
                    loadCustomers();
                    ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert deleteConfirm = new Alert(Alert.AlertType.CONFIRMATION, "You have removed Customer: " + selectedCustomer.getId() + "  -  " + selectedCustomer.getName(), clickOkay);
                    deleteConfirm.showAndWait();
                }

            }

        } catch(Error e){
            e.printStackTrace();
        }

    }

    @FXML
    void handleViewCustomerAppointment(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/AppointmentView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("FXML Load Exception, thrown!");
        }
    }

    @FXML
    void handleUpdateCustomer(ActionEvent event) throws IOException {
        CustomerModel currentCustomer = CustomerTableView.getSelectionModel().getSelectedItem();

        try {
            if (currentCustomer == null) {
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a Customer to update!", clickOkay);
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/UpdateCustomerView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                UpdateCustomerController controller = loader.getController();
                controller.setCustomerData(currentCustomer);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch(Error e){
            e.printStackTrace();
        }

    }

    @FXML
    private Label sceneLabel;

    @FXML
    private Button appointmentButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label locationLabel;

    @FXML
    private Button reportButton;

    @FXML
    private Button loginButton11;

    @FXML
    private TableView<CustomerModel> CustomerTableView;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private TableColumn<CustomerModel, Integer> customerId;

    @FXML
    private TableColumn<CustomerModel, String> customerName;

    @FXML
    private TableColumn<CustomerModel, String> customerAddress;

    @FXML
    private TableColumn<CustomerModel, Integer> division;

    @FXML
    private TableColumn<CustomerModel, String> postCode;

    @FXML
    private TableColumn<CustomerModel, String> phone;




}
