package com.baidu.ssp.querys;

import com.baidu.ssp.querys.conditions.Condition;
import com.baidu.ssp.tables.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojie on 2015/2/10.
 */
public class DistinctQuery implements Query {

    private Table table;

    private List<String> fields;

    private List<Condition> filters;

    public DistinctQuery(Table table, List<String> fields) {
        this.table = table;
        this.fields = fields;
        this.filters = new ArrayList<Condition>();
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

    public Table getTable() {
        return table;
    }

    public List<String> getFields() {
        return fields;
    }
}
