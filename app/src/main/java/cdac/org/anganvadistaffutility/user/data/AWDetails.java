package cdac.org.anganvadistaffutility.user.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AWDetails {

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
        private Data_ data;

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

        public Data_ getData() {
            return data;
        }

        public void setData(Data_ data) {
            this.data = data;
        }

    }

    public static class Data_ {

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

    public static class Empdata implements Parcelable {

        @SerializedName("tjam_awc_name_english")
        @Expose
        private String tjamAwcNameEnglish;
        @SerializedName("tjam_awc_name_hindi")
        @Expose
        private String tjamAwcNameHindi;
        @SerializedName("tjpm_project_name_english")
        @Expose
        private String tjpmProjectNameEnglish;
        @SerializedName("tjpm_project_name_hindi")
        @Expose
        private Object tjpmProjectNameHindi;
        @SerializedName("tjpm_district_id")
        @Expose
        private String tjpmDistrictId;
        @SerializedName("tjsm_sector_name_english")
        @Expose
        private String tjsmSectorNameEnglish;
        @SerializedName("tjsm_sector_name_hindi")
        @Expose
        private String tjsmSectorNameHindi;
        @SerializedName("taem_employee_name_english")
        @Expose
        private String taemEmployeeNameEnglish;
        @SerializedName("taem_employee_name_hindi")
        @Expose
        private Object taemEmployeeNameHindi;
        @SerializedName("taem_employee_id")
        @Expose
        private String taemEmployeeId;

        protected Empdata(Parcel in) {
            tjamAwcNameEnglish = in.readString();
            tjamAwcNameHindi = in.readString();
            tjpmProjectNameEnglish = in.readString();
            tjpmDistrictId = in.readString();
            tjsmSectorNameEnglish = in.readString();
            tjsmSectorNameHindi = in.readString();
            taemEmployeeNameEnglish = in.readString();
            taemEmployeeId = in.readString();
        }

        public static final Creator<Empdata> CREATOR = new Creator<Empdata>() {
            @Override
            public Empdata createFromParcel(Parcel in) {
                return new Empdata(in);
            }

            @Override
            public Empdata[] newArray(int size) {
                return new Empdata[size];
            }
        };

        public String getTjamAwcNameEnglish() {
            return tjamAwcNameEnglish;
        }

        public void setTjamAwcNameEnglish(String tjamAwcNameEnglish) {
            this.tjamAwcNameEnglish = tjamAwcNameEnglish;
        }

        public String getTjamAwcNameHindi() {
            return tjamAwcNameHindi;
        }

        public void setTjamAwcNameHindi(String tjamAwcNameHindi) {
            this.tjamAwcNameHindi = tjamAwcNameHindi;
        }

        public String getTjpmProjectNameEnglish() {
            return tjpmProjectNameEnglish;
        }

        public void setTjpmProjectNameEnglish(String tjpmProjectNameEnglish) {
            this.tjpmProjectNameEnglish = tjpmProjectNameEnglish;
        }

        public Object getTjpmProjectNameHindi() {
            return tjpmProjectNameHindi;
        }

        public void setTjpmProjectNameHindi(Object tjpmProjectNameHindi) {
            this.tjpmProjectNameHindi = tjpmProjectNameHindi;
        }

        public String getTjpmDistrictId() {
            return tjpmDistrictId;
        }

        public void setTjpmDistrictId(String tjpmDistrictId) {
            this.tjpmDistrictId = tjpmDistrictId;
        }

        public String getTjsmSectorNameEnglish() {
            return tjsmSectorNameEnglish;
        }

        public void setTjsmSectorNameEnglish(String tjsmSectorNameEnglish) {
            this.tjsmSectorNameEnglish = tjsmSectorNameEnglish;
        }

        public String getTjsmSectorNameHindi() {
            return tjsmSectorNameHindi;
        }

        public void setTjsmSectorNameHindi(String tjsmSectorNameHindi) {
            this.tjsmSectorNameHindi = tjsmSectorNameHindi;
        }

        public String getTaemEmployeeNameEnglish() {
            return taemEmployeeNameEnglish;
        }

        public void setTaemEmployeeNameEnglish(String taemEmployeeNameEnglish) {
            this.taemEmployeeNameEnglish = taemEmployeeNameEnglish;
        }

        public Object getTaemEmployeeNameHindi() {
            return taemEmployeeNameHindi;
        }

        public void setTaemEmployeeNameHindi(Object taemEmployeeNameHindi) {
            this.taemEmployeeNameHindi = taemEmployeeNameHindi;
        }

        public String getTaemEmployeeId() {
            return taemEmployeeId;
        }

        public void setTaemEmployeeId(String taemEmployeeId) {
            this.taemEmployeeId = taemEmployeeId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(tjamAwcNameEnglish);
            parcel.writeString(tjamAwcNameHindi);
            parcel.writeString(tjpmProjectNameEnglish);
            parcel.writeString(tjpmDistrictId);
            parcel.writeString(tjsmSectorNameEnglish);
            parcel.writeString(tjsmSectorNameHindi);
            parcel.writeString(taemEmployeeNameEnglish);
            parcel.writeString(taemEmployeeId);
        }
    }
}

