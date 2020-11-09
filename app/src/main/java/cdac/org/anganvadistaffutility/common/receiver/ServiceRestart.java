package cdac.org.anganvadistaffutility.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import cdac.org.anganvadistaffutility.common.service.UserLogoutService;

public class ServiceRestart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

      //  Log.e("Service", "Service Restarted Receiver");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent i = new Intent(context, UserLogoutService.class);
            i.setAction(UserLogoutService.ACTION_START_FOREGROUND_SERVICE);
            context.startForegroundService(i);
        } else {
            Intent i = new Intent(context, UserLogoutService.class);
            i.setAction(UserLogoutService.ACTION_START_FOREGROUND_SERVICE);
            context.startService(new Intent(context, UserLogoutService.class));
        }
    }
}
