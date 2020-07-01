package com.north.sys.service;

import com.north.base.excel.service.ExcelServiceAbstract;
import com.north.sys.entity.SysDict;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysDictExcelService extends ExcelServiceAbstract<SysDict, ISysDictService> {

    @Override
    protected void checkValue(SysDict bean, Map<String, String> errorMap) {

    }

}
