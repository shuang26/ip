package ui;

/**
 * Represents the user interface of the Cow application.
 */
public class Ui {
    private final String line = "____________________________________________________________\n";

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcome() {
        System.out.print(line + "Hello! I'm Cow\nWhat can I do for you?\n" + line);
    }

    /**
     * Displays a welcome message to the user.
     */
    public void showExit() {
        System.out.print(line + "Bye. Hope to see you again soon!\n" + line);
    }
}