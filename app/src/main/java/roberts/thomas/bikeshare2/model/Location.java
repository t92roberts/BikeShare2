package roberts.thomas.bikeshare2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 01/05/2018.
 */

public class Location extends RealmObject {

    @PrimaryKey
    public String mId;

    public double mLatitude, mLongitude;

    public Location() {

    }

    public Location(String id, double latitude, double longitude) {
        mId = id;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double distanceTo(Location targetLocation) {
        return Math.sqrt(Math.pow(mLatitude - targetLocation.mLatitude, 2) + Math.pow(mLongitude - targetLocation.mLongitude, 2));
    }

    public static double distanceBetween(Location location1, Location location2) {
        return Math.sqrt(Math.pow(location1.mLatitude - location2.mLatitude, 2) + Math.pow(location1.mLongitude - location2.mLongitude, 2));
    }

    public String toString() {
        return String.valueOf(mLatitude) + ", " + String.valueOf(mLongitude);
    }
}
