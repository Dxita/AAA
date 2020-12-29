package cdac.org.anganvadistaffutility.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RemainingInfrastructureData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("empdata")
        @Expose
        private List<Empdatum> empdata = null;

        public List<Empdatum> getEmpdata() {
            return empdata;
        }

        public void setEmpdata(List<Empdatum> empdata) {
            this.empdata = empdata;
        }
    }

    public static class Empdatum {

        @SerializedName("tim_infra_id")
        @Expose
        private String timInfraId;
        @SerializedName("tim_infra_namee")
        @Expose
        private String timInfraNamee;
        @SerializedName("tim_infra_nameh")
        @Expose
        private String timInfraNameh;
        @SerializedName("tim_created_date")
        @Expose
        private String timCreatedDate;
        @SerializedName("tim_modified_date")
        @Expose
        private String timModifiedDate;
        @SerializedName("tim_status")
        @Expose
        private String timStatus;
        @SerializedName("tim_user_id")
        @Expose
        private String timUserId;
        @SerializedName("tim_ip_address")
        @Expose
        private Object timIpAddress;
        @SerializedName("tim_infra_icon")
        @Expose
        private Object timInfraIcon;
        @SerializedName("tim_accept_status")
        @Expose
        private String timAcceptStatus;

        public String getTimInfraId() {
            return timInfraId;
        }

        public void setTimInfraId(String timInfraId) {
            this.timInfraId = timInfraId;
        }

        public String getTimInfraNamee() {
            return timInfraNamee;
        }

        public void setTimInfraNamee(String timInfraNamee) {
            this.timInfraNamee = timInfraNamee;
        }

        public String getTimInfraNameh() {
            return timInfraNameh;
        }

        public void setTimInfraNameh(String timInfraNameh) {
            this.timInfraNameh = timInfraNameh;
        }

        public String getTimCreatedDate() {
            return timCreatedDate;
        }

        public void setTimCreatedDate(String timCreatedDate) {
            this.timCreatedDate = timCreatedDate;
        }

        public String getTimModifiedDate() {
            return timModifiedDate;
        }

        public void setTimModifiedDate(String timModifiedDate) {
            this.timModifiedDate = timModifiedDate;
        }

        public String getTimStatus() {
            return timStatus;
        }

        public void setTimStatus(String timStatus) {
            this.timStatus = timStatus;
        }

        public String getTimUserId() {
            return timUserId;
        }

        public void setTimUserId(String timUserId) {
            this.timUserId = timUserId;
        }

        public Object getTimIpAddress() {
            return timIpAddress;
        }

        public void setTimIpAddress(Object timIpAddress) {
            this.timIpAddress = timIpAddress;
        }

        public Object getTimInfraIcon() {
            return timInfraIcon;
        }

        public void setTimInfraIcon(Object timInfraIcon) {
            this.timInfraIcon = timInfraIcon;
        }

        public String getTimAcceptStatus() {
            return timAcceptStatus;
        }

        public void setTimAcceptStatus(String timAcceptStatus) {
            this.timAcceptStatus = timAcceptStatus;
        }


    }
}
