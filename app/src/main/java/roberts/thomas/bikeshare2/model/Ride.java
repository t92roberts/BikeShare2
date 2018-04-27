package roberts.thomas.bikeshare2.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Ride extends RealmObject {
    @PrimaryKey
    public String mId;

    public String mBikeId;
    public String mCustomerId;

    public boolean active;

    public String mStartLocation;
    public Date mStartTime;

    public String mEndLocation;
    public Date mEndTime;

    public int mBikePricePerHour;
    public int mTotalRidePrice;

    public Ride(String id, String bikeId, int bikePricePerHour, String customerId) {
        mId = id;
        mBikeId = bikeId;
        mBikePricePerHour = bikePricePerHour;
        mCustomerId = customerId;
    }

    public void startRide(String startLocation) {
        mStartLocation = startLocation;
        mStartTime = new Date();
        mTotalRidePrice = 0;
    }

    public void endRide(String endLocation) {
        mEndLocation = endLocation;
        mEndTime = new Date();
        mTotalRidePrice = calculateRidePrice();
    }

    private int calculateRidePrice() {
        int hours = (int)(mEndTime.getTime() - mStartTime.getTime()) / 3600000;
        return hours * mBikePricePerHour;
    }
}
