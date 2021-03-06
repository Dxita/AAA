package cdac.org.anganvadistaffutility.common.retrofit;

public class ApiUtils {

    private ApiUtils() {
        // Default Constructor
    }

    // User Type: Employee
   public static final String BASE_URL = "http://rajposhan.rajasthan.gov.in/rajposhan/";
    public static final String PROFILE_BASE_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String LOCAL_URL = "http://10.68.114.57/projects/rajposhan_2/";
    /*public static final String PROFILE_BASE_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String PAYMENT_BASE_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String AVAIL_INFRA_DETAILS_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";

    public static final String SEND_OTP_TO_SERVER_BASE_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String SET_USER_PASSWORD = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String LOGIN_USER = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String UPDATE_INFRASTRUCTURE = "http://10.68.114.57/projects/rajposhan_v2_5_2_test/";
    public static final String AW_BUILDING_DATA = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String GET_BENEFICIARY_CRITERIA = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String BENEFICIARY_SEARCH_DATA = "http://10.68.114.57/projects/rajposhan_v2_5_2_test/";

    // User Type: Admin
    public static final String VERIFY_ADMIN_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String SHOW_USER_KPI_BASE_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String INFRASTRUCTURE_BASE_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String ADMIN_USER_DATA_BASE_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";
    public static final String ADMIN_INFRA_DETAILS_BASE_URL = "http://164.100.222.241/rajposhan/rajposhan_2/";*/

    public static ApiInterface getApiInterface(String baseURL) {
        return ApiClient.getClient(baseURL).create(ApiInterface.class);
    }
}
