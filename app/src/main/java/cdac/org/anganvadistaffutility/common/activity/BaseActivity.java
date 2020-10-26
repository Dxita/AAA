package cdac.org.anganvadistaffutility.common.activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.ViewAaGanWadiDataActivity;
import cdac.org.anganvadistaffutility.admin.data.VerifyAdmin;
import cdac.org.anganvadistaffutility.common.LanguageActivity;
import cdac.org.anganvadistaffutility.common.preferences.AppPreferences;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.activity.VerifyOTPActivity;
import cdac.org.anganvadistaffutility.user.activity.VerifyUserActivity;
import cdac.org.anganvadistaffutility.user.data.VerifyOTPDetails;
import retrofit2.Call;

import static android.content.pm.PackageManager.GET_META_DATA;


public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    private final int RESOLVE_HINT = 2;

    private RelativeLayout relativeLay;
    public AppPreferences appPreferences;
    public Context context;
    public ApiInterface apiInterface;
    public List<String> adminNumberList;
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        appPreferences = new AppPreferences(context);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        resetTitles();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    protected void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setAppLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(new Intent(context, LanguageActivity.class));
        finishAffinity();
    }

    public void changeAppLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        mContext.finish();
        startActivity(intent);
    }

    protected void sendOtpToServer(RelativeLayout relativeLayout, String mobileNumber, String otp) {
        apiInterface = ApiUtils.getApiInterface(ApiUtils.SEND_OTP_TO_SERVER_BASE_URL);
        Call<VerifyOTPDetails> call = apiInterface.sendOtpToServer(mobileNumber, otp);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyOTPDetails>() {
            @Override
            public void onSuccess(VerifyOTPDetails body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                if (body.getResult().getStatus().getStatusCode() == 0) {
                    AppUtils.showToast(context, body.getData().getResponseMessage());

                    if (context instanceof VerifyOTPActivity) {
                        finish();
                    }
                    appPreferences.saveGeneratedOtp(body.getData().getOtp());
                    startActivity(new Intent(context, VerifyOTPActivity.class)
                            .putExtra("mobile_number", body.getData().getMobile()));

                    if (context instanceof VerifyUserActivity) {
                        finish();
                    }
                } else {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    AppUtils.showToast(context, body.getData().getResponseMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
            }
        }));
    }

    public void fetchAdminPhoneNumber(RelativeLayout relativeLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = (SubscriptionManager)
                    getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            adminNumberList = new ArrayList<>();
            assert subscriptionManager != null;
            List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            if (subscriptionInfoList != null && subscriptionInfoList.size() > 0) {
                for (SubscriptionInfo info : subscriptionInfoList) {
                    String number = info.getNumber();

                    /*if (number == null) {
                        number = "";
                    } else {
                        number = number.replaceAll("[\\D]", "").replaceFirst("91-", "").replaceFirst("91", "");
                    }
                    adminNumberList.add(number);*/

                    if (number != null && !number.isEmpty()) {
                        number = number.replaceAll("[\\D]", "").replaceFirst("91-", "").replaceFirst("91", "");
                        adminNumberList.add(number);
                    }
                }
            }
        } else {
            if (telephonyManager != null) {
                String number = telephonyManager.getLine1Number();

                /*if (number == null) {
                    number = "";
                } else {
                    number = number.replaceAll("[\\D]", "").replaceFirst("91-", "").replaceFirst("91", "");
                }
                adminNumberList.add(number);*/

                if (number != null && !number.isEmpty()) {
                    number = number.replaceAll("[\\D]", "").replaceFirst("91-", "").replaceFirst("91", "");
                    adminNumberList.add(number);
                }
            }
        }

        if (adminNumberList.isEmpty()) {
            relativeLay = relativeLayout;
            getHintPhoneNumber();
        } else {
            callVerifyAdmin(relativeLayout);
        }
    }

    private void getHintPhoneNumber() {
        HintRequest hintRequest =
                new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .setEmailAddressIdentifierSupported(false)
                        .build();

       /* HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(new CredentialPickerConfig.Builder()
                        .setShowCancelButton(true)
                        .build())
                .setPhoneNumberIdentifierSupported(true)
                .setEmailAddressIdentifierSupported(false)
                .setAccountTypes(IdentityProviders.GOOGLE)
                .build();*/

        PendingIntent mIntent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void callVerifyAdmin(RelativeLayout relativeLayout) {
        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            verifyAdmin(relativeLayout);
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }

    public void verifyAdmin(RelativeLayout relativeLayout) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.VERIFY_ADMIN_URL);
        Call<VerifyAdmin> call = apiInterface.verifyAdmin(android.text.TextUtils.join(",", adminNumberList));
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyAdmin>() {
            @Override
            public void onSuccess(VerifyAdmin body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, body.getMessage());

                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    startActivity(new Intent(context, ViewAaGanWadiDataActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    if (credential != null) {
                        String number = credential.getId();
                        number = number.replaceAll("[\\D]", "").replaceFirst("91-", "").replaceFirst("91", "");
                        adminNumberList.add(number);
                        callVerifyAdmin(relativeLay);
                    }
                    // credential.getId();  <-- will need to process phone number string
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //   AppUtils.showToast(context, "landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //  AppUtils.showToast(context, "portrait");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
