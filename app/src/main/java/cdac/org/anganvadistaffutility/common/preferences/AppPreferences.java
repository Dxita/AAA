package cdac.org.anganvadistaffutility.common.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    protected SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCES_NAME = "RAJPOSHAN_AANGANVAADI";
    private static final String SAVE_GENERATED_OTP = "SAVE_GENERATED_OTP";
    private static final String EMPLOYEE_ID = "EMPLOYEE_ID";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    private static final String LANGUAGE_SET = "IS_LANGUAGE_SET";
    private static final String AWCID = "AWC_ID";

    private static final String TYPE_USER = "IS_TYPE_USER";

    private static final String TYPE_ADMIN = "IS_TYPE_ADMIN";
    private static final String TYPE_PUBLIC = "IS_TYPE_PUBLIC";
    private static final String MOBILENO = "MOBILE_NO";

    private static final String AADHARNO = "AADHAR_NO";
    private static final String JANAADHARNO = "JANAADHAR_NO";
    private static final String BHAMASHANO = "BHAMASHA_NO";
    private static final String INFRA_ID = "INFRAID";
    private static final String LAST_CHECKED_POS = "LAST_CHECKED_POSITION";
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

    public void setLanguagePref(boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(LANGUAGE_SET, value);
        editor.apply();
    }

    public boolean isSetLanguagePref() {
        return sharedPreferences.getBoolean(LANGUAGE_SET, false);
    }

    public void setTypeUser(boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(TYPE_USER, value);
        editor.apply();
    }

    public boolean istypeuser() {
        return sharedPreferences.getBoolean(TYPE_USER, false);
    }

    public void settypeadmin(boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(TYPE_ADMIN, value);
        editor.apply();
    }

    public boolean istypeadmin() {
        return sharedPreferences.getBoolean(TYPE_ADMIN, false);
    }

    public void settyppublic(boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(TYPE_PUBLIC, value);
        editor.apply();
    }

    public boolean istypepublic() {
        return sharedPreferences.getBoolean(TYPE_PUBLIC, false);
    }


    public void setAwcId(String awcId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AWCID, awcId);
        editor.apply();
    }

    public String getAwcId() {
        return sharedPreferences.getString(AWCID, "");
    }

    public String getMobileNumber() {
        return sharedPreferences.getString(MOBILENO, "");
    }

    public void setMobileNumber(String mobileNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOBILENO, mobileNumber);
        editor.apply();
    }

    public String getAadharNo() {
        return sharedPreferences.getString(AADHARNO, "");
    }

    public void setAadharno(String aadharno) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AADHARNO, aadharno);
        editor.apply();
    }

    public String getJanaadharno() {
        return sharedPreferences.getString(JANAADHARNO, "");
    }

    public void setJanaadharno(String janaadharno) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JANAADHARNO, janaadharno);
        editor.apply();
    }

    public String getBhamashano() {
        return sharedPreferences.getString(BHAMASHANO, "");
    }

    public void setBhamashano(String bhamashano) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BHAMASHANO, bhamashano);
        editor.apply();
    }

    public String getInfraId() {
        return sharedPreferences.getString(INFRA_ID, "");
    }

    public void setInfraId(String infraId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INFRA_ID, infraId);
        editor.apply();
    }

    public String getLastCheckedPos() {
        return sharedPreferences.getString(LAST_CHECKED_POS, "");
    }

    public void setLastCheckedPos(String lastCheckedPos) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_CHECKED_POS, lastCheckedPos);
        editor.apply();

    }
}