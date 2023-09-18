package com.poc.weatherdata.repository;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.poc.weatherdata.model.WeatherData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends CrudRepository<WeatherData, UUID> {
    List<WeatherData> findByTimestampBetween(Instant startTime, Instant endTime);
    List<WeatherData> findByStationIdInAndTimestampBetween(List<UUID> stations, Instant startTime, Instant endTime);

}
