package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class PutYourTestCodeInThisDirectoryTest {

    IWeatherService weather;
    SystemClock clock;

    @Before
    public void Setup()
    {
        this.weather = mock(WeatherService.class);
        when(weather.getForecast(Region.LONDON, Day.MONDAY)).thenReturn(new Forecast("Cold",2));
        this.clock = mock(SystemClock.class);
        when(clock.getUtcNow()).thenReturn(new Date(2000, 1, 1,1,0));
    }

    @Test
    public void shouldRetrieveForecastForLondonOnMonday() {
        IWeatherService cachedWeather = new CachedWeatherService(weather, clock);
        Forecast forecast = cachedWeather.getForecast(Region.LONDON, Day.MONDAY);

        assertEquals("Cold", forecast.summary());
        assertEquals(2, forecast.temperature());

        verify(weather, times(1)).getForecast(Region.LONDON, Day.MONDAY);
    }

    @Test
    public void shouldRetrieveForecastForLondonOnMondayFromCache() {
        IWeatherService cachedWeather = new CachedWeatherService(weather, clock);
        cachedWeather.getForecast(Region.LONDON, Day.MONDAY);

        Forecast forecast = cachedWeather.getForecast(Region.LONDON, Day.MONDAY);

        assertEquals("Cold", forecast.summary());
        assertEquals(2, forecast.temperature());

        verify(weather, times(1)).getForecast(Region.LONDON, Day.MONDAY);
    }


    @Test
    public void shouldRetrieveForecastForLondonOnTuesdayFromCache() {
        when(weather.getForecast(Region.LONDON, Day.TUESDAY)).thenReturn(new Forecast("Cold",4));

        IWeatherService cachedWeather = new CachedWeatherService(weather, clock);
        cachedWeather.getForecast(Region.LONDON, Day.MONDAY);

        Forecast forecast = cachedWeather.getForecast(Region.LONDON, Day.TUESDAY);

        assertEquals("Cold", forecast.summary());
        assertEquals(4, forecast.temperature());

        verify(weather).getForecast(Region.LONDON, Day.MONDAY);
        verify(weather).getForecast(Region.LONDON, Day.TUESDAY);
    }


    @Test
    public void shouldRetrieveForecastTwiceForLondonOnMondayAfterItGetsEvictedFromCache() {
        when(weather.getForecast(Region.LONDON, Day.TUESDAY)).thenReturn(new Forecast("Warm",4));
        when(weather.getForecast(Region.LONDON, Day.WEDNESDAY)).thenReturn(new Forecast("Hot",7));
        when(weather.getForecast(Region.LONDON, Day.THURSDAY)).thenReturn(new Forecast("Freezzzing",-10));


        IWeatherService cachedWeather = new CachedWeatherService(weather, clock);

        cachedWeather.getForecast(Region.LONDON, Day.MONDAY);
        cachedWeather.getForecast(Region.LONDON, Day.TUESDAY);
        cachedWeather.getForecast(Region.LONDON, Day.WEDNESDAY);
        cachedWeather.getForecast(Region.LONDON, Day.THURSDAY);
        cachedWeather.getForecast(Region.LONDON, Day.MONDAY);

        verify(weather, times(2)).getForecast(Region.LONDON, Day.MONDAY);
        verify(weather).getForecast(Region.LONDON, Day.TUESDAY);
        verify(weather).getForecast(Region.LONDON, Day.WEDNESDAY);
        verify(weather).getForecast(Region.LONDON, Day.THURSDAY);
    }

    @Test
    public void shouldRetrieveForecastForLondonOnTuesdayFromServiceOncePushedOutOfCache() {
        when(weather.getForecast(Region.LONDON, Day.TUESDAY)).thenReturn(new Forecast("Warm",4));

        IWeatherService cachedWeather = new CachedWeatherService(weather, clock);

        cachedWeather.getForecast(Region.LONDON, Day.MONDAY);
        cachedWeather.getForecast(Region.LONDON, Day.TUESDAY);

        when(clock.getUtcNow()).thenReturn(new Date(2000, 1, 1,2,1));
        cachedWeather.getForecast(Region.LONDON, Day.TUESDAY);

        verify(weather).getForecast(Region.LONDON, Day.MONDAY);
        verify(weather, times(2)).getForecast(Region.LONDON, Day.TUESDAY);
    }
}
