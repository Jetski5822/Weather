package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class Example {
    public static void main(String[] args) {
        // This is just an example of using the 3rd party API - delete this class before submission.
        IWeatherService forecaster = new CachedWeatherService(new WeatherService(), null);

        Forecast londonForecast = forecaster.getForecast(Region.LONDON, Day.MONDAY);

        System.out.println("London outlook: " + londonForecast.summary());
        System.out.println("London temperature: " + londonForecast.temperature());forecaster.getForecast(Region.LONDON, Day.MONDAY);

        System.out.println("London outlook: " + londonForecast.summary());
        System.out.println("London temperature: " + londonForecast.temperature());forecaster.getForecast(Region.LONDON, Day.MONDAY);

        System.out.println("London outlook: " + londonForecast.summary());
        System.out.println("London temperature: " + londonForecast.temperature());forecaster.getForecast(Region.LONDON, Day.MONDAY);

        System.out.println("London outlook: " + londonForecast.summary());
        System.out.println("London temperature: " + londonForecast.temperature());forecaster.getForecast(Region.LONDON, Day.MONDAY);

        System.out.println("London outlook: " + londonForecast.summary());
        System.out.println("London temperature: " + londonForecast.temperature());
    }
}
