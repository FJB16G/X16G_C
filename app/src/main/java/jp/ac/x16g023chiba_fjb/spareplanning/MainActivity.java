package jp.ac.x16g023chiba_fjb.spareplanning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;

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
    double nowLong;
    double nowLat;

    //ゴール地点の緯度経度
    double lastLong;
    double lastLat;

    //指定地点の緯度経度(初期値は現在位置)
    double selectLat;
    double selectLong;

    //指定地点の緯度経度(初期値は現在位置)の退避用
    double selectLat2;
    double selectLong2;

    // 休憩場所（配列の最後には最終目的地が入る）
    ArrayList<String> breakPlace = new ArrayList<String>();

    ArrayList<String> breakLat = new ArrayList<>();
    ArrayList<String> breakLong = new ArrayList<>();

    // 目的地への移動時間（目的地の配列の番号と、目的地に向かう移動時間の配列の番号が同じになるように）
    ArrayList<Integer> moveMinute = new ArrayList<Integer>(); // 分

    // 滞在時間を格納
    ArrayList<Integer> breakDuration = new ArrayList<>();

    // 初期設定空き時間（現在時刻＋？？分）
    int startspaceminute = 30;

    // ダイアログ受け渡し用
    int breakNumber;

    public ArrayList<String> getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(ArrayList<String> leaveTime) {
        this.leaveTime = leaveTime;
    }

    //
    ArrayList<String> leaveTime = new ArrayList<>();

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

    public double getLastLong() {
        return lastLong;
    }

    public double getLastLat() {
        return lastLat;
    }

    public int getLastMove() {
        return lastMove;
    }

    public double getNowLong() {
        return nowLong;
    }

    public double getNowLat() {
        return nowLat;
    }

    public double getSelectLat() {
        return selectLat;
    }

    public double getSelectLong() {
        return selectLong;
    }

    public double getSelectLat2() {
        return selectLat2;
    }

    public double getSelectLong2() {
        return selectLong2;
    }

    public int getBreakNumber() {
        return breakNumber;
    }

    public ArrayList<Integer> getBreakDuration() {
        return breakDuration;
    }

    public ArrayList<String> getBreakLat() {
        return breakLat;
    }

    public ArrayList<String> getBreakLong() {
        return breakLong;
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

    public void setLastLong(double lastLong) {
        this.lastLong = lastLong;
    }

    public void setLastLat(double lastLat) {
        this.lastLat = lastLat;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }

    public void setNowLong(double nowLong) {
        this.nowLong = nowLong;
    }

    public void setNowLat(double nowLat) {
        this.nowLat = nowLat;
    }

    public void setSelectLat(double selectLat) {
        this.selectLat = selectLat;
    }

    public void setSelectLong(double selectLong) {
        this.selectLong = selectLong;
    }

    public void setSelectLat2(double selectLat2) {
        this.selectLat2 = selectLat2;
    }

    public void setSelectLong2(double selectLong2) {
        this.selectLong2 = selectLong2;
    }

    public void setBreakNumber(int breakNumber) {
        this.breakNumber = breakNumber;
    }

    public void setBreakDuration(ArrayList<Integer> breakDuration) {
        this.breakDuration = breakDuration;
    }

    public void setBreakLat(ArrayList<String> breakLat) {
        this.breakLat = breakLat;
    }

    public void setBreakLong(ArrayList<String> breakLong) {
        this.breakLong = breakLong;
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
        // 起点
        String src_lat = String.valueOf(getNowLat());
        String src_ltg = String.valueOf(getNowLong());

        // 目的地
        String des_lat = String.valueOf(getLastLat());
        String des_ltg = String.valueOf(getLastLong());

        //経由地
        ArrayList <String> lat = getBreakLat();
        ArrayList <String> lng = getBreakLong();

        // 移動手段：電車:r, 車:d, 歩き:w
        String[] dir = {"r", "d", "w"};

        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_VIEW);

        // 出発地, 目的地, 交通手段
        String str = String.format(Locale.US,
                "http://maps.google.com/maps?saddr=%s,%s&daddr="
                ,src_lat, src_ltg);
        for (int i = 0 ; i < lat.size() ; i++){
            str += lat.get(i) + "," + lng.get(i) + "+to:";
        }
        str += des_lat + "," + des_ltg + "&dirflg=" + dir[2];

        intent2.setData(Uri.parse(str));
        startActivity(intent2);
    }
}