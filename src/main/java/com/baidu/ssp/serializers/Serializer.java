package com.baidu.ssp.serializers;

import com.baidu.ssp.Frame;

/**
 * Created by mojie on 2015/2/15.
 */
public interface Serializer<T> {
    public T serialize(Frame frame);
}
