package com.baidu.ssp.querys.conditions;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import java.util.Collection;

/**
 * @author mojie
 * @since 10/23 0023
 */
public final class Conditions {

    public static Condition in(String field, Collection values) {
        if (values.isEmpty()) {
            return raw("1 = 2");
        }
        return new BasicOperation(field, "in", String.format("(%s)", Joiner.on(",").join(Iterables.transform(values, VALUE_TO_SQL_FUNCTION))));
    }

    public static Condition notIn(String field, Collection values) {
        if (values.isEmpty()) {
            return raw("1 = 1");
        }
        return new BasicOperation(field, "not in", String.format("(%s)", Joiner.on(",").join(Iterables.transform(values, VALUE_TO_SQL_FUNCTION))));
    }

    public static Condition equal(String field, Object value) {
        return new BasicOperation(field, "=", VALUE_TO_SQL_FUNCTION.apply(value));
    }

    public static Condition notEqual(String field, Object value) {
        return new BasicOperation(field, "!=", VALUE_TO_SQL_FUNCTION.apply(value));
    }

    public static Condition greatOrEqual(String field, Object value) {
        return new BasicOperation(field, ">=", VALUE_TO_SQL_FUNCTION.apply(value));
    }

    public static Condition lessOrEqual(String field, Object value) {
        return new BasicOperation(field, "<=", VALUE_TO_SQL_FUNCTION.apply(value));
    }

    public static Condition not(Condition condition) {
        return new Not(condition);
    }

    public static Condition raw(String sql) {
        return new Raw(sql);
    }

    public static final Function<Object, String> VALUE_TO_SQL_FUNCTION = new Function<Object, String>() {
        public String apply(Object input) {
            if (input == null) {
                return "null";
            }
            if (input instanceof String) {
                return "'" + input + "'";
            }
            return input.toString();
        }
    };
}
