package tysonjmiller.github.conquer;

import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Injector;

import roboguice.RoboGuice;

/**
 * Created by Tyson Miller on 2/25/2015.
 */
public class ConquerApplication extends Application {
    public static final String TAG = ConquerApplication.class.getSimpleName();

    @Inject MediaDAO mMediaDAO;
    @Inject UserDAO mUserDAO;
    @Inject MusicService mMusicService;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the singletons so their instances are bound to the application process.
        initSingletons();
    }

    protected void initSingletons() {
        Injector injector = RoboGuice.getOrCreateBaseApplicationInjector(this);
        mMediaDAO = injector.getInstance(MediaDAO.class);
        mUserDAO = injector.getInstance(UserDAO.class);
        mMusicService = injector.getInstance(MusicService.class);

        Log.d(TAG, "Singletons initialized");
}
}
