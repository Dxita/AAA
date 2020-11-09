package cdac.org.anganvadistaffutility.common.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.preferences.AppPreferences;
import cdac.org.anganvadistaffutility.common.receiver.ServiceRestart;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

public class UserLogoutService extends Service {

    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    private static Context context;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    protected int counter = 1;
    private AppPreferences appPreferences;
    private final static int sessionTimeOut = 900;   // 15 minutes

    public UserLogoutService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        appPreferences = new AppPreferences(this);
        //  Log.d(TAG_FOREGROUND_SERVICE, "My foreground service onCreate().");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent != null) {
            String action = intent.getAction();
            if (action != null)
                switch (action) {
                    case ACTION_START_FOREGROUND_SERVICE:
                        startForegroundService();
                        //  Log.e("Logout Service", "startForeground");
                        //   Toast.makeText(getApplicationContext(), "Foreground service is started.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_STOP_FOREGROUND_SERVICE:
                        stopForegroundService();
                        //   Log.e("Logout Service", "stopForeground");
                        // Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                        break;
                }
        }
        return START_STICKY;
    }

    /* Used to build and start foreground service. */
    private void startForegroundService() {

        //  Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("my_service", "My Background Service");
        } else {
            // Create notification default intent.
            Intent intent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            // Create notification builder.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_service");

            // Make notification show big text.
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setBigContentTitle("Logout foreground service.");
            bigTextStyle.bigText("Android foreground service is a android service which can run in foreground always, it can be controlled by user via notification.");
            // Set big text style.
            builder.setStyle(bigTextStyle);

            builder.setWhen(System.currentTimeMillis());
            builder.setSmallIcon(R.mipmap.ic_launcher);
            Bitmap largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
            builder.setLargeIcon(largeIconBitmap);
            // Make the notification max priority.
            builder.setPriority(Notification.PRIORITY_MAX);
            // Make head-up notification.
            builder.setFullScreenIntent(pendingIntent, true);

           /* // Add Play button intent in notification.
            Intent playIntent = new Intent(this, UserLogoutService.class);
            playIntent.setAction(ACTION_PLAY);
            PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);
            NotificationCompat.Action playAction = new NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", pendingPlayIntent);
            builder.addAction(playAction);

            // Add Pause button intent in notification.
            Intent pauseIntent = new Intent(this, UserLogoutService.class);
            pauseIntent.setAction(ACTION_PAUSE);
            PendingIntent pendingPrevIntent = PendingIntent.getService(this, 0, pauseIntent, 0);
            NotificationCompat.Action prevAction = new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", pendingPrevIntent);
            builder.addAction(prevAction);*/

            // Build the notification.
            Notification notification = builder.build();

            // Start foreground service.
            startForeground(1, notification);
        }

        startAlarmManager();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName) {
        Intent resultIntent = new Intent(this, BaseActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("User is logged in currently.Inactivity for 15 min. will automatically log out user.")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentIntent(resultPendingIntent) //intent
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notificationBuilder.build());
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Log.e("Logout Service", "Destroy");


        // stopForegroundService();

        if (appPreferences.isUserLoggedIn() && !AppUtils.isLogoutServiceRunning(this, AppUtils.serviceName)) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, ServiceRestart.class);
            this.sendBroadcast(broadcastIntent);
        }

        /*if (appPreferences.isUserLoggedIn()) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, ServiceRestart.class);
            this.sendBroadcast(broadcastIntent);
        }*/

        /*stopForegroundService();
        if (appPreferences.isUserLoggedIn()) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, ServiceRestart.class);
            this.sendBroadcast(broadcastIntent);
        }*/
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        //    Log.e("Logout Service", "onTaskRemoved");

        //stopForegroundService();

        if (appPreferences.isUserLoggedIn() && !AppUtils.isLogoutServiceRunning(this, AppUtils.serviceName)) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, ServiceRestart.class);
            this.sendBroadcast(broadcastIntent);
        }
    }

    private void startAlarmManager() {

        //  Log.e(TAG_FOREGROUND_SERVICE, "Alarm Started");

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("lo");
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Calendar calendar = Calendar.getInstance();
        //  calendar.setTimeInMillis(System.currentTimeMillis());
        // setRepeating() lets you specify a precise custom interval--in this case,// 15 minutes.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setAndAllowWhileIdle(AlarmManager.RTC, System.currentTimeMillis() +
                    60 * 1000 * 15, alarmIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC, System.currentTimeMillis() +
                    60 * 1000 * 15, alarmIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC, System.currentTimeMillis() +
                    60 * 1000 * 15, alarmIntent);
        }

        //  sendFinishMessage();

       /* alarmMgr.set(AlarmManager.RTC,
                SystemClock.elapsedRealtime() +
                        60 * 1000 * 15, alarmIntent);*/
    }

    private void stopAlarmManager() {

        //  Log.e(TAG_FOREGROUND_SERVICE, "Alarm Stopped");

        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);
        }
    }

  /*  private Timer timer;
    protected TimerTask timerTask;

    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {

              //  Log.e("Logout Service", "" + counter);

                if (counter == sessionTimeOut) {
                    SharedPreferences.Editor editor = appPreferences.getAppPreference().edit();
                    editor.clear();
                    editor.apply();
                    sendFinishMessage();
                    stopForegroundService();
                } else {
                    counter += 1;
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000); //
    }*/

    public static void sendFinishMessage() {
        Intent intent = new Intent("logout");
        intent.putExtra("message", "logout");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

   /* public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }*/

    private void stopForegroundService() {
        //  Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");

        stopAlarmManager();

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            sendFinishMessage();

            //  Log.e("Service", "Log out broadcast sent");
        }
    }

  /*  private boolean isServiceRunning(String serviceName) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : l) {
            if (runningServiceInfo.service.getClassName().equals(serviceName)) {
                if (runningServiceInfo.foreground) {
                    //service run in foreground
                    serviceRunning = true;
                }
            }
        }
        return serviceRunning;
    }*/
}
