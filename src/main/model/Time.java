package model;

import org.json.JSONObject;

// class used to represent a static time with hours and minutes, to be used for due dates of assignments
public class Time {
    private int mins;
    private int hrs;
    private final boolean am;

    // MODIFIES: this
    // EFFECTS: creates a time with the given hour, minute, and whether its in AM
    public Time(int hours, int minutes, boolean am) {
        this.am = am;
        hrs = hours;
        mins = minutes;
        while (mins >= 60) {
            hrs++;
            mins -= 60;
        }
    }

    public int getHours() {
        return hrs;
    }

    public int getMinutes() {
        return mins;
    }

    public boolean getAm() {
        return am;
    }

    // EFFECTS: returns the total amount hours (including minutes) of the time to two decimal places
    public double getTotalHours() {
        return (double) (int) (((double) hrs + ((double) mins / 60)) * 100) / 100;
    }


    // EFFECTS: gets a string with the format HR:MIN AM/PM
    public String getTime() {
        String tmp = new String();
        tmp = (hrs < 10 ? "0" : "") + hrs + ":" + (mins < 10 ? "0" : "") + mins + " " + (am ? "AM" : "PM");
        return tmp;
    }

    // REQUIRES: other time to be valid
    // EFFECTS: returns true if this is at a later time than the passedthrough time, returns false otherwise
    public boolean biggerThan(Time otherTime) {
        if (this.am != otherTime.am) {
            if (this.am) {
                return false;
            }
            return true;
        } else if (hrs != otherTime.hrs) {
            return hrs > otherTime.hrs;
        }
        return mins > otherTime.mins;
    }

    // EFFECTS: converts the time object into a json object
    public JSONObject convertToJson() {
        JSONObject output = new JSONObject();
        output.put("hour", hrs);
        output.put("minute", mins);
        output.put("am", am);
        return output;
    }

}
