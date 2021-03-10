package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.ViewAaGanWadiDataActivity;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;

public class SplashActivity extends BaseActivity {
    private ImageView logo;
    private static final int splashTimeOut = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        logo = (ImageView) findViewById(R.id.logo1);

        new Handler().postDelayed(() -> {
            if (appPreferences.istypeuser()) {
                startActivity(new Intent(this, UserSectionActivity.class));
            } else if (appPreferences.istypeadmin()) {
                startActivity(new Intent(this, ViewAaGanWadiDataActivity.class));
            } else {
                startActivity(new Intent(this, SelectLanguageActivity.class));
            }

            finish();
        }, splashTimeOut);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.anim);
        logo.startAnimation(myanim);

    }

}
