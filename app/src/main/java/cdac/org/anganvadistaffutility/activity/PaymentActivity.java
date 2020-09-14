package cdac.org.anganvadistaffutility.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.EmployeeDetails;
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
int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Payment Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        from_year=findViewById(R.id.from_year);
        to_year=findViewById(R.id.to_year);
        generate_slip_button=findViewById(R.id.generate_slip_button);

        f_year=from_year.getText().toString();
        t_year=to_year.getText().toString();


        generate_slip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String version = from_year.getText().toString();
                String newVersion = "20"+((Integer.parseInt(version.substring(1,version.length()))+1));
                to_year.setText(newVersion);


                //Toast.makeText(context, ""+newVersion, Toast.LENGTH_SHORT).show();
                //increment the count
                //getPaymentData();

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
        Call<PaymentDetails> call = apiInterface.paymentDetails(AppUtils.empID,from_year.getText().toString(),to_year.getText().toString());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<PaymentDetails>() {
                      @Override
                             public void onSuccess(PaymentDetails body) {

                                AppUtils.showToast(getApplicationContext(),body.getMessage());
                             }

                        @Override
                        public void onFailure(Throwable t) {
                            //
                        }
        }));
    }
}