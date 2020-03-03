package com.north.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类的描述
 *
 * @Author zxn
 * @Date 2018-10-11 12:26
 */
public abstract class BaseController<T extends IService<U>,U> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private T service;

    protected QueryWrapper<U> setListWrapper(U bean, Map<String,String> map){
        return new QueryWrapper<U>();
    }

    @RequestMapping(path = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public R listJson(U bean, Page page, @RequestParam Map<String,String> map){
        QueryWrapper<U> wrapper = setListWrapper(bean,map);
        try {
            if(page == null){
                List<U> list = service.list(wrapper);
                return R.ok().putObject("rows",list).putObject("total", list.size());
            }else{
                IPage< U> list = service.page(page,wrapper);
                return R.ok().putObject("rows",list.getRecords()).putObject("total", list.getTotal());
            }

        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping(path = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public R addJson(U bean){
        Boolean flag = false;
        try {
            flag = service.save(bean);
            if(!flag){
                return R.error("添加失败,无数据");
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("添加失败,出现异常");
        }
        return R.ok("data",flag);
    }

    @RequestMapping(path = "get", method = {RequestMethod.GET, RequestMethod.POST})
    public R get(String id){
        try {
            U bean =  service.getById(id);
            return R.ok("data",bean);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return R.error("无数据");
    }

    @RequestMapping(path = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public R editJson(U bean){
        Boolean flag = false;
        try {
            flag = service.updateById(bean);
            if(!flag){
                return R.error("保存失败,无数据");
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("保存失败,出现异常");
        }
        return R.ok("data",flag);
    }

    @RequestMapping(path = "del", method = {RequestMethod.GET, RequestMethod.POST})
    public R delJson(@RequestParam("ids") List<String> ids ){
        Boolean flag = false;
        try {
            flag = service.removeByIds(ids);
        } catch (Exception e) {
            logger.error("Exception ", e);
            return R.error("删除失败,出现异常");
        }
        return R.ok("data",flag);
    }

}
