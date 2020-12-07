package cdac.org.anganvadistaffutility.admin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;


public class ShowKPIActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_kpi);

        CardView view_users_data = findViewById(R.id.view_users_data);
        CardView view_infra_data = findViewById(R.id.view_infra_data);

        view_users_data.setOnClickListener(this);
        view_infra_data.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_users_data:
                //  startActivity(new Intent(context, UsersGraphActivity.class));
                startActivity(new Intent(context, UsersPieChartActivity.class));
                break;
            case R.id.view_infra_data:
                startActivity(new Intent(context, ViewInfraStructureActivity.class));
                break;
        }
    }
}

