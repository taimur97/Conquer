package tysonjmiller.github.conquer;

import com.google.inject.Singleton;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

/**
 * Created by Tyson Miller on 2/25/2015. A single instance can be used in the whole app
 */
@Singleton
public class UserDAO {
    private boolean mUsingSpotify;
    private AuthenticationResponse mSpotifyAuthResponse;

    public UserDAO(){
        // empty constructor to prevent Roboguice issues
    }

    public boolean isUsingSpotify() {
        return mUsingSpotify;
    }

    public void setIsUsingSpotify(boolean usingSpotify) {
        this.mUsingSpotify = usingSpotify;
    }

    public AuthenticationResponse getSpotifyAuthResponse() {
        return mSpotifyAuthResponse;
    }

    public void setSpotifyAuthResponse(AuthenticationResponse mSpotifyAuthResponse) {
        this.mSpotifyAuthResponse = mSpotifyAuthResponse;
    }
}