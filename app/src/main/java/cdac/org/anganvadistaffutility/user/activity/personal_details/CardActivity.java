package cdac.org.anganvadistaffutility.user.activity.personal_details;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;

public class CardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_card);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        AppCompatEditText pan_card_no = findViewById(R.id.pan_card_no);
        AppCompatEditText bhamashah_no = findViewById(R.id.bhamasha_no);
        AppCompatEditText aadhar_no = findViewById(R.id.aadhar_no);
        AppCompatEditText asha_id = findViewById(R.id.asha_id);
        AppCompatEditText janaadhar_no = findViewById(R.id.janaadhar_no);
        AppCompatEditText janaadhar_self_no = findViewById(R.id.janaadhar_self_no);

        EmployeeDetails.Card cardDetails = getIntent().getParcelableExtra("card_details");
        if (cardDetails != null) {
            pan_card_no.setText(cardDetails.getPanNumber());
            bhamashah_no.setText(cardDetails.getBhamashahNumber());
            aadhar_no.setText(cardDetails.getAadharNumber());
            asha_id.setText(cardDetails.getAshaid());
            janaadhar_no.setText(cardDetails.getJanaadharNumber());
            janaadhar_self_no.setText(cardDetails.getJanaadharSelfNumber());
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
