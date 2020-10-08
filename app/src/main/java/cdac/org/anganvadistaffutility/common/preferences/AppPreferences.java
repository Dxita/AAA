package cdac.org.anganvadistaffutility.common.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String APP_PREFERENCES_NAME = "RAJPOSHAN_AANGANVAADI";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String OTP_GENERATED = "OTP_GENERATED";
    private static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    public AppPreferences() {
        // Default Constructor
    }

    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setOtpGenerated(String otp) {
        editor = sharedPreferences.edit();
        editor.putString(OTP_GENERATED, otp);
        editor.apply();
    }

    public String getOtpGenerated() {
        return sharedPreferences.getString(OTP_GENERATED, "");
    }

    public void setEmployeeId(String empId) {
        editor = sharedPreferences.edit();
        editor.putString(EMPLOYEE_ID, empId);
        editor.apply();
    }

    public String getEmployeeId() {
        return sharedPreferences.getString(EMPLOYEE_ID, "");
    }
}