package cdac.org.anganvadistaffutility.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import org.spongycastle.jcajce.provider.symmetric.ARC4;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.ViewAaGanWadiDataActivity;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.SelectLanguageActivity;
import cdac.org.anganvadistaffutility.common.activity.SplashActivity;
import cdac.org.anganvadistaffutility.public_u.activity.PublicLoginActivity;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;

public class AppPosterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_app_poster);

        new Handler().postDelayed(() -> {

                startActivity(new Intent(this, SplashActivity.class));


            finish();
        }, 3000);
    }
}