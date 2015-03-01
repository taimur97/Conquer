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
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
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
            String localUri = intent.getStringExtra(Constants.LOCAL_SONG_URI);
            String remoteUrl = intent.getStringExtra(Constants.REMOTE_SONG_URI);
            boolean mediaOnDevice = !StringUtils.isNullOrEmpty(localUri);
            if (!mediaOnDevice && StringUtils.isNullOrEmpty(remoteUrl)) {
                Log.e(TAG, "No valid media source found!");
                return -1;
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
            mMediaPlayer.setOnCompletionListener();
            mMediaPlayer.prepareAsync(); // prepare async to not block main thread
        }
        return startId;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
        Log.d(TAG, "MediaPlayer prepared, starting!");
        mMediaDAO.setMediaPlayer(player);
        mMediaDAO.getMediaPlayer().start();
        MediaUtils.showNotification(this, player.getTrackInfo().);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    };
}