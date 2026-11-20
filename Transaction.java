
package com.app.bank;

import java.time.LocalDateTime;

public class Transaction {

    int accountNumber;
    String type;
    double amount;
    LocalDateTime dateTime;

    public Transaction(int accountNumber, String type, double amount) {

        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public void displayTransaction() {

        System.out.println("Account: " + accountNumber);
        System.out.println("Type: " + type);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + dateTime);

        System.out.println("------------------------");
    }
}
