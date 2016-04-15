package com.baidu.ssp.executors.impl;

import com.baidu.ssp.querys.DistinctQuery;
import com.baidu.ssp.querys.conditions.Condition;
import com.baidu.ssp.tables.Table;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojie on 2015/2/10.
 */
public class DistinctQueryExecutor extends AbstractExecutor<DistinctQuery> {
    /**
     * 生成sql
     *
     * @param query
     */
    @Override
    public String getSql(DistinctQuery query) {
        Table table = query.getTable();
        List<String> fields = query.getFields();
        List<String> selectParts = new ArrayList<String>();
        for (String field : fields) {
            selectParts.add(
                    String.format("%s as %s", table.getField(field).getRawExpression(), field));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select distinct ").append(Joiner.on(",").join(selectParts))
                .append(" from ").append(query.getTable().getTableName());
        List<String> whereParts = new ArrayList<String>();
        for (Condition condition : query.getFilters()) {
            whereParts.add(condition.toSql(table));
        }
        if (!whereParts.isEmpty()) {
            stringBuilder.append(" where ")
                    .append(Joiner.on(" and ").join(whereParts));
        }
        return stringBuilder.toString();
    }
}
