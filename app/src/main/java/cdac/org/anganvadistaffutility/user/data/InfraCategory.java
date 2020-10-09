package cdac.org.anganvadistaffutility.user.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfraCategory {

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

        @SerializedName("Form_master")
        @Expose
        private List<FormMaster> formMaster = null;

        public List<FormMaster> getFormMaster() {
            return formMaster;
        }

        public void setFormMaster(List<FormMaster> formMaster) {
            this.formMaster = formMaster;
        }

        public static class FormMaster implements Parcelable {

            @SerializedName("tim_infra_id")
            @Expose
            private String timInfraId;
            @SerializedName("tim_infra_namee")
            @Expose
            private String timInfraNamee;
            @SerializedName("tim_infra_nameh")
            @Expose
            private String timInfraNameh;
            @SerializedName("tim_created_date")
            @Expose
            private String timCreatedDate;
            @SerializedName("tim_modified_date")
            @Expose
            private String timModifiedDate;
            @SerializedName("tim_status")
            @Expose
            private String timStatus;
            @SerializedName("tim_user_id")
            @Expose
            private String timUserId;
            @SerializedName("tim_ip_address")
            @Expose
            private Object timIpAddress;

            protected FormMaster(Parcel in) {
                timInfraId = in.readString();
                timInfraNamee = in.readString();
                timInfraNameh = in.readString();
                timCreatedDate = in.readString();
                timModifiedDate = in.readString();
                timStatus = in.readString();
                timUserId = in.readString();
            }

            public static final Creator<FormMaster> CREATOR = new Creator<FormMaster>() {
                @Override
                public FormMaster createFromParcel(Parcel in) {
                    return new FormMaster(in);
                }

                @Override
                public FormMaster[] newArray(int size) {
                    return new FormMaster[size];
                }
            };

            public String getTimInfraId() {
                return timInfraId;
            }

            public void setTimInfraId(String timInfraId) {
                this.timInfraId = timInfraId;
            }

            public String getTimInfraNamee() {
                return timInfraNamee;
            }

            public void setTimInfraNamee(String timInfraNamee) {
                this.timInfraNamee = timInfraNamee;
            }

            public String getTimInfraNameh() {
                return timInfraNameh;
            }

            public void setTimInfraNameh(String timInfraNameh) {
                this.timInfraNameh = timInfraNameh;
            }

            public String getTimCreatedDate() {
                return timCreatedDate;
            }

            public void setTimCreatedDate(String timCreatedDate) {
                this.timCreatedDate = timCreatedDate;
            }

            public String getTimModifiedDate() {
                return timModifiedDate;
            }

            public void setTimModifiedDate(String timModifiedDate) {
                this.timModifiedDate = timModifiedDate;
            }

            public String getTimStatus() {
                return timStatus;
            }

            public void setTimStatus(String timStatus) {
                this.timStatus = timStatus;
            }

            public String getTimUserId() {
                return timUserId;
            }

            public void setTimUserId(String timUserId) {
                this.timUserId = timUserId;
            }

            public Object getTimIpAddress() {
                return timIpAddress;
            }

            public void setTimIpAddress(Object timIpAddress) {
                this.timIpAddress = timIpAddress;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(timInfraId);
                parcel.writeString(timInfraNamee);
                parcel.writeString(timInfraNameh);
                parcel.writeString(timCreatedDate);
                parcel.writeString(timModifiedDate);
                parcel.writeString(timStatus);
                parcel.writeString(timUserId);
            }
        }
    }
}