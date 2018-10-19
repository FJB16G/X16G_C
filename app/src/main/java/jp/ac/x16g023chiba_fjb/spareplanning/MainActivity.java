package jp.ac.x16g023chiba_fjb.spareplanning;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //時刻設定用変数　ここから
    private TextView pickerTextView;
    NumberPicker numPicker0;
    NumberPicker numPicker1;
    NumberPicker numPicker2;
    NumberPicker numPicker3;
    NumberPicker numPicker4;

    int flg = 0;
    int ji2k = 0;
    int funk = 0;

    // 空き時間　結果の変数　ji2・・・時間　fun2・・・分数

    //時刻設定画面用変数　ここまで


    String[] figures = new String[5];

    FrameLayout base;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        base = (FrameLayout) findViewById(R.id.output);
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

            //カテゴリ画面（時刻設定画面）
            case R.id.first_nextbutton:
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
        final Time time = new Time("Asia/Tokyo");
        time.setToNow();
        String date = time.hour + "時" + time.minute + "分";
        dateText.setText(date);

        numPicker1 = findViewById(R.id.numPicker1);
        numPicker2 = findViewById(R.id.numPicker2);

        numPicker1.setMaxValue(23);
        numPicker1.setMinValue(time.hour);
        numPicker1.setValue(time.hour);

        numPicker2.setMaxValue(59);
        numPicker2.setMinValue(0);
        numPicker2.setValue(time.minute);


        final TextView text2 = (TextView) findViewById(R.id.textView6);
        final TextView text1 = (TextView) findViewById(R.id.textView4);

        Button button = (Button) findViewById(R.id.first_nextbutton);
        button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            figures[0] = String.valueOf(numPicker1.getValue());
            figures[1] = String.valueOf(numPicker2.getValue());
            int ji = Integer.parseInt(figures[0]);
            int fun = Integer.parseInt(figures[1]);
            int ji2 = ji - time.hour;
            int fun2 = fun - time.minute;

            if (ji2 == 0 && fun2 <= 0) {
                Toast t3 = Toast.makeText(MainActivity.this, "有効な時間を選択してください", Toast.LENGTH_LONG);
                t3.show();

            } else if (ji2 >= 0) {
                if (fun2 <= -1) {
                    fun2 = fun2 + 60;
                    ji2 = ji2 - 1;
                }
                String jikan = String.valueOf(ji2);
                String funsu = String.valueOf(fun2);
                text1.setText(jikan);
                text2.setText(funsu);
            }
        }
    });
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