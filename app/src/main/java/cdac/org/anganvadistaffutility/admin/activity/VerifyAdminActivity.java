package cdac.org.anganvadistaffutility.admin.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

public class VerifyAdminActivity extends BaseActivity {

    private TelephonyManager telephonyManager;
    private RelativeLayout relativeLayout;
    private List<String> adminNumberList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_admin);

        relativeLayout = findViewById(R.id.relativeLayout);

        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        getHintPhoneNumber();
    }

    private void getHintPhoneNumber() {
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
                    if (number == null) {
                        number = "";
                    }
                    adminNumberList.add(number);
                }
            }
        } else {
            if (telephonyManager != null) {
                @SuppressLint("HardwareIds") String number = telephonyManager.getLine1Number();
                adminNumberList.add(number);
            }
        }

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

    }

}

