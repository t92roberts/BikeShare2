package roberts.thomas.bikeshare2.presenter;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import roberts.thomas.bikeshare2.model.Bike;
import roberts.thomas.bikeshare2.model.Customer;
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
        testData(mContext, true);
    }

    public void testData(Context context, boolean displayToasts) {
        Bike bike1 = new Bike("1", "Christiania bike", "ITU", 75, false, null);
        addBikeToRealm(bike1, context, displayToasts);

        Bike bike2 = new Bike("2", "Men's bike", "Nørreport", 50, true, null);
        addBikeToRealm(bike2, context, displayToasts);

        /*Customer customer1 = new Customer(UUID.randomUUID().toString(), "John", "Smith", 100);
        addCustomerToRealm(customer1, context, displayToasts);

        Ride ride1 = new Ride(UUID.randomUUID().toString(), bike1, customer1, "ITU");
        addRideToRealm(ride1, context, displayToasts);

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
        addRideToRealm(ride2, context, displayToasts);

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
    //  CUSTOMER
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addCustomerToRealm(final Customer customer, final Context context, final boolean displayToast) {
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

    public Customer getCustomerFromRealm(String customerId) {
        return sRealm.where(Customer.class)
                .equalTo("mId", customerId)
                .findFirst();
    }

    public List<Customer> getAllCustomersFromRealm() {
        return sRealm.where(Customer.class)
                .findAll();
    }

    public void deleteCustomerFromRealm(final String customerId, final Context context, final boolean displayToast) {
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
    //  BIKE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addBikeToRealm(final Bike bike, final Context context, final boolean displayToast) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bike);
                if (displayToast) {
                    Toast.makeText(context,
                            "Added bike: " + bike.mId,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Bike getBikeFromRealm(String bikeId) {
        return sRealm.where(Bike.class)
                .equalTo("mId", bikeId)
                .findFirst();
    }

    public List<Bike> getAllBikesFromRealm() {
        return sRealm.where(Bike.class)
                .findAll();
    }

    public List<Bike> getAllBikesFromRealm(boolean isBeingRidden) {
        return sRealm.where(Bike.class)
                .equalTo("mIsBeingRidden", isBeingRidden)
                .findAll();
    }

    public void deleteBikeFromRealm(final String bikeId, final Context context, final boolean displayToast) {
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

    public File getBikePhotoFile(Bike bike) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, bike.getPhotoFileName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //  RIDE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addRideToRealm(final Ride ride, final Context context, final boolean displayToast) {
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

    public Ride getRideFromRealm(String rideId) {
        return sRealm.where(Ride.class)
                .equalTo("mId", rideId)
                .findFirst();
    }

    public List<Ride> getAllRidesFromRealm() {
        return sRealm.where(Ride.class)
                .findAll();
    }

    public List<Ride> getAllActiveRidesFromRealm(boolean isActive) {
        return sRealm.where(Ride.class)
                .equalTo("mIsActive", isActive)
                .findAll();
    }

    public void deleteRideFromRealm(final String rideId, final Context context, final boolean displayToast) {
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
