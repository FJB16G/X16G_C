package jp.ac.chiba_fjb.x16g_c.test09202;

import com.google.android.gms.maps.model.LatLng;

public class JsonItem {
    private LatLng latLng;
    private String nameString;
    private int openFlag;
    private String addressString;

    public JsonItem(LatLng latLng, String  nameString,
                    int openFlag, String addressString){
        this.latLng = latLng;
        this.nameString = nameString;
        this.openFlag = openFlag;
        this.addressString = addressString;
    }
    public LatLng getLatLng() {
        return latLng;
    }
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
    public String getNameString() {
        return nameString;
    }
    public void setNameString(String nameString) {
        this.nameString = nameString;
    }
    public int getOpenFlag() {
        return openFlag;
    }
    public void setOpenFlag(int openFlag) {
        this.openFlag = openFlag;
    }
    public String getAddressString() {
        return addressString;
    }
    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }
}