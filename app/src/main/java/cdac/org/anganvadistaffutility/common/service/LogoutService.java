package cdac.org.anganvadistaffutility.common.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Timer;
import java.util.TimerTask;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.preferences.AppPreferences;
import cdac.org.anganvadistaffutility.common.receiver.ServiceRestart;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

public class LogoutService extends Service {

    protected int counter = 1;
    private AppPreferences appPreferences;
    private final static int sessionTimeOut = 180;   // 15 minutes
    private static final int ID_SERVICE = 101;


    @Override
    public void onCreate() {
        super.onCreate();

        appPreferences = new AppPreferences(this);

        // Create the Foreground Service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MIN)
                //   .setContentTitle("App is running count::" + counter++)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build();

        startForeground(ID_SERVICE, notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager) {
        String channelId = "com.rajposhan";
        String channelName = "Log out service";

        int importance = NotificationManager.IMPORTANCE_MIN;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        // channel.setDescription("App is running count::" + counter++);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager.createNotificationChannel(channel);

       /* NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);*/
        return channelId;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        startTimer();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopTimerTask();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        stopTimerTask();
        if (appPreferences.isUserLoggedIn()) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, ServiceRestart.class);
            this.sendBroadcast(broadcastIntent);
        }
    }

    private Timer timer;
    protected TimerTask timerTask;

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {

                //   Log.e("Logout Service", "" + counter);

                if (counter == sessionTimeOut) {
                    SharedPreferences.Editor editor = appPreferences.getAppPreference().edit();
                    editor.clear();
                    editor.apply();
                    sendFinishMessage();
                    stopTimerTask();
                } else {
                    counter += 1;
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000); //
    }

    private void sendFinishMessage() {
        Intent intent = new Intent("logout");
        intent.putExtra("message", "logout");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        stopForeground(true);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
