package ua.com.servio.statisticservio.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Band {

    @Expose
    @SerializedName("BandName")
    private String bandName;
    @Expose
    @SerializedName("Fields")
    private List<Field> fields;

    public Band(String bandName, List<Field> fields) {
        this.bandName = bandName;
        this.fields = fields;
    }

    public String getBandName() {
        return bandName;
    }

    public List<Field> getFields() {
        return fields;
    }
}

