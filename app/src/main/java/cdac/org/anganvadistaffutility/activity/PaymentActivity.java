package cdac.org.anganvadistaffutility.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.PaymentDetails;
import cdac.org.anganvadistaffutility.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import retrofit2.Call;

public class PaymentActivity extends BaseActivity {

    // String[] financial_year = {"2019-20", "2018-19", "2017-18", "2016-17", "2015-16", "2014-15"};
    EditText from_year;
    TextView to_year;
    Button generate_slip_button;
    String f_year, t_year;
    int nextyear = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        from_year = findViewById(R.id.from_year);
        to_year = findViewById(R.id.to_year);
        generate_slip_button = findViewById(R.id.generate_slip_button);
        f_year = from_year.getText().toString();


        from_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (from_year.length() == 4) {
                    String version = from_year.getText().toString();
                    String newVersion = "20" + (Integer.parseInt(version.substring(1)) + 1);
                    to_year.setText(newVersion);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        generate_slip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPaymentData();

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

    private void getPaymentData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PAYMENT_BASE_URL);
        Call<PaymentDetails> call = apiInterface.paymentDetails(AppUtils.empID, from_year.getText().toString(), to_year.getText().toString());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<PaymentDetails>() {
            @Override
            public void onSuccess(PaymentDetails body) {

                AppUtils.showToast(context, body.getMessage());

                PaymentDetails.Data data = body.getData();
                List<PaymentDetails.Empdatum> paymentDetails = data.getEmpdata();
                ArrayList<PaymentDetails.Empdatum> empdatumArrayList = new ArrayList<>(paymentDetails);

                Intent intent = new Intent(context, SalaryDetailsTblActivity.class);
                intent.putExtra("salary_data", AppUtils.convertToPut(empdatumArrayList));
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                //
            }
        }));
    }
}