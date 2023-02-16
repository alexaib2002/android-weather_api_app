package org.dam.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView hourTv;
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
        hourTv = findViewById(R.id.hourTv);
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
                {
                    Log.e("API_REQ_ERROR", String.format("%s - %s", response.code(), response.message()));
                    Toast.makeText(InfoActivity.this, "Ocurrió un error en la respuesta de la API. Revisa los datos introducidos", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Log.e("API_REQ_ERROR", t.getMessage());
                Toast.makeText(InfoActivity.this, "Ocurrió un error durante la llamada a la API", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void fillWeatherResult(WeatherResult weatherResult) {
        locationTv.setText(weatherResult.getTimezone());
        tempTv.setText(String.valueOf(Math.round(weatherResult.getCurrently().getTemperatureCelsius())));
        humTv.setText(String.format("%s%%", weatherResult.getCurrently().getHumidity() * 100));
        rainTv.setText(String.format("%s%%", weatherResult.getCurrently().getPrecipProbability() * 100));
        summaryTv.setText(weatherResult.getCurrently().getSummary());
        // convert UNIX time to AM/PM time format
        hourTv.setText(String.format("%s:%s %s",
                weatherResult.getCurrently().getTime() / 3600 % 12,
                weatherResult.getCurrently().getTime() / 60 % 60,
                weatherResult.getCurrently().getTime() / 3600 < 12 ? "AM" : "PM"));
        int iconResId = getResources().getIdentifier(
                weatherResult.getCurrently().getIcon(),
                "drawable",
                getPackageName());
        if (iconResId != 0)
            iconIv.setImageResource(iconResId);
        else
            Toast.makeText(this, "La API indica un icono no encontrado localmente. El icono no será actualizado", Toast.LENGTH_SHORT).show();
    }
}