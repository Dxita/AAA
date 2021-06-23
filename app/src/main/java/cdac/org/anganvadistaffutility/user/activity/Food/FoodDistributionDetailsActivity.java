package cdac.org.anganvadistaffutility.user.activity.Food;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

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
import cdac.org.anganvadistaffutility.user.data.FoodConsolidateData;
import cdac.org.anganvadistaffutility.user.data.FoodNameData;
import cdac.org.anganvadistaffutility.user.data.SaveFFoodRequirement;
import retrofit2.Call;

public class FoodDistributionDetailsActivity extends BaseActivity implements View.OnClickListener {
    SmartMaterialSpinner<String> sp_beneficiary_criteria;
    private RelativeLayout relativeLayout;
    AppCompatEditText food_supplier_name, food_category, food_unit, food_price, food_qty, approved_qty;
    private FoodConsolidateData.Fooddata data;
    private List<FoodNameData.Foodname> leaveTypeItems;
    private AppCompatButton save_btn;
    String awc_id, photo, day, week, verified_beneficiary, unverified_beneifciary;
    int foodQty;
    private int approvedqty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_food_distribution_details);

        relativeLayout = findViewById(R.id.relativeLayout);
        sp_beneficiary_criteria = findViewById(R.id.sp_beneficiary_cat);
        food_supplier_name = findViewById(R.id.food_supplier_name);
        food_category = findViewById(R.id.food_category);
        food_price = findViewById(R.id.food_price);
        food_unit = findViewById(R.id.food_unit);
        food_qty = findViewById(R.id.qty);
        approved_qty = findViewById(R.id.approved_qty);
        save_btn = findViewById(R.id.save);

        save_btn.setOnClickListener(this);

//        if (savedInstanceState == null) {
        //Bundle extras = getIntent().getExtras();
        if (getIntent() == null) {
            awc_id = "";
            photo = "";
            day = "";
            week = "";
            verified_beneficiary = "";
            unverified_beneifciary = "";

        } else {
            awc_id = getIntent().getStringExtra("awc_id");
            photo = getIntent().getStringExtra("photo");
            day = getIntent().getStringExtra("day");
            week = getIntent().getStringExtra("week");
            verified_beneficiary = getIntent().getStringExtra("verified_beneficiary");
            unverified_beneifciary = getIntent().getStringExtra("unverified_beneifciary");

            Log.e("Response_000", "AWC: " + awc_id + "\nPHOTO: " + photo + "\n" + "DAY: " + day + "\nWEEK: " + week + "\nVB: " + verified_beneficiary + "\nUB" + unverified_beneifciary);
        }
//        } else {
//            awc_id= (String) savedInstanceState.getSerializable("awc_id");
//            photo= (String) savedInstanceState.getSerializable("photo");
//            day= (String) savedInstanceState.getSerializable("day");
//            week= (String) savedInstanceState.getSerializable("week");
//            verified_beneficiary= (String) savedInstanceState.getSerializable("verified_beneficiary");
//            unverified_beneifciary= (String) savedInstanceState.getSerializable("unverified_beneifciary");
//        }

//        Log.d("awc_id",awc_id);
//        Log.d("day",day);
//        Log.d("week",week); Log.d("photo",photo);
//        Log.d("verified_beneficiary",verified_beneficiary);
//        Log.d("unverified_beneifciary",unverified_beneifciary);


        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getFoodName();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

    }

    private void getFoodName() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<FoodNameData> call = apiInterface.getFoodName();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<FoodNameData>() {
            @Override
            public void onSuccess(FoodNameData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> addNewInfrastructureList = new ArrayList<>();
                leaveTypeItems = body.getData().getFoodname();
                for (int i = 0; i < leaveTypeItems.size(); i++) {
                    String leaveType;


                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = leaveTypeItems.get(i).getTfnNamee();
                    } else {
                        leaveType = leaveTypeItems.get(i).getTfnNameh();
                    }

                    addNewInfrastructureList.add(leaveType);
                }
                sp_beneficiary_criteria.setItem(addNewInfrastructureList);
                sp_beneficiary_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                        String food_name_id = leaveTypeItems.get(position).getTfnId();
                        Log.e("food_name_id", food_name_id);
                        //Toast.makeText(context, "" + leaveTypeItems.get(position).getTfnId(), Toast.LENGTH_SHORT).show();

                        if (AppUtils.isNetworkConnected(context)) {
                            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                            getFoodData(food_name_id);
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

    private void getFoodData(String food_name_id) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<FoodConsolidateData> call = apiInterface.getFoodData(food_name_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<FoodConsolidateData>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(FoodConsolidateData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);

                if (body.getStatus().equalsIgnoreCase("false")) {
                    Toast.makeText(context, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();

                } else {
                    data = body.getData().getFooddata();
                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        food_supplier_name.setText(data.getTfsmSupplierNamee());
                        food_category.setText(data.getCatname());
                        food_unit.setText(data.getUnit());
                        food_price.setText(getResources().getString(R.string.Rs) + "" + data.getTfsdPrice());
                        approved_qty.setText(data.getQty());
                    } else {
                        food_supplier_name.setText(data.getTfsmSupplierNameh());
                        food_category.setText(data.getCatnameh());
                        food_unit.setText(data.getUnit());
                        food_price.setText(getResources().getString(R.string.Rs) + "" + data.getTfsdPrice());
                        approved_qty.setText(data.getQty());
                    }

                }

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.save) {
            foodQty = Integer.parseInt(Objects.requireNonNull(food_qty.getText()).toString().trim());
            approvedqty = Integer.parseInt(Objects.requireNonNull(approved_qty.getText()).toString().trim());
            String supplier_id, food_id, cat_id, unit_id;
            supplier_id = data.getSupplierId();
            food_id = data.getTfnId();
            cat_id = data.getCatid();
            unit_id = data.getUnitId();

            if (foodQty >= approvedqty + 1) {
                food_qty.setText("0");
                food_qty.requestFocus();
                food_qty.setError(getResources().getString(R.string.count_validation));
            } else {
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    saveFoodDistributionApi(awc_id, day, week, photo, verified_beneficiary, unverified_beneifciary, String.valueOf(foodQty), food_id, supplier_id, cat_id, unit_id);
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }

            }
        }
    }

    private void saveFoodDistributionApi(String awc_id, String day, String week, String photo, String verified_beneficiary, String unverified_beneifciary, String foodQty, String food_id, String supplier_id, String cat_id, String unit_id) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<SaveFFoodRequirement> call = apiInterface.saveFoodReequirement(awc_id, day, week, photo, verified_beneficiary, unverified_beneifciary, foodQty, food_id, supplier_id, cat_id, unit_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<SaveFFoodRequirement>() {
            @Override
            public void onSuccess(SaveFFoodRequirement body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                Toast.makeText(FoodDistributionDetailsActivity.this, ""+body.getMessage(), Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }

        }));
    }
}