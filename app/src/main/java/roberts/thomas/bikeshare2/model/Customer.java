package roberts.thomas.bikeshare2.model;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Customer extends RealmObject {
    @PrimaryKey
    public String mId;

    public String mFirstName, mLastName;
    public final MutableRealmInteger mAccountBalance;

    public Customer(String id, String firstName, String lastName) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mAccountBalance = MutableRealmInteger.valueOf(0);
    }

    public Customer(String id, String firstName, String lastName, int startingBalance) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mAccountBalance = MutableRealmInteger.valueOf(startingBalance);
    }
}
