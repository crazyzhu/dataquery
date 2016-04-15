package com.baidu.ssp.serializers;

import au.com.bytecode.opencsv.CSVWriter;
import com.baidu.ssp.Frame;
import com.google.common.collect.Lists;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * CSV序列化器
 * @author zhuyijie
 * @date 14-9-22
 */
public class CsvSerializator implements Serializer<byte[]> {


    private Map<String, String> titleMap;

    private List<String> keyOrders;

    private String encoding = "utf8";

    public CsvSerializator(Map<String, String> titleMap, List<String> keyOrders) {
        this.titleMap = titleMap;
        this.keyOrders = keyOrders;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @param frame     需要序列化的frame对象

     */
    @Override
    public byte[] serialize(Frame frame) {
        List<String[]> retVal = Lists.newArrayList();
        List<String> actualKeys = Lists.newArrayList();
        for (String key : keyOrders) {
            if (frame.titles().contains(key)) {
                actualKeys.add(key);
            }
        }
        // the title
        int colSize = actualKeys.size();
        String[] titles = new String[colSize];
        for (int i = 0;i < colSize;i++) {
            titles[i] = titleMap.get(actualKeys.get(i));
        }
        retVal.add(titles);
        // handle the frame
        int rowSize = frame.height();
        for (int i = 0;i < rowSize;i++) {
            Object[] data = frame.row(i).select(actualKeys).values().toArray();
            String[] strData = new String[data.length];
            for (int j = 0;j < colSize;j++) {
                Object value = data[j];
                if (value == null) {
                    value = "--";
                }
                strData[j] = String.valueOf(value);
            }
            retVal.add(strData);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Writer writer;
        try {
            writer = new OutputStreamWriter(out, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeAll(retVal);
        try {
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }
}
