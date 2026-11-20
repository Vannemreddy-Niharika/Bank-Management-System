
package com.app.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bank {

    // 1. Create Account
    public void createAccount(String name, double amount) {

        if (amount < 0 || name.trim().isEmpty()) {
            System.out.println("Invalid name or opening balance!");
            return;
        }

        String sql = "INSERT INTO accounts (customer_name, balance) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                System.out.println("Account created successfully!");
                System.out.println("Your Account Number: " + rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("Account creation failed!");
            e.printStackTrace();
        }
    }

    // 2. Deposit Money
    public void depositMoney(int accNo, double amount) {

        if (amount <= 0) {
            System.out.println("Enter a valid amount!");
            return;
        }

        String update = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String insert = "INSERT INTO transactions (account_number, transaction_type, amount) VALUES (?, 'Deposit', ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(update)) {
                ps.setDouble(1, amount);
                ps.setInt(2, accNo);

                if (ps.executeUpdate() == 0) {
                    con.rollback();
                    System.out.println("Account not found!");
                    return;
                }
            }

            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setInt(1, accNo);
                ps.setDouble(2, amount);
                ps.executeUpdate();
            }

            con.commit();
            System.out.println("Money deposited successfully!");

        } catch (SQLException e) {
            System.out.println("Deposit failed!");
            e.printStackTrace();
        }
    }

    // 3. Withdraw Money
    public void withdrawMoney(int accNo, double amount) {

        if (amount <= 0) {
            System.out.println("Enter a valid amount!");
            return;
        }

        String update = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        String insert = "INSERT INTO transactions (account_number, transaction_type, amount) VALUES (?, 'Withdrawal', ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(update)) {
                ps.setDouble(1, amount);
                ps.setInt(2, accNo);
                ps.setDouble(3, amount);

                if (ps.executeUpdate() == 0) {
                    con.rollback();
                    System.out.println("Account not found or insufficient balance!");
                    return;
                }
            }

            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setInt(1, accNo);
                ps.setDouble(2, amount);
                ps.executeUpdate();
            }

            con.commit();
            System.out.println("Money withdrawn successfully!");

        } catch (SQLException e) {
            System.out.println("Withdrawal failed!");
            e.printStackTrace();
        }
    }

    // 4. Check Balance
    public void checkBalance(int accNo) {

        String sql = "SELECT customer_name, balance FROM accounts WHERE account_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Customer: " + rs.getString("customer_name"));
                System.out.println("Current Balance: Rs. " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. View Transaction History
    public void showTransactions(int accNo) {

        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("Transaction ID: " + rs.getInt("transaction_id"));
                System.out.println("Type: " + rs.getString("transaction_type"));
                System.out.println("Amount: Rs. " + rs.getDouble("amount"));
                System.out.println("Date: " + rs.getTimestamp("transaction_date"));
                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("No transactions found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
