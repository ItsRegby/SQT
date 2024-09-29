package com.example.bank;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("123456", 100);
        System.out.println("Initial balance: " + account.getBalance());
        account.deposit(50);
        System.out.println("Balance after deposit: " + account.getBalance());
        account.withdraw(20);
        System.out.println("Balance after withdrawal: " + account.getBalance());

        BankAccount recipient = new BankAccount("654321", 200);
        account.transfer(recipient, 30);
        System.out.println("Balance after transfer: " + account.getBalance());
        System.out.println("Recipient balance after transfer: " + recipient.getBalance());

        account.resetDailyWithdrawalLimit();
        System.out.println("Balance after resetting daily withdrawal limit: " + account.getBalance());

        account.withdraw(100);
        System.out.println("Balance after withdrawal: " + account.getBalance());

        account.withdraw(1000);
        System.out.println("Balance after withdrawal: " + account.getBalance());
    }
}