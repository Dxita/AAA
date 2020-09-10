package cdac.org.anganvadistaffutility.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.utils.AppUtils;

public class ProfileActivity extends BaseActivity  {

    TextView name, dob,mobile_no,awc_id, category,father_husband_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        name=findViewById(R.id.name);
        dob=findViewById(R.id.dob);
        category=findViewById(R.id.category);
        father_husband_name=findViewById(R.id.father_husband_name);
        mobile_no=findViewById(R.id.mobile_no);
        awc_id=findViewById(R.id.awc_id);

        EmployeeDetails.Profile profileDetails = (EmployeeDetails.Profile) getIntent().getParcelableExtra("profile_details");
        if (profileDetails != null) {
            name.setText(profileDetails.getEmployeeNameEnglish());
            dob.setText(profileDetails.getDateOfBirth());
            category.setText(profileDetails.getCategory());
            awc_id.setText(profileDetails.getAwcid());
            mobile_no.setText(profileDetails.getMoblieNumber());
            father_husband_name.setText(profileDetails.getHusbandFatherName());

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