package com.north.sys.service;

import com.north.base.excel.service.ExcelServiceAbstract;
import com.north.sys.entity.SysLog;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysLogExcelService extends ExcelServiceAbstract<SysLog,ISysLogService> {

    @Override
    protected void checkValue(SysLog bean, Map<String, String> errorMap) {

    }

    @Override
    protected Map<String, Map<String, String>> setValueFormatMap() {
        return null;
    }
}
