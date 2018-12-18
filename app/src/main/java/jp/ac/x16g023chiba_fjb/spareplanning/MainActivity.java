package jp.ac.x16g023chiba_fjb.spareplanning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;

public class MainActivity extends AppCompatActivity {

    //スケジュール生成に必要な要素
    // 現在時刻
    int nowHour;       //時
    int nowMinute;     //分
    //戻り到着時間
    int reHour;        //時
    int reMinute;      //分
    //カテゴリナンバー
    int categoryNo;
    //検索文字列
    String searchText;
    //休憩場所
    String breakPlace[];
    //現在位置から目的地への移動時間
    int moveMinute[];  //分
    //初期設定空き時間（現在時刻＋？？分）
    int startspaceminute = 30;

<<<<<<< HEAD
=======
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
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37


<<<<<<< HEAD
    //以下ゲッター群--------------------------------------------------------------------------------
=======
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
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37

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

    public int getCategoryNo() {
        return categoryNo;
    }

    public String getSearchText(){ return searchText; }

    public String[] getBreakPlace() {
        return breakPlace;
    }

    public int[] getMoveMinute() {
        return moveMinute;
    }

<<<<<<< HEAD
    //以下セッター群--------------------------------------------------------------------------------
=======
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
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37

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

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void setBreakPlace(String[] breakPlace) {
        this.breakPlace = breakPlace;
    }

    public void setMoveMinute(int[] moveMinute) {
        this.moveMinute = moveMinute;
    }

<<<<<<< HEAD
    //----------------------------------------------------------------------------------------------
=======
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
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初期設定
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
        //最初の画面を表示
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

    public void startNotificationService(){
        //MainActivity送信側
        Intent intent = new Intent(this,NotificationService.class);
        intent.putStringArrayListExtra("list",getLeaveTime());
        startService(intent);
        changeFragment(NaviFragment.class);
    }
}