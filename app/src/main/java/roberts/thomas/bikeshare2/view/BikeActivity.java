package roberts.thomas.bikeshare2.view;

import android.support.v4.app.Fragment;

/**
 * Created by Tom on 01/05/2018.
 */

public class BikeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(String arg) {
        return BikeFragment.newInstance(arg);
    }
}
