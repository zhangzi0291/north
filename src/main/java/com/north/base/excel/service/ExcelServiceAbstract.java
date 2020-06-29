package com.north.base.excel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.north.base.converter.LocalDateTimeConverter;
import com.north.sys.entity.SysLog;
import com.north.utils.CamelToUnderlineUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class ExcelServiceAbstract< T,S extends IService<T>> {

    @Autowired
    private S service;

    private Logger logger = LoggerFactory.getLogger(ExcelServiceAbstract.class);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeFormatter exportDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 默认实现的导入
     * @param inputStream
     * @param cellNames
     * @return
     */
    public List<Map<String, String>> importExcel(InputStream inputStream, List<String> cellNames) {
        List<Map<String, Object>> list = readExcel(inputStream, cellNames);
        List<Map<String, String>> errorMapList = new ArrayList<>();
        List<T> beanList = getBeanList(list, errorMapList);

        if(errorMapList.size() == 0){
            upsertBean(beanList);
        }

        return errorMapList;
    }

    /**
     * 需要继承自定义的导入
     * @param inputStream
     * @param cellNames
     * @param map
     * @return
     */
    protected List<Map<String, String>> importExcel(InputStream inputStream, List<String> cellNames, Map<String, Object> map) {
        List<Map<String, String>> errorMapList = new ArrayList<>();
        return errorMapList;
    }

    /**
     * 获取workbook对象
     * @param inputStream
     * @return
     */
    public Workbook getWorkbook(InputStream inputStream) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("获取excel内容失败");
        }
        return workbook;
    }

    /**
     * 把读取到excel内容的map对象转成javaBean
     * @param list
     * @param errorMapList
     * @return
     */
    public List<T> getBeanList(List<Map<String, Object>> list, List<Map<String, String>> errorMapList) {
        List<T> rwList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> errorMap = new HashMap<>();
            try {
                Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

                T bean = getJavaBean(list.get(i), entityClass, (T) entityClass.newInstance(), errorMap);
                checkValue(bean, errorMap);
                if (errorMap.isEmpty()) {
                    rwList.add(bean);
                } else {
                    errorMap.put("row", i + 1 + "");
                    errorMapList.add(errorMap);
                }
            } catch (Exception e) {
                errorMap.put("row", i + 1 + "");
                errorMapList.add(errorMap);
                e.printStackTrace();
            }

        }
        return rwList;
    }

    /**
     * 获取需要新增和更新的bean，批量入库
     * @param beans
     */
    public void upsertBean(List<T> beans) {

        InsertOrUpdate insertOrUpdate = getInsertOrUpdateMap(beans);

        List<T> insertBeans = insertOrUpdate.getInsertBeans();
        if(!CollectionUtils.isEmpty(insertBeans)) {
            service.saveBatch(insertBeans);
        }

        List<T> updateBeans = insertOrUpdate.getUpdateBeans();
        if(!CollectionUtils.isEmpty(updateBeans)) {
            service.updateBatchById(updateBeans);
        }

    }

    /**
     * 子类实现把javaBean分类为需要新增的和需要更新的
     * @param beans
     * @return
     */
    protected InsertOrUpdate getInsertOrUpdateMap(List<T> beans){
        InsertOrUpdate iou = new InsertOrUpdate();
        for (T bean : beans) {
            iou.addInsertBeans(bean);
        }
        return iou;
    };

    /**
     * 读取excel内容，包括第一行的表头
     * @param inputStream
     * @param cellNames
     * @return
     */
    public List<Map<String, Object>> readExcelWithTiltl(InputStream inputStream, List<String> cellNames) {
        return readExcel(inputStream,cellNames,0);
    }

    /**
     * 读取excel内容，从第二行开始
     * @param inputStream
     * @param cellNames
     * @return
     */
    public List<Map<String, Object>> readExcel(InputStream inputStream, List<String> cellNames) {
        return readExcel(inputStream,cellNames,1);
    }

    /**
     * 通过文件流，按照cellNames的顺序读取excel内容
     * @param inputStream
     * @param cellNames
     * @param rowStart
     * @return
     */

    private List<Map<String, Object>> readExcel(InputStream inputStream, List<String> cellNames,int rowStart) {
        Workbook workbook = getWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int rowEnd = sheet.getPhysicalNumberOfRows();


        List<Map<String, Object>> resultDataList = new ArrayList<>();

        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = sheet.getRow(rowNum);

            if (null == row) {
                continue;
            }

            Map<String, Object> resultData = convertRowToData(row, cellNames);

            resultDataList.add(resultData);
        }
        return resultDataList;
    }

    /**
     * 转换行数据成map
     * @param row
     * @param cellNames
     * @return
     */
    private Map<String, Object> convertRowToData(Row row, List<String> cellNames) {
        Map<String, Object> resultData = new HashMap<>();
        int cellFirstNum = 0;
        int cellEndNum = row.getLastCellNum();

        for (int cellNum = cellFirstNum; cellNum < cellEndNum; cellNum++) {
            Cell cell = row.getCell(cellNum);
            String value = convertCellValueToString(cell);
            resultData.put(cellNames.get(cellNum - cellFirstNum), value);
        }
        return resultData;
    }

    /**
     * 转换单元格数据为String
     * @param cell
     * @return
     */
    public String convertCellValueToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                if (DateUtil.isCellDateFormatted(cell)) {
                    returnValue = cell.getLocalDateTimeCellValue().format(dateTimeFormatter);
                } else {
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

    /**
     * 把读取到的map数据通过反射转成javaBean
     * @param map
     * @param clazz
     * @param bean
     * @param errorMap
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getJavaBean(Map<String, Object> map, Class<T> clazz, T bean, Map<String, String> errorMap) throws Exception {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String CamelStr = CamelToUnderlineUtil.underlineToCamel(key);

            Field field = null;
            try {
                field = clazz.getDeclaredField(CamelStr);
                field.setAccessible(true);
            } catch (Exception e) {
                continue;
            }

            Object value = entry.getValue();
            if (value == null) {
                continue;
            }

            Class fieldType = field.getType();
            if (CamelStr.equalsIgnoreCase(field.getName())) {
                try {
                    String setter = "set" + CamelToUnderlineUtil.captureName(CamelStr);
                    Method method = clazz.getMethod(setter, fieldType);
                    value = formatBeanValue(fieldType,value);
                    method.invoke(bean, value);
                } catch (Exception e) {
                    errorMap.put("rowName", key);
                    errorMap.put("value", value == null ? "" : value.toString());
                    logger.error("导入数据异常", e);
                    throw new Exception("数据异常");
                }
            }

        }
        return bean;
    }


    public Workbook exportAllWorkBook(List<T> rwList, CellNames cellName, String sheetTitle) {
        return exportWorkBook(rwList,cellName,cellName.getCellNames(),sheetTitle);
    }

    /**
     * 导出
     * @param rwList
     * @param cellNames
     * @param targetCellNames
     * @return
     */
    public Workbook exportWorkBook(List<T> rwList, CellNames cellNames, List<String> targetCellNames, String sheetTitle) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetTitle);
        Row row0 = sheet.createRow(0);
        List<Map<String,String>> cellName = cellNames.getCellNameListMap();
        for (int i = 0; i < targetCellNames.size(); i++) {
            Cell cell = row0.createCell(i);
            String targetCellName = targetCellNames.get(i);
            Map<String, String> cname = null;
            try {
                cname = cellName.stream().filter(stringStringMap -> stringStringMap.get("cellName").equals(targetCellName)).findFirst().get();

            } catch (Exception e) {
                logger.error("export error:",e);
                throw new RuntimeException("获取导出表头错误");
            }
            cell.setCellValue(cname.get("cellNameCn"));
        }

        for (int i = 0; i < rwList.size(); i++) {
            T bean = rwList.get(i);
            Row row = sheet.createRow(i + 1);

            for (int j = 0; j < targetCellNames.size(); j++) {
                String targetCellName = targetCellNames.get(j);
                Map<String, String> cellNameMap = cellName.stream().filter(stringStringMap -> stringStringMap.get("cellName").equals(targetCellName)).findFirst().get();

                String CamelStr = CamelToUnderlineUtil.underlineToCamel(cellNameMap.get("cellName"));
                Field field = null;
                try {
                    field = bean.getClass().getDeclaredField(CamelStr);
                    field.setAccessible(true);
                } catch (Exception e) {
                    continue;
                }

                try {
                    String getter = "get" + CamelToUnderlineUtil.captureName(CamelStr);
                    Method method = bean.getClass().getMethod(getter);
                    Object value = method.invoke(bean);

                    Cell cell = row.createCell(j);
                    Class fieldType = field.getType();

                    if (value == null) {
                        continue;
                    }
                    value = exportformatBeanValue(fieldType,value);
                    cell.setCellValue(value.toString());
                } catch (Exception e) {
                    logger.error("export error:",e);
                }
            }
        }
        return workbook;
    }

    /**
     * 获取表头内容
     * @param inputStream
     * @return
     */
    public List<String> getTitle(InputStream inputStream) {
        List<String> titleList = new ArrayList<>();

        Workbook workbook = getWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int firstRowNum = sheet.getFirstRowNum();
        Row row = sheet.getRow(firstRowNum);
        if (null == row) {
            logger.error("解析Excel失败，在第一行没有读取到任何数据！");
            return titleList;
        }
        int cellFirstNum = 0;
        int cellEndNum = row.getLastCellNum();

        for (int cellNum = cellFirstNum; cellNum < cellEndNum; cellNum++) {
            Cell cell = row.getCell(cellNum);
            String value = convertCellValueToString(cell);
            titleList.add(value);
        }
        return titleList;
    }

    /**
     * 子类实现获取的javaBean内容检查
     * @param bean
     * @param errorMap
     */
    protected abstract void checkValue(T bean, Map<String, String> errorMap);

    /**
     * 数字格式化
     * @param doubleValue
     * @return
     */
    public String getNumberStr(Double doubleValue) {
        if (doubleValue == doubleValue.longValue()) {
            return String.valueOf(doubleValue.longValue());
        } else {
            return new BigDecimal(doubleValue).setScale(8, BigDecimal.ROUND_HALF_UP).toString();
        }
    }

    /**
     * 导入反射转换时类型的特殊转换
     * @param fieldType
     * @param value
     * @return
     */
    public Object formatBeanValue(Class fieldType, Object value){
        Object result = value.toString();
        if (fieldType.isAssignableFrom(Integer.class)) {
            result = Integer.valueOf(value.toString());
            if (value.equals("是")) {
                result = 1;
            }
            if (value.equals("否")) {
                result = 0;
            }
        } else if (fieldType.isAssignableFrom(LocalDateTime.class)) {
            if(value instanceof LocalDateTime){
                result = value;
            }else {
                LocalDateTimeConverter ldtc = new LocalDateTimeConverter();
                result = ldtc.convert(value.toString());
            }
        } else if (fieldType.isAssignableFrom(Double.class)) {
            result = Double.valueOf(value.toString());
        } else if (fieldType.isAssignableFrom(Float.class)) {
            result = Float.valueOf(value.toString());
        } else if (fieldType.isAssignableFrom(Long.class)) {
            result = Long.valueOf(value.toString());
        } else if (fieldType.isAssignableFrom(Boolean.class)) {
            if (value.equals("是")||value.equals("1")) {
                result = true;
            }
            if (value.equals("否")||value.equals("0")) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 导出反射转换时类型的特殊转换
     * @param fieldType
     * @param value
     * @return
     */
    public Object exportformatBeanValue(Class fieldType, Object value){
        Object result = value.toString();
        if (fieldType.isAssignableFrom(Boolean.class)) {
            if (Boolean.valueOf(value.toString())) {
                result = "是";
            }else{
                result = "否";
            }
        } else if(fieldType.isAssignableFrom(Integer.class)){
            result = Integer.valueOf(value.toString());
        } else if (fieldType.isAssignableFrom(LocalDateTime.class)) {
            try {
                result = ((LocalDateTime) value).format(exportDateTimeFormatter);
            } catch (Exception e) {
                logger.warn("export LocalDateTime format error,set default");
                result = "未知";
            }
        }
        return result;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public DateTimeFormatter getExportDateTimeFormatter() {
        return exportDateTimeFormatter;
    }

    public void setExportDateTimeFormatter(DateTimeFormatter exportDateTimeFormatter) {
        this.exportDateTimeFormatter = exportDateTimeFormatter;
    }

    public class InsertOrUpdate{
        List<T> insertBeans = new ArrayList<>();
        List<T> updateBeans = new ArrayList<>();

        public void addInsertBeans(T bean){
            insertBeans.add(bean );
        }
        public void addUpdateBeans(T bean){
            updateBeans.add(bean );
        }

        public List<T> getInsertBeans() {
            return insertBeans;
        }

        public List<T> getUpdateBeans() {
            return updateBeans;
        }
    }

    public static class CellNames{

        public CellNames(String[] cellNames, String[] cellNameCns) {
            List<String> cellNamesList = Arrays.asList(cellNames);
            List<String> cellNameCnsList = Arrays.asList(cellNameCns);
            if(cellNamesList.size()!=cellNameCnsList.size()){
                throw new RuntimeException("表头不对应");
            }
            this.cellNames = cellNamesList;
            this.cellNameCns = cellNameCnsList;
        }

        List<String> cellNames = new ArrayList<>();
        List<String> cellNameCns = new ArrayList<>();

        public List<String> getCellNames() {
            return cellNames;
        }

        public void setCellNames(List<String> cellNames) {
            this.cellNames = cellNames;
        }

        public List<String> getCellNameCns() {
            return cellNameCns;
        }

        public void setCellNameCns(List<String> cellNameCns) {
            this.cellNameCns = cellNameCns;
        }

        public List<Map<String,String>> getCellNameListMap(){
            List<Map<String,String>> list = new ArrayList<>();

            for (int i = 0; i < cellNames.size(); i++) {
                Map<String,String> map = new HashMap<>();
                map.put("cellName",cellNames.get(i));
                map.put("cellNameCn",cellNameCns.get(i));
                list.add(map);
            }
            return list;
        }
    }
}
