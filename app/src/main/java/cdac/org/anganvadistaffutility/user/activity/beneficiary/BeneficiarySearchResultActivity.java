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
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySearchData;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;

public class BeneficiarySearchResultActivity extends BaseActivity implements View.OnClickListener {
    AppCompatButton view_btn;
    AppCompatEditText mother_name, mobile_number, father_husband_name, pcts_anm_id, pcts_asha_id, aadhar_no, janaadhar_no, bhamasha_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_beneficiary_search_result);

        view_btn = findViewById(R.id.btn_search);
        view_btn.setOnClickListener(this);
        mother_name = findViewById(R.id.mother_name_id);
        mobile_number = findViewById(R.id.mobile_no);
        father_husband_name = findViewById(R.id.father_husband_name);
        pcts_anm_id = findViewById(R.id.pcts_anm_id);
        pcts_asha_id = findViewById(R.id.pcts_asha_id);
        aadhar_no = findViewById(R.id.aadhar_no);
        janaadhar_no = findViewById(R.id.janaadhar_no);
        bhamasha_no = findViewById(R.id.bhamasha_no);

        BeneficiarySearchData.Data data = (BeneficiarySearchData.Data) getIntent().getParcelableExtra("benefeciary_data");
        Log.d("data", String.valueOf(data));

        if (data != null) {
            mother_name.setText(data.getTjmName());
            mobile_number.setText(data.getTjmMobileno());
            father_husband_name.setText(data.getTjmHusbname());
            pcts_asha_id.setText(data.getTjmAshaId());
            pcts_anm_id.setText(data.getTjmAnmId());
            aadhar_no.setText(data.getTbmAadharNo());
            janaadhar_no.setText(data.getTbmJanaadhar());
            bhamasha_no.setText(data.getTbmBhamashahId());
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_search) {
            startActivity(new Intent(context, MotherMappingActivity.class));
            finish();
        }
    }
}