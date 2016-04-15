package com.baidu.ssp.querys.conditions;

import com.baidu.ssp.tables.Table;

/**
 * 条件接口
 * @author mojie
 * @since 10/23 0023
 */
public interface Condition {
    String toSql(Table table);
}
