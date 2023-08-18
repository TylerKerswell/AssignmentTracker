package ui;

import model.*;
import org.json.JSONObject;
import persistence.FileHandler;
import persistence.Parser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// A Class that handles all the gui components of the assignment tracker.
public class Gui {

    boolean quit;

    AssignmentList mainList;

    // main gui screen
    private final JFrame main;

    // map section
    JButton mapButton;

    // table
    JTable list;
    JScrollPane scroller;
    DefaultTableModel table;

    // change panel (the panel for adding, saving, and loading)
    JTabbedPane changePanel;

    // inside changepanel
    JPanel addPanel;
    JPanel savePanel;
    JPanel loadPanel;

    // inside addpanel
    JPanel datePanel;
    JSpinner dayInput;
    JSpinner monthInput;
    JSpinner yearInput;

    JPanel timePanel;
    JSpinner hourInput;
    JSpinner minuteInput;
    JCheckBox amInput;
    JSpinner completionTimeInput;

    JPanel namePanel;
    JTextField nameInput;
    JTextField classInput;

    JButton submitButton;

    // inside save/load panel
    JTextField saveFileInput;
    JButton saveFileButton;
    JTextField loadFileInput;
    JButton loadFileButton;



    // MODIFIES: this
    // EFFECTS: creates and initializes the gui, there should be 3 regions for the gui:
    // one for the actual list of assignments, another one for a button to display a map,
    // and another one for adding an assignment, saving, and loading
    public Gui(AssignmentList assignList) {
        mainList = assignList;
        main = new JFrame("Assignment Tracker");

        main.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.ipadx = 100;
        gbc.ipady = 100;

        // MAIN ASSIGNMENT LIST
        createTable(gbc);

        // PANEL TO ADD, SAVE, LOAD
        createChangePanel(gbc);

        // MAP BUTTON: COMPLETE
        createMapButton(gbc);


        main.pack();
        main.setSize(1000, 500);
        main.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the button you click to display a ma.
    // The map should display in a popup window and you should be able to scroll around to see the entire image
    private void createMapButton(GridBagConstraints gbc) {
        // create the button
        mapButton = new JButton("Click me for a map!");

        // add the event listener
        mapButton.addActionListener(event -> {

            JFrame popup = new JFrame();

            // grab image and throw into a scrollpane (to allow for scrolling)
            ImageIcon mapPicture = new ImageIcon(".\\data\\map.jpg");
            JScrollPane mapPane = new JScrollPane(new JLabel(mapPicture));

            // set popup
            popup.add(mapPane);
            popup.setSize(500, 500);
            popup.setVisible(true);
        });

        // add the button to the main screen
        gbc.gridx = 1;
        gbc.gridy = 1;
        main.add(mapButton, gbc);
    }

    // MODIFIES: this
    // EFFECTS: creates the panel for adding, saving, and loading
    private void createChangePanel(GridBagConstraints gbc) {
        changePanel = new JTabbedPane();

        // create each 3 tabs
        createAddPanel();
        createSavePanel();
        createLoadPanel();

        // add them to the panel
        changePanel.addTab("ADD", addPanel);
        changePanel.addTab("SAVE", savePanel);
        changePanel.addTab("LOAD", loadPanel);

        // put in top right corner
        gbc.gridx = 1;
        gbc.gridy = 0;
        main.add(changePanel, gbc);

    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void createAddPanel() {
        addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(2,2));

        // FOR DATE
        createDatePanel();

        // FOR TIME
        createTimePanel();


        // FOR NAMES
        namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.PAGE_AXIS));

        // get assignment name
        namePanel.add(new Label("Assignment name:"));
        nameInput = new JTextField();
        namePanel.add(nameInput);

        // get class name
        namePanel.add(new Label("Class name:"));
        classInput = new JTextField();
        namePanel.add(classInput);

        addPanel.add(namePanel);

        // create button to submit information
        submitButton = new JButton("SUBMIT");

        // collect data from each field and make an assignment with it
        submitButton.addActionListener(event -> {

            Assignment newAssignment = null;

            try {
                // grab due date
                Date dueDate = new Date((int) dayInput.getValue(), (int) monthInput.getValue(),
                        (int) yearInput.getValue());

                // grab due time
                Time dueTime = new Time((int) hourInput.getValue(), (int) minuteInput.getValue(),
                        amInput.isSelected());

                // only works when we allow one decimal of precision for the input
                double hours = (double) completionTimeInput.getValue();
                int hr = (int) hours;
                int min = (int) ((hours % 1) * 60);
                Time completionTime = new Time(hr,
                        min,false);

                // create the assignment
                newAssignment = new Assignment(nameInput.getText(), classInput.getText(),
                        dueDate, dueTime, completionTime);

            } catch (Exception e) {
                createErrorPoppup("Error adding assignment!");
                return;
            }

            // add the assignment to the list
            mainList.addAssignment(newAssignment);

            updateTable();

            // TODO: clear input fields if I have time
        });

        addPanel.add(submitButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel to get a date object
    private void createDatePanel() {
        datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.PAGE_AXIS));

        datePanel.add(new Label("Due Date:\n"));

        // get day input
        datePanel.add(new Label("Day:"));
        SpinnerNumberModel dayModel = new SpinnerNumberModel(1, 1, 31, 1);
        dayInput = new JSpinner(dayModel);
        datePanel.add(dayInput);

        // get month input
        datePanel.add(new Label("Month:"));
        SpinnerNumberModel monthModel = new SpinnerNumberModel(1, 1, 12, 1);
        monthInput = new JSpinner(monthModel);
        datePanel.add(monthInput);

        // get year input
        datePanel.add(new Label("Year:"));
        SpinnerNumberModel yearModel = new SpinnerNumberModel(2022, 2022, 2030, 1);
        yearInput = new JSpinner(yearModel);
        datePanel.add(yearInput);

        addPanel.add(datePanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel to get the time parameters for an assignment
    private void createTimePanel() {
        timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));

        timePanel.add(new Label("TIME DUE:"));

        // get hour
        timePanel.add(new Label("Hour:"));
        SpinnerNumberModel hourModel = new SpinnerNumberModel(1, 1, 12, 1);
        hourInput = new JSpinner(hourModel);
        timePanel.add(hourInput);

        // get minute
        timePanel.add(new Label("Minute:"));
        minuteInput = new JSpinner(new SpinnerNumberModel(1, 1, 59, 1));
        timePanel.add(minuteInput);

        // get AM
        timePanel.add(new Label("AM?:"));
        amInput = new JCheckBox();
        timePanel.add(amInput);

        // get completion time:
        timePanel.add(new Label("Time to complete (in hours):"));
        completionTimeInput = new JSpinner(new SpinnerNumberModel(0, 0, 24, 0.1));
        timePanel.add(completionTimeInput);

        addPanel.add(timePanel);
    }

    // MODIFIES: this
    // EFFECTS: makes a panel to save the assignment list to a file in the data folder
    private void createSavePanel() {

        // make panel and set layout
        savePanel = new JPanel();
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.PAGE_AXIS));

        // UX
        savePanel.add(new JLabel("Filename to save to: "));

        // create the text field to input information from
        saveFileInput = new JTextField();
        saveFileInput.setMaximumSize(new Dimension(200,30));
        saveFileInput.setMinimumSize(new Dimension(200, 30));
        savePanel.add(saveFileInput);

        // make the input button
        saveFileButton = new JButton("Enter");

        // save to the filename inputted from the savefileinput
        saveFileButton.addActionListener(event -> {
            // grab the file name
            String filename = saveFileInput.getText();

            try {
                // open file and write JSON to it
                FileHandler outputFile = new FileHandler(filename);
                outputFile.writeJson(mainList.convertToJson());

            } catch (Exception e) {
                createErrorPoppup("Error opening/writing to the file!");
            }
        });
        savePanel.add(saveFileButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel to load a file from
    private void createLoadPanel() {

        // make the panel and set the layout
        loadPanel = new JPanel();
        loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.PAGE_AXIS));

        // UI
        loadPanel.add(new JLabel("Filename to load from: "));

        // make the text box
        loadFileInput = new JTextField();
        loadFileInput.setMaximumSize(new Dimension(200,30));
        loadFileInput.setMinimumSize(new Dimension(200, 30));
        loadPanel.add(loadFileInput);

        // make the button to input the filename
        loadFileButton = new JButton("Enter");

        // load the file and store it in mainlist
        loadFileButton.addActionListener(event -> {

            try {
                // create a new file handler with the input
                FileHandler inputFile = new FileHandler(loadFileInput.getText());

                // read json from it
                JSONObject input = inputFile.readJson();

                // parse the read json
                Parser.parseAssignmentList(input, mainList);

            } catch (Exception e) {
                createErrorPoppup("Error reading from file!");
            }

            updateTable();

        });
        loadPanel.add(loadFileButton);

    }

    // EFFECTS: display an error popup window with the error message
    private void createErrorPoppup(String errorMsg) {
        JFrame error = new JFrame("Error");
        error.add(new JLabel(errorMsg));
        error.setSize(200,100);
        error.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: makes the panel for the table to show the user the list of assignments they have
    private void createTable(GridBagConstraints gbc) {

        // create a defaulttablemodel with the correct column names and with no information
        String[] cols = {"Class", "Date due", "Time due", "Time to complete", "Name"};
        table = new DefaultTableModel(cols, 0);

        // create the main table and put it inside a scroll pane
        list = new JTable(table);
        scroller = new JScrollPane(list);

        // make the table read-only
        list.setEnabled(false);

        // add the scroll pane to the main frame with the proper settings
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        main.add(scroller, gbc);
        gbc.gridheight = 1;
    }

    // MODIFIES: this
    // EFFECTS: updates the table panel with the correct information in mainlist
    private void updateTable() {

        // delete each row in the table
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            table.removeRow(i);
        }

        // repopulate the rows with every assignment in mainList
        for (int i = 0; i < mainList.getTotalLength(); i++) {
            if (!mainList.getAssignment(i).checkIfCompleted()) {
                Assignment tmp = mainList.getAssignment(i);

                table.addRow(new String[]{tmp.getCourse(), tmp.getDueDate().verboseDate(),
                            tmp.getDueTime().getTime(), String.valueOf(tmp.getCompletionTime().getTotalHours()),
                            tmp.getAssignmentName()});
            }
        }
    }


}
