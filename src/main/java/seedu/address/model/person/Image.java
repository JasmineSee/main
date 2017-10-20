package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's image in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class Image {

    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Not a valid image file";

    public final String filePath;

    /**
     * Validates given image file path.
     *
     * @throws IllegalValueException if given image file is invalid.
     */
    public Image(String filePath) throws IllegalValueException {

        //String filepath = "/the/file/path/image.jpg";
        File f = new File(filePath);
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        if (!type.equals("image")) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
        this.filePath = filePath;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidImage(String test) {
        File f = new File(test);
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }


    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.filePath.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }

}
