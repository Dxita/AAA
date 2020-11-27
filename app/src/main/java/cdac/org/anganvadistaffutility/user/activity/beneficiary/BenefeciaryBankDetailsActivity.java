package cdac.org.anganvadistaffutility.user.activity.beneficiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySearchData;

public class BenefeciaryBankDetailsActivity extends BaseActivity {

    AppCompatEditText ifsc_code,bank_ac_no,account_name,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_benefeciary_bank_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ifsc_code=findViewById(R.id.ifsc_code);
        bank_ac_no=findViewById(R.id.bank_ac_no);
        account_name=findViewById(R.id.account_name);
        address=findViewById(R.id.address);

        BeneficiarySearchData.Data bank_details = (BeneficiarySearchData.Data) getIntent().getParcelableExtra("bank_details");
        Log.d("data", String.valueOf(bank_details));
        if (bank_details != null) {
            ifsc_code.setText(bank_details.getTjmIfscCode());
            bank_ac_no.setText(bank_details.getTjmAccountno());
            account_name.setText(bank_details.getTjmAccountName());
            address.setText(bank_details.getTjmAddress());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}