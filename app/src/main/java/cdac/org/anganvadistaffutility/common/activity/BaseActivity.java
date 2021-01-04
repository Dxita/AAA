package cdac.org.anganvadistaffutility.common.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.AccountPicker;

import org.spongycastle.math.ntru.polynomial.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

import cdac.org.anganvadistaffutility.App;
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
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;
import cdac.org.anganvadistaffutility.user.activity.VerifyOTPActivity;
import cdac.org.anganvadistaffutility.user.activity.VerifyUserActivity;
import cdac.org.anganvadistaffutility.user.data.VerifyOTPDetails;
import retrofit2.Call;

import static android.content.pm.PackageManager.GET_META_DATA;


public class BaseActivity extends AppCompatActivity implements LogoutListener {
    //implement LogoutListner
    public static final String TAG = BaseActivity.class.getSimpleName();
    private final int PHONE_RESOLVE_HINT = 2;
    private final int EMAIL_RESOLVE_HINT = 3;
    private BroadcastReceiver br;
    private RelativeLayout relativeLay;
    public static AppPreferences appPreferences;
    public Context context;
    public ApiInterface apiInterface;
    protected List<String> adminNumberList;


    private Boolean isUserTimedOut = false;
    private static Dialog mDialog;
    private AlarmManager alarmMgr; //TO CALL OUT THE CLASS OF THE ALARM SERVICE
    private PendingIntent alarmIntent;// FOR TARGET FUNCTION TO PERFORM WITH BROADCASTRECEIVER
    public static final long DISCONNECT_TIMEOUT = 600000;
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String message = intent.getStringExtra("message");
            if (message.equalsIgnoreCase("logout")) {
                finishAffinity();
            }
        }
    };

    public BaseActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        appPreferences = new AppPreferences(context);
        resetTitles();


        ((App) getApplication()).registerSessionListener(this);
        ((App) getApplication()).startUserSession();


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
        //overridePendingTransition(0, 0);
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

    protected void sendOtpToServerPublic(RelativeLayout relativeLayout, String mobileNumber, String otp) {
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
        //  adminNumberList.add("9784544208");
        //  adminNumberList.add("7014259658");

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
                //     AppUtils.showToast(context, body.getMessage());

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
                AppUtils.showToast(context, getResources().getString(R.string.unauthorized_user));
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

   /* @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        App.myAutoLogoutApp.touch();
    }
*/
    /*@Override
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
    }*/


    protected boolean isClassAvailable() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = manager.getRunningTasks(10);
        return taskList.get(0).numActivities != 1 ||
                !taskList.get(0).topActivity.getClassName().equals(this.getClass().getName());
    }

    protected void clearUserData() {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        resetDisconnectTimer();
        if (isUserTimedOut) {
            //show TimerOut dialog
            alertbox();
        } else {
            ((App) getApplication()).onUserInteracted();
        }

    }

    @Override
    public void onSessionLogout() {
        isUserTimedOut = true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

        if (!appPreferences.isUserLoggedIn() && AppUtils.isLogoutServiceRunning(context, AppUtils.serviceName)) {
            Intent intent = new Intent(context, UserLogoutService.class);
            intent.setAction(UserLogoutService.ACTION_STOP_FOREGROUND_SERVICE);
            startService(intent);
        }


       /* alarmMgr.cancel(alarmIntent);
        unregisterReceiver(br);*/

    }


    @SuppressLint("LongLogTag")
    @Override
    public void onStart() {
        super.onStart();// CALLING ON SUPER CLASS METHOD OF ALARM MGR

        Log.i("RootActivity:SampleBootReceiver", "On Timeout after 1 min");


        setupAlarm(); // this could be called in onCreate() instead


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        //App.myAutoLogoutApp.touch();
        resetDisconnectTimer();
        Log.e(TAG, "User interacting with screen");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopDisconnectTimer();
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + DISCONNECT_TIMEOUT, alarmIntent);


    }


    private void setupAlarm() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
                //alertbox();
                logout();
            }
        };
        registerReceiver(br, new IntentFilter("com.myapp.logout"));
        alarmIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.myapp.logout"), 0);
        alarmMgr = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
    }


    private static final Handler disconnectHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // todo
            return true;
        }
    });

    private final Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            //  alertbox();
            logout();
            // Perform any required operation on disconnect
        }
    };

    private void alertbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.app_logo);
        builder.setMessage(getString(R.string.session))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.okay), (dialog, id) -> logout());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @SuppressLint("NewApi")
    private void logout() {

        AppUtils.showToast(context, getResources().getString(R.string.logout_success));
        ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
        SharedPreferences.Editor editor = appPreferences.getAppPreference().edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(context,
                UserTypeActivity.class);
        startActivity(intent);
        finishAffinity();
        AppUtils.showToast(context, getResources().getString(R.string.logout_success));

       /* if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {

        }*/
    }

    public void resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }
}

