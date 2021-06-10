package cdac.org.anganvadistaffutility.admin.igmpy.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistList {

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

        @SerializedName("igmpyDistrictData")
        @Expose
        private List<IgmpyDistrictDatum> igmpyDistrictData = null;

        public List<IgmpyDistrictDatum> getIgmpyDistrictData() {
            return igmpyDistrictData;
        }

        public void setIgmpyDistrictData(List<IgmpyDistrictDatum> igmpyDistrictData) {
            this.igmpyDistrictData = igmpyDistrictData;
        }
    }

    public static class IgmpyDistrictDatum {

        @SerializedName("distid")
        @Expose
        private String distid;
        @SerializedName("distnameh")
        @Expose
        private String distnameh;
        @SerializedName("distnamee")
        @Expose
        private String distnamee;

        public String getDistid() {
            return distid;
        }

        public void setDistid(String distid) {
            this.distid = distid;
        }

        public String getDistnameh() {
            return distnameh;
        }

        public void setDistnameh(String distnameh) {
            this.distnameh = distnameh;
        }

        public String getDistnamee() {
            return distnamee;
        }

        public void setDistnamee(String distnamee) {
            this.distnamee = distnamee;
        }

    }
}