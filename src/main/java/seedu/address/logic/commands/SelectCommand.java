package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Selects a person identified using its displayed index from the address book.
 */
public class SelectCommand extends Command {
    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_WORD_ALIAS = "s";

    public static final String ARGS_ME = "me";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects yourself or the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 OR "
            + "Example: " + COMMAND_WORD + " " + ARGS_ME;

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected %1$s";

    private final Index targetIndex;

    public SelectCommand() {
        this.targetIndex = null;
    }

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (targetIndex == null) {
            model.updateTimeTable(model.getUser().getTimeTable());
            return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, ARGS_ME));
        } else {
            List<Person> friendList = model.getCurrentFriendList();

            if (targetIndex.getZeroBased() >= friendList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            model.updateTimeTable(friendList.get(targetIndex.getZeroBased()).getTimeTable());
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
            String namePerson = model.getCurrentFriendList()
                    .get(targetIndex.getZeroBased()).getName().toString();

            return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, namePerson));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SelectCommand)) {
            return false;
        }

        SelectCommand otherSelectCommand = (SelectCommand) other;

        if (targetIndex == null && otherSelectCommand.targetIndex == null) {
            return true;
        }

        if (targetIndex.equals(otherSelectCommand.targetIndex)) {
            return true;
        }

        return false;
    }
}
