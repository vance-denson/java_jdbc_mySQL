package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ04seK";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    // Driver and Connection Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn  = null;

    public static final String username = "U04seK";
    public static final String password = "53688333534";

//    private String user = "U04sek";
//    private String userId = "53688333534";
//
//    public static boolean validateUser(String user, String pass){
//        return true;
//    }

    public static Connection startConnection(String username, String password)
    {
        try
        {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection success.");
        }
        catch (ClassNotFoundException e)
        {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection()
    {
        return conn;
    }

    public static void closeConnection()
    {
        try
        {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e)
        {
            //do nothing
        }

    }

    public static Integer getUserId() {
        Integer userID = null;
        try {
            // stubbed
            userID = 1;

        } catch (Error e) {
            e.printStackTrace();
        }
        return userID;
    }

    public static String getUserName(Integer userId) {
        String userName = null;
        try {
            // stubbed
            userName = "Vance";

        } catch (Error e) {
            e.printStackTrace();
        }
        return userName;
    }

}