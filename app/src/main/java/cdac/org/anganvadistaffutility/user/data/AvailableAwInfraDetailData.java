package cdac.org.anganvadistaffutility.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableAwInfraDetailData {

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

        public static class Empdatum {

            @SerializedName("tim_infra_namee")
            @Expose
            private String timInfraNamee;
            @SerializedName("tim_infra_nameh")
            @Expose
            private String timInfraNameh;
            @SerializedName("tid_infra_namee")
            @Expose
            private String tidInfraNamee;
            @SerializedName("tid_infra_nameh")
            @Expose
            private String tidInfraNameh;
            @SerializedName("tjaid_qty")
            @Expose
            private String tjaidQty;
            @SerializedName("tid_tim_infra_id")
            @Expose
            private String tidTimInfraId;
            @SerializedName("tid_infra_detail_id")
            @Expose
            private String tidInfraDetailId;

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

            public String getTjaidQty() {
                return tjaidQty;
            }

            public void setTjaidQty(String tjaidQty) {
                this.tjaidQty = tjaidQty;
            }

            public String getTidTimInfraId() {
                return tidTimInfraId;
            }

            public void setTidTimInfraId(String tidTimInfraId) {
                this.tidTimInfraId = tidTimInfraId;
            }

            public String getTidInfraDetailId() {
                return tidInfraDetailId;
            }

            public void setTidInfraDetailId(String tidInfraDetailId) {
                this.tidInfraDetailId = tidInfraDetailId;
            }
        }
    }
}