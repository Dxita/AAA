package cdac.org.anganvadistaffutility.user.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.concurrent.TimeUnit;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.GeneratePasswordActivity;
import cdac.org.anganvadistaffutility.common.callback.OtpReceivedInterface;
import cdac.org.anganvadistaffutility.common.receiver.SmsBroadcastReceiver;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;


public class VerifyOTPActivity extends BaseActivity implements OtpReceivedInterface, View.OnClickListener {

    private static final int TIME_LIMIT = 1000 * 60 * 10; // 10 min
    private static final int TIME_INTERVAL = 1000; // 1 sec

    private int RESOLVE_HINT = 2;
    private EditText et_input_otp;
    private TextView mobile_number_text;
    private TextView txt_time_remaining;
    private LinearLayout ll_resend_otp, ll_resend_timer;
    private RelativeLayout relativeLayout;

    private CountDownTimer countDownTimer;
    private String mobileNumber = "";

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
            mobileNumber = bundle.getString("mobile_number");
            mobile_number_text.setText(getResources().getString(R.string.sms_code_sent_text, mobileNumber));
        }

        SmsBroadcastReceiver mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
        getHintPhoneNumber();
        // startSMSListener();
    }

    private void initViews() {
        relativeLayout = findViewById(R.id.relativeLayout);
        et_input_otp = findViewById(R.id.et_input_otp);
        Button btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        mobile_number_text = findViewById(R.id.mobile_number_text);
        ll_resend_otp = findViewById(R.id.ll_resend_otp);
        ll_resend_timer = findViewById(R.id.ll_resend_timer);
        TextView txt_resend_otp = findViewById(R.id.txt_resend_otp);
        txt_time_remaining = findViewById(R.id.txt_time_remaining);

        btnVerifyOtp.setOnClickListener(this);
        txt_resend_otp.setOnClickListener(this);
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

                    startActivity(new Intent(context, GeneratePasswordActivity.class).putExtra("mobile_number", mobileNumber));
                    finish();
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.enter_valid_otp));
                }
            }
        } else if (view.getId() == R.id.txt_resend_otp) {
            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                sendOtpToServer(relativeLayout, mobileNumber, AppUtils.getRandomNumberString());
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        }
    }

    private void startOtpTimer() {
        countDownTimer = new CountDownTimer(TIME_LIMIT, TIME_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                txt_time_remaining.setText(getResources().getString(R.string.time_remaining, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60));
            }

            @Override
            public void onFinish() {
                ll_resend_timer.setVisibility(View.GONE);
                ll_resend_otp.setVisibility(View.VISIBLE);
            }
        };
        countDownTimer.start();
    }

    private void stopOtpTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startOtpTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopOtpTimer();
    }
}
