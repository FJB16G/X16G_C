package jp.ac.x16g023chiba_fjb.spareplanning;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.Toast;

public class SplashActivity extends Activity {

    private final int REQUEST_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // タイトルを非表示にします。
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // splash.xmlをViewに指定します。
        setContentView(R.layout.splash);
        // Android 6, API 23以上でパーミッシンの確認
        if(Build.VERSION.SDK_INT >= 23){
            checkPermission();
        }
        else{
            startLocationActivity();
        }
    }

    // 位置情報許可の確認
    public void checkPermission() {
        // 既に許可している
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            startLocationActivity();
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);

        } else {
            Toast toast = Toast.makeText(this,
                    "許可されないとアプリが実行できません", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},
                    REQUEST_PERMISSION);

        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationActivity();

            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this,
                        "これ以上なにもできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    // Intent でLocation
    private void startLocationActivity() {
        LocationManager nlLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(nlLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Handler hdl = new Handler();
            // 500ms遅延させてsplashHandlerを実行します。　
            hdl.postDelayed(new splashHandler(), 1000);
        }else{
            chkGpsService();
        }
    }
    class splashHandler implements Runnable {
        public void run() {
            // スプラッシュ完了後に実行するActivityを指定します。
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            // SplashActivityを終了させます。
            SplashActivity.this.finish();
        }
    }


    // GPSが有効かCheck
// 有効になっていなければ、設定画面の表示確認ダイアログ
    private void chkGpsService() {
        LocationManager nlLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //GPSセンサーが利用可能か？
        if (!nlLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("GPSが有効になっていません。\n有効化しますか？")
                    .setCancelable(false)

                    //GPS設定画面起動用ボタンとイベントの定義
                    .setPositiveButton("GPS設定起動",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    Intent callGPSSettingIntent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
                                }
                            });
            //キャンセルボタン処理
            alertDialogBuilder.setNegativeButton("キャンセル",
                    new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            // 設定画面へ移動するかの問い合わせダイアログを表示
            alert.show();
        }
    }
    public void onRestart() {
        super.onRestart();
        Intent intent = new Intent(getApplication(),SplashActivity.class);
        startActivity(intent);
    }


}