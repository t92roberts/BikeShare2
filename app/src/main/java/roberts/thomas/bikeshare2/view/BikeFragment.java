package roberts.thomas.bikeshare2.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import roberts.thomas.bikeshare2.R;
import roberts.thomas.bikeshare2.helpers.PictureUtils;
import roberts.thomas.bikeshare2.model.Bike;
import roberts.thomas.bikeshare2.presenter.Database;

/**
 * Created by Tom on 29/04/2018.
 */

public class BikeFragment extends Fragment {

    // Bundle arguments
    private static final String ARG_BIKE_ID = "bike_id";

    // Intent request codes
    private static final int REQUEST_PHOTO = 0;

    private static Database sDatabase;

    private Bike mBike;

    private File mPhotoFile;
    private ImageView mBikeImageView;
    private ImageButton mAddPhotoButton;

    private TextView mBikeNameTextView, mBikeTypeTextView, mPricePerHourTextView, mCurrentLocationTextView;

    public static BikeFragment newInstance(String bikeId) {
        Bundle args = new Bundle();
        args.putString(ARG_BIKE_ID, bikeId);

        BikeFragment fragment = new BikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sDatabase = Database.get(getActivity());

        String bikeId = getArguments().getString(ARG_BIKE_ID);
        mBike = sDatabase.getBikeFromRealm(bikeId);

        mPhotoFile = sDatabase.getBikePhotoFile(mBike);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bike, container, false);

        mBikeImageView = view.findViewById(R.id.image_view_bike_photo);
        updatePhotoView();

        mAddPhotoButton = view.findViewById(R.id.image_button_add_photo);

        PackageManager packageManager = getActivity().getPackageManager();

        /////////////////////////////////////////////////////////////////////////////////
        //  Capture an image using the camera (if a camera is available)
        /////////////////////////////////////////////////////////////////////////////////
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Disable the button if the phone doesn't have a camera
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mAddPhotoButton.setEnabled(canTakePhoto);

        // Capture and store a photo to the local file system
        mAddPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "roberts.thomas.bikeshare2.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////

        mBikeNameTextView = view.findViewById(R.id.text_view_bike_name);
        mBikeNameTextView.setText(mBike.mBikeName);

        mBikeTypeTextView = view.findViewById(R.id.text_view_bike_type);
        mBikeTypeTextView.setText(mBike.mBikeType);

        mPricePerHourTextView = view.findViewById(R.id.text_view_price_per_hour);
        mPricePerHourTextView.setText(String.valueOf(mBike.mPricePerHour));

        mCurrentLocationTextView = view.findViewById(R.id.text_view_location);

        mCurrentLocationTextView.setText(mBike.mIsBeingRidden ? "Being ridden" : mBike.mCurrentLocation);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO) {
            // Image taken using the camera and stored in the local file system
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "roberts.thomas.bikeshare2.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mBikeImageView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mBikeImageView.setImageBitmap(bitmap);
        }
    }
}
