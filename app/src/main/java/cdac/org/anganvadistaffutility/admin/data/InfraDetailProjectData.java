package cdac.org.anganvadistaffutility.admin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfraDetailProjectData {

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

        @SerializedName("infradata")
        @Expose
        private List<Infradatum> infradata = null;

        public List<Infradatum> getInfradata() {
            return infradata;
        }

        public void setInfradata(List<Infradatum> infradata) {
            this.infradata = infradata;
        }

    }

    public static class Infradatum {

        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("tid_infra_namee")
        @Expose
        private String tidInfraNamee;
        @SerializedName("tim_infra_namee")
        @Expose
        private String timInfraNamee;
        @SerializedName("tid_infra_detail_id")
        @Expose
        private String tidInfraDetailId;
        @SerializedName("projectname")
        @Expose
        private String projectname;
        @SerializedName("projectcode")
        @Expose
        private String projectcode;
        @SerializedName("cdpo_name")
        @Expose
        private String cdpoName;
        @SerializedName("cdpo_email")
        @Expose
        private String cdpoEmail;
        @SerializedName("cdpo_mobileno")
        @Expose
        private String cdpoMobileno;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getTidInfraNamee() {
            return tidInfraNamee;
        }

        public void setTidInfraNamee(String tidInfraNamee) {
            this.tidInfraNamee = tidInfraNamee;
        }

        public String getTimInfraNamee() {
            return timInfraNamee;
        }

        public void setTimInfraNamee(String timInfraNamee) {
            this.timInfraNamee = timInfraNamee;
        }

        public String getTidInfraDetailId() {
            return tidInfraDetailId;
        }

        public void setTidInfraDetailId(String tidInfraDetailId) {
            this.tidInfraDetailId = tidInfraDetailId;
        }

        public String getProjectname() {
            return projectname;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }

        public String getProjectcode() {
            return projectcode;
        }

        public void setProjectcode(String projectcode) {
            this.projectcode = projectcode;
        }

        public String getCdpoName() {
            return cdpoName;
        }

        public void setCdpoName(String cdpoName) {
            this.cdpoName = cdpoName;
        }

        public String getCdpoEmail() {
            return cdpoEmail;
        }

        public void setCdpoEmail(String cdpoEmail) {
            this.cdpoEmail = cdpoEmail;
        }

        public String getCdpoMobileno() {
            return cdpoMobileno;
        }

        public void setCdpoMobileno(String cdpoMobileno) {
            this.cdpoMobileno = cdpoMobileno;
        }
    }
}