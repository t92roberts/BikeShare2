package roberts.thomas.bikeshare2.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import roberts.thomas.bikeshare2.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private Fragment mFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the initial fragment to display
        mFragmentManager = getSupportFragmentManager();
        mFragment = MapFragment.newInstance();
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.container, mFragment).commit();

        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        mFragment = MapFragment.newInstance();
                        break;
                    case R.id.navigation_add_bike:
                        mFragment = AddBikeFragment.newInstance();
                        break;
                    case R.id.navigation_vacant_bikes:
                        mFragment = BikesRecyclerViewFragment.newInstance(true);
                        break;
                    case R.id.navigation_active_rides:
                        mFragment = RidesRecyclerViewFragment.newInstance(false);
                        break;
                    case R.id.navigation_profile:
                        // TODO - show the customer's balance
                        break;
                }

                // Replace the current fragment with the selected one
                final FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.container, mFragment).commit();

                return true;
            }
        });
    }
}
