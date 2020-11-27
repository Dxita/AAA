package cdac.org.anganvadistaffutility.user.activity.beneficiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RadioButton;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySearchData;

public class MotherDetailsActivity extends BaseActivity {
    AppCompatEditText beneficiary_type, mother_name, age_of_mother, height_cm, mobile_no, live_child, husband_father_name, lmc_date, registration_date, delivery_date;
    RadioButton rb_husband, rb_father;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mother_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mother_name = findViewById(R.id.mother_name);
        beneficiary_type = findViewById(R.id.beneficiary_type);
        age_of_mother = findViewById(R.id.age_of_mother);
        mobile_no = findViewById(R.id.mobile_no);
        height_cm = findViewById(R.id.height_cm);
        live_child = findViewById(R.id.live_child);
        husband_father_name = findViewById(R.id.husband_father_name);
        lmc_date = findViewById(R.id.lmc_date);
        registration_date = findViewById(R.id.registration_date);
        delivery_date = findViewById(R.id.delivery_date);
        rb_father = findViewById(R.id.rb_father);
        rb_husband = findViewById(R.id.rb_husband);

        rb_father.setClickable(false);
        rb_husband.setClickable(false);
        BeneficiarySearchData.Data mother_details = (BeneficiarySearchData.Data) getIntent().getParcelableExtra("mother_details");
        Log.d("data", String.valueOf(mother_details));
        if (mother_details != null) {
            // beneficiary_type.setText();
            mother_name.setText(mother_details.getTjmName());
            age_of_mother.setText(mother_details.getTjmAge());
            height_cm.setText(mother_details.getTjmHeight());
            mobile_no.setText(mother_details.getTjmMobileno());
            live_child.setText(mother_details.getTjmLiveChild());
            husband_father_name.setText(mother_details.getTjmHusbname());
            lmc_date.setText(mother_details.getTjmLmcDate());
            registration_date.setText(mother_details.getTjmRegistrationDate());
            delivery_date.setText(mother_details.getTjmDeliveryDate());

            if (mother_details.getTjmIsHusband().equalsIgnoreCase("1")) {
                rb_husband.setChecked(true);
            } else {
                rb_father.setChecked(true);
            }

            if (mother_details.getTjmMotherType().equalsIgnoreCase("1"))
            {
                beneficiary_type.setText(getResources().getString(R.string.pregnant_woman));
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