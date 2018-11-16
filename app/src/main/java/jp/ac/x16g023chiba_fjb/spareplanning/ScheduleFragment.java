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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {


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
    int txtSize = 18;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  フィールドの作成
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
        }


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

        //デバッグ用戻りボタン
        view.findViewById(R.id.ReturnFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(FirstFragment.class);
            }
        });
    }

    public void verticalLine(int move){   // move : 移動時間(分)
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

    //横バーの生成
    public  void  horizontalLine(){
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
                //三段目
                LinearLayout LL_ThirdStage = new LinearLayout(getActivity());
                LL_ThirdStage.setOrientation(LinearLayout.HORIZONTAL);
                LL_VERTICAL.addView(LL_ThirdStage);
                    //到着時刻
                    TextView textView4 = new TextView(getActivity());
                    textView4.setText("00:00");
                    textView4.setTextSize(txtSize);
                    textView4.setTextColor(Color.rgb(255,255,255));
                    LinearLayout.LayoutParams p6  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    p6.setMargins(20,0,0,20);
                    LL_ThirdStage.addView(textView4,p6);
    }

}
