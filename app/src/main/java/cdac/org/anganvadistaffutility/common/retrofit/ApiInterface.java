package cdac.org.anganvadistaffutility.common.retrofit;

import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.RegisteredUserKPI;
import cdac.org.anganvadistaffutility.common.data.PaymentDetails;
import cdac.org.anganvadistaffutility.data.VerifyUser;
import cdac.org.anganvadistaffutility.user.data.AWDetails;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.user.data.VerifyOTPDetails;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("api_js_emp_check_by_mob/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<VerifyUser> verifyUser(@Field("mobileno") String mobileNumber);

    @POST("api_js_app_employee_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<EmployeeDetails> employeeDetails(@Field("empid") String empID);

    @POST("api_js_app_auth_otp_verify_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<VerifyOTPDetails> sendOtpToServer(@Field("mobileno") String mobileNumber,
                                           @Field("otp") String OTP);

    @POST("api_js_salry_employee_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<PaymentDetails> paymentDetails(@Field("empid") String empID,
                                        @Field("fromdate") String fromDate,
                                        @Field("todate") String toDate);

    @GET("api_js_app_graph_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    Call<RegisteredUserKPI> getRegisteredUserKPI();

    @GET("api_infrastructure_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    Call<AaganwadiInfraStructure> getInfrastructureData();

    @GET("api_js_total_employees_registered_and_unregistered_graph_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    Call<AdminUserData> getAdminUserData();

    @POST("api_js_app_employee_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<AWDetails> awDetails(@Field("empid") String empID);


}
