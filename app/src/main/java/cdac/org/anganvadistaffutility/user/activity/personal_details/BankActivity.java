package cdac.org.anganvadistaffutility.user.activity.personal_details;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;

public class BankActivity extends BaseActivity {
    TextView bank_ac_no, ifsc_code, branch_code, branch_name, branch_address, aadhar_acc_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bank);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        bank_ac_no = findViewById(R.id.bank_ac_no);
        ifsc_code = findViewById(R.id.ifsc_code);
        branch_code = findViewById(R.id.branch_code);
        branch_name = findViewById(R.id.branch_name);
        branch_address = findViewById(R.id.branch_address);
        aadhar_acc_no = findViewById(R.id.aadhar_acc_no);

        EmployeeDetails.Bank bankDetails = getIntent().getParcelableExtra("bank_details");
        if (bankDetails != null) {
            bank_ac_no.setText(bankDetails.getBankAccountNumber());
            ifsc_code.setText(bankDetails.getIfscCode());
            branch_code.setText(bankDetails.getBranchCode());
            branch_name.setText(bankDetails.getBankName());
            branch_address.setText(bankDetails.getBranchAddress());
            aadhar_acc_no.setText(bankDetails.getAadharAttachedBankAccountNumber());
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