
package cdac.org.anganvadistaffutility.user.activity;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.adapter.InfraStructureAdapter;
import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.AutoFitGridLayoutManager;
import cdac.org.anganvadistaffutility.user.adapter.UserInfraStructureAdapter;
import retrofit2.Call;

public class InfraCategoriesActivity extends BaseActivity implements UserInfraStructureAdapter.ItemClickListener  {
    private RelativeLayout relativeLayout;
    private UserInfraStructureAdapter userInfraStructureAdapter;
    private List<AaganwadiInfraStructure.InfrastructureDatum> infrastructureData;
    private List<Integer> infraStructureImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_infra_categories);

        relativeLayout = findViewById(R.id.relativeLayout);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        infrastructureData = new ArrayList<>();
        infraStructureImageList = new ArrayList<>();
        UserInfraStructureAdapter.ItemClickListener itemClickListener = this;
        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(context, 500);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAaGanWadiInfrastructureData();

        }

        userInfraStructureAdapter = new UserInfraStructureAdapter(context, infrastructureData, infraStructureImageList, itemClickListener);
        recyclerView.setAdapter(userInfraStructureAdapter);
    }

    private void getAaGanWadiInfrastructureData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.INFRASTRUCTURE_BASE_URL);
        Call<AaganwadiInfraStructure> call = apiInterface.getInfrastructureData();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AaganwadiInfraStructure>() {
            @Override
            public void onSuccess(AaganwadiInfraStructure body) {
                // AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData.addAll(body.getData().getInfrastructureData());
                setCustomInfraImages();
                userInfraStructureAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));

    }

    @Override
    public void onItemClick(AaganwadiInfraStructure.InfrastructureDatum item) {
        AppUtils.showToast(context, "" + item.getTimInfrastructureId() + ": " + item.getTimInfrastructureNameh());
    }

    private void setCustomInfraImages() {
        infraStructureImageList.add(R.drawable.ic_aaganwadi_building);
        infraStructureImageList.add(R.drawable.ic_electricity);
        infraStructureImageList.add(R.drawable.ic_drinking_water);
        infraStructureImageList.add(R.drawable.ic_toilet_new);

        infraStructureImageList.add(R.drawable.ic_kitchen);
        infraStructureImageList.add(R.drawable.ic_open_area);
        infraStructureImageList.add(R.drawable.ic_creche_house);

        if (infrastructureData.size() > infraStructureImageList.size()) {
            for (int i = infraStructureImageList.size(); i < infrastructureData.size(); i++) {
                infraStructureImageList.add(R.drawable.app_logo);
            }
        }
    }


}



