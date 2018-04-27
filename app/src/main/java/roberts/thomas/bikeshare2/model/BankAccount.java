package roberts.thomas.bikeshare2.model;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class BankAccount extends RealmObject {
    @PrimaryKey
    private String mId;

    public final MutableRealmInteger mBalance;

    public BankAccount() {
        mBalance = MutableRealmInteger.valueOf(0);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
