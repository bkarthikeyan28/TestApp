package com.example.baskara.customlauncher;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class FetchWeather {
    private static final String OPEN_WEATHER_MAP_API =
            "https://samples.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    private static JSONObject getJSON(Context context, String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city, context.getString(R.string.weather_id)));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

//            connection.addRequestProperty("x-api-key",
//                    context.getString(R.string.weather_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }

    public static WeatherInfo fetchWeather(Context context, String city) throws Exception {
        JSONObject json = getJSON(context, city);

        String cityInfo = json.getString("name").toUpperCase(Locale.US) +
                ", " + json.getJSONObject("sys").getString("country");
        JSONObject details = json.getJSONArray("weather").getJSONObject(0);
        JSONObject main = json.getJSONObject("main");
        String detailsInfo = details.getString("description").toUpperCase(Locale.US) +
                "\n" + "Humidity: " + main.getString("humidity") + "%" +
                "\n" + "Pressure: " + main.getString("pressure") + " hPa";
        String tempInfo = String.format("%.2f", main.getDouble("temp")) + " ℃";
        DateFormat df = DateFormat.getDateTimeInstance();
        String updatedOn = df.format(new Date(json.getLong("dt") * 1000));
        String updatedInfo = "Last update: " + updatedOn;
        String weatherInfo = getWeatherIcon( details.getInt("id"),
        json.getJSONObject("sys").getLong("sunrise") * 1000,
                json.getJSONObject("sys").getLong("sunset") * 1000,context);

        return new WeatherInfo(cityInfo, updatedInfo,detailsInfo,tempInfo,weatherInfo);

    }
    private static String getWeatherIcon(int actualId, long sunrise, long sunset, Context context){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = context.getString(R.string.weather_sunny);
            } else {
                icon = context.getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = context.getString(R.string.weather_thunder);
                    break;
                case 3 : icon = context.getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = context.getString(R.string.weather_foggy);
                    break;
                case 8 : icon = context.getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = context.getString(R.string.weather_snowy);
                    break;
                case 5 : icon = context.getString(R.string.weather_rainy);
                    break;
            }
        }
        return icon;
    }


}
