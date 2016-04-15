package com.baidu.ssp.querys.conditions;

import com.baidu.ssp.tables.Table;

/**
 * 大于或等于
 * @author mojie
 * @since 10/23 0023
 */
public class BasicOperation implements Condition {

    private String field;

    private String operation;

    private Object value;

    public BasicOperation(String field, String operation, Object value) {
        this.field = field;
        this.operation = operation;
        this.value = value;
    }

    public String toSql(Table table) {
        final String tableField = table.getField(field).getRawExpression();
        return String.format("%s %s %s", tableField, operation, value);
    }
}
