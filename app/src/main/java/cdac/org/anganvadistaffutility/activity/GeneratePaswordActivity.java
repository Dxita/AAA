package cdac.org.anganvadistaffutility.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cdac.org.anganvadistaffutility.R;

public class GeneratePaswordActivity extends BaseActivity implements View.OnClickListener {

    AppCompatButton continue_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pasword);

        continue_button=findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_button:
                startActivity(new Intent(context, HomeActivity.class));
                finish();
                break;
        }

    }
}