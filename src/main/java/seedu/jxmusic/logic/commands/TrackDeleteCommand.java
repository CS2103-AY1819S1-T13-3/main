package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Delete a track from target playlist.
 */
public class TrackDeleteCommand extends Command {
    public static final String COMMAND_PHRASE = "track del";
    public static final String MESSAGE_SUCCESS = "Track %1$s deleted from playlist %2$s";
    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Deletes a track from the playlist identified "
            + "by the index of track in the playlist. "
            + "Track will be deleted from the playlist.\n"
            + "Parameters: [" + PREFIX_PLAYLIST + "PLAYLIST] [INDEX]...\n"
            + "Example: " + COMMAND_PHRASE + " "
            + PREFIX_PLAYLIST + "rockPlaylist "
            + "1";
    public static final String MESSAGE_INDEX_DOES_NOT_EXIST = "This playlist does not have index: %1$s";
    public static final String MESSAGE_PLAYLIST_DOES_NOT_EXIST = "This playlist %1$s does not exist";

    private Index indexToDelete;
    private Playlist targetPlaylist;

    public TrackDeleteCommand(Playlist targetPlaylist, Index indexToDelete) {
        requireNonNull(indexToDelete);
        requireNonNull(targetPlaylist);
        this.indexToDelete = indexToDelete;
        this.targetPlaylist = targetPlaylist;
    }

    @Override
    public CommandResult execute(Model model) {
        Playlist updatedPlaylist;
        Playlist actualPlaylist;
        int trackNum = indexToDelete.getOneBased();
        int playlistSize = 0;
        Track trackToDelete;

        // check if playlist exists
        if (!model.hasPlaylist(targetPlaylist)) {
            return new CommandResult(String.format(MESSAGE_PLAYLIST_DOES_NOT_EXIST, targetPlaylist.getName()));
        }

        actualPlaylist = model.getLibrary().getPlaylistList()
                .filtered(playlist -> playlist.isSamePlaylist(targetPlaylist))
                .get(0);

        updatedPlaylist = actualPlaylist.copy();
        // check if track exists in existing playlist
        playlistSize = updatedPlaylist.getTracks().size();
        if (trackNum > playlistSize) {
            return new CommandResult(String.format(MESSAGE_INDEX_DOES_NOT_EXIST, trackNum));
        }

        // get track from trackNum
        trackToDelete = actualPlaylist.getTracks().get(indexToDelete.getZeroBased());

        if (updatedPlaylist.deleteTrack(trackToDelete)) {
            model.updatePlaylist(actualPlaylist, updatedPlaylist);
            return new CommandResult(String.format(MESSAGE_SUCCESS, trackToDelete, actualPlaylist.getName()));
        }
        return null;
    }

}
