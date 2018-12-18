package jp.ac.x16g023chiba_fjb.spareplanning;

<<<<<<< HEAD
=======

import android.graphics.Color;
import android.location.Location;
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

<<<<<<< HEAD
=======
import com.google.android.gms.maps.LocationSource;

import java.util.ArrayList;
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
import java.util.Timer;
import java.util.TimerTask;
//import jp.ac.chiba_fjb.x16g_c.test09202.R;

/**
 * A simple {@link Fragment} subclass.
 */
<<<<<<< HEAD
public class FirstFragment extends Fragment{
=======
public class FirstFragment extends Fragment implements LocationSource.OnLocationChangedListener {


>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
    public FirstFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }
    NumberPicker numPicker1;
    NumberPicker numPicker2;
    String date;
    //時刻設定タイマー処理用変数
    private Handler mHandler = new Handler();
    private Timer mTimer;
    private int agoTime;
    RadioGroup radioGroup;
    //空き時間表示用
    private TextView spacehour;
    private TextView spaceminute;
    //アプリ開始時の空き時間（分）
    int startspaceminute = 30;
    // 現在時刻
    int nowHour;       //時
    int nowMinute;     //分
<<<<<<< HEAD
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView dateText = view.findViewById(R.id.date);
=======

    MyLocationSource ls;
    boolean flg = false;
    Button nextbutton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ls = new MyLocationSource(getContext());
        ls.activate(this);

        // フィールドの作成
        final TextView dateText = view.findViewById(R.id.date);
        nextbutton = view.findViewById(R.id.first_nextbutton);

        nextbutton.setEnabled(false);
        nextbutton.setText("現在位置取得中です、暫くお待ちください・・・" );

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        nowHour = ((MainActivity)getActivity()).getNowHour();
        nowMinute = ((MainActivity)getActivity()).getNowMinute();
        date = nowHour + "時" + nowMinute + "分";
        dateText.setText(date);
        numPicker1 = view.findViewById(R.id.numPicker1);
        numPicker2 = view.findViewById(R.id.numPicker2);
        //ドラムロール（時）
        numPicker1.setMaxValue(23);
        numPicker1.setMinValue(0);
        numPicker1.setValue(((MainActivity)getActivity()).reHour);
        //ドラムロール（分）
        numPicker2.setMaxValue(59);
        numPicker2.setMinValue(0);
        numPicker2.setValue(((MainActivity)getActivity()).reMinute);
<<<<<<< HEAD
        final Button nextbutton = view.findViewById(R.id.first_nextbutton);
=======

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        radioGroup  = view.findViewById(R.id.radiogroup);
        radioGroup.check(R.id.radioButton);
        spacehour = view.findViewById(R.id.spacehour);
        spaceminute = view.findViewById(R.id.spaceminute);
        agoTime = nowMinute;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //ex:１：５９←→２：００
                        if (agoTime == 59 && numPicker2.getValue() == 0) {      //次の瞬間時上げ
                            numPicker1.setValue(numPicker1.getValue() + 1);
                        }else if (agoTime == 0 && numPicker2.getValue() == 59){ //次の瞬間時下げ
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
                        //空き時間の表示
<<<<<<< HEAD
                        spacehour.setText(String.valueOf(ji));
                        spaceminute.setText(String.valueOf(fun));
                        //一定以下の空き時間の時次へのボタンを押せないように
                        if (ji == 0 && fun < 30){
                            nextbutton.setEnabled(false);
                            nextbutton.setText(startspaceminute + "分以上の空き時間を設定してください" );
                        }else {
                            nextbutton.setEnabled(true);
                            nextbutton.setText("次へ");
=======
                        spaceHour = ji;
                        spaceMinute = fun;
                        spaceHourView.setText(String.valueOf(ji));
                        spaceMinuteView.setText(String.valueOf(fun));

                        //現在位置取得後実行
                        if (flg) {
                            //一定以下の空き時間の時次へのボタンを押せないように
                            if (ji == 0 && fun < 30) {
                                nextbutton.setEnabled(false);
                                nextbutton.setText(startSpaceMinute + "分以上の空き時間を設定してください");
                                nextbutton.setBackgroundColor(Color.rgb(255, 200, 100));
                            } else {
                                nextbutton.setEnabled(true);
                                nextbutton.setText("次へ");
                                nextbutton.setBackgroundColor(Color.rgb(255, 136, 0));
                            }
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
                        }
                        agoTime = numPicker2.getValue();
                    }
                });
            }
        };
        //タイマーの起動
        mTimer = new Timer();
        mTimer.schedule(timerTask,0,15);


        //画面切り替えボタン処理
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //結果を保存
                ((MainActivity)getActivity()).setReHour(numPicker1.getValue());
                ((MainActivity)getActivity()).setReMinute(numPicker2.getValue());
                ((MainActivity)getActivity()).setNowHour(nowHour);
                ((MainActivity)getActivity()).setNowMinute(nowMinute);
                mTimer.cancel();
<<<<<<< HEAD
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton){
=======

                // GPSの停止
                ls.deactivate();
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton){
                    ((MainActivity)getActivity()).setLastLat(((MainActivity)getActivity()).getNowLat());
                    ((MainActivity)getActivity()).setLastLong(((MainActivity)getActivity()).getNowLong());
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
                    //カテゴリ選択画面に遷移
                    ((MainActivity)getActivity()).changeFragment(CategoryFragment.class);
                }else {
                    //戻り場所選択画面に遷移
<<<<<<< HEAD
=======
                    ((MainActivity)getActivity()).changeFragment(GoalFragment.class);

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
                }
            }
        });
        //更新・リセットボタン処理
        ImageView ut = view.findViewById(R.id.updatenowtime);
        ut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time time = new Time("Asia/Tokyo");
                time.setToNow();
                nowHour = time.hour;
                nowMinute = time.minute;
                date = nowHour + "時" + nowMinute + "分";
                dateText.setText(date);
                if (nowMinute + startspaceminute < 60){
                    numPicker1.setValue(nowHour);
                }else {
                    numPicker1.setValue(nowHour + 1);
                }
                numPicker2.setValue(nowMinute + startspaceminute);
                agoTime = nowMinute;
<<<<<<< HEAD
            }
        });
    }
}
=======

                // 現在位置の再取得
                flg = false;
                nextbutton.setEnabled(false);
                nextbutton.setText("現在位置取得中です、暫くお待ちください・・・" );
                nextbutton.setBackgroundColor(Color.rgb(255, 200, 100));
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        if (!(flg)){
            ((MainActivity)getActivity()).setNowLat(location.getLatitude());
            ((MainActivity)getActivity()).setNowLong(location.getLongitude());
            ((MainActivity)getActivity()).setSelectLat(location.getLatitude());
            ((MainActivity)getActivity()).setSelectLong(location.getLongitude());
        }
        flg = true;
    }
}
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
