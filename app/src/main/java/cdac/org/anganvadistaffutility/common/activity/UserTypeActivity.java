package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.ViewAaGanWadiDataActivity;
import cdac.org.anganvadistaffutility.common.preferences.AppPreferences;
import cdac.org.anganvadistaffutility.public_u.activity.activity.PublicLoginActivity;
import cdac.org.anganvadistaffutility.user.activity.HomeActivity;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;
import cdac.org.anganvadistaffutility.user.activity.VerifyUserActivity;

public class UserTypeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_type);

        CardView user_type_admin = findViewById(R.id.user_type_admin);
        CardView user_type_employee = findViewById(R.id.user_type_employee);
        CardView user_type_public = findViewById(R.id.user_type_public);

        user_type_admin.setOnClickListener(this);
        user_type_employee.setOnClickListener(this);
        user_type_public.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_type_admin:
                startActivity(new Intent(context, ViewAaGanWadiDataActivity.class));
                break;
            case R.id.user_type_employee:

                if (AppPreferences.getKey(context, "loggedin").equals("true")) {
                    startActivity(new Intent(this, UserSectionActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(context, VerifyUserActivity.class));

                }

                break;
            case R.id.user_type_public:
                startActivity(new Intent(context, PublicLoginActivity.class));
                break;

        }
    }
}
