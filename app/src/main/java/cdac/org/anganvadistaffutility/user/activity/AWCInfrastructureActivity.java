package cdac.org.anganvadistaffutility.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.UserTypeActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.AutoFitGridLayoutManager;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.adapter.UserInfraStructureAdapter;
import cdac.org.anganvadistaffutility.user.data.UserInfrastructureData;
import retrofit2.Call;

public class AWCInfrastructureActivity extends BaseActivity implements UserInfraStructureAdapter.ItemClickListener {
    private RelativeLayout relativeLayout;
    private UserInfraStructureAdapter userInfraStructureAdapter;
    private List<UserInfrastructureData.Data.InfrastructureDatum> infrastructureData;
    private List<Integer> infraStructureImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_awc_infrastructure);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

        userInfraStructureAdapter = new UserInfraStructureAdapter(context, infrastructureData, infraStructureImageList, itemClickListener);
        recyclerView.setAdapter(userInfraStructureAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.change_your_language:
                if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                    changeAppLocale((AppCompatActivity) context, LocaleManager.ENGLISH);
                } else {
                    changeAppLocale((AppCompatActivity) context, LocaleManager.HINDI);
                }
                return true;
            case R.id.log_out:
                SharedPreferences.Editor editor = appPreferences.getAppPreference().edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(context,
                        UserTypeActivity.class);
                startActivity(intent);
                finishAffinity();

                AppUtils.showToast(context, getResources().getString(R.string.logout_success));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setLoginState(boolean b) {
        SharedPreferences sp = getSharedPreferences("LoginState",
                MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("setLoggingOut", b);
        ed.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void getAaGanWadiInfrastructureData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.USER_AWC_ITEMS);
        Call<UserInfrastructureData> call = apiInterface.userInfrastructureData(appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<UserInfrastructureData>() {
            @Override
            public void onSuccess(UserInfrastructureData body) {
                // AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData.addAll(body.getData().getInfrastructureData());
                infrastructureData = body.getData().getInfrastructureData();
                //   setCustomInfraImages();
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
    public void onItemClick(UserInfrastructureData.Data.InfrastructureDatum item) {

       /* startActivity(new Intent(context,AwcInfraItemsActivity.class).putExtra("awc_item_id", item.getTimInfraId()));
        Log.d("id_infra",item.getTimInfraId());
*/
        AppUtils.showToast(context, "" + item.getTimInfraId() + ": " + item.getTimInfraNamee());
    }

    private void setCustomInfraImages() {
        for (UserInfrastructureData.Data.InfrastructureDatum infrastructureDatum : infrastructureData) {
            if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("building")) {
                infraStructureImageList.add(R.drawable.ic_aaganwadi_building);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("creche")) {
                infraStructureImageList.add(R.drawable.ic_creche_house);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("electricity")) {
                infraStructureImageList.add(R.drawable.ic_electricity);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("toilet")) {
                infraStructureImageList.add(R.drawable.ic_toilet_new);
            } else if (infrastructureDatum.getTimInfraNamee().toLowerCase().contains("water")) {
                infraStructureImageList.add(R.drawable.ic_drinking_water);
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



