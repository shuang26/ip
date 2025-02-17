package parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

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
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void testParseCommand_list() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void testParseCommand_find() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("find keyword");
        assertInstanceOf(FindCommand.class, command);
        // assertEquals("keyword", command.execute(null, null).toString());
    }

    @Test
    void testParseCommand_delete_validIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
        // assertEquals(0, ((command).execute());
    }

    @Test
    void testParseCommand_delete_invalidIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("delete abc");
        assertInstanceOf(IncorrectCommand.class, command);
        // assertEquals("Please enter a valid index for delete request.",
        // ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_mark_validIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("mark 2");
        assertInstanceOf(MarkCommand.class, command);
        // assertEquals(1, ((MarkCommand) command).getIndex());
    }

    @Test
    void testParseCommand_mark_invalidIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("mark abc");
        assertInstanceOf(IncorrectCommand.class, command);
        // assertEquals("Please enter a valid index for mark request.", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_unmark_validIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("unmark 2");
        assertInstanceOf(UnmarkCommand.class, command);
        // assertEquals(1, ((UnmarkCommand) command).getIndex());
    }

    @Test
    void testParseCommand_unmark_invalidIndex() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("unmark abc");
        assertInstanceOf(IncorrectCommand.class, command);
        // assertEquals("Please enter a valid index for unmark request.",
        // ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_todo_valid() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("todo read book");
        assertInstanceOf(AddCommand.class, command);
        // assertEquals("read book", ((AddCommand) command).getDescription());
    }

    @Test
    void testParseCommand_todo_missingDescription() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("todo ");
        assertInstanceOf(IncorrectCommand.class, command);
        // assertEquals("Error: Please provide a description for todo task.",
        //  ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_deadline_valid() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deadline submit report /by 1/12/2023 18:00");
        assertInstanceOf(AddCommand.class, command);
        // assertEquals("deadline", ((AddCommand) command).getTaskType());
        // assertEquals("submit report", ((AddCommand) command).getDescription());
        // assertEquals("2023-12-01T18:00", ((AddCommand) command).getDeadline().toString());
    }

    @Test
    void testParseCommand_deadline_missingDeadline() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deadline submit report");
        assertInstanceOf(IncorrectCommand.class, command);
        // assertEquals("Error: Missing deadline.\nFormat for Deadline is : deadline <description>
        // /by <deadline>", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_deadline_invalidFormat() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deadline submit report /by invalid_date");
        assertInstanceOf(IncorrectCommand.class, command);
        // assertTrue(((IncorrectCommand) command).getErrorMessage().contains("Invalid format."));
    }

    @Test
    void testParseCommand_event_valid() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("event conference /from 1/12/2023 09:00 /to 1/12/2023 17:00");
        assertInstanceOf(AddCommand.class, command);
        // assertEquals("event", ((AddCommand) command).getTaskType());
        // assertEquals("conference", ((AddCommand) command).getDescription());
        // assertEquals("2023-12-01T09:00", ((AddCommand) command).getStartDate().toString());
        // assertEquals("2023-12-01T17:00", ((AddCommand) command).getEndDate().toString());
    }

    @Test
    void testParseCommand_event_missingDates() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("event conference /from /to ");
        assertInstanceOf(IncorrectCommand.class, command);
        // assertEquals("Error: Missing from date / to date.\nFormat for Event is: event <description>
        // /from <fromDate> /to <toDate>", ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_event_toDateBeforeFromDate() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("event conference /from 1/12/2023 15:00 /to 1/12/2023 09:00");
        assertInstanceOf(IncorrectCommand.class, command);
        // assertEquals("Error: /to date cannot be before /from date for Event task",
        // ((IncorrectCommand) command).getErrorMessage());
    }

    @Test
    void testParseCommand_unknownCommand() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("unknowncommand");
        assertInstanceOf(UnknownCommand.class, command);
        // assertEquals("Sorry, I don't know what that means.", ((UnknownCommand) command).getMessage());
    }
}
