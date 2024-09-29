package com.example.bank;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private double balance;
    private final String accountNumber;
    private final List<Transaction> transactions;
    private static final double DAILY_WITHDRAWAL_LIMIT = 1000;
    private double todayWithdrawals;

    public BankAccount(String accountNumber, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.todayWithdrawals = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        addTransaction(TransactionType.DEPOSIT, amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        if (todayWithdrawals + amount > DAILY_WITHDRAWAL_LIMIT) {
            throw new IllegalArgumentException("Daily withdrawal limit exceeded");
        }
        balance -= amount;
        todayWithdrawals += amount;
        addTransaction(TransactionType.WITHDRAWAL, amount);
    }

    public void transfer(BankAccount recipient, double amount) {
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient account cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        if (todayWithdrawals + amount > DAILY_WITHDRAWAL_LIMIT) {
            throw new IllegalArgumentException("Daily withdrawal limit exceeded");
        }
        balance -= amount;
        todayWithdrawals += amount;
        recipient.balance += amount;
        addTransaction(TransactionType.TRANSFER, amount, recipient.getAccountNumber());
    }

    private void addTransaction(TransactionType type, double amount) {
        addTransaction(type, amount, null);
    }

    private void addTransaction(TransactionType type, double amount, String recipientAccount) {
        transactions.add(new Transaction(type, amount, recipientAccount));
    }

    public List<String> getTransactionHistory() {
        List<String> history = new ArrayList<>();
        for (Transaction transaction : transactions) {
            history.add(transaction.toString());
        }
        return history;
    }

    public void resetDailyWithdrawalLimit() {
        todayWithdrawals = 0;
    }

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    public static class Transaction {
        private final TransactionType type;
        private final double amount;
        private final String recipientAccount;

        Transaction(TransactionType type, double amount, String recipientAccount) {
            this.type = type;
            this.amount = amount;
            this.recipientAccount = recipientAccount;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "type=" + type +
                    ", amount=" + amount +
                    (recipientAccount != null ? ", recipientAccount='" + recipientAccount + '\'' : "") +
                    '}';
        }
    }
}