package cdac.org.anganvadistaffutility.user.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.GeneratePasswordActivity;
import cdac.org.anganvadistaffutility.common.callback.OtpReceivedInterface;
import cdac.org.anganvadistaffutility.common.receiver.SmsBroadcastReceiver;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;


public class VerifyOTPActivity extends BaseActivity implements OtpReceivedInterface, View.OnClickListener {

    private int RESOLVE_HINT = 2;
    private EditText et_input_otp;
    private Button btnVerifyOtp;
    private TextView mobile_number_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

        initViews();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String mobileNumber = bundle.getString("mobile_number");
            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                mobile_number_text.setText(getResources().getString(R.string.sms_code_sent_text, mobileNumber));
            } else {
                mobile_number_text.setText(getString(R.string.sms_code_sent_text, mobileNumber));
            }
        }

        SmsBroadcastReceiver mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
        getHintPhoneNumber();
        // startSMSListener();

        btnVerifyOtp.setOnClickListener(this);
    }

    private void initViews() {
        et_input_otp = findViewById(R.id.et_input_otp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        mobile_number_text = findViewById(R.id.mobile_number_text);
    }

    @Override
    public void onOtpReceived(String otp) {
        Toast.makeText(this, "Otp Received " + otp, Toast.LENGTH_LONG).show();
        et_input_otp.setText(otp);
    }

    @Override
    public void onOtpTimeout() {
        Toast.makeText(this, "Time out, please resend", Toast.LENGTH_LONG).show();
    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(aVoid -> {
            Toast.makeText(VerifyOTPActivity.this, "SMS Retriever starts", Toast.LENGTH_LONG).show();
        });
        mTask.addOnFailureListener(e -> Toast.makeText(VerifyOTPActivity.this, "Error", Toast.LENGTH_LONG).show());
    }

    public void getHintPhoneNumber() {
        HintRequest hintRequest =
                new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .build();

        PendingIntent mIntent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    // credential.getId();  <-- will need to process phone number string
                    assert credential != null;
                    et_input_otp.setText(credential.getId());
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnVerifyOtp) {
            if (et_input_otp.getText().toString().isEmpty()) {
                et_input_otp.requestFocus();
                et_input_otp.setError(getResources().getString(R.string.required_field));
            } else {
                if (et_input_otp.getText().toString().equalsIgnoreCase(appPreferences.getSavedOtp())) {
                    AppUtils.showToast(context, getResources().getString(R.string.otp_verified));

                    startActivity(new Intent(context, GeneratePasswordActivity.class));
                    finish();
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.enter_valid_otp));
                }
            }
        }
    }
}
