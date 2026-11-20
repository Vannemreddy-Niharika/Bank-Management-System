
package com.app.bank;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank bank = new Bank();

        int choice;

        do {

            System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Opening Balance: ");
                    double amount = sc.nextDouble();

                    bank.createAccount(name, amount);

                    break;

                case 2:

                    System.out.print("Enter Account Number: ");
                    int depositAcc = sc.nextInt();

                    System.out.print("Enter Deposit Amount: ");
                    double depositAmount = sc.nextDouble();

                    bank.depositMoney(depositAcc, depositAmount);

                    break;

                case 3:

                    System.out.print("Enter Account Number: ");
                    int withdrawAcc = sc.nextInt();

                    System.out.print("Enter Withdrawal Amount: ");
                    double withdrawAmount = sc.nextDouble();

                    bank.withdrawMoney(withdrawAcc, withdrawAmount);

                    break;

                case 4:

                    System.out.print("Enter Account Number: ");
                    int balanceAcc = sc.nextInt();

                    bank.checkBalance(balanceAcc);

                    break;

                case 5:

                    System.out.print("Enter Account Number: ");
                    int historyAcc = sc.nextInt();

                    bank.showTransactions(historyAcc);

                    break;

                case 6:

                    System.out.println("Thank you for using our bank!");

                    break;

                default:

                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
