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

public class CardActivity extends AppCompatActivity {
    TextView pan_card_no, bhamasha_no,aadhar_no,asha_id, janaadhar_no,janaadhar_self_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Card Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        pan_card_no=findViewById(R.id.pan_card_no);
        bhamasha_no=findViewById(R.id.bhamasha_no);
        aadhar_no=findViewById(R.id.aadhar_no);
        asha_id=findViewById(R.id.asha_id);
        janaadhar_no=findViewById(R.id.janaadhar_no);
        janaadhar_self_no=findViewById(R.id.janaadhar_self_no);


        EmployeeDetails.Card cardDetails = (EmployeeDetails.Card) getIntent().getParcelableExtra("card_details");
        if (cardDetails != null) {
            pan_card_no.setText(cardDetails.getPanNumber());
            bhamasha_no.setText(cardDetails.getBhamashahNumber());
            aadhar_no.setText(cardDetails.getAadharNumber());
            asha_id.setText(cardDetails.getAshaid());
            janaadhar_no.setText((Integer) cardDetails.getJanaadharNumber());
            janaadhar_self_no.setText((Integer) cardDetails.getJanaadharSelfNumber());

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
