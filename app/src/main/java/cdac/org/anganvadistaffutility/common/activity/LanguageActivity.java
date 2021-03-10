package cdac.org.anganvadistaffutility.common.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatButton;

import cdac.org.anganvadistaffutility.R;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    AppCompatButton continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_language);

        continue_button = findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.continue_button) {
            startActivity(new Intent(context, UserTypeActivity.class));
        }
    }
}