package com.baidu.ssp.querys.conditions;

import com.baidu.ssp.tables.Table;

/**
 * �����ӿ�
 * @author mojie
 * @since 10/23 0023
 */
public interface Condition {
    String toSql(Table table);
}
