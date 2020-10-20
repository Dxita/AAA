package cdac.org.anganvadistaffutility.user.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;

public class UserSectionActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_section);
        CardView personal_info = findViewById(R.id.personal_info_card);
        CardView infra_data = findViewById(R.id.infra_data_card);

        personal_info.setOnClickListener(this);
        infra_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.infra_data_card:
                startActivity(new Intent(context, InfraCategoriesActivity.class));
                finish();
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