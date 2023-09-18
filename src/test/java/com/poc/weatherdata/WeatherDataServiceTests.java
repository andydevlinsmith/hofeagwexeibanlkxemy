package com.poc.weatherdata;

import com.poc.weatherdata.model.Metric;
import com.poc.weatherdata.model.QueryResult;
import com.poc.weatherdata.model.Statistic;
import com.poc.weatherdata.model.WeatherData;
import com.poc.weatherdata.repository.WeatherDataRepository;
import com.poc.weatherdata.service.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WeatherDataServiceTests {

    @Mock
    WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private WeatherDataService weatherDataService = new WeatherDataService();
    
    @Test
    public void returnsCorrectValuesForOneStation() throws Exception{
        UUID station = UUID.randomUUID();
        Instant timestamp = Instant.now().minus(6, ChronoUnit.HOURS);
        Double humidity = 100.0;
        Double temperature = 200.0;
        Double wind = 300.0;
        WeatherData wd = new WeatherData(station, timestamp, temperature, humidity, wind);

        Instant timestamp1 = Instant.now().minus(5, ChronoUnit.HOURS);
        Double humidity1 = 400.0;
        Double temperature1 = 500.0;
        Double wind1 = 600.0;
        WeatherData wd1 = new WeatherData(station, timestamp1, temperature1, humidity1, wind1);

        List<WeatherData> result = new ArrayList<>();
        result.add(wd);
        result.add(wd1);

        List<UUID> stations = new ArrayList<>();
        stations.add(station);

        Mockito.when(weatherDataRepository.findByStationIdInAndTimestampBetween(stations, timestamp, timestamp1))
                .thenReturn(result);

        List<Metric> metrics = new ArrayList<>();
        metrics.add(Metric.HUMIDITY);
        metrics.add(Metric.TEMPERATURE);
        metrics.add(Metric.WIND);

        List<QueryResult> maxQueryResult = weatherDataService.getWeatherData(metrics, Statistic.MAX, stations, timestamp, timestamp1);
        assert(maxQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(maxQueryResult.get(0).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(0).getValue() == 400.0);
        assert(maxQueryResult.get(0).getSensor().equals(station.toString()));
        assert(maxQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(maxQueryResult.get(1).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(1).getValue() == 500.0);
        assert(maxQueryResult.get(1).getSensor().equals(station.toString()));
        assert(maxQueryResult.get(2).getMetric() == Metric.WIND);
        assert(maxQueryResult.get(2).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(2).getValue() == 600.0);
        assert(maxQueryResult.get(2).getSensor().equals(station.toString()));

        List<QueryResult> minQueryResult = weatherDataService.getWeatherData(metrics, Statistic.MIN, stations, timestamp, timestamp1);
        assert(minQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(minQueryResult.get(0).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(0).getValue() == 100.0);
        assert(minQueryResult.get(0).getSensor().equals(station.toString()));
        assert(minQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(minQueryResult.get(1).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(1).getValue() == 200.0);
        assert(minQueryResult.get(1).getSensor().equals(station.toString()));
        assert(minQueryResult.get(2).getMetric() == Metric.WIND);
        assert(minQueryResult.get(2).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(2).getValue() == 300.0);
        assert(minQueryResult.get(2).getSensor().equals(station.toString()));

        List<QueryResult> sumQueryResult = weatherDataService.getWeatherData(metrics, Statistic.SUM, stations, timestamp, timestamp1);
        assert(sumQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(sumQueryResult.get(0).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(0).getValue() == 500.0);
        assert(sumQueryResult.get(0).getSensor().equals(station.toString()));
        assert(sumQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(sumQueryResult.get(1).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(1).getValue() == 700.0);
        assert(sumQueryResult.get(1).getSensor().equals(station.toString()));
        assert(sumQueryResult.get(2).getMetric() == Metric.WIND);
        assert(sumQueryResult.get(2).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(2).getValue() == 900.0);
        assert(sumQueryResult.get(2).getSensor().equals(station.toString()));

        List<QueryResult> avgQueryResult = weatherDataService.getWeatherData(metrics, Statistic.AVG, stations, timestamp, timestamp1);
        assert(avgQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(avgQueryResult.get(0).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(0).getValue() == 250);
        assert(avgQueryResult.get(0).getSensor().equals(station.toString()));
        assert(avgQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(avgQueryResult.get(1).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(1).getValue() == 350);
        assert(avgQueryResult.get(1).getSensor().equals(station.toString()));
        assert(avgQueryResult.get(2).getMetric() == Metric.WIND);
        assert(avgQueryResult.get(2).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(2).getValue() == 450.0);
        assert(avgQueryResult.get(2).getSensor().equals(station.toString()));
    }

    @Test
    public void returnsCorrectValuesForMultipleStations() throws Exception{

        Instant timestamp = Instant.now().minus(6, ChronoUnit.HOURS);
        Instant timestamp1 = Instant.now().minus(5, ChronoUnit.HOURS);

        UUID station = UUID.randomUUID();
        Double humidity = 0.0;
        Double temperature = 0.0;
        Double wind = 0.0;
        WeatherData wd = new WeatherData(station, timestamp, temperature, humidity, wind);

        Double humidity1 = 100.0;
        Double temperature1 = 200.0;
        Double wind1 = 300.0;
        WeatherData wd1 = new WeatherData(station, timestamp1, temperature1, humidity1, wind1);

        UUID station1 = UUID.randomUUID();
        Double humidity2 = 400.0;
        Double temperature2 = 400.0;
        Double wind2 = 400.0;
        WeatherData wd2 = new WeatherData(station1, timestamp, temperature2, humidity2, wind2);

        Double humidity3 = 100.0;
        Double temperature3 = 200.0;
        Double wind3 = 300.0;
        WeatherData wd3 = new WeatherData(station1, timestamp1, temperature3, humidity3, wind3);

        List<WeatherData> result = new ArrayList<>();
        result.add(wd);
        result.add(wd1);
        result.add(wd2);
        result.add(wd3);

        List<Metric> metrics = new ArrayList<>();
        metrics.add(Metric.HUMIDITY);
        metrics.add(Metric.TEMPERATURE);
        metrics.add(Metric.WIND);
        List<UUID> stations = new ArrayList<>();
        stations.add(station);
        stations.add(station1);


        Mockito.when(weatherDataRepository.findByStationIdInAndTimestampBetween(stations, timestamp, timestamp1))
                .thenReturn(result);

        List<QueryResult> queryResult = weatherDataService.getWeatherData(metrics, Statistic.AVG, stations, timestamp, timestamp1);


        assert(queryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(queryResult.get(0).getStatistic() == Statistic.AVG);
        assert(queryResult.get(0).getValue() == 50.0);
        assert(queryResult.get(0).getSensor().equals(station.toString()));

        assert(queryResult.get(1).getMetric() == Metric.HUMIDITY);
        assert(queryResult.get(1).getStatistic() == Statistic.AVG);
        assert(queryResult.get(1).getValue() == 250.0);
        assert(queryResult.get(1).getSensor().equals(station1.toString()));

        assert(queryResult.get(2).getMetric() == Metric.TEMPERATURE);
        assert(queryResult.get(2).getStatistic() == Statistic.AVG);
        assert(queryResult.get(2).getValue() == 100.0);
        assert(queryResult.get(2).getSensor().equals(station.toString()));

        assert(queryResult.get(3).getMetric() == Metric.TEMPERATURE);
        assert(queryResult.get(3).getStatistic() == Statistic.AVG);
        assert(queryResult.get(3).getValue() == 300.0);
        assert(queryResult.get(3).getSensor().equals(station1.toString()));

        assert(queryResult.get(4).getMetric() == Metric.WIND);
        assert(queryResult.get(4).getStatistic() == Statistic.AVG);
        assert(queryResult.get(4).getValue() == 150.0);
        assert(queryResult.get(4).getSensor().equals(station.toString()));

        assert(queryResult.get(5).getMetric() == Metric.WIND);
        assert(queryResult.get(5).getStatistic() == Statistic.AVG);
        assert(queryResult.get(5).getValue() == 350.0);
        assert(queryResult.get(5).getSensor().equals(station1.toString()));

    }

    @Test
    public void returnsCorrectValuesForAllStations() throws Exception{
        UUID station = UUID.randomUUID();
        Instant timestamp = Instant.now().minus(6, ChronoUnit.HOURS);
        Double humidity = 100.0;
        Double temperature = 200.0;
        Double wind = 300.0;
        WeatherData wd = new WeatherData(station, timestamp, temperature, humidity, wind);

        Instant timestamp1 = Instant.now().minus(5, ChronoUnit.HOURS);
        Double humidity1 = 400.0;
        Double temperature1 = 500.0;
        Double wind1 = 600.0;
        WeatherData wd1 = new WeatherData(station, timestamp1, temperature1, humidity1, wind1);

        List<WeatherData> result = new ArrayList<>();
        result.add(wd);
        result.add(wd1);

        List<UUID> stations = new ArrayList<>();
        stations.add(station);

        Mockito.when(weatherDataRepository.findByTimestampBetween(timestamp, timestamp1))
                .thenReturn(result);

        List<Metric> metrics = new ArrayList<>();
        metrics.add(Metric.HUMIDITY);
        metrics.add(Metric.TEMPERATURE);
        metrics.add(Metric.WIND);

        List<QueryResult> maxQueryResult = weatherDataService.getWeatherData(metrics, Statistic.MAX, null, timestamp, timestamp1);
        assert(maxQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(maxQueryResult.get(0).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(0).getValue() == 400.0);
        assert(maxQueryResult.get(0).getSensor().equals("ALL"));
        assert(maxQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(maxQueryResult.get(1).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(1).getValue() == 500.0);
        assert(maxQueryResult.get(1).getSensor().equals("ALL"));
        assert(maxQueryResult.get(2).getMetric() == Metric.WIND);
        assert(maxQueryResult.get(2).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(2).getValue() == 600.0);
        assert(maxQueryResult.get(2).getSensor().equals("ALL"));

        List<QueryResult> minQueryResult = weatherDataService.getWeatherData(metrics, Statistic.MIN, null, timestamp, timestamp1);
        assert(minQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(minQueryResult.get(0).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(0).getValue() == 100.0);
        assert(minQueryResult.get(0).getSensor().equals("ALL"));
        assert(minQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(minQueryResult.get(1).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(1).getValue() == 200.0);
        assert(minQueryResult.get(1).getSensor().equals("ALL"));
        assert(minQueryResult.get(2).getMetric() == Metric.WIND);
        assert(minQueryResult.get(2).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(2).getValue() == 300.0);
        assert(minQueryResult.get(2).getSensor().equals("ALL"));

        List<QueryResult> sumQueryResult = weatherDataService.getWeatherData(metrics, Statistic.SUM, null, timestamp, timestamp1);
        assert(sumQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(sumQueryResult.get(0).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(0).getValue() == 500.0);
        assert(sumQueryResult.get(0).getSensor().equals("ALL"));
        assert(sumQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(sumQueryResult.get(1).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(1).getValue() == 700.0);
        assert(sumQueryResult.get(1).getSensor().equals("ALL"));
        assert(sumQueryResult.get(2).getMetric() == Metric.WIND);
        assert(sumQueryResult.get(2).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(2).getValue() == 900.0);
        assert(sumQueryResult.get(2).getSensor().equals("ALL"));

        List<QueryResult> avgQueryResult = weatherDataService.getWeatherData(metrics, Statistic.AVG, null, timestamp, timestamp1);
        assert(avgQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(avgQueryResult.get(0).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(0).getValue() == 250);
        assert(avgQueryResult.get(0).getSensor().equals("ALL"));
        assert(avgQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(avgQueryResult.get(1).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(1).getValue() == 350);
        assert(avgQueryResult.get(1).getSensor().equals("ALL"));
        assert(avgQueryResult.get(2).getMetric() == Metric.WIND);
        assert(avgQueryResult.get(2).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(2).getValue() == 450.0);
        assert(avgQueryResult.get(2).getSensor().equals("ALL"));
    }
    
    
}
