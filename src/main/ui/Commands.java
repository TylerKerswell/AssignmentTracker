package ui;

import model.*;
import model.exceptions.*;
import org.json.JSONObject;
import persistence.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Commands {

    // EFFECTS: returns an integer between 1-8 which represents a following command:
    // 1: help, 2: add, 3: complete, 4: next, 5: show, 6: quit, 7: load, 8: save
    public static int getCommand() throws InvalidCommand {
        System.out.print("Command: ");
        Scanner input = new Scanner(System.in);
        String cmd = input.next().toLowerCase();

        switch (cmd) {
            case "help":
                return 1;
            case "add":
                return 2;
            case "complete":
                return 3;
            case "next":
                return 4;
            case "show":
                return 5;
            case "quit":
                return 6;
            case "load":
                return 7;
            case "save":
                return 8;
        }
        throw new InvalidCommand();
    }

    // REQUIRES: commandId to be an integer from 1-8
    // MODIFIES: list
    // EFFECTS: runs the command corresponding to the correct id on the given AssignmentList,
    // returns true if quit was called, false otherwise
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static boolean runCommand(int commandId, AssignmentList list) {
        switch (commandId) {
            case 1:
                help();
                break;
            case 2:
                add(list);
                break;
            case 3:
                complete(list);
                break;
            case 4:
                nextDue(list);
                break;
            case 5:
                show(list);
                break;
            case 6:
                return true;
            case 7:
                load(list);
                break;
            case 8:
                save(list);
                break;
        }
        return false;
    }

    // commands: help(1) , add(2) , complete(3) , next due(4) , show(5)

    // EFFECTS: prints the help message
    private static void help() {
        System.out.println("Hello! Welcome to the assignment tracker!\n"
                + "Commands:\n"
                + "Type \"Help\" to show this message again.\n"
                + "Type \"Add\" to add an assignment.\n"
                + "Type \"Show\" to show your uncompleted assignments.\n"
                + "Type \"Next\" to show your closest assignment due.\n"
                + "Type \"Complete\" to complete an assignment.\n"
                + "Type \"Quit\" to quit.\n"
                + "Type \"Load\" to load your assignments from a file.\n"
                + "Type \"Save\" to save your assignments to a file.");
    }

    // REQUIRES: a valid AssignmentList object
    // MODIFIES: list
    // EFFECTS: asks the user for information and adds the assignment to the list
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private static void add(AssignmentList list) {

        Scanner input = new Scanner(System.in);

        System.out.print("Name of the course: ");
        String course = input.nextLine();

        System.out.print("Name of the assignment: ");
        String name = input.nextLine();

        Date dueDate;

        while (true) {
            System.out.print("Due date (day month year): ");

            int da = input.nextInt();
            int mo = input.nextInt();
            int yr = input.nextInt();

            try {
                dueDate = new Date(da, mo, yr);
                break;
            } catch (InvalidDate e) {
                System.out.println("That date is invalid!");
            }
        }


        System.out.print("Due time (hour minute): ");
        int hr = input.nextInt();
        int min = input.nextInt();

        System.out.print("Is it in the AM? (yes/no): ");
        boolean am = input.next().equalsIgnoreCase("yes");

        Time dueTime = new Time(hr, min, am);


        System.out.print("Time to complete (hour minute): ");
        int chr = input.nextInt();
        int cmin = input.nextInt();

        Time completeTime = new Time(chr, cmin, false);

        list.addAssignment(new Assignment(name, course, dueDate, dueTime, completeTime));

        System.out.println("Assignment added!");
    }


    // MODIFIES: asks the user for an assignment to complete and then marks that assignment as complete
    private static void complete(AssignmentList list) {
        System.out.print("Assignment name: ");

        Scanner input = new Scanner(System.in);
        String name = input.nextLine().toLowerCase();
        if (list.completeAssignment(name)) {
            System.out.println("Assignment completed!");
        } else {
            System.out.println("That is not a valid assignment!");
        }
    }

    // REQUIRES: a non-empty list
    // EFFECTS: prints the uncompleted assignment that is due next, if no assignments are due next
    // it prints the next completed assignment.
    private static void nextDue(AssignmentList list) {
        AssignmentList tmp = new AssignmentList();
        tmp.addAssignment(list.dueNext());
        show(tmp);
    }

    // REQUIRES: a non-empty list
    // EFFECTS: prints the list of assignments ordered by due date
    private static void show(AssignmentList list) {
        System.out.print(list.getList());
    }

    // MODIFIES: list
    // EFFECTS: loads an assignmentList from a json file
    private static void load(AssignmentList list) {

        FileHandler inputFile;
        try {
            inputFile = getFile();
        } catch (IOException e) {
            System.out.println("File Does not exist!");
            return;
        }

        JSONObject input;
        try {
            input = inputFile.readJson();
        } catch (Exception e) {
            System.out.println("Error Reading from file!");
            return;
        }

        try {
            Parser.parseAssignmentList(input, list);
        } catch (Exception e) {
            System.out.println("Error parsing file!");
        }
    }

    // EFFECTS: opens a file from user input
    private static FileHandler getFile() throws IOException {
        FileHandler inputFile;
        while (true) {
            System.out.print("Filename to load from: ");
            Scanner input = new Scanner(System.in);
            String fileName = input.nextLine();

            try {
                inputFile = new FileHandler(fileName);
                if (inputFile.fileExists()) {
                    break;
                }
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Error accessing file.");
            }
        }

        return inputFile;
    }


    // EFFECTS: saves the assignmentList to a json file
    private static void save(AssignmentList list) {

        JSONObject listJson =  list.convertToJson();
        FileHandler outputFile;

        System.out.print("Filename to save to: ");
        while (true) {

            Scanner input = new Scanner(System.in);
            String filename = input.nextLine();

            try {
                outputFile = new FileHandler(filename);
                break;
            } catch (IOException e) {
                System.out.println("Error in opening file. Enter a new name: ");
            }
        }

        try {
            outputFile.writeJson(listJson);
        } catch (IOException e) {
            System.out.println("Cannot save to the given file.");
        }
    }
}
