package cdac.org.anganvadistaffutility.admin.igmpy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;

public class IgmpyStatusActivity extends BaseActivity implements View.OnClickListener {
 private CardView verify_status_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_igmpy_status);

        verify_status_card=findViewById(R.id.verify_status_card);
        verify_status_card.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.verify_status_card){
            startActivity(new Intent(context, VerifyStatusActivity.class));
        }
    }
}