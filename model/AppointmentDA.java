package com.model;

import com.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentDA {
    public static ObservableList<AppointmentModel> getAllAppointments(){

        ObservableList<AppointmentModel> appointments = FXCollections.observableArrayList();

        try{
            String newQuery = "SELECT * FROM appointments";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Integer id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                Integer contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                Integer customerId = rs.getInt("Customer_ID");
                Integer userId = rs.getInt("User_ID");
                ZonedDateTime zonedStart = startDate.toLocalDateTime().atZone(ZoneId.systemDefault());
                ZonedDateTime zonedEnd = endDate.toLocalDateTime().atZone(ZoneId.systemDefault());
                AppointmentModel appointmentModel = new AppointmentModel(id, title, description, location, contactId, type, zonedStart, zonedEnd, customerId, userId);
                appointments.add(appointmentModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;

    }

    public static ObservableList<String> getContacts(){

        ObservableList<String> contacts = FXCollections.observableArrayList();

        try{
            String newQuery = "SELECT DISTINCT Contact_Name, Contact_ID FROM contacts";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
            ResultSet rs = ps.executeQuery();
            contacts.removeAll();
            while(rs.next()) {
                contacts.add(rs.getString("Contact_ID") + ", " + rs.getString("Contact_Name"));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;

    }

    public static Boolean addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer contactId) throws SQLException{
        //get current user
        String currentUser = "Test";
        Integer userId = DatabaseConnection.getUserId();

        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        ZonedDateTime aptStartDate = ZonedDateTime.of(start, ZoneOffset.UTC);
        ZonedDateTime aptEndDate = ZonedDateTime.of(end, ZoneOffset.UTC);


        String newQuery = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newQuery);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, aptStartDate.format(dtFormat));
        ps.setString(6, aptEndDate.format(dtFormat));
        ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(dtFormat).toString());
        ps.setString(8, DatabaseConnection.getUserName(userId));
        ps.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(dtFormat).toString());
        ps.setString(10, DatabaseConnection.getUserName(userId));
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);

        try{
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void deleteAppointment(Integer appointmentID) throws SQLException{

        try {
            String delAppt = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement s1 = DatabaseConnection.getConnection().prepareStatement(delAppt);
            s1.setInt(1, appointmentID);
            s1.executeUpdate();
            s1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAppointment(Integer aptId, String title, String description, String location, String type, Integer contactId, Integer customerId, LocalDate date, LocalDateTime start, LocalDateTime end, Integer userID) throws SQLException {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String startTime = ZonedDateTime.of(LocalDateTime.of(date, start.toLocalTime()), ZoneOffset.UTC).format(dtFormat);
        String endTime = ZonedDateTime.of(LocalDateTime.of(date, end.toLocalTime()), ZoneOffset.UTC).format(dtFormat);
        String updateTime = ZonedDateTime.now(ZoneOffset.UTC).format(dtFormat);

        String newUpdate = "Update appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, Contact_ID = ?  WHERE Appointment_ID = ?";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(newUpdate);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, startTime);
        ps.setString(6, endTime);
        ps.setString(7, updateTime);
        ps.setString(8, DatabaseConnection.getUserName(userID));
        ps.setInt(9, customerId);
        ps.setInt(10, contactId);
        ps.setInt(11, aptId);

        try {
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



}
