package cdac.org.anganvadistaffutility.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.activity.Infrastructure.ViewInfraDetailTableActivity;
import cdac.org.anganvadistaffutility.admin.adapter.InfraStructureAdapter;
import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.AutoFitGridLayoutManager;
import retrofit2.Call;


public class ViewInfraStructureActivity extends BaseActivity implements InfraStructureAdapter.ItemClickListener {

    private RelativeLayout relativeLayout;
    private InfraStructureAdapter infraStructureAdapter;
    private List<AaganwadiInfraStructure.Data.InfrastructureDatum> infrastructureData;
    private List<Integer> infraStructureImageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_infrastructure);

        relativeLayout = findViewById(R.id.relativeLayout);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        infrastructureData = new ArrayList<>();
        infraStructureImageList = new ArrayList<>();
        InfraStructureAdapter.ItemClickListener itemClickListener = this;
        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(context, 500);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAaGanWadiInfrastructureData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

        infraStructureAdapter = new InfraStructureAdapter(context, infrastructureData, infraStructureImageList, itemClickListener);
        recyclerView.setAdapter(infraStructureAdapter);
    }

    private void getAaGanWadiInfrastructureData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<AaganwadiInfraStructure> call = apiInterface.getInfrastructureData();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AaganwadiInfraStructure>() {
            @Override
            public void onSuccess(AaganwadiInfraStructure body) {
                // AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData.addAll(body.getData().getInfrastructureData());
                setCustomInfraImages();
                infraStructureAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    @Override
    public void onItemClick(AaganwadiInfraStructure.Data.InfrastructureDatum item) {
        startActivity(new Intent(context, ViewInfraDetailTableActivity.class).putExtra("infra_id", "" + item.getTimInfraId()));
    }

    private void setCustomInfraImages() {
        for (AaganwadiInfraStructure.Data.InfrastructureDatum infrastructureDatum : infrastructureData) {
            if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("building")) {
                infraStructureImageList.add(R.drawable.ic_aaganwadi_building);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("creche")) {
                infraStructureImageList.add(R.drawable.ic_creche_house);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("electricity")) {
                infraStructureImageList.add(R.drawable.ic_electricity);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("toilet")) {
                infraStructureImageList.add(R.drawable.ic_toilet_new);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("water")) {
                infraStructureImageList.add(R.drawable.drinking_water);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("kitchen")) {
                infraStructureImageList.add(R.drawable.ic_kitchen);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("area")) {
                infraStructureImageList.add(R.drawable.ic_open_area);
            }
        }
        if (infrastructureData.size() > infraStructureImageList.size()) {
            for (int i = infraStructureImageList.size(); i < infrastructureData.size(); i++) {
                infraStructureImageList.add(R.drawable.app_logo);
            }
        }
    }
}