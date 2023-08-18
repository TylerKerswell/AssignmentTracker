package model;

import model.exceptions.InvalidDate;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DateTest {

    Date testDate;
    Date testAnotherDate;

    @BeforeEach
    void setup() throws InvalidDate {
        testDate = new Date(25, 6, 2022);
        testAnotherDate = new Date(4, 10, 2023);
    }

    @Test
    void testGetDay() {
        assertEquals(25, testDate.getDay());
    }

    @Test
    void testGetMonth() {
        assertEquals(6, testDate.getMonth());
    }

    @Test
    void testGetYear() {
        assertEquals(2022, testDate.getYear());
    }

    @Test
    void testGetDate() {
        assertEquals("25/6/2022", testDate.getDate());
    }

    @Test
    void testVerboseDate() throws InvalidDate {
        testDate = new Date(5, 1, 1);
        assertEquals("January 5th", testDate.verboseDate());
        testDate = new Date(5, 2, 1);
        assertEquals("February 5th", testDate.verboseDate());
        testDate = new Date(5, 3, 1);
        assertEquals("March 5th", testDate.verboseDate());
        testDate = new Date(5, 4, 1);
        assertEquals("April 5th", testDate.verboseDate());
        testDate = new Date(5, 5, 1);
        assertEquals("May 5th", testDate.verboseDate());
        testDate = new Date(5, 6, 1);
        assertEquals("June 5th", testDate.verboseDate());
        testDate = new Date(5, 7, 1);
        assertEquals("July 5th", testDate.verboseDate());
        testDate = new Date(5, 8, 1);
        assertEquals("August 5th", testDate.verboseDate());
        testDate = new Date(5, 9, 1);
        assertEquals("September 5th", testDate.verboseDate());
        testDate = new Date(5, 10, 1);
        assertEquals("October 5th", testDate.verboseDate());
        testDate = new Date(5, 11, 1);
        assertEquals("November 5th", testDate.verboseDate());
        testDate = new Date(5, 12, 1);
        assertEquals("December 5th", testDate.verboseDate());
    }

    @Test
    void testBiggerThan() throws InvalidDate {
        assertTrue(testAnotherDate.biggerThan(testDate));
        testAnotherDate = new Date(5, 7, 2022);
        assertTrue(testAnotherDate.biggerThan(testDate));
        testAnotherDate = new Date(5, 6, 2022);
        assertFalse(testAnotherDate.biggerThan(testDate));
    }

    @Test
    void testConvertToJson() {
        assertEquals("{\"month\":6,\"year\":2022,\"day\":25}", testDate.convertToJson().toString());
    }
}
