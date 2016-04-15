package com.baidu.ssp.serializers;

import com.baidu.ssp.Frame;
import com.baidu.ssp.Row;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mojie on 2015/2/15.
 */
public class MapSerializer implements Serializer<List<Map<String, Object>>> {

    private static final Function<Row, Map<String, Object>> ROW_TO_MAP = new Function<Row, Map<String, Object>>() {
        @Override
        public Map<String, Object> apply(Row input) {
            return input.mapView();
        }
    };

    @Override
    public List<Map<String, Object>> serialize(Frame frame) {
        return new ArrayList<Map<String, Object>>(FluentIterable.from(frame).transform(ROW_TO_MAP).toList());
    }
}
