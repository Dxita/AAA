package cdac.org.anganvadistaffutility.activity;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
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
import cdac.org.anganvadistaffutility.callback.OtpReceivedInterface;
import cdac.org.anganvadistaffutility.receiver.SmsBroadcastReceiver;
import cdac.org.anganvadistaffutility.utils.LocaleManager;

public class VerifyOTPActivity extends BaseActivity implements OtpReceivedInterface, View.OnClickListener {

    SmsBroadcastReceiver mSmsBroadcastReceiver;
    private int RESOLVE_HINT = 2;
    EditText inputMobileNumber, inputOtp;
    Button btnGetOtp, btnVerifyOtp;
    RelativeLayout layoutInput, layoutVerify;
    private TextView mobile_number_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

        initViews();

        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOtpListeners(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
        getHintPhoneNumber();

      /*  btnGetOtp.setOnClickListener(v -> {
            // Call server API for requesting OTP and when you got success start
            // SMS Listener for listing auto read message listener
            appPreferences.setOtpGenerated(AppUtils.getRandomNumberString());

            inputOtp.setText(appPreferences.getOtpGenerated());

            //   startSMSListener();
        });*/

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String mobileNumber = (String) bundle.get("mobile_number");
            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                mobile_number_text.setText( "+91" + mobileNumber+" "+ getResources().getString(R.string.sms_code_sent_text));
            }
            else
            {
                mobile_number_text.setText(getString(R.string.sms_code_sent_text)+" "+"+91"+mobileNumber);

            }
        }

        btnVerifyOtp.setOnClickListener(this);
    }

    private void initViews() {
        inputMobileNumber = findViewById(R.id.editTextInputMobile);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        mobile_number_text = findViewById(R.id.mobile_number_text);
    }

    @Override
    public void onOtpReceived(String otp) {
        Toast.makeText(this, "Otp Received " + otp, Toast.LENGTH_LONG).show();
        inputOtp.setText(otp);
    }

    @Override
    public void onOtpTimeout() {

        Toast.makeText(this, "Time out, please resend", Toast.LENGTH_LONG).show();

    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(aVoid -> {
            layoutInput.setVisibility(View.GONE);
            layoutVerify.setVisibility(View.VISIBLE);
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
                    inputMobileNumber.setText(credential.getId());
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVerifyOtp:
                 /*   if (inputOtp.getText().toString().equals(appPreferences.getOtpGenerated())) {
            }*/
                startActivity(new Intent(context, HomeActivity.class));
                finish();
                break;
        }
    }
}
