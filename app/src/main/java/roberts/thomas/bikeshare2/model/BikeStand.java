package roberts.thomas.bikeshare2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tom on 01/05/2018.
 */

public class BikeStand extends RealmObject {
    @PrimaryKey
    public String mId;

    public String mName;
    public Location mLocation;

    public BikeStand() {

    }

    public BikeStand(String id, String name, Location location) {
        mId = id;
        mName = name;
        mLocation = location;
    }

    public String toString() {
        return mName + " is located at " + mLocation.mLongitude + ", " + mLocation.mLatitude;
    }
}
