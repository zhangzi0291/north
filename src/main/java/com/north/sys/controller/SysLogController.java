package com.north.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.north.base.BaseController;
import com.north.base.R;
import com.north.base.excel.service.ExcelServiceAbstract;
import com.north.sys.entity.SysLog;
import com.north.sys.service.ISysLogService;
import com.north.sys.service.SysLogExcelService;
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
 * 系统访问日志 前端控制器
 * </p>
 *
 * @author NorthZX
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController<SysLog, ISysLogService> {

    @Resource
    private ISysLogService sysLogService;
    @Resource
    private SysLogExcelService sysLogExcelService;

    @Override
    protected QueryWrapper<SysLog> setListWrapper(SysLog bean, Map<String, String> map) {
        QueryWrapper<SysLog> qw = new QueryWrapper<>();
        if(!StringUtils.isEmpty(bean.getIp())){
            qw.like("ip",bean.getIp());
        }
        return qw;
    }

    @RequestMapping("importExcel")
    public R importExcel(@RequestParam("file") MultipartFile file, String userId) throws Exception{

        String fileName = "合作人员信息表";
        List<String> cellNames = getCellNameMapList().getCellNames();

        int titleSize = sysLogExcelService.getTitle(file.getInputStream()).size();
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

        List<Map<String,String>> errorMapList = sysLogExcelService.importExcel(file.getInputStream(), cellNames );

        return R.ok("errorList",errorMapList);
    }

    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response, SysLog bean, String targetCellName) throws Exception {
        String exportFileName = "系统日志";

        QueryWrapper<SysLog>  qw = setListWrapper(bean,new HashMap<>());
        List<SysLog> rwList = sysLogService.list(qw);

        ExcelServiceAbstract.CellNames cellNameMapList = getCellNameMapList();
        if(StringUtils.isEmpty(targetCellName)){
            targetCellName = "";
        }
        Workbook workbook = sysLogExcelService.exportWorkBook(rwList,cellNameMapList,Arrays.asList(targetCellName.split(",")),"sheet1");

        response.setHeader("Content-Disposition", "attachment;filename="+new String(exportFileName.getBytes(),"iso-8859-1")+".xlsx");

        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        workbook.close();
        ouputStream.flush();
        ouputStream.close();

    }

    private ExcelServiceAbstract.CellNames getCellNameMapList() {
        String [] cellNames = new String[] {"ip","moduleName","operationName","username","createTime","remark"};
        String [] cellNameCns = new String[] {"IP","模块名称","操作名称","操作用户","操作时间","备注"};
        ExcelServiceAbstract.CellNames cellNameBean = new ExcelServiceAbstract.CellNames(cellNames,cellNameCns);
        return cellNameBean;
    }
}
