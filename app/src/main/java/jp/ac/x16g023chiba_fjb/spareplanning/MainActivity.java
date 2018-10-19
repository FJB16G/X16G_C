package jp.ac.x16g023chiba_fjb.spareplanning;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //時刻設定用変数　ここから
    NumberPicker numPicker1;
    NumberPicker numPicker2;
    //タイマー用
    private TextView spacehour;
    private TextView spaceminute;
    private Handler mHandler = new Handler();
    private Timer mTimer;
    private int agoTime;
    //時刻設定画面用変数　ここまで
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



    FrameLayout base;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        base = (FrameLayout) findViewById(R.id.output);
        final Time time = new Time("Asia/Tokyo");
        time.setToNow();
        nowHour = time.hour;
        nowMinute = time.minute;
        //最初の画面を表示
        First();
    }

    @Override
    public void onClick(View v) {
        //レイアウトのリセット
        base.removeAllViews();
        //例：遷移先（遷移元：追加値・追加値）
        switch (v.getId()) {
            //時刻設定画面（カテゴリ画面）
            case R.id.ReturnFirst:
                First();
                break;
            //カテゴリ画面（マップ画面：検索文字列）
            //case

            case R.id.first_nextbutton:
                //カテゴリ画面（時刻設定画面）
                Category();
                break;
            //マップ画面（スケジュール画面：検索結果）
            //case

            //マップ画面（カテゴリ画面）
            //case

            //break;
            //スケジュール画面（マップ画面）
            //case

            //break;
            //ナビ画面（スケジュール画面）
            //case

            //break;
        }
    }


    //トップページ（時間・目的地）
    public void First() {
        //レイアウト切り替え
        layout = (FrameLayout) getLayoutInflater().inflate(R.layout.first_layout, null);
        base.addView(layout);

        TextView dateText = (TextView) findViewById(R.id.date);

        String date = nowHour + "時" + nowMinute + "分";
        dateText.setText(date);

        numPicker1 = findViewById(R.id.numPicker1);
        numPicker2 = findViewById(R.id.numPicker2);
        //ドラムロール（時）
        numPicker1.setMaxValue(23);
        numPicker1.setMinValue(0);
        numPicker1.setValue(nowHour);
        //ドラムロール（分）
        numPicker2.setMaxValue(59);
        numPicker2.setMinValue(0);
        numPicker2.setValue(nowMinute);

        spacehour = (TextView) findViewById(R.id.spacehour);
        spaceminute = (TextView) findViewById(R.id.spaceminute);
        agoTime = nowMinute;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //UI関係の処理をサブスレッドで処理するとエラー
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //例：１：５９←→２：００
                        if (agoTime == 59 && numPicker2.getValue() == 0) {
                            numPicker1.setValue(numPicker1.getValue() + 1);
                        }else if (agoTime == 0 && numPicker2.getValue() == 59){
                            numPicker1.setValue(numPicker1.getValue() - 1);
                        }
                        int ji = numPicker1.getValue() - nowHour;
                        int fun = numPicker2.getValue() - nowMinute;
                        if (fun < 0){
                            ji = ji - 1;
                            fun = fun + 60;
                        }
                        if (numPicker1.getValue() < nowHour || numPicker1.getValue() == nowHour && numPicker2.getValue() < nowMinute){
                            ji = ji + 24;
                        }
                        spacehour.setText(String.valueOf(String.valueOf(ji)));
                        spaceminute.setText(String.valueOf(String.valueOf(fun)));
                        agoTime = numPicker2.getValue();
                    }
                });
            }
        };
        //タイマーの起動
        mTimer = new Timer();
        mTimer.schedule(timerTask,0,10);
        Button button1 = (Button) findViewById(R.id.first_nextbutton);
        button1.setOnClickListener(this);
    }

            //カテゴリ画面
            public void Category() {
                //レイアウト切り替え
                layout = (FrameLayout) getLayoutInflater().inflate(R.layout.category_layout, null);
                base.addView(layout);
                //戻るボタン
                ImageView re = (ImageView) findViewById(R.id.ReturnFirst);
                re.setOnClickListener(this);
            }

            //マップ画面
            public void Map() {
                //レイアウト切り替え
                layout = (FrameLayout) getLayoutInflater().inflate(R.layout.map_layout, null);
                base.addView(layout);
            }

            //スケジュール画面
            public void Schedule() {
                //レイアウト切り替え
                layout = (FrameLayout) getLayoutInflater().inflate(R.layout.schedule_layout, null);
                base.addView(layout);
            }

            //ナビ画面
            public void navi() {
                //レイアウト切り替え

            }

        }