package tysonjmiller.github.conquer;

import com.google.inject.Singleton;
import com.spotify.sdk.android.playback.Config;
import com.spotify.sdk.android.playback.Player;

/**
 * Created by Tyson Miller on 2/25/2015. A single instance can be used in the whole app.
 */
@Singleton
public class MediaDAO {
    private Player mPlayer;
    private Config mPlayerConfig;

    public MediaDAO() {
        // empty constructor to prevent Roboguice issues
    }

    public Player getSpotifyPlayer() {
        return mPlayer;
    }

    public void setSpotifyPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    public Config getSpotifyPlayerConfig() {
        return mPlayerConfig;
    }

    public void setSpotifyPlayerConfig(Config mPlayerConfig) {
        this.mPlayerConfig = mPlayerConfig;
    }
}
