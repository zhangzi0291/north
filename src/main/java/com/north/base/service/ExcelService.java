package com.north.base.service;

import com.north.utils.CamelToUnderlineUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ExcelService<T> {

    private Logger logger = LoggerFactory.getLogger(ExcelService.class);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeFormatter expprtDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Map<String, String>> importExcel(InputStream inputStream, List<String> cellNames){
        List<Map<String, Object>> list = readExcel(inputStream, cellNames);
        List<Map<String, String>> errorMapList = new ArrayList<>();
        List<T> beanList = getBeanList(list,errorMapList);

        upsertBean(beanList);

        return errorMapList;
    }

    protected List<Map<String, String>> importExcel(InputStream inputStream, List<String> cellNames, Map<String, Object> map) {
        List<Map<String, String>> errorMapList = new ArrayList<>();

        return errorMapList;
    }

    public Workbook getWorkbook(InputStream inputStream) {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            workbook = new XSSFWorkbook();
        }
        return workbook;
    }

    public List<T> getBeanList(List<Map<String, Object>> list, List<Map<String, String>> errorMapList) {
        List<T> rwList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if(i==0){
                continue;
            }
            Map<String, String> errorMap = new HashMap<>();
            try {
                Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

                T bean = getJavaBean(list.get(i), entityClass,(T) entityClass.newInstance(),errorMap);
                checkValue(bean,errorMap);
                if(errorMap.isEmpty()){
                    rwList.add(bean);
                }else{
                    errorMap.put("row",i+1+"");
                    errorMapList.add(errorMap);
                }
            } catch (Exception e) {
                errorMap.put("row",i+1+"");
                errorMapList.add(errorMap);
            }

        }
        return  rwList;
    }

    public void upsertBean(List<T> beans){
        ExecutorService pool = Executors.newFixedThreadPool(10);

        beans.forEach((T bean) ->{

            pool.submit(new Thread(()->{
                try {
                    upsert(bean);
                } catch (Exception e) {
                    logger.error("excel upert error:",e);
                }
            }));

        });
        pool.shutdown();
        while(!pool.isTerminated()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void upsert(T bean) ;

    public int getCellNum(InputStream inputStream) throws Exception {
        Workbook workbook = getWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    public List<Map<String, Object>> readExcel(InputStream inputStream, List<String> cellNames) {
        Workbook workbook = getWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int firstRowNum = sheet.getFirstRowNum();
        Row firstRow = sheet.getRow(firstRowNum);
        if (null == firstRow) {
            logger.error("解析Excel失败，在第一行没有读取到任何数据！");
            return null;
        }

        int rowStart = 0;
        int rowEnd = sheet.getPhysicalNumberOfRows();


        List<Map<String, Object>> resultDataList = new ArrayList<>();

        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = sheet.getRow(rowNum);

            if (null == row) {
                continue;
            }

            Map<String, Object> resultData = convertRowToData(row,cellNames);

            resultDataList.add(resultData);
        }
        return resultDataList;
    }

    private Map<String, Object> convertRowToData(Row row, List<String> cellNames) {
        Map<String, Object> resultData = new HashMap<>();
        int cellFirstNum = 0;
        int cellEndNum = row.getLastCellNum();

        for (int cellNum = cellFirstNum; cellNum < cellEndNum; cellNum++) {
            Cell cell = row.getCell(cellNum);
            String value = convertCellValueToString(cell);
            resultData.put(cellNames.get(cellNum-cellFirstNum), value);
        }
        return resultData;
    }



    private String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                if(DateUtil.isCellDateFormatted(cell)){
                    returnValue = cell.getLocalDateTimeCellValue().format(dateTimeFormatter);
                }else {
                    Double doubleValue = cell.getNumericCellValue();
                    returnValue = getNumberStr(doubleValue);
                }
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                try {
                    returnValue = getNumberStr(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    returnValue = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case ERROR:     // 故障
                returnValue = "";
                break;
            default:
                break;
        }
        return returnValue;
    }

    public <T> T getJavaBean(Map<String, Object> map, Class<T> clazz, T bean, Map<String, String> errorMap) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for ( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key  = entry.getKey();
            String CamelStr = CamelToUnderlineUtil.underlineToCamel(key);
            Object value  = entry.getValue();

            if(value == null){
                continue;
            }
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Class fieldType = field.getType();
                if(CamelStr.equalsIgnoreCase(field.getName())){
                    try {
                        String setter = "set"+ CamelToUnderlineUtil.captureName(CamelStr);
                        Method method = clazz.getMethod(setter,fieldType);
                        if( fieldType.isAssignableFrom(LocalDateTime.class) ){
                            try {
                                value = LocalDateTime.parse(value.toString(),dateTimeFormatter);
                            } catch (Exception e) {
                                continue;
                            }
                            method.invoke(bean,value);
                        }else if (fieldType.isAssignableFrom(Double.class)){
                            value = Double.valueOf(value.toString());
                            method.invoke(bean,value);
                        }else if (fieldType.isAssignableFrom(Integer.class)){
                            if(value.equals("是")){
                                value = 1;
                            }
                            if(value.equals("否")){
                                value = 0;
                            }
                            value = Integer.valueOf(value.toString());
                            method.invoke(bean,value);
                        }else{
                            method.invoke(bean,value);
                        }
                    } catch (Exception e) {
                        errorMap.put("rowName",key);
                        errorMap.put("value",value==null?"":value.toString());
                        logger.error("导入数据异常",e);
                        throw new Exception("数据异常");
                    }
                }
            }

        }

        return bean;
    }

    public Workbook exportWorkBook(List<T> rwList, List<Map<String, String>> cellName, String[] targetCellNames){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("复工情况");
        Row row0 = sheet.createRow(0);

        for (int i = 0; i < targetCellNames.length; i++) {
            Cell cell = row0.createCell(i);
            String targetCellName = targetCellNames[i];
            Map<String, String> cname = null;
            try {
                cname = cellName.stream().filter(stringStringMap -> stringStringMap.get("cellName").equals(targetCellName)).findFirst().get();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("========="+targetCellName);
                throw new RuntimeException();
            }
            cell.setCellValue(cname.get("cellNameCn"));
        }

        for (int i = 0; i < rwList.size(); i++) {
            T bean = rwList.get(i);
            Row row = sheet.createRow(i+1);

            Field[] fields = bean.getClass().getDeclaredFields();

            for (int j = 0; j < targetCellNames.length; j++) {
                String targetCellName = targetCellNames[j];
                Map<String, String> cellNameMap = cellName.stream().filter(stringStringMap -> stringStringMap.get("cellName").equals(targetCellName)).findFirst().get();

                for (int k = 0; k < fields.length; k++) {
                    Field field = fields[k];
                    String CamelStr = CamelToUnderlineUtil.underlineToCamel(cellNameMap.get("cellName"));
                    if( field.getName().equals(CamelStr) ){
                        try {
                            String getter = "get"+ CamelToUnderlineUtil.captureName(CamelStr);
                            Method method = bean.getClass().getMethod(getter);
                            Object value = method.invoke(bean);

                            Cell cell = row.createCell(j);
                            Class fieldType = field.getType();

                            if( fieldType.isAssignableFrom(LocalDateTime.class) ){
                                try {
                                    value = ((LocalDateTime)value).format(expprtDateTimeFormatter) ;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    cell.setCellValue("未知");
                                    continue;
                                }
                            }

                            if(value==null){
                                continue;
                            }

                            if (fieldType.isAssignableFrom(Integer.class)){
                                if(value.equals(1)){
                                    cell.setCellValue("是");
                                    continue;
                                }
                                if(value.equals(0)){
                                    cell.setCellValue("否");
                                    continue;
                                }
                                value = Integer.valueOf(value.toString());
                            }

                            cell.setCellValue(value.toString());

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        }

        return workbook;
    }

    protected abstract void checkValue(T bean, Map<String, String> errorMap);

    private String getNumberStr(Double doubleValue){
        if( doubleValue == doubleValue.longValue()){
            return String.valueOf(doubleValue.longValue());
        }else{
            return new BigDecimal(doubleValue).setScale(8, BigDecimal.ROUND_HALF_UP).toString();
        }
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public DateTimeFormatter getExpprtDateTimeFormatter() {
        return expprtDateTimeFormatter;
    }

    public void setExpprtDateTimeFormatter(DateTimeFormatter expprtDateTimeFormatter) {
        this.expprtDateTimeFormatter = expprtDateTimeFormatter;
    }
}
