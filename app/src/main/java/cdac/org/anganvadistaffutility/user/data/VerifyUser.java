package cdac.org.anganvadistaffutility.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyUser {

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
        private Empdata empdata;

        public Empdata getEmpdata() {
            return empdata;
        }

        public void setEmpdata(Empdata empdata) {
            this.empdata = empdata;
        }
    }

    public static class Empdata {

        @SerializedName("empid")
        @Expose
        private String empid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("mobileno")
        @Expose
        private String mobileno;
        @SerializedName("awcid")
        @Expose
        private String awcid;
        @SerializedName("passwordset")
        @Expose
        private Boolean passwordset;

        public String getAwcid() {
            return awcid;
        }

        public void setAwcid(String awcid) {
            this.awcid = awcid;
        }

        public Boolean getPasswordset() {
            return passwordset;
        }

        public void setPasswordset(Boolean passwordset) {
            this.passwordset = passwordset;
        }

        public String getEmpid() {
            return empid;
        }

        public void setEmpid(String empid) {
            this.empid = empid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }
    }
}