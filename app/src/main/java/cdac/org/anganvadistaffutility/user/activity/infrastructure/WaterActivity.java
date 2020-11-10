package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import cdac.org.anganvadistaffutility.user.adapter.DrinkingWaterAdapter;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;
import retrofit2.Call;

public class WaterActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
    String infra_id;
    AppCompatButton submit_btn, update_btn, cancel_btn;
    DrinkingWaterAdapter drinkingWaterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_water);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                infra_id = null;
            } else {
                infra_id = extras.getString("infra_id");
            }
        } else {
            infra_id = (String) savedInstanceState.getSerializable("infra_id");
        }

        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);
        update_btn = findViewById(R.id.update_btn);
        submit_btn = findViewById(R.id.submit_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        update_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        infrastructureData = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getDrinkingWaterData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

    }

    private void getDrinkingWaterData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.AW_BUILDING_DATA);
        Call<AanganwadiBuildingData> call = apiInterface.aanganwadiBuildingData(infra_id, appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AanganwadiBuildingData>() {
            @Override
            public void onSuccess(AanganwadiBuildingData body) {
                Toast.makeText(context, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();

                drinkingWaterAdapter = new DrinkingWaterAdapter(context, infrastructureData);
                recyclerView.setAdapter(drinkingWaterAdapter);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_btn) {
            //update_btn.setVisibility(View.VISIBLE);
            //    submit_btn.setVisibility(View.GONE);
            Toast.makeText(context, "submitted", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.cancel_btn) {
            // update_btn.setVisibility(View.VISIBLE);
            //  submit_btn.setVisibility(View.GONE);
            finish();
        }
    }


}