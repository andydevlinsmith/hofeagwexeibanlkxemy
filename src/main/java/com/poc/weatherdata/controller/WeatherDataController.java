package com.poc.weatherdata.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.poc.weatherdata.service.WeatherDataService;
import com.poc.weatherdata.model.Metric;
import com.poc.weatherdata.model.QueryResult;
import com.poc.weatherdata.model.Statistic;
import com.poc.weatherdata.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherDataController {

    @Autowired
    private WeatherDataService weatherDataService;


    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity getWeatherData(
            @RequestParam List<Metric> metrics,
            @RequestParam Statistic statistic,
            @RequestParam(required = false) List<UUID> stations,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        Instant start = startDate != null ? startDate.toInstant().truncatedTo( ChronoUnit.DAYS ) : null;
        Instant end = endDate != null ? endDate.toInstant().truncatedTo( ChronoUnit.DAYS ).plus(1, ChronoUnit.DAYS) : null;
        List<QueryResult> weatherData = weatherDataService.getWeatherData(metrics, statistic, stations, start, end);

        return ResponseEntity.ok(weatherData);
    }


    @PostMapping("/data")
    @ResponseBody
    public ResponseEntity postWeatherData(@RequestBody WeatherData weatherData) {
        WeatherData savedData = weatherDataService.createWeatherData(weatherData);
        return ResponseEntity.ok(savedData);
    }

}
