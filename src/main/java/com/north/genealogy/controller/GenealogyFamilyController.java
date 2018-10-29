package com.north.genealogy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.BaseController;
import com.north.base.R;
import com.north.genealogy.entity.GenealogyFamily;
import com.north.genealogy.service.IGenealogyFamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家族 前端控制器
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@RestController
@RequestMapping("genealogy/family")
public class GenealogyFamilyController extends BaseController<IGenealogyFamilyService,GenealogyFamily> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected QueryWrapper<GenealogyFamily> setListWrapper(GenealogyFamily bean) {
        QueryWrapper wrapper = new QueryWrapper<GenealogyFamily>();
        if(!StringUtils.isEmpty(bean.getId())) {
            wrapper.eq("id", bean.getId());
        }
        if(!StringUtils.isEmpty(bean.getGenealogyArea())) {
            wrapper.eq("genealogy_area", bean.getGenealogyArea());
        }
        if(!StringUtils.isEmpty(bean.getGenealogySurname())) {
            wrapper.eq("genealogy_surname", bean.getGenealogySurname());
        }
        return wrapper;
    }
}
