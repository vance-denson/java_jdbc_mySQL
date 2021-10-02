package com.model;

import com.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerDA {
        public static ObservableList<CustomerModel> getCustomers(){
        ObservableList<CustomerModel> customers = FXCollections.observableArrayList();

        try{
            String newQuery = "SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID=countries.Country_ID";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Integer id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Integer division = rs.getInt("Division_ID");
                String country = rs.getString("Country");
                CustomerModel customerModel = new CustomerModel(id, name, phone, address, zip, division, country);
                customers.add(customerModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;

    }

    public static CustomerModel getCustomer(Integer id) throws SQLException{
        CustomerModel singleCustomer = new CustomerModel(null, null, null, null, null, null, null);

        try {
            String newQuery = "SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID=countries.Country_ID WHERE Customer_ID=?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            singleCustomer.setName(rs.getString("Customer_Name"));
//            singleCustomer = new CustomerModel((rs.getInt("Customer_ID")),(rs.getString("Customer_Name")), (rs.getString("Phone")), (rs.getString("Address")), (rs.getString("Postal_Code")), (rs.getInt("Division_ID")), (rs.getString("Country")) );
        } catch (SQLException e){
            e.printStackTrace();
        }
        return singleCustomer;
    }

    public static ObservableList<String> getCountries(){

        ObservableList<String> countries = FXCollections.observableArrayList();

        try{
            String newQuery = "SELECT DISTINCT country FROM countries";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                countries.add(rs.getString("Country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;

    }

    public static ObservableList<String> getCustomerList(){

        ObservableList<String> customers = FXCollections.observableArrayList();

        try{
            String newQuery = "SELECT DISTINCT Customer_ID, Customer_Name FROM customers";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                customers.add(rs.getString("Customer_ID") + ", " + rs.getString("Customer_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;

    }


    public static ObservableList<String> getDivisions(Integer countryId){

        ObservableList<String> divisions = FXCollections.observableArrayList();

        try{
            String newQuery = "SELECT DISTINCT Division FROM first_level_divisions WHERE COUNTRY_ID = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            divisions.removeAll();
            while(rs.next()) {
                divisions.add(rs.getString("Division"));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;

    }

    public static Integer getDivisionID(String divisionSelection){

        Integer divisionID = null;

        try{
            String newQuery = "SELECT DISTINCT Division_ID FROM first_level_divisions WHERE Division = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ps.setString(1, divisionSelection);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionID;

    }

    public static String getDivisionName(Integer divisionId){

        String divisionName = null;

        try{
            String newQuery = "SELECT DISTINCT Division FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                divisionName = rs.getString("Division");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionName;

    }

    public static Boolean addCustomer(String customerName, String address, String postCode, String phone, String country, Integer division) throws SQLException{
        //get current user
        String currentUser = "Test";

        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        String newQuery = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Created_By, Last_Updated_By, Last_Update, Create_Date) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postCode);
        ps.setString(4, phone);
        ps.setInt(5, division);
        ps.setString(6, currentUser);
        ps.setString(7, currentUser);
        ps.setString(8, ZonedDateTime.now(ZoneOffset.UTC).format(dtFormat).toString());
        ps.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(dtFormat).toString());

        try{
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static void deleteCustomer(Integer customerId) throws SQLException{

        try {
            String delAppt = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement s1 = DatabaseConnection.getConnection().prepareStatement(delAppt);
            s1.setInt(1, customerId);
            s1.executeUpdate();
            s1.close();
            String delCust = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement s2 = DatabaseConnection.getConnection().prepareStatement(delCust);
            s2.setInt(1, customerId);
            s2.executeUpdate();
            s2.close();
        } catch (SQLException e) {
            e.printStackTrace();
           }
    }

    public static void updateCustomer(Integer customerId, String name, String address, String postCode, String phone, String division) throws SQLException {
        String currentUser = "Test";
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String newUpdate = "Update customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update = ?, Last_Update_By = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newUpdate);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postCode);
        ps.setString(4, phone);
        ps.setInt(5, CustomerDA.getDivisionID(division));
        ps.setString(6, ZonedDateTime.now(ZoneOffset.UTC).format(dtFormat).toString());
        ps.setString(7, DatabaseConnection.getUserName(DatabaseConnection.getUserId()));
        ps.setString(8, customerId.toString());

        try {
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
