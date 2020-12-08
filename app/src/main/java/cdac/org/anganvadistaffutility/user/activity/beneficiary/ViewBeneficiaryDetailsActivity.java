package cdac.org.anganvadistaffutility.user.activity.beneficiary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.BeneficiaryCriteria;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.SalaryDetailsTblActivity;
import cdac.org.anganvadistaffutility.common.data.PaymentDetails;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.activity.personal_details.ProfileActivity;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySearchData;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;
import retrofit2.Call;

public class ViewBeneficiaryDetailsActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatEditText edt_mobile, edt_aadhar_id, edt_janadhar_id, edt_bhamashah_id;
    private RelativeLayout relativeLayout;
    String mobileNumber, aadharNumber, janadharNumber, bhamashahNumber;

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

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getBeneficiaryCriteriaData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
        AppCompatButton btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
    }

    private void getBeneficiaryCriteriaData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.GET_BENEFICIARY_CRITERIA);
        Call<BeneficiaryCriteria> call = apiInterface.getBeneficiaryCriteria();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<BeneficiaryCriteria>() {
            @Override
            public void onSuccess(BeneficiaryCriteria body) {
                //AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> beneficiaryCriteriaList = new ArrayList<>();
                for (BeneficiaryCriteria.Beneficiary beneficiary : body.getData().getBeneficiary()) {
                    beneficiaryCriteriaList.add(beneficiary.getTbmBeneficiaryNamee());
                }
                initBeneficiaryCriteriaSpinner(beneficiaryCriteriaList);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
              //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void initBeneficiaryCriteriaSpinner(List<String> beneficiaryCriteriaList) {
        SmartMaterialSpinner<String> sp_beneficiary_criteria = findViewById(R.id.sp_beneficiary_criteria);
        sp_beneficiary_criteria.setItem(beneficiaryCriteriaList);
        sp_beneficiary_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(context, beneficiaryCriteriaList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            mobileNumber = Objects.requireNonNull(edt_mobile.getText()).toString().trim();
            aadharNumber = Objects.requireNonNull(edt_aadhar_id.getText()).toString().trim();
            janadharNumber = Objects.requireNonNull(edt_janadhar_id.getText()).toString().trim();
            bhamashahNumber = Objects.requireNonNull(edt_bhamashah_id.getText()).toString().trim();

            if (mobileNumber.length() == 0 && aadharNumber.length() == 0 && janadharNumber.length() == 0 && bhamashahNumber.length() == 0) {
                AppUtils.showToast(context, getResources().getString(R.string.fill_required_field));
            } else {
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    getBeneficiarySearchData();
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }
            }
        }
    }

    private void getBeneficiarySearchData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<BeneficiarySearchData> call = apiInterface.getBeneficiarSearchData("1", aadharNumber, mobileNumber, janadharNumber, bhamashahNumber);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<BeneficiarySearchData>() {
            @Override
            public void onSuccess(BeneficiarySearchData body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    AppUtils.showToast(context, body.getMessage());

                    BeneficiarySearchData.Data data = body.getData();
                    appPreferences.setMobileNumber(body.getData().getTjmMobileno());
                    appPreferences.setAadharno(body.getData().getTbmAadharNo());
                    appPreferences.setJanaadharno(body.getData().getTbmJanaadhar());
                    appPreferences.setBhamashano(body.getData().getTbmBhamashahId());

                    startActivity(new Intent(context, BeneficiarySearchResultActivity.class).putExtra("benefeciary_data", data));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, context.getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }
}
