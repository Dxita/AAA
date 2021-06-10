package cdac.org.anganvadistaffutility.admin.igmpy.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SecList {

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

        @SerializedName("igmpySectorData")
        @Expose
        private List<IgmpySectorDatum> igmpySectorData = null;

        public List<IgmpySectorDatum> getIgmpySectorData() {
            return igmpySectorData;
        }

        public void setIgmpySectorData(List<IgmpySectorDatum> igmpySectorData) {
            this.igmpySectorData = igmpySectorData;
        }

    }

    public static class IgmpySectorDatum {

        @SerializedName("sectorid")
        @Expose
        private String sectorid;
        @SerializedName("secnamee")
        @Expose
        private String secnamee;
        @SerializedName("secnameh")
        @Expose
        private String secnameh;

        public String getSectorid() {
            return sectorid;
        }

        public void setSectorid(String sectorid) {
            this.sectorid = sectorid;
        }

        public String getSecnamee() {
            return secnamee;
        }

        public void setSecnamee(String secnamee) {
            this.secnamee = secnamee;
        }

        public String getSecnameh() {
            return secnameh;
        }

        public void setSecnameh(String secnameh) {
            this.secnameh = secnameh;
        }

    }
}