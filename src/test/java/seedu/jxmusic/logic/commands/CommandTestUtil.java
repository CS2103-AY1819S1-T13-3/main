package seedu.jxmusic.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.NameContainsKeywordsPredicate;
import seedu.jxmusic.model.Playlist;
// import seedu.jxmusic.testutil.EditPlaylistDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_PLAYLIST_NAME_EMPTY = "Empty playlist";
    public static final String VALID_PLAYLIST_NAME_ANIME = "Anime music";
    public static final String VALID_PLAYLIST_NAME_INSTRUMENTAL = "Instrumental songs";
    public static final String VALID_PLAYLIST_NAME_METAL = "METAL";

    /** for anime playlist */
    public static final String VALID_TRACK_NAME_HAIKEI = "Haikei Goodbye Sayonara";
    /** for anime and instrumental playlist */
    public static final String VALID_TRACK_NAME_IHOJIN = "Ihojin no Yaiba";
    /** for anime and metal playlist */
    public static final String VALID_TRACK_NAME_EXISTENCE = "EXiSTENCE";
    /** for instrumental playlist */
    public static final String VALID_TRACK_NAME_FURELISE = "Fur Elise";
    /** for metal playlist */
    public static final String VALID_TRACK_NAME_MYDEMONS = "My Demons";

    public static final String PLAYLIST_NAME_ARG_ANIME = " " + PREFIX_PLAYLIST + VALID_PLAYLIST_NAME_ANIME;
    public static final String PLAYLIST_NAME_ARG_INSTRUMENTAL = " " + PREFIX_PLAYLIST + VALID_PLAYLIST_NAME_INSTRUMENTAL;
    public static final String PLAYLIST_NAME_ARG_METAL = " " + PREFIX_PLAYLIST + VALID_PLAYLIST_NAME_METAL;

    public static final String TRACK_NAME_ARG_HAIKEI = " " + PREFIX_TRACK + VALID_TRACK_NAME_HAIKEI;
    public static final String TRACK_NAME_ARG_IHOJIN = " " + PREFIX_TRACK + VALID_TRACK_NAME_IHOJIN;
    public static final String TRACK_NAME_ARG_EXISTENCE = " " + PREFIX_TRACK + VALID_TRACK_NAME_EXISTENCE;
    public static final String TRACK_NAME_ARG_FURELISE = " " + PREFIX_TRACK + VALID_TRACK_NAME_FURELISE;
    public static final String TRACK_NAME_ARG_MYDEMONS = " " + PREFIX_TRACK + VALID_TRACK_NAME_MYDEMONS;

    public static final String INVALID_PLAYLIST_NAME_ARG = " " + PREFIX_PLAYLIST + "Rock&Roll"; // '&' not allowed in playlist name
    public static final String INVALID_TRACK_NAME_ARG = " " + PREFIX_TRACK + "Don't Stop Believin'"; // `'` not allowed in track name
    public static final String INVALID_TRACK_FILE_NOT_EXIST_ARG = " " + PREFIX_TRACK + "no track file"; // name is valid but file does not exist
    public static final String INVALID_TRACK_FILE_NOT_SUPPORTED_ARG = " " + PREFIX_TRACK + "unsupported track"; // name is valid, file exists but file not supported

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // Not using Edit Command so following lines not needed, todo cleanup: delete this block
    //
    // public static final EditCommand.EditPlaylistDescriptor DESC_AMY;
    // public static final EditCommand.EditPlaylistDescriptor DESC_BOB;

    // static {
    //     DESC_AMY = new EditPlaylistDescriptorBuilder().withName(VALID_PLAYLIST_NAME_ANIME)
    //             .withTracks(VALID_TRACK_EXISTENCE).build();
    //     DESC_BOB = new EditPlaylistDescriptorBuilder().withName(VALID_NAME_METAL)
    //             .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
    //             .withTRACKs(VALID_TRACK_NAME_IHOJIN, VALID_TRACK_EXISTENCE).build();
    // }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the jxmusic book and the filtered playlist list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Library expectedLibrary = new Library(actualModel.getLibrary());
        List<Playlist> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPlaylistList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedLibrary, actualModel.getLibrary());
            assertEquals(expectedFilteredList, actualModel.getFilteredPlaylistList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the playlist at the given {@code targetIndex} in the
     * {@code model}'s jxmusic book.
     */
    public static void showPlaylistAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPlaylistList().size());

        Playlist playlist = model.getFilteredPlaylistList().get(targetIndex.getZeroBased());
        final String[] splitName = playlist.getName().nameString.split("\\s+");
        model.updateFilteredPlaylistList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPlaylistList().size());
    }

    /**
     * Deletes the first playlist in {@code model}'s filtered list from {@code model}'s jxmusic book.
     */
    public static void deleteFirstPlaylist(Model model) {
        Playlist firstPlaylist = model.getFilteredPlaylistList().get(0);
        model.deletePlaylist(firstPlaylist);
    }

}
