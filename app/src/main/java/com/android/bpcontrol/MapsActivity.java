package com.android.bpcontrol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.Toast;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.fragments.CentersListFragment;
import com.android.bpcontrol.model.Center;
import com.android.bpcontrol.model.GoogleMapsDirection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends BPcontrolMasterActivity {

    private GoogleMap mMap;
    private Center center;
    private Location currentLocation;
    private LocationManager manager;
    private Polyline newPolyline;
    private Location mylocation;
    private Location centerlocation;
    private LatLngBounds latlngBounds;
    private int width, height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        Intent intent = getIntent();
        if (intent != null ){
            Bundle bundle = intent.getExtras();
            if (bundle!=null && (bundle.containsKey(CentersListFragment.CENTER) && bundle.containsKey(CentersListFragment.MYLOCATION))){
            center =(Center)bundle.getParcelable(CentersListFragment.CENTER);
            centerlocation = center.getLocation();
                getSreenDimanstions();
            mylocation = (Location) bundle.getParcelable(CentersListFragment.MYLOCATION);
            findDirections( mylocation.getLatitude(), mylocation.getLongitude(),centerlocation.getLatitude(),
                    centerlocation.getLongitude(), GoogleMapsDirection.MODE_DRIVING );
            latlngBounds = createLatLngBoundsObject(new LatLng(mylocation.getLatitude(),mylocation.getLongitude()),
                    new LatLng(centerlocation.getLatitude(),centerlocation.getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
        }
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    private class GetDirectionsAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList>
    {
        public static final String USER_CURRENT_LAT = "user_current_lat";
        public static final String USER_CURRENT_LONG = "user_current_long";
        public static final String DESTINATION_LAT = "destination_lat";
        public static final String DESTINATION_LONG = "destination_long";
        public static final String DIRECTIONS_MODE = "directions_mode";
        private Context context;
        private Exception exception;


        public GetDirectionsAsyncTask(Context context)
        {
            super();
            this.context = context;
        }

        public void onPreExecute()
        {

        }

        @Override
        public void onPostExecute(ArrayList result)
        {
            if (exception == null)
            {
                ((MapsActivity)context).handleGetDirectionsResult(result);
            }
            else
            {
                processException();
            }
        }

        @Override
        protected ArrayList doInBackground(Map<String, String>... params)
        {
            Map<String, String> paramMap = params[0];
            try
            {
                LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
                LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
                GoogleMapsDirection md = new GoogleMapsDirection();
                Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
                ArrayList directionPoints = md.getDirection(doc);
                return directionPoints;
            }
            catch (Exception e)
            {
                exception = e;
                return null;
            }
        }

        private void processException()
        {
            Toast.makeText(context, "Error getting data points", Toast.LENGTH_LONG).show();
        }
    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);

        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null)
        {
            newPolyline.remove();
        }
        newPolyline = mMap.addPolyline(rectLine);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));


    }

    private void getSreenDimanstions()
    {
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }

    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
    {
        if (firstLocation != null && secondLocation != null)
        {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        Map<String, String> map = new HashMap<>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }
}


