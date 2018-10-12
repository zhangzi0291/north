package com.north.genealogy.service.impl;

import com.north.genealogy.entity.GenealogyFamily;
import com.north.genealogy.mapper.GenealogyFamilyMapper;
import com.north.genealogy.service.IGenealogyFamilyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 家族 服务实现类
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@Service("IGenealogyFamilyService")
public class GenealogyFamilyServiceImpl extends ServiceImpl<GenealogyFamilyMapper, GenealogyFamily> implements IGenealogyFamilyService {

}
