package cdac.org.anganvadistaffutility;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import cdac.org.anganvadistaffutility.utils.AppSignatureHelper;

public class App extends Application {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override public void onCreate() {
        super.onCreate();
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignatures();
    }
}
