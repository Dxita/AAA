package cdac.org.anganvadistaffutility.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.EmployeeDetails;

public class JobActivity extends AppCompatActivity {
    TextView employment_status, doj,designation,payslab_amount,qualification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Job Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        employment_status=findViewById(R.id.employment_status);
        doj=findViewById(R.id.doj);
        payslab_amount=findViewById(R.id.payslab_amount);
        qualification=findViewById(R.id.qualification);
        designation=findViewById(R.id.designation);

        EmployeeDetails.Job jobDetails= getIntent().getParcelableExtra("job_details");
        if (jobDetails != null) {
            employment_status.setText(jobDetails.getEmployeeStatus());
            doj.setText(jobDetails.getDateOfBirth());
            payslab_amount.setText(jobDetails.getPayslabAmount());
            qualification.setText(jobDetails.getEducationalQualification());
            designation.setText(jobDetails.getDesignationNameEnglish());

            //  AppUtils.showToast(context, "" + profileDetails.getEmployeeNameEnglish() + "\n\n" + profileDetails.getEmployeeNameHindi());
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return true;

    }

}