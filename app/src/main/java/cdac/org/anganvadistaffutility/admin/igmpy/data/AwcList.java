package cdac.org.anganvadistaffutility.admin.igmpy.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AwcList {

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

        @SerializedName("igmpyAwcData")
        @Expose
        private List<IgmpyAwcDatum> igmpyAwcData = null;

        public List<IgmpyAwcDatum> getIgmpyAwcData() {
            return igmpyAwcData;
        }

        public void setIgmpyAwcData(List<IgmpyAwcDatum> igmpyAwcData) {
            this.igmpyAwcData = igmpyAwcData;
        }

    }

    public static class IgmpyAwcDatum {

        @SerializedName("pawcid")
        @Expose
        private String pawcid;
        @SerializedName("awcnamee")
        @Expose
        private String awcnamee;
        @SerializedName("awcnameh")
        @Expose
        private String awcnameh;

        public String getPawcid() {
            return pawcid;
        }

        public void setPawcid(String pawcid) {
            this.pawcid = pawcid;
        }

        public String getAwcnamee() {
            return awcnamee;
        }

        public void setAwcnamee(String awcnamee) {
            this.awcnamee = awcnamee;
        }

        public String getAwcnameh() {
            return awcnameh;
        }

        public void setAwcnameh(String awcnameh) {
            this.awcnameh = awcnameh;
        }

    }
}