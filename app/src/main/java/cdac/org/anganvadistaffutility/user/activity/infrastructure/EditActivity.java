package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import cdac.org.anganvadistaffutility.user.InfraDetailsActivity;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.user.data.UpdateInfrastructureData;
import cdac.org.anganvadistaffutility.user.data.UserInfrastructureData;
import retrofit2.Call;

public class EditActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    AppCompatButton submit;
    RecyclerView recyclerView;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
    AwcBuildingAdapter awcBuildingAdapter;
    public static String infra_id, item_nameE, item_nameH, tim_accept_status, infradetail_id,last_infra_detail_id;
    public static String quantity_edtx="";
  /*  AppCompatEditText facility, quantity, Availbility_in_Infrastructure;
    String infra_id;
    private RelativeLayout relativeLayout;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
    private List<UserInfrastructureData.Data.InfrastructureDatum> infrastructureDatum;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                infra_id = null;
                item_nameE = null;
                item_nameH = null;
                tim_accept_status = null;
            } else {
                infra_id = extras.getString("infra_id");
                item_nameH = extras.getString("item_nameH");
                item_nameE = extras.getString("item_nameE");
                tim_accept_status = extras.getString("tim_accept_status");

            }
        } else {
            infra_id = (String) savedInstanceState.getSerializable("infra_id");
            item_nameH = (String) savedInstanceState.getSerializable("item_nameH");
            item_nameE = (String) savedInstanceState.getSerializable("item_nameE");
            tim_accept_status = (String) savedInstanceState.getSerializable("tim_accept_status");
        }


        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);
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


                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();
                awcBuildingAdapter = new AwcBuildingAdapter(context, infrastructureData);
                recyclerView.setAdapter(awcBuildingAdapter);
                appPreferences.setInfraId(infrastructureData.get(0).getTidTimInfraId());

                last_infra_detail_id=infrastructureData.get(0).getTidInfraDetailId();
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
        if (v.getId()==R.id.submit){
            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                updateInfrastructure();
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        }
    }

    private void updateInfrastructure() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<UpdateInfrastructureData> call = apiInterface.updateInfrastructureData(infra_id,appPreferences.getAwcId(),tim_accept_status,quantity_edtx,infradetail_id,last_infra_detail_id);
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


    public static class AwcBuildingAdapter extends RecyclerView.Adapter<AwcBuildingAdapter.MyViewHolders> {
        Context context;
        List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
        AwcBuildingAdapter.MyViewHolders myViewHolders;
        private CheckBox lastChecked = null;
        private int lastCheckedPos = 0;
        public static String quantity_edtx;
        private int selectedPosition;// no selection by default

        public AwcBuildingAdapter(Context context, List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData) {
            this.context = context;
            this.infrastructureData = infrastructureData;
        }

        @NonNull
        @Override
        public AwcBuildingAdapter.MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.toilet_rv_items, null);
            return new AwcBuildingAdapter.MyViewHolders(view);

        }

        @Override
        public void onBindViewHolder(@NonNull AwcBuildingAdapter.MyViewHolders holder, int position) {
            myViewHolders = holder;
            infrastructureData.get(position);



            holder.checkBox.setTag(position);
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;

            if (holder.checkBox.isChecked()) {


            }
          /*  if (infrastructureData.get(position).getStatus().equalsIgnoreCase("yes")) {
                lastChecked = holder.checkBox;
                lastCheckedPos = 0;
                holder.checkBox.setChecked(true);

            }*/
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  if (tim_accept_status.equals("1")) {
                    final boolean isChecked = holder.checkBox.isChecked();

                    String qt;
                    CheckBox cb = (CheckBox) view;
                    int clickedPos = (Integer) cb.getTag();
                    if (cb.isChecked()) {

                        if (lastChecked != null) {
                            lastChecked.setChecked(false);
                        }
                        lastChecked = cb;
                        lastCheckedPos = clickedPos;
                        infradetail_id=infrastructureData.get(position).getTidInfraDetailId();

                        Toast.makeText(context, infrastructureData.get(position).getTidInfraDetailId() + "", Toast.LENGTH_SHORT).show();

                        if (tim_accept_status.equalsIgnoreCase("2")){
                            String str= Objects.requireNonNull(holder.edtx_qty.getText()).toString();
                            quantity_edtx=str;
                            Log.d("q",str);
                        }
                        else {
                            quantity_edtx ="0";

                        }
                        //   Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        quantity_edtx ="0";
                        lastChecked = null;
                    }
                  /*  if (tim_accept_status.equals("1")){
                        quantity_edtx="0";
                    }
                    if(tim_accept_status.equals("2")){
                        quantity_edtx=holder.edtx_qty.getText().toString();
                    }*/

                }
            });


            holder.edtx_qty.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    quantity_edtx="0";

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(tim_accept_status.equals("2")){
                        quantity_edtx= Objects.requireNonNull(holder.edtx_qty.getText()).toString();
                    }
                    else {
                        quantity_edtx="0";
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

                //...
            });
            holder.setData(context, infrastructureData.get(position));

            if (tim_accept_status.equals("1")) {

                holder.edtx_qty.setVisibility(View.GONE);

            } else {
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
                // checkBox.setClickable(false);
            }

            public void setData(Context context, AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData) {
                this.infrastructureData = infrastructureData;
                String name = "";
                String qty = "";

                if (tim_accept_status.equals("2")) {

                    qty = infrastructureData.getTjaidQty();
                } else {
                    qty = "";
                }

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

}