package roberts.thomas.bikeshare2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Customer extends RealmObject {
    @PrimaryKey
    private String mId;

    private String mFirstName, mLastName;
    private BankAccount mBankAccount;

    public Customer() {

    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public BankAccount getBankAccount() {
        return mBankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        mBankAccount = bankAccount;
    }
}
