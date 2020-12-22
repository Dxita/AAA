package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.BeneficiaryCriteria;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;
import cdac.org.anganvadistaffutility.user.data.AddNewInfrastructureData;
import retrofit2.Call;

public class AddActivity extends BaseActivity {
    SmartMaterialSpinner<String> sp_beneficiary_criteria;
    private RelativeLayout relativeLayout;
    public static String infra_id, item_nameE, item_nameH;
    AppCompatEditText facility_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add);
        relativeLayout=findViewById(R.id.relativeLayout);
        facility_name=findViewById(R.id.facility);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                infra_id = null;
                item_nameE = null;
                item_nameH = null;
            } else {
                infra_id = extras.getString("infraID");
                item_nameH = extras.getString("infra_nameh");
                item_nameE = extras.getString("infra_namee");
            }
        } else {
            infra_id = (String) savedInstanceState.getSerializable("infraID");
            item_nameH = (String) savedInstanceState.getSerializable("infra_nameh");
            item_nameE = (String) savedInstanceState.getSerializable("infra_namee");
        }


        if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
         facility_name.setText(item_nameH);

        } else {
           facility_name.setText(item_nameE);
        }
        sp_beneficiary_criteria = findViewById(R.id.sp_beneficiary_criteria);
        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getBeneficiaryCriteriaData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getBeneficiaryCriteriaData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<AddNewInfrastructureData> call = apiInterface.addNewInfraList("1", appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AddNewInfrastructureData>() {
            @Override
            public void onSuccess(AddNewInfrastructureData body) {

                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);

                List<String> addNewInfrastructureList = new ArrayList<>();


                for (AddNewInfrastructureData.InfrastructureDatum infrastructureDatum : body.getData().getInfrastructureData()) {
                    addNewInfrastructureList.add(infrastructureDatum.getTidInfraNamee());
                    sp_beneficiary_criteria.setItem(addNewInfrastructureList);

                    sp_beneficiary_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            Toast.makeText(context, addNewInfrastructureList.get(position), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }
}