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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    /**
     * Tests for the parseCommand method in the Parser class.
     * The parseCommand method takes a user input string and parses it into a specific Command object.
     * Each test focuses on a single use case.
     */

    @Test
    void testParseCommand_exit() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("bye");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    void testParseCommand_list() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    void testParseCommand_find() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("find keyword");
        assertTrue(command instanceof FindCommand);
        assertEquals("keyword", ((FindCommand) command).getFindString());
    }

    @Test
    void testParseCommand_delete_validIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("delete 1");
        assertTrue(command instanceof DeleteCommand);
        assertEquals(0, ((DeleteCommand) command).getIndex());
    }

    @Test
    void testParseCommand_delete_invalidIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("delete abc");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Please enter a valid index for delete request.", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_mark_validIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("mark 2");
        assertTrue(command instanceof MarkCommand);
        assertEquals(1, ((MarkCommand) command).getIndex());
    }

    @Test
    void testParseCommand_mark_invalidIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("mark abc");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Please enter a valid index for mark request.", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_unmark_validIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("unmark 2");
        assertTrue(command instanceof UnmarkCommand);
        assertEquals(1, ((UnmarkCommand) command).getIndex());
    }

    @Test
    void testParseCommand_unmark_invalidIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("unmark abc");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Please enter a valid index for unmark request.", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_todo_valid() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("todo read book");
        assertTrue(command instanceof AddCommand);
        assertEquals("todo", ((AddCommand) command).getTaskType());
        assertEquals("read book", ((AddCommand) command).getDescription());
    }

    @Test
    void testParseCommand_todo_missingDescription() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("todo ");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Error: Please provide a description for todo task.", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_deadline_valid() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deadline submit report /by 1/12/2023 18:00");
        assertTrue(command instanceof AddCommand);
        assertEquals("deadline", ((AddCommand) command).getTaskType());
        assertEquals("submit report", ((AddCommand) command).getDescription());
        assertEquals("2023-12-01T18:00", ((AddCommand) command).getDeadline().toString());
    }

    @Test
    void testParseCommand_deadline_missingDeadline() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deadline submit report");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Error: Missing deadline.\nFormat for Deadline is : deadline <description> /by <deadline>", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_deadline_invalidFormat() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deadline submit report /by invalid_date");
        assertTrue(command instanceof IncorrectCommand);
        assertTrue(((IncorrectCommand) command).getErrorMessage().contains("Invalid format."));
    }

    @Test
    void testParseCommand_event_valid() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("event conference /from 1/12/2023 09:00 /to 1/12/2023 17:00");
        assertTrue(command instanceof AddCommand);
        assertEquals("event", ((AddCommand) command).getTaskType());
        assertEquals("conference", ((AddCommand) command).getDescription());
        assertEquals("2023-12-01T09:00", ((AddCommand) command).getStartDate().toString());
        assertEquals("2023-12-01T17:00", ((AddCommand) command).getEndDate().toString());
    }

    @Test
    void testParseCommand_event_missingDates() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("event conference /from /to ");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Error: Missing from date / to date.\nFormat for Event is: event <description> /from <fromDate> /to <toDate>", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_event_toDateBeforeFromDate() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("event conference /from 1/12/2023 15:00 /to 1/12/2023 09:00");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Error: /to date cannot be before /from date for Event task", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_unknownCommand() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("unknowncommand");
        assertTrue(command instanceof UnknownCommand);
        assertEquals("Sorry, I don't know what that means.", ((UnknownCommand) command).getMessage());
    }
}