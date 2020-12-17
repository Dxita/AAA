package cdac.org.anganvadistaffutility.user.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AanganwadiBuildingData  {

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

        @SerializedName("infrastructureData")
        @Expose
        private List<InfrastructureDatum> infrastructureData = null;

        public List<InfrastructureDatum> getInfrastructureData() {
            return infrastructureData;
        }

        public void setInfrastructureData(List<InfrastructureDatum> infrastructureData) {
            this.infrastructureData = infrastructureData;
        }


        public static class InfrastructureDatum implements Parcelable {

            @SerializedName("tid_tim_infra_id")
            @Expose
            private String tidTimInfraId;
            @SerializedName("tid_infra_detail_id")
            @Expose
            private String tidInfraDetailId;
            @SerializedName("tid_infra_namee")
            @Expose
            private String tidInfraNamee;
            @SerializedName("tid_infra_nameh")
            @Expose
            private String tidInfraNameh;
            @SerializedName("tjaid_qty")
            @Expose
            private String tjaidQty;
            @SerializedName("status")
            @Expose
            private String status;

            protected InfrastructureDatum(Parcel in) {
                tidTimInfraId = in.readString();
                tidInfraDetailId = in.readString();
                tidInfraNamee = in.readString();
                tidInfraNameh = in.readString();
                tjaidQty = in.readString();
                status = in.readString();
            }

            public static final Creator<InfrastructureDatum> CREATOR = new Creator<InfrastructureDatum>() {
                @Override
                public InfrastructureDatum createFromParcel(Parcel in) {
                    return new InfrastructureDatum(in);
                }

                @Override
                public InfrastructureDatum[] newArray(int size) {
                    return new InfrastructureDatum[size];
                }
            };

            public String getTidTimInfraId() {
                return tidTimInfraId;
            }

            public void setTidTimInfraId(String tidTimInfraId) {
                this.tidTimInfraId = tidTimInfraId;
            }

            public String getTidInfraDetailId() {
                return tidInfraDetailId;
            }

            public void setTidInfraDetailId(String tidInfraDetailId) {
                this.tidInfraDetailId = tidInfraDetailId;
            }

            public String getTidInfraNamee() {
                return tidInfraNamee;
            }

            public void setTidInfraNamee(String tidInfraNamee) {
                this.tidInfraNamee = tidInfraNamee;
            }

            public String getTidInfraNameh() {
                return tidInfraNameh;
            }

            public void setTidInfraNameh(String tidInfraNameh) {
                this.tidInfraNameh = tidInfraNameh;
            }

            public String getTjaidQty() {
                return tjaidQty;
            }

            public void setTjaidQty(String tjaidQty) {
                this.tjaidQty = tjaidQty;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(tidTimInfraId);
                dest.writeString(tidInfraDetailId);
                dest.writeString(tidInfraNamee);
                dest.writeString(tidInfraNameh);
                dest.writeString(tjaidQty);
                dest.writeString(status);
            }
        }

    }
}
