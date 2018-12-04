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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapViewFragment2 extends Fragment implements OnMapReadyCallback, RouteReader.RouteListener, RouteReader.PlaceListener, LocationSource.OnLocationChangedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    //フィールドの生成
    private GoogleMap mMap;
    android.location.Location loc;
    boolean flg = true;
    double Long;
    double Lat;
    float[] results = new float[1];

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
        view.findViewById(R.id.imageButton).setOnClickListener(this);
        view.findViewById(R.id.imageButton2).setOnClickListener(this);
        view.findViewById(R.id.imageButton3).setOnClickListener(this);
        view.findViewById(R.id.imageButton4).setOnClickListener(this);
    }

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
        mMap.setOnMarkerClickListener(this);
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

        //検索した値の結果をマーカーでたてる
        for(PlaceData.Results result : placeData.results){
            System.out.println(result.geometry.location.lat+","+result.geometry.location.lng);
            System.out.println(result.name);
            Location loc = result.geometry.location;
            mMap.addMarker(new MarkerOptions().position(new LatLng(loc.lat, loc.lng)).title(result.name));
        }

        // ゴール地点が現在位置の場合はピンを立てない
        if (!(((MainActivity)getActivity()).lastPlace.equals("現在位置"))){
            //ゴール地点のポインターの設置
            MarkerOptions options = new MarkerOptions();
            options.position(new LatLng(((MainActivity)getActivity()).getLastLat(),((MainActivity)getActivity()).getLastLong()));
            // アイコンの変更
            // (1) 色選択
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            // (2) リソースのアイコン画像
            //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
            options.icon(icon);
            // アイコンの名前
            options.title(((MainActivity)getActivity()).getLastPlace());
            mMap.addMarker(options);
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        loc = location;
        Long =(location.getLongitude());
        Lat = (location.getLatitude());
        //初回のみ周辺の情報を取得する
        if (flg) {
            LatLng sydney = new LatLng(location.getLatitude(),location.getLongitude());  //位置設定
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f));   //範囲2.0～21.0(全体～詳細)
            RouteReader.recvPlace("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y",
                    ((MainActivity)getActivity()).searchText, new LatLng(Lat,Long), 500, this);
            flg = false;
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

    //マーカークリック処理
    @Override
    public boolean onMarkerClick(final Marker marker) {
        LinearLayout layout = getView().findViewById(R.id.shopData);
        layout.removeAllViews();
        if (marker.getTitle().toString().equals(((MainActivity)getActivity()).lastPlace)){
            TextView textView = new TextView(getActivity());
            textView.setText("ゴール地点です");
            layout.addView(textView);
        }else {
            //二点間の最短距離の計算
            loc.distanceBetween(marker.getPosition().latitude, marker.getPosition().longitude,Lat,Long, results);

            //クリックしたマーカーの情報をコンソール出力
            System.out.println(marker.getTitle() + "\n" + (float)(results[0]) + "m , " + (int)(results[0]/60) + "分");

            // レイアウトの生成
            // 店舗名
            TextView textView = new TextView(getActivity());
            textView.setText(marker.getTitle().toString());
            layout.addView(textView);
            // 現在地点からタップしたマーカーまでの移動時間
            TextView textView2 = new TextView(getActivity());
            textView2.setText((int) (results[0]) + "m , " + (int)(results[0]/60) + "分");
            layout.addView(textView2);
            // 決定ボタン
            Button button = new Button(getActivity());
            button.setText("ここで休む");
            layout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 休憩場所情報を保存
                    ((MainActivity)getActivity()).getBreakPlace().add(marker.getTitle().toString());
                    ((MainActivity)getActivity()).getMoveMinute().add((int)(results[0]/60));

                    // 戻り地点が現在位置ではない場合
                    if (!(((MainActivity)getActivity()).lastPlace.equals("現在位置"))){

                        // 登録した休憩場所からゴール地点までの移動時間
                        loc.distanceBetween(marker.getPosition().latitude, marker.getPosition().longitude,((MainActivity)getActivity()).lastLat,((MainActivity)getActivity()).lastLong, results);
                        ((MainActivity)getActivity()).setLastMove((int)(results[0]/60));

                    // 戻り地点が現在位置の場合
                    }else {

                        //登録した休憩場所から現在位置までの移動時間
                        loc.distanceBetween(marker.getPosition().latitude, marker.getPosition().longitude,Lat,Long, results);
                        ((MainActivity)getActivity()).setLastMove((int)(results[0]/60));
                    }

                    //スケジュール画面へ遷移
                    ((MainActivity)getActivity()).changeFragment(ScheduleFragment.class);
                }
            });
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButton) {
            ((MainActivity) getActivity()).setSearchText("cafe");
        } else if (v.getId() == R.id.imageButton2) {
            ((MainActivity) getActivity()).setSearchText("restaurant");
        } else if (v.getId() == R.id.imageButton3) {
            ((MainActivity) getActivity()).setSearchText("amusement_park");
        } else if (v.getId() == R.id.imageButton4) {

        }
        RouteReader.recvPlace("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y",
                ((MainActivity)getActivity()).searchText, new LatLng(Lat,Long), 500, this);
    }
}