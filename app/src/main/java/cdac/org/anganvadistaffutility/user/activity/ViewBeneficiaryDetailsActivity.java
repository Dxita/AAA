package cdac.org.anganvadistaffutility.user.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

public class ViewBeneficiaryDetailsActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatEditText edt_mobile, edt_aadhar_id, edt_janadhar_id, edt_bhamashah_id;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_beneficiary_details);

        relativeLayout = findViewById(R.id.relativeLayout);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_aadhar_id = findViewById(R.id.edt_aadhar_id);
        edt_janadhar_id = findViewById(R.id.edt_janadhar_id);
        edt_bhamashah_id = findViewById(R.id.edt_bhamashah_id);

        AppCompatButton btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            String mobileNumber = Objects.requireNonNull(edt_mobile.getText()).toString().trim();
            String aadharNumber = Objects.requireNonNull(edt_aadhar_id.getText()).toString().trim();
            String janadharNumber = Objects.requireNonNull(edt_janadhar_id.getText()).toString().trim();
            String bhamashahNumber = Objects.requireNonNull(edt_bhamashah_id.getText()).toString().trim();

            if (mobileNumber.length() == 0 && aadharNumber.length() == 0 && janadharNumber.length() == 0 && bhamashahNumber.length() == 0) {
                AppUtils.showToast(context, getResources().getString(R.string.fill_required_field));
            }
        }
    }
}
