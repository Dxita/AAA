package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.activity.beneficiary.MotherMappingActivity;
import cdac.org.anganvadistaffutility.user.data.AvailableAwInfraDetailData;
import cdac.org.anganvadistaffutility.user.data.RemainingInfrastructureData;
import retrofit2.Call;

public class AvailableInfraDetailsActivity extends BaseActivity implements View.OnClickListener {
    AppCompatButton add_btn, edit_btn;
    RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    List<AvailableAwInfraDetailData.Data.Empdatum> empdatumList;
    AvailableInfraAdapter availableInfraAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_available_infra_details);
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);
        add_btn = findViewById(R.id.add);
        edit_btn = findViewById(R.id.edit);

        add_btn.setOnClickListener(this);
        edit_btn.setOnClickListener(this);

        empdatumList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAvailableInfraDetailData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAvailableInfraDetailData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getAvailableInfraDetailData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.AVAIL_INFRA_DETAILS_URL);
        Call<AvailableAwInfraDetailData> call = apiInterface.availableInfrastructureData(appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AvailableAwInfraDetailData>() {
            @Override
            public void onSuccess(AvailableAwInfraDetailData body) {

                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                empdatumList = new ArrayList<>();
                empdatumList = body.getData().getEmpdata();

                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                empdatumList = new ArrayList<>();
                empdatumList = body.getData().getEmpdata();
                availableInfraAdapter = new AvailableInfraAdapter(context, empdatumList);
                recyclerView.setAdapter(availableInfraAdapter);

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add) {


            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                getAvailableInfra();
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }

        }

        if (v.getId() == R.id.edit) {
            startActivity(new Intent(context, AWCInfrastructureActivity.class));
        }
    }

    private void getAvailableInfra() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.AVAIL_INFRA_DETAILS_URL);
        Call<RemainingInfrastructureData> call = apiInterface.remainingInfrastructureData(appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<RemainingInfrastructureData>() {
            @Override
            public void onSuccess(RemainingInfrastructureData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);

                Toast.makeText(context, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }


    private static class AvailableInfraAdapter extends RecyclerView.Adapter<MyViewHold> {

        Context context;
        List<AvailableAwInfraDetailData.Data.Empdatum> empdatumList;
        MyViewHold myViewHold;


        public AvailableInfraAdapter(Context context, List<AvailableAwInfraDetailData.Data.Empdatum> empdatumList) {
            this.context = context;
            this.empdatumList = empdatumList;
        }

        @NonNull
        @Override
        public MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.availableinfradetailsrv, null);
            return new MyViewHold(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHold holder, int position) {
            myViewHold = holder;
            empdatumList.get(position);

            holder.qty_tv.setText(empdatumList.get(position).getTjaidQty());

            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                holder.item_tv.setText(empdatumList.get(position).getTidInfraNameh());
                holder.infra_name.setText(empdatumList.get(position).getTimInfraNameh());
            } else {
                holder.item_tv.setText(empdatumList.get(position).getTidInfraNamee());
                holder.infra_name.setText(empdatumList.get(position).getTimInfraNamee());
            }


        }

        @Override
        public int getItemCount() {
            return empdatumList.size();
        }
    }

    private static class MyViewHold extends RecyclerView.ViewHolder {
        AppCompatTextView qty_tv, item_tv, infra_name;

        public MyViewHold(@NonNull View itemView) {
            super(itemView);
            qty_tv = itemView.findViewById(R.id.qty_tv);
            item_tv = itemView.findViewById(R.id.item_tv);
            infra_name = itemView.findViewById(R.id.infra_name);

        }
    }
}