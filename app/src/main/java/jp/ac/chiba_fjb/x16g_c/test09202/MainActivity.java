package jp.ac.chiba_fjb.x16g_c.test09202;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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


public class MainActivity extends AppCompatActivity {

     NumberPicker numPicker0;
    NumberPicker numPicker1;
    NumberPicker numPicker2;
    NumberPicker numPicker3;
    NumberPicker numPicker4;

int flg =0 ;
int ji2k =0;
int funk =0;
final String kk="test";
    final String kk2="test";

    private TextView pickerTextView;

     String[] figures = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dateText = (TextView) findViewById(R.id.date);
        final Time time = new Time("Asia/Tokyo");
        time.setToNow();
        String date = time.hour + "時" + time.minute + "分";
        dateText.setText(date);


        // pickerTextView = findViewById(R.id.text_view);

        // numPicker0 = findViewById(R.id.numPicker0);
        numPicker1 = findViewById(R.id.numPicker1);
        numPicker2 = findViewById(R.id.numPicker2);
        // numPicker3 = findViewById(R.id.numPicker3);
        //numPicker4 = findViewById(R.id.numPicker4);


        // numPicker0.setMaxValue(2);
        // numPicker0.setMinValue(0);

        numPicker1.setMaxValue(23);
        numPicker1.setMinValue(time.hour);
        numPicker1.setValue(time.hour);

        numPicker2.setMaxValue(59);
        numPicker2.setMinValue(0);
        numPicker2.setValue(time.minute);

        // numPicker3.setMaxValue(9);
        // numPicker3.setMinValue(0);

        //numPicker4.setMaxValue(9);
        //numPicker4.setMinValue(0);
        final TextView text2 = (TextView)findViewById(R.id.textView6);
        final TextView text1 = (TextView)findViewById(R.id.textView4);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
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

                }else if (ji2 >= 0) {
                    if (fun2 <= -1) {
                        fun2 = fun2 + 60;
                        ji2 = ji2 - 1;
                    }
                    String jikan = String.valueOf(ji2);
                    String funsu = String.valueOf(fun2);
                    text1.setText(jikan);
                    text2.setText(funsu);

                    System.out.println(figures[0] + ":" + figures[1] + "が選択されました(｀･ω･´)ゞ");
                    Toast t1 = Toast.makeText(MainActivity.this, figures[0] + ":" + figures[1] + "が選択されました(｀･ω･´)ゞ", Toast.LENGTH_SHORT);
                    System.out.println("空き時間は" + ji2 + ":" + fun2 + "です");
                    Toast t2 = Toast.makeText(MainActivity.this, "空き時間は" + ji2 + ":" + fun2 + "です", Toast.LENGTH_SHORT);
                    t1.show();
                    t2.show();


                    if (ji2k==ji2 && funk==fun2 && flg == 1) {
                        Toast t4 = Toast.makeText(MainActivity.this, "画面遷移します", Toast.LENGTH_SHORT);
                    t4.show();
                    flg = 0;
                    Intent intent = new Intent(MainActivity.this,Sub1.class);
                    intent.putExtra("funsu",funsu);
                    intent.putExtra("jikan",jikan);
                    startActivity(intent);
                    }

                    flg = 1;
                    ji2k = ji2;
                    funk = fun2;
                }
            }
        });

    }
}




