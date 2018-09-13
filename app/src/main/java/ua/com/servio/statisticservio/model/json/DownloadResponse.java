package ua.com.servio.statisticservio.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadResponse {

    @Expose
    @SerializedName("Bands")
    private List<Band> bands;

    public DownloadResponse(List<Band> bands) {
        this.bands = bands;
    }

    public List<Band> getBands() {
        return bands;
    }
}

