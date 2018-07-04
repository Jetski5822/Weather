package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

public interface IWeatherService {
    Forecast getForecast(Region london, Day monday);
}
