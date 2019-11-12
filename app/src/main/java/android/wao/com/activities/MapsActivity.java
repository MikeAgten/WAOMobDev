package android.wao.com.activities;

import androidx.fragment.app.FragmentActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.wao.com.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try{

            mMap = googleMap;

            //eerst gaan we alle POI's uitzetten
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json));
            //nu zetten we enkel De Business Poi's  op de kaart
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_poi));

            // ZEt een marker in hasselt en verplaats de camera naar het centrum van hasselt
            LatLng hasselt = new LatLng(50.9315293 ,5.3351366);
            float zoomLevel = 15.9f; //This goes up to 21
            mMap.addMarker(new MarkerOptions().position(hasselt).title("Marker On zara"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hasselt,zoomLevel));

        }catch(Resources.NotFoundException e){

        }




    }
}
