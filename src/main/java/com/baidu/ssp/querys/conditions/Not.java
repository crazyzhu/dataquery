package com.baidu.ssp.querys.conditions;

import com.baidu.ssp.tables.Table;

/**
 * @author mojie
 * @since 10/23 0023
 */
public class Not implements Condition {

    private String sql;

    public Not(Condition condition) {
        sql = String.format("!(%s)", condition);
    }

    public String toSql(Table table) {
        return sql;
    }
}
