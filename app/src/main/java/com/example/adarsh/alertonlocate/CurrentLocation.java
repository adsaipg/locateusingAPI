package com.example.adarsh.alertonlocate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by adaRSH on 22-Oct-16.
 */
public class CurrentLocation extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    Geocoder geocoder;
    private List<Address> address;
    Context context;
    String destination;
    

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        buildGoogleApiClient();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        else
            Log.d("CodeKamp", "not connected");
        return super.onStartCommand(intent, flags, startId);
    }

    public void onConnected(Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!=null) {
            String strAdd=null;
            geocoder = new Geocoder(context, Locale.getDefault());
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
                    setdestination(destination);
                  //  RangeActivity ra=new RangeActivity();
                   // ra.getLocations(destination);
                   // getLocations(place);
                } else {
                    Log.d("CodeKamp","No Address returned!");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onConnectionSuspended(int i) {

        Log.d("CodeKamp","connection suspended");
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d("CodeKamp","Failed to connect");
    }
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient=new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void setdestination(String destination) {
        this.destination=destination;
        Log.d("CodeKamp","destination set:"+destination);

    }


    public String getdestination(){
        Log.d("CodeKamp","CurDest:"+destination);
        mGoogleApiClient.connect();
        return destination;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
