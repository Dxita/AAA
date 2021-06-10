package cdac.org.anganvadistaffutility.user.pctsMapping.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SinglePageList {

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

    public static class IgmpyMotherDatum {

        @SerializedName("tjm_mother_id")
        @Expose
        private String tjmMotherId;
        @SerializedName("tjm_anm_id")
        @Expose
        private String tjmAnmId;
        @SerializedName("tjm_asha_id")
        @Expose
        private String tjmAshaId;
        @SerializedName("tjm_name")
        @Expose
        private String tjmName;
        @SerializedName("tjm_address")
        @Expose
        private String tjmAddress;
        @SerializedName("tjm_age")
        @Expose
        private String tjmAge;
        @SerializedName("tjm_lmc_date")
        @Expose
        private String tjmLmcDate;
        @SerializedName("tjm_registration_date")
        @Expose
        private String tjmRegistrationDate;
        @SerializedName("tjm_mobileno")
        @Expose
        private String tjmMobileno;
        @SerializedName("tjm_husbname")
        @Expose
        private String tjmHusbname;
        @SerializedName("tjm_accountno")
        @Expose
        private String tjmAccountno;
        @SerializedName("tjm_ifsc_code")
        @Expose
        private String tjmIfscCode;
        @SerializedName("tjm_account_name")
        @Expose
        private String tjmAccountName;
        @SerializedName("tjm_verifyflag")
        @Expose
        private String tjmVerifyflag;
        @SerializedName("tjm_mother_type")
        @Expose
        private String tjmMotherType;
        @SerializedName("tbm_bhamashah_id")
        @Expose
        private String tbmBhamashahId;
        @SerializedName("tbm_aadhar_no")
        @Expose
        private String tbmAadharNo;
        @SerializedName("tbm_janaadhaar_family")
        @Expose
        private String tbmJanaadhaarFamily;
        @SerializedName("tbm_janaadhar")
        @Expose
        private String tbmJanaadhar;
        @SerializedName("tjm_statusflag")
        @Expose
        private String tjmStatusflag;

        public String getTjmMotherId() {
            return tjmMotherId;
        }

        public void setTjmMotherId(String tjmMotherId) {
            this.tjmMotherId = tjmMotherId;
        }

        public String getTjmAnmId() {
            return tjmAnmId;
        }

        public void setTjmAnmId(String tjmAnmId) {
            this.tjmAnmId = tjmAnmId;
        }

        public String getTjmAshaId() {
            return tjmAshaId;
        }

        public void setTjmAshaId(String tjmAshaId) {
            this.tjmAshaId = tjmAshaId;
        }

        public String getTjmName() {
            return tjmName;
        }

        public void setTjmName(String tjmName) {
            this.tjmName = tjmName;
        }

        public String getTjmAddress() {
            return tjmAddress;
        }

        public void setTjmAddress(String tjmAddress) {
            this.tjmAddress = tjmAddress;
        }

        public String getTjmAge() {
            return tjmAge;
        }

        public void setTjmAge(String tjmAge) {
            this.tjmAge = tjmAge;
        }

        public String getTjmLmcDate() {
            return tjmLmcDate;
        }

        public void setTjmLmcDate(String tjmLmcDate) {
            this.tjmLmcDate = tjmLmcDate;
        }

        public String getTjmRegistrationDate() {
            return tjmRegistrationDate;
        }

        public void setTjmRegistrationDate(String tjmRegistrationDate) {
            this.tjmRegistrationDate = tjmRegistrationDate;
        }

        public String getTjmMobileno() {
            return tjmMobileno;
        }

        public void setTjmMobileno(String tjmMobileno) {
            this.tjmMobileno = tjmMobileno;
        }

        public String getTjmHusbname() {
            return tjmHusbname;
        }

        public void setTjmHusbname(String tjmHusbname) {
            this.tjmHusbname = tjmHusbname;
        }

        public String getTjmAccountno() {
            return tjmAccountno;
        }

        public void setTjmAccountno(String tjmAccountno) {
            this.tjmAccountno = tjmAccountno;
        }

        public String getTjmIfscCode() {
            return tjmIfscCode;
        }

        public void setTjmIfscCode(String tjmIfscCode) {
            this.tjmIfscCode = tjmIfscCode;
        }

        public String getTjmAccountName() {
            return tjmAccountName;
        }

        public void setTjmAccountName(String tjmAccountName) {
            this.tjmAccountName = tjmAccountName;
        }

        public String getTjmVerifyflag() {
            return tjmVerifyflag;
        }

        public void setTjmVerifyflag(String tjmVerifyflag) {
            this.tjmVerifyflag = tjmVerifyflag;
        }

        public String getTjmMotherType() {
            return tjmMotherType;
        }

        public void setTjmMotherType(String tjmMotherType) {
            this.tjmMotherType = tjmMotherType;
        }

        public String getTbmBhamashahId() {
            return tbmBhamashahId;
        }

        public void setTbmBhamashahId(String tbmBhamashahId) {
            this.tbmBhamashahId = tbmBhamashahId;
        }

        public String getTbmAadharNo() {
            return tbmAadharNo;
        }

        public void setTbmAadharNo(String tbmAadharNo) {
            this.tbmAadharNo = tbmAadharNo;
        }

        public String getTbmJanaadhaarFamily() {
            return tbmJanaadhaarFamily;
        }

        public void setTbmJanaadhaarFamily(String tbmJanaadhaarFamily) {
            this.tbmJanaadhaarFamily = tbmJanaadhaarFamily;
        }

        public String getTbmJanaadhar() {
            return tbmJanaadhar;
        }

        public void setTbmJanaadhar(String tbmJanaadhar) {
            this.tbmJanaadhar = tbmJanaadhar;
        }

        public String getTjmStatusflag() {
            return tjmStatusflag;
        }

        public void setTjmStatusflag(String tjmStatusflag) {
            this.tjmStatusflag = tjmStatusflag;
        }

    }


   public static class Data {

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
        private Data__1 data;

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

        public Data__1 getData() {
            return data;
        }

        public void setData(Data__1 data) {
            this.data = data;
        }

        public static class Data__1 {

            @SerializedName("igmpyMotherData")
            @Expose
            private List<IgmpyMotherDatum> igmpyMotherData = null;

            public List<IgmpyMotherDatum> getIgmpyMotherData() {
                return igmpyMotherData;
            }

            public void setIgmpyMotherData(List<IgmpyMotherDatum> igmpyMotherData) {
                this.igmpyMotherData = igmpyMotherData;
            }

        }
    }
}