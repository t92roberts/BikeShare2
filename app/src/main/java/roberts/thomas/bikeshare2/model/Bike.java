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
    public String mCurrentLocation;
    public int mPricePerHour;

    public byte[] mPhoto;

    public boolean mIsBeingRidden;

    public Bike() {

    }

    public Bike(String id, String bikeName, String bikeType, String currentLocation, int pricePerHour, byte[] photo, boolean isBeingRidden) {
        mId = id;
        mBikeName = bikeName;
        mBikeType = bikeType;
        mCurrentLocation = currentLocation;
        mPricePerHour = pricePerHour;
        mPhoto = photo;
        mIsBeingRidden = isBeingRidden;
    }

    public String toString() {
        return mBikeName +
                " is a " + mBikeType +
                " that costs " + mPricePerHour + " kr per hour" +
                " and is currently" + (mIsBeingRidden ? " being ridden" : " at ") + mCurrentLocation;
    }
}
