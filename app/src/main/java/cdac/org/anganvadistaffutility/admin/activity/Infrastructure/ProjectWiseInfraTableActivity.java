package cdac.org.anganvadistaffutility.admin.activity.Infrastructure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailProjectData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import retrofit2.Call;

public class ProjectWiseInfraTableActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    private String infra_detail_id, infra_id, district_id;
    private RecyclerView recyclerView;
    private List<InfraDetailProjectData.Infradatum> infraDetailsData;
    private TextView textView;
    private ProjectWiseInfraDetailAdapter projectWiseInfraDetailAdapter;
    private LinearLayout tbl_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_project_wise_infra_table);

        infra_id = getIntent().getStringExtra("infra_id");
        district_id = getIntent().getStringExtra("district_id");
        infra_detail_id = getIntent().getStringExtra("infra_detail_id");

        relativeLayout = findViewById(R.id.relativeLayout);
        tbl_layout = findViewById(R.id.tbl_layout);
        recyclerView = findViewById(R.id.recycler_view);

        textView = findViewById(R.id.txt_title);
        if (infra_id.equalsIgnoreCase("1")) {
            textView.setText(getResources().getString(R.string.project_anganwadi));
        } else if (infra_id.equalsIgnoreCase("2")) {
            textView.setText(getResources().getString(R.string.project_electericity));
        } else if (infra_id.equalsIgnoreCase("3")) {
            textView.setText(getResources().getString(R.string.project_drinking_water));
        } else if (infra_id.equalsIgnoreCase("4")) {
            textView.setText(getResources().getString(R.string.project_toilet));
        } else if (infra_id.equalsIgnoreCase("5")) {
            textView.setText(getResources().getString(R.string.project_kitchen));
        } else if (infra_id.equalsIgnoreCase("6")) {
            textView.setText(getResources().getString(R.string.project_open_area));
        } else if (infra_id.equalsIgnoreCase("7")) {
            textView.setText(getResources().getString(R.string.project_creche));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getProjectWIseInfraDetailList();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getProjectWIseInfraDetailList() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<InfraDetailProjectData> call = apiInterface.getInfrastructureProjectDetails("proj", infra_id, district_id, infra_detail_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfraDetailProjectData>() {
            @Override
            public void onSuccess(InfraDetailProjectData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infraDetailsData = new ArrayList<>();
                infraDetailsData = body.getData().getInfradata();
                if (infraDetailsData.size() > 0) {
                    if (tbl_layout.getVisibility() == View.GONE) {
                        tbl_layout.setVisibility(View.VISIBLE);
                    }

                    projectWiseInfraDetailAdapter = new ProjectWiseInfraDetailAdapter(context, infraDetailsData);
                    recyclerView.setAdapter(projectWiseInfraDetailAdapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private class ProjectWiseInfraDetailAdapter extends RecyclerView.Adapter<MyViewHolers> {
        private final Context context;
        private final List<InfraDetailProjectData.Infradatum> infradata;

        public ProjectWiseInfraDetailAdapter(Context context, List<InfraDetailProjectData.Infradatum> infraDetailsData) {
            this.context = context;
            this.infradata = infraDetailsData;
        }

        @NonNull
        @Override
        public MyViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.admin_district_rv_items, parent, false);
            return new MyViewHolers(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolers holder, int position) {
            holder.setData(context, position, infradata);

            holder.view_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AanganwadiListActivity.class);
                    intent.putExtra("infra_id", infra_id);
                    intent.putExtra("infra_detail_id", infra_detail_id);
                    intent.putExtra("project_id", infradata.get(position).getProjectcode());
                    intent.putExtra("project_name", infradata.get(position).getProjectname());
                    intent.putExtra("project_nameh", infradata.get(position).getProjectnameh());
                    intent.putExtra("cdpo_number", infradata.get(position).getCdpoMobileno());
                    intent.putExtra("cdpo_email", infradata.get(position).getCdpoEmail());
                    intent.putExtra("cdpo_name", infradata.get(position).getCdpoName());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return infradata.size();
        }
    }

    private static class MyViewHolers extends RecyclerView.ViewHolder {
        TextView dist_name, dist_count, sr_no, view_data;

        public MyViewHolers(@NonNull View itemView) {
            super(itemView);
            dist_name = itemView.findViewById(R.id.name);
            dist_count = itemView.findViewById(R.id.count);
            sr_no = itemView.findViewById(R.id.sr_no);
            view_data = itemView.findViewById(R.id.view_registered_emp);
        }

        @SuppressLint("SetTextI18n")
        public void setData(Context context, int position, List<InfraDetailProjectData.Infradatum> infradata) {
            sr_no.setText("" + (position + 1));

            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                dist_name.setText(infradata.get(position).getProjectnameh());
                dist_count.setText(infradata.get(position).getCount());
            } else {
                dist_name.setText(infradata.get(position).getProjectname());
                dist_count.setText(infradata.get(position).getCount());
            }
        }
    }
}