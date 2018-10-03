import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CameraPosition cameraPosition;
    private String language="JA";
    private Marker marker;
    private java.util.Set<Marker> mMarker = new java.util.HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
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
        mMap = googleMap;

        //自分の位置をマップ上に表示
        MyLocationSource ls = new MyLocationSource(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true); //警告は無視
        mMap.setLocationSource(ls);
        UiSettings settings = mMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        String[] placeTypeArray = {"atm", "convenience_store"};
        setJsonList(mMap.getCameraPosition().target, 1000, placeTypeArray);
    }
        private void setJsonList(LatLng hereLL,int m,String[] typeArray){
            final List<JsonItem> childItem = new ArrayList<JsonItem>();
            String API = getResources().getString(R.string.google_maps_key);
            double centerLat = hereLL.latitude;
            double centerLng = hereLL.longitude;
            for(int j=0 ; j<typeArray.length ; j++){
//指定タイプごとにurlを用意します。2つであれば2回実行することになります。
                final String typeString = typeArray[j];
                String addressUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+centerLat+","+centerLng+"&radius="+m+"&type="+typeString+"&language="+language+"&key="+API;
                JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET, addressUrl, null, new Response.Listener<JSONObject>() {
                  @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for(int i = 0; i<response.getJSONArray("results").length() ; i++) {
                                LatLng latLng = new LatLng(
                                        Double.parseDouble(response.getJSONArray("results")
                                                .getJSONObject(i).getJSONObject("geometry")
                                                .getJSONObject("location").getString("lat"))
                                        ,Double.parseDouble(response.getJSONArray("results")
                                        .getJSONObject(i).getJSONObject("geometry")
                                        .getJSONObject("location").getString("lng"))
                                );
/*
LatLngで扱いますから取得した緯度、経度をLatLngとします。
*/
                                int openFlag;
                                if(response.getJSONArray("results").getJSONObject(i)
                                        .has("opening_hours")){
                                    String openFlagString = response.getJSONArray("results").getJSONObject(i).getJSONObject("opening_hours").getString("open_now");
                                    if (openFlagString.equals("true")) openFlag = 0;
                                    else openFlag = 1;
                                }else{
                                    openFlag = -1;
                                }
/*
opening_hours自体が存在するかどうかhas()で確認を行い、値を取得します。
true,falseを取得するわけですがStringですのでequalsで判定し、trueで0としました。
has()がfalse、つまり存在しない場合は-1とします。
レーティング情報についても同様に存在判定を行います。存在しない場合は同様に-1とします
*/
                                float ratingFloat;
                                if(response.getJSONArray("results").getJSONObject(i)
                                .has("rating")){
                                    ratingFloat = Float.parseFloat(response.getJSONArray("results").getJSONObject(i).getString("rating"));
                                }else{
                                    ratingFloat = -1;
                                }
/*
LIST<JsonItem>のchildItemに値を入れていきます。
*/
                                childItem.add(new JsonItem(
                                        latLng
                                        , response.getJSONArray("results").getJSONObject(i)
                                        .getString("name")
                                        , openFlag
                                        , response.getJSONArray("results").getJSONObject(i)
                                        .getString("vicinity")
                                ));
                                int nowNumber = childItem.size()-1;
//現時点でのchildItemの最後の値を取得するため、nowNumberを用意
//マーカーを地図にセットしていきます。
                                marker = mMap.addMarker(new MarkerOptions()
                                        .position(childItem.get(nowNumber).getLatLng())
                                        .icon(BitmapDescriptorFactory
                                                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                                        .title(childItem.get(nowNumber).getNameString())
                                        .snippet(childItem.get(nowNumber).getAddressString()));
                                mMarker.add(marker);
                            }
                        } catch (JSONException e) {
                            Log.d("Log0",e.getMessage());
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("Log0", "Error: " + error.getMessage());
                            }
                        });
                AppController.getInstance().addToRequestQueue(movieReq);
            }
        }
    }
}
