package tysonjmiller.github.conquer;

import android.app.Application;

import com.google.inject.Inject;

import roboguice.RoboGuice;

/**
 * Created by Tyson Miller on 2/25/2015.
 */
public class ConquerApplication extends Application {
    @Inject MediaDAO mMediaDao;
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    protected void initSingletons() {
        // Initialize the instance of MySingleton
        //MySingleton.initInstance();
        RoboGuice.getInjector(this).injectMembers(this);
        mMediaDao = new MediaDAO();
}
}
