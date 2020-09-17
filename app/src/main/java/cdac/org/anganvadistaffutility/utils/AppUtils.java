package cdac.org.anganvadistaffutility.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cdac.org.anganvadistaffutility.data.PaymentDetails;

public class AppUtils {

    public static final String successStatus = "true";
    // public static final String empMobileNumber = "9982471586";
    //public static final String empMobileNumber = "7062613314";

    //public static final String empID = "109626";
    // public static final String empID = "109567";
    public static final String empID = "30819";

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

    public static String convertToPut(ArrayList<PaymentDetails.Empdatum> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<PaymentDetails.Empdatum> convertToGet(String list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<PaymentDetails.Empdatum>>(){}.getType();
        return gson.fromJson(list, type);
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format(Locale.ENGLISH, "%06d", number);
    }
}
