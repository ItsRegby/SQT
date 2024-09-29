package com.example.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("123456", 1000);
    }

    @Test
    void testCreateBankAccountWithValidInitialBalance() {
        assertEquals(1000, account.getBalance());
        assertEquals("123456", account.getAccountNumber());
    }

    @Test
    void testCreateBankAccountWithNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BankAccount("123456", -100);
        });
    }

    @Test
    void testDepositValidAmount() {
        account.deposit(50);
        assertEquals(1050, account.getBalance());
    }

    @Test
    void testDepositInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50);
        });
    }

    @Test
    void testWithdrawValidAmount() {
        account.withdraw(50);
        assertEquals(950, account.getBalance());
    }

    @Test
    void testWithdrawMoreThanBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(2000);
        });
    }

    @Test
    void testWithdrawInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-50);
        });
    }

    @Test
    void testDailyWithdrawalLimit() {
        account.withdraw(900);
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(200);
        });
    }

    @Test
    void testResetDailyWithdrawalLimit() {
        account.withdraw(900);
        account.resetDailyWithdrawalLimit();
        account.withdraw(100);
        assertEquals(0, account.getBalance());
    }

    @Test
    void testTransfer() {
        BankAccount recipient = new BankAccount("654321", 0);
        account.transfer(recipient, 50);
        assertEquals(950, account.getBalance());
        assertEquals(50, recipient.getBalance());
    }

    @Test
    void testTransferInsufficientFunds() {
        BankAccount recipient = new BankAccount("654321", 0);
        assertThrows(IllegalArgumentException.class, () -> {
            account.transfer(recipient, 1500);
        });
    }

    @Test
    void testTransactionHistory() {
        account.deposit(50);
        account.withdraw(25);
        BankAccount recipient = new BankAccount("654321", 0);
        account.transfer(recipient, 10);

        var transactions = account.getTransactionHistory();
        assertEquals(3, transactions.size());
        assertTrue(transactions.get(0).contains("DEPOSIT"));
        assertTrue(transactions.get(1).contains("WITHDRAWAL"));
        assertTrue(transactions.get(2).contains("TRANSFER"));
    }

    @Test
    void testLargeNumberOfTransactions() {
        for (int i = 0; i < 1000; i++) {
            account.deposit(1);
            account.withdraw(1);
        }
        assertEquals(1000, account.getBalance());
        assertEquals(2000, account.getTransactionHistory().size());
    }
}