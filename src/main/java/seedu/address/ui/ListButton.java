package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for listing all contacts in the PersonListPanel.
 */
public class ListButton extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "ListButton.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private int selectedIndex;

    @FXML
    private Button listButton;

    public ListButton(Logic logic, int selectedIn) {
        super(FXML);
        this.logic = logic;
        this.selectedIndex = selectedIn;
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the List button pressed event.
     */
    @FXML
    private void handleListButtonPressed() throws CommandException, ParseException {
        CommandResult commandResult = logic.execute("list");
        logger.info("Result: " + commandResult.feedbackToUser);
    }
}
