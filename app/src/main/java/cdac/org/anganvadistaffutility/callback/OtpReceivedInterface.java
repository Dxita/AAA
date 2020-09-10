package cdac.org.anganvadistaffutility.callback;

public interface OtpReceivedInterface {
    void onOtpReceived(String otp);
    void onOtpTimeout();
}