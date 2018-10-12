package seedu.jxmusic.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_ALIEZ;
import static seedu.jxmusic.testutil.TypicalPlaylists.ANIME;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jxmusic.model.exceptions.DuplicatePlaylistException;
import seedu.jxmusic.testutil.PlaylistBuilder;
import seedu.jxmusic.testutil.TypicalPlaylists;

public class LibraryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Library library = new Library();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), library.getPlaylistList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        library.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyLibrary_replacesData() {
        Library newData = TypicalPlaylists.getTypicalLibrary();
        library.resetData(newData);
        assertEquals(newData, library);
    }

    @Test
    public void resetData_withDuplicatePlaylists_throwsDuplicatePlaylistException() {
        // Two playlists with the same identity fields
        Playlist editedAlice = new PlaylistBuilder(ANIME).withTracks(VALID_TRACK_ALIEZ)
                .build();
        List<Playlist> newPlaylists = Arrays.asList(ANIME, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPlaylists);

        thrown.expect(DuplicatePlaylistException.class);
        library.resetData(newData);
    }

    @Test
    public void hasPlaylist_nullPlaylist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        library.hasPlaylist(null);
    }

    @Test
    public void hasPlaylist_playlistNotInAddressBook_returnsFalse() {
        assertFalse(library.hasPlaylist(ANIME));
    }

    @Test
    public void hasPlaylist_playlistInAddressBook_returnsTrue() {
        library.addPlaylist(ANIME);
        assertTrue(library.hasPlaylist(ANIME));
    }

    @Test
    public void hasPlaylist_playlistWithSameIdentityFieldsInAddressBook_returnsTrue() {
        library.addPlaylist(ANIME);
        Playlist editedAlice = new PlaylistBuilder(ANIME).withTracks(VALID_TRACK_ALIEZ)
                .build();
        assertTrue(library.hasPlaylist(editedAlice));
    }

    @Test
    public void getPlaylistList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        library.getPlaylistList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose playlists list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyLibrary {
        private final ObservableList<Playlist> playlists = FXCollections.observableArrayList();

        AddressBookStub(Collection<Playlist> playlists) {
            this.playlists.setAll(playlists);
        }

        @Override
        public ObservableList<Playlist> getPlaylistList() {
            return playlists;
        }
    }

}