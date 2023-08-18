package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTest {

    Time testTime;
    Time testMoreTime;

    @BeforeEach
    void setup() {
        testTime = new Time(11, 59, false);
        testMoreTime = new Time(2, 63, true);
    }

    @Test
    void testGetHours() {
        assertEquals(11, testTime.getHours());
        assertEquals(3,testMoreTime.getHours());
    }

    @Test
    void testGetMinutes() {
        assertEquals(59, testTime.getMinutes());
        assertEquals(3, testMoreTime.getMinutes());

    }

    @Test
    void testGetAM() {

        assertFalse(testTime.getAm());
        assertTrue(testMoreTime.getAm());
    }

    @Test
    void testGetTime() {

        assertEquals("11:59 PM", testTime.getTime());
        assertEquals("03:03 AM", testMoreTime.getTime());

    }

    @Test
    void testGetTotalHours() {
        testTime = new Time(2, 30, false);
        assertEquals(2.5, testTime.getTotalHours());
    }

    @Test
    void testBiggerThan() {
        assertTrue(testTime.biggerThan(testMoreTime));
        assertFalse(testMoreTime.biggerThan(testTime));
        Time moreTime = new Time(11, 58, false);
        assertTrue(testTime.biggerThan(moreTime));

    }

    @Test
    void testConvertToJson() {
        assertEquals("{\"hour\":11,\"am\":false,\"minute\":59}", testTime.convertToJson().toString());
    }


}
