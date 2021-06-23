package cdac.org.anganvadistaffutility.user.activity.Food;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.BeneficiaryCriteria;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySubCategory;
import cdac.org.anganvadistaffutility.user.data.VerifiedUnverifiedBeneficiaryData;
import retrofit2.Call;

public class FoodDistributionActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText awc_id, awc_name, year, verifiedbeneficiary, notverifiedbeneficiary;
    RelativeLayout relativeLayout;
    CheckBox cb_accept_beneficiary;
    LinearLayout unverified_layout, spinner_layout, upload_img_layout;
    AppCompatButton next_btn, upload_btn;
    RadioButton rb_day, rb_week, rb_month;
    AppCompatImageView imageView;
    AppCompatTextView actual_count, month;
    private static List<BeneficiaryCriteria.Beneficiary> leaveTypeItems;
    private static List<BeneficiarySubCategory.InfrastructureDatum> leaveTypeItem;
    SmartMaterialSpinner<String> sp_beneficiary_criteria, sp_beneficiary_sub_cat, week_sp;
    private  String cat_id, verified_beneficiary, newString;
    private int unverified_beneficiary;
    private final int GALLERY = 1;
    RadioGroup radioGroup;
    int unv_beneficiary;
    Bitmap photo;
    String image = "";


    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_food_distribution);

        relativeLayout = findViewById(R.id.relativeLayout);
        awc_id = findViewById(R.id.awc_id);
        awc_name = findViewById(R.id.awc_name);
        month = findViewById(R.id.month);

        next_btn = findViewById(R.id.add);
        spinner_layout = findViewById(R.id.spinner_layout);
        rb_day = findViewById(R.id.rb_day);
        upload_btn = findViewById(R.id.upload_btn);
        imageView = findViewById(R.id.image);
        rb_week = findViewById(R.id.rb_week);
        actual_count = findViewById(R.id.actual_count);
        cb_accept_beneficiary = findViewById(R.id.cb_accept_beneficiary);
        unverified_layout = findViewById(R.id.unverified_layout);
        sp_beneficiary_criteria = findViewById(R.id.sp_beneficiary_cat);
        week_sp = findViewById(R.id.week_sp);
        verifiedbeneficiary = findViewById(R.id.verified_beneficiary);
        sp_beneficiary_sub_cat = findViewById(R.id.sp_beneficiary_sub_cat);
        upload_img_layout = findViewById(R.id.upload_img_layout);
        upload_btn.setOnClickListener(this);
        awc_id.setText(appPreferences.getAwcId());
        if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
            awc_name.setText(appPreferences.getAwcnamee());
        } else {
            awc_name.setText(appPreferences.getAwcnameh());
        }
        notverifiedbeneficiary = findViewById(R.id.extra_beneficiary);

        cb_accept_beneficiary.setOnClickListener(this);


        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getBeneficiaryStatus();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }


        Toast.makeText(context, "" + currentWeek(), Toast.LENGTH_SHORT).show();

        rb_day.setChecked(true);
        if (rb_day.isChecked()) {
            month.setText(getCurrentDate());
        }
        next_btn.setOnClickListener(this);
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }

    private String getMonthAndYear() {
        return new SimpleDateFormat("MM-yyyy", Locale.getDefault()).format(new Date());
    }

    private String getMonth() {
        return new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
    }

    private List<String> listOfWeeksInAMonth() {
        List<String> list = new ArrayList<>();
        try {
            Calendar calendar = Calendar.getInstance();
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse("01-" + getMonthAndYear());
            calendar.setTime(date);
            int no_of_weeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
            for (int i = 0; i < no_of_weeks; i++) {
                if (i >= (currentWeek() - 1)) {
                    list.add(getMonth() + " "+getResources().getString(R.string.week)+" " + String.valueOf((i + 1)));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }


    private int currentWeek() {
        int no_of_week = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(getCurrentDate());
            calendar.setTime(date);
            no_of_week = calendar.get(Calendar.WEEK_OF_MONTH);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return no_of_week;

    }


    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_day:
                if (checked)
                    rb_week.setChecked(false);
                month.setVisibility(View.VISIBLE);
                spinner_layout.setVisibility(View.GONE);
                upload_img_layout.setVisibility(View.VISIBLE);
                month.setText(getCurrentDate());
                break;

            case R.id.rb_week:
                if (checked)
                    rb_day.setChecked(false);
                month.setVisibility(View.GONE);
                spinner_layout.setVisibility(View.VISIBLE);
                upload_img_layout.setVisibility(View.GONE);
                setWeekSpinner();

                break;

        }
    }

    private void setWeekSpinner() {
        week_sp.setItem(listOfWeeksInAMonth());
        week_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //      Code = leaveTypeItems.get(position).getTbmBeneficiaryId();

//                if (position >= (currentWeek() -1)){
//                    //view.setEnabled(true);
//                    view.setVisibility(View.VISIBLE);
//
//                } else {
//                   // view.setEnabled(false);
//                    view.setVisibility(View.GONE);
//                }
                String oldString = String.valueOf(week_sp.getSelectedItem());
                newString = oldString.replace(getMonth() + " Week ", "");
                Toast.makeText(context, newString, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getBeneficiaryStatus() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<VerifiedUnverifiedBeneficiaryData> call = apiInterface.getVerifiedUnverifiedBeneficiaryData(appPreferences.getAwcId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifiedUnverifiedBeneficiaryData>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(VerifiedUnverifiedBeneficiaryData body) {

                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                VerifiedUnverifiedBeneficiaryData.Beneficiaryinfo data = body.getData().getBeneficiaryinfo();

                verified_beneficiary = data.getVerifiedBeneficiary();

                verifiedbeneficiary.setText(data.getVerifiedBeneficiary());
                unverified_beneficiary = Integer.parseInt(data.getNotverifiedBeneficiary());

                actual_count.setText(getResources().getString(R.string.actual_count) + ":" + "" + unverified_beneficiary);

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));

    }


    private void showSubmitDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_view);
        dialog.setCancelable(false);
        dialog.show();


        Button okGotIt = (Button) dialog.findViewById(R.id.okbtn);
        Button not_ok = (Button) dialog.findViewById(R.id.notok);
        TextView dialog_verified_beneficiary = dialog.findViewById(R.id.verified_beneficiary);
        TextView dialog_unverified_beneficiary = dialog.findViewById(R.id.unverified_beneficiary);

        dialog_verified_beneficiary.setText(verified_beneficiary);
        dialog_unverified_beneficiary.setText(String.valueOf(unv_beneficiary));

        okGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, FoodDistributionDetailsActivity.class);
                intent.putExtra("awc_id", appPreferences.getAwcId()).putExtra("day", getCurrentDate()).putExtra("week", newString)
                        .putExtra("verified_beneficiary", verified_beneficiary).putExtra("unverified_beneifciary", unv_beneficiary);
                ;
                if (rb_day.isChecked()) {
                    image = compressedIntoBase64();
                } else {
                    image = "";
                }
                intent.putExtra("photo", image);
                startActivity(intent);
                finish();

                dialog.dismiss();
            }
        });

        not_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cb_accept_beneficiary) {
            if (cb_accept_beneficiary.isChecked()) {
                unverified_layout.setVisibility(View.VISIBLE);
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    getBeneficiaryCriteriaData();
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }
            } else if (!cb_accept_beneficiary.isChecked()) {

                unverified_layout.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.add) {

            unv_beneficiary = Integer.parseInt(Objects.requireNonNull(notverifiedbeneficiary.getText()).toString().trim());
            String month_str = Objects.requireNonNull(month.getText()).toString().trim();


            if (unv_beneficiary >= unverified_beneficiary + 1) {
                notverifiedbeneficiary.setText("0");
                notverifiedbeneficiary.requestFocus();
                notverifiedbeneficiary.setError(getResources().getString(R.string.count_validation));
            } else if (rb_week.isChecked()) {
                if (week_sp.getSelectedItem() == null) {
                    week_sp.setErrorText(getResources().getString(R.string.required_field));
                    return;
                } else {
                    showSubmitDialog();
                }

            } else if (cb_accept_beneficiary.isChecked()) {
                if (sp_beneficiary_criteria.getSelectedItem() == null) {
                    sp_beneficiary_criteria.setErrorText(getResources().getString(R.string.required_field));
                    return;
                } else if (sp_beneficiary_sub_cat.getSelectedItem() == null) {
                    sp_beneficiary_sub_cat.setErrorText(getResources().getString(R.string.required_field));
                    return;
                } else {
                    showSubmitDialog();
                }
            } else if (rb_day.isChecked()) {
                if (photo == null) {
                    Toast.makeText(context, getResources().getString(R.string.please_select_image), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    showSubmitDialog();
                }
            } else {
                showSubmitDialog();
            }


        } else if (v.getId() == R.id.upload_btn) {

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void getBeneficiaryCriteriaData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<BeneficiaryCriteria> call = apiInterface.getBeneficiaryCriteria();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<BeneficiaryCriteria>() {
            @Override
            public void onSuccess(BeneficiaryCriteria body) {
                //AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> beneficiaryCriteriaList = new ArrayList<>();

                leaveTypeItems = body.getData().getBeneficiary();
                for (int i = 0; i < leaveTypeItems.size(); i++) {
                    String leaveType;

                    //  Code = leaveTypeItems.get(i).getTbmBeneficiaryId(); // I want to show this when Selected
                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = leaveTypeItems.get(i).getTbmBeneficiaryNamee();
                    } else {
                        leaveType = leaveTypeItems.get(i).getTbmBeneficiaryNameh();
                    }

                    beneficiaryCriteriaList.add(leaveType);
                }


                sp_beneficiary_criteria.setItem(beneficiaryCriteriaList);

                sp_beneficiary_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        cat_id = leaveTypeItems.get(position).getTbmBeneficiaryId();
                        if (AppUtils.isNetworkConnected(context)) {
                            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                            getSubCategory(cat_id);
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
                //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void getSubCategory(String cat_id) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<BeneficiarySubCategory> call = apiInterface.getBeneficiarySubCategory("1");
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<BeneficiarySubCategory>() {
            @Override
            public void onSuccess(BeneficiarySubCategory body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> beneficiaryCriteriaList = new ArrayList<>();

                leaveTypeItem = body.getData().getInfrastructureData();
                for (int i = 0; i < leaveTypeItem.size(); i++) {
                    String leaveType;

                    //  Code = leaveTypeItems.get(i).getTbmBeneficiaryId(); // I want to show this when Selected
                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = leaveTypeItem.get(i).getTcsCategoryNamee();
                    } else {
                        leaveType = leaveTypeItem.get(i).getTcsCategoryNameh();
                    }

                    beneficiaryCriteriaList.add(leaveType);
                }


                sp_beneficiary_sub_cat.setItem(beneficiaryCriteriaList);

                sp_beneficiary_sub_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        String sub_cat_id = leaveTypeItem.get(position).getTcsCategoryId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            //photo = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }
    }

    private String compressedIntoBase64() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

}