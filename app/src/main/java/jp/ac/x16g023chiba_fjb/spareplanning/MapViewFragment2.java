package jp.ac.x16g023chiba_fjb.spareplanning;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragment2 extends Fragment implements OnMapReadyCallback, RouteReader.RouteListener, RouteReader.PlaceListener, LocationSource.OnLocationChangedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener {

    private GoogleMap mMap;
    int count = 0;
    float[] results = new float[] {};
    String distance = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

 //   @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
    Location loc;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MyLocationSource ls = new MyLocationSource(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        ls.activate(this);
        mMap.setMyLocationEnabled(true); //警告は無視
        //mMap.setLocationSource(ls);
        mMap.setLocationSource(new MyLocationSource(getContext()));
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraIdleListener(this);
        UiSettings settings = mMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
        LatLng sydney = new LatLng(35.7016369, 139.9836126);                //位置設定
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));   //範囲2.0～21.0(全体～詳細)
        //ルート検索
        RouteReader.recvRoute("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y", "千葉県船橋市本町", "", this);
    }

    @Override
    public void onRoute(RouteData routeData) {
//        //ルート受け取り処理
//        if(routeData != null && routeData.routes.length > 0 && routeData.routes[0].legs.length > 0){
//            RouteData.Routes r = routeData.routes[0];
//            Location start = r.legs[0].start_location;
//            Location end = r.legs[0].end_location;
//
//            RouteReader.recvPlace("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y",
//                    "cafe",new LatLng(start.lat, start.lng),500,this);
//        }
    }

    @Override
    public void onPlace(PlaceData placeData) {
        mMap.clear();
        for(PlaceData.Results result : placeData.results){
            System.out.println(result.geometry.location.lat+","+result.geometry.location.lng);
            System.out.println(result.name);

            Location loc = result.geometry.location;

            mMap.addMarker(new MarkerOptions().position(new LatLng(loc.lat, loc.lng)).title(result.name));


        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        double Long =(location.getLongitude());
        double Lat = (location.getLatitude());
        LatLng sydney = new LatLng(location.getLatitude(),location.getLongitude());                //位置設定
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));   //範囲2.0～21.0(全体～詳細)
        if (count < 1) {
//			RouteReader.recvPlace("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y",
//					"cafe", new LatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude), 500, this);
            RouteReader.recvPlace("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y",
                    "cafe", new LatLng(location.getLatitude(),location.getLongitude()), 500, this);

            count++;
        }
    }


    @Override
    public void onCameraMove() {
    }

    @Override
    public void onCameraIdle() {

        //LatLng sydney = new LatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);                //位置設定
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));   //範囲2.0～21.0(全体～詳細)
//			if (count < 1) {
//			RouteReader.recvPlace("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y",
//					"cafe", new LatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude), 500, this);
////				RouteReader.recvPlace("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y",
////						"cafe", new LatLng(onLocationChanged(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude*/), 500, this);
//
//				count++;
//
//		}
    }
}
