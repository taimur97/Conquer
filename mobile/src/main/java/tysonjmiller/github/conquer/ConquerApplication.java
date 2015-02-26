package tysonjmiller.github.conquer;

import android.app.Application;

/**
 * Created by Tyson Miller on 2/25/2015.
 */
public class ConquerApplication extends Application {
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
    }
}
