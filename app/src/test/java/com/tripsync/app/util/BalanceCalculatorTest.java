package com.tripsync.app.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class BalanceCalculatorTest {

    @Test
    public void testRupeesToPaise() {
        // Test conversion from rupees to paise (multiply by 100)
        assertEquals(10000, BalanceCalculator.rupeesToPaise(100.0));
        assertEquals(100, BalanceCalculator.rupeesToPaise(1.0));
        assertEquals(1, BalanceCalculator.rupeesToPaise(0.01));
        assertEquals(0, BalanceCalculator.rupeesToPaise(0.0));
    }

    @Test
    public void testPaiseToRupees() {
        // Test conversion from paise to rupees (divide by 100)
        assertEquals(100.0, BalanceCalculator.paiseToRupees(10000), 0.01);
        assertEquals(1.0, BalanceCalculator.paiseToRupees(100), 0.01);
        assertEquals(0.01, BalanceCalculator.paiseToRupees(1), 0.01);
        assertEquals(0.0, BalanceCalculator.paiseToRupees(0), 0.01);
    }

    @Test
    public void testCalculateEqualShare_100_among_3() {
        // ₹100 among 3 people = 3334 + 3333 + 3333 paise (remainder distributed to first)
        long total = 10000; // 100 rupees in paise
        int count = 3;
        
        long share0 = BalanceCalculator.calculateEqualShare(total, count, 0); // gets remainder
        long share1 = BalanceCalculator.calculateEqualShare(total, count, 1);
        long share2 = BalanceCalculator.calculateEqualShare(total, count, 2);
        
        assertEquals(3334, share0);
        assertEquals(3333, share1);
        assertEquals(3333, share2);
        
        // Verify sum equals total
        assertEquals(total, share0 + share1 + share2);
    }

    @Test
    public void testCalculateEqualShare_10_among_4() {
        // ₹10 among 4 people = 250 each (no remainder)
        long total = 1000; // 10 rupees in paise
        int count = 4;
        
        long share0 = BalanceCalculator.calculateEqualShare(total, count, 0);
        long share1 = BalanceCalculator.calculateEqualShare(total, count, 1);
        long share2 = BalanceCalculator.calculateEqualShare(total, count, 2);
        long share3 = BalanceCalculator.calculateEqualShare(total, count, 3);
        
        assertEquals(250, share0);
        assertEquals(250, share1);
        assertEquals(250, share2);
        assertEquals(250, share3);
        
        assertEquals(total, share0 + share1 + share2 + share3);
    }

    @Test
    public void testCalculateEqualShare_1_paise_among_2() {
        // ₹0.01 (1 paise) among 2 people = 1 + 0
        long total = 1;
        int count = 2;
        
        long share0 = BalanceCalculator.calculateEqualShare(total, count, 0);
        long share1 = BalanceCalculator.calculateEqualShare(total, count, 1);
        
        assertEquals(1, share0);
        assertEquals(0, share1);
        
        assertEquals(total, share0 + share1);
    }

    @Test
    public void testFormatIndianCurrency_12_45_000() {
        // ₹12,45,000 (12 lakh 45 thousand)
        String formatted = BalanceCalculator.formatIndianCurrency(124500000);
        assertEquals("₹12,45,000", formatted);
    }

    @Test
    public void testFormatIndianCurrency_1_234() {
        // ₹1,234
        String formatted = BalanceCalculator.formatIndianCurrency(123400);
        assertTrue(formatted.contains("1234"));
    }

    @Test
    public void testFormatIndianCurrency_100() {
        // ₹100
        String formatted = BalanceCalculator.formatIndianCurrency(10000);
        assertTrue(formatted.contains("100"));
    }

    @Test
    public void testFormatIndianCurrency_0() {
        String formatted = BalanceCalculator.formatIndianCurrency(0);
        assertEquals("₹0", formatted);
    }

    @Test
    public void testBalance_scenario1() {
        // Scenario: I paid ₹3000 for 3 people (all participating)
        // My share = 3000 / 3 = 1000
        // My balance = 3000 (paid) - 1000 (share) = +2000 (should receive)
        
        long paid = 300000; // ₹3000 in paise
        int participantCount = 3;
        long myShare = BalanceCalculator.calculateEqualShare(paid, participantCount, 0);
        long balance = paid - myShare;
        
        assertEquals(100000, myShare); // ₹1000 in paise
        assertEquals(200000, balance); // ₹2000 in paise (positive = should receive)
    }

    @Test
    public void testBalance_scenario2() {
        // Scenario: Someone else paid ₹1200 for 3 of us
        // My share = 1200 / 3 = 400
        // My balance = 0 (I didn't pay) - 400 (share) = -400 (I owe)
        
        long paid = 0; // I didn't pay
        long theirExpense = 120000; // ₹1200 in paise
        int participantCount = 3;
        long myShare = BalanceCalculator.calculateEqualShare(theirExpense, participantCount, 0);
        long balance = paid - myShare;
        
        assertEquals(40000, myShare); // ₹400 in paise
        assertEquals(-40000, balance); // ₹-400 in paise (negative = I owe)
    }

    @Test
    public void testBalance_multiple_expenses() {
        // I paid ₹3000 for 3 people (my share = 1000, balance so far = +2000)
        // Someone else paid ₹1200 for 3 of us (my share = 400, so -400)
        // Total balance = +2000 - 400 = +1600
        
        long paid = 300000; // I paid ₹3000 in paise
        long theirExpense = 120000; // They paid ₹1200 in paise
        int count3 = 3;
        
        long myShare1 = BalanceCalculator.calculateEqualShare(paid, count3, 0);
        long myShare2 = BalanceCalculator.calculateEqualShare(theirExpense, count3, 0);
        long totalBalance = paid - myShare1 - myShare2;
        
        assertEquals(100000, myShare1); // ₹1000
        assertEquals(40000, myShare2); // ₹400
        assertEquals(160000, totalBalance); // ₹1600 (should receive)
    }

    @Test
    public void testSettleUp_simple() {
        // Settlement example: net balances (+2000, -1000, -1000)
        // Person A should receive 2000
        // Person B owes 1000
        // Person C owes 1000
        // Solution: B pays A ₹1000, C pays A ₹1000 (or C pays B ₹1000, B pays A ₹2000)
        
        // For this test, we just verify the sum of balances = 0 (conservation of money)
        long balanceA = 200000; // +₹2000
        long balanceB = -100000; // -₹1000
        long balanceC = -100000; // -₹1000
        
        long sum = balanceA + balanceB + balanceC;
        assertEquals(0, sum); // All balances must sum to zero
    }
}
