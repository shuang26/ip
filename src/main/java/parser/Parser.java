package parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public static class ParsedCommand {
        public final String command;
        public final String arguments;

        public ParsedCommand(String command, String arguments) {
            this.command = command;
            this.arguments = arguments;
        }
    }

    public static ParsedCommand parse(String input) {
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : ""; // Handle cases where no arguments are provided

        return new ParsedCommand(command, arguments);
    }

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
