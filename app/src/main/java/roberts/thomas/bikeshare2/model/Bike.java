package roberts.thomas.bikeshare2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Bike extends RealmObject {
    @PrimaryKey
    public String mId;

    public String mBikeName;
    public String mBikeType;
    public int mPricePerHour;

    public byte[] mPhoto;

    public boolean mIsBeingRidden;

    public Bike() {

    }

    public Bike(String id, String bikeName, String bikeType, int pricePerHour) {
        mId = id;
        mBikeName = bikeName;
        mBikeType = bikeType;
        mPricePerHour = pricePerHour;
        mIsBeingRidden = false;
    }

    public Bike(String id, String bikeName, String bikeType, int pricePerHour, byte[] photo) {
        mId = id;
        mBikeName = bikeName;
        mBikeType = bikeType;
        mPricePerHour = pricePerHour;
        mPhoto = photo;
        mIsBeingRidden = false;
    }

    public String toString() {
        return mBikeName +
                " is a " + mBikeType +
                " that costs " + mPricePerHour + " kr per hour" +
                " and is currently " + (mIsBeingRidden ? "" : "not") +" being ridden";
    }
}
