package cdac.org.anganvadistaffutility.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import cdac.org.anganvadistaffutility.common.service.UserLogoutService;

public class ServiceRestart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, UserLogoutService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            i.setAction(UserLogoutService.ACTION_START_FOREGROUND_SERVICE);
            context.startForegroundService(i);
        } else {
            i.setAction(UserLogoutService.ACTION_START_FOREGROUND_SERVICE);
            context.startService(i);
        }
    }
}
