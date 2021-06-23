package cdac.org.anganvadistaffutility.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeneficiarySubCategory {

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

    }

    public static class InfrastructureDatum {

        @SerializedName("tcs_category_id")
        @Expose
        private String tcsCategoryId;
        @SerializedName("tcs_category_namee")
        @Expose
        private String tcsCategoryNamee;
        @SerializedName("tcs_category_nameh")
        @Expose
        private String tcsCategoryNameh;

        public String getTcsCategoryId() {
            return tcsCategoryId;
        }

        public void setTcsCategoryId(String tcsCategoryId) {
            this.tcsCategoryId = tcsCategoryId;
        }

        public String getTcsCategoryNamee() {
            return tcsCategoryNamee;
        }

        public void setTcsCategoryNamee(String tcsCategoryNamee) {
            this.tcsCategoryNamee = tcsCategoryNamee;
        }

        public String getTcsCategoryNameh() {
            return tcsCategoryNameh;
        }

        public void setTcsCategoryNameh(String tcsCategoryNameh) {
            this.tcsCategoryNameh = tcsCategoryNameh;
        }

    }
}