package cdac.org.anganvadistaffutility.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AwcItemsData {

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

            @SerializedName("taid_awc_id")
            @Expose
            private String taidAwcId;
            @SerializedName("taid_tim_infra_id")
            @Expose
            private String taidTimInfraId;
            @SerializedName("taid_tid_infra_detail_id")
            @Expose
            private String taidTidInfraDetailId;
            @SerializedName("taid_qty")
            @Expose
            private String taidQty;
            @SerializedName("tid_infra_namee")
            @Expose
            private String tidInfraNamee;
            @SerializedName("tid_infra_nameh")
            @Expose
            private String tidInfraNameh;

            public String getTaidAwcId() {
                return taidAwcId;
            }

            public void setTaidAwcId(String taidAwcId) {
                this.taidAwcId = taidAwcId;
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

            public String getTaidQty() {
                return taidQty;
            }

            public void setTaidQty(String taidQty) {
                this.taidQty = taidQty;
            }

            public String getTidInfraNamee() {
                return tidInfraNamee;
            }

            public void setTidInfraNamee(String tidInfraNamee) {
                this.tidInfraNamee = tidInfraNamee;
            }

            public String getTidInfraNameh() {
                return tidInfraNameh;
            }

            public void setTidInfraNameh(String tidInfraNameh) {
                this.tidInfraNameh = tidInfraNameh;
            }
        }
    }
}
