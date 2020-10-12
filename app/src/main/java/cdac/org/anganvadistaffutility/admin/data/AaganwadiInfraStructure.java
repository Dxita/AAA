package cdac.org.anganvadistaffutility.admin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;


public class AaganwadiInfraStructure {

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

        public Collection<? extends InfrastructureDatum> getInfrastructureData() {
            return infrastructureData;
        }

        public void setInfrastructureData(List<InfrastructureDatum> infrastructureData) {
            this.infrastructureData = infrastructureData;
        }
    }

    public static class InfrastructureDatum {

        @SerializedName("tim_infrastructure_id")
        @Expose
        private String timInfrastructureId;
        @SerializedName("tim_infra_namee")
        @Expose
        private String timInfrastructureNamee;
        @SerializedName("tim_infra_nameh")
        @Expose
        private String timInfrastructureNameh;

        public String getTimInfrastructureId() {
            return timInfrastructureId;
        }

        public void setTimInfrastructureId(String timInfrastructureId) {
            this.timInfrastructureId = timInfrastructureId;
        }

        public String getTimInfrastructureNamee() {
            return timInfrastructureNamee;
        }

        public void setTimInfrastructureNamee(String timInfrastructureNamee) {
            this.timInfrastructureNamee = timInfrastructureNamee;
        }

        public String getTimInfrastructureNameh() {
            return timInfrastructureNameh;
        }

        public void setTimInfrastructureNameh(String timInfrastructureNameh) {
            this.timInfrastructureNameh = timInfrastructureNameh;
        }
    }
}