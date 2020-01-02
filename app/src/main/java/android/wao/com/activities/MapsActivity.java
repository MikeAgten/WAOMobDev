package android.wao.com.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.wao.com.Database.WaoDatabase;
import android.wao.com.R;
import android.wao.com.model.Shop;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public  Location currentLocation;
    public double currentLongitute;
    public double currentLatitute;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    WaoDatabase db = MainActivity.getDb();
    String typeBusiness;
    String city;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        typeBusiness = getIntent().getExtras().getString("type_business","defaultKey");
        city = getIntent().getExtras().getString("city","defaultKey");
        setContentView(R.layout.activity_maps);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(this, new String[]
                   {
                           Manifest.permission.ACCESS_FINE_LOCATION
                   },REQUEST_CODE);
            return;
        }
        Task<Location> task =  fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude() + ""+ currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    currentLatitute = currentLocation.getLatitude();
                    currentLongitute = currentLocation.getLongitude();
                    Log.d("halp",""+currentLocation.getLatitude());

                    Log.d("long",""+  currentLocation.getLongitude());
                    //krijgen wel de juiste locatie binnen


                    //test vanaf hier
                    SupportMapFragment supportMapFragment  = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
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
            List<Shop> shopsData = db.shopDAO().getAll();


            //eerst gaan we alle POI's uitzetten
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json));
            //nu zetten we enkel De Business Poi's  op de kaart
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_poi));

            // ZEt een marker in hasselt en verplaats de camera naar het centrum van hasselt
            LatLng hasselt = new LatLng(50.9315293 ,5.3351366);
            LatLng genk = new LatLng(50.9662569, 5.4970428);
            LatLng  antwerpen  = new LatLng(51.2197401 ,4.4005566);

            float zoomLevel = 17f; //This goes up to 21
            //mMap.addMarker(new MarkerOptions().position(hasselt).title("Marker On zara"));

            for( int i =0; i< shopsData.size(); i++){

                if(typeBusiness.equals(shopsData.get(i).typeBusiness) && city.equals(shopsData.get(i).city)){
                    LatLng currentshop = new LatLng(shopsData.get(i).latitute, shopsData.get(i).longitute);
                    MarkerOptions marker = new MarkerOptions().position(currentshop).title(""+  shopsData.get(i).shopName);
                    googleMap.addMarker(marker);
                }
             if(typeBusiness.equals("defaultKey")){
                 LatLng currentshop = new LatLng(shopsData.get(i).latitute, shopsData.get(i).longitute);
                 MarkerOptions marker = new MarkerOptions().position(currentshop).title(""+  shopsData.get(i).shopName);
                 googleMap.addMarker(marker);
             }

            }
            LatLng current = new LatLng(currentLatitute, currentLongitute);
           MarkerOptions markerOptions = new MarkerOptions().position(current).title("Ik ben hier");
            googleMap.addMarker(markerOptions);

            if(city.equals("Hasselt")){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hasselt,zoomLevel));
            }else{
                if(city.equals("Genk")){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(genk,zoomLevel));
                }else{
                    if(city.equals("Antwerpen")){
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(antwerpen,zoomLevel));
                    }else {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoomLevel));
                    }
                }
            }
        }catch(Resources.NotFoundException e){
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case    REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
}
