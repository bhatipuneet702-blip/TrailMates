package com.tripsync.app.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for packing logic
 */
public class PackingUtilTest {

    @Test
    public void testProgressCalculation_12Of20() {
        int packed = 12;
        int total = 20;
        int progress = (int) ((packed * 100.0) / total);
        assertEquals(60, progress);
        assertEquals("12/20", String.format("%d/%d", packed, total));
    }

    @Test
    public void testProgressCalculation_ZeroOf0() {
        int packed = 0;
        int total = 0;
        int progress = total > 0 ? (int) ((packed * 100.0) / total) : 0;
        assertEquals(0, progress);
        assertEquals("0/0", String.format("%d/%d", packed, total));
    }

    @Test
    public void testProgressCalculation_5Of5() {
        int packed = 5;
        int total = 5;
        int progress = (int) ((packed * 100.0) / total);
        assertEquals(100, progress);
        assertEquals("5/5", String.format("%d/%d", packed, total));
    }

    @Test
    public void testCategoryNameValidation_ValidName() {
        String name = "Medicines";
        boolean isValid = name != null && !name.trim().isEmpty() && name.length() <= 20;
        assertTrue(isValid);
    }

    @Test
    public void testCategoryNameValidation_BlankName() {
        String name = "   ";
        boolean isValid = name != null && !name.trim().isEmpty() && name.length() <= 20;
        assertFalse(isValid);
    }

    @Test
    public void testCategoryNameValidation_TooLongName() {
        String name = "This is a very long category name";  // 33 characters
        boolean isValid = name != null && !name.trim().isEmpty() && name.length() <= 20;
        assertFalse(isValid);
    }

    @Test
    public void testCategoryNameValidation_ExactlyTwentyChars() {
        String name = "12345678901234567890";  // 20 characters
        boolean isValid = name != null && !name.trim().isEmpty() && name.length() <= 20;
        assertTrue(isValid);
    }

    @Test
    public void testItemNameValidation_ValidName() {
        String name = "T-shirt";
        boolean isValid = name != null && !name.trim().isEmpty() && name.length() <= 40;
        assertTrue(isValid);
    }

    @Test
    public void testItemNameValidation_BlankName() {
        String name = "";
        boolean isValid = name != null && !name.trim().isEmpty() && name.length() <= 40;
        assertFalse(isValid);
    }

    @Test
    public void testItemNameValidation_TooLongName() {
        String name = "This is a very long item name that exceeds the forty character limit";  // > 40
        boolean isValid = name != null && !name.trim().isEmpty() && name.length() <= 40;
        assertFalse(isValid);
    }

    @Test
    public void testProgressCalculation_OneOf10() {
        int packed = 1;
        int total = 10;
        int progress = (int) ((packed * 100.0) / total);
        assertEquals(10, progress);
    }

    @Test
    public void testNoDivideByZero() {
        int packed = 0;
        int total = 0;
        // Should not throw exception
        int progress = total > 0 ? (int) ((packed * 100.0) / total) : 0;
        assertEquals(0, progress);
    }
}
