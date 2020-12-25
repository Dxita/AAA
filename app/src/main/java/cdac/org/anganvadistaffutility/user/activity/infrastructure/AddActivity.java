package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.preferences.AppPreferences;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.activity.beneficiary.MotherMappingActivity;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;
import cdac.org.anganvadistaffutility.user.data.RemainingInfraDetailData;
import cdac.org.anganvadistaffutility.user.data.RemainingInfrastructureData;
import retrofit2.Call;

public class AddActivity extends BaseActivity {
    SmartMaterialSpinner<String> sp_beneficiary_criteria;
    private RelativeLayout relativeLayout;
    List<RemainingInfraDetailData.Data.Empdatum> empdata;
    RemainingInfraDetailAdapter remainingInfraDetailAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add);
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);

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
    }

    private void getBeneficiaryCriteriaData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.AVAIL_INFRA_DETAILS_URL);
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
                        Toast.makeText(context, "" + appPreferences.getStatus(), Toast.LENGTH_SHORT).show();
                        if (AppUtils.isNetworkConnected(context)) {
                            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                            getInfraDetailData(leaveTypeItems.get(position).getTimInfraId());
                        } else {
                            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                        }
                        //  Toast.makeText(context,leaveTypeItems.get(position).getTimInfraId(), Toast.LENGTH_SHORT).show();
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
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.AVAIL_INFRA_DETAILS_URL);
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


    private static class RemainingInfraDetailAdapter extends RecyclerView.Adapter<MyViewHolder> {
        Context context;
        List<RemainingInfraDetailData.Data.Empdatum> empdata;
        private CheckBox lastChecked = null;
        private int lastCheckedPos = 0;
        private int selectedPosition;// no selection by default
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

            myViewHolder = holder;
            empdata.get(position);

            holder.checkBox.setTag(position);
            holder.edtx_qty.setTag(position);
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;

         /*
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        int clickedPos = (Integer) cb.getTag();
                        if (cb.isChecked()) {
                            if (lastChecked != null) {
                                lastChecked.setChecked(false);
                                holder.edtx_qty.setText("1");
                            }
                            lastChecked = cb;
                            lastCheckedPos = clickedPos;

                            Toast.makeText(context, empdata.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();

                        } else
                            lastChecked = null;
                    }
                });

            */

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selectedStrings.add(holder.checkBox.getText().toString());

                        holder.edtx_qty.setEnabled(true);
                        holder.edtx_qty.setText("1");
                        holder.edtx_qty.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String str = holder.edtx_qty.getText().toString();
                                Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show();
                                //   quantity_edtx = Objects.requireNonNull(holder.edtx_qty.getText()).toString();
                                //  Toast.makeText(context, ""+quantity_edtx, Toast.LENGTH_SHORT).show();
                                // Place the logic here for your output edittext
                            }
                        });

                    } else {
                        selectedStrings.remove(holder.checkBox.getText().toString());
                    }

                }
            });


            holder.setData(context, empdata.get(position));

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
                name = empdatum.getTidInfraNamee();
            } else {
                name = empdatum.getTidInfraNameh();
            }
            //  name = infrastructureData.getTidInfraNamee();
            item_name.setText(name);
        }
    }
}