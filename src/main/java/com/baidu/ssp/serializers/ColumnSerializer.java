package com.baidu.ssp.serializers;

import com.baidu.ssp.Frame;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 按列序列化
 * @author mojie
 * @since 10/28 0028
 */
public class ColumnSerializer implements Serializer<Map<String, List>> {
    @Override
    public Map<String, List> serialize(Frame frame) {
        Map<String, List> result = new HashMap<String, List>();
        for (String title : frame.titles()) {
            result.put(title, Lists.newArrayList(frame.column(title).values()));
        }
        return result;
    }
}
