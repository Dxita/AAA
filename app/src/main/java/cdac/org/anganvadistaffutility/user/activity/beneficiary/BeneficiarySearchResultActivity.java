package cdac.org.anganvadistaffutility.user.activity.beneficiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;

public class BeneficiarySearchResultActivity extends BaseActivity implements View.OnClickListener {
    AppCompatButton view_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_beneficiary_search_result);

        view_btn = findViewById(R.id.btn_search);
        view_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_search) {
            startActivity(new Intent(context, MotherMappingActivity.class));
            finish();
        }
    }
}