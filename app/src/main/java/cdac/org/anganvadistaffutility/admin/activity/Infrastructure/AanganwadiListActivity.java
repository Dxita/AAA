package cdac.org.anganvadistaffutility.admin.activity.Infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AnganwadiInfraData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.activity.infrastructure.AddActivity;
import cdac.org.anganvadistaffutility.user.data.RemainingInfraDetailData;
import retrofit2.Call;


public class AanganwadiListActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    AppCompatButton call, email;
    List<AnganwadiInfraData.Data.Infradatum> empdata;
    private AnganwadiInfraData.Data.Infradatum infradatum;
    private RemainingInfraDetailAdapter remainingInfraDetailAdapter;
    String infra_id, project_id, cdponame, p_name, cdpoemail, cdponumber;
    AppCompatTextView project_name, cdpo_name, cdpo_number, project_code;
    TextView awc_name, awc_code;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aanganwadi_list);
        infra_id = getIntent().getStringExtra("infra_id");
        project_id = getIntent().getStringExtra("project_id");
        p_name = getIntent().getStringExtra("project_name");

        cdpoemail = getIntent().getStringExtra("cdpo_email");
        cdponumber = getIntent().getStringExtra("cdpo_number");
        cdponame = getIntent().getStringExtra("cdpo_name");

        relativeLayout = findViewById(R.id.relativeLayout);
        project_name = findViewById(R.id.txt_project_name);
        cdpo_name = findViewById(R.id.txt_cdpo_name);
        project_code = findViewById(R.id.txt_project_code);
        awc_name = findViewById(R.id.awc_name);
        awc_code = findViewById(R.id.awc_code);
        call = findViewById(R.id.btn_call);
        email = findViewById(R.id.btn_email);
        recyclerView = findViewById(R.id.recycler_view);

        call.setOnClickListener(this);
        email.setOnClickListener(this);
        cdpo_name.setText(getResources().getString(R.string.cdpo_name) + " " + cdponame);
        project_code.setText(getResources().getString(R.string.proj_code) + " " + project_id);
        project_name.setText(getResources().getString(R.string.proj_name) + " " + p_name);
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
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.AVAIL_INFRA_DETAILS_URL);
        Call<AnganwadiInfraData> call = apiInterface.getAnganwadiInfraData("awc", infra_id, project_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AnganwadiInfraData>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(AnganwadiInfraData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);

                empdata = new ArrayList<>();
                empdata = body.getData().getInfradata();
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                empdata = new ArrayList<>();
                empdata = body.getData().getInfradata();
                remainingInfraDetailAdapter = new RemainingInfraDetailAdapter(context, empdata);
                recyclerView.setAdapter(remainingInfraDetailAdapter);

                awc_name.setText(getResources().getString(R.string.awc_name) + ":" + " " + empdata.get(0).getAwcnamee());
                awc_code.setText(getResources().getString(R.string.awc_id) + ":" + " " + empdata.get(0).getPawcid());


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
            makePhoneCall(cdponumber);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.hasPermissions(context, AppUtils.CALL_PERMISSIONS)) {
                    makePhoneCall(cdponumber);
                } else {
                    requestPermissions(AppUtils.CALL_PERMISSIONS, AppUtils.CALL_PERMISSION_REQUEST_CODE);
                }
            } else {
                makePhoneCall(cdponumber);
            }
        }
        if (view.getId() == R.id.btn_email) {
            sendEmail(cdpoemail);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtils.CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(cdponumber);
            }
        }
    }

    private void makePhoneCall(String mobile) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        startActivity(callIntent);
    }

    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        //need this to prompts email client only
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }

    private static class RemainingInfraDetailAdapter extends RecyclerView.Adapter<MyHolder> {

        Context context;
        List<AnganwadiInfraData.Data.Infradatum> empdata;

        public RemainingInfraDetailAdapter(Context context, List<AnganwadiInfraData.Data.Infradatum> empdata) {
            this.context = context;
            this.empdata = empdata;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.anganwadilist_rv, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            if (empdata != null && empdata.size() > 0) {
                empdata.get(position);
                holder.sr_no.setText("" + (position + 1));
                holder.aw_worker.setText(empdata.get(position).getEmpName().trim().replace(" ", " "));
                holder.aw_number.setText(empdata.get(position).getEmpMob().trim().replace(" ", " "));
            }
        }


        @Override
        public int getItemCount() {
            return empdata.size();
        }
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        AppCompatTextView aw_name, aw_worker, aw_number, sr_no;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //  aw_name = itemView.findViewById(R.id.awc_name);
            sr_no = itemView.findViewById(R.id.sr_no);
            aw_number = itemView.findViewById(R.id.awc_number);
            aw_worker = itemView.findViewById(R.id.awc_worker);
        }
    }
}