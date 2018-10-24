package jp.ac.x16g023chiba_fjb.spareplanning;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

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

    int startspaceminute = 30;



    //以下ゲッター群--------------------------------------------------------------------------------

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

    public String getSearchText() {
        return searchText;
    }

    public String[] getBreakPlace() {
        return breakPlace;
    }

    public int[] getMoveMinute() {
        return moveMinute;
    }

    //以下セッター群--------------------------------------------------------------------------------

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

    //----------------------------------------------------------------------------------------------

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
}