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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragment2 extends Fragment implements OnMapReadyCallback, RouteReader.RouteListener, RouteReader.PlaceListener, LocationSource.OnLocationChangedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener, View.OnClickListener {
    //フィールドの生成
    private GoogleMap mMap;android.location.Location loc;
    //int count = 0;
    boolean flg = true;double Long;double Lat;float[] results = new float[1];
    Button button1;Button button2;Button button3;Button button4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapview, container, false);

    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        textView = view.findViewById(R.id.textView6);
//        textView2 = view.findViewById(R.id.textView8);
//       textView3 = view.findViewById(R.id.textView9);
//      textView4 = view.findViewById(R.id.textView10);
      button1=new Button(getContext());
        button2=new Button(getContext());
        button3=new Button(getContext());
        button4=new Button(getContext());
        view.findViewById(R.id.imageButton).setOnClickListener(this);
       view.findViewById(R.id.imageButton2).setOnClickListener(this);
       view.findViewById(R.id.imageButton2).setOnClickListener(this);
       view.findViewById(R.id.imageButton3).setOnClickListener(this);
        view.findViewById(R.id.imageButton4).setOnClickListener(this);


        //お店の営業時間、お店の写真
    }

    @Override public void onMapReady(GoogleMap googleMap) {
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
        mMap.setOnMarkerClickListener(this);

        //ルート検索
        //RouteReader.recvRoute("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y","千葉県船橋市本町","",this);
    }
    @Override public void onRoute(RouteData routeData) {
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
    @Override public void onPlace(PlaceData placeData) {
        mMap.clear();

        //検索した値の結果をマーカーでたてる
        for(PlaceData.Results result : placeData.results) {
            System.out.println(result.geometry.location.lat + "," + result.geometry.location.lng);
            System.out.println(result.name);

            Location loc = result.geometry.location;
            mMap.addMarker(new MarkerOptions().position(new LatLng(loc.lat, loc.lng)).title(result.name));


        }
    }
    @Override public void onLocationChanged(android.location.Location location) {
        loc = location;
        Long =(location.getLongitude());
        Lat = (location.getLatitude());
        //初回のみ周辺の情報を取得する
        if (flg) {
            LatLng sydney = new LatLng(location.getLatitude(),location.getLongitude());                //位置設定
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));   //範囲2.0～21.0(全体～詳細)
            RouteReader.recvPlace("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y",
                    ((MainActivity)getActivity()).searchText, new LatLng(location.getLatitude(),location.getLongitude()), 500, this);
            flg = false;
        }
    }
    @Override public void onCameraMove() {
    }
    @Override public void onCameraIdle() {

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
    //マーカークリック処理
    @Override public boolean onMarkerClick(Marker marker) {

        //二点間の最短距離の計算
        loc.distanceBetween(marker.getPosition().latitude, marker.getPosition().longitude, Lat, Long, results);
        LinearLayout layout = getView().findViewById(R.id.shopData);
        layout.removeAllViews();
        TextView textView = new TextView(getContext());
        TextView textView2 = new TextView(getContext());
        Button button5 = new Button(getContext());
        textView.setText(marker.getTitle());
        textView2.setText("徒歩"+(int)results[0]/60+"分");
        layout.addView(textView);
        layout.addView(textView2);
        layout.addView(button5);
        button5.setText("目的地を設定");
        button5.setOnClickListener(this);
        //クリックしたマーカーの情報をコンソール出力
        System.out.println(marker.getTitle() + "\n" + (float) (results[0]) + "m , " + (int) (results[0] / 60) + "分");
        return false;
    }    private void setContentView(LinearLayout linearLayout) {
}
    @Override public void onClick(View v) {
        if (v.getId() == R.id.imageButton) {
            ((MainActivity) getActivity()).setSearchText("cafe");
            ((MainActivity) getActivity()).changeFragment(MapViewFragment2.class);
            if (((MainActivity) getActivity()).searchText.equals("cafe")) {
                button1.setEnabled(false);
            }
        } else if (v.getId() == R.id.imageButton2) {
            ((MainActivity) getActivity()).setSearchText("restaurant");
            ((MainActivity) getActivity()).changeFragment(MapViewFragment2.class);
            if (((MainActivity) getActivity()).searchText.equals("restaurant")) {
                button1.setEnabled(false);
            }
        } else if (v.getId() == R.id.imageButton3) {
            ((MainActivity) getActivity()).setSearchText("amusement_park");
            ((MainActivity) getActivity()).changeFragment(MapViewFragment2.class);
            if (((MainActivity) getActivity()).searchText.equals("amusement_park")) {
                button1.setEnabled(false);
            }
        } else if (v.getId() == R.id.imageButton4) {

        }

    }
}

