package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.VerifyAdminActivity;
import cdac.org.anganvadistaffutility.public_u.activity.activity.PublicLoginActivity;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (appPreferences.isSetLanguagePref()) {
                if (appPreferences.istypeuser()) {
                    startActivity(new Intent(this, UserSectionActivity.class));
                    finish();
                } else if (appPreferences.istypeadmin()) {
                    startActivity(new Intent(this, VerifyAdminActivity.class));
                    finish();
                } else if (appPreferences.istypeadmin()) {
                    startActivity(new Intent(this, PublicLoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(this, UserTypeActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(context, SelectLanguageActivity.class));
            }

            /*   startActivity(new Intent(SplashActivity.this, SelectLanguageActivity.class));*/
            finish();
        }, 2000);
    }
}
