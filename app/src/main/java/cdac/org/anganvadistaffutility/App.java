package cdac.org.anganvadistaffutility;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import androidx.annotation.RequiresApi;

import cdac.org.anganvadistaffutility.common.utils.AppSignatureHelper;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;

public class App extends Application {
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
}
