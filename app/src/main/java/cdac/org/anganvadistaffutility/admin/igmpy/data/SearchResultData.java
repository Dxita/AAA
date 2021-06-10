package cdac.org.anganvadistaffutility.admin.igmpy.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultData {

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
    public class Data {

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

    }
    public static class Data__1 {

        @SerializedName("igmpyStatusData")
        @Expose
        private List<IgmpyStatusDatum> igmpyStatusData = null;

        public List<IgmpyStatusDatum> getIgmpyStatusData() {
            return igmpyStatusData;
        }

        public void setIgmpyStatusData(List<IgmpyStatusDatum> igmpyStatusData) {
            this.igmpyStatusData = igmpyStatusData;
        }

    }
    public static class IgmpyStatusDatum {

        @SerializedName("Mother Name")
        @Expose
        private String motherName;
        @SerializedName("Mother Id")
        @Expose
        private String motherId;
        @SerializedName("Husband Name")
        @Expose
        private String husbandName;
        @SerializedName("Aadhar")
        @Expose
        private String aadhar;
        @SerializedName("Jan Aadhar")
        @Expose
        private String janAadhar;
        @SerializedName("LMP Date")
        @Expose
        private String lMPDate;
        @SerializedName("REG Date")
        @Expose
        private String rEGDate;
        @SerializedName("Status")
        @Expose
        private String status;

        public String getMotherName() {
            return motherName;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName;
        }

        public String getMotherId() {
            return motherId;
        }

        public void setMotherId(String motherId) {
            this.motherId = motherId;
        }

        public String getHusbandName() {
            return husbandName;
        }

        public void setHusbandName(String husbandName) {
            this.husbandName = husbandName;
        }

        public String getAadhar() {
            return aadhar;
        }

        public void setAadhar(String aadhar) {
            this.aadhar = aadhar;
        }

        public String getJanAadhar() {
            return janAadhar;
        }

        public void setJanAadhar(String janAadhar) {
            this.janAadhar = janAadhar;
        }

        public String getLMPDate() {
            return lMPDate;
        }

        public void setLMPDate(String lMPDate) {
            this.lMPDate = lMPDate;
        }

        public String getREGDate() {
            return rEGDate;
        }

        public void setREGDate(String rEGDate) {
            this.rEGDate = rEGDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
