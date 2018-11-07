package jp.ac.x16g023chiba_fjb.spareplanning;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //値の受け取り
        int nowHour = ((MainActivity)getActivity()).getNowHour();
        //String converted = FixedLengthTextUtil.paddingCharToRight("aaa", 5, " ");
        int nowMinute = ((MainActivity)getActivity()).getNowMinute();
        int reHour = ((MainActivity)getActivity()).getReHour();
        int reMinute = ((MainActivity)getActivity()).getReMinute();
        TextView nowTime = view.findViewById(R.id.textView1);
        TextView reTime = view.findViewById(R.id.textView2);
        TextView spaceTime = view.findViewById(R.id.textView3);
        nowTime.setText(nowHour + ":" + String.format("%02d",nowMinute));
        reTime.setText(reHour + ":" + String.format("%02d",reMinute));
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

        //デバッグ用戻り
        view.findViewById(R.id.ReturnFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(FirstFragment.class);
            }
        });
        //以下スケジュール作成処理
        layout = view.findViewById(R.id.ScheduleMain);
        nowTime = view.findViewById(R.id.textView1_2);
        reTime = view.findViewById(R.id.textView2_2);
        nowTime.setText(String.format("%2s",nowHour) + ":" + String.format("%02d",nowMinute));
        reTime.setText(String.format("%2s",reHour) + ":" + String.format("%02d",reMinute));
        verticalLine(30);
    }

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
        p.setMargins(120,0,0,0);
        LL.addView(textView,p);
        //歩く人のアイコン設置
        ImageView img = new ImageView(getActivity());
        img.setImageResource(R.drawable.walk);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(60,60);
        p2.setMargins(30,30,0,30);
        LL.addView(img,p2);
        //移動時間の生成
        TextView textView2 = new TextView(getActivity());
        textView2.setText(String.valueOf(move) + "分");
        textView2.setTextSize(25);
        LinearLayout.LayoutParams p3  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        p3.setMargins(10,30,0,30);
        LL.addView(textView2,p3);
    }

    //横バーの生成
    public  void  horizontalLine(){
        LinearLayout LL = new LinearLayout(getActivity());
        LL.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(LL);


    }

}
