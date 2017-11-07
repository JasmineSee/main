package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBook());
       // deletePhoto();
        LoggingCommand loggingCommand = new LoggingCommand();
        loggingCommand.keepLog("", "Clear");
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author JasmineSee
    /**
     * Deletes all photos of persons in the address book.
     */
    public void deletePhoto() {
        File dir = new File("photos/");
        for (File file : dir.listFiles()) {
            if (!(file.getName().equals("default.jpeg"))) {
                file.delete();
            }
        }
    }
    //@@author
}
