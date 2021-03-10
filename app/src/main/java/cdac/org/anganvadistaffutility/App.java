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
    public static App myAutoLogoutApp;


    private LogoutListener listener;
    private Timer timer;
    private static final long INACTIVE_TIMEOUT = 600000;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignatures();


        myAutoLogoutApp = this;
       /* ApplockManager.getInstance().enableDefaultAppLockIfAvailable(this);
        ApplockManager.getInstance().startWaitThread(myAutoLogoutApp);*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
        //MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

   /* public void touch() {
        ApplockManager.getInstance().updateTouch();
    }

    public void setStopTrue() {
        ApplockManager.getInstance().setStopTrue();
    }

    public void setStopFalse() {
        ApplockManager.getInstance().setStopFalse();
        ApplockManager.getInstance().startWaitThread(App.myAutoLogoutApp);
    }
*/

    public void startUserSession() {
        cancelTimer();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                listener.onSessionLogout();

            }
        }, INACTIVE_TIMEOUT);

    }

    private void cancelTimer() {
        if (timer != null) timer.cancel();
    }

    public void registerSessionListener(LogoutListener listener) {
        this.listener = listener;
    }

    public void onUserInteracted() {
        startUserSession();
    }
}
