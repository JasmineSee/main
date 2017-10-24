package seedu.address.logic.commands;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

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
    private Stage stage;
    private ImageView imageView = new ImageView();

    public UploadPhotoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;

    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        File imageFile;
        Photo targetPhoto = lastShownList.get(targetIndex.getZeroBased()).getPhoto();

        imageFile = handleFileChooser();
        imageFile = saveFile(imageFile, targetPhoto);

        ReadOnlyPerson personToUploadImage = lastShownList.get(targetIndex.getZeroBased());
        ReadOnlyPerson editedPerson = lastShownList.get(targetIndex.getZeroBased());
        editedPerson.getPhoto().setPath(imageFile.getPath());

        try {
            model.updatePerson(personToUploadImage, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UPLOAD_IMAGE_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UploadPhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((UploadPhotoCommand) other).targetIndex)); // state check
    }

    /**
     * Opens fileChooser to select image file
     */
    public File handleFileChooser() {
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }
    /**
     * Reads and saves image file into project directory folder "photos".
     */
    private File saveFile(File file, Photo photo) {

        File path = new File("photos/" + file.getName());

        try {
            path.mkdirs();
            path.createNewFile();
            //     System.out.println(path.getPath());
            //  BufferedImage image = new BufferedImage(100, 100, 1);
            BufferedImage image;
            image = ImageIO.read(file);
            ImageIO.write(image, "png", path);

        } catch (IOException e) {
            photo.setPath("");
            e.printStackTrace();
            Logger.getLogger(UploadPhotoCommand.class.getName()).log(Level.SEVERE, null, e);
        }
        return path;
    }
}
