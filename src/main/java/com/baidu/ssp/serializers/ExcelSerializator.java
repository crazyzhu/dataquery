package com.baidu.ssp.serializers;

import com.baidu.ssp.Frame;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * excel序列化器
 * @author zhuyijie
 * @date 14-12-18
 *
 */
public class ExcelSerializator implements Serializer<byte[]> {

    private Map<String, String> titleMap;

    private List<String> keyOrders;

    public ExcelSerializator(Map<String, String> titleMap, List<String> keyOrders) {
        this.titleMap = titleMap;
        this.keyOrders = keyOrders;
    }

    @Override
    public byte[] serialize(Frame frame) {
        List<String> actualKeys = Lists.newArrayList();
        for (String key : keyOrders) {
            if (frame.titles().contains(key)) {
                actualKeys.add(key);
            }
        }
        int numOfCols = actualKeys.size();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        for (int i = 0;i < numOfCols;i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(titleMap.get(actualKeys.get(i)));
        }
        for (int iRow = 0;iRow < frame.height();iRow++) {
            Row dataRow = sheet.createRow(iRow + 1);
            com.baidu.ssp.Row frameRow = frame.row(iRow);
            for (int iData = 0;iData < numOfCols;iData++) {
                Cell dataCell = dataRow.createCell(iData);
                Object value = frameRow.select(actualKeys.get(iData));
                if (value == null) {
                    value = "--";
                }
                dataCell.setCellValue(String.valueOf(value));
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
