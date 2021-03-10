package cdac.org.anganvadistaffutility.admin.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.DistrictEmployeesCount;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import retrofit2.Call;

public class DistrictWiseTableActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private List<DistrictEmployeesCount.Empdatum> empDatumList;
    private LinearLayout tbl_layout;
    private String userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_district_wise_table);

        userType = getIntent().getStringExtra("user_type");

        TextView txt_title = findViewById(R.id.txt_title);
        if (userType.equalsIgnoreCase("registered_user")) {
            txt_title.setText(getResources().getString(R.string.district_wise_reg_users));
        } else {
            txt_title.setText(getResources().getString(R.string.district_wise_unreg_users));
        }

        tbl_layout = findViewById(R.id.tbl_layout);
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAdminUserData();
        }
    }

    private void getAdminUserData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<DistrictEmployeesCount> call = apiInterface.getDistrictEmployees("1");
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<DistrictEmployeesCount>() {
            @Override
            public void onSuccess(DistrictEmployeesCount body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                empDatumList = new ArrayList<>();
                empDatumList = body.getData().getEmpdata();

                if (empDatumList.size() > 0) {
                    if (tbl_layout.getVisibility() == View.GONE) {
                        tbl_layout.setVisibility(View.VISIBLE);
                    }

                    DistrictWiseTableAdapter districtWiseTableAdapter = new DistrictWiseTableAdapter(context, empDatumList);
                    recyclerView.setAdapter(districtWiseTableAdapter);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    public class DistrictWiseTableAdapter extends RecyclerView.Adapter<DistrictWiseTableAdapter.ViewHolder> {

        private final Context mContext;
        protected List<DistrictEmployeesCount.Empdatum> districtWiseEmployeeDetailsList;


        public DistrictWiseTableAdapter(Context mContext, List<DistrictEmployeesCount.Empdatum> districtWiseEmployeeDetailsList) {
            this.mContext = mContext;
            this.districtWiseEmployeeDetailsList = districtWiseEmployeeDetailsList;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.admin_district_rv_items, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.setData(position, districtWiseEmployeeDetailsList);

            holder.view_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProjectWiseTableActivity.class);
                    intent.putExtra("dist_id", districtWiseEmployeeDetailsList.get(position).getTjdmDistrictId());
                    intent.putExtra("user_type", userType);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return districtWiseEmployeeDetailsList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView dist_name, dist_count, sr_no, view_data;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                dist_name = itemView.findViewById(R.id.name);
                dist_count = itemView.findViewById(R.id.count);
                sr_no = itemView.findViewById(R.id.sr_no);
                view_data = itemView.findViewById(R.id.view_registered_emp);
            }

            @SuppressLint("SetTextI18n")
            public void setData(int position, List<DistrictEmployeesCount.Empdatum> districtWiseEmployeeDetailsList) {
                sr_no.setText("" + (position + 1));

                if (userType.equalsIgnoreCase("registered_user")) {
                    if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                        dist_name.setText(districtWiseEmployeeDetailsList.get(position).getTjdmDistrictNameHindi());
                        dist_count.setText(districtWiseEmployeeDetailsList.get(position).getRegistered());
                    } else {
                        dist_name.setText(districtWiseEmployeeDetailsList.get(position).getTjdmDistrictNameEnglish());
                        dist_count.setText(districtWiseEmployeeDetailsList.get(position).getRegistered());
                    }
                } else {
                    if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                        dist_name.setText(districtWiseEmployeeDetailsList.get(position).getTjdmDistrictNameHindi());
                        dist_count.setText(districtWiseEmployeeDetailsList.get(position).getUnregistered());
                    } else {
                        dist_name.setText(districtWiseEmployeeDetailsList.get(position).getTjdmDistrictNameEnglish());
                        dist_count.setText(districtWiseEmployeeDetailsList.get(position).getUnregistered());
                    }
                }

            }

        }

    }

}

