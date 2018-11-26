package jp.ac.x16g023chiba_fjb.spareplanning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;

import java.util.ArrayList;

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

    // 最終目的地
    String lastPlace;

    // 休憩場所（配列の最後には最終目的地が入る）
    ArrayList<String> breakPlace;

    // 目的地への移動時間（目的地の配列の番号と、目的地に向かう移動時間の配列の番号が同じになるように）
    ArrayList<Integer> moveMinute; // 分

    // 初期設定空き時間（現在時刻＋？？分）
    int startspaceminute = 30;

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
}