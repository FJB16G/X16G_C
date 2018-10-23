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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //レイアウト切り替え用変数
    FrameLayout base;
    FrameLayout layout;
    //時刻設定用変数　ここから
    NumberPicker numPicker1;
    NumberPicker numPicker2;
    private Time time;
    //時刻設定タイマー処理用変数
    private Handler mHandler = new Handler();
    private Timer mTimer;
    private int agoTime;
    RadioGroup group;
    //空き時間表示用
    private TextView spacehour;
    private TextView spaceminute;
    //アプリ開始時の空き時間（分）
    int startspaceminute = 30;
    // 現在時刻
    int nowHour;       //時
    int nowMinute;     //分
    //戻り到着時間
    int reHour;        //時
    int reMinute;      //分
    //ラジオボタンフラグ(戻り場所が現在位置かそれ以外か)
    int radioflg;
    //カテゴリナンバー
    int categoryNo;
    //検索文字列
    String searchText;
    //休憩場所
    String breakPlace[];
    //現在位置から目的地への移動時間
    int moveMinute[];  //分


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        base = (FrameLayout) findViewById(R.id.output);
        time = new Time("Asia/Tokyo");
        time.setToNow();
        nowHour = time.hour;
        nowMinute = time.minute;
        reHour = nowHour;
        reMinute = nowMinute;
        reMinute = reMinute + startspaceminute;
        if (reMinute >= 60){
            reHour = reHour + 1 ;
        }
        //最初の画面を表示
        First();
    }

    @Override
    public void onClick(View v) {
        //レイアウトのリセット
        base.removeAllViews();
        //例：遷移先（遷移元）
        if (v.getId() == R.id.first_nextbutton){ //カテゴリ画面（時刻設定画面）
            mTimer.cancel();
            if (radioflg == R.id.radioButton){
                Category();
            }else {
                reMap();
            }
        }else if (v.getId() == R.id.ReturnFirst || v.getId() == R.id.ReturnFirst2){ //時刻設定画面に戻る（カテゴリ画面or目的地設定マップ）
            First();
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
        numPicker1.setValue(reHour);
        //ドラムロール（分）
        numPicker2.setMaxValue(59);
        numPicker2.setMinValue(0);
        numPicker2.setValue(reMinute);

        final Button button1 = (Button) findViewById(R.id.first_nextbutton);
        button1.setOnClickListener(this);
        group  = findViewById(R.id.radiogroup);
        group.check(R.id.radioButton);
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
                        //マイナス回避処理（分）
                        int ji = numPicker1.getValue() - nowHour;
                        int fun = numPicker2.getValue() - nowMinute;
                        if (fun < 0){
                            ji = ji - 1;
                            fun = fun + 60;
                        }
                        //マイナス回避処理（時）
                        if (numPicker1.getValue() < nowHour || numPicker1.getValue() == nowHour && numPicker2.getValue() < nowMinute){
                            ji = ji + 24;
                        }
                        spacehour.setText(String.valueOf(ji));
                        spaceminute.setText(String.valueOf(fun));
                        //共有変数に保存
                        reHour = numPicker1.getValue();
                        reMinute = numPicker2.getValue();
                        //一定以下の空き時間の時次へのボタンを押せないように
                        if (ji == 0 && fun < 30){
                            button1.setEnabled(false);
                            button1.setText(startspaceminute + "分以上の空き時間を設定してください" );
                        }else {
                            button1.setEnabled(true);
                            button1.setText("次へ");
                        }
                        radioflg = group.getCheckedRadioButtonId();
                        agoTime = numPicker2.getValue();
                    }
                });
            }
        };
        //タイマーの起動
        mTimer = new Timer();
        mTimer.schedule(timerTask,0,15);

        //時刻更新処理
        ImageView ut = (ImageView)findViewById(R.id.updatenowtime);
        ut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setToNow();
                nowHour = time.hour;
                nowMinute = time.minute;
                reHour = nowHour;
                reMinute = nowMinute;
                reMinute = reMinute + startspaceminute;
                if (reMinute >= 60){
                    reHour = reHour + 1 ;
                }
                //最初の画面を表示
                base.removeAllViews();
                First();
            }
        });
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

    //戻り目的地選択画面
    public void reMap(){
        layout = (FrameLayout) getLayoutInflater().inflate(R.layout.remap_layout, null);
        base.addView(layout);
        //戻るボタン
        ImageView re = (ImageView) findViewById(R.id.ReturnFirst2);
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