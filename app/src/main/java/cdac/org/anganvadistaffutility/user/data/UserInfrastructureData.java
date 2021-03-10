package cdac.org.anganvadistaffutility.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfrastructureData {

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

        @SerializedName("infrastructureData")
        @Expose
        private List<InfrastructureDatum> infrastructureData = null;

        public List<InfrastructureDatum> getInfrastructureData() {
            return infrastructureData;
        }

        public void setInfrastructureData(List<InfrastructureDatum> infrastructureData) {
            this.infrastructureData = infrastructureData;
        }

        public static class InfrastructureDatum {

            @SerializedName("tim_infra_namee")
            @Expose
            private String timInfraNamee;
            @SerializedName("tim_infra_nameh")
            @Expose
            private String timInfraNameh;
            @SerializedName("tim_infra_id")
            @Expose
            private String timInfraId;
            @SerializedName("tim_accept_status")
            @Expose
            private String timAcceptStatus;

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

            public String getTimInfraId() {
                return timInfraId;
            }

            public void setTimInfraId(String timInfraId) {
                this.timInfraId = timInfraId;
            }

            public String getTimAcceptStatus() {
                return timAcceptStatus;
            }

            public void setTimAcceptStatus(String timAcceptStatus) {
                this.timAcceptStatus = timAcceptStatus;
            }
        }
    }
}
