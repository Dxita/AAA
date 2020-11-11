package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.public_u.activity.PublicLoginActivity;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;
import cdac.org.anganvadistaffutility.user.activity.VerifyUserActivity;

public class UserTypeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_type);

        relativeLayout = findViewById(R.id.relativeLayout);
        CardView user_type_admin = findViewById(R.id.user_type_admin);
        CardView user_type_employee = findViewById(R.id.user_type_employee);
        CardView user_type_public = findViewById(R.id.user_type_public);

        user_type_admin.setOnClickListener(this);
        user_type_employee.setOnClickListener(this);
        user_type_public.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.user_type_admin) {
            //   startActivity(new Intent(context, ViewAaGanWadiDataActivity.class));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.hasPermissions(context, AppUtils.PHONE_PERMISSIONS)) {
                    fetchAdminPhoneNumber(relativeLayout);
                } else {
                    requestPermissions(AppUtils.PHONE_PERMISSIONS, AppUtils.PHONE_PERMISSION_REQUEST_CODE);
                }
            } else {
                fetchAdminPhoneNumber(relativeLayout);
            }
        } else if (view.getId() == R.id.user_type_employee) {
            if (!appPreferences.getEmployeeId().isEmpty() && appPreferences.isUserLoggedIn()) {
                startActivity(new Intent(this, UserSectionActivity.class));
                finish();
            } else {
                startActivity(new Intent(context, VerifyUserActivity.class));
            }
        } else if (view.getId() == R.id.user_type_public) {
            startActivity(new Intent(context, PublicLoginActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtils.PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchAdminPhoneNumber(relativeLayout);
            }
        }
    }

    @Override
    protected void onDestroy() {

        if (!appPreferences.isUserLoggedIn() && LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.ENGLISH)) {
            LocaleManager.setLanguagePref(context, LocaleManager.HINDI);
        }
        super.onDestroy();
    }
}
