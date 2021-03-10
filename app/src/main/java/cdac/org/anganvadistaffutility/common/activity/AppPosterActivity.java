package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import cdac.org.anganvadistaffutility.R;

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