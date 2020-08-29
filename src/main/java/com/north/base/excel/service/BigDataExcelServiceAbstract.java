package com.north.base.excel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.north.base.excel.HssfHander;
import com.north.base.excel.XssfHander;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public abstract class BigDataExcelServiceAbstract< T,S extends IService<T>> extends ExcelServiceAbstract<T, S> {

    @Autowired
    private S service;

    private Logger logger = LoggerFactory.getLogger(BigDataExcelServiceAbstract.class);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeFormatter exportDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<Map<String, Object>> readExcel(InputStream inputStream, List<String> cellNames) {
        try {
            InputStream is = FileMagic.prepareToCheckMagic(inputStream);
            FileMagic fm = FileMagic.valueOf(is);
            switch (fm) {
                case OLE2:
                    HssfHander hssfHander = new HssfHander();
                    hssfHander.process(inputStream,cellNames);
                    return hssfHander.getRowValue();
                case OOXML:
                    XssfHander xssfHander = new XssfHander();
                    xssfHander.process(inputStream,cellNames);
                    return xssfHander.getRowValue();
                default:
                    throw new IOException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
            }

        } catch (Exception e) {
            logger.error("read excel data exception:",e);
        }
        return null;
    }

}
