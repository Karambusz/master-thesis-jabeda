package edu.agh.jabeda.server.application.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeocodingHelper {

    @Value("${jabeda.app.googleApiKey}")
    private String apiKey;

    public LatLng getLocation(String address) {
        try(GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build()) {

            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            if (results != null && results.length > 0) {
                GeocodingResult firstResult = results[0];
                double latitude = firstResult.geometry.location.lat;
                double longitude = firstResult.geometry.location.lng;


                System.out.println("Latitude: " + latitude);
                System.out.println("Longitude: " + longitude);
                return  firstResult.geometry.location;
            } else {
                System.out.println("No results found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
