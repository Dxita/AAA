package cdac.org.anganvadistaffutility.admin.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

public class ViewAaGanWadiDataActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_aaganwadi_data);

        TextView txt_user_type = findViewById(R.id.txt_user_type);
        CardView view_aw_data = findViewById(R.id.view_aaganwadi_data);
        CardView view_payment_data = findViewById(R.id.view_payment_data);

        txt_user_type.setText(getResources().getString(R.string.admin_text).toUpperCase());
        view_aw_data.setOnClickListener(this);
        view_payment_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_aaganwadi_data) {
            startActivity(new Intent(context, ShowKPIActivity.class));
        } else if (view.getId() == R.id.view_payment_data) {
            startActivity(new Intent(context, EmployeePaymentDetailActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
