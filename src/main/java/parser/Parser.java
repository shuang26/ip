package parser;

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
     * Represents a parsed command with its associated arguments.
     */
    public static class ParsedCommand {
        public final String command;
        public final String arguments;

        public ParsedCommand(String command, String arguments) {
            this.command = command;
            this.arguments = arguments;
        }
    }

    /**
     * Parses an input string into a ParsedCommand containing the command and its associated arguments.
     *
     * @param input the input string to be parsed.
     * @return a ParsedCommand object containing the command and arguments.
     */
    public static ParsedCommand parse(String input) {
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : ""; // Handle cases where no arguments are provided

        return new ParsedCommand(command, arguments);
    }

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
}
