package roberts.thomas.bikeshare2.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import roberts.thomas.bikeshare2.model.Bike;
import roberts.thomas.bikeshare2.model.Customer;
import roberts.thomas.bikeshare2.model.Ride;

/**
 * Created by Tom on 27/04/2018.
 */

public class Database {
    private static Database sDatabase;
    private static Realm sRealm;

    private Database () {
        sRealm = Realm.getDefaultInstance();
    }

    public static Database get() {
        if (sDatabase == null) {
            // the singleton hasn't been created yet, initialise it
            sDatabase = new Database();
        }

        return sDatabase;
    }

    public void testData(Context context) {
        Customer customer1 = new Customer(UUID.randomUUID().toString(), "John", "Smith", 1000);
        addCustomerToRealm(customer1, context);

        Bike bike1 = new Bike(UUID.randomUUID().toString(), "Test Bike 1", "Christiania bike", 750);
        addBikeToRealm(bike1, context);

        Ride ride1 = new Ride(UUID.randomUUID().toString(), bike1, customer1, "ITU");
        addRideToRealm(ride1, context);
        if (ride1.endRide("Nørreport")) {
            Toast.makeText(context,
                    ride1.mTotalPrice + " kr deducted from account",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,
                    "Unable to deducet " + ride1.mTotalPrice + " kr from account",
                    Toast.LENGTH_LONG).show();
        }

        Ride ride2 = new Ride(UUID.randomUUID().toString(), bike1, customer1, "Nørreport");
        addRideToRealm(ride2, context);
        ride2.endRide("Home");
        if (ride2.endRide("Nørreport")) {
            Toast.makeText(context,
                    ride2.mTotalPrice + " kr deducted from account",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,
                    "Unable to deduct " + ride2.mTotalPrice + " kr from account",
                    Toast.LENGTH_LONG).show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //  CUSTOMER
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addCustomerToRealm(final Customer customer, final Context context) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(customer);
                Toast.makeText(context,
                        "Added customer: " + customer.getFullName() + " (" + customer.mId + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public Customer getCustomerFromRealm(Customer customer) {
        return sRealm.where(Customer.class)
                .equalTo("mId", customer.mId)
                .findFirst();
    }

    public RealmResults<Customer> getAllCustomersFromRealm() {
        return sRealm.where(Customer.class)
                .findAll();
    }

    public void deleteCustomer(final Customer customer, final Context context) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Customer> customers = realm.where(Customer.class)
                        .equalTo("mId", customer.mId)
                        .findAll();

                if (customers.size() > 0) {
                    customers.deleteAllFromRealm();
                    Toast.makeText(context,
                            "Deleted customer: " + customer.getFullName() + " (" + customer.mId + ")",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //  BIKE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addBikeToRealm(final Bike bike, final Context context) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bike);
                Toast.makeText(context,
                        "Added bike: " + bike.mBikeName + " (" + bike.mId + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public Bike getBikeFromRealm(Bike bike) {
        return sRealm.where(Bike.class)
                .equalTo("mId", bike.mId)
                .findFirst();
    }

    public RealmResults<Bike> getAllBikesFromRealm() {
        return sRealm.where(Bike.class)
                .findAll();
    }

    public RealmResults<Bike> getAllActiveBikesFromRealm(boolean isBeingRidden) {
        return sRealm.where(Bike.class)
                .equalTo("mIsBeingRidden", isBeingRidden)
                .findAll();
    }

    public void deleteBike(final Bike bike, final Context context) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Bike> bikes = realm.where(Bike.class)
                        .equalTo("mId", bike.mId)
                        .findAll();

                if (bikes.size() > 0) {
                    bikes.deleteAllFromRealm();
                    Toast.makeText(context,
                            "Deleted bike: " + bike.mBikeName + "(" + bike.mId + ")",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //  RIDE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addRideToRealm(final Ride ride, final Context context) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(ride);
                Toast.makeText(context, "Added ride: " + ride.mId, Toast.LENGTH_LONG).show();
            }
        });
    }

    public Ride getRideFromRealm(Ride ride) {
        return sRealm.where(Ride.class)
                .equalTo("mId", ride.mId)
                .findFirst();
    }

    public RealmResults<Ride> getAllRidesFromRealm() {
        return sRealm.where(Ride.class)
                .findAll();
    }

    public RealmResults<Ride> getAllActiveRidesFromRealm(boolean isActive) {
        return sRealm.where(Ride.class)
                .equalTo("mIsActive", isActive)
                .findAll();
    }

    public void deleteRide(final Ride ride, final Context context) {
        sRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Ride> rides = realm.where(Ride.class)
                        .equalTo("mId", ride.mId)
                        .findAll();

                if (rides.size() > 0) {
                    rides.deleteAllFromRealm();
                    Toast.makeText(context,
                            "Deleted ride: " + ride.mId,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
