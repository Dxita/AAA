package cdac.org.anganvadistaffutility.admin.activity;

import android.Manifest;
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
import cdac.org.anganvadistaffutility.admin.data.VerifyAdmin;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;

public class VerifyAdminActivity extends BaseActivity {

    private TelephonyManager telephonyManager;
    private RelativeLayout relativeLayout;
    private final int RESOLVE_HINT = 2;

    private List<String> adminNumberList;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;
    private static final int SELECT_CONTACT = 5;

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
        /*HintRequest hintRequest =
                new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .build();

        PendingIntent mIntent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }*/

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            adminNumberList = new ArrayList<>();
            List<SubscriptionInfo> subscription = SubscriptionManager.from(getApplicationContext()).getActiveSubscriptionInfoList();
            for (int i = 0; i < subscription.size(); i++) {
                SubscriptionInfo info = subscription.get(i);

                adminNumberList.add("7877713957");
                adminNumberList.add("8899889955");
                Log.e(TAG, "number " + info.getNumber());
            }
        }*/

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
                    // adminNumberList.add("8899889955");
                }
            }
        } else {
            if (telephonyManager != null) {
                String number = telephonyManager.getLine1Number();
                adminNumberList.add(number);
            }
        }

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            //verifyAdmin();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

       /* if ((ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            startActivityForResult(intent, SELECT_CONTACT);
        }*/
    }

    /*private void verifyAdmin() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.VERIFY_ADMIN_URL);
        Call<VerifyAdmin> call = apiInterface.verifyAdmin(android.text.TextUtils.join(",", adminNumberList));
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyAdmin>() {
            @Override
            public void onSuccess(VerifyAdmin body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, body.getMessage());
                appPreferences.settypeadmin(true);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    startActivityForResult(intent, SELECT_CONTACT);
                }
                break;
            }
        }
    }
    

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (reqCode == SELECT_CONTACT) {
                Uri contactData = data.getData();
                assert contactData != null;
                Cursor c = getContentResolver().query(contactData, null, null, null, null);
                assert c != null;
                if (c.moveToFirst()) {
                    String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    String num = "";
                    if (Integer.parseInt(hasNumber) == 1) {
                        Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        assert numbers != null;
                        while (numbers.moveToNext()) {
                            num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Log.i(">>number", "onActivityResult: " + num + "");
                        }

                    }
                }
                c.close();
            }
        }
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    assert credential != null;
                    Log.e(TAG, "" + credential.getId());
                    // credential.getId();  <-- will need to process phone number string
                }
            }
        }
    }*/
}

