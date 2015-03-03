package tysonjmiller.github.conquer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.inject.Inject;
import com.spotify.sdk.android.Spotify;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.playback.Config;
import com.spotify.sdk.android.playback.ConnectionStateCallback;
import com.spotify.sdk.android.playback.Player;
import com.spotify.sdk.android.playback.PlayerNotificationCallback;
import com.spotify.sdk.android.playback.PlayerState;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by Tyson Miller on 2/23/2015.
 */
public class LoginActivity extends RoboActivity implements
        PlayerNotificationCallback, ConnectionStateCallback {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private Config mPlayerConfig;
    private Player mPlayer;
    @InjectView(R.id.spotify_login_button) ImageView mSpotifyLoginImageView;
    @InjectView(R.id.no_login_button) Button mNoLoginButton;
    @Inject UserDAO mUserDAO;
    @Inject MediaDAO mMediaDAO;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_to_spotify);
        mPlayer = null;
        mPlayerConfig = null;
        mSpotifyLoginImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotifyUtils.authenticateSpotify(LoginActivity.this);
            }
        });
        mNoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Skipping spotify login, opening MainActivity");
                mUserDAO.setIsUsingSpotify(false);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == Constants.SPOTIFY_LOGIN_REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                mUserDAO.setSpotifyAuthResponse(response);
                mUserDAO.setIsUsingSpotify(true);

                mPlayerConfig = new Config(this, response.getAccessToken(), Constants.SPOTIFY_CLIENT_ID);
                mMediaDAO.setSpotifyPlayerConfig(mPlayerConfig);

                Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(newIntent);
            }
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("LoginActivity", "User logged in. Starting MainActivity");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoggedOut() {
        Log.d("LoginActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("LoginActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("LoginActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("LoginActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("LoginActivity", "Playback event received: " + eventType.name());
        switch (eventType) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("LoginActivity", "Playback error received: " + errorType.name());
        switch (errorType) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();

    }
}