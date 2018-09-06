package com.example.baskara.customlauncher;

public class WeatherInfo implements Data{
    private String cityField;
    private String updatedField;
    private String detailsField;
    private String currentTemperatureField;
    private String weatherIcon;

    public WeatherInfo(String cityField, String updatedField, String detailsField, String currentTemperatureField, String weatherIcon) {
        this.cityField = cityField;
        this.updatedField = updatedField;
        this.detailsField = detailsField;
        this.currentTemperatureField = currentTemperatureField;
        this.weatherIcon = weatherIcon;
    }

    public String getCityField() {
        return cityField;
    }

    public String getUpdatedField() {
        return updatedField;
    }

    public String getDetailsField() {
        return detailsField;
    }

    public String getCurrentTemperatureField() {
        return currentTemperatureField;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    @Override
    public int getType() {
        return 4;
    }
}
