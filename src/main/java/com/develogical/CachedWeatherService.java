package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CachedWeatherService implements IWeatherService {
    private IWeatherService service;
    private SystemClock clock;
    private Map<String,CacheEntry> cache = new LinkedHashMap<>();

    public CachedWeatherService(IWeatherService service, SystemClock clock) {
        this.service = service;
        this.clock = clock;
    }

    public Forecast getForecast(Region region, Day day) {
        InvalidateCache();

        String key = region.name() + day.toString();

        if (cache.containsKey(key)) {
            return cache.get(key).CachedItem;
        }

        Forecast item = service.getForecast(region,day);

        if(cache.size() == 3) {
            Map.Entry<String,CacheEntry> entry = cache.entrySet().iterator().next();
            cache.remove(entry.getKey());
        }
        CacheEntry cacheItem = new CacheEntry();
        cacheItem.CachedItem = item;
        cacheItem.CacheDate = clock.getUtcNow();

        cache.put(key,cacheItem);

        return item;
    }

    public void InvalidateCache() {


        for (Object key: cache.keySet().toArray()) {
                CacheEntry item = cache.get(key);
                Date currentTime = clock.getUtcNow();
            if(TimeUnit.MILLISECONDS.toHours(currentTime.getTime() - item.CacheDate.getTime()) >= 1) {
                cache.remove(key);
            }
        }
    }
}

