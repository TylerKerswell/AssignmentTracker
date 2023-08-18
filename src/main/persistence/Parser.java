package persistence;

import model.*;
import model.exceptions.InvalidDate;
import org.json.*;

// class to parse an entire assignment list from a jason object input, it parses each datatype individually.
// credit to CPSC210 / JsonSerializationDemo for a bit of inspiration in making this
public class Parser {

    //EFFECTS: creates and returns an assignmentList from a JSONObject as input
    public static void parseAssignmentList(JSONObject input, AssignmentList list) throws InvalidDate {
        list.clear();
        JSONArray assignments = input.getJSONArray("assignments");
        parseAssignments(assignments, list);
    }

    // MODIFIES: output
    // EFFECTS: parses the inputted JSON array and stores the assignments into the inputted assignment list
    public static void parseAssignments(JSONArray input, AssignmentList output) throws InvalidDate {
        for (int i = 0; i < input.length(); i++) {
            JSONObject assignment = input.getJSONObject(i);
            output.addAssignment(new Assignment(assignment.getString("name"),
                    assignment.getString("course"), parseDate(assignment.getJSONObject("dueDate")),
                    parseTime(assignment.getJSONObject("dueTime")),
                    parseTime(assignment.getJSONObject("completionTime")),
                    assignment.getBoolean("completed")));
        }
    }

    // EFFECTS: parses date from a JSON object
    public static Date parseDate(JSONObject input) throws InvalidDate {
        return new Date(input.getInt("day"), input.getInt("month"), input.getInt("year"));
    }

    // EFFECTS: parses time from a JSON object
    public static Time parseTime(JSONObject input) {
        return new Time(input.getInt("hour"), input.getInt("minute"), input.getBoolean("am"));
    }

}
