package tysonjmiller.github.conquer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.google.inject.Inject;

import java.io.IOException;

/**
 * Created by Tyson Miller on 2/28/2015.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener {
    public static final String TAG = MusicService.class.getSimpleName();

    @Inject MediaDAO mMediaDAO;
    MediaPlayer mMediaPlayer = null;

    /**
     * Init and start the media player, using either local URI or URL for streaming. For local, make sure
     * the intent contains LOCAL_SONG_URI set. Otherwise include REMOTE_SONG_URI for streaming from a remote URL.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        if (intent.getAction().equals(Constants.ACTION_PLAY)) {
            initAndStartMediaPlayer(intent);
        } else if (intent.getAction().equals(Constants.ACTION_PAUSE)) {
            mMediaDAO.getMediaPlayer().pause();
        } else if (intent.getAction().equals(Constants.ACTION_STOP)) {
            mMediaDAO.stopAndReleaseMediaPlayer();
            stopSelf();
        }



        return START_STICKY;
    }

    private void initAndStartMediaPlayer(Intent intent){
        String localUri = intent.getStringExtra(Constants.LOCAL_SONG_URI);
        String remoteUrl = intent.getStringExtra(Constants.REMOTE_SONG_URI);
        boolean mediaOnDevice = !StringUtils.isNullOrEmpty(localUri);
        if (!mediaOnDevice && StringUtils.isNullOrEmpty(remoteUrl)) {
            Log.e(TAG, "No valid media source found!");
            return;
        }
        mMediaPlayer = new MediaPlayer(); // initialize it here
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        try {
            if (mediaOnDevice){
                mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(localUri));
            } else {
                mMediaPlayer.setDataSource(remoteUrl);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException while setting media data source!");
        }
        mMediaPlayer.setOnCompletionListener((MediaPlayer.OnCompletionListener) this);
        mMediaPlayer.prepareAsync(); // prepare async to not block main thread
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
        mMediaDAO.stopAndReleaseMediaPlayer();
    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
        Log.d(TAG, "MediaPlayer prepared");
        mMediaDAO.setMediaPlayer(player);
        mMediaDAO.getMediaPlayer().start();
        MediaUtils.showNotification(this, "test", "test");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mMediaDAO.getMediaPlayer() == null) return;
                else if (!mMediaDAO.getMediaPlayer().isPlaying()) mMediaDAO.getMediaPlayer().start();
                mMediaDAO.getMediaPlayer().setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                mMediaDAO.stopAndReleaseMediaPlayer();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mMediaDAO.getMediaPlayer().isPlaying()) mMediaDAO.getMediaPlayer().pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mMediaDAO.getMediaPlayer().isPlaying()) mMediaDAO.getMediaPlayer().setVolume(0.1f, 0.1f);
                break;
        }
    }
}