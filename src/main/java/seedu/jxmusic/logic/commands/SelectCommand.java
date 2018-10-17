package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.jxmusic.commons.core.EventsCenter;
import seedu.jxmusic.commons.core.Messages;
import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.commons.events.ui.JumpToListRequestEvent;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;

/**
 * Selects a playlist identified using it's displayed index from the jxmusic book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the playlist identified by the index number used in the displayed playlist list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PLAYLIST_SUCCESS = "Selected Playlist: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Playlist> filteredPlaylistList = model.getFilteredPlaylistList();

        if (targetIndex.getZeroBased() >= filteredPlaylistList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_PLAYLIST_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
