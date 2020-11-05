package cdac.org.anganvadistaffutility.admin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeneficiaryCriteria {

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

        @SerializedName("beneficiary")
        @Expose
        private List<Beneficiary> beneficiary = null;

        public List<Beneficiary> getBeneficiary() {
            return beneficiary;
        }

        public void setBeneficiary(List<Beneficiary> beneficiary) {
            this.beneficiary = beneficiary;
        }
    }

    public static class Beneficiary {

        @SerializedName("tbm_beneficiary_id")
        @Expose
        private String tbmBeneficiaryId;
        @SerializedName("tbm_beneficiary_namee")
        @Expose
        private String tbmBeneficiaryNamee;
        @SerializedName("tbm_beneficiary_nameh")
        @Expose
        private String tbmBeneficiaryNameh;

        public String getTbmBeneficiaryId() {
            return tbmBeneficiaryId;
        }

        public void setTbmBeneficiaryId(String tbmBeneficiaryId) {
            this.tbmBeneficiaryId = tbmBeneficiaryId;
        }

        public String getTbmBeneficiaryNamee() {
            return tbmBeneficiaryNamee;
        }

        public void setTbmBeneficiaryNamee(String tbmBeneficiaryNamee) {
            this.tbmBeneficiaryNamee = tbmBeneficiaryNamee;
        }

        public String getTbmBeneficiaryNameh() {
            return tbmBeneficiaryNameh;
        }

        public void setTbmBeneficiaryNameh(String tbmBeneficiaryNameh) {
            this.tbmBeneficiaryNameh = tbmBeneficiaryNameh;
        }
    }
}
