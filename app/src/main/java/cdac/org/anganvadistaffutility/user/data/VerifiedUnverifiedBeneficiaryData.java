package cdac.org.anganvadistaffutility.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifiedUnverifiedBeneficiaryData {

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

        @SerializedName("beneficiaryinfo")
        @Expose
        private Beneficiaryinfo beneficiaryinfo;

        public Beneficiaryinfo getBeneficiaryinfo() {
            return beneficiaryinfo;
        }

        public void setBeneficiaryinfo(Beneficiaryinfo beneficiaryinfo) {
            this.beneficiaryinfo = beneficiaryinfo;
        }

    }

    public static class Beneficiaryinfo {

        @SerializedName("verified_beneficiary")
        @Expose
        private String verifiedBeneficiary;
        @SerializedName("notverified_beneficiary")
        @Expose
        private String notverifiedBeneficiary;

        public String getVerifiedBeneficiary() {
            return verifiedBeneficiary;
        }

        public void setVerifiedBeneficiary(String verifiedBeneficiary) {
            this.verifiedBeneficiary = verifiedBeneficiary;
        }

        public String getNotverifiedBeneficiary() {
            return notverifiedBeneficiary;
        }

        public void setNotverifiedBeneficiary(String notverifiedBeneficiary) {
            this.notverifiedBeneficiary = notverifiedBeneficiary;
        }

    }
}