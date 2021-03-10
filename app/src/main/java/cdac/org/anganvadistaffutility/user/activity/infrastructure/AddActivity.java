package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.AddInfrastructureData;
import cdac.org.anganvadistaffutility.user.data.RemainingInfraDetailData;
import cdac.org.anganvadistaffutility.user.data.RemainingInfrastructureData;
import retrofit2.Call;

public class AddActivity extends BaseActivity implements View.OnClickListener {
    SmartMaterialSpinner<String> sp_beneficiary_criteria;
    private RelativeLayout relativeLayout;
    List<RemainingInfraDetailData.Data.Empdatum> empdata;
    private RemainingInfraDetailAdapter remainingInfraDetailAdapter;
    RecyclerView recyclerView;
    AppCompatButton btn_submit;
    private static String infra_id;
    private static String infra_detail_id;
    private static String quantity = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add);
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);

        btn_submit = findViewById(R.id.btn_submit);
        empdata = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sp_beneficiary_criteria = findViewById(R.id.sp_beneficiary_criteria);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getBeneficiaryCriteriaData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

        btn_submit.setOnClickListener(this);
    }

    private void getBeneficiaryCriteriaData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<RemainingInfrastructureData> call = apiInterface.remainingInfrastructureData(appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<RemainingInfrastructureData>() {
            @Override
            public void onSuccess(RemainingInfrastructureData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> addNewInfrastructureList = new ArrayList<>();
                List<RemainingInfrastructureData.Empdatum> leaveTypeItems = body.getData().getEmpdata();
                for (int i = 0; i < leaveTypeItems.size(); i++) {
                    String leaveType;

                    String status = leaveTypeItems.get(i).getTimAcceptStatus();
                    String Code = leaveTypeItems.get(i).getTimInfraId(); // I want to show this when Selected
                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = leaveTypeItems.get(i).getTimInfraNamee();
                    } else {
                        leaveType = leaveTypeItems.get(i).getTimInfraNameh();
                    }

                    addNewInfrastructureList.add(leaveType);
                }
                sp_beneficiary_criteria.setItem(addNewInfrastructureList);
                sp_beneficiary_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        appPreferences.setStatus(leaveTypeItems.get(position).getTimAcceptStatus());
                        //     Toast.makeText(context, "" + appPreferences.getStatus(), Toast.LENGTH_SHORT).show();
                        if (AppUtils.isNetworkConnected(context)) {
                            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                            getInfraDetailData(leaveTypeItems.get(position).getTimInfraId());
                            if (infra_id != null) {
                                infra_id.equals("");
                            }
                            if (infra_detail_id != null) {
                                infra_detail_id.equals("");
                            }
                            if (quantity != null) {
                                quantity.equals("");
                            }
                        } else {
                            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }

    private void getInfraDetailData(String timInfraId) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<RemainingInfraDetailData> call = apiInterface.remainingInfraDetailData(appPreferences.getAwcId(), timInfraId);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<RemainingInfraDetailData>() {
            @Override
            public void onSuccess(RemainingInfraDetailData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                empdata = new ArrayList<>();
                empdata = body.getData().getEmpdata();

                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                empdata = new ArrayList<>();
                empdata = body.getData().getEmpdata();
                remainingInfraDetailAdapter = new RemainingInfraDetailAdapter(context, empdata);
                recyclerView.setAdapter(remainingInfraDetailAdapter);

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                addInfrastructure();
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        }
    }

    private void addInfrastructure() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<AddInfrastructureData> call = apiInterface.addInfrastructureData(appPreferences.getAwcId(), infra_id, infra_detail_id, quantity);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AddInfrastructureData>() {
            @Override
            public void onSuccess(AddInfrastructureData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                Toast.makeText(context, getResources().getString(R.string.infra_added_successfully), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, AvailableInfraDetailsActivity.class));
                finish();

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }


    private static class RemainingInfraDetailAdapter extends RecyclerView.Adapter<MyViewHolder> {
        Context context;
        List<RemainingInfraDetailData.Data.Empdatum> empdata;
        private CheckBox lastChecked = null;
        private int lastCheckedPos = 0;
        private final int selectedPosition = -1;// no selection by default
        MyViewHolder myViewHolder;


        public RemainingInfraDetailAdapter(Context context, List<RemainingInfraDetailData.Data.Empdatum> empdata) {
            this.context = context;
            this.empdata = empdata;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.remaininginfradetailrv, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ArrayList<String> selectedStrings = new ArrayList<String>();
            holder.setData(context, empdata.get(position));
            myViewHolder = holder;
            empdata.get(position);

            holder.checkBox.setTag(position);
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;

            if (appPreferences.getStatus().equals("1")) {

                holder.edtx_qty.setVisibility(View.GONE);

            } else {
                holder.edtx_qty.setVisibility(View.VISIBLE);
            }
        }




        @Override
        public int getItemCount() {
            return empdata.size();
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView item_name;
        private RemainingInfraDetailData.Data.Empdatum empdatum;
        CheckBox checkBox;
        AppCompatEditText edtx_qty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_tv);
            checkBox = itemView.findViewById(R.id.checkbox);
            edtx_qty = itemView.findViewById(R.id.qty_edtx);
            edtx_qty.setEnabled(false);
        }

        public void setData(Context context, RemainingInfraDetailData.Data.Empdatum empdatum) {
            this.empdatum = empdatum;
            String name = "";
            String qty = "";

            if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
               // name = empdatum.getTidInfraNamee();
                item_name.setText(empdatum.getTidInfraNamee());
            } else {
                item_name.setText(empdatum.getTidInfraNameh());

            }
            //item_name.setText(name);
        }
    }
}