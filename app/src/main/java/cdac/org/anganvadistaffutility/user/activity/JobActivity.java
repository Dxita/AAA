package cdac.org.anganvadistaffutility.user.activity;

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

public class JobActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_job);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView employment_status = findViewById(R.id.employment_status);
        TextView doj = findViewById(R.id.doj);
        TextView payslab_amount = findViewById(R.id.payslab_amount);
        TextView qualification = findViewById(R.id.qualification);
        TextView designation = findViewById(R.id.designation);

        EmployeeDetails.Job jobDetails = getIntent().getParcelableExtra("job_details");
        if (jobDetails != null) {
            employment_status.setText(jobDetails.getEmployeeStatus());

            if (jobDetails.getEmployeeStatus().equals("0")) {
                employment_status.setText(getString(R.string.retired));
            } else {
                employment_status.setText(getString(R.string.employed));
            }

            doj.setText(jobDetails.getDateOfJoining());
            payslab_amount.setText(jobDetails.getPayslabAmount());
            qualification.setText(jobDetails.getEducationalQualification());
            designation.setText(jobDetails.getDesignationNameEnglish());
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