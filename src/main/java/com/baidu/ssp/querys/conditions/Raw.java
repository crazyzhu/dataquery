package com.baidu.ssp.querys.conditions;

import com.baidu.ssp.tables.Table;

/**
 * raw的sql，直接加入到where的part当中
 * @author mojie
 * @since 10/23 0023
 */
public class Raw implements Condition {

    private String sql;

    public Raw(String sql) {
        this.sql = sql;
    }

    public String toSql(Table table) {
        return sql;
    }
}
