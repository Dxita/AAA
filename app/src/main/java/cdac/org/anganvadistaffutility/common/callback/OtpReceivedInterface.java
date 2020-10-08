package cdac.org.anganvadistaffutility.common.callback;

public interface OtpReceivedInterface {
    void onOtpReceived(String otp);

    void onOtpTimeout();
}