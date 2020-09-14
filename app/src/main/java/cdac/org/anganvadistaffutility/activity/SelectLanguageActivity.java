package cdac.org.anganvadistaffutility.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.utils.LocaleManager;

public class SelectLanguageActivity extends BaseActivity implements View.OnClickListener {

    private TextView hindi_text, english_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_language);

        english_text = findViewById(R.id.english_text);
        hindi_text = findViewById(R.id.hindi_text);
        Button continue_button = findViewById(R.id.continue_button);

        if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
            english_text.setBackground(getResources().getDrawable(R.drawable.bg_lan_unselected));
            hindi_text.setBackground(getResources().getDrawable(R.drawable.bg_lan_selected));
        } else {
            english_text.setBackground(getResources().getDrawable(R.drawable.bg_lan_selected));
            hindi_text.setBackground(getResources().getDrawable(R.drawable.bg_lan_unselected));
        }

        english_text.setOnClickListener(this);
        hindi_text.setOnClickListener(this);
        continue_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_button:
                startActivity(new Intent(context, UserTypeActivity.class));
                finish();
                break;
            case R.id.english_text:
                english_text.setBackground(getResources().getDrawable(R.drawable.bg_lan_selected));
                hindi_text.setBackground(getResources().getDrawable(R.drawable.bg_lan_unselected));
                setAppLocale((AppCompatActivity) context, LocaleManager.ENGLISH);
                break;
            case R.id.hindi_text:
                english_text.setBackground(getResources().getDrawable(R.drawable.bg_lan_unselected));
                hindi_text.setBackground(getResources().getDrawable(R.drawable.bg_lan_selected));
                setAppLocale((AppCompatActivity) context, LocaleManager.HINDI);
                break;
        }
    }
}