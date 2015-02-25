package tysonjmiller.github.conquer;

import android.os.Bundle;
import android.util.Log;

import com.google.inject.Inject;
import com.spotify.sdk.android.playback.ConnectionStateCallback;
import com.spotify.sdk.android.playback.Player;
import com.spotify.sdk.android.playback.PlayerNotificationCallback;
import com.spotify.sdk.android.playback.PlayerState;

import roboguice.fragment.RoboFragment;

/**
 * Created by Tyson Miller on 2/24/2015.
 */
public class BaseFragment extends RoboFragment implements
        PlayerNotificationCallback, ConnectionStateCallback {
    public static final String TAG = BaseFragment.class.getSimpleName();
    @Inject Player mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (mPlayer != null && mPlayer.isInitialized()) {
            mPlayer.addConnectionStateCallback(this);
            mPlayer.addPlayerNotificationCallback(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onLoggedIn() {
        Log.d(TAG, "onLoggedIn");
    }

    @Override
    public void onLoggedOut() {
        Log.d(TAG, "onLoggedOut");
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        Log.d(TAG, "onLoginFailed, reason: " + throwable.getMessage());
    }

    @Override
    public void onTemporaryError() {
        Log.d(TAG, "onTemporaryError");
    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d(TAG, "onConnectionMessage: " + s);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d(TAG, "onPlaybackEvent, type: " + eventType.name() + ", " + playerState.toString());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {
        Log.d(TAG, "onPlaybackError, type:" + errorType.name() + ", " + s);
    }
}
