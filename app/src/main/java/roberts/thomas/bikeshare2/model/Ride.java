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

    public Bike mBike;
    public Customer mCustomer;

    public Date mStartTime;
    public String mStartLocation;

    public Date mEndTime;
    public String mEndLocation;

    public boolean mIsActive;
    public int mRideTotalPrice;

    public Ride(String id, Bike bike, Customer customer, String startLocation) {
        mId = id;
        mBike = bike;
        mCustomer = customer;

        mStartTime = new Date();
        mStartLocation = startLocation;

        mIsActive = true;
    }

    public void endRide(String endLocation) {
        mEndTime = new Date();
        mEndLocation = endLocation;

        mIsActive = false;
        mRideTotalPrice = calculateRidePrice();
    }

    private int calculateRidePrice() {
        long milliseconds = mEndTime.getTime() - mStartTime.getTime();
        long hours = milliseconds / 3600000;
        long price = hours * mBike.mPricePerHour;
        return (int) price;
    }
}
