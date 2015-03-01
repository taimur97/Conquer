package tysonjmiller.github.conquer;

import android.media.MediaPlayer;
import android.util.Log;

import com.google.inject.Singleton;
import com.spotify.sdk.android.playback.Config;
import com.spotify.sdk.android.playback.Player;

import java.util.ArrayList;

/**
 * Created by Tyson Miller on 2/25/2015. A single instance can be used in the whole app.
 */
@Singleton
public class MediaDAO {
    public static final String TAG = MediaDAO.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    private Player mSpotifyPlayer;
    private Config mPlayerConfig;
    private ArrayList<Song> mSongList;

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
        if (mMediaPlayer == null) {
            Log.e(TAG, "***** Media Player is null!!!!! *****");
            mMediaPlayer = new MediaPlayer();
        }
        return mMediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mMediaPlayer = mediaPlayer;
    }

    public void stopAndReleaseMediaPlayer(){
        if (mMediaPlayer == null) {
            return;
        } else if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
    }

    public ArrayList<Song> getSongList() {
        return mSongList;
    }

    public void setSongList(ArrayList<Song> mSongList) {
        this.mSongList = mSongList;
    }

    public void clearSongList(){
        if (mSongList == null) {
            mSongList = new ArrayList<Song>();
        } else {
            mSongList.clear();
        }
    }

    public void addSongToList(Song song){
        if (mSongList == null) {
            mSongList = new ArrayList<Song>();
        }
        mSongList.add(song);
    }

    public void addSongsToList(ArrayList<Song> songs) {
        if (mSongList == null) {
            mSongList = new ArrayList<Song>();
        }
        mSongList.addAll(songs);
    }
}
