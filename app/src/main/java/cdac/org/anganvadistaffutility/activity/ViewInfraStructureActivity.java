package cdac.org.anganvadistaffutility.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.adapter.InfraStructureAdapter;
import cdac.org.anganvadistaffutility.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import cdac.org.anganvadistaffutility.utils.AutoFitGridLayoutManager;
import retrofit2.Call;

public class ViewInfraStructureActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private InfraStructureAdapter infraStructureAdapter;
    private List<AaganwadiInfraStructure.InfrastructureDatum> infrastructureData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_infrastructure);

        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);

        infrastructureData = new ArrayList<>();
        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(context, 500);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAaganwadiInfrastructureData();
        }

        infraStructureAdapter = new InfraStructureAdapter(context, infrastructureData);
        recyclerView.setAdapter(infraStructureAdapter);
    }

    private void getAaganwadiInfrastructureData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.INFRASTRUCTURE_BASE_URL);
        Call<AaganwadiInfraStructure> call = apiInterface.getInfrastructureData();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AaganwadiInfraStructure>() {
            @Override
            public void onSuccess(AaganwadiInfraStructure body) {
                //   AppUtils.showToast(context, body.getMessage());
                //  AppUtils.showToast(context, body.getData().getInfrastructureData().get(0).getTimInfrastructureNamee());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData.addAll(body.getData().getInfrastructureData());
                infraStructureAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }
}
