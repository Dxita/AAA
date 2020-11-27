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

public class IdCardDetailsActivity extends BaseActivity {
AppCompatEditText bpl_card_number,ration_card_number,aadhar_no,janaadhar_no,bhamasha_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_id_card_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        bpl_card_number=findViewById(R.id.bpl_card_number);
        ration_card_number=findViewById(R.id.ration_card_number);
        aadhar_no=findViewById(R.id.aadhar_no);
        janaadhar_no=findViewById(R.id.janaadhar_no);
        bhamasha_no=findViewById(R.id.bhamasha_no);

        BeneficiarySearchData.Data id_cards_details = (BeneficiarySearchData.Data) getIntent().getParcelableExtra("id_cards_details");
        Log.d("data", String.valueOf(id_cards_details));
        if (id_cards_details != null) {
            bpl_card_number.setText(id_cards_details.getTjmBplcardNo());
            ration_card_number.setText(id_cards_details.getTjmRationcardNo());
            aadhar_no.setText(id_cards_details.getTbmAadharNo());
            janaadhar_no.setText(id_cards_details.getTbmJanaadhar());
            bhamasha_no.setText(id_cards_details.getTbmBhamashahId());
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