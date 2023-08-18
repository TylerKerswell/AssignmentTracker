package persistence;

import model.*;
import model.exceptions.InvalidDate;
import org.json.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static persistence.Parser.*;

public class ParserTest {

    @Test
    void testParseAssignmentList() throws InvalidDate {
        AssignmentList testList = new AssignmentList();
        testList.addAssignment(new Assignment("pre-lecture ticket", "CPSC 210",
                new Date(25, 10, 2022), new Time(10, 0, true),
                new Time(0, 10, false)));

        JSONObject jsonAssignmentList = testList.convertToJson();
        AssignmentList parsedList = new AssignmentList();
        parseAssignmentList(jsonAssignmentList, parsedList);
        assertEquals(parsedList.getList(), testList.getList());
    }

    @Test
    void testParseDate() throws InvalidDate {
        Date testDate = new Date(25,10,2022);
        JSONObject jsonDate = testDate.convertToJson();
        Date parcedDate = parseDate(jsonDate);
        assertEquals(parcedDate.getDate(), parcedDate.getDate());
    }

    @Test
    void testParseTime() {
        Time testTime = new Time(1,30,false);
        JSONObject jsonTime = testTime.convertToJson();
        Time parcedTime = parseTime(jsonTime);
        assertEquals(parcedTime.getTime(), testTime.getTime());
    }

}
