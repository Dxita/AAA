package cdac.org.anganvadistaffutility.user.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.ag.floatingactionmenu.OptionsFabLayout;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.UserTypeActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;

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
        fabWithOptions = findViewById(R.id.fab_l);
        fabWithOptions.setMiniFabsColors(R.color.sky, R.color.sky);

        fabWithOptions.setMainFabOnClickListener(view -> {
            if (fabWithOptions.isOptionsMenuOpened()) {
                fabWithOptions.closeOptionsMenu();
            }
        });

        fabWithOptions.setMiniFabSelectedListener(fabItem -> {
            switch (fabItem.getItemId()) {
                case R.id.fab_change_language:
                    if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                        changeAppLocale((AppCompatActivity) context, LocaleManager.ENGLISH);
                    } else {
                        changeAppLocale((AppCompatActivity) context, LocaleManager.HINDI);
                    }
                    break;
                case R.id.fab_logout:
                    SharedPreferences.Editor editor = appPreferences.getAppPreference().edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(context,
                            UserTypeActivity.class);
                    startActivity(intent);
                    finishAffinity();

                    AppUtils.showToast(context, getResources().getString(R.string.logout_success));
                default:
                    break;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.infra_data_card:
                startActivity(new Intent(context, InfraCategoriesActivity.class));
                break;
            case R.id.personal_info_card:
                startActivity(new Intent(context, HomeActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserSectionActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.app_logo);
        builder.setMessage(getString(R.string.exit_text))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> finishAffinity())
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}