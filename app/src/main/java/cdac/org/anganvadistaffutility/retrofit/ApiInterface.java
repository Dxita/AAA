package cdac.org.anganvadistaffutility.retrofit;

import cdac.org.anganvadistaffutility.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.data.PaymentDetails;
import cdac.org.anganvadistaffutility.data.VerifyUser;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("api_js_emp_check_by_mob/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<VerifyUser> verifyUser(@Field("mobileno") String mobileNumber);

    @POST("api_js_app_employee_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<EmployeeDetails> employeeDetails(@Field("empid") String empID);

    @POST("api_js_salry_employee_master_json/saxcfdkjsajdf567LASKDJFlsakjdfiu")
    @FormUrlEncoded
    Call<PaymentDetails> paymentDetails(@Field("empid") String empID,
                                        @Field("fromdate") String fromDate ,
                                        @Field("todate")String toDate);

}
