package com.poc.weatherdata.model;


import lombok.Data;

@Data
public class QueryResult {
    private Metric metric;
    private Statistic statistic;
    private Double value;
    private String sensor;

    public QueryResult(Metric metric, Statistic statistic, Double value, String sensor) {
        this.metric = metric;
        this.statistic = statistic;
        this.value = value;
        this.sensor = sensor;
    }
}
