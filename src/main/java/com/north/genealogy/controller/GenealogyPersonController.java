package com.north.genealogy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.BaseController;
import com.north.base.R;
import com.north.genealogy.entity.GenealogyPerson;
import com.north.genealogy.entity.GenealogyPersonDto;
import com.north.genealogy.service.IGenealogyPersonService;
import com.north.genealogy.service.IGenealogyPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 家族成员 前端控制器
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@RestController
@RequestMapping("/genealogy/person")
public class GenealogyPersonController extends BaseController<IGenealogyPersonService,GenealogyPerson> {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IGenealogyPersonService genealogyPersonService;

    @Override
    public QueryWrapper<GenealogyPerson> setListWrapper(GenealogyPerson bean) {
        QueryWrapper wrapper = new QueryWrapper<GenealogyPerson>();
        if(!StringUtils.isEmpty(bean.getId())) {
            wrapper.eq("id", bean.getId());
        }
        if(!StringUtils.isEmpty(bean.getGenealogyId())) {
            wrapper.eq("genealogy_id", bean.getGenealogyId());
        }
        if(!StringUtils.isEmpty(bean.getGenealogyName())) {
            wrapper.like("genealogy_name", bean.getGenealogyName());
        }
        return wrapper;
    }

    @Override
    public R editJson(GenealogyPerson bean) {
        if(bean.getGenealogyDeadtime()==null){

        }
        return super.editJson(bean);
    }

    @RequestMapping("getChild")
    public R getChild(){
        List<GenealogyPerson> list = (List<GenealogyPerson>)this.listJson(new GenealogyPerson(),null).get("rows");
        List<GenealogyPersonDto> tagetList = new ArrayList<>();
        list.stream().forEach(genealogyPerson -> {
            GenealogyPersonDto dto = new GenealogyPersonDto();
            BeanUtils.copyProperties(genealogyPerson, dto);
            tagetList.add(dto);
        });
        List<GenealogyPersonDto> tlist = setChildNood(tagetList,null);
        return R.ok().putObject("node",tlist);
    }

    private List<GenealogyPersonDto> setChildNood(List<GenealogyPersonDto> resourceList, List<GenealogyPersonDto> nextNodeList) {
        if(!CollectionUtils.isEmpty(resourceList)) {
            if (CollectionUtils.isEmpty(nextNodeList)) {
                List<GenealogyPersonDto> child = resourceList.stream()
                        .filter(s -> s.getParentId()==null).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(child)){
                    return resourceList;
                }
                resourceList.removeAll(child);
                nextNodeList = child;
                setChildNood(resourceList, child);
            } else {
                nextNodeList.forEach(p -> {
                    List<GenealogyPersonDto> child = resourceList.stream()
                            .filter(s -> s.getParentId().equals(p.getId())).collect(Collectors.toList());
                    p.setChild(child);
                    if(CollectionUtils.isEmpty(child)){
                        return;
                    }
                    resourceList.removeAll(child);
                    setChildNood(resourceList, child);
                });
            }
        }
        return nextNodeList;
    }
}
