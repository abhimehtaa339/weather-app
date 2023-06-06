package com.example.calculator.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout home;
    private ProgressBar loading;
    private TextView cityname;
    private TextView temprature;
    private TextView image_description;
    private TextView feelslike ;
    private ImageView image_for_temp , backiv;
    private TextInputEditText searchbar;
    public ArrayList<weather_modal> arr;
    public  adapter  adapt;
    public RecyclerView forecast;
    private LocationManager locationManager;
    private int permission_code = 1;
    private String city_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);



        home = findViewById(R.id.home);
        cityname = findViewById(R.id.city_name);
        loading = findViewById(R.id.progressbar);
        temprature = findViewById(R.id.Temprature);
        image_description = findViewById(R.id.image_description);
        feelslike = findViewById(R.id.feels_like);
        image_for_temp = findViewById(R.id.image_for_temp);
        searchbar = findViewById(R.id.edit_city);
        backiv = findViewById(R.id.imageView);
        forecast = findViewById(R.id.cards);






        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION},permission_code );
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        city_name = getCityname(location.getLongitude() , location.getLatitude());
        Log.d("city" , city_name);
        getWeatherinfo(city_name);

        arr = new ArrayList<>();
        adapt = new adapter(this , arr);
        forecast.setAdapter(adapt);

        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city  = searchbar.getText().toString();
                Log.d("citys" , city);

                if (city.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter the city name", Toast.LENGTH_SHORT).show();
                }else{
                    cityname.setText(city_name);
                    getWeatherinfo(city);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permission_code){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Please provide the permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private String getCityname(double longitude , double latitude){
        String cityname = "Not found";
        Geocoder geo = new Geocoder(getBaseContext() , Locale.getDefault());
        try{
            List<Address> addresses = geo.getFromLocation(latitude , longitude , 10);

            for (Address ade : addresses){
                if (ade != null){
                    String city =ade.getLocality();
                    if(city != null && !city.equals("")){
                        cityname = city;
                    }else {
                        Log.d("tag" , "city not found");
                        Toast.makeText(this, "City not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cityname;
    }

    private void getWeatherinfo(String cityName){
        String url = "https://api.weatherapi.com/v1/forecast.json?key=997740736a764174b80190252232904&q=" + cityName +"&days=1&aqi=no&alerts=no";
        cityname.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                loading.setVisibility(View.GONE);
                home.setVisibility(View.VISIBLE);
                arr.clear();
                try{
                    String temp = response.getJSONObject("current").getString("temp_c");
                    temprature.setText(temp+"°c");
                    int isday = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    image_description.setText(condition);
                    String conditionicon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("https:".concat(conditionicon)).into(image_for_temp);
                    String feels = response.getJSONObject("current").getString("feelslike_c");
                    feelslike.setText("Feels like ".concat(feels));


                    JSONObject forecastobj = response.getJSONObject("forecast");
                    JSONObject forecast0 = forecastobj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourarray = forecast0.getJSONArray("hour");
                    for (int i = 0 ; i<hourarray.length(); i++){
                        JSONObject hourObj = hourarray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        String tempure = hourObj.getString("temp_c");
                        String wind = hourObj.getString("wind_kph");
                        arr.add(new weather_modal(time , tempure , wind));
                    }

                        adapt.notifyDataSetChanged();

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "plese ", Toast.LENGTH_SHORT).show();
                    String ext = e.toString();
                    Log.d("ttss" , ext);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "please enter a valid city name", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(obj);
    }

}