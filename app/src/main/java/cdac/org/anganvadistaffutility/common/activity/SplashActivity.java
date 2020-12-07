package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.ViewAaGanWadiDataActivity;
import cdac.org.anganvadistaffutility.public_u.activity.PublicLoginActivity;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (appPreferences.istypeuser()) {
                startActivity(new Intent(this, UserSectionActivity.class));
            } else if (appPreferences.istypeadmin()) {
                startActivity(new Intent(this, ViewAaGanWadiDataActivity.class));
            } else if (appPreferences.istypeadmin()) {
                startActivity(new Intent(this, PublicLoginActivity.class));
            } else {
                startActivity(new Intent(this, SelectLanguageActivity.class));
            }

            finish();
        }, 2000);



    }

}
