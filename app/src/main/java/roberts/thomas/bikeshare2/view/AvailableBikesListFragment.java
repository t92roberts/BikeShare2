package roberts.thomas.bikeshare2.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import roberts.thomas.bikeshare2.R;
import roberts.thomas.bikeshare2.model.Bike;
import roberts.thomas.bikeshare2.presenter.Database;

/**
 * Created by Tom on 28/04/2018.
 */

public class AvailableBikesListFragment extends Fragment {
    private static Database sDatabase;

    private RecyclerView mBikesList;
    private BikesAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sDatabase = Database.get();
        sDatabase.testData(getContext(), true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        mBikesList = view.findViewById(R.id.recycler_view);
        mBikesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BikesAdapter(sDatabase.getAllActiveBikesFromRealm(false));
        mBikesList.setAdapter(mAdapter);

        return view;
    }

    public class BikeHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private Bike mBike;

        //private ImageView mBikeImageView;
        private TextView mBikeNameTextView, mBikeTypeTextView, mPricePerHourTextView;

        public BikeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_bike, parent, false));

            //mBikeImageView = itemView.findViewById(R.id.image_view_bike_photo);

            mBikeNameTextView = itemView.findViewById(R.id.text_view_bike_name);
            mBikeTypeTextView = itemView.findViewById(R.id.text_view_bike_type);
            mPricePerHourTextView = itemView.findViewById(R.id.text_view_price_per_hour);

            itemView.setOnLongClickListener(this);
        }

        public void bind(Bike bike) {
            mBike = bike;

            //mBikeImageView.setImageBitmap(bike.mPhoto);

            mBikeNameTextView.setText(bike.mBikeName);
            mBikeTypeTextView.setText(bike.mBikeType);
            mPricePerHourTextView.setText(bike.mPricePerHour + " kr");
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Delete bike")
                    .setMessage("Are you sure you want to delete bike: '" + mBike.mBikeName + "'?");

            final int position = getAdapterPosition();

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAdapter.removeItem(position);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }
    }

    private class BikesAdapter extends RecyclerView.Adapter<BikeHolder> {
        private List<Bike> mBikes;

        public BikesAdapter(List<Bike> bikes) {
            mBikes = bikes;
        }

        @Override
        public BikeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BikeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BikeHolder holder, int position) {
            Bike bike = mBikes.get(position);
            holder.bind(bike);
        }

        @Override
        public int getItemCount() {
            return mBikes.size();
        }

        public void removeItem(int position) {
            // Delete from Real
            sDatabase.deleteBikeFromRealm(mBikes.get(position).mId, getActivity(), true);

            // Notify the RecyclerView that the item has been removed
            //mBikes.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mBikes.size());
        }
    }

    public static AvailableBikesListFragment newInstance() {
        return new AvailableBikesListFragment();
    }
}
