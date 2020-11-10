package cdac.org.anganvadistaffutility.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import cdac.org.anganvadistaffutility.common.preferences.AppPreferences;
import cdac.org.anganvadistaffutility.common.service.UserLogoutService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent("logout");
        i.putExtra("message", "logout");
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);

        AppPreferences appPreferences = new AppPreferences(context);
        SharedPreferences.Editor editor = appPreferences.getAppPreference().edit();
        editor.clear();
        editor.apply();

        Intent stopServiceIntent = new Intent(context, UserLogoutService.class);
        stopServiceIntent.setAction(UserLogoutService.ACTION_STOP_FOREGROUND_SERVICE);
        context.startService(stopServiceIntent);

      //  Log.e("Service", "User Logged Out");

    }
}
