package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
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
import cdac.org.anganvadistaffutility.user.data.UpdateInfrastructureData;
import retrofit2.Call;

public class WaterActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
    String infra_id;
    AppCompatButton submit_btn, update_btn, cancel_btn;
    DrinkingWaterAdapter drinkingWaterAdapter;
    public static String item;
    public static String qantity;
    String tim_infra_id;

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
        submit_btn = findViewById(R.id.submit_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

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
                tim_infra_id = infrastructureData.get(0).getTidTimInfraId();


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
            // update_btn.setVisibility(View.VISIBLE);
            //  submit_btn.setVisibility(View.GONE);
            //  Toast.makeText(context, "submitted", Toast.LENGTH_SHORT).show();

            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                updateInfrastructure();
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }

        }
        if (v.getId() == R.id.cancel_btn) {
            // update_btn.setVisibility(View.VISIBLE);
            //  submit_btn.setVisibility(View.GONE);
            finish();
        }
    }

    private void updateInfrastructure() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.UPDATE_INFRASTRUCTURE);
        Call<UpdateInfrastructureData> call = apiInterface.updateInfrastructureData(appPreferences.getAwcId(),tim_infra_id,item,qantity);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<UpdateInfrastructureData>() {
            @Override
            public void onSuccess(UpdateInfrastructureData body) {
                Toast.makeText(context, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
               /* infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();
                awcBuildingAdapter = new AwcBuildingAdapter(context, infrastructureData);
                recyclerView.setAdapter(awcBuildingAdapter);*/
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }

    public static class DrinkingWaterAdapter extends RecyclerView.Adapter<DrinkingWaterAdapter.MyViewHolders> {
        Context context;
        List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
        public CheckBox lastChecked = null;
        public int lastCheckedPos = 0;

        public DrinkingWaterAdapter(Context context, List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData) {
            this.context = context;
            this.infrastructureData = infrastructureData;
        }

        @NonNull
        @Override
        public MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(context).inflate(R.layout.toilet_rv_items, null);
            return new MyViewHolders(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolders holder, int position) {
            infrastructureData.get(position);
            holder.checkBox.setTag(position);

            if (infrastructureData.get(position).getStatus().equalsIgnoreCase("yes")) {
                lastChecked = holder.checkBox;
                lastCheckedPos = 0;
                holder.checkBox.setChecked(true);

            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    int clickedPos = (Integer) cb.getTag();
                    if (cb.isChecked()) {
                        if (lastChecked != null) {
                            lastChecked.setChecked(false);
                        }
                        lastChecked = cb;
                        lastCheckedPos = clickedPos;

                        Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();

                    } else
                        lastChecked = null;
                }
            });

        /*    if (infrastructureData.get(position).getStatus().equalsIgnoreCase("yes")) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    int clickedPos = (Integer) cb.getTag();
                    if (cb.isChecked()) {
                        //Toast.makeText(context, ""+clickedPos, Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

            holder.edit_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.edtx_qty.requestFocus();
                    holder.edtx_qty.setCursorVisible(true);
                    holder.edtx_qty.setFocusable(true);
                    holder.edtx_qty.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                    holder.edtx_qty.setClickable(true); // user navigate
                }
            });
            holder.setData(context, infrastructureData.get(position));


            item = infrastructureData.get(position).getTidInfraDetailId();

            if (!(holder.edtx_qty== null)){
                qantity = holder.edtx_qty.getText().toString();

            }
            else {
                qantity= infrastructureData.get(position).getTjaidQty();
            }


        }

        @Override
        public int getItemCount() {
            return infrastructureData.size();
        }


        public static class MyViewHolders extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            AppCompatEditText edtx_qty;
            AppCompatImageView edit_icon;
            AppCompatTextView item_name;

            private AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData;

            public MyViewHolders(@NonNull View itemView) {
                super(itemView);

                item_name = itemView.findViewById(R.id.item_tv);
                checkBox = itemView.findViewById(R.id.checkbox);
                edtx_qty = itemView.findViewById(R.id.qty_edtx);
                edit_icon = itemView.findViewById(R.id.edit_icon);
                edtx_qty.setFocusable(false);
                edtx_qty.setCursorVisible(false);
                edtx_qty.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                edtx_qty.setClickable(false); // user navigat
            }

            public void setData(Context context, AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData) {
                this.infrastructureData = infrastructureData;
                String name = "";
                String qty = "";

                if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                    name = infrastructureData.getTidInfraNamee();
                    qty = infrastructureData.getTjaidQty();


                } else {
                    name = infrastructureData.getTidInfraNameh();
                    qty = infrastructureData.getTjaidQty();
                }
                //  name = infrastructureData.getTidInfraNamee();
                item_name.setText(name);
                edtx_qty.setText(qty);
            }

        }


    }
}