package jp.ac.x16g023chiba_fjb.spareplanning;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

//     ((MainActivity)getActivity()).getSearchText();


    private GoogleMap mMap;
    private List<Marker> mMakers = new LinkedList<Marker>();
    private PlacesAPI mPlaces;
    private Handler mHandler;
    EditText kensaku;
    LatLng selectLatLong;
    String name;
    LinearLayout layout;

    public GoalFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.Decision);
        kensaku = view.findViewById(R.id.search);
        kensaku.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //PlaceAPIに検索ワードを投げる
                    mPlaces.search(mMap,v.getText().toString(), new PlacesAPI.PlaceListener() {
                        @Override
                        public void onPlaces(List<Place> places) {
                            //removeMarker();
                            mMap.clear();
                            if (places != null) {
                                //マーカーの設置
                                for (Place p : places) {
                                    MarkerOptions options = new MarkerOptions();
                                    options.position(p.getLatLng());
                                    // アイコンの変更
                                    // (1) 色選択
                                    BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                                    // (2) リソースのアイコン画像
                                    //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
                                    options.icon(icon);
                                    // アイコンの名前
                                    options.title(p.getName().toString());
                                    mMap.addMarker(options);

                                    //旧型式のマーカー追加命令
                                    //addMarker(p.getLatLng(), p.getName().toString());
                                }
                            } else
                                Toast.makeText(getContext(), "検索エラー", Toast.LENGTH_SHORT).show();
                        }
                    });
                    handled = true;
                }
                // ソフトキーボードを非表示にする
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                kensaku.setCursorVisible(false);
                return handled;
            }
        });

        //マップ操作用
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //PlaceAPIの初期化
        mPlaces = new PlacesAPI(getContext());
        final Handler handle = new Handler();
        //EditTextのイベント設定
        //((EditText)view.findViewById(R.id.search)).setText(((MainActivity)getActivity()).getLastPlace());
//        new Thread(){
//            @Override
//            public void run() {
//
//
//
//                handle.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//            }
//        }.start();

        view.findViewById(R.id.imageView10000).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(FirstFragment.class);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //マップ初期化処理
        mMap = googleMap;

        //自分の位置をマップ上に表示

        //googleMap.setMyLocationEnabled(true); //警告は無視
        googleMap.setLocationSource(new MyLocationSource(getActivity()));
        UiSettings settings = mMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMarkerClickListener(this);
        //位置設定
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(((MainActivity)getActivity()).getNowLatLong(), 15.0f));   //範囲2.0～21.0(全体～詳細)

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                MarkerOptions op = new MarkerOptions();
                op.title("タップした地点");
                op.position(latLng);
                BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                op.icon(icon);
                mMap.addMarker(op);
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                layout.removeAllViews();
            }
        });
        //Toast.makeText(getContext(), "検索開始",Toast.LENGTH_SHORT).show();
    }

    void addMarker(LatLng l,String name){
        
        //マーカーの追加
        Marker marker = mMap.addMarker(new MarkerOptions().position(l).title(name));
        mMakers.add(marker);
    }

    void removeMarker() {
        //マーカーをすべて削除
        while (mMakers.size() > 0) {
            mMakers.get(0).remove();
            mMakers.remove(0);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        System.out.print(marker.getPosition().latitude + " : " + marker.getPosition().longitude);
        selectLatLong = marker.getPosition();
        name = marker.getTitle();
        layout.removeAllViews();
        Button button = new Button(getActivity());
        button.setText("決定");
        button.setTextSize(25);
        button.setBackgroundColor(Color.rgb(255,187,51));
        button.setTextColor(Color.rgb(255,255,255));
        LinearLayout.LayoutParams p  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0,0,10,15);
        layout.addView(button,p);
        button.setOnClickListener(this);
        return false;
    }

    @Override
    public void onClick(View v) {

        //値を受け渡してカテゴリ画面へ
        ((MainActivity)getActivity()).setLastPlace(name);
        ((MainActivity)getActivity()).setLastLatLong(selectLatLong);
        ((MainActivity)getActivity()).changeFragment(CategoryFragment.class);
    }





}
