package cdac.org.anganvadistaffutility.common.retrofit;

import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.AnganwadiInfraData;
import cdac.org.anganvadistaffutility.admin.data.AwcData;
import cdac.org.anganvadistaffutility.admin.data.BeneficiaryCriteria;
import cdac.org.anganvadistaffutility.admin.data.DistrictEmployeesCount;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailProjectData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfrastructureDetailSumData;
import cdac.org.anganvadistaffutility.admin.data.ProjectEmployeeData;
import cdac.org.anganvadistaffutility.admin.data.RegisteredUserKPI;
import cdac.org.anganvadistaffutility.admin.data.SectorWiseData;
import cdac.org.anganvadistaffutility.admin.data.VerifyAdmin;
import cdac.org.anganvadistaffutility.admin.igmpy.data.AwcList;
import cdac.org.anganvadistaffutility.admin.igmpy.data.DistList;
import cdac.org.anganvadistaffutility.admin.igmpy.data.ProjList;
import cdac.org.anganvadistaffutility.admin.igmpy.data.SearchResultData;
import cdac.org.anganvadistaffutility.admin.igmpy.data.SecList;
import cdac.org.anganvadistaffutility.common.data.PaymentDetails;
import cdac.org.anganvadistaffutility.user.data.AWDetails;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;
import cdac.org.anganvadistaffutility.user.data.AddInfrastructureData;
import cdac.org.anganvadistaffutility.user.data.AddNewInfrastructureData;
import cdac.org.anganvadistaffutility.user.data.AvailableAwInfraDetailData;
import cdac.org.anganvadistaffutility.user.data.AwcItemsData;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySearchData;
import cdac.org.anganvadistaffutility.user.data.ChangePasswordData;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.user.data.RemainingInfraDetailData;
import cdac.org.anganvadistaffutility.user.data.RemainingInfrastructureData;
import cdac.org.anganvadistaffutility.user.data.SetUserLogin;
import cdac.org.anganvadistaffutility.user.data.SetUserPassword;
import cdac.org.anganvadistaffutility.user.data.UpdateInfrastructureData;
import cdac.org.anganvadistaffutility.user.data.UserInfrastructureData;
import cdac.org.anganvadistaffutility.user.data.VerifyOTPDetails;
import cdac.org.anganvadistaffutility.user.data.VerifyUser;
import cdac.org.anganvadistaffutility.user.pctsMapping.data.SinglePageList;
import cdac.org.anganvadistaffutility.user.pctsMapping.data.VerifyStatusData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    // Verify user in database

    @POST("api_js_emp_check_by_mob/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<VerifyUser> verifyUser(@Field("mobileno") String mobileNumber);

    // Get employee profile details


    @POST("api_js_app_employee_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<EmployeeDetails> employeeDetails(@Field("empid") String empID);

    // Send otp to server & user number

    @POST("api_js_app_auth_otp_verify_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<VerifyOTPDetails> sendOtpToServer(@Field("mobileno") String mobileNumber,
                                           @Field("otp") String OTP);

    // Set user password

    @POST("api_js_app_auth_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<SetUserPassword> setUserPassword(@Field("empid") String empID, @Field("mobileno") String mobileNumber,
                                          @Field("password") String password);

    // Make user login

    @POST("api_js_app_auth_login_verify_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<SetUserLogin> setUserLogin(@Field("validuser") String empID,
                                    @Field("password") String password);

    // Get employee payment details both admin & user side

    @POST("api_js_salry_employee_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<PaymentDetails> paymentDetails(@Field("empid") String empID,
                                        @Field("fromdate") String fromDate,
                                        @Field("todate") String toDate);

    // Get line chart registered and unregistered user (not in use for now)

    @GET("api_js_app_graph_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    Call<RegisteredUserKPI> getRegisteredUserKPI();

    // Get aanganwadi infrastructure data

    @GET("api_infrastructure_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    Call<AaganwadiInfraStructure> getInfrastructureData();

    // Get pie chart of registered and unregistered user (in use)

    @GET("api_js_total_employees_registered_and_unregistered_graph_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    Call<AdminUserData> getAdminUserData();

    // Get pie chart of registered and unregistered user (for total count)
    @POST("api_js_total_employees_registered_and_unregistered_graph_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AdminUserData> getAdminUsersData(@Field("parm") String parm);


    // dist wise employee count

    @POST("api_js_total_employees_registered_and_unregistered_graph_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<DistrictEmployeesCount> getDistrictEmployees(@Field("parm") String parm);

    // poject wise employee count

    @POST("api_js_total_employees_registered_and_unregistered_graph_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<ProjectEmployeeData> getProjectEmployeeData(@Field("distid") String distid,
                                                     @Field("parm") String parm);

    // get awc  wise employee count
    @POST("api_js_total_employees_registered_and_unregistered_graph_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AwcData> getAwcData(@Field("distid") String distid,
                             @Field("parm") String parm);


    // Get aanganwadi details

    @POST("api_js_app_employee_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AWDetails> awDetails(@Field("empid") String empID);

    // Verify admin phone and email

    @POST("api_js_admin_check_by_mob/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<VerifyAdmin> verifyAdmin(@Field("mobileno") String adminNumber, @Field("email") String email);

    // Aaganwadi details (User Side)

    /*@POST("api_js_infrastructure_master_by_awcid/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AwcItemsData> awcItemData(@Field("taid_awc_id") String awc_id,
                                   @Field("taid_tim_infra_id") String infra_id);*/

    // Infrastructure list using awc id in user side

    @POST("api_infrastructure_master_by_awcid_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<UserInfrastructureData> userInfrastructureData(@Field("tjaid_awc_id") String awc_id);

    // aanganwadi building list

    @POST("api_js_infrastructure_detail_by_infra_and_awcid_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AanganwadiBuildingData> aanganwadiBuildingData(@Field("tjaid_tim_infra_id") String tjaid_tim_infra_id,
                                                        @Field("tjaid_awc_id") String tjaid_awc_id);

  /*  //add new infra spinner list(user side)
    @POST("api_js_infrastructure_detail_by_infra_and_awcid_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AddNewInfrastructureData> addNewInfraList(@Field("tjaid_tim_infra_id") String tjaid_tim_infra_id,
                                                   @Field("tjaid_awc_id") String tjaid_awc_id);*/


    //  InfraStructure Details (Admin Side)

    @POST("api_js_infrastructure_detail_by_infra_id_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<InfraStructureDetailData> getInfrastructureDetails(@Field("filterby") String filterType,
                                                            @Field("infra_id") String infraID,
                                                            @Field("infra_detail_id") String infra_detail_id);

    //Infra details project wise in admin side)
    @POST("api_js_infrastructure_detail_by_infra_id_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<InfraDetailProjectData> getInfrastructureProjectDetails(@Field("filterby") String filterType,
                                                                 @Field("infra_id") String infraID,
                                                                 @Field("distid") String district_id,
                                                                 @Field("infra_detail_id") String infra_detail_id);
    //Infra details sector wise in admin side)
    @POST("api_js_infrastructure_detail_by_infra_id_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<SectorWiseData> getSectorWiseDetails(@Field("filterby") String filterType,
                                                         @Field("infra_id") String infraID,
                                                         @Field("projectid") String projectid,
                                                         @Field("infra_detail_id") String infra_detail_id);


    //awc data(infra admin)
    @POST("api_js_infrastructure_detail_by_infra_id_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AnganwadiInfraData> getAnganwadiInfraData(@Field("filterby") String filterType,
                                                   @Field("infra_id") String infraID,
                                                   @Field("infra_detail_id") String infra_detail_id,
                                                   @Field("sectorid") String sectorid);

    // Get Beneficiary Criteria List

    @POST("api_js_Beneficiary_Master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    Call<BeneficiaryCriteria> getBeneficiaryCriteria();

    @POST("api_js_Beneficiary_search_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<BeneficiarySearchData> getBeneficiarSearchData(@Field("mother_type") String motherType,
                                                        @Field("adhar_number") String adhar_number,
                                                        @Field("mobile_number") String mobile_number,
                                                        @Field("janadhar_number") String janadhar_number,
                                                        @Field("bhamasha_number") String bhamasha_number);

    //update infra details in user side
    @POST("api_js_infrastructur_update_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<UpdateInfrastructureData> updateInfrastructureData(@Field("infra_id") String infra_id,
                                                            @Field("awc_id") String awc_id,
                                                            @Field("tim_accept_status") String tim_accept_status,
                                                            @Field("qty") String qty,
                                                            @Field("infra_detail_id") String infra_detail_id,
                                                            @Field("last_infra_detail_id") String last_infra_detail_id);


    //public side
  /*  @POST("api_js_public_user_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    //Call<VerifyPublicData> verifyPublic(@Field("mobileno") String mobileNumber);
*/

    //available infrastructure detail data as per AWC(user side)
    @POST("api_available_infrastructure_master_by_awcid_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AvailableAwInfraDetailData> availableInfrastructureData(@Field("tjaid_awc_id") String tjaid_awc_id);

    //remaining infrastructure
    @POST("api_reaming_infrastructure_master_by_awcid_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<RemainingInfrastructureData> remainingInfrastructureData(@Field("tjaid_awc_id") String tjaid_awc_id);

    //add remaining infra detail
    @POST("api_reaming_infradetail_by_awcid_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<RemainingInfraDetailData> remainingInfraDetailData(@Field("tjaid_awc_id") String tjaid_awc_id,
                                                            @Field("tjaid_tim_infra_id") String tjaid_tim_infra_id);

    //add remaining infra details in user side
    @POST("api_add_reaming_infradetail_by_awcid_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AddInfrastructureData> addInfrastructureData(@Field("awc_id") String awc_id,
                                                      @Field("infra_id") String infra_id,
                                                      @Field("infra_detail_id") String infra_detail_id,
                                                      @Field("quantity") String quantity);


    @POST("api_js_infrastructure_detail_by_infra_id_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<InfrastructureDetailSumData> infrastructureDetailSumData(@Field("filterby") String filterType,
                                                                  @Field("infra_id") String infraID);


    //change password in user side
    @POST("api_js_app_reset_pwd_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<ChangePasswordData> changePassword(@Field("mobileno") String mobileno,
                                            @Field("oldpassword") String infraID,
                                            @Field("newpassword") String newpassword);


    //mother mapping single page list (user side)
    @POST("api_singlepage_mapping/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<SinglePageList> getSinglePageList(@Field("mother_type") String mother_type,
                                           @Field("mothername") String mothername,
                                           @Field("pctsashaid") String pctsashaid,
                                           @Field("pctsanmid") String pctsanmid,
                                           @Field("aadharno") String aadharno,
                                           @Field("mobileno") String mobileno,
                                           @Field("icdscode") String icdscode);

    @POST("api_update_singlepage_mapping/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<VerifyStatusData> updateVerifyFlag(@Field("mother_id") String mother_id,
                                            @Field("mother_type") String mother_type,
                                            @Field("verify_flag") String verify_flag);

    @GET("api_igmpy_district/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    Call<DistList> getDistrictList();

    @POST("api_igmpy_project_by_district/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<ProjList> getProjectList(@Field("dist_id") String dist_id);

    @POST("api_igmpy_sector_by_project/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<SecList> getSectorList(@Field("project_id") String project_id);

    @POST("api_igmpy_awc_by_sector/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AwcList> getAwcList(@Field("sector_id") String sector_id);

    @POST("api_igmpy_view_status_details/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<SearchResultData> getSearchResult(@Field("district_id") String district_id,
                                           @Field("project_id") String project_id,
                                           @Field("sector_id") String sector_id,
                                           @Field("awc_id") String awc_id,
                                           @Field("motherName") String motherName,
                                           @Field("motherId") String motherId,
                                           @Field("mobileNo") String mobileNo,
                                           @Field("aadharNo") String aadharNo,
                                           @Field("accountNo") String accountNo,
                                           @Field("sector_id") String janadharNumber);

}
