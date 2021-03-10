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
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import retrofit2.Call;

public class DistrictWiseInfraTableActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    private String infra_detail_id, infra_id;
    private RecyclerView recyclerView;
    private List<InfraStructureDetailData.Infradatum> infraDetailsData;
    private TextView textView;
    private DistrictWiseInfraDetailAdapter districtWiseInfraDetailAdapter;
    private LinearLayout tbl_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_district_wise_infra_table);

        infra_detail_id = getIntent().getStringExtra("infra_detail_id");
        infra_id = getIntent().getStringExtra("infra_id");
        relativeLayout = findViewById(R.id.relativeLayout);
        tbl_layout = findViewById(R.id.tbl_layout);
        recyclerView = findViewById(R.id.recycler_view);

        textView = findViewById(R.id.txt_title);
        if (infra_id.equalsIgnoreCase("1")) {
            textView.setText(getResources().getString(R.string.district_anganwadi));
        } else if (infra_id.equalsIgnoreCase("2")) {
            textView.setText(getResources().getString(R.string.district_electericity));
        } else if (infra_id.equalsIgnoreCase("3")) {
            textView.setText(getResources().getString(R.string.district_drinking_water));
        } else if (infra_id.equalsIgnoreCase("4")) {
            textView.setText(getResources().getString(R.string.district_toilet));
        } else if (infra_id.equalsIgnoreCase("5")) {
            textView.setText(getResources().getString(R.string.district_kitchen));
        } else if (infra_id.equalsIgnoreCase("6")) {
            textView.setText(getResources().getString(R.string.district_open_area));
        } else if (infra_id.equalsIgnoreCase("7")) {
            textView.setText(getResources().getString(R.string.district_creche));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getDistrictWIseInfraDetailList();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

    }

    private void getDistrictWIseInfraDetailList() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<InfraStructureDetailData> call = apiInterface.getInfrastructureDetails("dist", infra_id, infra_detail_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfraStructureDetailData>() {
            @Override
            public void onSuccess(InfraStructureDetailData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);

                infraDetailsData = new ArrayList<>();
                infraDetailsData = body.getData().getInfradata();
                if (infraDetailsData.size() > 0) {
                    if (tbl_layout.getVisibility() == View.GONE) {
                        tbl_layout.setVisibility(View.VISIBLE);
                    }
                    districtWiseInfraDetailAdapter = new DistrictWiseInfraDetailAdapter(context, infraDetailsData);
                    recyclerView.setAdapter(districtWiseInfraDetailAdapter);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private class DistrictWiseInfraDetailAdapter extends RecyclerView.Adapter<MyHolder> {
        private final Context context;
        private final List<InfraStructureDetailData.Infradatum> infradata;

        public DistrictWiseInfraDetailAdapter(Context context, List<InfraStructureDetailData.Infradatum> infraDetailsData) {
            this.context = context;
            this.infradata = infraDetailsData;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.admin_district_rv_items, parent, false);
            return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.setData(context, position, infradata);

            holder.view_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProjectWiseInfraTableActivity.class);
                    intent.putExtra("district_id", infradata.get(position).getDistid());
                    intent.putExtra("infra_detail_id", infra_detail_id);
                    intent.putExtra("infra_id", infra_id);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return infradata.size();
        }
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView dist_name, dist_count, sr_no, view_data;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            dist_name = itemView.findViewById(R.id.name);
            dist_count = itemView.findViewById(R.id.count);
            sr_no = itemView.findViewById(R.id.sr_no);
            view_data = itemView.findViewById(R.id.view_registered_emp);

        }

        @SuppressLint("SetTextI18n")
        public void setData(Context context, int position, List<InfraStructureDetailData.Infradatum> infradata) {
            sr_no.setText("" + (position + 1));

            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                dist_name.setText(infradata.get(position).getDistnameh());
                dist_count.setText(infradata.get(position).getCount());
            } else {
                dist_name.setText(infradata.get(position).getDistrict());
                dist_count.setText(infradata.get(position).getCount());
            }
        }
    }
}