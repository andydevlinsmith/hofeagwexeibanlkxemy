package com.poc.weatherdata.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.poc.weatherdata.model.Metric;
import com.poc.weatherdata.model.QueryResult;
import com.poc.weatherdata.model.Statistic;
import com.poc.weatherdata.model.WeatherData;
import com.poc.weatherdata.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WeatherDataService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;


    public WeatherData createWeatherData(WeatherData weatherData) {
        weatherData.setWeatherDataId(UUID.randomUUID());
        weatherDataRepository.save(weatherData);
        return weatherData;
    }

    public List<QueryResult> getWeatherData(List<Metric> metrics, Statistic statistic, List<UUID> stations, Instant start, Instant end) throws IllegalArgumentException {
        List<QueryResult> result = new ArrayList<>();

        if ((start == null && end != null) || (start != null && end == null)) {
            throw new IllegalArgumentException("A start and end date are required");
        }
        if (start == null && end == null) {  // Default to return latest data
            Integer hoursAgo = 12;
            start = Instant.now().minus(hoursAgo, ChronoUnit.HOURS);
            end = Instant.now();
        }

        for (Metric metric : metrics) {
            Double val;
            if (stations == null){
                val = getMetricWithoutStations(metric, statistic, start, end);
            } else {
                val = getMetricWithStations(metric, statistic, stations, start, end);
            }
            if (val != null){
                QueryResult qr = new QueryResult(metric, statistic, val, stations);
                result.add(qr);
            }
        }

        return result;
    }

    public Double getMetricWithoutStations(Metric metric, Statistic statistic, Instant start, Instant end) {
        Double queryValue = null;
        if (metric.equals(Metric.HUMIDITY)) {
            if (statistic.equals(Statistic.MIN)) {
                queryValue = weatherDataRepository.findMinHumidity(start, end);
            } else if (statistic.equals(Statistic.MAX)) {
                queryValue = weatherDataRepository.findMaxHumidity(start, end);
            } else if (statistic.equals(Statistic.AVG)) {
                queryValue = weatherDataRepository.findAvgHumidity(start, end);
            } else if (statistic.equals(Statistic.SUM)) {
                queryValue = weatherDataRepository.findSumHumidity(start, end);
            }
        } else if (metric.equals(Metric.TEMPERATURE)) {
            if (statistic.equals(Statistic.MIN)) {
                queryValue = weatherDataRepository.findMinTemp(start, end);
            } else if (statistic.equals(Statistic.MAX)) {
                queryValue = weatherDataRepository.findMaxTemp(start, end);
            } else if (statistic.equals(Statistic.AVG)) {
                queryValue = weatherDataRepository.findAvgTemp(start, end);
            } else if (statistic.equals(Statistic.SUM)) {
                queryValue = weatherDataRepository.findSumTemp(start, end);
            }
        } else if (metric.equals(Metric.WIND)) {
            if (statistic.equals(Statistic.MIN)) {
                queryValue = weatherDataRepository.findMinWind(start, end);
            } else if (statistic.equals(Statistic.MAX)) {
                queryValue = weatherDataRepository.findMaxWind(start, end);
            } else if (statistic.equals(Statistic.AVG)) {
                queryValue = weatherDataRepository.findAvgWind(start, end);
            } else if (statistic.equals(Statistic.SUM)) {
                queryValue = weatherDataRepository.findSumWind(start, end);
            }
        }
        return queryValue;
    }


    public Double getMetricWithStations(Metric metric, Statistic statistic, List<UUID> stations, Instant start, Instant end) {
        Double queryValue = null;
        if (metric.equals(Metric.HUMIDITY)) {
            if (statistic.equals(Statistic.MIN)) {
                queryValue = weatherDataRepository.findMinHumidityWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.MAX)) {
                queryValue = weatherDataRepository.findMaxHumidityWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.AVG)) {
                queryValue = weatherDataRepository.findAvgHumidityWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.SUM)) {
                queryValue = weatherDataRepository.findSumHumidityWithStations(start, end, stations);
            }
        } else if (metric.equals(Metric.TEMPERATURE)) {
            if (statistic.equals(Statistic.MIN)) {
                queryValue = weatherDataRepository.findMinTempWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.MAX)) {
                queryValue = weatherDataRepository.findMaxTempWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.AVG)) {
                queryValue = weatherDataRepository.findAvgTempWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.SUM)) {
                queryValue = weatherDataRepository.findSumTempWithStations(start, end, stations);
            }
        } else if (metric.equals(Metric.WIND)) {
            if (statistic.equals(Statistic.MIN)) {
                queryValue = weatherDataRepository.findMinWindWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.MAX)) {
                queryValue = weatherDataRepository.findMaxWindWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.AVG)) {
                queryValue = weatherDataRepository.findAvgWindWithStations(start, end, stations);
            } else if (statistic.equals(Statistic.SUM)) {
                queryValue = weatherDataRepository.findSumWindWithStations(start, end, stations);
            }
        }

        return queryValue;
    }

}
