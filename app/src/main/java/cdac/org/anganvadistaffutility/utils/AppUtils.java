package cdac.org.anganvadistaffutility.utils;

import android.Manifest;
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
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.PaymentDetails;

public class AppUtils {

    public static final String successStatus = "true";
    // public static final String empMobileNumber = "9982471586";
    //public static final String empMobileNumber = "7062613314";

    //public static final String empID = "109626";
    // public static final String empID = "109567";
    public static final String empID = "30819";

    private static final String FOLDER_NAME = "RajPosh";
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 123;
    public static final String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
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
        try {
            String rootPath = Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME;
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

    public static String getRandomNumberString() {
        Random random = new Random();
        int number = random.nextInt(999999);
        //  int number = random.nextInt(900) + 100;
        return String.format(Locale.ENGLISH, "%06d", number);
        // return String.format(Locale.ENGLISH, "%03d", number);
    }
}
