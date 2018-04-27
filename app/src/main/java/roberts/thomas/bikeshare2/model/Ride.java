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

    public Date mStartTime;
    public String mStartLocation;

    public Date mEndTime;
    public String mEndLocation;

    public boolean mIsActive;
    public int mTotalPrice;

    public Ride() {

    }

    public Ride(String id, Bike bike, Customer customer, String startLocation) {
        mId = id;
        mBike = bike;
        mCustomer = customer;

        mStartTime = new Date();
        mStartLocation = startLocation;

        mIsActive = true;
        mBike.mIsBeingRidden = true;
    }

    public boolean endRide(String endLocation) {
        mEndTime = new Date();
        mEndLocation = endLocation;

        mIsActive = false;
        mBike.mIsBeingRidden = false;

        mTotalPrice = calculateRidePrice();

        return mCustomer.takePayment(mTotalPrice);
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

    private String getFormattedStartTime() {
        return new SimpleDateFormat(mCustomer.TIME_FORMAT_PATTERN).format(mStartTime);
    }

    private String getFormattedStartDate() {
        return new SimpleDateFormat(mCustomer.DATE_FORMAT_PATTERN).format(mStartTime);
    }

    private String getFormattedEndTime() {
        return new SimpleDateFormat(mCustomer.TIME_FORMAT_PATTERN).format(mEndTime);
    }

    private String getFormattedEndDate() {
        return new SimpleDateFormat(mCustomer.DATE_FORMAT_PATTERN).format(mEndTime);
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
