
package com.app.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database URL
    private static final String URL =
        "jdbc:mysql://localhost:3306/bank_management";

    // MySQL username
    private static final String USER = "****";

    // MySQL password
    private static final String PASSWORD =
        "********";

    // Method to connect Java with MySQL
    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
            URL, USER, PASSWORD
        );
    }
}
