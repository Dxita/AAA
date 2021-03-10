package cdac.org.anganvadistaffutility.admin.activity.Infrastructure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import cdac.org.anganvadistaffutility.admin.data.InfrastructureDetailSumData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import retrofit2.Call;

public class ViewInfraDetailTableActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    private String infraID = "";
    private RecyclerView recyclerView;
    private List<InfrastructureDetailSumData.Infradatum> infraDetailsData;
    private TextView textView;
    private InfraDetailAdapter infraDetailAdapter;
    private LinearLayout tbl_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_infra_detail_table);
        infraID = getIntent().getStringExtra("infra_id");
        relativeLayout = findViewById(R.id.relativeLayout);
        tbl_layout = findViewById(R.id.tbl_layout);
        textView = findViewById(R.id.txt_title);
        recyclerView = findViewById(R.id.recycler_view);


        if (infraID.equalsIgnoreCase("1")) {
            textView.setText(getResources().getString(R.string.unavailable_anganwadi_infrastructure_facilities));
        } else if (infraID.equalsIgnoreCase("2")) {
            textView.setText(getResources().getString(R.string._electericity));
        } else if (infraID.equalsIgnoreCase("3")) {
            textView.setText(getResources().getString(R.string._drinking_water));
        } else if (infraID.equalsIgnoreCase("4")) {
            textView.setText(getResources().getString(R.string._toilet));
        } else if (infraID.equalsIgnoreCase("5")) {
            textView.setText(getResources().getString(R.string._kitchen));
        } else if (infraID.equalsIgnoreCase("6")) {
            textView.setText(getResources().getString(R.string._open_area));
        } else if (infraID.equalsIgnoreCase("7")) {
            textView.setText(getResources().getString(R.string._creche));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getInfraDetailList();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getInfraDetailList() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<InfrastructureDetailSumData> call = apiInterface.infrastructureDetailSumData("infraid", infraID);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfrastructureDetailSumData>() {
            @Override
            public void onSuccess(InfrastructureDetailSumData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infraDetailsData = new ArrayList<>();
                infraDetailsData = body.getData().getInfradata();
                if (infraDetailsData.size() > 0) {
                    if (tbl_layout.getVisibility() == View.GONE) {
                        tbl_layout.setVisibility(View.VISIBLE);
                    }

                    infraDetailAdapter = new InfraDetailAdapter(context, infraDetailsData);
                    recyclerView.setAdapter(infraDetailAdapter);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private class InfraDetailAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private final Context context;
        private final List<InfrastructureDetailSumData.Infradatum> infradata;

        public InfraDetailAdapter(Context context, List<InfrastructureDetailSumData.Infradatum> infraDetailsData) {

            this.context = context;
            this.infradata = infraDetailsData;

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.admin_district_rv_items, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.setData(context, position, infradata);
            holder.view_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DistrictWiseInfraTableActivity.class);
                    intent.putExtra("infra_detail_id", infradata.get(position).getTaidTidInfraDetailId());
                    intent.putExtra("infra_id", infraID);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return infradata.size();
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dist_name, dist_count, sr_no, view_data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dist_name = itemView.findViewById(R.id.name);
            dist_count = itemView.findViewById(R.id.count);
            sr_no = itemView.findViewById(R.id.sr_no);
            view_data = itemView.findViewById(R.id.view_registered_emp);
        }

        @SuppressLint("SetTextI18n")
        public void setData(Context context, int position, List<InfrastructureDetailSumData.Infradatum> infradata) {
            sr_no.setText("" + (position + 1));

            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                dist_name.setText(infradata.get(position).getTidInfraNameh());
                dist_count.setText(infradata.get(position).getSum());
            } else {
                dist_name.setText(infradata.get(position).getTidInfraNamee());
                dist_count.setText(infradata.get(position).getSum());
            }

        }
    }
}