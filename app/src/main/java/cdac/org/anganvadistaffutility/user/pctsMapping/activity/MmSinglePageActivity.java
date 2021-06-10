package cdac.org.anganvadistaffutility.user.pctsMapping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.BeneficiaryCriteria;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.pctsMapping.data.SinglePageList;
import retrofit2.Call;

public class MmSinglePageActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    private static List<BeneficiaryCriteria.Beneficiary> leaveTypeItems;
    SmartMaterialSpinner<String> sp_beneficiary_criteria;
    private List<SinglePageList.IgmpyMotherDatum> datumList;
    private String Code, str_mother_name_id, str_pcts_id, str_pcts_asha_id, str_aadhar_no, str_mobile_no, str_icds_code;
    private AppCompatEditText mother_name_id, pcts_id, pcts_asha_id, aadhar_no, mobile_no, icds_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mm_single_page);

        relativeLayout = findViewById(R.id.relativeLayout);

        mother_name_id = findViewById(R.id.mother_name_id);
        pcts_id = findViewById(R.id.pcts_id);
        pcts_asha_id = findViewById(R.id.pcts_asha_id);
        aadhar_no = findViewById(R.id.aadhar_no);
        mobile_no = findViewById(R.id.mobile_no);
        icds_code = findViewById(R.id.icds_code);

        sp_beneficiary_criteria = findViewById(R.id.beneficiary_type);
        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getBeneficiaryCriteriaData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

        AppCompatButton search_button = findViewById(R.id.search);
        search_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search) {
            str_mother_name_id = Objects.requireNonNull(mother_name_id.getText()).toString().trim();
            str_pcts_id = Objects.requireNonNull(pcts_id.getText()).toString().trim();
            str_pcts_asha_id = Objects.requireNonNull(pcts_asha_id.getText()).toString().trim();
            str_aadhar_no = Objects.requireNonNull(aadhar_no.getText()).toString().trim();
            str_mobile_no = Objects.requireNonNull(mobile_no.getText()).toString().trim();
            str_icds_code = Objects.requireNonNull(icds_code.getText()).toString().trim();

            if (str_mother_name_id.length() == 0 && str_pcts_id.length() == 0 && str_pcts_asha_id.length() == 0 && str_aadhar_no.length() == 0 && str_mobile_no.length() == 0
                    && str_icds_code.length() == 0) {
                AppUtils.showToast(context, getResources().getString(R.string.fill_required_field));
            } else {
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    getSinglePageList();
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }
            }
            /*startActivity(new Intent(context, SinglePageListActivity.class));*/
        }

    }

    private void getSinglePageList() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<SinglePageList> call = apiInterface.getSinglePageList(Code, str_mother_name_id, str_pcts_id, str_pcts_asha_id,
                str_aadhar_no, str_mobile_no, str_icds_code);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<SinglePageList>() {
            @Override
            public void onSuccess(SinglePageList body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    //     AppUtils.showToast(context, body.getMessage());

                    datumList = body.getData().getData().getIgmpyMotherData();
                    Toast.makeText(context, "" + body.getMessage(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(context, SinglePageListActivity.class)
                            .putExtra("single_page_data_list", AppUtils.convertSinglePageListToPut(datumList)));
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, context.getResources().getString(R.string.error_in_fetch_data));
            }
        }));

    }

    private void getBeneficiaryCriteriaData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<BeneficiaryCriteria> call = apiInterface.getBeneficiaryCriteria();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<BeneficiaryCriteria>() {
            @Override
            public void onSuccess(BeneficiaryCriteria body) {
                //AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> beneficiaryCriteriaList = new ArrayList<>();

                leaveTypeItems = body.getData().getBeneficiary();
                for (int i = 0; i < leaveTypeItems.size(); i++) {
                    String leaveType;

                    //  Code = leaveTypeItems.get(i).getTbmBeneficiaryId(); // I want to show this when Selected
                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = leaveTypeItems.get(i).getTbmBeneficiaryNamee();
                    } else {
                        leaveType = leaveTypeItems.get(i).getTbmBeneficiaryNameh();
                    }

                    beneficiaryCriteriaList.add(leaveType);
                }


                sp_beneficiary_criteria.setItem(beneficiaryCriteriaList);

                sp_beneficiary_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        Code = leaveTypeItems.get(position).getTbmBeneficiaryId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                //initBeneficiaryCriteriaSpinner(beneficiaryCriteriaList);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }
}