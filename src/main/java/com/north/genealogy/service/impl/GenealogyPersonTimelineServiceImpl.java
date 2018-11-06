package com.north.genealogy.service.impl;

import com.north.genealogy.entity.GenealogyPersonTimeline;
import com.north.genealogy.mapper.GenealogyPersonTimelineMapper;
import com.north.genealogy.service.IGenealogyPersonTimelineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 个人大事件时间线 服务实现类
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@Service("IGenealogyPersonTimelineService")
public class GenealogyPersonTimelineServiceImpl extends ServiceImpl<GenealogyPersonTimelineMapper, GenealogyPersonTimeline> implements IGenealogyPersonTimelineService {

}
