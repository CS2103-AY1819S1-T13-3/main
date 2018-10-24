package seedu.jxmusic.player;

import javafx.util.Duration;

/**
 * The actual implemented player to be used by Logic component
 */
public class PlayerManager implements Player {
    private static PlayablePlaylist pp;
    @Override
    public void play() {
        // todo take in Playlist1 model as parameter, then construct PlayablePlaylist from it
        System.out.println("jxmusicplayer play");
        pp = new PlayablePlaylist();
        pp.play();
    }

    @Override
    public void stop() {
        System.out.println("jxmusicplayer stop");
        pp.stop();
    }
    @Override
    public void pause() {
        System.out.println("jxmusicplayer pause");
        pp.pause();
    }

    @Override
    public void seek(Duration time) {
        System.out.println("jxmusicplayer seek to " + time.toSeconds() + " second(s)");
        pp.seek(time);
    }
}
