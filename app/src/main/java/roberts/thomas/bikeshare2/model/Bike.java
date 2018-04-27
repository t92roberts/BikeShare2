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
    public int mPricePerHour;

    public byte[] mPhoto;

    public Bike(String id, String bikeType, int pricePerHour) {
        mId = id;
        mBikeType = bikeType;
        mPricePerHour = pricePerHour;
    }

    public Bike(String id, String bikeType, int pricePerHour, byte[] photo) {
        mId = id;
        mBikeType = bikeType;
        mPricePerHour = pricePerHour;
        mPhoto = photo;
    }
}
