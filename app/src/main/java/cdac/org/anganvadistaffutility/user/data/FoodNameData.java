package cdac.org.anganvadistaffutility.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodNameData {

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

        @SerializedName("foodname")
        @Expose
        private List<Foodname> foodname = null;

        public List<Foodname> getFoodname() {
            return foodname;
        }

        public void setFoodname(List<Foodname> foodname) {
            this.foodname = foodname;
        }

    }

    public static class Foodname {

        @SerializedName("tfn_id")
        @Expose
        private String tfnId;
        @SerializedName("tfn_namee")
        @Expose
        private String tfnNamee;
        @SerializedName("tfn_nameh")
        @Expose
        private String tfnNameh;
        @SerializedName("tfn_tft_food_type_id")
        @Expose
        private String tfnTftFoodTypeId;
        @SerializedName("tfn_tfsm_id")
        @Expose
        private String tfnTfsmId;

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

        public String getTfnTftFoodTypeId() {
            return tfnTftFoodTypeId;
        }

        public void setTfnTftFoodTypeId(String tfnTftFoodTypeId) {
            this.tfnTftFoodTypeId = tfnTftFoodTypeId;
        }

        public String getTfnTfsmId() {
            return tfnTfsmId;
        }

        public void setTfnTfsmId(String tfnTfsmId) {
            this.tfnTfsmId = tfnTfsmId;
        }
    }
}
