package jp.ac.x16g023chiba_fjb.spareplanning;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewFragment2 extends Fragment implements OnMapReadyCallback{


    private GoogleMap mMap;
    private List<Marker> mMakers = new LinkedList<Marker>();

    public MapViewFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //マップ操作用
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //EditTextのイベント設定
        //((EditText)view.findViewById(R.id.search)).setOnEditorActionListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //マップ初期化処理
        mMap = googleMap;
        LatLng sydney = new LatLng(35.7016369, 139.9836126);             //位置設定
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));   //範囲2.0～21.0(全体～詳細)
        Toast.makeText(getContext(), "検索開始", Toast.LENGTH_SHORT).show();
        //PlaceAPIに検索ワードを投げる
        PlacesAPI2.search("AIzaSyCh6xPYG2qMmVz7PScq-w7lZKyAtDwrS1Y", mMap, ((MainActivity) getActivity()).getSearchText(), new PlacesAPI2.PlaceListener() {
            @Override
            public void onPlaces(PlacesAPI2.PlaceData[] places) {
                removeMarker();
                if (places != null) {
                    //マーカーの設置
                    for (PlacesAPI2.PlaceData p : places) {
                        addMarker(new LatLng(p.geometry.location.lat, p.geometry.location.lng), p.name);
                    }
                } else
                    Toast.makeText(getContext(), "検索エラー", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void addMarker(LatLng l, String name){
        //マーカーの追加
        Marker marker = mMap.addMarker(new MarkerOptions().position(l).title(name));
        mMakers.add(marker);
    }
    void removeMarker(){
        //マーカーをすべて削除
        while(mMakers.size()>0) {
            mMakers.get(0).remove();
            mMakers.remove(0);
        }
    }
}
