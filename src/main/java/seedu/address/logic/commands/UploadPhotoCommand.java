package seedu.address.logic.commands;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Uploads image file to specified person.
 */
public class UploadPhotoCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "photo";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Uploads image to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UPLOAD_IMAGE_SUCCESS = "Uploaded image to Person: %1$s";

    private final Index targetIndex;
    private final FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();
    private Stage stage;
    ImageView imageView = new ImageView();

    public UploadPhotoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;

    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToUploadImage = lastShownList.get(targetIndex.getZeroBased());

        handleFileChooser();

        return new CommandResult(String.format(MESSAGE_UPLOAD_IMAGE_SUCCESS, personToUploadImage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UploadPhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((UploadPhotoCommand) other).targetIndex)); // state check
    }

    public void handleFileChooser() {

//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save Image");
//
//        File file = fileChooser.showSaveDialog(stage);
//        if (file != null) {
//            try {
//                ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(),
//                        null), "png", file);
//            } catch (IOException ex) {
//                Logger.getLogger(
//                        UploadPhotoCommand.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            openFile(file);
            System.out.println(file.getAbsolutePath());
        }
    }

    public void openFile(File file) {
        try {
            desktop.open(file);

        } catch (IOException ex) {
            Logger.getLogger(
                    UploadPhotoCommand.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

    private void saveFile(int content, File file) {
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(UploadPhotoCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
