package jp.ac.chiba_fjb.x16g_c.test09202;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class Sub1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub1);
Intent data = new Intent();
String funsu = getIntent().getStringExtra("funsu");
String jikan = getIntent().getStringExtra("jikan");

        TextView text1 = (TextView)findViewById(R.id.textView8);
        TextView text2 = (TextView)findViewById(R.id.textView9);
        text1.setText(jikan);
        text2.setText(funsu);

TextView text3 = (TextView)findViewById(R.id.textView10);
text3.setText("ジャンルを選択してください");

        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        button2.setText("飲食店");
        button3.setText("喫茶店");
        button4.setText("ゲームセンター");
        button5.setText("マップ");
        button6.setText("");
        button7.setText("");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("geo:0,0?q=飲食店");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("geo:0,0?q=喫茶店");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("geo:0,0?q=ゲームセンター");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(Sub1.this,Map.class);
                //startActivity(intent);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

            public void onClick(View view) {


                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }

    }
}
