package cdac.org.anganvadistaffutility.common.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;


public class SelectLanguageActivity extends BaseActivity implements View.OnClickListener {

    private TextView hindi_text, english_text;

    RelativeLayout select_language_activity, continue_button_layout;
    Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_language);

        english_text = findViewById(R.id.english_text);
        hindi_text = findViewById(R.id.hindi_text);
        continue_button = findViewById(R.id.continue_button);
        select_language_activity = findViewById(R.id.select_language_layout);
        continue_button_layout = findViewById(R.id.button_language_activity);



        if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
            english_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_unselected));
            hindi_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_selected));
        } else {
            english_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_selected));
            hindi_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_unselected));
        }

        english_text.setOnClickListener(this);
        hindi_text.setOnClickListener(this);
        continue_button.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.continue_button:
                startActivity(new Intent(context, UserTypeActivity.class));
                finish();
                break;
            case R.id.english_text:
                english_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_selected));
                hindi_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_unselected));

               setAppLocale((AppCompatActivity) context, LocaleManager.ENGLISH);
                //    appPreferences.setLanguagePref(true);
                break;
            case R.id.hindi_text:
                english_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_unselected));
                hindi_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_selected));
                setAppLocale((AppCompatActivity) context, LocaleManager.HINDI);
                //  appPreferences.setLanguagePref(true);
                break;
        }
    }
}
