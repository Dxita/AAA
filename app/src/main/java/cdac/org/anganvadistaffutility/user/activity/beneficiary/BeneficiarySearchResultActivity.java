package cdac.org.anganvadistaffutility.user.activity.beneficiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.data.PaymentDetails;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySearchData;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;

public class BeneficiarySearchResultActivity extends BaseActivity implements View.OnClickListener {
    AppCompatButton view_btn;
    AppCompatEditText mobile_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_beneficiary_search_result);

        view_btn = findViewById(R.id.btn_search);
        view_btn.setOnClickListener(this);
        mobile_number=findViewById(R.id.mobile_no);


       // mobile_number.setText(beneficiaryData.getTjmMobileno());

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_search) {
            startActivity(new Intent(context, MotherMappingActivity.class));
            finish();
        }
    }
}