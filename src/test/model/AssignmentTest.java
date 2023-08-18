package model;

import model.exceptions.InvalidDate;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AssignmentTest {

    Assignment testAssignment;
    Assignment anotherTestAssignment;

    @BeforeEach
    void setup() throws InvalidDate {
        testAssignment = new Assignment("pre-lecture ticket", "CPSC 210",
                new Date(25, 10, 2022), new Time(10, 0, true),
                new Time(0, 10, false));

        anotherTestAssignment = new Assignment("WebWork", "MATH 200",
                new Date(28, 10, 2022), new Time(11, 59, false),
                new Time(4, 30, false));
    }

    @Test
    void testCompleteAssignment() {
        assertFalse(testAssignment.checkIfCompleted());
        testAssignment.completeAssignment();
        assertTrue(testAssignment.checkIfCompleted());
    }

    @Test
    void testGetAssignmentName() {
        assertEquals("pre-lecture ticket", testAssignment.getAssignmentName());
    }

    @Test
    void testGetCourse() {
        assertEquals("CPSC 210", testAssignment.getCourse());
    }

    @Test
    void testGetCompletionTime() {
        assertEquals( 0.16, testAssignment.getCompletionTime().getTotalHours());
        assertEquals(4.5, anotherTestAssignment.getCompletionTime().getTotalHours());
    }

    @Test
    void testGetDueDate() {
        assertEquals("25/10/2022", testAssignment.getDueDate().getDate());
    }

    @Test
    void testGetDueTime() {
        assertEquals("10:00 AM", testAssignment.getDueTime().getTime());
    }

    @Test
    void testHasGreaterDueDate() {
        assertFalse(testAssignment.hasGreaterDueDate(anotherTestAssignment));
        assertTrue(anotherTestAssignment.hasGreaterDueDate(testAssignment));
    }

    @Test
    void testConvertToJson() {
        assertEquals("{\"completionTime\":{\"hour\":4,\"am\":false,\"minute\":30},"
                + "\"dueDate\":{\"month\":10,\"year\":2022,\"day\":28},\"name\":\"WebWork\","
                + "\"course\":\"MATH 200\",\"completed\":false,\""
                + "dueTime\":{\"hour\":11,\"am\":false,\"minute\":59}}",
                anotherTestAssignment.convertToJson().toString());
    }

}
