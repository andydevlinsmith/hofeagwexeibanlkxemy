package com.poc.weatherdata.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class QueryResult {
    private Metric metric;
    private Statistic statistic;
    private Double value;
    private List<UUID> stations;

    public QueryResult(Metric metric, Statistic statistic, Double value, List<UUID> stations) {
        this.metric = metric;
        this.statistic = statistic;
        this.value = value;
        this.stations = stations != null ? stations : new ArrayList<>();
    }
}
