package cdac.org.anganvadistaffutility.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.SalaryDetailsTblActivity;
import cdac.org.anganvadistaffutility.common.data.PaymentDetails;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;

public class EmployeePaymentDetailActivity extends BaseActivity implements TextWatcher, View.OnClickListener {

    private AppCompatEditText edt_employee_id, et_from_year, et_to_year;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_employee_payment_detail);

        edt_employee_id = findViewById(R.id.edt_employee_id);
        et_from_year = findViewById(R.id.et_from_year);
        et_to_year = findViewById(R.id.et_to_year);
        relativeLayout = findViewById(R.id.relativeLayout);
        AppCompatButton btn_get_employee_payment_details = findViewById(R.id.btn_get_employee_payment_details);

        et_from_year.addTextChangedListener(this);
        btn_get_employee_payment_details.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() == 4) {
            int currentFinancialYear = Integer.parseInt(charSequence.toString());
            int nextFinancialYear = currentFinancialYear + 1;
            et_to_year.setText(String.valueOf(nextFinancialYear));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_get_employee_payment_details) {
            if (AppUtils.isNetworkConnected(context)) {
                String employeeID = Objects.requireNonNull(edt_employee_id.getText()).toString().trim();
                String fromYear = Objects.requireNonNull(et_from_year.getText()).toString().trim();
                String toYear = Objects.requireNonNull(et_to_year.getText()).toString().trim();

                if (employeeID.length() == 0 || fromYear.length() == 0) {
                    AppUtils.showToast(context, getResources().getString(R.string.fill_required_fields));
                } else {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    getPaymentData(employeeID, fromYear, toYear);
                }
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        }
    }

    private void getPaymentData(String employeeID, String fromYear, String toYear) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PAYMENT_BASE_URL);
        Call<PaymentDetails> call = apiInterface.paymentDetails(employeeID, fromYear, toYear);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<PaymentDetails>() {
            @Override
            public void onSuccess(PaymentDetails body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, body.getMessage());

                PaymentDetails.Data data = body.getData();
                List<PaymentDetails.Empdatum> paymentDetails = data.getEmpdata();
                ArrayList<PaymentDetails.Empdatum> empDatumArrayList = new ArrayList<>(paymentDetails);

                if (!empDatumArrayList.isEmpty()) {
                    Intent intent = new Intent(context, SalaryDetailsTblActivity.class);
                    intent.putExtra("fromYear", fromYear);
                    intent.putExtra("toYear", toYear);
                    intent.putExtra("salary_data", AppUtils.convertToPut(empDatumArrayList));
                    startActivity(intent);
                } else {
                    AppUtils.showToast(context, body.getMessage());
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

