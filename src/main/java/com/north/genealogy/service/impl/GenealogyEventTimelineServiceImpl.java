package com.north.genealogy.service.impl;

import com.north.genealogy.entity.GenealogyEventTimeline;
import com.north.genealogy.mapper.GenealogyEventTimelineMapper;
import com.north.genealogy.service.IGenealogyEventTimelineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 大事件时间线 服务实现类
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@Service("IGenealogyEventTimelineService")
public class GenealogyEventTimelineServiceImpl extends ServiceImpl<GenealogyEventTimelineMapper, GenealogyEventTimeline> implements IGenealogyEventTimelineService {

}
