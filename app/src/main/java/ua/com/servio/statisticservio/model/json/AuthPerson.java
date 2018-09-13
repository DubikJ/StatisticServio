package ua.com.servio.statisticservio.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthPerson {

    @Expose
    @SerializedName("AppGUID")
    private String appGUID;
    @Expose
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @Expose
    @SerializedName("Password")
    private String password;

    public AuthPerson(String appGUID, String phoneNumber, String password) {
        this.appGUID = appGUID;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getAppGUID() {
        return appGUID;
    }

    public void setAppGUID(String appGUID) {
        this.appGUID = appGUID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
