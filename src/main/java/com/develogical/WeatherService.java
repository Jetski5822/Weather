package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class WeatherService implements IWeatherService{
    public Forecast getForecast(Region london, Day monday) {
         Forecaster forecaster = new Forecaster();
         return forecaster.forecastFor(london, monday);
    }
}


