package roberts.thomas.bikeshare2.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 27/04/2018.
 */

public class Ride extends RealmObject {
    @PrimaryKey
    private String mId;

    private Bike mBike;
    private Customer mCustomer;

    private String mStartLocation;
    private Date mStartTime;

    private String mEndLocation;
    private Date mEndTime;

    private int mRidePrice;

    public Ride() {

    }

    public boolean isActive() {
        return mEndLocation == null || mEndTime == null;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Bike getBike() {
        return mBike;
    }

    public void setBike(Bike bike) {
        this.mBike = bike;
    }

    public Customer getCustomer() {
        return mCustomer;
    }

    public void setCustomer(Customer customer) {
        mCustomer = customer;
    }

    public String getStartLocation() {
        return mStartLocation;
    }

    public void setStartLocation(String startLocation) {
        mStartLocation = startLocation;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Date startTime) {
        mStartTime = startTime;
    }

    public String getEndLocation() {
        return mEndLocation;
    }

    public void setEndLocation(String endLocation) {
        mEndLocation = endLocation;
    }

    public Date getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Date endTime) {
        mEndTime = endTime;
    }

    public int getRidePrice() {
        return mRidePrice;
    }

    public void setRidePrice(int ridePrice) {
        mRidePrice = ridePrice;
    }
}
