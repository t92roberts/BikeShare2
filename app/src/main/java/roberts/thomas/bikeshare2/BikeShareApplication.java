package roberts.thomas.bikeshare2;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Tom on 27/04/2018.
 */

public class BikeShareApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
