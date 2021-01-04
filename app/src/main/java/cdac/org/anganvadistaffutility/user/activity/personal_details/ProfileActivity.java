package cdac.org.anganvadistaffutility.user.activity.personal_details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;

public class ProfileActivity extends BaseActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        AppCompatEditText name = findViewById(R.id.name);
        AppCompatEditText dob = findViewById(R.id.dob);
        AppCompatEditText category = findViewById(R.id.category);
        AppCompatEditText father_husband_name = findViewById(R.id.father_husband_name);
        AppCompatEditText mobile_no = findViewById(R.id.mobile_no);
        AppCompatEditText awc_id = findViewById(R.id.awc_id);
        AppCompatEditText awc_name = findViewById(R.id.awc_name);
        AppCompatEditText awc_address = findViewById(R.id.awc_address);

        EmployeeDetails.Profile profileDetails = getIntent().getParcelableExtra("profile_details");
        Log.d("check", String.valueOf(profileDetails));
        if (profileDetails != null) {

            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                name.setText(profileDetails.getEmployeeNameHindi());
                awc_name.setText(profileDetails.getAwcNameHindi());
                dob.setText(profileDetails.getDateOfBirth());
                category.setText(profileDetails.getCategory());
                awc_id.setText(profileDetails.getAwcid());
                mobile_no.setText(profileDetails.getMoblieNumber());
                //    father_husband_name.setText(profileDetails.getHusbandFatherName());
                father_husband_name.setText(profileDetails.getHusbandFatherName().replaceAll("Name", ""));
                awc_address.setText(profileDetails.getAwcAddress());

            } else {
                name.setText(profileDetails.getEmployeeNameEnglish());
                dob.setText(profileDetails.getDateOfBirth());
                category.setText(profileDetails.getCategory());
                awc_id.setText(profileDetails.getAwcid());
                mobile_no.setText(profileDetails.getMoblieNumber());
                //    father_husband_name.setText(profileDetails.getHusbandFatherName());
                father_husband_name.setText(profileDetails.getHusbandFatherName().replaceAll("Name", ""));
                awc_address.setText(profileDetails.getAwcAddress());
                awc_name.setText(profileDetails.getAwcNameEnglish());
            }


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