package seedu.jxmusic.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.jxmusic.commons.core.ComponentManager;
import seedu.jxmusic.commons.core.LogsCenter;
import seedu.jxmusic.logic.commands.Command;
import seedu.jxmusic.logic.commands.CommandResult;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.logic.parser.LibraryParser;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final LibraryParser libraryParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        libraryParser = new LibraryParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = libraryParser.parseCommand(commandText);
        return command.execute(model);
    }

    @Override
    public ObservableList<Playlist> getFilteredPlaylistList() {
        return model.getFilteredPlaylistList();
    }

   @Override
   public ListElementPointer getHistorySnapshot() {
       return new ListElementPointer(history.getHistory());
   }
}
