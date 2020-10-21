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
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView name = findViewById(R.id.name);
        TextView dob = findViewById(R.id.dob);
        TextView category = findViewById(R.id.category);
        TextView father_husband_name = findViewById(R.id.father_husband_name);
        TextView mobile_no = findViewById(R.id.mobile_no);
        TextView awc_id = findViewById(R.id.awc_id);
        TextView awc_name = findViewById(R.id.awc_name);
        TextView awc_address = findViewById(R.id.awc_address);

        EmployeeDetails.Profile profileDetails = (EmployeeDetails.Profile) getIntent().getParcelableExtra("profile_details");
        if (profileDetails != null) {

            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                name.setText(profileDetails.getEmployeeNameHindi() + " (" + profileDetails.getEmployeeNameEnglish() + ")");
            } else {
                name.setText(profileDetails.getEmployeeNameEnglish() + " (" + profileDetails.getEmployeeNameHindi() + ")");
            }
            dob.setText(profileDetails.getDateOfBirth());
            category.setText(profileDetails.getCategory());
            awc_id.setText(profileDetails.getAwcid());
            mobile_no.setText(profileDetails.getMoblieNumber());
            father_husband_name.setText(profileDetails.getHusbandFatherName());
            awc_address.setText(profileDetails.getAwcAddress());
            awc_name.setText(profileDetails.getAwcNameEnglish());

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