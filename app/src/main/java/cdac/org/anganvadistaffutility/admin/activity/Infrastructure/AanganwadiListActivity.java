package cdac.org.anganvadistaffutility.admin.activity.Infrastructure;

import androidx.annotation.NonNull;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AnganwadiInfraData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import retrofit2.Call;

public class AanganwadiListActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    private LinearLayout main_layout;
    private RecyclerView recyclerView;
    private TextView call, email;
    private List<AnganwadiInfraData.Data.Infradatum> empdata;
    private AnganwadiInfraData.Data.Infradatum infradatum;
    private RemainingInfraDetailAdapter remainingInfraDetailAdapter;
    private String infra_id, infra_detail_id, project_id, cdponame, p_name, cdpoemail, cdponumber, p_nameh;
    private AppCompatTextView txt_project_name, txt_project_code, txt_cdpo_name;
    private TextView awc_name, awc_code, textview;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aanganwadi_list);


        infra_id = getIntent().getStringExtra("infra_id");
        infra_detail_id = getIntent().getStringExtra("infra_detail_id");
        project_id = getIntent().getStringExtra("sector_id");
       /* p_name = getIntent().getStringExtra("project_name");
        p_nameh = getIntent().getStringExtra("project_nameh");

        cdpoemail = getIntent().getStringExtra("cdpo_email");
        cdponumber = getIntent().getStringExtra("cdpo_number");
        cdponame = getIntent().getStringExtra("cdpo_name");*/

        relativeLayout = findViewById(R.id.relativeLayout);
        main_layout = findViewById(R.id.main_layout);

        txt_cdpo_name = findViewById(R.id.txt_cdpo_name);

        awc_name = findViewById(R.id.awc_name);

        call = findViewById(R.id.btn_call);
        email = findViewById(R.id.btn_email);
        recyclerView = findViewById(R.id.recycler_view);

        textview = findViewById(R.id.text);
        if (infra_id.equalsIgnoreCase("1")) {
            textview.setText(getResources().getString(R.string.aw_anganwadi));
        } else if (infra_id.equalsIgnoreCase("2")) {
            textview.setText(getResources().getString(R.string.aw_electericity));
        } else if (infra_id.equalsIgnoreCase("3")) {
            textview.setText(getResources().getString(R.string.aw_drinking_water));
        } else if (infra_id.equalsIgnoreCase("4")) {
            textview.setText(getResources().getString(R.string.aw_toilet));
        } else if (infra_id.equalsIgnoreCase("5")) {
            textview.setText(getResources().getString(R.string.aw_kitchen));
        } else if (infra_id.equalsIgnoreCase("6")) {
            textview.setText(getResources().getString(R.string.aw_open_area));
        } else if (infra_id.equalsIgnoreCase("7")) {
            textview.setText(getResources().getString(R.string.aw_creche));
        }
        call.setOnClickListener(this);
        email.setOnClickListener(this);

     /*   if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
            project_name.setText(" " + p_nameh);
        } else {
            project_name.setText(" " + p_name);
        }*/

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
        Call<AnganwadiInfraData> call = apiInterface.getAnganwadiInfraData("awc", infra_id, infra_detail_id, project_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AnganwadiInfraData>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(AnganwadiInfraData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                empdata = new ArrayList<>();
                empdata = body.getData().getInfradata();

                if (empdata.size() > 0) {
                    if (main_layout.getVisibility() == View.GONE) {
                        main_layout.setVisibility(View.VISIBLE);
                    }
                    remainingInfraDetailAdapter = new RemainingInfraDetailAdapter(context, empdata);
                    recyclerView.setAdapter(remainingInfraDetailAdapter);

                    txt_cdpo_name.setText(empdata.get(0).getProjectincharge());
                    cdpoemail=empdata.get(0).getEmailadd();
                    cdponumber=empdata.get(0).getMobileno();

                } else {
                    Toast.makeText(AanganwadiListActivity.this, "" + getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
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

    private void makePhoneCall(String cdponumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + cdponumber));
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

    private class RemainingInfraDetailAdapter extends RecyclerView.Adapter<MyHolder> {
        Context context;
        List<AnganwadiInfraData.Data.Infradatum> empdata;

        public RemainingInfraDetailAdapter(Context context, List<AnganwadiInfraData.Data.Infradatum> empdata) {
            this.context = context;
            this.empdata = empdata;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_design, null);
            return new MyHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

            holder.setData(context, position, empdata);
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (AppUtils.hasPermissions(context, AppUtils.CALL_PERMISSIONS)) {
                            callEmp(empdata.get(position).getEmpMob());
                        } else {
                            requestPermissions(AppUtils.CALL_PERMISSIONS, AppUtils.CALL_PERMISSION_REQUEST_CODE);
                        }
                    } else {
                        callEmp(empdata.get(position).getEmpMob());
                    }

                }
            });
          /*  if (empdata != null && empdata.size() > 0) {
                empdata.get(position);
                holder.sr_no.setText("" + (position + 1));
                if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                    holder.aw_worker.setText(empdata.get(position).getEmpNameh().trim().replace(" ", " "));

                } else {
                    holder.aw_worker.setText(empdata.get(position).getEmpName().trim().replace(" ", " "));

                }
                holder.aw_number.setText(empdata.get(position).getEmpMob().trim().replace(" ", " "));
            }*/
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

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView awc_worker, awc_id, awc_name, awc_number, call;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            awc_worker = itemView.findViewById(R.id.awc_worker);
            awc_id = itemView.findViewById(R.id.awc_id);
            awc_name = itemView.findViewById(R.id.awc_name);
            awc_number = itemView.findViewById(R.id.awc_number);
            call = itemView.findViewById(R.id.btn_call);
        }

        public void setData(Context context, int position, List<AnganwadiInfraData.Data.Infradatum> empdata) {
            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                awc_worker.setText(empdata.get(position).getEmpNameh());
                awc_id.setText(empdata.get(position).getPawcid());
                awc_number.setText(empdata.get(position).getEmpMob());
                awc_name.setText(empdata.get(position).getAwcnameh());
            } else {
                awc_worker.setText(empdata.get(position).getEmpName());
                awc_id.setText(empdata.get(position).getPawcid());
                awc_number.setText(empdata.get(position).getEmpMob());
                awc_name.setText(empdata.get(position).getAwcnamee());
            }
        }
    }
}