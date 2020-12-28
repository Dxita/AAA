package cdac.org.anganvadistaffutility.admin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfrastructureDetailSumData {

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

        @SerializedName("infradata")
        @Expose
        private List<Infradatum> infradata = null;

        public List<Infradatum> getInfradata() {
            return infradata;
        }

        public void setInfradata(List<Infradatum> infradata) {
            this.infradata = infradata;
        }}

        public static class Infradatum {

            @SerializedName("tid_infra_namee")
            @Expose
            private String tidInfraNamee;
            @SerializedName("tim_infra_namee")
            @Expose
            private String timInfraNamee;
            @SerializedName("taid_tim_infra_id")
            @Expose
            private String taidTimInfraId;
            @SerializedName("taid_tid_infra_detail_id")
            @Expose
            private String taidTidInfraDetailId;
            @SerializedName("sum")
            @Expose
            private String sum;

            public String getTidInfraNamee() {
                return tidInfraNamee;
            }

            public void setTidInfraNamee(String tidInfraNamee) {
                this.tidInfraNamee = tidInfraNamee;
            }

            public String getTimInfraNamee() {
                return timInfraNamee;
            }

            public void setTimInfraNamee(String timInfraNamee) {
                this.timInfraNamee = timInfraNamee;
            }

            public String getTaidTimInfraId() {
                return taidTimInfraId;
            }

            public void setTaidTimInfraId(String taidTimInfraId) {
                this.taidTimInfraId = taidTimInfraId;
            }

            public String getTaidTidInfraDetailId() {
                return taidTidInfraDetailId;
            }

            public void setTaidTidInfraDetailId(String taidTidInfraDetailId) {
                this.taidTidInfraDetailId = taidTidInfraDetailId;
            }

            public String getSum() {
                return sum;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }

    }
}