package tysonjmiller.github.conquer;

import android.media.MediaPlayer;

import com.google.inject.Singleton;
import com.spotify.sdk.android.playback.Config;
import com.spotify.sdk.android.playback.Player;

/**
 * Created by Tyson Miller on 2/25/2015. A single instance can be used in the whole app.
 */
@Singleton
public class MediaDAO {
    private MediaPlayer mMediaPlayer;
    private Player mSpotifyPlayer;
    private Config mPlayerConfig;

    public MediaDAO() {
        // empty constructor to prevent Roboguice issues
    }

    public Player getSpotifyPlayer() {
        return mSpotifyPlayer;
    }

    public void setSpotifyPlayer(Player mPlayer) {
        this.mSpotifyPlayer = mPlayer;
    }

    public Config getSpotifyPlayerConfig() {
        return mPlayerConfig;
    }

    public void setSpotifyPlayerConfig(Config mPlayerConfig) {
        this.mPlayerConfig = mPlayerConfig;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mMediaPlayer = mediaPlayer;
    }

    // TODO: Implement these methods
    public boolean start(){
        return false;
    }

    public boolean pause() {
        return false;
    }

    public boolean nextSong(){
        return false;
    }
}
