package jp.ac.x16g023chiba_fjb.spareplanning;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

//必要API
//Maps SDK for Android
//Places SDK for Android

public class Map2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map2);

        Intent data = new Intent();
        String s = getIntent().getStringExtra("s");
        changeFragment(MapViewFragment.class);
    }

    //フラグメント切り替え用
    public void changeFragment(Class c){
        changeFragment(c,null);
    }
    public void changeFragment(Class c,Bundle bundle){
        try {
            Fragment f = (Fragment) c.newInstance();
            if(bundle != null)
                f.setArguments(bundle);
            else
                f.setArguments(new Bundle());

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment,f);
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
