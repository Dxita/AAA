package cdac.org.anganvadistaffutility;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Timer;
import java.util.TimerTask;

import cdac.org.anganvadistaffutility.common.activity.LogoutListener;
import cdac.org.anganvadistaffutility.common.utils.AppSignatureHelper;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;

public class App extends Application {
    /*private LogoutListener listener;
    private Timer timer;
    private static final long INACTIVE_TIMEOUT = 180000; // 3 min*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignatures();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

   /* public void startUserSession () {
        cancelTimer ();

        timer = new Timer ();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                listener.onSessionLogout ();

            }
        }, INACTIVE_TIMEOUT);

    }

    private void cancelTimer () {
        if (timer !=null) timer.cancel();
    }

    public void registerSessionListener(LogoutListener listener){
        this.listener = listener;
    }

    public void onUserInteracted () {
        startUserSession();
    }*/

}
