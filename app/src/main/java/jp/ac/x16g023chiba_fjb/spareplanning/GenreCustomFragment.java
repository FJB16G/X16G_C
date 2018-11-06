package jp.ac.x16g023chiba_fjb.spareplanning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenreCustomFragment extends Fragment implements View.OnClickListener {





    public GenreCustomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre_custom, container, false);
    }

    //チェックボックスの配列
    private CheckBox checkBox[] = new CheckBox[10];
    //チェックボックスの配列
    private ArrayList<String> categoryBox = new ArrayList();
    //保存ボタンの変数
    private Button button;
    //検索文字列格納用変数
    private String sText;
//    //表示領域
//    private TextView textView;
//
//
//    //本番では消す
//    private TextView sText;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //チェックボックスの取得・値の格納
        checkBox[0] = view.findViewById(R.id.cBox1);
        categoryBox.add("restaurant");
        checkBox[1] = view.findViewById(R.id.cBox2);
        categoryBox.add("cafe");
        checkBox[2] = view.findViewById(R.id.cBox3);
        categoryBox.add("book_store");
        checkBox[3] = view.findViewById(R.id.cBox4);
        categoryBox.add("clothing_store");
        checkBox[4] = view.findViewById(R.id.cBox5);
        categoryBox.add("museum");
        checkBox[5] = view.findViewById(R.id.cBox6);
        categoryBox.add("bowling_alley");
        checkBox[6] = view.findViewById(R.id.cBox7);
        categoryBox.add("store");
        checkBox[7] = view.findViewById(R.id.cBox8);
        categoryBox.add("library");
        checkBox[8] = view.findViewById(R.id.cBox9);
        categoryBox.add("movie_theater");
        checkBox[9] = view.findViewById(R.id.cBox10);
        categoryBox.add("shopping_mall");

        for(int i=0;i<10;i++) {
            // チェック状態を false に設定
            checkBox[i].setChecked(false);
        }
//        //本番では消す
//        sText = view.findViewById(R.id.sText);

//        //textviewの取得
//        textView = view.findViewById(R.id.Print_Test);
        //保存ボタン取得・onClickの呼び出し
        button = view.findViewById(R.id.StorageButton);
        button.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        for(int i=0;i<checkBox.length;i++){
            if(checkBox[i].isChecked()){
                sText += categoryBox.get(i);
            }
        }


        ((MainActivity)getActivity()).setSearchText(sText);
//        ((MainActivity)getActivity()).changeFragment(MapViewFragment.class);
    }
}
