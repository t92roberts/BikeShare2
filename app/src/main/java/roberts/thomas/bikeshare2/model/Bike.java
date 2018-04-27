package roberts.thomas.bikeshare2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Bike extends RealmObject {
    @PrimaryKey
    private String mId;

    private String mBikeType;
    private byte[] mPhoto;
    private int mPricePerHour;
    private boolean mInUse;

    public Bike() {

    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getBikeType() {
        return mBikeType;
    }

    public void setBikeType(String bikeType) {
        mBikeType = bikeType;
    }

    public byte[] getPhoto() {
        return mPhoto;
    }

    public void setPhoto(byte[] photo) {
        mPhoto = photo;
    }

    public int getPricePerHour() {
        return mPricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        mPricePerHour = pricePerHour;
    }

    public boolean isInUse() {
        return mInUse;
    }

    public void setInUse(boolean inUse) {
        this.mInUse = inUse;
    }
}
