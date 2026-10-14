package com.tripsync.app.util;

import java.math.BigDecimal;
import java.util.List;

public class BalanceCalculator {
    
    /**
     * Convert rupees (as double) to paise (as long).
     * Uses BigDecimal to avoid floating-point precision issues.
     */
    public static long rupeesToPaise(double rupees) {
        return BigDecimal.valueOf(rupees)
                .multiply(BigDecimal.valueOf(100))
                .longValue();
    }
    
    /**
     * Convert paise (as long) to rupees (as double).
     */
    public static double paiseToRupees(long paise) {
        return BigDecimal.valueOf(paise)
                .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP)
                .doubleValue();
    }
    
    /**
     * Calculate equal split of an amount among participants.
     * Distributes remainder paise one-by-one to first participants so total always equals amount.
     * 
     * Example: 10000 paise among 3 people = [3334, 3333, 3333]
     * 
     * @param totalPaise total amount in paise
     * @param participantCount number of participants
     * @param participantIndex index of this participant (0-based)
     * @return this participant's share in paise
     */
    public static long calculateEqualShare(long totalPaise, int participantCount, int participantIndex) {
        if (participantCount <= 0) return 0;
        
        long baseShare = totalPaise / participantCount;
        long remainder = totalPaise % participantCount;
        
        // Distribute remainder to first participants
        if (participantIndex < remainder) {
            return baseShare + 1;
        }
        return baseShare;
    }
    
    /**
     * Format paise as Indian currency string (e.g., "₹12,45,000").
     * Uses Indian numbering system (lakh, crore, etc.).
     */
    public static String formatIndianCurrency(long paise) {
        long rupees = paise / 100;
        long remainingPaise = paise % 100;
        
        String rupeeStr;
        if (rupees == 0) {
            rupeeStr = "0";
        } else if (rupees < 1000) {
            rupeeStr = String.valueOf(rupees);
        } else if (rupees < 100000) {
            // Format: 1,234
            rupeeStr = String.format("%,d", rupees).replace(",", ",");
        } else {
            // Format: 12,34,567 (lakh format)
            String fullStr = String.format("%010d", rupees);
            rupeeStr = fullStr.substring(0, fullStr.length() - 5) + "," +
                      fullStr.substring(fullStr.length() - 5, fullStr.length() - 2) + "," +
                      fullStr.substring(fullStr.length() - 2);
            rupeeStr = rupeeStr.replaceAll("^0+,", "").replaceAll("^0+", "");
        }
        
        if (remainingPaise == 0) {
            return "₹" + rupeeStr;
        } else {
            return String.format("₹%s.%02d", rupeeStr, remainingPaise);
        }
    }
    
    /**
     * Format balance with sign and caption.
     * Positive = green, Negative = red, Zero = neutral
     */
    public static class BalanceFormatted {
        public String amount;      // "₹620", "+ ₹620", "- ₹620"
        public String caption;     // "all settled", "you should receive", "you owe"
        public int color;          // R.color.success_green, R.color.error_red, etc.
        
        public BalanceFormatted(String amount, String caption, int color) {
            this.amount = amount;
            this.caption = caption;
            this.color = color;
        }
    }
    
    /**
     * Calculate your balance for the trip (what you paid - your share of expenses).
     * 
     * @param expensePaise expenses where you are the payer (List<Long>)
     * @param sharesPaise your shares in each expense (List<Long>)
     * @return positive = you should receive, negative = you owe
     */
    public static long calculateBalance(List<Long> expensePaise, List<Long> sharesPaise) {
        long paid = 0;
        for (Long amount : expensePaise) {
            paid += amount;
        }
        
        long share = 0;
        for (Long amount : sharesPaise) {
            share += amount;
        }
        
        return paid - share;
    }
}
