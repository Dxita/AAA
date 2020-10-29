package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;
import retrofit2.Call;

public class AanganwadiBuildingActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
    String infra_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aanganwadi_building);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        infrastructureData = new ArrayList<>();
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);

    /*    if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                infra_id = null;
            } else {
                infra_id = extras.getString("awc_item_id");
            }
        } else {
            infra_id = (String) savedInstanceState.getSerializable("awc_item_id");
        }
*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        getAanganwadiBuildingData();
    }

    private void getAanganwadiBuildingData() {


        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.AW_BUILDING_DATA);
        Call<AanganwadiBuildingData> call = apiInterface.aanganwadiBuildingData( "1",appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AanganwadiBuildingData>() {
            @Override
            public void onSuccess(AanganwadiBuildingData body) {
                Toast.makeText(context, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();

             AwcBuildingAdapter awcBuildingAdapter = new AwcBuildingAdapter(context, infrastructureData);
                recyclerView.setAdapter(awcBuildingAdapter);
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

    private static class AwcBuildingAdapter extends RecyclerView.Adapter<MyViewHolders> {

        Context context;
        List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;

        public AwcBuildingAdapter(Context context, List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData) {
            this.context = context;
            this.infrastructureData = infrastructureData;
        }

        @NonNull
        @Override
        public MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.aw_building_items, null);
            return new MyViewHolders(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolders holder, int position) {
            infrastructureData.get(position);
            holder.checkBox.setChecked(infrastructureData.get(position).getStatus().equalsIgnoreCase("yes"));
            holder.setData(context,infrastructureData.get(position));


        }


        @Override
        public int getItemCount() {
            return infrastructureData.size();

        }
    }

    private static class MyViewHolders extends RecyclerView.ViewHolder {
        AppCompatTextView item_name;
        private AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData;
        CheckBox checkBox;

        public MyViewHolders(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_tv);
            checkBox=itemView.findViewById(R.id.checkbox);
        }

        public void setData(Context context,AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData) {
            this.infrastructureData = infrastructureData;
            String name = "";
            String qty = "";

            if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                name = infrastructureData.getTidInfraNamee();

            } else {
                name = infrastructureData.getTidInfraNameh();
            }
         //  name = infrastructureData.getTidInfraNamee();
            item_name.setText(name);



        }
    }

}