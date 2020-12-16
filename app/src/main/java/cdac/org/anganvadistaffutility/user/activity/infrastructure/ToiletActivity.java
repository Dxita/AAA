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
import cdac.org.anganvadistaffutility.user.data.Model;
import cdac.org.anganvadistaffutility.user.data.UpdateInfrastructureData;
import retrofit2.Call;

public class ToiletActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
    String infra_id;
    AppCompatButton submit_btn, update_btn, cancel_btn;
    ToiletAdapter awcBuildingAdapter;
    public static String item;
    public static String qantity;
    String tim_infra_id;
    String itemsData = "", quantityData = "";
    //   public List<Model> mModelList;
    ArrayList<String> arrayListitems = new ArrayList<>();
    ArrayList<String> arrayListquantity = new ArrayList<>();
    ArrayList<String> last_item_position = new ArrayList<>();
    ArrayList<String> last_quantity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_toilet);
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
            getData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

    }

    private void getData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<AanganwadiBuildingData> call = apiInterface.aanganwadiBuildingData(infra_id, appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AanganwadiBuildingData>() {
            @Override
            public void onSuccess(AanganwadiBuildingData body) {
                Toast.makeText(context, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                infrastructureData = new ArrayList<>();
                infrastructureData = body.getData().getInfrastructureData();
                awcBuildingAdapter = new ToiletAdapter(context, infrastructureData);
                recyclerView.setAdapter(awcBuildingAdapter);
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
            //  update_btn.setVisibility(View.VISIBLE);
            //submit_btn.setVisibility(View.GONE);
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
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<UpdateInfrastructureData> call = apiInterface.updateInfrastructureData(appPreferences.getAwcId(), tim_infra_id,
                itemsData, quantityData, String.valueOf(last_item_position), String.valueOf(last_quantity));
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


    public class ToiletAdapter extends RecyclerView.Adapter<ToiletAdapter.MyViewHolders> {
        Context context;
        List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
        private final CheckBox lastChecked = null;
        private final int lastCheckedPos = 0;

        public ToiletAdapter(Context context, List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData) {
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

            //saving last checked position into arraylist
            for (int j = 0; j < infrastructureData.size(); j++) {
                if (infrastructureData.get(position).getStatus().equalsIgnoreCase("yes")) {

                    if (!(last_item_position == null)) {
                        last_item_position.add(infrastructureData.get(position).getTidInfraNamee());
                        Log.d("quant", String.valueOf(last_item_position));
                    } else {
                        infrastructureData.get(position).getTidInfraDetailId();
                    }

                    if (!(last_quantity == null)) {
                        last_quantity.add(infrastructureData.get(position).getTjaidQty());

                    } else {
                        infrastructureData.get(position).getTjaidQty();
                    }

                    holder.checkBox.setChecked(true);
                    Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();
                }
            }


            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    final boolean isChecked = holder.checkBox.isChecked();

                    CheckBox cb = (CheckBox) arg0;
                    int clickedPos = (Integer) cb.getTag();
                    if (cb.isChecked()) {

                        //   Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();
                    } else {
                    }

                    //saving data in arraylist
                    for (int i = 0; i < infrastructureData.size(); i++) {
                        if (isChecked) {

                            if (!arrayListitems.contains(infrastructureData.get(position).getTidInfraDetailId())) {

                                arrayListitems.add(i, infrastructureData.get(position).getTidInfraDetailId());
                                itemsData = arrayListitems.toString().replace("[", "").replace("]", "").trim();

                            } else {
                                itemsData = infrastructureData.get(position).getTidInfraDetailId();
                            }
                        } else {
                            arrayListitems.remove(infrastructureData.get(position).getTidInfraNamee());
                            itemsData = arrayListitems.toString().replace("[", "").replace("]", "").trim();
                        }

                    }
                }
            });

           /* holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    int clickedPos = (Integer) cb.getTag();
                    if (cb.isChecked()) {

                *//*     item_list.add(Integer.valueOf(item));
                        Log.d("item", String.valueOf(item));*//*
                        //   Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();
                    } else {
                        item_list.remove(Integer.valueOf(item));
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

            //getting quantaties in list
            qantity = Objects.requireNonNull(holder.edtx_qty.getText()).toString().trim();
            for (int k = 0; k < infrastructureData.size(); k++) {
                if (!arrayListquantity.contains(infrastructureData.get(position).getTjaidQty())) {

                    arrayListquantity.add(k, qantity);
                    quantityData = arrayListquantity.toString().replace("[", "").replace("]", "").trim();
                    Log.d("aahhjxnjjhcj", quantityData);
                } else {
                    quantityData = infrastructureData.get(position).getTjaidQty();
                }
            }

        /*    for (int i = 0; i < infrastructureData.size(); i++) {
                if (isChecked) {

                    if (!arrayListitems.contains(infrastructureData.get(position).getTidInfraNamee()))
                        arrayListitems.add(i, infrastructureData.get(position).getTidInfraNamee());
                    itemsData = arrayListitems.toString().replace("[", "").replace("]", "").trim();
                    Log.d("aahhjxnjjhcj", itemsData);
                } else {
                    arrayListitems.remove(infrastructureData.get(position).getTidInfraNamee());
                    itemsData = arrayListitems.toString().replace("[", "").replace("]", "").trim();
                }

            }*/


            // item_list.add(Integer.valueOf(item));
            //Log.d("arraylist", String.valueOf(item_list));


        }

        @Override
        public int getItemCount() {
            return infrastructureData.size();
        }

        class MyViewHolders extends RecyclerView.ViewHolder {
            AppCompatTextView item_name;
            private AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData;
            CheckBox checkBox;
            AppCompatEditText edtx_qty;
            AppCompatImageView edit_icon;

            public MyViewHolders(@NonNull View itemView) {
                super(itemView);
                item_name = itemView.findViewById(R.id.item_tv);
                checkBox = itemView.findViewById(R.id.checkbox);
                edtx_qty = itemView.findViewById(R.id.qty_edtx);
                edit_icon = itemView.findViewById(R.id.edit_icon);
                edtx_qty.setFocusable(false);
                edtx_qty.setCursorVisible(false);
                edtx_qty.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                edtx_qty.setClickable(false); // user navigation

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