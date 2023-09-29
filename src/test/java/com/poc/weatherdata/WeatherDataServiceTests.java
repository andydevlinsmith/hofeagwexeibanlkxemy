package com.poc.weatherdata;

import com.poc.weatherdata.model.Metric;
import com.poc.weatherdata.model.QueryResult;
import com.poc.weatherdata.model.Statistic;
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
    public void returnsCorrectValuesForMultipleMetrics() throws Exception{
        UUID station = UUID.randomUUID();
        Instant timestamp = Instant.now().minus(6, ChronoUnit.HOURS);
        Instant timestamp1 = Instant.now().minus(5, ChronoUnit.HOURS);

        List<UUID> stations = new ArrayList<>();
        stations.add(station);

        List<Metric> metrics = new ArrayList<>();
        metrics.add(Metric.HUMIDITY);
        metrics.add(Metric.TEMPERATURE);
        metrics.add(Metric.WIND);

        Mockito.when(weatherDataRepository.findMinHumidityWithStations(timestamp, timestamp1, stations)).thenReturn(100.0);
        Mockito.when(weatherDataRepository.findMinTempWithStations(timestamp, timestamp1, stations)).thenReturn(200.0);
        Mockito.when(weatherDataRepository.findMinWindWithStations(timestamp, timestamp1, stations)).thenReturn(300.0);

        Mockito.when(weatherDataRepository.findMaxHumidityWithStations(timestamp, timestamp1, stations)).thenReturn(400.0);
        Mockito.when(weatherDataRepository.findMaxTempWithStations(timestamp, timestamp1, stations)).thenReturn(500.0);
        Mockito.when(weatherDataRepository.findMaxWindWithStations(timestamp, timestamp1, stations)).thenReturn(600.0);

        Mockito.when(weatherDataRepository.findAvgHumidityWithStations(timestamp, timestamp1, stations)).thenReturn(700.0);
        Mockito.when(weatherDataRepository.findAvgTempWithStations(timestamp, timestamp1, stations)).thenReturn(800.0);
        Mockito.when(weatherDataRepository.findAvgWindWithStations(timestamp, timestamp1, stations)).thenReturn(900.0);

        Mockito.when(weatherDataRepository.findSumHumidityWithStations(timestamp, timestamp1, stations)).thenReturn(1000.0);
        Mockito.when(weatherDataRepository.findSumTempWithStations(timestamp, timestamp1, stations)).thenReturn(1100.0);
        Mockito.when(weatherDataRepository.findSumWindWithStations(timestamp, timestamp1, stations)).thenReturn(1200.0);

        List<QueryResult> minQueryResult = weatherDataService.getWeatherData(metrics, Statistic.MIN, stations, timestamp, timestamp1);
        assert(minQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(minQueryResult.get(0).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(0).getValue() == 100.0);
        assert(minQueryResult.get(0).getStations().get(0).equals(station));
        assert(minQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(minQueryResult.get(1).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(1).getValue() == 200.0);
        assert(minQueryResult.get(1).getStations().get(0).equals(station));
        assert(minQueryResult.get(2).getMetric() == Metric.WIND);
        assert(minQueryResult.get(2).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(2).getValue() == 300.0);
        assert(minQueryResult.get(2).getStations().get(0).equals(station));

        List<QueryResult> maxQueryResult = weatherDataService.getWeatherData(metrics, Statistic.MAX, stations, timestamp, timestamp1);
        assert(maxQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(maxQueryResult.get(0).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(0).getValue() == 400.0);
        assert(maxQueryResult.get(0).getStations().get(0).equals(station));
        assert(maxQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(maxQueryResult.get(1).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(1).getValue() == 500.0);
        assert(maxQueryResult.get(1).getStations().get(0).equals(station));
        assert(maxQueryResult.get(2).getMetric() == Metric.WIND);
        assert(maxQueryResult.get(2).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(2).getValue() == 600.0);
        assert(maxQueryResult.get(2).getStations().get(0).equals(station));

        List<QueryResult> avgQueryResult = weatherDataService.getWeatherData(metrics, Statistic.AVG, stations, timestamp, timestamp1);
        assert(avgQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(avgQueryResult.get(0).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(0).getValue() == 700.0);
        assert(avgQueryResult.get(0).getStations().get(0).equals(station));
        assert(avgQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(avgQueryResult.get(1).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(1).getValue() == 800.0);
        assert(avgQueryResult.get(1).getStations().get(0).equals(station));
        assert(avgQueryResult.get(2).getMetric() == Metric.WIND);
        assert(avgQueryResult.get(2).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(2).getValue() == 900.0);
        assert(avgQueryResult.get(2).getStations().get(0).equals(station));

        List<QueryResult> sumQueryResult = weatherDataService.getWeatherData(metrics, Statistic.SUM, stations, timestamp, timestamp1);
        assert(sumQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(sumQueryResult.get(0).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(0).getValue() == 1000.0);
        assert(sumQueryResult.get(0).getStations().get(0).equals(station));
        assert(sumQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(sumQueryResult.get(1).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(1).getValue() == 1100.0);
        assert(sumQueryResult.get(1).getStations().get(0).equals(station));
        assert(sumQueryResult.get(2).getMetric() == Metric.WIND);
        assert(sumQueryResult.get(2).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(2).getValue() == 1200.0);
        assert(sumQueryResult.get(2).getStations().get(0).equals(station));

    }

    @Test
    public void returnsCorrectValuesForAllStations() throws Exception{
        Instant timestamp = Instant.now().minus(6, ChronoUnit.HOURS);
        Instant timestamp1 = Instant.now().minus(5, ChronoUnit.HOURS);

        List<Metric> metrics = new ArrayList<>();
        metrics.add(Metric.HUMIDITY);
        metrics.add(Metric.TEMPERATURE);
        metrics.add(Metric.WIND);

        Mockito.when(weatherDataRepository.findMinHumidity(timestamp, timestamp1)).thenReturn(100.0);
        Mockito.when(weatherDataRepository.findMinTemp(timestamp, timestamp1)).thenReturn(200.0);
        Mockito.when(weatherDataRepository.findMinWind(timestamp, timestamp1)).thenReturn(300.0);

        Mockito.when(weatherDataRepository.findMaxHumidity(timestamp, timestamp1)).thenReturn(400.0);
        Mockito.when(weatherDataRepository.findMaxTemp(timestamp, timestamp1)).thenReturn(500.0);
        Mockito.when(weatherDataRepository.findMaxWind(timestamp, timestamp1)).thenReturn(600.0);

        Mockito.when(weatherDataRepository.findAvgHumidity(timestamp, timestamp1)).thenReturn(700.0);
        Mockito.when(weatherDataRepository.findAvgTemp(timestamp, timestamp1)).thenReturn(800.0);
        Mockito.when(weatherDataRepository.findAvgWind(timestamp, timestamp1)).thenReturn(900.0);

        Mockito.when(weatherDataRepository.findSumHumidity(timestamp, timestamp1)).thenReturn(1000.0);
        Mockito.when(weatherDataRepository.findSumTemp(timestamp, timestamp1)).thenReturn(1100.0);
        Mockito.when(weatherDataRepository.findSumWind(timestamp, timestamp1)).thenReturn(1200.0);

        List<QueryResult> minQueryResult = weatherDataService.getWeatherData(metrics, Statistic.MIN, null, timestamp, timestamp1);
        assert(minQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(minQueryResult.get(0).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(0).getValue() == 100.0);
        assert(minQueryResult.get(0).getStations().isEmpty());
        assert(minQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(minQueryResult.get(1).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(1).getValue() == 200.0);
        assert(minQueryResult.get(1).getStations().isEmpty());
        assert(minQueryResult.get(2).getMetric() == Metric.WIND);
        assert(minQueryResult.get(2).getStatistic() == Statistic.MIN);
        assert(minQueryResult.get(2).getValue() == 300.0);
        assert(minQueryResult.get(2).getStations().isEmpty());

        List<QueryResult> maxQueryResult = weatherDataService.getWeatherData(metrics, Statistic.MAX, null, timestamp, timestamp1);
        assert(maxQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(maxQueryResult.get(0).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(0).getValue() == 400.0);
        assert(maxQueryResult.get(0).getStations().isEmpty());
        assert(maxQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(maxQueryResult.get(1).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(1).getValue() == 500.0);
        assert(maxQueryResult.get(1).getStations().isEmpty());
        assert(maxQueryResult.get(2).getMetric() == Metric.WIND);
        assert(maxQueryResult.get(2).getStatistic() == Statistic.MAX);
        assert(maxQueryResult.get(2).getValue() == 600.0);
        assert(maxQueryResult.get(2).getStations().isEmpty());

        List<QueryResult> avgQueryResult = weatherDataService.getWeatherData(metrics, Statistic.AVG, null, timestamp, timestamp1);
        assert(avgQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(avgQueryResult.get(0).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(0).getValue() == 700.0);
        assert(avgQueryResult.get(0).getStations().isEmpty());
        assert(avgQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(avgQueryResult.get(1).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(1).getValue() == 800.0);
        assert(avgQueryResult.get(1).getStations().isEmpty());
        assert(avgQueryResult.get(2).getMetric() == Metric.WIND);
        assert(avgQueryResult.get(2).getStatistic() == Statistic.AVG);
        assert(avgQueryResult.get(2).getValue() == 900.0);
        assert(avgQueryResult.get(2).getStations().isEmpty());

        List<QueryResult> sumQueryResult = weatherDataService.getWeatherData(metrics, Statistic.SUM, null, timestamp, timestamp1);
        assert(sumQueryResult.get(0).getMetric() == Metric.HUMIDITY);
        assert(sumQueryResult.get(0).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(0).getValue() == 1000.0);
        assert(sumQueryResult.get(0).getStations().isEmpty());
        assert(sumQueryResult.get(1).getMetric() == Metric.TEMPERATURE);
        assert(sumQueryResult.get(1).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(1).getValue() == 1100.0);
        assert(sumQueryResult.get(1).getStations().isEmpty());
        assert(sumQueryResult.get(2).getMetric() == Metric.WIND);
        assert(sumQueryResult.get(2).getStatistic() == Statistic.SUM);
        assert(sumQueryResult.get(2).getValue() == 1200.0);
        assert(sumQueryResult.get(2).getStations().isEmpty());
    }
}
