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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import roberts.thomas.bikeshare2.R;
import roberts.thomas.bikeshare2.helpers.PictureUtils;
import roberts.thomas.bikeshare2.model.Bike;
import roberts.thomas.bikeshare2.model.BikeStand;
import roberts.thomas.bikeshare2.presenter.Database;

/**
 * Created by Tom on 30/04/2018.
 */

public class AddBikeFragment extends Fragment {

    // Intent request codes
    private static final int REQUEST_PHOTO = 0;

    private static Database sDatabase;

    //private Bike mBike;

    private File mPhotoFile;
    private ImageView mBikeImageView;
    private ImageButton mAddPhotoButton;
    private EditText mTypeEditText, mPricePerHourEditText;
    private Spinner mCurrentLocationSpinner;
    private Button mAddBikeButton;

    public static AddBikeFragment newInstance() {
        /*Bundle args = new Bundle();
        args.putString(ARG_BIKE_ID, bikeId);*/

        AddBikeFragment fragment = new AddBikeFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sDatabase = Database.get(getActivity());

        //mBike = new Bike(UUID.randomUUID().toString(), "", null, 0, false);

        //mPhotoFile = sDatabase.getBikePhotoFile(mBike);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_bike, container, false);

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

        mTypeEditText = view.findViewById(R.id.edit_text_type);
        mPricePerHourEditText = view.findViewById(R.id.edit_text_price_per_hour);
        mCurrentLocationSpinner = view.findViewById(R.id.spinner_current_location);

        final Map<String, String> bikeStandsMap = sDatabase.getAllBikeStandNames();

        final String[] bikeStandIds = bikeStandsMap.keySet().toArray(new String[bikeStandsMap.size()]);
        final String[] bikeStandNames = bikeStandsMap.values().toArray(new String[bikeStandsMap.size()]);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bikeStandNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCurrentLocationSpinner.setAdapter(spinnerAdapter);

        mAddBikeButton = view.findViewById(R.id.button_add_bike);

        mAddBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bikeTypeString = mTypeEditText.getText().toString().trim();
                String bikeStandString = mCurrentLocationSpinner.getSelectedItem().toString().trim();
                String pricePerHourString = mPricePerHourEditText.getText().toString().trim();
                String currentLocationString = mCurrentLocationSpinner.getSelectedItem().toString().trim();

                boolean userHasInputData = bikeTypeString.length() > 0
                        & bikeStandString.length() > 0
                        && pricePerHourString.length() > 0
                        && currentLocationString.length() > 0;

                if (userHasInputData) {
                    // Get the associated ID of the BikeStand from the Map
                    int pos = mCurrentLocationSpinner.getSelectedItemPosition();
                    String bikeStandId = bikeStandIds[pos];
                    // Get the complete BikeStand object from the database using the ID
                    BikeStand selectedBikeStand = sDatabase.getBikeStand(bikeStandId);

                    Bike newBike = new Bike(UUID.randomUUID().toString(),
                            bikeTypeString,
                            selectedBikeStand,
                            Integer.parseInt(pricePerHourString),
                            false);

                    sDatabase.addBike(newBike, getActivity(), true);

                    Intent intent = new Intent(getActivity(), BikeActivity.class);
                    intent.putExtra("arg", newBike.mId);
                    startActivity(intent);

                    getActivity().recreate();

                    mTypeEditText.setText("");
                    mPricePerHourEditText.setText("");
                    mCurrentLocationSpinner.setSelection(0);
                }
            }
        });

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
