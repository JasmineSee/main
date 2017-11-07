package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undo the previous {@code UndoableCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_ALIAS = "u";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        undoRedoStack.popUndo().undo();
       // restorePhotos();
        LoggingCommand loggingCommand = new LoggingCommand();
        loggingCommand.keepLog("", "Undo");
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }

    //@@author JasmineSee

    /**
     * Restores all photos of persons in the address book.
     */
    public void restorePhotos() {
        File backupDir = new File("photosBackup/");
        File dir = new File("photos/");
        for (File backupFile : backupDir.listFiles()) {
            if (!(isPhotoExist(backupFile.getName()))) {
                System.out.println("photo does not exist in current directory");
                savePhoto(backupFile);
            }
        }
    }
    //@@author

    //@@author JasmineSee

    /**
     * Checks against current photos directory and returns true if photo in backup is in current photos directory
     */
    public boolean isPhotoExist(String backupFileName) {
        File dir = new File("photos/");
        for (File currentFile : dir.listFiles()) {
            if (backupFileName.equals(currentFile.getName())) {
                return true;
            }
        }
        return false;
    }
    //@@author

    //@@author JasmineSee

    /**
     * Saves backup photo to current photo directory
     */
    public void savePhoto(File backupFile) {
        try {
            File path = new File("photos/" + backupFile.getName());
            path.mkdirs();
            path.createNewFile();
            BufferedImage image;
            image = ImageIO.read(backupFile);
            ImageIO.write(image, "png", path);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(UploadPhotoCommand.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    //@@author
}
