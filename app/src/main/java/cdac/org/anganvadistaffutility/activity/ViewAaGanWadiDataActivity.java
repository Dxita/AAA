package cdac.org.anganvadistaffutility.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;

public class ViewAaGanWadiDataActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_aaganwadi_data);

        CardView view_aw_data = findViewById(R.id.view_aaganwadi_data);
        CardView view_payment_data = findViewById(R.id.view_payment_data);

        view_aw_data.setOnClickListener(this);
        view_payment_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_aaganwadi_data:
                startActivity(new Intent(context, ShowKPIActivity.class));
                break;
            case R.id.view_payment_data:
                startActivity(new Intent(context, EmployeePaymentDetailActivity.class));
                break;
        }
    }
}
