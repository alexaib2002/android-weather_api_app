package org.dam.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.dam.weatherapp.retrofitdata.WeatherResult;
import org.dam.weatherapp.retrofitutils.APIRestService;
import org.dam.weatherapp.retrofitutils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoActivity extends AppCompatActivity {
    private TextView locationTv;
    private TextView tempTv;
    private TextView humTv;
    private TextView rainTv;
    private TextView summaryTv;
    private ImageView iconIv;

    public static String exclude = "minutely,hourly,daily,alerts,flags";
    public static String lang = "es";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        locationTv = findViewById(R.id.locationTv);
        tempTv = findViewById(R.id.tempTv);
        humTv = findViewById(R.id.humTv);
        rainTv = findViewById(R.id.rainTv);
        summaryTv = findViewById(R.id.summaryTv);
        iconIv = findViewById(R.id.iconIv);
        requestWeatherResult();
    }

    private void requestWeatherResult() {
        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestService = retrofit.create(APIRestService.class);
        Call<WeatherResult> call = apiRestService.getWeatherResult(
                getIntent().getDoubleExtra("lat", 0),
                getIntent().getDoubleExtra("lon", 0),
                exclude, lang);

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if (response.isSuccessful())
                    fillWeatherResult((WeatherResult) response.body());
                else
                    Log.e("API_REQ_ERROR", String.format("%s - %s", response.code(), response.message()));
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Log.e("API_REQ_ERROR", t.getMessage());
            }
        });
    }

    private void fillWeatherResult(WeatherResult weatherResult) {
        locationTv.setText(weatherResult.getTimezone());
        tempTv.setText(String.format("%s", weatherResult.getCurrently().getTemperature()));
        humTv.setText(String.format("%s%%", weatherResult.getCurrently().getHumidity()));
        rainTv.setText(String.format("%s%%", weatherResult.getCurrently().getPrecipProbability()));
        summaryTv.setText(weatherResult.getCurrently().getSummary());
        iconIv.setImageResource(getResources().getIdentifier(
                weatherResult.getCurrently().getIcon(),
                "drawable",
                getPackageName()));
    }
}