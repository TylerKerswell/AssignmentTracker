package model;

import org.json.*;

import java.util.*;

// class that stores the list of users assignments as well as orders them
public class AssignmentList {

    private List<Assignment> list;

    // MODIFIES: this
    // EFFECTS: creates an empty list of assignments
    public AssignmentList() {
        list = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the assignment to the assignment list
    public void addAssignment(Assignment newAssignment) {
        list.add(newAssignment);
        this.sortByDue();
        EventLog.getInstance().logEvent(new Event(
                "Assignment \"" + newAssignment.getAssignmentName() + "\" has been added."));
    }

    // REQUIRES: an unempty assignment list
    // EFFECTS: returns the uncompleted assignment which is due next
    public Assignment dueNext() {
        this.sortByDue();

        for (Assignment assignment : list) {
            if (!assignment.checkIfCompleted()) {
                return assignment;
            }
        }
        return list.get(0);
    }

    // EFFECTS: returns a formatted string of the list of assignments to be printed to the screen.
    public String getList() {

        this.sortByDue();

        StringBuilder finalStr;

        finalStr = new StringBuilder(String.format("|%1$-15.15s|%2$-20.20s|%3$-10.10s|%4$-20.20s|ASSIGNMENT NAME\n",
                "COURSE", "DUE DATE", "TIME DUE", "HOURS TO COMPLETE"));

        for (Assignment assignment : list) {
            if (!assignment.checkIfCompleted()) {
                finalStr.append(String.format("|%1$-15.15s|%2$-20.20s|%3$-10.10s|%4$-20.20s|%5$s\n",
                        assignment.getCourse(), assignment.getDueDate().verboseDate(),
                        assignment.getDueTime().getTime(),
                        assignment.getCompletionTime().getTotalHours(), assignment.getAssignmentName()));
            }
        }
        return finalStr.toString();
    }

    // MODIFIES: this
    // EFFECTS: completes the assignment with the passedthrough name
    public boolean completeAssignment(String name) {
        for (Assignment assignment : list) {
            if (Objects.equals(assignment.getAssignmentName().toLowerCase(), name.toLowerCase())) {
                assignment.completeAssignment();
                return true;
            }
        }
        return false;
    }
/*
    // EFFECTS: returns a string of completed assignments to be printed
    public String getCompletedList() {
        this.sortByDue();

        StringBuilder finalStr;

        finalStr = new StringBuilder(String.format("|%1$-15.15s|%2$-20.20s|%3$-10.10s|%4$-20.20s|ASSIGNMENT NAME\n\n",
                "COURSE", "DUE DATE", "TIME DUE", "HOURS TO COMPLETE"));

        for (Assignment assignment : list) {
            if (assignment.checkIfCompleted()) {
                finalStr.append(String.format("|%1$-15.15s|%2$-20.20s|%3$-10.10s|%4$-15.15s|%5$s\n",
                        assignment.getCourse(), assignment.getDueDate().verboseDate(),
                        assignment.getDueTime().getTime(),
                        assignment.getCompletionTime().getTotalHours(), assignment.getAssignmentName()));
            }
        }
        return finalStr.toString();
    }*/

    // EFFECTS: gets the number of uncompleted assignments
    public int getLength() {
        int len = 0;
        for (Assignment assignment : list) {
            if (!assignment.checkIfCompleted()) {
                len++;
            }
        }
        return len;
    }

    // EFFECTS: gets the number of total assignments (completed/uncompleted)
    public int getTotalLength() {
        return list.size();
    }


    // REQUIRES: a non-empty assignment list
    // MODIFIES: this
    // EFFECTS: sorts the list with the next due date first using selection sort algorithm
    private void sortByDue() {

        for (int i = 0; i < list.size() - 1; i++) {
            int smallestDate = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(smallestDate).hasGreaterDueDate(list.get(j))) {
                    smallestDate = j;
                }
            }

            Assignment tmp = list.get(i);

            list.set(i, list.get(smallestDate));

            list.set(smallestDate, tmp);
        }
    }

    // EFFECTS: creates a JSONObject and stores the assignmentList inside of it,
    // with each assignment being an index in an array
    public JSONObject convertToJson() {
        JSONArray output = new JSONArray();
        for (Assignment assignment : list) {
            output.put(assignment.convertToJson());
        }
        return new JSONObject().put("assignments", output);
    }

    // MODIFIES: this
    // EFFECTS: deletes all assignments in the list
    public void clear() {
        list = new ArrayList<>();
    }

    // REQUIRES: 0 <= index <= totalLength
    // EFFECTS: returns the assignment located at the given index of the list
    public Assignment getAssignment(int index) {
        return list.get(index);
    }
}
