package com.baidu.ssp.querys;

import com.baidu.ssp.querys.conditions.Condition;
import com.baidu.ssp.tables.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojie on 2015/2/9.
 */
public class MetricQuery implements Query {

    private Table table;

    private List<String> dimensions;

    private List<String> metrics;


    private List<Condition> filters;


    public MetricQuery(Table table, List<String> dimensions, List<String> metrics) {
        this.table = table;
        this.dimensions = dimensions;
        this.metrics = metrics;
        this.filters = new ArrayList<Condition>();
    }

    public Table getTable() {
        return table;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public List<String> getMetrics() {
        return metrics;
    }


    public void addFilter(Condition valueFilter) {
        this.filters.add(valueFilter);
    }

    public void addFilters(List<Condition> filters) {
        this.filters.addAll(filters);
    }

    public List<Condition> getFilters() {
        return filters;
    }

}
