package com.north.genealogy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.BaseController;
import com.north.base.R;
import com.north.genealogy.entity.GenealogyEventTimeline;
import com.north.genealogy.entity.GenealogyEventTimeline;
import com.north.genealogy.service.IGenealogyEventTimelineService;
import com.north.genealogy.service.IGenealogyPersonTimelineService;
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
 * 大事件时间线 前端控制器
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@RestController
@RequestMapping("/genealogyEventTimeline")
public class GenealogyEventTimelineController extends BaseController<IGenealogyEventTimelineService,GenealogyEventTimeline> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IGenealogyEventTimelineService genealGenealogyEventTimelineogyEventTimelineService;

}
