package roberts.thomas.bikeshare2.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import roberts.thomas.bikeshare2.R;
import roberts.thomas.bikeshare2.helpers.PictureUtils;
import roberts.thomas.bikeshare2.model.Ride;
import roberts.thomas.bikeshare2.presenter.Database;

/**
 * Created by Tom on 02/05/2018.
 */

public class RidesRecyclerViewFragment extends Fragment {

    // Bundle arguments
    private static final String ARG_SHOW_ONLY_ACTIVE_RIDES = "active_rides";

    private static Database sDatabase;

    private RecyclerView mRidesList;
    private RidesAdapter mAdapter;

    public static RidesRecyclerViewFragment newInstance(boolean showOnlyActiveRides) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_ONLY_ACTIVE_RIDES, showOnlyActiveRides);

        RidesRecyclerViewFragment fragment = new RidesRecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sDatabase = Database.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        boolean onlyActiveRides = getArguments().getBoolean(ARG_SHOW_ONLY_ACTIVE_RIDES);

        mRidesList = view.findViewById(R.id.recycler_view);
        mRidesList.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (onlyActiveRides) {
            mAdapter = new RidesAdapter(sDatabase.getAllRides(true));
        } else {
            mAdapter = new RidesAdapter(sDatabase.getAllRides());
        }

        mRidesList.setAdapter(mAdapter);

        return view;
    }

    public class RideHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Ride mRide;

        private ImageView mBikeImageView;
        private TextView mTypeTextView;
        private TextView mStartLocationTextView, mStartDateTextView, mStartTimeTextView;
        private TextView mEndLocationTextView, mEndDateTextView, mEndTimeTextView;

        public RideHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_ride, parent, false));

            mBikeImageView = itemView.findViewById(R.id.image_view_bike_photo);
            mTypeTextView = itemView.findViewById(R.id.text_view_type);

            mStartLocationTextView = itemView.findViewById(R.id.text_view_start_location);
            mStartDateTextView = itemView.findViewById(R.id.text_view_start_date);
            mStartTimeTextView = itemView.findViewById(R.id.text_view_start_time);

            mEndLocationTextView = itemView.findViewById(R.id.text_view_end_location);
            mEndDateTextView = itemView.findViewById(R.id.text_view_end_date);
            mEndTimeTextView = itemView.findViewById(R.id.text_view_end_time);

            itemView.setOnClickListener(this);
        }

        public void bind(Ride ride) {
            mRide = ride;

            // Load the bike image (if it exists)
            File mPhotoFile = sDatabase.getBikePhotoFile(ride.mBike);

            if (mPhotoFile == null || !mPhotoFile.exists()) {
                mBikeImageView.setImageDrawable(null);
            } else {
                Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
                mBikeImageView.setImageBitmap(bitmap);
            }

            mTypeTextView.setText(ride.mBike.mType);
            mStartLocationTextView.setText(ride.mStartLocation.mName);
            mStartDateTextView.setText(ride.getFormattedStartDate());
            mStartTimeTextView.setText(ride.getFormattedStartTime());


            if (!ride.mIsActive) {
                mEndLocationTextView.setText(ride.mEndLocation.mName);
                mEndDateTextView.setText(ride.getFormattedEndDate());
                mEndTimeTextView.setText(ride.getFormattedEndTime());
            } else {
                mEndLocationTextView.setText("");
                mEndDateTextView.setText("");
                mEndTimeTextView.setText("");
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), RideActivity.class);
            intent.putExtra("arg", mRide.mId);
            startActivity(intent);
        }
    }

    private class RidesAdapter extends RecyclerView.Adapter<RideHolder> {
        private List<Ride> mRides;

        public RidesAdapter(List<Ride> rides) {
            mRides = rides;
        }

        @Override
        public RideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RideHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RideHolder holder, int position) {
            Ride ride = mRides.get(position);
            holder.bind(ride);
        }

        @Override
        public int getItemCount() {
            return mRides.size();
        }
    }
}
