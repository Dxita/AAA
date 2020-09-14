package cdac.org.anganvadistaffutility.activity;

import android.os.Bundle;
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
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;

public class PaymentActivity extends BaseActivity {
   // String[] financial_year = {"2019-20", "2018-19", "2017-18", "2016-17", "2015-16", "2014-15"};
EditText from_year;
TextView to_year;
Button generate_slip_button;


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







        generate_slip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //increment the count

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
}