package jp.ac.x16g023chiba_fjb.spareplanning;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment2 extends android.support.v4.app.DialogFragment implements View.OnClickListener {

    NumberPicker numberPicker;


    public DialogFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return d;
    }

    @Override
    public void onClick(View v) {
        //リスナーに登録されているメソッドを呼び出す
        if(mListener != null) {
            if (v.getId() == R.id.dialogButton)
                mListener.onDialogButton(0);
        }
        //ダイアログを閉じる
        getDialog().cancel();
    }



    //インタフェイスの定義
    public interface OnDialogButtonListener{
        void onDialogButton(int value);
    }
    //インタフェイスのインスタンス保存用
    OnDialogButtonListener mListener;

    //ボタン動作のインスタンスを受け取る
    public void setOnDialogButtonListener(OnDialogButtonListener listener){
        mListener =  listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.dialogButton).setOnClickListener(this);
        numberPicker = view.findViewById(R.id.numPicker4);
        int max = ((MainActivity)getActivity()).getSpaceHour() * 60 + ((MainActivity)getActivity()).getSpaceMinute();

        // 空き時間の総計から移動時間を引く
        for (int move : ((MainActivity)getActivity()).getMoveMinute()){
            max -= move;
        }
        max -= ((MainActivity)getActivity()).getLastMove();

        // 滞在時間のそれぞれが１分以上になるように最大値を調整
        max -= (((MainActivity)getActivity()).getBreakPlace().size() - 1);
        numberPicker.setMaxValue(max);
        numberPicker.setMinValue(1);
        numberPicker.setValue(((MainActivity)getActivity()).getBreakDuration().get(((MainActivity)getActivity()).getBreakNumber()));
    }
}


