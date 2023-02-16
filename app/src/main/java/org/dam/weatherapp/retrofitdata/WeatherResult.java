package org.dam.weatherapp.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResult {

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("currently")
    @Expose
    private Currently currently;

    public String getTimezone() {
        return timezone;
    }

    public Currently getCurrently() {
        return currently;
    }
}
