package com.mayer.recognition.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by irikhmayer on 16.01.2015.
 */
public class LoactionUtil {
    public static Location getBestLocation(Context ctxt) {
        Location gpslocation = getLocationByProvider(LocationManager.GPS_PROVIDER, ctxt);
        Location networkLocation = getLocationByProvider(LocationManager.NETWORK_PROVIDER, ctxt);
        Location fetchedlocation;
        // if we have only one location available, the choice is easy
        if (gpslocation != null) {
            Logger.d("New Location Receiver. GPS Location available.");
            fetchedlocation = gpslocation;
        } else {
            Logger.d("New Location Receiver. No GPS Location available. Fetching Network location lat="
                            + networkLocation.getLatitude() + " lon ="
                            + networkLocation.getLongitude());
            fetchedlocation = networkLocation;
        }
        return fetchedlocation;
    }

    /**
     * get the last known location from a specific provider (network/gps)
     */
    private static Location getLocationByProvider(String provider, Context ctxt) {
        Location location = null;
        // if (!isProviderSupported(provider)) {
        // return null;
        // }
        LocationManager locationManager = (LocationManager) ctxt.getSystemService(Context.LOCATION_SERVICE);
        try {
            if (locationManager.isProviderEnabled(provider)) {
                location = locationManager.getLastKnownLocation(provider);
            }
        } catch (IllegalArgumentException e) {
            Logger.d("New Location Receiver. Cannot access Provider " + provider);
        }
        return location;
    }

}
