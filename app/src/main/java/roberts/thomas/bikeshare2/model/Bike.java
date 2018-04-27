package roberts.thomas.bikeshare2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Bike extends RealmObject {
    @PrimaryKey
    public String mId;

    public String mBikeType;
    public byte[] mPhoto;
    public int mPricePerHour;
    public boolean mInUse;

    public Bike(String id, String bikeType, byte[] photo, int pricePerHour) {
        mId = id;
        mBikeType = bikeType;
        mPhoto = photo;
        mPricePerHour = pricePerHour;
        mInUse = false;
    }

    public Bike(String id, String bikeType, byte[] photo, int pricePerHour, boolean inUse) {
        mId = id;
        mBikeType = bikeType;
        mPhoto = photo;
        mPricePerHour = pricePerHour;
        mInUse = inUse;
    }
}
