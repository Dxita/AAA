package cdac.org.anganvadistaffutility.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import cdac.org.anganvadistaffutility.utils.AppUtils;

public class VerifyOTPActivity extends BaseActivity implements OtpReceivedInterface {

    SmsBroadcastReceiver mSmsBroadcastReceiver;
    private int RESOLVE_HINT = 2;
    EditText inputMobileNumber, inputOtp;
    Button btnGetOtp, btnVerifyOtp;
    RelativeLayout layoutInput, layoutVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        initViews();

        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOtpListeners(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
        getHintPhoneNumber();

        btnGetOtp.setOnClickListener(v -> {
            // Call server API for requesting OTP and when you got success start
            // SMS Listener for listing auto read message listener
            appPreferences.setOtpGenerated(AppUtils.getRandomNumberString());
            layoutInput.setVisibility(View.GONE);
            layoutVerify.setVisibility(View.VISIBLE);
            inputOtp.setText(appPreferences.getOtpGenerated());
            //   startSMSListener();
        });

        btnVerifyOtp.setOnClickListener(view -> {
            if (inputOtp.getText().toString().equals(appPreferences.getOtpGenerated())) {
                startActivity(new Intent(context, HomeActivity.class));
            }
        });
    }

    private void initViews() {
        inputMobileNumber = findViewById(R.id.editTextInputMobile);
        inputOtp = findViewById(R.id.editTextOTP);
        btnGetOtp = findViewById(R.id.buttonGetOTP);
        btnVerifyOtp = findViewById(R.id.buttonVerify);
        layoutInput = findViewById(R.id.getOTPLayout);
        layoutVerify = findViewById(R.id.verifyOTPLayout);


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
        //Result if we want hint number
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
}
