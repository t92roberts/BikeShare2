package roberts.thomas.bikeshare2.view;

import android.support.v4.app.Fragment;

/**
 * Created by Tom on 03/05/2018.
 */

public class RideActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(String arg) {
        return RideFragment.newInstance(arg);
    }
}
