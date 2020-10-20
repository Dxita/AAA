package cdac.org.anganvadistaffutility.common.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCES_NAME = "RAJPOSHAN_AANGANVAADI";
    private static final String SAVE_GENERATED_OTP = "SAVE_GENERATED_OTP";
    private static final String EMPLOYEE_ID = "EMPLOYEE_ID";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    private SharedPreferences.Editor editor;

    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getAppPreference() {
        return sharedPreferences;
    }

    public void setEmployeeId(String empId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMPLOYEE_ID, empId);
        editor.apply();
    }

    public String getEmployeeId() {
        return sharedPreferences.getString(EMPLOYEE_ID, "");
    }

    public void saveGeneratedOtp(String otp) {
        editor = sharedPreferences.edit();
        editor.putString(SAVE_GENERATED_OTP, otp);
        editor.apply();
    }

    public String getSavedOtp() {
        return sharedPreferences.getString(SAVE_GENERATED_OTP, "");
    }

    public void setUserLoggedIn(boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, value);
        editor.apply();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}