package cdac.org.anganvadistaffutility.admin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistrictEmployeesCount {

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

        @SerializedName("empdata")
        @Expose
        private List<Empdatum> empdata = null;

        public List<Empdatum> getEmpdata() {
            return empdata;
        }

        public void setEmpdata(List<Empdatum> empdata) {
            this.empdata = empdata;
        }

    }

    public static class Empdatum {

        @SerializedName("tjdm_district_id")
        @Expose
        private String tjdmDistrictId;
        @SerializedName("tjdm_district_name_hindi")
        @Expose
        private String tjdmDistrictNameHindi;
        @SerializedName("tjdm_district_name_english")
        @Expose
        private String tjdmDistrictNameEnglish;
        @SerializedName("unregistered")
        @Expose
        private String unregistered;
        @SerializedName("registered")
        @Expose
        private String registered;

        public String getTjdmDistrictId() {
            return tjdmDistrictId;
        }

        public void setTjdmDistrictId(String tjdmDistrictId) {
            this.tjdmDistrictId = tjdmDistrictId;
        }

        public String getTjdmDistrictNameHindi() {
            return tjdmDistrictNameHindi;
        }

        public void setTjdmDistrictNameHindi(String tjdmDistrictNameHindi) {
            this.tjdmDistrictNameHindi = tjdmDistrictNameHindi;
        }

        public String getTjdmDistrictNameEnglish() {
            return tjdmDistrictNameEnglish;
        }

        public void setTjdmDistrictNameEnglish(String tjdmDistrictNameEnglish) {
            this.tjdmDistrictNameEnglish = tjdmDistrictNameEnglish;
        }

        public String getUnregistered() {
            return unregistered;
        }

        public void setUnregistered(String unregistered) {
            this.unregistered = unregistered;
        }

        public String getRegistered() {
            return registered;
        }

        public void setRegistered(String registered) {
            this.registered = registered;
        }

    }
}