package cdac.org.anganvadistaffutility.user.activity.personal_details;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

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


public class
PaymentActivity extends BaseActivity implements TextWatcher {

     AppCompatEditText from_year, to_year;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        from_year = findViewById(R.id.from_year);
        to_year = findViewById(R.id.to_year);
        AppCompatButton generate_slip_button = findViewById(R.id.generate_slip_button);
        relativeLayout = findViewById(R.id.relativeLayout);
        from_year.addTextChangedListener(this);


        from_year.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    to_year.getText().clear();
                }
                return false;
            }
        });
        generate_slip_button.setOnClickListener(view -> {
            String f_year = Objects.requireNonNull(from_year.getText()).toString().trim();
            String t_year = Objects.requireNonNull(to_year.getText()).toString().trim();
            if (f_year.isEmpty()) {
                from_year.requestFocus();
                from_year.setError(getResources().getString(R.string.required_field));
            } else if (f_year.length() < 4) {
                from_year.requestFocus();
                from_year.setError(getResources().getString(R.string.should_have_4_digits));
            } else {
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    getPaymentData(f_year, t_year);
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void getPaymentData(String fromYear, String toYear) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<PaymentDetails> call = apiInterface.paymentDetails(appPreferences.getEmployeeId(), fromYear, toYear);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<PaymentDetails>() {
            @Override
            public void onSuccess(PaymentDetails body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);

                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                  //  AppUtils.showToast(context, body.getMessage());
                    PaymentDetails.Data data = body.getData();
                    List<PaymentDetails.Empdatum> paymentDetails = data.getEmpdata();
                    ArrayList<PaymentDetails.Empdatum> empDatumArrayList = new ArrayList<>(paymentDetails);

                    if (!empDatumArrayList.isEmpty()) {
                        Intent intent = new Intent(context, SalaryDetailsTblActivity.class);
                        intent.putExtra("fromYear", fromYear);
                        intent.putExtra("toYear", toYear);
                        intent.putExtra("salary_data", AppUtils.convertToPut(empDatumArrayList));

                        startActivity(intent);
                    }

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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() == 4) {
            int currentFinancialYear = Integer.parseInt(charSequence.toString());
            if (currentFinancialYear >= AppUtils.currentYear() + 1) {
                AppUtils.showToast(context, getResources().getString(R.string.next_financial_year));
                to_year.setText("");
            } else if (currentFinancialYear < AppUtils.previousYear()) {
                AppUtils.showToast(context, getResources().getString(R.string.previous_financial_year));
                to_year.setText("");
            } else {
                int nextFinancialYear = currentFinancialYear + 1;
                to_year.setText(String.valueOf(nextFinancialYear));
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

}