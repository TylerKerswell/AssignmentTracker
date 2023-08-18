package model;

import org.json.JSONObject;

// main class that represents a single assignment, with time it takes to complete, course name, assignment name,
// due date, time that it is due, and whether or not its completed
public class Assignment {

    private final Time completionTime;
    private final String course;
    private final String assignmentName;
    private final Date dueDate;
    private final Time dueTime;
    private boolean completed;
    
    // MODIFIES: this
    // EFFECTS: creates an assignment with all the passed parameters
    public Assignment(String name, String course, Date dueD, Time dueT, Time timeToComplete) {
        assignmentName = name;
        this.course = course;
        dueDate = dueD;
        dueTime = dueT;
        completionTime = timeToComplete;
        completed = false;

    }

    // MODIFIES: this
    // EFFECTS: creates an assignment with all the passed parameters, this one has completed (for persistence)
    public Assignment(String name, String course, Date dueD, Time dueT, Time timeToComplete, boolean completed) {
        assignmentName = name;
        this.course = course;
        dueDate = dueD;
        dueTime = dueT;
        completionTime = timeToComplete;
        this.completed = completed;

    }

    public void completeAssignment() {
        completed = true;
    }

    public boolean checkIfCompleted() {
        return completed;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getCourse() {
        return course;
    }

    public Time getCompletionTime() {
        return completionTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }


    // EFFECTS: returns true if this assignment has a greater due date than the passed assignment, false otherwise
    public boolean hasGreaterDueDate(Assignment compare) {
        if (dueDate.biggerThan(compare.dueDate)) {
            return true;
        } else if (compare.dueDate.biggerThan(dueDate)) {
            return false;
        } else {
            return dueTime.biggerThan(compare.dueTime);
        }
    }

    // EFFECTS: returns a JSONObject with this objects data stored inside
    public JSONObject convertToJson() {
        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("name", assignmentName);
        jsonOutput.put("course", course);
        jsonOutput.put("completed", completed);
        jsonOutput.put("dueDate", dueDate.convertToJson());
        jsonOutput.put("dueTime", dueTime.convertToJson());
        jsonOutput.put("completionTime", completionTime.convertToJson());
        return jsonOutput;
    }
}
