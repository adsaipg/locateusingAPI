package com.example.adarsh.alertonlocate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RangeActivity extends FragmentActivity implements OnMapReadyCallback{

    private static final String PLACE_OF = "key";
    private GoogleMap mMap;
    private LatLng latLng;
    private LatLng loc;
    private String origin = null;
    private String startAddress = null;
    private String endAddress = null;
  //  private LocationClient mLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String destination="IIT Delhi";
    Geocoder geocoder;
    private List<android.location.Address> address;
    private Place place;
   // CurrentLocation currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);
        Button button=(Button)findViewById(R.id.service_buton);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //currentLocation=new CurrentLocation(RangeActivity.this);
        //destination=currentLocation.getdestination();
        Intent i = getIntent();
        place = i.getParcelableExtra(PLACE_OF);

       // currentLocation=new CurrentLocation(RangeActivity.this);
        Log.d("CodeKamp", String.valueOf(place.getName()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(RangeActivity.this, CurrentLocation.class));
            }
        });

        getLocations(place);

      /*  buildGoogleApiClient();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        else
            Log.d("CodeKamp", "not connected");*/

    }

  /*  @Override
    public void onConnected(Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!=null) {
            String strAdd=null;
            geocoder = new Geocoder(this, Locale.getDefault());
            Log.d("CodeKamp", "Last Location:" + String.valueOf(mLastLocation.getLatitude()));
            try {
                address= geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);
                Log.d("CodeKamp",address.get(0).getAddressLine(1));

                if (address != null) {
                    android.location.Address returnedAddress = address.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");

                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
                    }
                    strAdd = strReturnedAddress.toString();
                    destination=strAdd;
                    Log.d("CodeKamp","My Current loction address:"+strAdd );
                    getLocations(place);
                } else {
                    Log.d("CodeKamp","No Address returned!");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.d("CodeKamp","connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d("CodeKamp","Failed to connect");
    }
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        //Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



    public void getLocations(Place place) {
        Log.d("CodeKamp", "function starts");
       // this.destination=destination;
       // destination=currentLocation.getdestination();
        Log.d("CodeKamp","place:"+place.getName());

        Log.d("CodeKamp","Dest:"+destination);
        latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
        Log.d("Codekamp","check1");
        startAddress = String.valueOf(place.getName());
        endAddress = destination;
        String toastMsg = String.format("Place: %s", place.getName());
        Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
        origin = (String) place.getAddress();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocationData locationData = retrofit.create(LocationData.class);

        Call<LocationAlert> call = locationData.loadList(origin, destination);

        call.enqueue(new Callback<LocationAlert>() {
            @Override
            public void onResponse(Call<LocationAlert> call, Response<LocationAlert> response) {

                Retrofit_response(response);

            }

            @Override
            public void onFailure(Call<LocationAlert> call, Throwable t) {

                Log.d("CodeKamp", "error");
                Log.d("CodeKamp", t.getMessage());
                t.printStackTrace();

            }
        });


    }


    protected void Retrofit_response(Response<LocationAlert> response) {

        List<Route> route = response.body().getRoutes();
        Leg ofRoute = route.get(0).getLegs().get(0);
       // Log.d("CodeKamp", ofRoute.getDistance().getText());
       // Log.d("CodeKamp", ofRoute.getDuration().getText());
        double laitude = ofRoute.getEndLocation().getLat();
        double longitude = ofRoute.getEndLocation().getLng();
        double lat = ofRoute.getStartLocation().getLat();
        double lon = ofRoute.getStartLocation().getLng();

        LatLng latLng2 = new LatLng(lat, lon);
        LatLng latLng1 = new LatLng(laitude, longitude);

        updateMarker(latLng1, latLng2, startAddress, endAddress);
        OverviewPolyline overviewPolylines = route.get(0).getOverviewPolyline();
        String encodedString = overviewPolylines.getPoints();
        List<LatLng> list = decodePoly(encodedString);
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .addAll(list)
                .width(7)
                .color(Color.DKGRAY)
                .geodesic(true)
        );

    }

    protected void updateMarker(LatLng latandlong1, LatLng latandlong2, String start, String end) {


        mMap.addMarker(new MarkerOptions().position(latandlong1).title(end).icon(getMarkerIcon("#FF3E97CA")));
        mMap.addMarker(new MarkerOptions().position(latandlong2).title(start));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latandlong1));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(6));

    }
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public static Intent createNewIntent(Context context, Place place) {
        Intent intent = new Intent(context, RangeActivity.class);
        Log.d("CodeKamp", "place");
        intent.putExtra(PLACE_OF, (Parcelable) place);

        return intent;
    }


}