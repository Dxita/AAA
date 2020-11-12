package cdac.org.anganvadistaffutility.common.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.AccountPicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.ViewAaGanWadiDataActivity;
import cdac.org.anganvadistaffutility.admin.data.VerifyAdmin;
import cdac.org.anganvadistaffutility.common.preferences.AppPreferences;
import cdac.org.anganvadistaffutility.common.receiver.ServiceRestart;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.service.UserLogoutService;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.activity.VerifyOTPActivity;
import cdac.org.anganvadistaffutility.user.activity.VerifyUserActivity;
import cdac.org.anganvadistaffutility.user.data.VerifyOTPDetails;
import retrofit2.Call;

import static android.content.pm.PackageManager.GET_META_DATA;


public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    private final int PHONE_RESOLVE_HINT = 2;
    private final int EMAIL_RESOLVE_HINT = 3;

    private RelativeLayout relativeLay;
    public AppPreferences appPreferences;
    public Context context;
    public ApiInterface apiInterface;
    protected List<String> adminNumberList;

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String message = intent.getStringExtra("message");
            if (message.equalsIgnoreCase("logout")) {
                finishAffinity();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        appPreferences = new AppPreferences(context);
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
        startActivity(new Intent(context, SelectLanguageActivity.class));
        finishAffinity();
    }

    public void setDefaultAppLocale(AppCompatActivity mContext) {
        LocaleManager.setLocale(this);
        Intent intent = mContext.getIntent();
        mContext.finish();
        startActivity(intent);
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
        adminNumberList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = (SubscriptionManager)
                    getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            assert subscriptionManager != null;
            List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            if (subscriptionInfoList != null && subscriptionInfoList.size() > 0) {
                for (SubscriptionInfo info : subscriptionInfoList) {
                    String number = info.getNumber();
                    if (number != null && !number.isEmpty()) {
                        number = number.replaceAll("[\\D]", "").replaceFirst("91-", "").replaceFirst("91", "");
                        adminNumberList.add(number);
                    }
                }
            }
        }

        adminNumberList.add("9784544208");
        adminNumberList.add("7014259658");

        if (adminNumberList.isEmpty() || adminNumberList.size() == 1) {
            if (!adminNumberList.isEmpty()) {
                adminNumberList.clear();
            }
            relativeLay = relativeLayout;
            getHintPhoneNumber();
        } else {
            callVerifyAdmin(relativeLayout, "");
        }
    }

    private void getHintPhoneNumber() {
        HintRequest hintRequest =
                new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .build();
        PendingIntent mIntent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), PHONE_RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void getHintEmail() {
        Intent intent =
                AccountPicker.newChooseAccountIntent(
                        new AccountPicker.AccountChooserOptions.Builder().setAlwaysShowAccountPicker(true)
                                .setAllowableAccountsTypes(Collections.singletonList("com.google"))
                                .build());
        startActivityForResult(intent, EMAIL_RESOLVE_HINT);
    }

    private void callVerifyAdmin(RelativeLayout relativeLayout, String email) {
        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            verifyAdmin(relativeLayout, email);
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }

    public void verifyAdmin(RelativeLayout relativeLayout, String email) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.VERIFY_ADMIN_URL);
        Call<VerifyAdmin> call = apiInterface.verifyAdmin(android.text.TextUtils.join(",", adminNumberList), email);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyAdmin>() {
            @Override
            public void onSuccess(VerifyAdmin body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, body.getMessage());

                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    appPreferences.setUserLoggedIn(true);
                    appPreferences.settypeadmin(true);
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
        if (requestCode == PHONE_RESOLVE_HINT) {
            int NO_HINTS_AVAILABLE = 1002;
            int SELECT_NONE = 1001;
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    if (credential != null) {
                        String number = credential.getId();
                        number = number.replaceAll("[\\D]", "").replaceFirst("91-", "").replaceFirst("91", "");
                        adminNumberList.add(number);
                        callVerifyAdmin(relativeLay, "");
                    }
                }
            } else if (resultCode == NO_HINTS_AVAILABLE || resultCode == SELECT_NONE) {
                getHintEmail();
            }
        } else if (requestCode == EMAIL_RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (!adminNumberList.isEmpty()) {
                        adminNumberList.clear();
                    }
                    callVerifyAdmin(relativeLay, accountName);
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       /* if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            AppUtils.showToast(context, "landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AppUtils.showToast(context, "portrait");
        }*/
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        if (appPreferences.isUserLoggedIn()) {
            if (AppUtils.isLogoutServiceRunning(context, AppUtils.serviceName)) {
                Intent intent = new Intent(context, UserLogoutService.class);
                intent.setAction(UserLogoutService.ACTION_STOP_FOREGROUND_SERVICE);
                startService(intent);
            } else {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("restartservice");
                broadcastIntent.setClass(this, ServiceRestart.class);
                this.sendBroadcast(broadcastIntent);
            }
        }
    }

    protected void clearUserData() {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
        }
    }

    @Override
    protected void onResume() {

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("logout"));
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected void onDestroy() {

        Log.e("BaseActivity", "onDestroy");

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

        if (!appPreferences.isUserLoggedIn() && AppUtils.isLogoutServiceRunning(context, AppUtils.serviceName)) {
            Intent intent = new Intent(context, UserLogoutService.class);
            intent.setAction(UserLogoutService.ACTION_STOP_FOREGROUND_SERVICE);
            startService(intent);
        }

        super.onDestroy();
    }
}

