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

public class BasicInformationActivity extends BaseActivity {
AppCompatEditText district,project, sector,pcts_anm_id,pcts_asha_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_basic_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        district=findViewById(R.id.district);
        project=findViewById(R.id.project);
        sector=findViewById(R.id.sector);
        pcts_anm_id=findViewById(R.id.pcts_anm_id);
        pcts_asha_id=findViewById(R.id.pcts_asha_id);


        BeneficiarySearchData.Data basic_info = getIntent().getParcelableExtra("basic_info");
        Log.d("data", String.valueOf(basic_info));
        if (basic_info != null) {
            district.setText(basic_info.getTjmDistId());
            project.setText(basic_info.getTjmProjectId());
            sector.setText(basic_info.getTjmSectorId());
            pcts_anm_id.setText(basic_info.getTjmAnmId());
            pcts_asha_id.setText(basic_info.getTjmAshaId());
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