package roberts.thomas.bikeshare2.presenter;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import roberts.thomas.bikeshare2.model.Bike;
import roberts.thomas.bikeshare2.model.BikeStand;
import roberts.thomas.bikeshare2.model.Customer;
import roberts.thomas.bikeshare2.model.Location;
import roberts.thomas.bikeshare2.model.Ride;

/**
 * Created by Tom on 27/04/2018.
 */

public class Database {
    private Context mContext;
    private static Database sDatabase;
    private static Realm sRealm;

    public static Database get(Context context) {
        if (sDatabase == null) {
            // the singleton hasn't been created yet, initialise it
            sDatabase = new Database(context);
        }

        return sDatabase;
    }

    private Database (Context context) {
        mContext = context.getApplicationContext();
        sRealm = Realm.getDefaultInstance();

        testData(mContext, false);
    }

    public void testData(Context context, boolean displayToasts) {
        Location ituLoc = new Location("itu", 55.659011d, 12.590795d);
        BikeStand itu = new BikeStand("itu", "ITU", ituLoc);
        createOrUpdateBikeStand(itu, context, displayToasts);

        Location npLoc = new Location("np",55.683383d, 12.571811d);
        BikeStand np = new BikeStand("np", "Nørreport", npLoc);
        createOrUpdateBikeStand(np, context, displayToasts);

        Location knLoc = new Location("kn",55.679551d, 12.585150d);
        BikeStand kn = new BikeStand("kn", "Kongens Nytorv", knLoc);
        createOrUpdateBikeStand(kn, context, displayToasts);

        Bike bike1 = new Bike("test1", "Test bike 1", itu, 75, false);
        createOrUpdateBike(bike1, context, displayToasts);

        Bike bike2 = new Bike("test2", "Test bike 2", np, 50, true);
        createOrUpdateBike(bike2, context, displayToasts);

        Customer johnSmith = new Customer("johnSmith", "John", "Smith", 100);
        createOrUpdateCustomer(johnSmith, context, displayToasts);

        /*Ride ride1 = new Ride(UUID.randomUUID().toString(), bike1, customer1, "ITU");
        createOrUpdateRide(ride1, context, displayToasts);

        boolean success1 = ride1.endRide("Nørreport");
        if (displayToasts) {
            if (success1) {
                Toast.makeText(context,
                        ride1.mTotalPrice + " kr deducted from account",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,
                        "Unable to deduct " + ride1.mTotalPrice + " kr from account",
                        Toast.LENGTH_SHORT).show();
            }
        }

        Ride ride2 = new Ride(UUID.randomUUID().toString(), bike1, customer1, "Nørreport");
        createOrUpdateRide(ride2, context, displayToasts);

        boolean success2 = ride2.endRide("Home");
        if (displayToasts) {
            if (success2) {
                Toast.makeText(context,
                        ride2.mTotalPrice + " kr deducted from account",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,
                        "Unable to deduct " + ride2.mTotalPrice + " kr from account",
                        Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //  BIKE STAND
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void createOrUpdateBikeStand(final BikeStand bikeStand, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bikeStand);

                if (displayToast) {
                    Toast.makeText(context,
                            "Added bike stand: " + bikeStand.mLocation
                                    + " at " + bikeStand,
                            Toast.LENGTH_LONG);
                }
            }
        });
    }

    public BikeStand getBikeStand(String bikeStandId) {
        return sRealm.where(BikeStand.class)
                .equalTo("mId", bikeStandId)
                .findFirst();
    }

    public List<BikeStand> getAllBikeStands() {
        return sRealm.where(BikeStand.class)
                .findAll();
    }

    public Map<String, String> getAllBikeStandNames() {
        final Map<String, String> bikeStandNames = new HashMap<>();

        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<BikeStand> bikeStands = realm.where(BikeStand.class)
                        .findAll();

                if (bikeStands.size() > 0) {
                    //for (int i = 0; i < bikeStands.size(); i++) {
                    for (BikeStand bikeStand : bikeStands) {
                        String bikeStandId = bikeStand.mId;
                        String bikeStandName = bikeStand.mName;
                        bikeStandNames.put(bikeStandId, bikeStandName);
                    }
                }
            }
        });

        return bikeStandNames;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //  BIKE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void createOrUpdateBike(final Bike bike, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bike);

                if (displayToast) {
                    Toast.makeText(context,
                            "Added " + bike.mType,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Bike getBike(String bikeId) {
        return sRealm.where(Bike.class)
                .equalTo("mId", bikeId)
                .findFirst();
    }

    public List<Bike> getAllBikes() {
        return sRealm.where(Bike.class)
                .findAll();
    }

    public List<Bike> getAllBikes(boolean isBeingRidden) {
        return sRealm.where(Bike.class)
                .equalTo("mIsBeingRidden", isBeingRidden)
                .findAll();
    }

    public void deleteBike(final String bikeId, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Bike> bikes = realm.where(Bike.class)
                        .equalTo("mId", bikeId)
                        .findAll();

                if (bikes.size() > 0) {
                    if (displayToast) {
                        Toast.makeText(context,
                                "Deleted bike: " + bikes.first().mId,
                                Toast.LENGTH_LONG).show();
                    }
                    bikes.deleteFirstFromRealm();
                }
            }
        });
    }

    public void setBikeActivityFlag(final Bike bike, final boolean isBeingRidden, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                bike.mIsBeingRidden = true;
                realm.copyToRealmOrUpdate(bike);

                if (displayToast) {
                    Toast.makeText(context,
                            "Bike: " + bike.mType + " is now " + (!isBeingRidden ? "not " : "") + "being ridden",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public File getBikePhotoFile(Bike bike) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, bike.getPhotoFileName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //  CUSTOMER
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void createOrUpdateCustomer(final Customer customer, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(customer);
                if (displayToast) {
                    Toast.makeText(context,
                            "Added customer: " + customer.getFullName() + " (" + customer.mId + ")",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Customer getCustomer(String customerId) {
        return sRealm.where(Customer.class)
                .equalTo("mId", customerId)
                .findFirst();
    }

    public List<Customer> getAllCustomers() {
        return sRealm.where(Customer.class)
                .findAll();
    }

    public void deleteCustomer(final String customerId, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Customer> customers = realm.where(Customer.class)
                        .equalTo("mId", customerId)
                        .findAll();

                if (customers.size() > 0) {
                    if (displayToast) {
                        Toast.makeText(context,
                                "Deleted customer: " + customers.first().getFullName() +
                                        " (" + customers.first().mId + ")",
                                Toast.LENGTH_LONG).show();
                    }
                    customers.deleteFirstFromRealm();
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //  RIDE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void createOrUpdateRide(final Ride ride, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(ride);
                if (displayToast) {
                    Toast.makeText(context, "Added ride: " + ride.mId, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Ride getRide(String rideId) {
        return sRealm.where(Ride.class)
                .equalTo("mId", rideId)
                .findFirst();
    }

    public List<Ride> getAllRides() {
        return sRealm.where(Ride.class)
                .findAll();
    }

    public List<Ride> getAllRides(boolean isActive) {
        return sRealm.where(Ride.class)
                .equalTo("mIsActive", isActive)
                .findAll();
    }

    public List<Ride> getAllCustomerRides(Customer customer) {
        return sRealm.where(Ride.class)
                .equalTo("mCustomer.mId", customer.mId)
                .findAll();
    }

    public void deleteRide(final String rideId, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Ride> rides = realm.where(Ride.class)
                        .equalTo("mId", rideId)
                        .findAll();

                if (rides.size() > 0) {
                    if (displayToast) {
                        Toast.makeText(context,
                                "Deleted ride: " + rides.first().mId,
                                Toast.LENGTH_LONG).show();
                    }
                    rides.deleteFirstFromRealm();
                }
            }
        });
    }
}
