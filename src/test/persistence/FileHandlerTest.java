package persistence;

import model.*;
import model.exceptions.InvalidDate;
import org.json.*;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    AssignmentList test;
    FileHandler tester;

    @BeforeEach
    void setup() throws InvalidDate, IOException {
        test = new AssignmentList();
        test.addAssignment(new Assignment("WEBWORK", "MATH 200", new Date(30, 10, 2022),
                new Time(11, 59, false), new Time(3, 30, false)));
        tester = new FileHandler("test.json");
    }

    @Test
    void testReadJson() throws IOException {
        assertEquals(test.convertToJson().toString(), tester.readJson().toString());
    }

    @Test
    void testWriteJson() throws IOException {
        tester.writeJson(test.convertToJson());
        assertEquals(tester.readJson().toString(), test.convertToJson().toString());
    }

}
