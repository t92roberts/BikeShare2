package roberts.thomas.bikeshare2.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import roberts.thomas.bikeshare2.R;
import roberts.thomas.bikeshare2.model.BikeStand;
import roberts.thomas.bikeshare2.model.Location;
import roberts.thomas.bikeshare2.model.Ride;
import roberts.thomas.bikeshare2.presenter.Database;

/**
 * Created by Tom on 03/05/2018.
 */

public class RideFragment extends Fragment {

    // Bundle arguments
    private static final String ARG_RIDE_ID = "ride_id";

    private static Database sDatabase;

    private Ride mRide;

    private TextView mStartLocationTextView, mStartDateTextView, mStartTimeTextView;
    private TextView mEndLocationTextView, mEndDateTextView, mEndTimeTextView;

    Button mEndRideButton;

    public static RideFragment newInstance(String rideId) {
        Bundle args = new Bundle();
        args.putString(ARG_RIDE_ID, rideId);

        RideFragment fragment = new RideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sDatabase = Database.get(getActivity());

        String rideId = getArguments().getString(ARG_RIDE_ID);
        mRide = sDatabase.getRide(rideId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ride, container, false);

        mStartLocationTextView = view.findViewById(R.id.text_view_start_location);
        mStartDateTextView = view.findViewById(R.id.text_view_start_date);
        mStartTimeTextView = view.findViewById(R.id.text_view_start_time);

        mEndLocationTextView = view.findViewById(R.id.text_view_end_location);
        mEndDateTextView = view.findViewById(R.id.text_view_end_date);
        mEndTimeTextView = view.findViewById(R.id.text_view_end_time);

        mEndRideButton = view.findViewById(R.id.button_end_ride);

        mStartLocationTextView.setText(mRide.mStartLocation.mName);
        mStartDateTextView.setText(mRide.getFormattedStartDate());
        mStartTimeTextView.setText(mRide.getFormattedStartTime());

        if (mRide.mIsActive) {
            mEndLocationTextView.setVisibility(View.INVISIBLE);
            mEndDateTextView.setVisibility(View.INVISIBLE);
            mEndTimeTextView.setVisibility(View.INVISIBLE);
        } else {
            mEndLocationTextView.setText(mRide.mEndLocation.mName);
            mEndDateTextView.setText(mRide.getFormattedEndDate());
            mEndTimeTextView.setText(mRide.getFormattedEndTime());
            mEndRideButton.setVisibility(View.INVISIBLE);
        }

        mEndRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - Use the phone's current location
                Location customerLocation = mRide.mCustomer.getCurrentLocation();

                BikeStand nearestBikeStand = sDatabase.getNearestBikeStand(customerLocation);
                sDatabase.endRide(mRide, nearestBikeStand, getActivity(), true);
                getActivity().recreate();
            }
        });

        return view;
    }
}
