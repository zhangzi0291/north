package com.north.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.north.base.BaseController;
import com.north.base.R;
import com.north.base.excel.service.ExcelServiceAbstract;
import com.north.sys.entity.SysDict;
import com.north.sys.service.ISysDictService;
import com.north.sys.service.SysDictExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

/**
* <p>
* 数据字典 前端控制器
* </p>
*
* @author NorthZX
* @since 2020-07-01
*/
@RestController
@RequestMapping("sys/dict")
public class SysDictController extends BaseController<SysDict, ISysDictService> {

    @Resource
    private ISysDictService service;
    @Resource
    private SysDictExcelService excelService;

    @RequestMapping("getDict")
    public R getDict(String dictName){
        QueryWrapper<SysDict> qw = new QueryWrapper();
        qw.eq("dict_name",dictName);
        List<SysDict> list =service.list(qw);
        return R.ok().putObject("dict",list);
    }

    @RequestMapping("importExcel")
    public R importExcel(@RequestParam("file") MultipartFile file, String userId) throws Exception{

        String fileName = "数据字典";
        List<String> cellNames = getCellNameMapList().getCellNames();

        int titleSize = excelService.getTitle(file.getInputStream()).size();
        if(titleSize != cellNames.size()){
            List<Map<String,String>> errorMapList = new ArrayList<>();
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("row","1");
            errorMap.put("rowName","模板表头不正确");
            errorMap.put("value","模板表头不正确");
            errorMapList.add(errorMap);
            return R.ok("errorList",errorMapList);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("fileName",fileName);
        map.put("file",file);

        List<Map<String,String>> errorMapList = excelService.importExcel(file.getInputStream(), cellNames );

        return R.ok("errorList",errorMapList);
    }

    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response, SysDict bean, String targetCellName) throws Exception {
        String exportFileName = "数据字典";

        QueryWrapper<SysDict>  qw = setListWrapper(bean,new HashMap<>());
        List<SysDict> rwList = service.list(qw);
            ExcelServiceAbstract.CellNames cellNameMapList = getCellNameMapList();
            if(StringUtils.isEmpty(targetCellName)){
            targetCellName = "";
            }
            Workbook workbook = excelService.exportWorkBook(rwList,cellNameMapList,Arrays.asList(targetCellName.split(",")),"sheet1");

            response.setHeader("Content-Disposition", "attachment;filename="+new String(exportFileName.getBytes(),"iso-8859-1")+".xlsx");

            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            workbook.close();
            ouputStream.flush();
            ouputStream.close();

        }

        private ExcelServiceAbstract.CellNames getCellNameMapList() {
        String [] cellNames = new String[] {"id","dictName","dictKey","dictValue",};
        String [] cellNameCns = new String[] {"ID","字典名称","Key","Value",};
        ExcelServiceAbstract.CellNames cellNameBean = new ExcelServiceAbstract.CellNames(cellNames,cellNameCns);
        return cellNameBean;
    }

}
