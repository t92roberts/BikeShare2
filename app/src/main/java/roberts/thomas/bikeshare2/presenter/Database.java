package roberts.thomas.bikeshare2.presenter;

import java.util.Observable;

import io.realm.Realm;

/**
 * Created by Tom on 27/04/2018.
 */

public class Database extends Observable {
    private static Database sDatabase;
    private static Realm sRealm;

    private Database () {
        sRealm = Realm.getDefaultInstance();
    }

    public synchronized static Database get() {
        if (sDatabase == null) {
            // the singleton hasn't been created yet, initialise it
            sDatabase = new Database();
        }

        return sDatabase;
    }
}
