package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

public class NoInternetActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        findViewById(R.id.btn_try_again).setOnClickListener(view -> {
            if (AppUtils.isNetworkConnected(context)) {
                startActivity(new Intent(context, SplashActivity.class));
                finish();
            }
        });
    }
}
