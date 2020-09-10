package cdac.org.anganvadistaffutility.retrofit;

public class ApiUtils {

    private ApiUtils() {
        // Default Constructor
    }

    public static final String BASE_URL = "http://10.68.114.51/projects/rajposhan_v2_5_2_test/";
    public static final String PROFILE_BASE_URL = "http://10.68.114.57/projects/rajposhan_v2_5_2_test/";

    public static ApiInterface getApiInterface(String baseURL) {
        return ApiClient.getClient(baseURL).create(ApiInterface.class);
    }
}