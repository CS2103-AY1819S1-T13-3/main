package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.ArrayList;
import java.util.List;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Add a track to target playlist.
 */
public class TrackAddCommand extends Command {
    public static final String COMMAND_PHRASE = "track add";
    public static final String MESSAGE_SUCCESS = "Tracks added to playlist: %1$s -> %2$s";
    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Adds a new track(s) to the playlist identified "
        + "by the name of the track and playlist.\n"
        + "Playlist will be modified with new track.\n"
        + "Parameters: [" + PREFIX_PLAYLIST + "PLAYLIST] "
        + "[" + PREFIX_TRACK + "TRACK]...\n"
        + "Example: " + COMMAND_PHRASE + " "
        + PREFIX_PLAYLIST + "rockPlaylist" + " "
        + PREFIX_TRACK + "rockNRoll";
    public static final String MESSAGE_TRACK_DOES_NOT_EXIST = "These tracks do not exist: %3$s";
    public static final String MESSAGE_PLAYLIST_DOES_NOT_EXIST = "Playlist does not exist: %1$s";

    private List<Track> argTracksToAdd;
    private Playlist argPlaylist;

    public TrackAddCommand(List<Track> tracksToAdd, Playlist targetPlaylist) {
        requireNonNull(tracksToAdd);
        requireNonNull(targetPlaylist);
        this.argTracksToAdd = tracksToAdd;
        this.argPlaylist = targetPlaylist;
    }

    @Override
    public CommandResult execute(Model model) {
        Playlist updatedPlaylist;
        Playlist actualPlaylist;
        List<Track> tracksToAdd = new ArrayList<Track>();
        List<Track> tracksNotAdded = new ArrayList<Track>();
        Track actualTrack = null;

        // check if playlist exists
        if (!model.hasPlaylist(argPlaylist)) {
            return new CommandResult(String.format(MESSAGE_PLAYLIST_DOES_NOT_EXIST, argPlaylist.getName()));
        }

        // violates law of demeter for read operation due to best access path to Playlist
        actualPlaylist = model.getLibrary().getPlaylistList()
                .filtered(playlist -> playlist.isSamePlaylist(argPlaylist))
                .get(0);

        updatedPlaylist = actualPlaylist.copy();

        // check if tracks exist
        // argTracksToAdd.stream().forEach(track -> System.out.println(track.getFileNameWithoutExtension()));
        for (Track trackToAdd : argTracksToAdd) {
            if (model.getFilteredTrackList().stream().anyMatch(track -> track.equals(trackToAdd))) {
                tracksToAdd.add(trackToAdd);
            } else {
                // to display as tracks that cannot be added
                tracksNotAdded.add(trackToAdd);
            }
        }

        for (Track trackToAdd : tracksToAdd) {
            actualTrack = model.getLibrary().getTracks().stream().filter(track -> track
                    .equals(trackToAdd)).findFirst().get();
            updatedPlaylist.addTrack(actualTrack);
        }

        model.updatePlaylist(actualPlaylist, updatedPlaylist);
        if (tracksNotAdded.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, tracksToAdd, actualPlaylist.getName()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS + "\n" + MESSAGE_TRACK_DOES_NOT_EXIST,
                tracksToAdd, actualPlaylist.getName(), tracksNotAdded));
    }

}
