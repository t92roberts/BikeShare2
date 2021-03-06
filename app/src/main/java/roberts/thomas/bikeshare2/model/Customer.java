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

    public static final String TIME_FORMAT_PATTERN = "HH:mm";
    public static final String DATE_FORMAT_PATTERN = "EEE d MMM yyyy";

    public Customer() {
        mAccountBalance = MutableRealmInteger.valueOf(0);
    }

    public Customer(String id, String firstName, String lastName, int accountBalance) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mAccountBalance = MutableRealmInteger.valueOf(accountBalance);
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public void takePayment(int amount) {
        mAccountBalance.decrement(amount);
    }

    public String toString() {
        return getFullName() + " has a balance of " + mAccountBalance.get().toString() + " kr";
    }
}
