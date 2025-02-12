package parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

/**
 * Utility class responsible for parsing commands and date-time inputs.
 * Provides methods for parsing user commands and converting date-time strings
 * into LocalDateTime objects.
 */
public class Parser {
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        String[] parts = userInput.split(" ", 2);
        trimArray(parts);
        String commandType = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandType) {
        case "bye":
        case "exit":
        case "close":
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

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param input The date-time string to be parsed.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws DateTimeParseException If the input string does not match the expected format.
     */
    public static LocalDateTime parseDateTime(String input) throws DateTimeParseException {
        input = input.replaceAll("[-/]", " ");
        String[] parts = input.split("\\s+");

        if (parts.length < 3) {
            throw new DateTimeParseException("Invalid format. Expected: d/M/yyyy or dd/MM/yyyy [HH:mm]", input, 0);
        }

        LocalDateTime dateTime = parseDate(parts);
        if (parts.length > 3) {
            LocalTime time = parseTime(parts[3]);
            dateTime = LocalDateTime.of(dateTime.toLocalDate(), time);
        }
        return dateTime;
    }

    // Split date and time parsing into two methods
    private static LocalDateTime parseDate(String[] parts) throws DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d M yyyy");
        try {
            return LocalDate.parse(parts[0] + " " + parts[1] + " " + parts[2], dateFormatter).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format.", String.join(" ", parts), 0);
        }
    }

    private static LocalTime parseTime(String timeInput) throws DateTimeParseException {
        timeInput = timeInput.replace(":", "");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        try {
            return LocalTime.parse(timeInput, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid time format. Expected: HH:mm or HHmm", timeInput, 0);
        }
    }

    /**
     * Parses a task from a formatted string line.
     *
     * @param line The string representation of the task from storage.
     * @return The corresponding Task object.
     * @throws IllegalArgumentException If the format of the task line is invalid.
     */
    public static Task parseTaskFromLine(String line) throws IllegalArgumentException {
        String[] parts = line.split("\\|");
        trimArray(parts);

        validateTaskParts(parts, line);

        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");

        switch (taskType) {
        case "T":
            return new Todo(parts[2], isDone);

        case "D":
            if (parts.length < 4) {
                throw new IllegalArgumentException("Missing deadline date: " + line);
            }
            try {
                LocalDateTime deadline = Parser.parseDateTime(parts[3]);
                return new Deadline(parts[2], isDone, deadline);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid deadline format: " + parts[3]);
            }

        case "E":
            if (parts.length < 5) {
                throw new IllegalArgumentException("Missing event start or end date: " + line);
            }
            try {
                LocalDateTime fromDate = Parser.parseDateTime(parts[3]);
                LocalDateTime toDate = Parser.parseDateTime(parts[4]);
                return new Event(parts[2], isDone, fromDate, toDate);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid event date format in line: " + line);
            }

        default:
            throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }

    private static void validateTaskParts(String[] parts, String line) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Unable to load this task: " + line);
        }
    }

    /**
     * Handles marking or unmarking a task.
     *
     * @param commandType The type of command, either "mark" or "unmark".
     * @param stringIndex   The index of the task to mark or unmark.
     * @return A Command object to mark or unmark a task.
     */
    private Command handleMarkUnMark(String commandType, String stringIndex) {
        int index;
        try {
            index = Integer.parseInt(stringIndex) - 1;
        } catch (NumberFormatException e) {
            return new IncorrectCommand("Please enter a valid index for " + commandType + " request.");
        }

        if (commandType.equals("mark")) {
            return new MarkCommand(index);
        } else {
            return new UnmarkCommand(index);
        }
    }

    /**
     * Handles the deletion of a task.
     *
     * @param stringIndex The index of the task to delete.
     * @return A DeleteCommand object to delete a task.
     */
    private Command handleDelete(String stringIndex) {
        int index;
        try {
            index = Integer.parseInt(stringIndex) - 1;
        } catch (NumberFormatException e) {
            return new IncorrectCommand("Please enter a valid index for delete request.");
        }
        return new DeleteCommand(index);
    }

    /**
     * Handles adding a new task.
     *
     * @param taskType  The type of task (todo, deadline, event).
     * @param arguments The arguments containing the task details.
     * @return A Command object for adding the task.
     */
    private Command handleAdd(String taskType, String arguments) {
        if (arguments.isEmpty()) {
            return new IncorrectCommand("Error: Please provide a description for "
                + taskType + " task.");
        }

        if (taskType.equals("todo")) {
            return new AddCommand(taskType, arguments, false);

        } else if (taskType.equals("deadline")) {
            String[] parts = arguments.split("/by", 2);
            if (parts.length < 2) {
                return new IncorrectCommand("Error: Missing deadline. Format is: deadline <task> /by yyyy-MM-dd HH:mm");
            }

            LocalDateTime deadline;
            try {
                deadline = Parser.parseDateTime(parts[1].trim());
            } catch (DateTimeParseException e) {
                return new IncorrectCommand(e.getMessage());
            }
            return new AddCommand(taskType, parts[0], false, deadline);

        } else { // if (commandType.equals("event")) {
            int fromIndex = arguments.indexOf("/from");
            int toIndex = arguments.indexOf("/to");

            if (fromIndex == -1 || toIndex == -1) {
                return new IncorrectCommand("Please provide /from data and /to date for Event task");
            }

            String description = arguments.substring(0, fromIndex).trim();
            LocalDateTime fromDate;
            LocalDateTime toDate;

            try {
                fromDate = Parser.parseDateTime(arguments.substring(fromIndex + 6, toIndex).trim());
                toDate = Parser.parseDateTime(arguments.substring(toIndex + 4).trim());

                if (toDate.isBefore(fromDate)) {
                    return new IncorrectCommand("Error: /to date cannot be before /from date for Event task");
                }
            } catch (DateTimeParseException e) {
                return new IncorrectCommand(e.getMessage());
            }

            return new AddCommand(taskType, description, false, fromDate, toDate);
        }
    }

    /**
     * Trims an array.
     *
     * @param array The array to be trimmed.
     */
    public static void trimArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                array[i] = array[i].trim();
            }
        }
    }
}
