package cdac.org.anganvadistaffutility.admin.igmpy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.igmpy.data.AwcList;
import cdac.org.anganvadistaffutility.admin.igmpy.data.DistList;
import cdac.org.anganvadistaffutility.admin.igmpy.data.ProjList;
import cdac.org.anganvadistaffutility.admin.igmpy.data.SearchResultData;
import cdac.org.anganvadistaffutility.admin.igmpy.data.SecList;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import retrofit2.Call;

public class VerifyStatusActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    String dist_id, proj_id, sec_id, awc_id;
    boolean m_name_,m_id,m_no,aadhar_no,acc_no,jan_no;
    private static SmartMaterialSpinner<String> sp_district, sp_project, sp_sector, sp_awc;
    private static List<DistList.IgmpyDistrictDatum> leaveTypeItems;
    private static List<ProjList.IgmpyProjectDatum> projectListItems;
    private static List<SecList.IgmpySectorDatum> sectrListItems;
    private static List<AwcList.IgmpyAwcDatum> awcListItems;
    private List<SearchResultData.IgmpyStatusDatum> datumList;

    private static RadioButton rb_1, rb_2, rb_3, rb_4, rb_5, rb_6;
    private static AppCompatEditText enter_value_here;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_status);
        AppCompatButton search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(this);

        sp_district = findViewById(R.id.sp_district);
        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getDistrictListApi();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

        sp_project = findViewById(R.id.sp_project);
        sp_sector = findViewById(R.id.sp_sector);
        sp_awc = findViewById(R.id.sp_awc);

        rb_1 = findViewById(R.id.rb_1);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);
        rb_4 = findViewById(R.id.rb_4);
        rb_5 = findViewById(R.id.rb_5);
        rb_6 = findViewById(R.id.rb_6);
        enter_value_here=findViewById(R.id.enter_value_here);

        rb_1.setOnClickListener(this);
        rb_2.setOnClickListener(this);
        rb_3.setOnClickListener(this);
        rb_4.setOnClickListener(this);
        rb_5.setOnClickListener(this);
        rb_6.setOnClickListener(this);


    }


    private void getDistrictListApi() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<DistList> call = apiInterface.getDistrictList();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<DistList>() {
            @Override
            public void onSuccess(DistList body) {
                //AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> beneficiaryCriteriaList = new ArrayList<>();

                leaveTypeItems = body.getData().getData().getIgmpyDistrictData();
                for (int i = 0; i < leaveTypeItems.size(); i++) {
                    String leaveType;

                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = leaveTypeItems.get(i).getDistnamee();
                    } else {
                        leaveType = leaveTypeItems.get(i).getDistnameh();
                    }

                    beneficiaryCriteriaList.add(leaveType);
                }


                sp_district.setItem(beneficiaryCriteriaList);

                sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        dist_id = leaveTypeItems.get(position).getDistid();
                        if (AppUtils.isNetworkConnected(context)) {
                            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                            getProjectListApi(dist_id);
                        } else {
                            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                //initBeneficiaryCriteriaSpinner(beneficiaryCriteriaList);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void getProjectListApi(String str_dist_id) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<ProjList> call = apiInterface.getProjectList(str_dist_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<ProjList>() {
            @Override
            public void onSuccess(ProjList body) {
                //AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> beneficiaryCriteriaList = new ArrayList<>();

                projectListItems = body.getData().getData().getIgmpyProjectData();
                for (int i = 0; i < projectListItems.size(); i++) {
                    String leaveType;

                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = projectListItems.get(i).getProjectname();
                    } else {
                        leaveType = projectListItems.get(i).getProjectnameh();
                    }

                    beneficiaryCriteriaList.add(leaveType);
                }


                sp_project.setItem(beneficiaryCriteriaList);

                sp_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        proj_id = projectListItems.get(position).getProjectcode();

                        if (AppUtils.isNetworkConnected(context)) {
                            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                            getSectorListApi(proj_id);
                        } else {
                            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                //initBeneficiaryCriteriaSpinner(beneficiaryCriteriaList);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void getSectorListApi(String str_proj_id) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<SecList> call = apiInterface.getSectorList(str_proj_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<SecList>() {
            @Override
            public void onSuccess(SecList body) {
                //AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> beneficiaryCriteriaList = new ArrayList<>();

                sectrListItems = body.getData().getData().getIgmpySectorData();
                for (int i = 0; i < sectrListItems.size(); i++) {
                    String leaveType;

                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = sectrListItems.get(i).getSecnamee();
                    } else {
                        leaveType = sectrListItems.get(i).getSecnameh();
                    }

                    beneficiaryCriteriaList.add(leaveType);
                }


                sp_sector.setItem(beneficiaryCriteriaList);

                sp_sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        sec_id = sectrListItems.get(position).getSectorid();

                        if (AppUtils.isNetworkConnected(context)) {
                            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                            getAwcList(sec_id);
                        } else {
                            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                //initBeneficiaryCriteriaSpinner(beneficiaryCriteriaList);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));

    }

    private void getAwcList(String str_sec_id) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<AwcList> call = apiInterface.getAwcList(str_sec_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AwcList>() {
            @Override
            public void onSuccess(AwcList body) {
                //AppUtils.showToast(context, body.getMessage());
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                List<String> beneficiaryCriteriaList = new ArrayList<>();

                awcListItems = body.getData().getData().getIgmpyAwcData();
                for (int i = 0; i < awcListItems.size(); i++) {
                    String leaveType;

                    if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                        leaveType = awcListItems.get(i).getAwcnamee();
                    } else {
                        leaveType = awcListItems.get(i).getAwcnameh();
                    }

                    beneficiaryCriteriaList.add(leaveType);
                }


                sp_awc.setItem(beneficiaryCriteriaList);

                sp_awc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        awc_id = awcListItems.get(position).getPawcid();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                //initBeneficiaryCriteriaSpinner(beneficiaryCriteriaList);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_button) {

            String ed_tx=Objects.requireNonNull(enter_value_here.getText()).toString();
            if (m_name_){
                fetch_search_data(ed_tx,"","","","","");
            } else if (m_id){
                fetch_search_data("",ed_tx,"","","","");
            } else if (m_no){
                fetch_search_data("","",ed_tx,"","","");
            } else if (aadhar_no){
                fetch_search_data("","","",ed_tx,"","");
            } else if (acc_no){
                fetch_search_data("","","","",ed_tx,"");
            } else if (jan_no){
                fetch_search_data("","","","","",ed_tx);
            }
//            else if (!m_name_ && !m_id && !m_no && !aadhar_no && !acc_no && !jan_no){
//                fetch_search_data("","","","","","");
//            }
            else{
                fetch_search_data("","","","","","");
            }

            //startActivity(new Intent(context, StatusResultActivity.class));
        }
       else if (v.getId() == R.id.rb_1) {

            enter_value_here.setVisibility(View.VISIBLE);
            enter_value_here.setHint(getResources().getString(R.string.mother_name));
            rb_2.setChecked(false);
            rb_3.setChecked(false);
            rb_4.setChecked(false);
            rb_5.setChecked(false);
            rb_6.setChecked(false);
            setValues(1);
        }
        else if (v.getId() == R.id.rb_2) {
            enter_value_here.setVisibility(View.VISIBLE);
            enter_value_here.setHint(getResources().getString(R.string.mother_id));
            rb_1.setChecked(false);
            rb_3.setChecked(false);
            rb_4.setChecked(false);
            rb_5.setChecked(false);
            rb_6.setChecked(false);
            setValues(2);
        }
        else if (v.getId() == R.id.rb_3) {
            enter_value_here.setVisibility(View.VISIBLE);
            enter_value_here.setHint(getResources().getString(R.string.mobile_no));
            rb_2.setChecked(false);
            rb_1.setChecked(false);
            rb_4.setChecked(false);
            rb_5.setChecked(false);
            rb_6.setChecked(false);
            setValues(3);
        }
        else if (v.getId() == R.id.rb_4) {
            enter_value_here.setVisibility(View.VISIBLE);
            enter_value_here.setHint(getResources().getString(R.string.aadhar_no));
            rb_2.setChecked(false);
            rb_3.setChecked(false);
            rb_1.setChecked(false);
            rb_5.setChecked(false);
            rb_6.setChecked(false);
            setValues(4);
        }
        else if (v.getId() == R.id.rb_5) {
            enter_value_here.setVisibility(View.VISIBLE);
            enter_value_here.setHint(getResources().getString(R.string.account_no));
            rb_2.setChecked(false);
            rb_3.setChecked(false);
            rb_4.setChecked(false);
            rb_1.setChecked(false);
            rb_6.setChecked(false);
            setValues(5);
        }
        else if (v.getId() == R.id.rb_6) {
            setValues(6);
            enter_value_here.setVisibility(View.VISIBLE);
            enter_value_here.setHint(getResources().getString(R.string.janaadhar_no));
            rb_2.setChecked(false);
            rb_3.setChecked(false);
            rb_4.setChecked(false);
            rb_5.setChecked(false);
            rb_1.setChecked(false);
        }
    }

    private void setValues(int key){
        switch (key){
            case 01:
                m_name_ = true;
                m_id = false;
                m_no = false;
                aadhar_no = false;
                acc_no = false;
                jan_no = false;
                break;
            case 02:
                m_name_ = false;
                m_id = true;
                m_no = false;
                aadhar_no = false;
                acc_no = false;
                jan_no = false;
                break;
            case 03:
                m_name_ = false;
                m_id = false;
                m_no = true;
                aadhar_no = false;
                acc_no = false;
                jan_no = false;
                break;
            case 04:
                m_name_ = false;
                m_id = false;
                m_no = false;
                aadhar_no = true;
                acc_no = false;
                jan_no = false;
                break;
            case 05:
                m_name_ = false;
                m_id = false;
                m_no = false;
                aadhar_no = false;
                acc_no = true;
                jan_no = false;
                break;
            case 06:
                m_name_ = false;
                m_id = false;
                m_no = false;
                aadhar_no = false;
                acc_no = false;
                jan_no = true;
                break;
        }
    }

    private void fetch_search_data(String m_name,String m_id, String m_no,String aadhar_no,String acc_no, String jan_no) {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
      /*  if (!dist_id.isEmpty() && dist_id.length() >0){

        }*/
        Call<SearchResultData> call = apiInterface.getSearchResult(dist_id,proj_id,sec_id,awc_id,m_name,m_id,m_no,aadhar_no,acc_no,jan_no);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<SearchResultData>() {
            @Override
            public void onSuccess(SearchResultData body) {
                //AppUtils.showToast(context, body.getMessage());
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    datumList = body.getData().getData().getIgmpyStatusData();
                        Toast.makeText(context, ""+body.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, StatusResultActivity.class)
                                .putExtra("single_page_data_list", AppUtils.convertSearchResultDataToPut(datumList)));

                        finish();

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }
}