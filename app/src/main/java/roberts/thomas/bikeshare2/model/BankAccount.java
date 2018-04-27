package roberts.thomas.bikeshare2.model;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class BankAccount extends RealmObject {
    @PrimaryKey
    public String mId;

    public final MutableRealmInteger mBalance;

    public BankAccount(String id) {
        mId = id;
        mBalance = MutableRealmInteger.valueOf(0);
    }

    public BankAccount(String id, int startingBalance) {
        mId = id;
        mBalance = MutableRealmInteger.valueOf(startingBalance);
    }
}
