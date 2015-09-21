package com.example.mapspractice;

import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(this, "connection failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null) {
            double currentLatitude = mCurrentLocation.getLatitude();
            double currentLongitude = mCurrentLocation.getLongitude();

            LatLng currentLatLng = new LatLng(currentLatitude, currentLongitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13));

            mMap.addMarker(new MarkerOptions()
                    .position(currentLatLng)
                    .title("You are here")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            addClosestPlaces(currentLatitude, currentLongitude);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection failed", Toast.LENGTH_LONG).show();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void addClosestPlaces(double latitude, double longitude) {
        new NearestMarkersGetter(latitude, longitude, mMap).execute();
    }

    private class NearestMarkersGetter extends AsyncTask<Void, Void, String> implements RoutingListener {

        private double mLatitude;
        private double mLongitude;
        private GoogleMap mMap;

        public NearestMarkersGetter(double latitude, double longitude, GoogleMap map) {
            mLatitude = latitude;
            mLongitude = longitude;
            mMap = map;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String urlString = "https://geocode-maps.yandex.ru/1.x/?geocode=" + mLatitude + ","
                        + mLongitude + "&kind=locality&sco=latlong&format=json&results=10&ll=" + mLatitude + ","
                        + mLongitude;
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null) {
                    responseStrBuilder.append(inputStr);
                }
                in.close();
                return responseStrBuilder.toString();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                if (mMap != null) {
                    try {
                        List<Place> places = ParseUtils.parse(result);
                        addMarkers(mMap, places);
                        List<LatLng> waypoints = getWaypoints(places);
//                        for (int i = 0; i < places.size()/10 + 1; i++) {
//                            List<LatLng> buffer = waypoints.subList(i*10 - i, Math.min((i + 1) * 10 - i, waypoints.size()));
                        for (int i = 0; i < waypoints.size() - 1; i++) {
                            Routing routing = new Routing.Builder()
                                    .travelMode(Routing.TravelMode.WALKING)
                                    .withListener(this)
                                    .waypoints(waypoints.get(i), waypoints.get(i + 1))
                                    .build();
                            routing.execute();
                        }
                    } catch (JSONException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void addMarkers(GoogleMap map, List<Place> places) {
            if (places != null) {
                for (Place place : places) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(place.getLatitude(), place.getLongitute()))
                            .title(place.getName()));
                }
            }
        }

        private List<LatLng> getWaypoints(List<Place> places) {
            List<LatLng> waypoints = new ArrayList<>();

            waypoints.add(new LatLng(mLatitude, mLongitude));

            for (Place place : places) {
                waypoints.add(new LatLng(place.getLatitude(), place.getLongitute()));
            }
            return waypoints;
        }

        @Override
        public void onRoutingFailure() {

        }

        @Override
        public void onRoutingStart() {

        }

        @Override
        public void onRoutingSuccess(PolylineOptions polylineOptions, Route route) {
            PolylineOptions polyoptions = new PolylineOptions();
            polyoptions.color(Color.BLUE);
            polyoptions.width(5);
            polyoptions.addAll(polylineOptions.getPoints());
            if (mMap != null)
                mMap.addPolyline(polyoptions);
        }

        @Override
        public void onRoutingCancelled() {

        }
    }
}