package cdac.org.anganvadistaffutility.admin.igmpy.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjList {

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

        @SerializedName("igmpyProjectData")
        @Expose
        private List<IgmpyProjectDatum> igmpyProjectData = null;

        public List<IgmpyProjectDatum> getIgmpyProjectData() {
            return igmpyProjectData;
        }

        public void setIgmpyProjectData(List<IgmpyProjectDatum> igmpyProjectData) {
            this.igmpyProjectData = igmpyProjectData;
        }

    }

    public static class IgmpyProjectDatum {

        @SerializedName("projectname")
        @Expose
        private String projectname;
        @SerializedName("projectnameh")
        @Expose
        private String projectnameh;
        @SerializedName("projectcode")
        @Expose
        private String projectcode;

        public String getProjectname() {
            return projectname;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }

        public String getProjectnameh() {
            return projectnameh;
        }

        public void setProjectnameh(String projectnameh) {
            this.projectnameh = projectnameh;
        }

        public String getProjectcode() {
            return projectcode;
        }

        public void setProjectcode(String projectcode) {
            this.projectcode = projectcode;
        }

    }

}
