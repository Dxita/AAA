package cdac.org.anganvadistaffutility.common.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.admin.data.ProjectWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.data.PaymentDetails;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySearchData;

import static android.content.Context.ACTIVITY_SERVICE;


public class AppUtils {

    public static final String successStatus = "true";

    // public static final String empMobileNumber = "9982471586";
    //public static final String empMobileNumber = "7062613314";

    //public static final String empID = "109626";
    // public static final String empID = "109567";
    //public static final String empID = "30819";

    private static final String FOLDER_NAME = "RajPosh";

    public static final int STORAGE_PERMISSION_REQUEST_CODE = 123;
    public static final int CALL_PERMISSION_REQUEST_CODE = 456;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 789;

    public static final String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] CALL_PERMISSIONS = {Manifest.permission.CALL_PHONE};
    public static final String[] PHONE_PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};

    /*public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }*/

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo networkInfo : info)
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void setProgressBarVisibility(Context context, ViewGroup parent, int visibility) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.layout_progress_bar, parent, true);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(visibility);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static File writeFilesToAppFolder(String fileName, String fileType) {
        File file = null;
        String rootPath = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                rootPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + FOLDER_NAME;
            } else {
                rootPath = Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME;
            }

            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
            file = new File(rootPath + File.separator + fileName + fileType);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String convertToPut(ArrayList<PaymentDetails.Empdatum> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<PaymentDetails.Empdatum> convertToGet(String list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<PaymentDetails.Empdatum>>() {
        }.getType();
        return gson.fromJson(list, type);
    }

    public static String convertUserToPut(ArrayList<AdminUserData.Empdatum> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<AdminUserData.Empdatum> convertUserToGet(String list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<AdminUserData.Empdatum>>() {
        }.getType();
        return gson.fromJson(list, type);
    }

    public static String convertInfradataToPut(ArrayList<InfraStructureDetailData.Infradatum> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<InfraStructureDetailData.Infradatum> convertInfradataToGet(String list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<InfraStructureDetailData.Infradatum>>() {
        }.getType();
        return gson.fromJson(list, type);
    }

    public static String convertProjectToPut(ArrayList<ProjectWiseEmployeeDetails> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<ProjectWiseEmployeeDetails> convertProjectToGet(String list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProjectWiseEmployeeDetails>>() {
        }.getType();
        return gson.fromJson(list, type);
    }


    public static String convertDistToPut(ArrayList<InfraDetailData> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<InfraStructureDetailData> convertDistrictToGet(String list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<InfraStructureDetailData>>() {
        }.getType();
        return gson.fromJson(list, type);
    }

    public static String getRandomNumberString() {
        Random random = new Random();
        //  int number = random.nextInt(999999);
        int number = random.nextInt(9000) + 1000;
        // return String.format(Locale.ENGLISH, "%06d", number);
        return String.format(Locale.ENGLISH, "%04d", number);
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-8])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return !matcher.matches();
    }

    public static int currentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int previousYear() {
        return 2015;
    }

    public static final String serviceName = "cdac.org.anganvadistaffutility.common.service.UserLogoutService";

    public static boolean isLogoutServiceRunning(Context context, String serviceName) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : l) {
            if (runningServiceInfo.service.getClassName().equals(serviceName)) {
                if (runningServiceInfo.foreground) {
                    serviceRunning = true;
                }
            }
        }
        return serviceRunning;
    }
}
