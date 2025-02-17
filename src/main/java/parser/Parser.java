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
 * Utility class responsible for parsing user inputs into commands.
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

        return switch (commandType) {
        case "bye", "exit", "close" -> handleExit();
        case "list" -> handleList();
        case "find" -> handleFind(arguments);
        case "delete" -> handleDelete(arguments);
        case "unmark", "mark" -> handleMarkUnmark(commandType, arguments);
        case "todo", "deadline", "event" -> handleAdd(commandType, arguments);
        default -> handleUnknown(); // TODO change to help command?
        };
    }

    private Command handleExit() {
        return new ExitCommand();
    }

    private Command handleList() {
        return new ListCommand();
    }

    private Command handleFind(String arguments) {
        return new FindCommand(arguments);
    }

    private Command handleDelete(String stringIndex) {
        int index = parseIndex(stringIndex);
        if (index == -1) {
            return new IncorrectCommand("Please enter a valid index for delete request.");
        }
        return new DeleteCommand(index);
    }

    private Command handleMarkUnmark(String commandType, String stringIndex) {
        int index = parseIndex(stringIndex);
        if (index == -1) {
            return new IncorrectCommand("Please enter a valid index for " + commandType + " request.");
        }

        if (commandType.equals("mark")) {
            return new MarkCommand(index);
        }
        return new UnmarkCommand(index);
    }

    private Command handleAdd(String taskType, String arguments) {
        if (arguments.isEmpty()) {
            return new IncorrectCommand("Error: Please provide a description for " + taskType + " task.");
        }

        return switch (taskType) {
        case "todo" -> handleTodo(arguments);
        case "deadline" -> handleDeadline(arguments);
        case "event" -> handleEvent(arguments);
        // This line is not supposed to be reached
        default -> throw new RuntimeException("Unknown task type - " + taskType);
        };
    }

    private Command handleUnknown() {
        return new UnknownCommand("Sorry, I don't know what that means.");
    }

    private Command handleTodo(String description) {
        return new AddCommand("todo", description, false);
    }

    private Command handleDeadline(String arguments) {
        String[] parts = arguments.split("/by", 2);
        if (parts.length < 2) {
            return new IncorrectCommand("Error: Missing deadline.\n"
                + "Format for Deadline is : deadline <description> /by <deadline>");
        }

        String description = parts[0].trim();
        LocalDateTime deadline;
        try {
            deadline = Parser.parseDateTime(parts[1].trim());
        } catch (DateTimeParseException e) {
            return new IncorrectCommand(e.getMessage());
        }
        return new AddCommand("deadline", description, false, deadline);
    }

    private Command handleEvent(String arguments) {
        int fromIndex = arguments.indexOf("/from");
        int toIndex = arguments.indexOf("/to");

        if (!isValidEventFormat(fromIndex, toIndex)) {
            return new IncorrectCommand("Error: Missing from date / to date.\n"
                + "Format for Event is: event <description> /from <fromDate> /to <toDate>");
        }

        String description = parseDescription(arguments, fromIndex);
        String fromString = parseDateString(arguments, fromIndex + 6, toIndex);
        String toString = parseDateString(arguments, toIndex + 4, arguments.length());

        if (fromString.isEmpty() || toString.isEmpty()) {
            return new IncorrectCommand("Error: Missing from date / to date.\n"
                + "Format for Event is: event <description> /from <fromDate> /to <toDate>");
        }

        return parseAndCreateEvent(description, fromString, toString);
    }

    private boolean isValidEventFormat(int fromIndex, int toIndex) {
        return fromIndex != -1 && toIndex != -1;
    }

    private int parseIndex(String stringIndex) {
        try {
            return Integer.parseInt(stringIndex) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String parseDescription(String arguments, int fromIndex) {
        return arguments.substring(0, fromIndex).trim();
    }

    private String parseDateString(String arguments, int start, int end) {
        if (start >= arguments.length()) {
            return "";
        }
        return arguments.substring(start, end).trim();
    }

    private Command parseAndCreateEvent(String description, String fromString, String toString) {
        try {
            LocalDateTime fromDate = Parser.parseDateTime(fromString);
            LocalDateTime toDate = Parser.parseDateTime(toString);

            if (toDate.isBefore(fromDate)) {
                return new IncorrectCommand("Error: /to date cannot be before /from date for Event task");
            }
            return new AddCommand("event", description, false, fromDate, toDate);
        } catch (DateTimeParseException e) {
            return new IncorrectCommand(e.getMessage());
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

        try {
            String taskType = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            switch (taskType) {
            case "T":
                return new Todo(description, isDone);
            case "D":
                LocalDateTime deadline = Parser.parseDateTime(parts[3]);
                return new Deadline(description, isDone, deadline);
            case "E":
                LocalDateTime fromDate = Parser.parseDateTime(parts[3]);
                LocalDateTime toDate = Parser.parseDateTime(parts[4]);
                return new Event(description, isDone, fromDate, toDate);
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
            }
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing task from line, skipping: " + line);
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
        // Parse userInput to get time, if its provided
        if (parts.length > 3) {
            LocalTime time = parseTime(parts[3]);
            dateTime = LocalDateTime.of(dateTime.toLocalDate(), time);
        }
        return dateTime;
    }

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

    private static void trimArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                array[i] = array[i].trim();
            }
        }
    }
}
