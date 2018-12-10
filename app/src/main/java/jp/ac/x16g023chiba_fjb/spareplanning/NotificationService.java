package jp.ac.x16g023chiba_fjb.spareplanning;

//import部分からコピーしました
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.format.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    Time time = new Time("Asia/Tokyo");

    final static int NOTIFY_ID = 1;

    //現在時刻用変数
    private Timer mTimer;
    private int nowHour;
    private int nowMinute;
    String date;

    //配列のsizeと比較する変数
    int cnt=0;

    //ここに退出時間を格納してね♪
    //String msg = "9:55";
    List<String> list = new ArrayList();

    public NotificationService() {

    }

    //サービスが初めて実行されたとき一度だけ実行されるメソッド
    public void onCreate() {
        setMessage();
    }

    //サービスを停止するメソッド
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //サービスが実行されるたびに呼び出されるメソッド
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // メインアクティビティからの値を受け取る
        list = intent.getStringArrayListExtra("list");

        return super.onStartCommand(intent, flags, startId);
    }

    //通知を出すメソッド
    void setMessage() {

        for (String a : list){
            System.out.println(a);
        }

        //タイマー処理の作成
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                //現在時刻を取得して整形
                time.setToNow();
                nowHour = time.hour;
                nowMinute = time.minute;
                date = nowHour + ":" + nowMinute;
                String i = list.get(cnt);
                //現在時刻と退出時間の比較
                if(date.equals(list.get(cnt))){

                    //通知内容の構築
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //API level 26 以上
                        NotificationManager manager = (NotificationManager)
                                getSystemService(Context.NOTIFICATION_SERVICE);

                        NotificationChannel channel = new NotificationChannel(
                                // アプリでユニークな ID
                                "channel_1",
                                // ユーザが「設定」アプリで見ることになるチャンネル名
                                getString(R.string.channel_name),
                                // チャンネルの重要度（0 ~ 4）
                                NotificationManager.IMPORTANCE_DEFAULT
                        );

                        // 通知時のライトの色
                        channel.setLightColor(Color.GREEN);
                        // ロック画面で通知を表示するかどうか
                        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
                        // 端末にチャンネルを登録し、「設定」で見れるようにする
                        manager.createNotificationChannel(channel);
                        Notification notification = new Notification.Builder(NotificationService.this)
                                //タイトル
                                .setContentTitle("Spare Planning")
                                //メッセージ
                                .setContentText("予定の時間が迫ってます")
                                //小さなアイコン
                                .setSmallIcon(R.drawable.notification3)
                                .setChannelId("channel_1")
                                //おおきなアイコン
                                .setLargeIcon(largeIcon)
                                //通知を表示
                                .build();

                        manager.notify(NOTIFY_ID, notification);
                    } else {
                        //APIレベル25以下
                        //通知内容の構築
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this);
                        builder.setSmallIcon(R.drawable.notification3);             //アイコンの指定
                        builder.setContentTitle(getString(R.string.app_name));  //タイトルをアプリ名に設定
                        builder.setContentText("予定の時間が迫ってます");      //通知内容

                        //通知を登録
                        NotificationManagerCompat manager = NotificationManagerCompat.from(NotificationService.this);
                        //通知を表示
                        manager.notify(NOTIFY_ID, builder.build());
                    }
                    //比較する退出時間を次の要素へ変える
                    cnt++;
                    //配列内の時間すべてと比較し終わったらサービスを停止させる
                    if(cnt==list.size()){
                        stopSelf();
                    }
                }
            }
        };
        //タイマー開始処理
        mTimer = new Timer();
        mTimer.schedule(timerTask,0,59999); //0秒後から59.9998秒ごとにイベントを発生させる
        //10分おきにずれが修正される
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}