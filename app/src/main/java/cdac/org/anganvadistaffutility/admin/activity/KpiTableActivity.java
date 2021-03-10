package cdac.org.anganvadistaffutility.admin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;

public class KpiTableActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    private LinearLayout tbl_layout;
    private AdminUserData.Data empData;
    private int totalRegisteredEmployees = 0;
    private int totalUnRegisteredEmployees = 0;
    private TextView txt_payment_month, txt_amount, view_registered_emp, view_unregistered_emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_kpi_table);

        relativeLayout = findViewById(R.id.relativeLayout);
        txt_amount = findViewById(R.id.registered_count);
        txt_payment_month = findViewById(R.id.unregistered_count);
        view_registered_emp = findViewById(R.id.view_registered_emp);
        view_unregistered_emp = findViewById(R.id.view_unregistered_emp);
        tbl_layout = findViewById(R.id.tbl_layout);

        view_registered_emp.setOnClickListener(this);
        view_unregistered_emp.setOnClickListener(this);


        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAdminUserData();
        }

    }

    private void getAdminUserData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<AdminUserData> call = apiInterface.getAdminUsersData("parm");
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AdminUserData>() {
            @Override
            public void onSuccess(AdminUserData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //   AppUtils.showToast(context, body.getMessage());
                empData = body.getData();
                if (empData.getEmpdata().size() > 0) {
                    if (tbl_layout.getVisibility() == View.GONE) {
                        tbl_layout.setVisibility(View.VISIBLE);
                    }
                    setUserData(empData);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    @SuppressLint("SetTextI18n")
    private void setUserData(AdminUserData.Data empData) {

        for (AdminUserData.Empdatum empDatum : empData.getEmpdata()) {
            totalRegisteredEmployees = totalRegisteredEmployees + Integer.parseInt(empDatum.getRegistered());
            totalUnRegisteredEmployees = totalUnRegisteredEmployees + Integer.parseInt(empDatum.getUnregistered());
        }

        txt_amount.setText(Integer.toString(totalRegisteredEmployees));
        txt_payment_month.setText(Integer.toString(totalUnRegisteredEmployees));
    }


    @Override
    public void onClick(View v) {
        List<AdminUserData.Empdatum> empDatumList = empData.getEmpdata();
        ArrayList<AdminUserData.Empdatum> empDatumArrayList = new ArrayList<>(empDatumList);
        if (v.getId() == R.id.view_registered_emp) {
            startActivity(new Intent(context, DistrictWiseTableActivity.class).putExtra("user_type", "registered_user"));

        }
        if (v.getId() == R.id.view_unregistered_emp) {
            startActivity(new Intent(context, DistrictWiseTableActivity.class).putExtra("user_type", "unregistered_user"));
        }
    }
}