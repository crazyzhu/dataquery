package com.baidu.ssp.executors.impl;

import com.baidu.ssp.querys.MetricQuery;
import com.baidu.ssp.querys.conditions.Condition;
import com.baidu.ssp.tables.Table;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojie on 2015/2/10.
 */
public class MetricQueryExecutor extends AbstractExecutor<MetricQuery> {
    /**
     * 生成sql
     *
     * @param query
     */
    @Override
    public String getSql(MetricQuery query) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ").append(selectClause(query))
                .append(" from ").append(query.getTable().getTableName())
                .append(" where ").append(whereClause(query));
        String groupClause = groupClause(query);
        if (!groupClause.isEmpty()) {
            stringBuilder.append(" group by ").append(groupClause);
        }
        return stringBuilder.toString();
    }

    private static String selectClause(MetricQuery query) {
        Table table = query.getTable();
        List<String> selectParts = new ArrayList<String>();
        // process dimensions
        List<String> dimensions = query.getDimensions();
        for (String dimension : dimensions) {
            selectParts.add(String.format("%s as %s", table.getField(dimension).getRawExpression(), dimension));
        }
        // process metrics
        List<String> metrics = query.getMetrics();
        for (String metric : metrics) {
            selectParts.add(String.format("sum(%s) as %s", table.getField(metric).getRawExpression(), metric));
        }
        return Joiner.on(',').join(selectParts);
    }

    private static String whereClause(MetricQuery query) {

        List<String> whereParts = new ArrayList<String>();
        List<Condition> filters = query.getFilters();
        for (Condition aFilter : filters) {
            whereParts.add(aFilter.toSql(query.getTable()));
        }
        return Joiner.on(" and ").join(whereParts);
    }

    private static String groupClause(MetricQuery query) {
        List<String> dimensions = query.getDimensions();
        List<String> groupByParts = new ArrayList<String>(dimensions);
        return Joiner.on(',').join(groupByParts);
    }
}
