package com.north.genealogy.service.impl;

import com.north.genealogy.entity.GenealogyPerson;
import com.north.genealogy.mapper.GenealogyPersonMapper;
import com.north.genealogy.service.IGenealogyPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 家族成员 服务实现类
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@Service("IGenealogyPersonService")
public class GenealogyPersonServiceImpl extends ServiceImpl<GenealogyPersonMapper, GenealogyPerson> implements IGenealogyPersonService {

}
