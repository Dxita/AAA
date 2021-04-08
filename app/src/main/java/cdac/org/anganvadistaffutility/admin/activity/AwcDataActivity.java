package cdac.org.anganvadistaffutility.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.Infrastructure.AanganwadiListActivity;
import cdac.org.anganvadistaffutility.admin.data.AnganwadiInfraData;
import cdac.org.anganvadistaffutility.admin.data.AwcData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import retrofit2.Call;

public class AwcDataActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    private String distid, project_id, project_name, project_nameh, cdpo_number, cdpo_email, cdpo_name;
    private AppCompatTextView txt_project_name, txt_project_code, txt_cdpo_name;
    private List<AwcData.Data.Empdatum> empdata;
    private LinearLayout main_layout;
    private RecyclerView recyclerView;
    private TextView btn_call, btn_email;
    private AwcWiseDataAdapter awcWiseDataAdapter;
    private String userType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_awc_data);

        userType = getIntent().getStringExtra("user_type");
        distid = getIntent().getStringExtra("district_id");
        project_id = getIntent().getStringExtra("project_id");
        project_name = getIntent().getStringExtra("project_name");
        project_nameh = getIntent().getStringExtra("project_nameh");
        cdpo_number = getIntent().getStringExtra("cdpo_number");
        cdpo_email = getIntent().getStringExtra("cdpo_email");
        cdpo_name = getIntent().getStringExtra("cdpo_name");

        main_layout = findViewById(R.id.main_layout);
        recyclerView = findViewById(R.id.recycler_view);
        txt_project_code = findViewById(R.id.txt_project_code);
        txt_project_name = findViewById(R.id.txt_project_name);
        txt_cdpo_name = findViewById(R.id.txt_cdpo_name);
        btn_call = findViewById(R.id.btn_call);
        btn_email = findViewById(R.id.btn_email);
        txt_project_code.setText(project_id);
        txt_cdpo_name.setText(cdpo_name);

        if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
            txt_project_name.setText(project_nameh);
        } else {
            txt_project_name.setText(project_name);
        }

        TextView txt_title = findViewById(R.id.text);
        if (userType.equalsIgnoreCase("registered_user")) {
            txt_title.setText(getResources().getString(R.string.registered_aaganwadi_data));
        } else {
            txt_title.setText(getResources().getString(R.string.unregistered_aaganwadi_data));
        }

        btn_call.setOnClickListener(this);
        btn_email.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAnganwadiList();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

    }

    private void getAnganwadiList() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<AwcData> call = apiInterface.getAwcData(distid, "3");
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AwcData>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(AwcData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                empdata = body.getData().getEmpdata();


                if (empdata.size() > 0) {
                    if (main_layout.getVisibility() == View.GONE) {
                        main_layout.setVisibility(View.VISIBLE);
                    }
                    awcWiseDataAdapter = new AwcWiseDataAdapter(context, empdata);
                    recyclerView.setAdapter(awcWiseDataAdapter);

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
    public void onClick(View view) {
        if (view.getId() == R.id.btn_call) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.hasPermissions(context, AppUtils.CALL_PERMISSIONS))
                    makePhoneCall(cdpo_number);
                else {
                    requestPermissions(AppUtils.CALL_PERMISSIONS, AppUtils.CALL_PERMISSION_REQUEST_CODE);
                }
            } else {
                makePhoneCall(cdpo_number);
            }
        }
        if (view.getId() == R.id.btn_email) {
            sendEmail(cdpo_email);
        }
    }

    private void sendEmail(String cdpo_email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{cdpo_email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        //need this to prompts email client only
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }

    private void makePhoneCall(String cdpo_number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + cdpo_number));
        startActivity(callIntent);
    }

    private class AwcWiseDataAdapter extends RecyclerView.Adapter<AwcHolder> {
        Context context;
        List<AwcData.Data.Empdatum> empdata;

        public AwcWiseDataAdapter(Context context, List<AwcData.Data.Empdatum> empdata) {
            this.context = context;
            this.empdata = empdata;
        }

        @NonNull
        @Override
        public AwcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_design, null);
            return new AwcHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AwcHolder holder, int position) {
            holder.setData(context, position, empdata);
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (AppUtils.hasPermissions(context, AppUtils.CALL_PERMISSIONS)) {
                            callEmp(empdata.get(position).getTaemMoblieNumber());
                        } else {
                            requestPermissions(AppUtils.CALL_PERMISSIONS, AppUtils.CALL_PERMISSION_REQUEST_CODE);
                        }
                    } else {
                        callEmp(empdata.get(position).getTaemMoblieNumber());
                    }

                }
            });
        }

        private void callEmp(String empMob) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + empMob));
            startActivity(callIntent);
        }
        @Override
        public int getItemCount() {
            return empdata.size();
        }
    }

    private class AwcHolder extends RecyclerView.ViewHolder {
        TextView awc_worker, awc_id, awc_name, awc_number, call;

        public AwcHolder(@NonNull View itemView) {
            super(itemView);
            awc_worker = itemView.findViewById(R.id.awc_worker);
            awc_id = itemView.findViewById(R.id.awc_id);
            awc_name = itemView.findViewById(R.id.awc_name);
            awc_number = itemView.findViewById(R.id.awc_number);
            call = itemView.findViewById(R.id.btn_call);
        }

        public void setData(Context context, int position, List<AwcData.Data.Empdatum> empdata) {

            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                awc_worker.setText(empdata.get(position).getTaemEmployeeNameHindi());
                awc_id.setText(empdata.get(position).getTaemAwcId());
                awc_number.setText(empdata.get(position).getTaemMoblieNumber());
                awc_name.setText(empdata.get(position).getTaemAwcNameHindi());
            } else {
                awc_worker.setText(empdata.get(position).getTaemEmployeeNameEnglish());
                awc_id.setText(empdata.get(position).getTaemAwcId());
                awc_number.setText(empdata.get(position).getTaemMoblieNumber());
                awc_name.setText(empdata.get(position).getTaemAwcNameEnglish());
            }
        }
    }
}
