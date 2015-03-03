package tysonjmiller.github.conquer;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by Tyson Miller on 3/2/2015.
 */
public class ConquerRoboguiceModule extends AbstractModule {

    @Inject
    public ConquerRoboguiceModule() {
        super();
    }

    @Override
    protected void configure() {
        // Data Accessor Objects
        bind(MediaDAO.class).in(Singleton.class);
        bind(UserDAO.class).in(Singleton.class);
    }
}
