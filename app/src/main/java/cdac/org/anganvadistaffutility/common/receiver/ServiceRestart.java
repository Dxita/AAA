package cdac.org.anganvadistaffutility.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import cdac.org.anganvadistaffutility.common.service.LogoutService;

public class ServiceRestart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, LogoutService.class));
        } else {
            context.startService(new Intent(context, LogoutService.class));
        }
    }
}
