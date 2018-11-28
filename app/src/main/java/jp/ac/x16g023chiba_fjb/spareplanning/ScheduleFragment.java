package jp.ac.x16g023chiba_fjb.spareplanning;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener {


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

    // テキストサイズ
    int txtSize = 18;

    // ボタン識別用リスト
    ArrayList<Button> BtID = new ArrayList<Button>();

    ArrayList<String> place;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  フィールドの作成

        //現在時刻
        int nowHour = ((MainActivity)getActivity()).getNowHour();
        int nowMinute = ((MainActivity)getActivity()).getNowMinute();
        int nowTime = conversionMinute(nowHour,nowMinute);

        //戻り時刻と場所名
        int reHour = ((MainActivity)getActivity()).getReHour();
        int reMinute = ((MainActivity)getActivity()).getReMinute();
        int reTime = conversionMinute(reHour,reMinute);
        String rePiace = ((MainActivity)getActivity()).getLastPlace();

        // 目的地１番～戻り場所の一つ前まで
        //ArrayList<String> place = ((MainActivity)getActivity()).getBreakPlace();
        place = ((MainActivity)getActivity()).getBreakPlace();

        // 各場所の滞在時間
        ArrayList<Integer> duration = new ArrayList<Integer>();

        // 現在位置→目的地１番目～戻り場所の一つ前→戻り場所　（要素数はPlaceより１多くなる）
        ArrayList<Integer> moveMinute = ((MainActivity)getActivity()).getMoveMinute();

        // 空き時間
        int spaceHour = ((MainActivity)getActivity()).getSpaceHour();
        int spaceMinute = ((MainActivity)getActivity()).getSpaceMinute();

        //ビュー系
        TextView nowTimeView = view.findViewById(R.id.textView1);
        TextView reTimeView = view.findViewById(R.id.textView2);
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
        totalMinute = conversionMinute(spaceHour , spaceMinute);

        // 計画時間から移動時間を減算
        for (int i : moveMinute){
            totalMinute -= i;
        }

        // 均等割り付けで滞在時間を設定
        for (int i = 0 ; i < place.size() - 1  ; i++){

            // 少数値が出る場合、最後の場所の滞在時間が増え過ぎないように
            duration.add((int)(totalMinute / (place.size() - i)));
            totalMinute -= duration.get(i);
        }
        duration.add(totalMinute);

        //以下スケジュール作成処理

        //現在位置時刻設定（文字サイズを統一化するため、ここで設定する）
        nowTimeView = view.findViewById(R.id.textView1_2);
        nowTimeView.setTextSize(txtSize);
        nowTimeView.setText(conversionTime(nowTime));
        TextView nowPlace = view.findViewById(R.id.NowPlace);
        nowPlace.setTextSize(txtSize);

        //前の場所の出発時間を格納、初期値は現在時刻
        int outPlaceTime = nowTime;

        // 一時保存用変数
        int work;

        // アクションバーと連結バーをセットとして表示（placeより要素が１多いmoveMinuteの末尾の値は使わない）
        for (int i = 0 ; i < place.size()  ; i++){

            //連結バーの表示
            verticalLine(moveMinute.get(i));

            //整理用に一時保存
            work = timeCalculation(outPlaceTime , moveMinute.get(i));

            //アクションバーの表示
            horizontalLine(place.get(i) , work , timeCalculation(work , duration.get(i)) , duration.get(i));

            //このループの出発時間を次のループの到着時間の計算に使う
            outPlaceTime = timeCalculation(work , duration.get(i));
        }

        //戻り場所への移動時間の連結バーを表示する
        verticalLine(moveMinute.get(moveMinute.size() - 1));

        //戻り場所のアクションバーの生成と設定
        reTimeView = view.findViewById(R.id.textView2_2);
        reTimeView.setTextSize(txtSize);
        reTimeView.setText((conversionTime(conversionMinute(reHour,reMinute))));
        TextView rePlaceView = view.findViewById(R.id.RePlace);
        rePlaceView.setText(rePiace);
        rePlaceView.setTextSize(txtSize);

        //デバッグ用戻りボタン
        view.findViewById(R.id.ReturnFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(FirstFragment.class);
            }
        });

        //プラン追加ボタン
        view.findViewById(R.id.addPlans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }

    //時間を分換算値に変換（[２]:[３０]　→　１５０分）
    public  int conversionMinute(int hour , int minute){
        return hour * 60 + minute;
    }

    //分を時間文字列に変換（１５０分　→　"□２：３０"）
    public String conversionTime(int time){
        return String.format("%2s",time / 60) + ":" + String.format("%02d",time % 60);
    }

    //時刻計算処理（計算される時間（分換算） , 計算する値（分換算））
    public int timeCalculation(int time , int value){

        // 加算処理
            time += value;

        //２４時以上になったら
        if(time >= 1440) {
            time -= 1440;
        }
        return time;
    }

    //連結バーと移動時間の表示（移動時間（分））
    public void verticalLine(int move){

        //LinearLayout(horizontal)を生成
        LinearLayout LL = new LinearLayout(getActivity());
        LL.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(LL);

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

            //移動時間の生成
            TextView textView2 = new TextView(getActivity());
            textView2.setText(String.valueOf(move) + "分");
            textView2.setTextSize(16);
            LinearLayout.LayoutParams p3  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            p3.setMargins(10,60,0,60);
            LL.addView(textView2,p3);
    }

    //アクションバーの生成（休憩場所 , 到着時間（分換算） , 出発時間（分換算） , 滞在時間）
    public  void  horizontalLine(String place , int inTime , int outTime , int space){

        //色分け用
        LinearLayout LL_HORIZONTAL = new LinearLayout(getActivity());
        LL_HORIZONTAL.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(LL_HORIZONTAL);

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

                //整列用一段目
                LinearLayout LL_FirstStage = new LinearLayout(getActivity());
                LL_FirstStage.setOrientation(LinearLayout.HORIZONTAL);
                LL_VERTICAL.addView(LL_FirstStage);

                    //到着時刻
                    TextView textView = new TextView(getActivity());
                    textView.setText(conversionTime(inTime));
                    textView.setTextSize(txtSize);
                    textView.setTextColor(Color.rgb(255,255,255));
                    LinearLayout.LayoutParams p2  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    p2.setMargins(20,20,0,20);
                    LL_FirstStage.addView(textView,p2);

                    //場所名
                    TextView textView2 = new TextView(getActivity());
                    textView2.setText(place);
                    textView2.setTextSize(txtSize);
                    textView2.setTextColor(Color.rgb(255,255,255));
                    textView2.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams p4  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    p4.setMargins(20,20,0,20);
                    LL_FirstStage.addView(textView2,p4);

                //二段目
                LinearLayout LL_SecondStage = new LinearLayout(getActivity());
                LL_SecondStage.setOrientation(LinearLayout.HORIZONTAL);
                LL_VERTICAL.addView(LL_SecondStage);

                    //「～」と滞在時間
                    TextView textView3 = new TextView(getActivity());
                    textView3.setText("|");
                    textView3.setTextSize(txtSize);
                    textView3.setTextColor(Color.rgb(255,255,255));
                    LinearLayout.LayoutParams p5  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    p5.setMargins(60,0,0,20);
                    LL_SecondStage.addView(textView3,p5);

                    //滞在時間
                    TextView textView5 = new TextView(getActivity());
                    textView5.setText(String.valueOf(space) + "分");
                    textView5.setTextColor(Color.rgb(255,255,255));
                    LinearLayout.LayoutParams p7  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    p7.setMargins(20,0,0,20);
                    LL_SecondStage.addView(textView5,p7);

                //三段目
                LinearLayout LL_ThirdStage = new LinearLayout(getActivity());
                LL_ThirdStage.setOrientation(LinearLayout.HORIZONTAL);
                LL_VERTICAL.addView(LL_ThirdStage);

                    //到着時刻
                    TextView textView4 = new TextView(getActivity());
                    textView4.setText(conversionTime(outTime));
                    textView4.setTextSize(txtSize);
                    textView4.setTextColor(Color.rgb(255,255,255));
                    LinearLayout.LayoutParams p6  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    p6.setMargins(20,0,0,20);
                    p6.weight = 7.0f;
                    LL_ThirdStage.addView(textView4,p6);

                    Button button = new Button(getActivity());
                    button.setText("編集");
                    button.setTextSize(txtSize);
                    LinearLayout.LayoutParams p8  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    p8.weight = 1.0f;
                    LL_ThirdStage.addView(button,p8);

                    // ボタン識別用
                    BtID.add(button);
                    button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0 ; i < BtID.size() ; i++){
            if (v.getId() == BtID.get(i).getId()){
                System.out.print(place.get(i));
                break;
            }
        }
    }
}
