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


    public WeatherData createWeatherData(WeatherData weatherData){
        weatherData.setWeatherDataId(UUID.randomUUID());
        weatherDataRepository.save(weatherData);
        return weatherData;
    }

    public List<QueryResult> getWeatherData(List<Metric> metrics, Statistic statistic, List<UUID> stations, Instant start, Instant end) throws IllegalArgumentException {
        List<WeatherData> weatherData;
        List<QueryResult> result = new ArrayList<>();

        if ((start == null && end != null) || (start != null && end == null)) {
            throw new IllegalArgumentException("A start and end date are required");
        }
        if (start == null && end == null) {  // Default to return latest data
            Integer hoursAgo = 12;
            start = Instant.now().minus(hoursAgo, ChronoUnit.HOURS);
            end = Instant.now();
        }

        if (stations != null) {
            weatherData = weatherDataRepository.findByStationIdInAndTimestampBetween(stations, start, end);
        } else {
            weatherData = weatherDataRepository.findByTimestampBetween(start, end);
        }

        for (Metric metric : metrics) {
            List<QueryResult> queryResult = getQueryResult(metric, statistic, stations, weatherData);
            result.addAll(queryResult);
        }

        return result;
    }


    public List<QueryResult> getQueryResult(Metric metric, Statistic statistic, List<UUID> stations, List<WeatherData> weatherData){
        List<QueryResult> result = new ArrayList<>();
        if (weatherData.size() > 0 ){
            if (stations == null){
                List<Double> filteredData = filterData(metric, weatherData);
                Double value = getData(statistic, filteredData);
                QueryResult stationResult = new QueryResult(metric, statistic, value, "ALL");
                result.add(stationResult);
            } else {
                for (UUID station: stations){
                    List<WeatherData> filteredWeatherData = weatherData.stream()
                            .filter(weatherDatum -> weatherDatum.getStationId().equals(station))
                            .toList();
                    List<Double> filteredData = filterData(metric, filteredWeatherData);
                    if (filteredData.size() > 0){
                        Double value = getData(statistic, filteredData);
                        QueryResult stationResult = new QueryResult(metric, statistic, value, station.toString());
                        result.add(stationResult);
                    }
                }
            }
        }


        return result;
    }

    public List<Double> filterData(Metric metric, List<WeatherData> weatherData) {
        List<Double> data = new ArrayList<>();
        switch (metric) {
            case HUMIDITY:
                for (WeatherData datum : weatherData) {
                    data.add(datum.getHumidity());
                }
                break;
            case TEMPERATURE:
                for (WeatherData datum : weatherData) {
                    data.add(datum.getTemperature());
                }
                break;
            case WIND:
                for (WeatherData datum : weatherData) {
                    data.add(datum.getWind());
                }
                break;
        }
        return data;
    }

    public Double getData(Statistic statistic, List<Double> data){

        Double result = null;

        switch (statistic) {
            case MIN:
                result =Collections.min(data);
                break;

            case MAX:
                result = Collections.max(data);
                break;

            case SUM:
                result = data.stream()
                             .mapToDouble(a -> a)
                             .sum();
                break;

            case AVG:
                result = data.stream()
                             .mapToDouble(a -> a)
                             .sum()/data.size();
                break;
        }

        if (result == null){
            throw new IllegalArgumentException("Unexpected statistic");
        }

        return result;

    }
}
