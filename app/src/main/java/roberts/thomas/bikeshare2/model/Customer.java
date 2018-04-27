package roberts.thomas.bikeshare2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Customer extends RealmObject {
    @PrimaryKey
    public String mId;

    public String mFirstName, mLastName;
    public BankAccount mBankAccount;

    public Customer(String id, String firstName, String lastName, BankAccount bankAccount) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mBankAccount = bankAccount;
    }
}
