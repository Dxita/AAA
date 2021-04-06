package cdac.org.anganvadistaffutility.admin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AwcData {

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
        private List<Empdatum> empdata = null;

        public List<Empdatum> getEmpdata() {
            return empdata;
        }

        public void setEmpdata(List<Empdatum> empdata) {
            this.empdata = empdata;
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
            @SerializedName("tjpm_project_code")
            @Expose
            private String tjpmProjectCode;
            @SerializedName("tjpm_project_name_english")
            @Expose
            private String tjpmProjectNameEnglish;
            @SerializedName("tjpm_project_name_hindi")
            @Expose
            private String tjpmProjectNameHindi;
            @SerializedName("tjpm_project_incharge_cdpo")
            @Expose
            private String tjpmProjectInchargeCdpo;
            @SerializedName("tjpm_cdpo_email")
            @Expose
            private String tjpmCdpoEmail;
            @SerializedName("tjpm_cdpo_mobile_no")
            @Expose
            private String tjpmCdpoMobileNo;
            @SerializedName("taem_awc_id")
            @Expose
            private String taemAwcId;
            @SerializedName("taem_awc_name_english")
            @Expose
            private String taemAwcNameEnglish;
            @SerializedName("taem_awc_name_hindi")
            @Expose
            private String taemAwcNameHindi;
            @SerializedName("taem_employee_name_english")
            @Expose
            private String taemEmployeeNameEnglish;
            @SerializedName("taem_employee_name_hindi")
            @Expose
            private String taemEmployeeNameHindi;
            @SerializedName("taem_moblie_number")
            @Expose
            private String taemMoblieNumber;
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

            public String getTjpmProjectCode() {
                return tjpmProjectCode;
            }

            public void setTjpmProjectCode(String tjpmProjectCode) {
                this.tjpmProjectCode = tjpmProjectCode;
            }

            public String getTjpmProjectNameEnglish() {
                return tjpmProjectNameEnglish;
            }

            public void setTjpmProjectNameEnglish(String tjpmProjectNameEnglish) {
                this.tjpmProjectNameEnglish = tjpmProjectNameEnglish;
            }

            public String getTjpmProjectNameHindi() {
                return tjpmProjectNameHindi;
            }

            public void setTjpmProjectNameHindi(String tjpmProjectNameHindi) {
                this.tjpmProjectNameHindi = tjpmProjectNameHindi;
            }

            public String getTjpmProjectInchargeCdpo() {
                return tjpmProjectInchargeCdpo;
            }

            public void setTjpmProjectInchargeCdpo(String tjpmProjectInchargeCdpo) {
                this.tjpmProjectInchargeCdpo = tjpmProjectInchargeCdpo;
            }

            public String getTjpmCdpoEmail() {
                return tjpmCdpoEmail;
            }

            public void setTjpmCdpoEmail(String tjpmCdpoEmail) {
                this.tjpmCdpoEmail = tjpmCdpoEmail;
            }

            public String getTjpmCdpoMobileNo() {
                return tjpmCdpoMobileNo;
            }

            public void setTjpmCdpoMobileNo(String tjpmCdpoMobileNo) {
                this.tjpmCdpoMobileNo = tjpmCdpoMobileNo;
            }

            public String getTaemAwcId() {
                return taemAwcId;
            }

            public void setTaemAwcId(String taemAwcId) {
                this.taemAwcId = taemAwcId;
            }

            public String getTaemAwcNameEnglish() {
                return taemAwcNameEnglish;
            }

            public void setTaemAwcNameEnglish(String taemAwcNameEnglish) {
                this.taemAwcNameEnglish = taemAwcNameEnglish;
            }

            public String getTaemAwcNameHindi() {
                return taemAwcNameHindi;
            }

            public void setTaemAwcNameHindi(String taemAwcNameHindi) {
                this.taemAwcNameHindi = taemAwcNameHindi;
            }

            public String getTaemEmployeeNameEnglish() {
                return taemEmployeeNameEnglish;
            }

            public void setTaemEmployeeNameEnglish(String taemEmployeeNameEnglish) {
                this.taemEmployeeNameEnglish = taemEmployeeNameEnglish;
            }

            public String getTaemEmployeeNameHindi() {
                return taemEmployeeNameHindi;
            }

            public void setTaemEmployeeNameHindi(String taemEmployeeNameHindi) {
                this.taemEmployeeNameHindi = taemEmployeeNameHindi;
            }

            public String getTaemMoblieNumber() {
                return taemMoblieNumber;
            }

            public void setTaemMoblieNumber(String taemMoblieNumber) {
                this.taemMoblieNumber = taemMoblieNumber;
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
}


