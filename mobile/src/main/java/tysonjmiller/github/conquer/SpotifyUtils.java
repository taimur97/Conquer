package tysonjmiller.github.conquer;

import android.app.Activity;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.playback.Player;

/**
 * Created by Tyson Miller on 2/24/2015.
 */
public class SpotifyUtils {

    public static void authenticateSpotify(Activity callback){
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(Constants.SPOTIFY_CLIENT_ID, AuthenticationResponse.Type.TOKEN, Constants.SPOTIFY_REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(callback, Constants.SPOTIFY_LOGIN_REQUEST_CODE, request);
    }

    public static boolean isSpotifyPlayerValid(Player player){
        return (player != null && player.isInitialized() && player.isLoggedIn());
    }
}
