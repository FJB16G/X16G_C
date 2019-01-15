package jp.ac.x16g023chiba_fjb.spareplanning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // 現在時刻
    int nowHour;       // 時
    int nowMinute;     // 分

    // 戻り到着時間
    int reHour;        // 時
    int reMinute;      // 分

    // 空き時間
    int spaceHour;
    int spaceMinute;

    // 検索文字列
    String searchText;

    // 最終目的地（デフォルトは現在位置）
    String lastPlace = "現在位置";

    // 最後の休憩場所からゴール地点までの移動時間
    int lastMove;

    //現在位置の緯度経度
    LatLng nowLatLong;

    //ゴール地点の緯度経度
    LatLng lastLatLong;

    //指定地点の緯度経度(初期値は現在位置)
    LatLng selectLatLong;

    //指定地点の緯度経度(初期値は現在位置)の退避用
    LatLng selectLatLong2;

    // 休憩場所（配列の最後には最終目的地が入る）
    ArrayList<String> breakPlace = new ArrayList<String>();

    ArrayList<LatLng> breakLatLong = new ArrayList<>();

    // 目的地への移動時間（目的地の配列の番号と、目的地に向かう移動時間の配列の番号が同じになるように）
    ArrayList<Integer> moveMinute = new ArrayList<Integer>(); // 分

    // 滞在時間を格納
    ArrayList<Integer> breakDuration = new ArrayList<>();

    // 初期設定空き時間（現在時刻＋？？分）
    int startspaceminute = 30;

    // ダイアログ受け渡し用
    int breakNumber;

    //通知用時間格納リスト
    ArrayList<String> leaveTime = new ArrayList<>();

    //戻りボタン遷移フラグ
    boolean reFlg = false;

    // 以下ゲッター群--------------------------------------------------------------------------------

    public int getNowHour() {
        return nowHour;
    }

    public int getNowMinute() {
        return nowMinute;
    }

    public int getReHour() {
        return reHour;
    }

    public int getReMinute() {
        return reMinute;
    }

    public String getSearchText(){ return searchText; }

    public String getLastPlace() {
        return lastPlace;
    }

    public int getSpaceHour() {
        return spaceHour;
    }

    public int getSpaceMinute() {
        return spaceMinute;
    }

    public ArrayList<String> getBreakPlace() {
        return breakPlace;
    }

    public ArrayList<Integer> getMoveMinute() {
        return moveMinute;
    }

    public int getLastMove() {
        return lastMove;
    }

    public int getBreakNumber() {
        return breakNumber;
    }

    public ArrayList<Integer> getBreakDuration() {
        return breakDuration;
    }

    public LatLng getSelectLatLong2() {
        return selectLatLong2;
    }

    public LatLng getSelectLatLong() {
        return selectLatLong;
    }

    public ArrayList<LatLng> getBreakLatLong() {
        return breakLatLong;
    }

    public ArrayList<String> getLeaveTime() {
        return leaveTime;
    }

    public LatLng getNowLatLong() {
        return nowLatLong;
    }

    public LatLng getLastLatLong() {
        return lastLatLong;
    }

    // 以下セッター群--------------------------------------------------------------------------------

    public void setNowHour(int nowHour) {
        this.nowHour = nowHour;
    }

    public void setNowMinute(int nowMinute) {
        this.nowMinute = nowMinute;
    }

    public void setReHour(int reHour) {
        this.reHour = reHour;
    }

    public void setReMinute(int reMinute) {
        this.reMinute = reMinute;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void setLastPlace(String lastPlace) {
        this.lastPlace = lastPlace;
    }

    public void setSpaceHour(int spaceHour) {
        this.spaceHour = spaceHour;
    }

    public void setSpaceMinute(int spaceMinute) {
        this.spaceMinute = spaceMinute;
    }

    public void setBreakPlace(ArrayList<String> breakPlace) {
        this.breakPlace = breakPlace;
    }

    public void setMoveMinute(ArrayList<Integer> moveMinute) {
        this.moveMinute = moveMinute;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }

    public void setBreakNumber(int breakNumber) {
        this.breakNumber = breakNumber;
    }

    public void setBreakDuration(ArrayList<Integer> breakDuration) {
        this.breakDuration = breakDuration;
    }

    public void setBreakLatLong(ArrayList<LatLng> breakLatLong) {
        this.breakLatLong = breakLatLong;
    }

    public void setSelectLatLong(LatLng selectLatLong) {
        this.selectLatLong = selectLatLong;
    }

    public void setSelectLatLong2(LatLng selectLatLong2) {
        this.selectLatLong2 = selectLatLong2;
    }

    public void setLeaveTime(ArrayList<String> leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setNowLatLong(LatLng nowLatLong) {
        this.nowLatLong = nowLatLong;
    }

    public void setLastLatLong(LatLng lastLatLong) {
        this.lastLatLong = lastLatLong;
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初期設定
        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        setNowHour(time.hour);
        setNowMinute(time.minute);
        if (nowMinute + startspaceminute < 60){
            setReHour(nowHour);
        }else {
            setReHour(nowHour + 1);
        }
        setReMinute(nowMinute + startspaceminute);
        // 最初の画面を表示
        changeFragment(FirstFragment.class);
    }

    public void changeFragment(Class c){
        changeFragment(c,null);
    }

    public void changeFragment(Class c,Bundle budle){
        try {
            Fragment f = (Fragment) c.newInstance();
            if(budle != null)
                f.setArguments(budle);
            else
                f.setArguments(new Bundle());

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.output,f);
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startLeadService(){
        //MainActivity送信側
        Intent intent = new Intent(this,NotificationService.class);
        intent.putStringArrayListExtra("list",getLeaveTime());
        startService(intent);

        //以下ナビゲーション処理

        // 移動手段：電車:r, 車:d, 歩き:w
        String[] dir = {"r", "d", "w"};

        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_VIEW);

        // 出発地, 目的地, 交通手段
        String str = String.format(Locale.US,
                "http://maps.google.com/maps?saddr=%s,%s&daddr="
                ,String.valueOf(getNowLatLong().latitude), String.valueOf(getNowLatLong().longitude));
        for (int i = 0 ; i < getBreakLatLong().size() ; i++){
            str += String.valueOf(getBreakLatLong().get(i).latitude) + "," + String.valueOf(getBreakLatLong().get(i).longitude) + "+to:";
        }
        str += String.valueOf(getLastLatLong().latitude) + "," + getLastLatLong().longitude + "&dirflg=" + dir[2];

        intent2.setData(Uri.parse(str));
        startActivity(intent2);
    }
}