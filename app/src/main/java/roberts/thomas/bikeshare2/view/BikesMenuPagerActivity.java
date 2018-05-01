package roberts.thomas.bikeshare2.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import roberts.thomas.bikeshare2.R;

/**
 * Created by Tom on 27/04/2018.
 */

public class BikesMenuPagerActivity extends FragmentActivity {

    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        mViewPager = findViewById(R.id.view_pager);

        // Add the fragments to the ArrayList that will be paged
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(BikesRecyclerViewFragment.newInstance(true)); // show only vacant bikes
        fragments.add(AddBikeFragment.newInstance());
        fragments.add(BikesRecyclerViewFragment.newInstance(false)); // show all bikes

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }
}
