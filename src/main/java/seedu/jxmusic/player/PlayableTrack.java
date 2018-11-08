package seedu.jxmusic.player;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import seedu.jxmusic.model.Track;

/**
 * Playlist1 structure used by Player
 */
public class PlayableTrack implements Playable {
    private MediaPlayer mediaPlayer;
    private String fileName;
    private Track track;

    public PlayableTrack(Track track) {
        Media media = new Media(track.getFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            System.out.println("ready");
        });
        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("end of media");
        });
    }

    public String getTrackName() {
        return track.getFileNameWithoutExtension();
    }

    @Override
    public void play(boolean unpause) {
        System.out.println("playabletrack play");
        if (!unpause) {
            mediaPlayer.setStartTime(new Duration(0));
        }
        mediaPlayer.play();
    }

    @Override
    public void stop() {
        System.out.println("playabletrack stop");
        mediaPlayer.stop();
    }

    @Override
    public void pause() {
        System.out.println("playabletrack pause");
        mediaPlayer.pause();
    }

    @Override
    public Status getStatus() {
        switch (mediaPlayer.getStatus()) {
        case PLAYING: return Status.PLAYING;
        case PAUSED: return Status.PAUSED;
        case STOPPED: return Status.STOPPED;
        default: return Status.ERROR;
        }
    }

    @Override
    public void seek(Duration time) {
        System.out.println("playabletrack seek to " + time.toSeconds() + " second(s)");
        mediaPlayer.seek(time);
    }

    public void setOnEndOfMedia(Runnable runnable) {
        mediaPlayer.setOnEndOfMedia(runnable);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
