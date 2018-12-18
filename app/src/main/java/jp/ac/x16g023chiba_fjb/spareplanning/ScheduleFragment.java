package jp.ac.x16g023chiba_fjb.spareplanning;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements DialogFragment2.OnDialogButtonListener {


    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    LinearLayout layout;
    TextView reTimeView;
    int txtSize = 18;
    int nowTime;
    int reTime;

    ArrayList<String> place;

    ArrayList<Integer> duration;

    ArrayList<String> leaveTime = new ArrayList<>();

    ArrayList<Integer> moveMinute;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  フィールドの作成
<<<<<<< HEAD
        int nowHour = ((MainActivity)getActivity()).getNowHour();
        int nowMinute = ((MainActivity)getActivity()).getNowMinute();
        int reHour = ((MainActivity)getActivity()).getReHour();
        int reMinute = ((MainActivity)getActivity()).getReMinute();
        TextView nowTime = view.findViewById(R.id.textView1);
        TextView reTime = view.findViewById(R.id.textView2);
        TextView spaceTime = view.findViewById(R.id.textView3);

        // 現在時刻の表示
        nowTime.setText(nowHour + ":" + String.format("%02d",nowMinute));
        reTime.setText(reHour + ":" + String.format("%02d",reMinute));

        // 空き時間表示
        if (nowMinute > reMinute){
            if (nowHour >= reHour){
                spaceTime.setText("(" + (reHour + 24 - 1 - nowHour) + "時間" + (reMinute + 60 - nowMinute) + "分)");
            }else {
                spaceTime.setText("(" + (reHour - 1 - nowHour) + "時間" + (reMinute + 60 - nowMinute) + "分)");
            }
        }else {
            if (nowHour > reHour){
                spaceTime.setText("(" + (reHour + 24 - nowHour) + "時間" + (reMinute - nowMinute) + "分)");
            }else {
                spaceTime.setText("(" + (reHour - nowHour) + "時間" + (reMinute - nowMinute) + "分)");
            }
=======

        // 現在時刻
        final int nowHour = ((MainActivity) getActivity()).getNowHour();
        final int nowMinute = ((MainActivity) getActivity()).getNowMinute();
        nowTime = conversionMinute(nowHour, nowMinute);

        // 戻り時刻と場所名
        final int reHour = ((MainActivity) getActivity()).getReHour();
        final int reMinute = ((MainActivity) getActivity()).getReMinute();
        reTime = conversionMinute(reHour, reMinute);
        final String rePiace = ((MainActivity) getActivity()).getLastPlace();

        // 目的地１番～戻り場所の一つ前まで
        place = ((MainActivity) getActivity()).getBreakPlace();

        // 各場所の滞在時間
        duration = new ArrayList<>();

        // 現在位置→目的地１番目～戻り場所の一つ前→戻り場所　（要素数はPlaceより１多くなる）
        moveMinute = ((MainActivity) getActivity()).getMoveMinute();

        // 空き時間
        int spaceHour = ((MainActivity) getActivity()).getSpaceHour();
        int spaceMinute = ((MainActivity) getActivity()).getSpaceMinute();

        // ビュー系
        TextView nowTimeView = view.findViewById(R.id.textView1);
        reTimeView = view.findViewById(R.id.textView2);
        TextView spaceTimeView = view.findViewById(R.id.textView3);
        layout = view.findViewById(R.id.ScheduleMain);

        // 空き時間格納変数
        int totalMinute;

        // 設定時刻の表示
        nowTimeView.setText(conversionTime(nowTime));
        reTimeView.setText(conversionTime(reTime));

        // 空き時間表示
        spaceTimeView.setText("(" + spaceHour + "時間" + spaceMinute + "分)");

        // 空き時間計算
        totalMinute = conversionMinute(spaceHour, spaceMinute);

        // 計画時間から移動時間を減算
        for (int i : moveMinute) {
            totalMinute -= i;
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        }
        totalMinute -= ((MainActivity) getActivity()).getLastMove();

<<<<<<< HEAD

        //以下スケジュール作成処理
        layout = view.findViewById(R.id.ScheduleMain);


        //現在位置時刻設定
        nowTime = view.findViewById(R.id.textView1_2);
        nowTime.setTextSize(txtSize);
        nowTime.setText(String.format("%2s",nowHour) + ":" + String.format("%02d",nowMinute));
        TextView nowPlace = view.findViewById(R.id.NowPlace);
        nowPlace.setTextSize(txtSize);

        //以下スケジュール作成処理
        verticalLine(30);
        horizontalLine();
        verticalLine(30);

        //最終目的地表示設定
        reTime = view.findViewById(R.id.textView2_2);
        reTime.setTextSize(txtSize);
        reTime.setText(String.format("%2s",reHour) + ":" + String.format("%02d",reMinute));
=======
        // 均等割り付けで滞在時間を設定
        for (int i = 0; i < place.size() - 1; i++) {

            // 少数値が出る場合、最後の場所の滞在時間が増え過ぎないように
            duration.add((int) (totalMinute / (place.size() - i)));
            totalMinute -= duration.get(i);
        }
        duration.add(totalMinute);

        // 以下スケジュール作成処理

        // 現在位置時刻設定（文字サイズを統一化するため、ここで設定する）
        nowTimeView = view.findViewById(R.id.textView1_2);
        nowTimeView.setTextSize(txtSize);
        nowTimeView.setText(conversionTime(nowTime));
        TextView nowPlace = view.findViewById(R.id.NowPlace);
        nowPlace.setTextSize(txtSize);

        // 前の場所の出発時間を格納、初期値は現在時刻
        int outPlaceTime = nowTime;

        // 一時保存用変数
        int work;

        // アクションバーと連結バーをセットとして表示（placeより要素が１多いmoveMinuteの末尾の値は使わない）
        for (int i = 0; i < place.size(); i++) {

            // 連結バーの表示
            verticalLine(moveMinute.get(i));

            // 整理用に一時保存
            work = timeCalculation(outPlaceTime, moveMinute.get(i));

            //アクションバーの表示
            horizontalLine(place.get(i), work, timeCalculation(work, duration.get(i)), duration.get(i), i);

            // 通知用の値を追加
            leaveTime.add(conversionTime(timeCalculation(work, duration.get(i))));

            // このループの出発時間を次のループの到着時間の計算に使う
            outPlaceTime = timeCalculation(work, duration.get(i));
        }

        // 戻り場所への移動時間の連結バーを表示する
        verticalLine(((MainActivity) getActivity()).getLastMove());

        // 戻り場所のアクションバーの設定
        reTimeView = view.findViewById(R.id.textView2_2);
        reTimeView.setTextSize(txtSize);
        reTimeView.setText((conversionTime(reTime)));
        TextView rePlaceView = view.findViewById(R.id.RePlace);
        rePlaceView.setText(rePiace);
        rePlaceView.setTextSize(txtSize);
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37

        // 戻りボタン
        view.findViewById(R.id.ReturnFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 追加した要素を削除する
                ((MainActivity) getActivity()).getBreakPlace().remove(((MainActivity) getActivity()).getBreakPlace().size() - 1);
                ((MainActivity) getActivity()).getMoveMinute().remove(((MainActivity) getActivity()).getMoveMinute().size() - 1);
                ((MainActivity) getActivity()).changeFragment(MapViewFragment2.class);
            }
        });
<<<<<<< HEAD
    }

    public void verticalLine(int move){   // move : 移動時間(分)
=======

        // プラン追加ボタン
        view.findViewById(R.id.addPlans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setSelectLat(((MainActivity) getActivity()).getSelectLat2());
                ((MainActivity) getActivity()).setSelectLong(((MainActivity) getActivity()).getSelectLong2());
                ((MainActivity) getActivity()).changeFragment(MapViewFragment2.class);
            }
        });

        // ナビ画面ボタン
        view.findViewById(R.id.nextNavi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setLeaveTime(leaveTime);
                ((MainActivity) getActivity()).startNotificationService();
                //((MainActivity) getActivity()).changeFragment(NaviFragment.class);
            }
        });
    }

    //時間を分換算値に変換（[２]:[３０]　→　１５０分）
    public int conversionMinute(int hour, int minute) {
        return hour * 60 + minute;
    }

    //分を時間文字列に変換（１５０分　→　"□２：３０"）
    public String conversionTime(int time) {
        return String.format("%2s", time / 60) + ":" + String.format("%02d", time % 60);
    }

    //時刻計算処理（計算される時間（分換算） , 計算する値（分換算））
    public int timeCalculation(int time, int value) {

        // 加算処理
        time += value;

        //２４時以上になったら
        if (time >= 1440) {
            time -= 1440;
        }
        return time;
    }

    //連結バーと移動時間の表示（移動時間（分））
    public void verticalLine(int move) {

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        //LinearLayout(horizontal)を生成
        LinearLayout LL = new LinearLayout(getActivity());
        LL.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(LL);
<<<<<<< HEAD
        //連結バーを生成
        TextView textView = new TextView(getActivity());
        textView.setWidth(50);
        textView.setBackgroundColor(Color.rgb(255,217,102));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        p.setMargins(100,0,0,0);
        LL.addView(textView,p);
        //歩く人のアイコン設置
        ImageView img = new ImageView(getActivity());
        img.setImageResource(R.drawable.walk);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(50,50);
        p2.setMargins(30,60,0,60);
        LL.addView(img,p2);
=======

        //連結バーを生成
        TextView textView = new TextView(getActivity());
        textView.setWidth(50);
        textView.setBackgroundColor(Color.rgb(255, 217, 102));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        p.setMargins(100, 0, 0, 0);
        LL.addView(textView, p);

        //歩く人のアイコン設置
        ImageView img = new ImageView(getActivity());
        img.setImageResource(R.drawable.walk);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(50, 50);
        p2.setMargins(30, 60, 0, 60);
        LL.addView(img, p2);

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        //移動時間の生成
        TextView textView2 = new TextView(getActivity());
        textView2.setText(String.valueOf(move) + "分");
        textView2.setTextSize(16);
<<<<<<< HEAD
        LinearLayout.LayoutParams p3  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        p3.setMargins(10,60,0,60);
        LL.addView(textView2,p3);
    }

    //横バーの生成
    public  void  horizontalLine(){
=======
        LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        p3.setMargins(10, 60, 0, 60);
        LL.addView(textView2, p3);
    }

    //アクションバーの生成（休憩場所 , 到着時間（分換算） , 出発時間（分換算） , 滞在時間 , 要素番号）
    public void horizontalLine(String place, int inTime, int outTime, int space, final int number) {

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        //色分け用
        LinearLayout LL_HORIZONTAL = new LinearLayout(getActivity());
        LL_HORIZONTAL.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(LL_HORIZONTAL);
<<<<<<< HEAD
        //カラーボックス
        FrameLayout FL_Coler = new FrameLayout(getActivity());
        FL_Coler.setBackgroundColor(Color.rgb(255,136,0));
        LinearLayout.LayoutParams pColor = new LinearLayout.LayoutParams(60,LinearLayout.LayoutParams.MATCH_PARENT);
        LL_HORIZONTAL.addView(FL_Coler,pColor);
        //メイン
        LinearLayout LL_VERTICAL = new LinearLayout(getActivity());
        LL_VERTICAL.setOrientation(LinearLayout.VERTICAL);
        LL_VERTICAL.setBackgroundColor(Color.rgb(255,187,51));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        LL_HORIZONTAL.addView(LL_VERTICAL,p);
=======

        //カラーボックス
        FrameLayout FL_Coler = new FrameLayout(getActivity());
        FL_Coler.setBackgroundColor(Color.rgb(255, 136, 0));
        LinearLayout.LayoutParams pColor = new LinearLayout.LayoutParams(60, LinearLayout.LayoutParams.MATCH_PARENT);
        LL_HORIZONTAL.addView(FL_Coler, pColor);

        //メイン
        LinearLayout LL_VERTICAL = new LinearLayout(getActivity());
        LL_VERTICAL.setOrientation(LinearLayout.VERTICAL);
        LL_VERTICAL.setBackgroundColor(Color.rgb(255, 187, 51));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LL_HORIZONTAL.addView(LL_VERTICAL, p);

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        //整列用一段目
        LinearLayout LL_FirstStage = new LinearLayout(getActivity());
        LL_FirstStage.setOrientation(LinearLayout.HORIZONTAL);
        LL_VERTICAL.addView(LL_FirstStage);
<<<<<<< HEAD
        //到着時刻
        TextView textView = new TextView(getActivity());
        textView.setText("00:00");
        textView.setTextSize(txtSize);
        textView.setTextColor(Color.rgb(255,255,255));
        LinearLayout.LayoutParams p2  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p2.setMargins(20,20,0,20);
        LL_FirstStage.addView(textView,p2);
        //場所名
        TextView textView2 = new TextView(getActivity());
        textView2.setText("休憩場所");
        textView2.setTextSize(txtSize);
        textView2.setTextColor(Color.rgb(255,255,255));
        textView2.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams p4  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p4.setMargins(20,20,0,20);
        LL_FirstStage.addView(textView2,p4);
=======

        //到着時刻
        TextView textView = new TextView(getActivity());
        textView.setText(conversionTime(inTime));
        textView.setTextSize(txtSize);
        textView.setTextColor(Color.rgb(255, 255, 255));
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p2.setMargins(20, 20, 0, 20);
        LL_FirstStage.addView(textView, p2);

        //場所名
        TextView textView2 = new TextView(getActivity());
        textView2.setText(place);
        textView2.setTextSize(txtSize);
        textView2.setTextColor(Color.rgb(255, 255, 255));
        textView2.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams p4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p4.setMargins(20, 20, 0, 20);
        LL_FirstStage.addView(textView2, p4);

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        //二段目
        LinearLayout LL_SecondStage = new LinearLayout(getActivity());
        LL_SecondStage.setOrientation(LinearLayout.HORIZONTAL);
        LL_VERTICAL.addView(LL_SecondStage);
<<<<<<< HEAD
=======

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        //「～」と滞在時間
        TextView textView3 = new TextView(getActivity());
        textView3.setText("|");
        textView3.setTextSize(txtSize);
<<<<<<< HEAD
        textView3.setTextColor(Color.rgb(255,255,255));
        LinearLayout.LayoutParams p5  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p5.setMargins(60,0,0,20);
        LL_SecondStage.addView(textView3,p5);
=======
        textView3.setTextColor(Color.rgb(255, 255, 255));
        LinearLayout.LayoutParams p5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p5.setMargins(60, 0, 0, 20);
        LL_SecondStage.addView(textView3, p5);

        //滞在時間
        TextView textView5 = new TextView(getActivity());
        textView5.setText(String.valueOf(space) + "分");
        textView5.setTextColor(Color.rgb(255, 255, 255));
        LinearLayout.LayoutParams p7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p7.setMargins(20, 0, 0, 20);
        LL_SecondStage.addView(textView5, p7);

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
        //三段目
        LinearLayout LL_ThirdStage = new LinearLayout(getActivity());
        LL_ThirdStage.setOrientation(LinearLayout.HORIZONTAL);
        LL_VERTICAL.addView(LL_ThirdStage);
<<<<<<< HEAD
        //到着時刻
        TextView textView4 = new TextView(getActivity());
        textView4.setText("00:00");
        textView4.setTextSize(txtSize);
        textView4.setTextColor(Color.rgb(255,255,255));
        LinearLayout.LayoutParams p6  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p6.setMargins(20,0,0,20);
        LL_ThirdStage.addView(textView4,p6);
=======

        //到着時刻
        TextView textView4 = new TextView(getActivity());
        textView4.setText(conversionTime(outTime));
        textView4.setTextSize(txtSize);
        textView4.setTextColor(Color.rgb(255, 255, 255));
        LinearLayout.LayoutParams p6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p6.setMargins(20, 0, 0, 20);
        p6.weight = 7.0f;
        LL_ThirdStage.addView(textView4, p6);

            Button button = new Button(getActivity());
            button.setText("編集");
            button.setTextSize(txtSize);
            button.setTextColor(Color.rgb(255, 255, 255));
            button.setBackgroundColor(Color.rgb(255, 136, 0));
            LinearLayout.LayoutParams p8 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p8.weight = 1.0f;
            LL_ThirdStage.addView(button, p8);
            // 滞在時間編集
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).setBreakDuration(duration);
                    ((MainActivity) getActivity()).setBreakNumber(number);

                    //フラグメントのインスタンスを作成
                    DialogFragment2 f = new DialogFragment2();

                    //ダイアログのボタンが押された場合の動作
                    f.setOnDialogButtonListener(ScheduleFragment.this);

                    //フラグメントをダイアログとして表示
                    f.show(getFragmentManager(), "");
                }
            });

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
    }
    int point;
    // ダイアログ実行後の処理(valueには対象の滞在時間の変化分が入る)
    @Override
    public void onDialogButton(int value) {
        layout.removeAllViews();
        leaveTime.clear();
        point -= value;
        duration.set(((MainActivity) getActivity()).getBreakNumber(),duration.get(((MainActivity) getActivity()).getBreakNumber()) - value);

<<<<<<< HEAD
=======
        // 前の場所の出発時間を格納、初期値は現在時刻
        int outPlaceTime = nowTime;

        // 一時保存用変数
        int work;

        for (int i = 0; i < place.size(); i++) {
            // 連結バーの表示
            verticalLine(moveMinute.get(i));

            // 整理用に一時保存
            work = timeCalculation(outPlaceTime, moveMinute.get(i));

            //アクションバーの表示
            horizontalLine(place.get(i), work, timeCalculation(work, duration.get(i)), duration.get(i), i);

            // 通知用の値を追加
            leaveTime.add(conversionTime(timeCalculation(work, duration.get(i))));

            // このループの出発時間を次のループの到着時間の計算に使う
            outPlaceTime = timeCalculation(work, duration.get(i));
        }
        // 戻り場所への移動時間の連結バーを表示する
        verticalLine(((MainActivity) getActivity()).getLastMove());
        reTime -= value;
        reTimeView.setText(conversionTime(reTime));
        FrameLayout hint = getView().findViewById(R.id.hint);
        hint.removeAllViews();
        if (point != 0){
            TextView textView = new TextView(getActivity());
            textView.setTextSize(txtSize);
            if (point > 0){
                textView.setText("休憩時間が " + point + " 分超過しています");
                textView.setPadding(25,25,25,25);
                textView.setTextColor(Color.rgb(255, 0, 0));
                textView.setBackgroundColor(Color.rgb(220, 220, 220));
            }else {
                textView.setText("休憩時間 " + Math.abs(point) + " 分割り振れます");
                textView.setPadding(25,25,25,25);
                textView.setTextColor(Color.rgb(255, 255, 255));
                textView.setBackgroundColor(Color.rgb(255, 136, 0));
            }
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            hint.addView(textView,p);
        }
    }
>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
}
