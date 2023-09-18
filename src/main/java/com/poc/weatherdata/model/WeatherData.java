package com.poc.weatherdata.model;

import java.time.Instant;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Getter
@Setter
@ToString
@Entity
@Table(name = "weatherData")
public class WeatherData extends RepresentationModel<WeatherData> {

    @Id
    @Column(name = "weather_data_id", nullable = false, unique = true)
    private UUID weatherDataId;
    @Column(name = "station_id", nullable = false)
    private UUID stationId;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "temperature", nullable = false)
    public double temperature;

    @Column(name = "humidity", nullable = false)
    private double humidity;
    @Column(name = "wind", nullable = false)
    private double wind;


    public WeatherData() {
    }

    public WeatherData(UUID station, Instant timestamp, double temperature, double humidity, double wind) {
        this.stationId = station;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.timestamp = timestamp;
    }


}