package ua.com.servio.statisticservio.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadResponse {

    @Expose
    @SerializedName("Error")
    private String error;

    @Expose
    @SerializedName("Bands")
    private List<Band> bands;

    public DownloadResponse(String error, List<Band> bands) {
        this.error = error;
        this.bands = bands;
    }

    public String getError() {
        return error;
    }

    public List<Band> getBands() {
        return bands;
    }
}

