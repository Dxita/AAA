package cdac.org.anganvadistaffutility.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.AwcItemsData;
import retrofit2.Call;

public class AwcInfraItemsActivity extends BaseActivity {
    private RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    List<AwcItemsData.Data.InfrastructureDatum> infrastructureData;
    String infra_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_awc_infra_items);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        infrastructureData = new ArrayList<>();
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);


/*
        AaganwadiInfraStructure.Data.InfrastructureDatum infrastructureData = getIntent().getDataString("awc_item_id");
        Log.d("id", String.valueOf(infrastructureData));

     */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                infra_id = null;
            } else {
                infra_id = extras.getString("awc_item_id");
            }
        } else {
            infra_id = (String) savedInstanceState.getSerializable("awc_item_id");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        getAwcItemData();


    }

    private void getAwcItemData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.USER_AWC_ITEMS);
        Call<AwcItemsData> call = apiInterface.awcItemData(appPreferences.getAwcId(), infra_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AwcItemsData>() {
            @Override
            public void onSuccess(AwcItemsData body) {
                Toast.makeText(context, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();

                AwcItemsAdapter awcItemsAdapter = new AwcItemsAdapter(context, infrastructureData);
                recyclerView.setAdapter(awcItemsAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private static class AwcItemsAdapter extends RecyclerView.Adapter<MyViewHolder> {
        Context context;
        List<AwcItemsData.Data.InfrastructureDatum> infrastructureData;

        public AwcItemsAdapter(Context context, List<AwcItemsData.Data.InfrastructureDatum> infrastructureData) {

            this.context = context;
            this.infrastructureData = infrastructureData;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(context).inflate(R.layout.awc_items_rv, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            infrastructureData.get(position);
            holder.setData(infrastructureData.get(position));
            //holder.item_name.setText();
        }


        @Override
        public int getItemCount() {
            return infrastructureData.size();
        }


    }


    private static class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView item_name, item_qty;
        protected AwcItemsData.Data.InfrastructureDatum infrastructureData;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_tv);
            item_qty = itemView.findViewById(R.id.item_qty);
        }

        public void setData(AwcItemsData.Data.InfrastructureDatum infrastructureData) {
            this.infrastructureData = infrastructureData;
            String name = "";
            String qty = "";
            name = infrastructureData.getTidInfraNamee();
            qty = infrastructureData.getTaidQty();
            item_name.setText(name);
            item_qty.setText(qty);


        }
    }

    private static class HeaderView extends MyViewHolder {
        TextView item_header, qty_header;

        public HeaderView(View itemView) {
            super(itemView);
            item_header = itemView.findViewById(R.id.item_header);
            qty_header = itemView.findViewById(R.id.qty_header);

        }
    }
}