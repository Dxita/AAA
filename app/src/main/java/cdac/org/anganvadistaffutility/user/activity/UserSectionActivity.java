package cdac.org.anganvadistaffutility.user.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.ag.floatingactionmenu.OptionsFabLayout;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.activity.infrastructure.AvailableInfraDetailsActivity;

public class UserSectionActivity extends BaseActivity implements View.OnClickListener {

    private OptionsFabLayout fabWithOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_section);

        CardView personal_info = findViewById(R.id.personal_info_card);
        CardView infra_data = findViewById(R.id.infra_data_card);
        initFabActionMenu();

        personal_info.setOnClickListener(this);
        infra_data.setOnClickListener(this);
    }

    private void initFabActionMenu() {
        fabWithOptions = findViewById(R.id.fab_menu);
        fabWithOptions.setMiniFabsColors(R.color.sky, R.color.sky);

        fabWithOptions.setMainFabOnClickListener(view -> {
            if (fabWithOptions.isOptionsMenuOpened()) {
                fabWithOptions.closeOptionsMenu();
            }
        });

        fabWithOptions.setMiniFabSelectedListener(fabItem -> {
            if (fabItem.getItemId() == R.id.fab_change_language) {
                if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                    changeAppLocale((AppCompatActivity) context, LocaleManager.ENGLISH);
                } else {
                    changeAppLocale((AppCompatActivity) context, LocaleManager.HINDI);
                }
            } else if (fabItem.getItemId() == R.id.fab_logout) {
                logout();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.infra_data_card) {
            startActivity(new Intent(context, AvailableInfraDetailsActivity.class));
        } else if (view.getId() == R.id.personal_info_card) {
            startActivity(new Intent(context, HomeActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserSectionActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.app_logo);
        builder.setMessage(getString(R.string.exit_text))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> logout())
                .setNegativeButton(getString(R.string.no),
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void logout() {
        AppUtils.showToast(context, getResources().getString(R.string.logout_success));

        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
        }

    }
}