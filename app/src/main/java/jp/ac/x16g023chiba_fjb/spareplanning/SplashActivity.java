package jp.ac.x16g023chiba_fjb.spareplanning;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

<<<<<<< HEAD
public class SplashActivity extends AppCompatActivity{
    private final int REQUEST_PERMISSION = 1000;
    private LocationManager nlLocationManager;

        @Override
        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // タイトルを非表示にします。
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            // splash.xmlをViewに指定します。
            setContentView(R.layout.splash);


            // Android 6, API 23以上でパーミッシンの確認
            if (Build.VERSION.SDK_INT >= 23) {
                checkPermission();
            } else {
                startLocationActivity();
            }
        }

            // 位置情報許可の確認
            public void checkPermission () {
                // 既に許可している
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    startLocationActivity();
                }
                // 拒否していた場合
                else {
                    requestLocationPermission();
                }
            }

            // 許可を求める
            private void requestLocationPermission () {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this,
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
        Handler hdl = new Handler();
        // 500ms遅延させてsplashHandlerを実行します。　
        hdl.postDelayed(new splashHandler(), 1000);
=======
public class SplashActivity extends Activity {
    private Permission mPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // タイトルを非表示にします。
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // splash.xmlをViewに指定します。
        setContentView(R.layout.splash);

        //Android6.0以降用パーミッション設定
        mPermission = new Permission();
        mPermission.setOnResultListener(new Permission.ResultListener() {
            @Override
            public void onResult() {
                //パーミッション設定完了後の初期化処理を入れる
                Handler hdl = new Handler();
                // 500ms遅延させてsplashHandlerを実行します。　
                hdl.postDelayed(new splashHandler(), 1000);
            }
        });
        mPermission.requestPermissions(this);

>>>>>>> 3c24a443a8f3fc8ac7b6e9ee30bfc46d07149a37
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this,SplashActivity.class);
        startActivity(intent);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mPermission.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}