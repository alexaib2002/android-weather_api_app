package org.dam.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView latTv;
    private TextView lonTv;

    // https://api.darksky.net/forecast/11ce4328111023379e0fdc9d28c24a02/lat,lon?exclude=minutely,hourly,daily,alerts,flags&lang=es

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latTv = findViewById(R.id.latTv);
        lonTv = findViewById(R.id.lonTv);
        ((Button) findViewById(R.id.requestWeatherBtn)).setOnClickListener((l) -> {
            try {
                Intent weatherIntent = new Intent(this, InfoActivity.class);
                weatherIntent.putExtra("lat", Double.parseDouble(latTv.getText().toString()));
                weatherIntent.putExtra("lon", Double.parseDouble(lonTv.getText().toString()));
                startActivity(weatherIntent);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Latitud y longitud deben ser números", Toast.LENGTH_SHORT).show();
            }
        });
    }
}