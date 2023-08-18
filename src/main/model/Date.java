package model;

import model.exceptions.*;
import org.json.JSONObject;

// class used to represent dates, with year, month, and day. Used for due dates of assignments.
public class Date {

    private int year;
    private int month;
    private int day;

    // REQUIRES: 0 < day < 32, 0 < month < 13
    // MODIFIES: this
    // EFFECTS: creates a day objeect with the day, month, year corresponding to the inputted integers
    // eg. 6/2/2023 for day/month/year
    public Date(int day, int month, int year) throws InvalidDate {
        this.day = day;
        this.month = month;
        this.year = year;

        if (day > 31 || day < 1) {
            throw new InvalidDate();
        }

        if (month > 12 || month < 1) {
            throw new InvalidDate();
        }

    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // EFFECTS: returns a string of the date in DD/MM/YYYY format
    public String getDate() {
        return day + "/" + month + "/" + year;
    }

    // EFFECTS: returns a verbose form of the date in the format MONTH DAY. With the month spelt out
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public String verboseDate() {
        String tmp = "";

        switch (month) {
            case 1:
                tmp = "January ";
                break;
            case 2:
                tmp = "February ";
                break;
            case 3:
                tmp = "March ";
                break;
            case 4:
                tmp = "April ";
                break;
            case 5:
                tmp = "May ";
                break;
            case 6:
                tmp = "June ";
                break;
            case 7:
                tmp = "July ";
                break;
            case 8:
                tmp = "August ";
                break;
            case 9:
                tmp = "September ";
                break;
            case 10:
                tmp = "October ";
                break;
            case 11:
                tmp = "November ";
                break;
            case 12:
                tmp = "December ";
                break;
        }

        tmp += day + "th";

        return tmp;

    }

    // EFFECTS: returns true if this is at a later date than otherDate, false otherwise
    public boolean biggerThan(Date otherDate) {
        if (year != otherDate.year) {
            return year > otherDate.year;
        } else if (month != otherDate.month) {
            return month > otherDate.month;
        }
        return day > otherDate.day;
    }

    // EFFECTS: creates a json object with this objects information in it and returns it
    public JSONObject convertToJson() {
        JSONObject output = new JSONObject();
        output.put("year", year);
        output.put("month", month);
        output.put("day", day);
        return output;
    }

}
