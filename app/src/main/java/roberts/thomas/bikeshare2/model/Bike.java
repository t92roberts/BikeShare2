package roberts.thomas.bikeshare2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Bike extends RealmObject {
    @PrimaryKey
    public String mId;

    public String mType;
    public String mCurrentLocation;
    public int mPricePerHour;

    public boolean mIsBeingRidden;

    public byte[] mPhoto;

    public Bike() {

    }

    public Bike(String id, String type, String currentLocation, int pricePerHour, boolean isBeingRidden, byte[] photo) {
        mId = id;
        mType = type;
        mCurrentLocation = currentLocation;
        mPricePerHour = pricePerHour;
        mIsBeingRidden = isBeingRidden;
        mPhoto = photo;
    }

    public String getPhotoFileName() {
        return "IMG_" + mId + ".jpg";
    }

    public String toString() {
        return mType + " that costs " + mPricePerHour + " kr per hour" +
                " and is currently" + (mIsBeingRidden ? " being ridden" : " at ") + mCurrentLocation;
    }
}
