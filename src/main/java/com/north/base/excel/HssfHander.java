package com.north.base.excel;

import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HssfHander implements HSSFListener {

    private int minColumns = -1;

    private POIFSFileSystem fs;

    private int lastRowNumber;

    private int lastColumnNumber;

    /** Should we output the formula, or the value it has? */
    private boolean outputFormulaValues = true;

    /** For parsing Formulas */
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;

    // excel2003工作薄
    private HSSFWorkbook stubWorkbook;

    // Records we pick up as we process
    private SSTRecord sstRecord;

    private FormatTrackingHSSFListener formatListener;

    // 表索引
    private int sheetIndex = -1;

    private BoundSheetRecord[] orderedBSRs;

    private ArrayList boundSheetRecords = new ArrayList();

    // For handling formulas with string results
    private int nextRow;

    private int nextColumn;

    private boolean outputNextStringRecord;

    // 当前行
    private int curRow = 0;

    // 存储行记录的容器
    private List<Object> cellList = new ArrayList<>();;

    private String sheetName;

    private List<Map<String,Object>> rowValue = new ArrayList<>();

    private List<String> cellName;
    /**
     * 遍历excel下所有的sheet
     *
     * @throws IOException
     */
    public void process(InputStream inputStream,List<String> cellName) throws IOException {
        this.rowValue = new ArrayList<>();
        this.cellName = cellName;
        this.fs = new POIFSFileSystem(inputStream);

        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        formatListener = new FormatTrackingHSSFListener(listener);
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        if (outputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
        }
        factory.processWorkbookEvents(request, fs);
    }

    /**
     * HSSFListener 监听方法，处理 Record
     */
    public void processRecord(Record record) {
        int thisRow = -1;
        int thisColumn = -1;
        Object value = null;
        switch (record.getSid()) {
            case BoundSheetRecord.sid:
                boundSheetRecords.add(record);
                break;
            case BOFRecord.sid:
                BOFRecord br = (BOFRecord) record;
                if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
                    // 如果有需要，则建立子工作薄
                    if (workbookBuildingListener != null && stubWorkbook == null) {
                        stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
                    }

                    sheetIndex++;
                    if (orderedBSRs == null) {
                        orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
                    }
                    sheetName = orderedBSRs[sheetIndex].getSheetname();
                }
                break;

            case SSTRecord.sid:
                sstRecord = (SSTRecord) record;
                break;

            case BlankRecord.sid:
                BlankRecord brec = (BlankRecord) record;
                thisRow = brec.getRow();
                thisColumn = brec.getColumn();
                cellList.add(thisColumn, "");
                break;
            case BoolErrRecord.sid: // 单元格为布尔类型
                BoolErrRecord berec = (BoolErrRecord) record;
                thisRow = berec.getRow();
                thisColumn = berec.getColumn();
                cellList.add(thisColumn, berec.getBooleanValue());
                break;

            case FormulaRecord.sid: // 单元格为公式类型
                FormulaRecord frec = (FormulaRecord) record;
                thisRow = frec.getRow();
                thisColumn = frec.getColumn();
                if (outputFormulaValues) {
                    if (Double.isNaN(frec.getValue())) {
                        // Formula result is a string
                        // This is stored in the next record
                        outputNextStringRecord = true;
                        nextRow = frec.getRow();
                        nextColumn = frec.getColumn();
                    } else {
                        value = formatListener.formatNumberDateCell(frec);
                    }
                } else {
                    value = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
                }
                cellList.add(thisColumn, value);
                break;
            case StringRecord.sid:// 单元格中公式的字符串
                if (outputNextStringRecord) {
                    // String for formula
                    StringRecord srec = (StringRecord) record;
                    value = srec.getString();
                    thisRow = nextRow;
                    thisColumn = nextColumn;
                    outputNextStringRecord = false;
                }
                break;
            case LabelRecord.sid:
                LabelRecord lrec = (LabelRecord) record;
                curRow = thisRow = lrec.getRow();
                thisColumn = lrec.getColumn();
                value = lrec.getValue().trim();
                this.cellList.add(thisColumn, value);
                break;
            case LabelSSTRecord.sid: // 单元格为字符串类型
                LabelSSTRecord lsrec = (LabelSSTRecord) record;
                curRow = thisRow = lsrec.getRow();
                thisColumn = lsrec.getColumn();
                if (sstRecord == null) {
                    cellList.add(thisColumn, "");
                } else {
                    value = sstRecord.getString(lsrec.getSSTIndex()).toString().trim();
                    cellList.add(thisColumn, value);
                }
                break;
            case NumberRecord.sid: // 单元格为数字类型
                NumberRecord numrec = (NumberRecord) record;
                curRow = thisRow = numrec.getRow();
                thisColumn = numrec.getColumn();
                String formatString = formatListener.getFormatString(numrec);
                if (formatString.contains("m/d/yy") || formatString.contains("yyyy/mm/dd") || formatString.contains("yyyy/m/d")) {
                    try {
                        Double doubleValue = numrec.getValue();
                        value = DateUtil.getLocalDateTime(doubleValue);
                    } catch (NumberFormatException ex) {
                        value = value.toString();
                    }
                }else {
                    value = formatListener.formatNumberDateCell(numrec).trim();
                }
                // 向容器加入列值
                cellList.add(thisColumn, value);
                break;
            default:
                break;
        }

        // 遇到新行的操作
        if (thisRow != -1 && thisRow != lastRowNumber) {
            lastColumnNumber = -1;
        }

        // 空值的操作
        if (record instanceof MissingCellDummyRecord) {
            MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
            curRow = thisRow = mc.getRow();
            thisColumn = mc.getColumn();
            cellList.add(thisColumn, "");
        }

        // 更新行和列的值
        if (thisRow > -1)
            lastRowNumber = thisRow;
        if (thisColumn > -1)
            lastColumnNumber = thisColumn;

        // 行结束时的操作
        if (record instanceof LastCellOfRowDummyRecord) {
            if (minColumns > 0) {
                // 列值重新置空
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }
            }
            lastColumnNumber = -1;

            // 每行结束时， 调用getRows() 方法
//            rowReader.getRows(sheetIndex, curRow, rowlist);
            if(curRow == 0){
                cellList.clear();
                return;
            }
            Map<String,Object> rowValueMap = new HashMap<>();
            for (int i = 0; i < cellList.size(); i++) {
                if(StringUtils.isEmpty(cellList.get(i))){
                    continue;
                }
                rowValueMap.put(cellName.get(i),cellList.get(i));
            }
            rowValue.add(curRow-1,rowValueMap);
            // 清空容器
            cellList.clear();
        }
    }

    public List<Map<String, Object>> getRowValue() {
        return rowValue;
    }

    public void setRowValue(List<Map<String, Object>> rowValue) {
        this.rowValue = rowValue;
    }

    public static void main(String[] args) throws Exception {
        List<String> cellName = Arrays.asList(new String[] {"null","string_value","text_value","int_value","long_value","float_value","double_value","date_value"});
        Path path = Paths.get("D:\\","test_table-small.xls");

        HssfHander example = new HssfHander();
        example.process(new FileInputStream(path.toFile()),cellName);
        List<Map<String, Object>> rowvalue = example.getRowValue();
        System.out.println(rowvalue);

    }

}
