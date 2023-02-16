package org.dam.weatherapp.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currently {

    @SerializedName("time")
    @Expose
    private Integer time;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("temperature")
    @Expose
    private Double temperature;

    @SerializedName("humidity")
    @Expose
    private Double humidity;

    @SerializedName("precipProbability")
    @Expose
    private Double precipProbability;

    @SerializedName("summary")
    @Expose
    private String summary;

    public Integer getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getPrecipProbability() {
        return precipProbability;
    }

    public String getSummary() {
        return summary;
    }
}
