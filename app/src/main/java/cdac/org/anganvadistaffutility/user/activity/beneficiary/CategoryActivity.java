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

public class CategoryActivity extends BaseActivity {
    RadioButton rb_domicileyes, rb_domicileno, rb_sahariya, rb_kathodi, rb_none, rb_ghumantu_yes, rb_ghumantu_no, bpl_yes, bpl_no, nfsa_yes, nfsa_no, sc, st, general;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Profile Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        rb_domicileyes = findViewById(R.id.rb_domicileyes);
        rb_domicileno = findViewById(R.id.rb_domicileno);
        rb_sahariya = findViewById(R.id.sahariya);
        rb_kathodi = findViewById(R.id.kathodi);
        rb_none = findViewById(R.id.none);
        rb_ghumantu_yes = findViewById(R.id.ghumantu_yes);
        rb_ghumantu_no = findViewById(R.id.ghumantu_no);
        bpl_yes = findViewById(R.id.bpl_yes);
        bpl_no = findViewById(R.id.bpl_no);
        nfsa_no = findViewById(R.id.nfsa_no);
        nfsa_yes = findViewById(R.id.nfsa_yes);
        sc = findViewById(R.id.sc);
        st = findViewById(R.id.st);
        general = findViewById(R.id.general);

        rb_domicileyes.setClickable(false);
        rb_domicileno.setClickable(false);
        rb_sahariya.setClickable(false);
        rb_kathodi.setClickable(false);
        rb_none.setClickable(false);
        rb_ghumantu_yes.setClickable(false);
        rb_ghumantu_no.setClickable(false);
        bpl_yes.setClickable(false);
        bpl_no.setClickable(false);
        nfsa_no.setClickable(false);
        nfsa_no.setClickable(false);


        BeneficiarySearchData.Data category = getIntent().getParcelableExtra("category");
        Log.d("data", String.valueOf(category));
        if (category != null) {
            // beneficiary_type.setText();

            if (category.getTjmDomicileStatus().equalsIgnoreCase("1")) {
                rb_domicileyes.setChecked(true);
            } else {
                rb_domicileno.setChecked(true);
            }

            if (category.getTjmCastCategory().equalsIgnoreCase("1")) {
                rb_sahariya.setChecked(true);
            } else if (category.getTjmCastCategory().equalsIgnoreCase("2")) {
                rb_kathodi.setChecked(true);
            } else {
                rb_none.setChecked(true);
            }

            if (category.getTjmGhumantu().equalsIgnoreCase("1")) {
                rb_ghumantu_yes.setChecked(true);
            } else {
                rb_ghumantu_no.setChecked(true);
            }

            if (category.getTjmBpl().equalsIgnoreCase("1")) {
                bpl_yes.setChecked(true);
            } else {
                bpl_no.setChecked(true);
            }

            if (category.getTjmNfsaEligibility().equalsIgnoreCase("1")) {
                nfsa_yes.setChecked(true);
            } else {
                nfsa_no.setChecked(true);
            }


            if (category.getTjmCastCategory().equalsIgnoreCase("1")) {
                sc.setChecked(true);
            } else if (category.getTjmCastCategory().equalsIgnoreCase("2")) {
                sc.setChecked(true);
            } else {
                general.setChecked(true);
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