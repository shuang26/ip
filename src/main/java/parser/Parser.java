package parser;

import commands.AddCommand;
import commands.Command;
import commands.DeleteCommand;
import commands.ExitCommand;
import commands.FindCommand;
import commands.IncorrectCommand;
import commands.ListCommand;
import commands.MarkCommand;
import commands.UnknownCommand;
import commands.UnmarkCommand;

import task.Task;
import task.Todo;
import task.Deadline;
import task.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class responsible for parsing commands and date-time inputs.
 * Provides methods for parsing user commands and converting date-time strings
 * into LocalDateTime objects.
 */
public class Parser {
    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param input the date-time string to be parsed.
     * @return a LocalDateTime object representing the parsed date and time.
     * @throws DateTimeParseException if the input string does not match the expected format.
     */
    public static LocalDateTime parseDateTime(String input) throws DateTimeParseException {
        input = input.replaceAll("[-/]", " "); // Normalize separators
        String[] parts = input.split("\\s+");

        if (parts.length < 4) {
            throw new DateTimeParseException("Invalid format", input, 0);
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d M yyyy");
        LocalDate date = LocalDate.parse(parts[0] + " " + parts[1] + " " + parts[2], dateFormatter);

        // Parse time (supports "HHmm" format)
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(parts[3], timeFormatter);

        return LocalDateTime.of(date, time);
    }

    public static Task parseTaskFromLine(String line) throws IllegalArgumentException {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Unable to load this task: " + line);
        }

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");

        switch (taskType) {
        case "T":
            return new Todo(parts[2], isDone);
        case "D":
            if (parts.length < 4) throw new IllegalArgumentException("Missing deadline date: " + line);
            return new Deadline(parts[2], isDone, Parser.parseDateTime(parts[3]));
        case "E":
            if (parts.length < 5) throw new IllegalArgumentException("Missing event start/end: " + line);
            return new Event(parts[2], isDone, Parser.parseDateTime(parts[3]), Parser.parseDateTime(parts[4]));
        default:
            throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        String[] parts = userInput.trim().split(" ", 2);
        String commandType = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandType) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "find":
            return new FindCommand(arguments);
        case "delete":
            return handleDelete(arguments);
        case "unmark":
        case "mark":
            return handleMarkUnMark(commandType, arguments);
        case "todo":
        case "deadline":
        case "event":
            return handleAdd(commandType, arguments);
        default: // TODO change to help command?
            return new UnknownCommand("Sorry, I don't know what that means.");
        }
    }

    private Command handleMarkUnMark(String commandType, String index) {
        int i;
        try {
            i = Integer.parseInt(index) - 1;
        } catch (NumberFormatException e) {
            return new IncorrectCommand("Please enter a valid index for " + commandType + " request.");
        }

        if (commandType.equals("mark")) {
            return new MarkCommand(i);
        } else {
            return new UnmarkCommand(i);
        }
    }

    private Command handleDelete(String index) {
        int i;
        try {
            i = Integer.parseInt(index) - 1;
        } catch (NumberFormatException e) {
            return new IncorrectCommand("Please enter a valid index for delete request.");
        }
        return new DeleteCommand(i);
    }

    private Command handleAdd(String taskType, String arguments) {
        if (arguments.isEmpty()) {
            return new IncorrectCommand("Error: Please provide a description for "
                + taskType + " task.");
        }

        if (taskType.equals("todo")) {
            return new AddCommand(taskType, arguments, false);
        } else if (taskType.equals("deadline")) {
            String[] parts = arguments.split("/by", 2);
            LocalDateTime deadline;
            try {
                deadline = Parser.parseDateTime(parts[1].trim());
            } catch (DateTimeParseException e) {
                return new IncorrectCommand("Error: Deadline format is yyyy-mm-dd HH:MM");
            }
            return new AddCommand(taskType, parts[0], false, deadline);
        } else { // if (commandType.equals("event")) {
            int fromIndex = arguments.indexOf("/from");
            int toIndex = arguments.indexOf("/to");

            if (fromIndex == -1 || toIndex == -1) {
                return new IncorrectCommand("Error: Please provide /from data or /to date for Event task");
            }

            String description = arguments.substring(0, fromIndex).trim();
            LocalDateTime fromDate = Parser.parseDateTime(arguments.substring(fromIndex + 6, toIndex).trim());
            LocalDateTime toDate = Parser.parseDateTime(arguments.substring(toIndex + 4).trim());
            return new AddCommand(taskType, description, false, fromDate, toDate);
        }
    }
}
