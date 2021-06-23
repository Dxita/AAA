package cdac.org.anganvadistaffutility.user.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FoodConsolidateData {

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

        @SerializedName("fooddata")
        @Expose
        private Fooddata fooddata;

        public Fooddata getFooddata() {
            return fooddata;
        }

        public void setFooddata(Fooddata fooddata) {
            this.fooddata = fooddata;
        }

    }

    public static class Fooddata implements Parcelable {

        @SerializedName("tfn_id")
        @Expose
        private String tfnId;
        @SerializedName("tfn_namee")
        @Expose
        private String tfnNamee;
        @SerializedName("tfn_nameh")
        @Expose
        private String tfnNameh;
        @SerializedName("catname")
        @Expose
        private String catname;
        @SerializedName("catnameh")
        @Expose
        private String catnameh;
        @SerializedName("unit")
        @Expose
        private String unit;
        @SerializedName("unith")
        @Expose
        private String unith;
        @SerializedName("tfn_food_nature_namee")
        @Expose
        private String tfnFoodNatureNamee;
        @SerializedName("tfn_food_nature_nameh")
        @Expose
        private String tfnFoodNatureNameh;
        @SerializedName("tfsm_supplier_namee")
        @Expose
        private String tfsmSupplierNamee;
        @SerializedName("tfsm_supplier_nameh")
        @Expose
        private String tfsmSupplierNameh;
        @SerializedName("tfsd_price")
        @Expose
        private String tfsdPrice;
        @SerializedName("unit_id")
        @Expose
        private String unitId;
        @SerializedName("supplier_id")
        @Expose
        private String supplierId;
        @SerializedName("catid")
        @Expose
        private String catid;
        @SerializedName("qty")
        @Expose
        private String qty;

        protected Fooddata(Parcel in) {
            tfnId = in.readString();
            tfnNamee = in.readString();
            tfnNameh = in.readString();
            catname = in.readString();
            catnameh = in.readString();
            unit = in.readString();
            unith = in.readString();
            tfnFoodNatureNamee = in.readString();
            tfnFoodNatureNameh = in.readString();
            tfsmSupplierNamee = in.readString();
            tfsmSupplierNameh = in.readString();
            tfsdPrice = in.readString();
            unitId = in.readString();
            supplierId = in.readString();
            catid = in.readString();
            qty = in.readString();
        }

        public static final Creator<Fooddata> CREATOR = new Creator<Fooddata>() {
            @Override
            public Fooddata createFromParcel(Parcel in) {
                return new Fooddata(in);
            }

            @Override
            public Fooddata[] newArray(int size) {
                return new Fooddata[size];
            }
        };

        public String getTfnId() {
            return tfnId;
        }

        public void setTfnId(String tfnId) {
            this.tfnId = tfnId;
        }

        public String getTfnNamee() {
            return tfnNamee;
        }

        public void setTfnNamee(String tfnNamee) {
            this.tfnNamee = tfnNamee;
        }

        public String getTfnNameh() {
            return tfnNameh;
        }

        public void setTfnNameh(String tfnNameh) {
            this.tfnNameh = tfnNameh;
        }

        public String getCatname() {
            return catname;
        }

        public void setCatname(String catname) {
            this.catname = catname;
        }

        public String getCatnameh() {
            return catnameh;
        }

        public void setCatnameh(String catnameh) {
            this.catnameh = catnameh;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnith() {
            return unith;
        }

        public void setUnith(String unith) {
            this.unith = unith;
        }

        public String getTfnFoodNatureNamee() {
            return tfnFoodNatureNamee;
        }

        public void setTfnFoodNatureNamee(String tfnFoodNatureNamee) {
            this.tfnFoodNatureNamee = tfnFoodNatureNamee;
        }

        public String getTfnFoodNatureNameh() {
            return tfnFoodNatureNameh;
        }

        public void setTfnFoodNatureNameh(String tfnFoodNatureNameh) {
            this.tfnFoodNatureNameh = tfnFoodNatureNameh;
        }

        public String getTfsmSupplierNamee() {
            return tfsmSupplierNamee;
        }

        public void setTfsmSupplierNamee(String tfsmSupplierNamee) {
            this.tfsmSupplierNamee = tfsmSupplierNamee;
        }

        public String getTfsmSupplierNameh() {
            return tfsmSupplierNameh;
        }

        public void setTfsmSupplierNameh(String tfsmSupplierNameh) {
            this.tfsmSupplierNameh = tfsmSupplierNameh;
        }

        public String getTfsdPrice() {
            return tfsdPrice;
        }

        public void setTfsdPrice(String tfsdPrice) {
            this.tfsdPrice = tfsdPrice;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(tfnId);
            dest.writeString(tfnNamee);
            dest.writeString(tfnNameh);
            dest.writeString(catname);
            dest.writeString(catnameh);
            dest.writeString(unit);
            dest.writeString(unith);
            dest.writeString(tfnFoodNatureNamee);
            dest.writeString(tfnFoodNatureNameh);
            dest.writeString(tfsmSupplierNamee);
            dest.writeString(tfsmSupplierNameh);
            dest.writeString(tfsdPrice);
            dest.writeString(unitId);
            dest.writeString(supplierId);
            dest.writeString(catid);
            dest.writeString(qty);
        }
    }

}