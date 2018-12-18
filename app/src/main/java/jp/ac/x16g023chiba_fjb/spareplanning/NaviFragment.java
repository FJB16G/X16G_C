package jp.ac.x16g023chiba_fjb.spareplanning;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.google.android.gms.maps.LocationSource;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class NaviFragment extends Fragment implements LocationSource.OnLocationChangedListener {
    public NaviFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyLocationSource ls;
        ls = new MyLocationSource(getContext());
        ls.activate(this);

        // 起点

        String src_lat = String.valueOf(((MainActivity) getActivity()).getNowLat());
        String src_ltg = String.valueOf(((MainActivity) getActivity()).getNowLong());

        // 目的地
        String des_lat = String.valueOf(((MainActivity) getActivity()).getLastLat());
        String des_ltg = String.valueOf(((MainActivity) getActivity()).getLastLong());

        //経由地
        ArrayList <String> lat = new ArrayList<>();

        lat.add("35.700204");
        lat.add("35.701851");

        ArrayList <String> lng = new ArrayList<>();

        lng.add("139.985550");
        lng.add("139.983522");

        // 移動手段：電車:r, 車:d, 歩き:w
        String[] dir = {"r", "d", "w"};

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        // 出発地, 目的地, 交通手段
        String str = String.format(Locale.US,
                "http://maps.google.com/maps?saddr=%s,%s&daddr="
                ,src_lat, src_ltg);
        for (int i = 0 ; i < lat.size() ; i++){
            str += lat.get(i) + "," + lng.get(i) + "+to:";
        }
        str += des_lat + "," + des_ltg + "&dirflg=" + dir[2];

        intent.setData(Uri.parse(str));
        startActivity(intent);

    }
    // 地名を入れて経路を検索


    // 緯度経度を入れて経路を検索
//    private void test1(){
//        // 起点の緯度経度
//        String src_lat = "35.681382";
//        String src_ltg = "139.7660842";
//
//        // 目的地の緯度経度
//        String des_lat = "35.684752";
//        String des_ltg = "139.707937";
//
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//
//        intent.setClassName("com.google.android.apps.maps",
//                "com.google.android.maps.MapsActivity");
//
//        // 起点の緯度,経度, 目的地の緯度,経度
//        String str = String.format(Locale.US,
//                "http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",
//                src_lat, src_ltg, des_lat, des_ltg);
//
//        intent.setData(Uri.parse(str));
//        startActivity(intent);
//    }

    @Override
    public void onLocationChanged(Location location) {

    }

}