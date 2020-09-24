package cdac.org.anganvadistaffutility.activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import cdac.org.anganvadistaffutility.R;

public class EmployeePaymentDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_employee_payment_detail);
    }
}

