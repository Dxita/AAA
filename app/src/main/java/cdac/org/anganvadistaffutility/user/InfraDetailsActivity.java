package cdac.org.anganvadistaffutility.user;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import cdac.org.anganvadistaffutility.user.activity.infrastructure.AddActivity;
import cdac.org.anganvadistaffutility.user.activity.infrastructure.EditActivity;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;
import retrofit2.Call;

public class InfraDetailsActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;

    RecyclerView recyclerView;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;

   public static String infra_id, item_nameE, item_nameH,tim_accept_status;
    AppCompatButton add, edit;
    AwcBuildingAdapter awcBuildingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_infra_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                infra_id = null;
                item_nameE = null;
                item_nameH = null;

                tim_accept_status=null;
            } else {
                infra_id = extras.getString("infra_id");
                item_nameH = extras.getString("item_nameH");
                item_nameE = extras.getString("item_nameE");
                tim_accept_status=extras.getString("tim_accept_status");
                Log.d("id", infra_id);
                Log.d("E", item_nameE);

            }
        } else {
            infra_id = (String) savedInstanceState.getSerializable("infra_id");
            item_nameH = (String) savedInstanceState.getSerializable("item_nameH");
            item_nameE = (String) savedInstanceState.getSerializable("item_nameE");
            tim_accept_status= (String) savedInstanceState.getSerializable("tim_accept_status");
        }

        if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(item_nameH);

        } else {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(item_nameE);
        }
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);
        add = findViewById(R.id.add);
        edit = findViewById(R.id.edit);
        infrastructureData = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAanganwadiBuildingData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }


         add.setOnClickListener(this);
        edit.setOnClickListener(this);

    }

    private void getAanganwadiBuildingData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<AanganwadiBuildingData> call = apiInterface.aanganwadiBuildingData(infra_id, appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AanganwadiBuildingData>() {
            @Override
            public void onSuccess(AanganwadiBuildingData body) {

                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();
                awcBuildingAdapter = new AwcBuildingAdapter(context, infrastructureData);
                recyclerView.setAdapter(awcBuildingAdapter);
                appPreferences.setInfraId(infrastructureData.get(0).getTidTimInfraId());
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
        if (v.getId() == R.id.add) {
            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                gotoAddScreen();
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        }

        if (v.getId() == R.id.edit) {
            startActivity(new Intent(context, EditActivity.class).putExtra("infra_id", infra_id).putExtra("tim_accept_status",tim_accept_status));
        }
       /* if (v.getId() == R.id.edit) {
            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                gotoEditScreen();
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        }*/
    }

    private void gotoEditScreen() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<AanganwadiBuildingData> call = apiInterface.aanganwadiBuildingData(infra_id, appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AanganwadiBuildingData>() {
            @Override
            public void onSuccess(AanganwadiBuildingData body) {

                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();
                startActivity(new Intent(context, EditActivity.class).putExtra("infra_id", infra_id));
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }

    private void gotoAddScreen() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<AanganwadiBuildingData> call = apiInterface.aanganwadiBuildingData(infra_id, appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AanganwadiBuildingData>() {
            @Override
            public void onSuccess(AanganwadiBuildingData body) {
              ;
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();
                startActivity(new Intent(context, AddActivity.class));
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }


    public static class AwcBuildingAdapter extends RecyclerView.Adapter<AwcBuildingAdapter.MyViewHolders> {
        Context context;
        List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
        MyViewHolders myViewHolders;
        private CheckBox lastChecked = null;
        private int lastCheckedPos = 0;

       private int VIEW_TYPE_ONE = 1;
       private int VIEW_TYPE_TWO = 2;


        public AwcBuildingAdapter(Context context, List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData) {
            this.context = context;
            this.infrastructureData = infrastructureData;
        }

        @NonNull
        @Override
        public MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                        View view = LayoutInflater.from(context).inflate(R.layout.toilet_rv_items, null);
                        return new AwcBuildingAdapter.MyViewHolders(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolders holder, int position) {
            myViewHolders = holder;
            infrastructureData.get(position);

            holder.checkBox.setTag(position);
            if (infrastructureData.get(position).getStatus().equalsIgnoreCase("yes")) {
                lastChecked = holder.checkBox;
                lastCheckedPos = 0;
                holder.checkBox.setChecked(true);

            }
            holder.setData(context, infrastructureData.get(position));

            if (tim_accept_status.equals("1")){
                holder.edtx_qty.setVisibility(View.GONE);
            }
            else {
                holder.edtx_qty.setVisibility(View.VISIBLE);
            }


        }


        @Override
        public int getItemCount() {
            return infrastructureData.size();

        }

        static class MyViewHolders extends RecyclerView.ViewHolder {
            AppCompatTextView item_name;
            private AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData;
            CheckBox checkBox;
            AppCompatEditText edtx_qty;
            public MyViewHolders(@NonNull View itemView) {
                super(itemView);

                item_name = itemView.findViewById(R.id.item_tv);
                checkBox = itemView.findViewById(R.id.checkbox);
                edtx_qty = itemView.findViewById(R.id.qty_edtx);
                checkBox.setClickable(false);
                edtx_qty.setFocusable(false);
                edtx_qty.setCursorVisible(false);
                edtx_qty.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                edtx_qty.setClickable(false); // user navigation
            }


            public void setData(Context context, AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData) {
                this.infrastructureData = infrastructureData;
                String name = "";
                String qty = "";

                if (tim_accept_status.equals("2")){

                    qty = infrastructureData.getTjaidQty();
                }
                else {
                    qty="";
                }

                if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                    name = infrastructureData.getTidInfraNamee();
                } else {
                    name = infrastructureData.getTidInfraNameh();
                }
                //  name = infrastructureData.getTidInfraNamee();
                item_name.setText(name);
                edtx_qty.setText(qty);
            }
        }


    }


}