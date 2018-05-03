package roberts.thomas.bikeshare2.model;

import java.text.SimpleDateFormat;
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

    public BikeStand mStartLocation;
    public Date mStartTime;

    public BikeStand mEndLocation;
    public Date mEndTime;

    public boolean mIsActive;
    public int mTotalPrice;

    public Ride() {

    }

    public Ride(String id, Bike bike, Customer customer, BikeStand startLocation) {
        mId = id;
        mBike = bike;
        mCustomer = customer;

        mStartLocation = startLocation;
        mStartTime = new Date();

        mIsActive = true;
    }

    public void endRide(BikeStand endLocation) {
        mEndTime = new Date();
        mEndLocation = endLocation;

        mIsActive = false;

        /////////////////////////////////////////////////////////////////
        // Change - can't modify a Realm object outside of a transaction
        mBike.mIsBeingRidden = false;
        /////////////////////////////////////////////////////////////////

        mTotalPrice = calculateRidePrice();
        mCustomer.takePayment(mTotalPrice);
    }

    private int calculateRidePrice() {
        long milliseconds = mEndTime.getTime() - mStartTime.getTime();
        long hours = (milliseconds / 3600000);

        if (hours < 1) {
            // Charge a minimum of 1 hour
            return mBike.mPricePerHour;
        } else {
            return (int)(hours * mBike.mPricePerHour);
        }
    }

    public String getFormattedStartDate() {
        return new SimpleDateFormat(mCustomer.DATE_FORMAT_PATTERN).format(mStartTime);
    }

    public String getFormattedStartTime() {
        return new SimpleDateFormat(mCustomer.TIME_FORMAT_PATTERN).format(mStartTime);
    }

    public String getFormattedEndDate() {
        return new SimpleDateFormat(mCustomer.DATE_FORMAT_PATTERN).format(mEndTime);
    }

    public String getFormattedEndTime() {
        return new SimpleDateFormat(mCustomer.TIME_FORMAT_PATTERN).format(mEndTime);
    }

    public String toString() {
        if (mIsActive) {
            return mCustomer.getFullName() +
                    " is riding bike " + mBike.mId + " from " + mStartLocation +
                    " at " + getFormattedStartTime() + " on " + getFormattedStartDate();
        } else {
            return mCustomer.getFullName() +
                    " rode bike " + mBike.mId + " from " + mEndLocation +
                    " at " + getFormattedStartTime() + " on " + getFormattedStartDate() +
                    ", to " + mEndLocation +
                    " at " + getFormattedEndTime() + " on " + getFormattedEndDate();
        }
    }
}
