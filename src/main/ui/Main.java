package ui;

import model.*;
import model.exceptions.InvalidCommand;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

        AssignmentList mainList = new AssignmentList();

        Gui gui = new Gui(mainList);

        Commands.runCommand(1, mainList);


        while (true) {
            int command = 6;
            while (true) {
                try {
                    command = Commands.getCommand();
                    break;
                } catch (InvalidCommand e) {
                    System.out.println("Invalid command!");
                }
            }
            if (Commands.runCommand(command, mainList) || gui.quit) {
                break;
            }
        }

        Iterator<Event> logs = EventLog.getInstance().iterator();

        System.out.println("Logs made:");
        while (logs.hasNext()) {
            System.out.println(logs.next().getDescription());
        }
        System.exit(0);
    }
}
