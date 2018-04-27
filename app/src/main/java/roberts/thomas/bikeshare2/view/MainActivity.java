package roberts.thomas.bikeshare2.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import roberts.thomas.bikeshare2.R;
import roberts.thomas.bikeshare2.presenter.Database;

public class MainActivity extends AppCompatActivity {
    private static Database sDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sDatabase = Database.get();
        sDatabase.testData(this);
    }
}
