package model;

import model.exceptions.InvalidDate;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AssignmentListTest {

    AssignmentList testList;
    Assignment testAssignment;
    Assignment secondTestAssignment;

    @BeforeEach
    void setup() throws InvalidDate {
        testList = new AssignmentList();
        testAssignment = new Assignment("A1", "CPSC 210",
                new Date(25, 10, 2022), new Time(10, 0, true),
                new Time(0, 30, false));

        secondTestAssignment = new Assignment("A2", "CPSC 210",
                new Date(26, 10, 2022), new Time(10, 30, false),
                new Time(5, 0, false));

        testList.addAssignment(testAssignment);

    }

    @Test
    void testAddAssignment() {
        assertEquals(1, testList.getLength());
        testList.addAssignment(secondTestAssignment);
        assertEquals(2, testList.getLength());

    }

    @Test
    void testGetList() {
        assertEquals("|COURSE         |DUE DATE            |TIME DUE  |HOURS TO COMPLETE   |ASSIGNMENT NAME\n"
                + "|CPSC 210       |October 25th        |10:00 AM  |0.5                 |A1\n",
                testList.getList());
    }

    @Test
    void testDueNext() {
        testList.addAssignment(secondTestAssignment);
        assertEquals("A1", testList.dueNext().getAssignmentName());
    }

    @Test
    void testCompleteAssignment() {
        testList.addAssignment(secondTestAssignment);
        assertEquals(2, testList.getLength());
        testList.completeAssignment("A1");
        assertEquals(1, testList.getLength());
        assertEquals(2, testList.getTotalLength());
    }

    @Test
    void testConvertToJson() {
        assertEquals("{\"assignments\":[{\"completionTime\":{\"hour\":0,\"am\":false,\"minute\":30},"
                + "\"dueDate\":{\"month\":10,\"year\":2022,\"day\":25},"
                + "\"name\":\"A1\",\"course\":\"CPSC 210\",\"completed\":false,"
                + "\"dueTime\":{\"hour\":10,\"am\":true,\"minute\":0}}]}",
                testList.convertToJson().toString());
    }
}
