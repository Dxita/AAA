package cdac.org.anganvadistaffutility.common.retrofit;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiServiceOperator<T> implements Callback<T> {

    public interface OnResponseListener<T> {
        void onSuccess(T body);

        void onFailure(Throwable t);
    }

    private OnResponseListener<T> onResponseListener;

    public ApiServiceOperator(OnResponseListener<T> onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.isSuccessful()) { // here, do the extraction of body
            onResponseListener.onSuccess(response.body());
        } else {
            onResponseListener.onFailure(new ServerErrorException());
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        onResponseListener.onFailure(new ConnectionErrorException());
    }

    public static class ServerErrorException extends Exception {
    }

    public static class ConnectionErrorException extends Exception {
    }
}
