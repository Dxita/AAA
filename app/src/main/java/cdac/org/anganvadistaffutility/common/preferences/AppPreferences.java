package cdac.org.anganvadistaffutility.common.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    //private SharedPreferences sharedPreferences;

    private static final String APP_PREFERENCES_NAME = "RAJPOSHAN_AANGANVAADI";
    private static final String SAVE_GENERATED_OTP = "SAVE_GENERATED_OTP";
    private static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;


    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setEmployeeId(String empId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMPLOYEE_ID, empId);
        editor.apply();
    }

    public static String getEmployeeId() {
        return sharedPreferences.getString(EMPLOYEE_ID, "");
    }

    public void saveGeneratedOtp(String otp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_GENERATED_OTP, otp);
        editor.apply();
    }

    public String getSavedOtp() {
        return sharedPreferences.getString(SAVE_GENERATED_OTP, "");
    }

    public static void putKey(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.apply();

    }

    public static String getKey(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        String Value = sharedPreferences.getString(Key, "");
        return Value;

    }

}