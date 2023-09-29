package com.poc.weatherdata.repository;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.poc.weatherdata.model.WeatherData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends CrudRepository<WeatherData, UUID> {

    @Query("SELECT MAX(u.temperature) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findMaxTemp(Instant start, Instant end);

    @Query("SELECT MAX(u.temperature) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findMaxTempWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT MIN(u.temperature) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findMinTemp(Instant start, Instant end);

    @Query("SELECT MIN(u.temperature) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findMinTempWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT SUM(u.temperature) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findSumTemp(Instant start, Instant end);

    @Query("SELECT SUM(u.temperature) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findSumTempWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT AVG(u.temperature) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findAvgTemp(Instant start, Instant end);

    @Query("SELECT AVG(u.temperature) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findAvgTempWithStations(Instant start, Instant end, List<UUID> stationIds);




    @Query("SELECT MAX(u.humidity) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findMaxHumidity(Instant start, Instant end);

    @Query("SELECT MAX(u.humidity) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findMaxHumidityWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT MIN(u.humidity) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findMinHumidity(Instant start, Instant end);

    @Query("SELECT MIN(u.humidity) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findMinHumidityWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT SUM(u.humidity) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findSumHumidity(Instant start, Instant end);

    @Query("SELECT SUM(u.humidity) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findSumHumidityWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT AVG(u.humidity) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findAvgHumidity(Instant start, Instant end);

    @Query("SELECT AVG(u.humidity) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findAvgHumidityWithStations(Instant start, Instant end, List<UUID> stationIds);


    @Query("SELECT MAX(u.wind) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findMaxWind(Instant start, Instant end);

    @Query("SELECT MAX(u.wind) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findMaxWindWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT MIN(u.wind) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findMinWind(Instant start, Instant end);

    @Query("SELECT MIN(u.wind) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findMinWindWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT SUM(u.wind) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findSumWind(Instant start, Instant end);

    @Query("SELECT SUM(u.wind) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findSumWindWithStations(Instant start, Instant end, List<UUID> stationIds);

    @Query("SELECT AVG(u.wind) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end")
    Double findAvgWind(Instant start, Instant end);

    @Query("SELECT AVG(u.wind) FROM WeatherData u WHERE u.timestamp BETWEEN :start AND :end AND u.stationId in :stationIds")
    Double findAvgWindWithStations(Instant start, Instant end, List<UUID> stationIds);


}
