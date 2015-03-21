package com.android.bpcontrol;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.bpcontrol.adapters.WindowsMapAdapter;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.fragments.CentersListFragment;
import com.android.bpcontrol.model.Center;
import com.android.bpcontrol.model.GoogleMapsDirection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends BPcontrolMasterActivity {

    public static enum MAPTYPE {

        HYBRID,
        NORMAL,

    }

    public static final String LOCATION_COUNTRY = "country";
    public static final String LOCATION_PROVINCE = "province";
    public static final String LOCATION_ADMINISTRATION = "administration";
    public static final String LOCATION_POSTALCODE="postalcode";
    public static final String LOCATION_CITY="city";
    public static final String LOCATION_ADDRESS="address";
    public static final String LOCATION_DRIVEDIST = "drivedist";
    public static final String LOCATION_WALKDIST="walkdist";


    private GoogleMap mMap;
    private Center center;
    private LocationManager manager;
    private Polyline newPolyline;
    private Location mylocation;
    private LatLng centerlocation;
    private LatLngBounds latlngBounds;
    private int width, height;
    private int maptype;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        configureActionBar();
        maptype = GoogleMap.MAP_TYPE_NORMAL;
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();


            if (bundle != null && (bundle.containsKey(CentersListFragment.CENTER) && bundle.containsKey(CentersListFragment.MYLOCATION))) {
                center = (Center) bundle.getParcelable(CentersListFragment.CENTER);
                centerlocation = center.getLocation();
                getSreenDimensions();
                mylocation = (Location) bundle.getParcelable(CentersListFragment.MYLOCATION);
                findDirections(mylocation.getLatitude(), mylocation.getLongitude(), centerlocation.latitude,
                        centerlocation.longitude, GoogleMapsDirection.MODE_DRIVING);
                latlngBounds = createLatLngBoundsObject(new LatLng(mylocation.getLatitude(), mylocation.getLongitude()),
                        new LatLng(centerlocation.latitude, centerlocation.longitude));
                setUpMapIfNeeded();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
            }


        } else {
            setUpMapIfNeeded();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setMapToolbarEnabled(true);
        mMap.setMapType(maptype);
        Bundle bundle = obtainLocationInfo(center.getLocation().latitude,center.getLocation().longitude);
        if (bundle!=null){

        }
        WindowsMapAdapter windowsMapAdapter = new WindowsMapAdapter(this,center,bundle);
        mMap.setInfoWindowAdapter(windowsMapAdapter);
        if (centerlocation != null)
            mMap.addMarker(new MarkerOptions().position(new LatLng(centerlocation.latitude, centerlocation.longitude)).title(center.getName()));
        else
            mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title(""));
    }

    private class GetDirectionsAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList> {
        public static final String USER_CURRENT_LAT = "user_current_lat";
        public static final String USER_CURRENT_LONG = "user_current_long";
        public static final String DESTINATION_LAT = "destination_lat";
        public static final String DESTINATION_LONG = "destination_long";
        public static final String DIRECTIONS_MODE = "directions_mode";
        private Context context = MapsActivity.this;
        private Exception exception;


        public void onPreExecute() {

        }

        @Override
        protected ArrayList doInBackground(Map<String, String>... params) {
            Map<String, String> paramMap = params[0];
            try {
                LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)), Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
                LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)), Double.valueOf(paramMap.get(DESTINATION_LONG)));
                GoogleMapsDirection md = new GoogleMapsDirection();
                Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
                md.getDistanceText(doc);
                md.getDistanceValue(doc);
                md.getDurationText(doc);
                md.getDurationValue(doc);
                md.getCopyRights(doc);
                ArrayList directionPoints = md.getDirection(doc);

                return directionPoints;
            }
            catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }
        }

        @Override
        public void onPostExecute(ArrayList result)
        {
            if (result != null)
            {
                ((MapsActivity)context).handleGetDirectionsResult(result);
            }
            else
            {
                processException();
            }
        }

        private void processException()
        {
            Toast.makeText(context, getResources().getString(R.string.errorgettingpoint), Toast.LENGTH_LONG).show();
        }
    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {

        PolylineOptions rectLine = new PolylineOptions().width(15).color(Color.RED);

        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null) {
            newPolyline.remove();
        }
        newPolyline = mMap.addPolyline(rectLine);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));


    }

    private void getSreenDimensions() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation) {
        if (firstLocation != null && secondLocation != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode) {
        Map<String, String> map = new HashMap<>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);


        new GetDirectionsAsyncTask().execute(map);
    }

    private void configureActionBar() {

        View view = getActionBarView();
        ImageButton button = (ImageButton) view.findViewById(R.id.actionBarMenu);
        button.setImageResource(R.drawable.arrow_buttonselector);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RobotoTextView robotoTextView = (RobotoTextView) view.findViewById(R.id.textviewbpcontrol);
        robotoTextView.setText(getResources().getString(R.string.mapactheader).toUpperCase());
        ImageButton secondactionbarbutton = (ImageButton) getActionBarView()
                .findViewById(R.id.secondActionBarButton);
        secondactionbarbutton.setVisibility(View.VISIBLE);
        secondactionbarbutton.setImageResource(R.drawable.secondbuttonbarselectormap);
        secondactionbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                String[] types = getResources().getStringArray(R.array.maptypes);
                builder.setTitle(getResources().getString(R.string.mapdialogtitle));
                builder.setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                maptype = GoogleMap.MAP_TYPE_HYBRID;
                                break;
                            case 1:
                                maptype = GoogleMap.MAP_TYPE_NORMAL;
                                break;
                            case 2:
                                maptype = GoogleMap.MAP_TYPE_SATELLITE;
                                break;
                            case 3:
                                maptype = GoogleMap.MAP_TYPE_TERRAIN;
                                break;
                        }
                        mMap.setMapType(maptype);
                    }
                });

                builder.show();
            }
        });
    }

    private Bundle obtainLocationInfo(double lat, double lng) {

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        Bundle locationelements = null;
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            if (address.size() > 0) {
                locationelements = new Bundle();
                Address foundAddress = address.get(0);
                locationelements.putString(LOCATION_COUNTRY, foundAddress.getCountryName());
                locationelements.putString(LOCATION_PROVINCE, foundAddress.getSubAdminArea());
                locationelements.putString(LOCATION_CITY, foundAddress.getLocality());
                locationelements.putString(LOCATION_ADMINISTRATION, foundAddress.getAdminArea());
                locationelements.putString(LOCATION_POSTALCODE, foundAddress.getPostalCode());
                locationelements.putString(LOCATION_ADDRESS,foundAddress.getAddressLine(0));
                return locationelements;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}


