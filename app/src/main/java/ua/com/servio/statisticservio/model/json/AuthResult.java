package ua.com.servio.statisticservio.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResult {

    @Expose
    @SerializedName("Error")
    private String error;
    @Expose
    @SerializedName("ServiceLink")
    private String serviceLink;

    public AuthResult(String error, String serviceLink) {
        this.error = error;
        this.serviceLink = serviceLink;
    }

    public String getError() {
        return error;
    }

    public String getServiceLink() {
        return serviceLink;
    }
}
