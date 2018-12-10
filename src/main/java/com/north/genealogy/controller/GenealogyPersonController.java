package com.north.genealogy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.north.base.BaseController;
import com.north.base.R;
import com.north.genealogy.entity.GenealogyPerson;
import com.north.genealogy.entity.GenealogyPersonDto;
import com.north.genealogy.service.IGenealogyPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    @RequestMapping("getChildTree")
    public R getChildTree(){
        List<GenealogyPerson> list = (List<GenealogyPerson>)this.listJson(new GenealogyPerson(),null).get("rows");
        List<GenealogyPersonDto> tagetList = new CopyOnWriteArrayList<>();
        List<GenealogyPersonDto> spouseList = new CopyOnWriteArrayList<>();
        List<GenealogyPersonDto> saveList = new CopyOnWriteArrayList<>();
        list.stream().forEach(genealogyPerson -> {
            GenealogyPersonDto dto = new GenealogyPersonDto();
            BeanUtils.copyProperties(genealogyPerson, dto);
            tagetList.add(dto);
        });
        list.stream().forEach(genealogyPerson -> {
            GenealogyPersonDto dto = new GenealogyPersonDto();
            if(!StringUtils.isEmpty(genealogyPerson.getSpouseId())){
                BeanUtils.copyProperties(genealogyPerson, dto);
                spouseList.add(dto);
            }
        });
        tagetList.forEach(v->{
            if(v.getSpouseId() != null){
                getSpouseNode(spouseList,v);
            }
        });
        tagetList.forEach(v->{
            if("-1".equals(v.getParentId()) ){
                getChildNode(tagetList,v);
                saveList.add(v);
            }
        });
        GenealogyPersonDto root = new GenealogyPersonDto();
        root.setGenealogyName("起点");
        root.setGenealogySex("男");
        root.setChild(saveList);
            return R.ok().putObject("node",root);
    }
    private List<GenealogyPersonDto> getChildNode(List<GenealogyPersonDto> list , GenealogyPersonDto parent){
        list.forEach(v->{
            if(parent.getId().equals(v.getParentId())){
                parent.getChild().add(v);
                list.remove(v);
                getChildNode(list,v);
            }
        });
        return list;
    }
    private List<GenealogyPersonDto> getSpouseNode(List<GenealogyPersonDto> list , GenealogyPersonDto spouse){
        list.forEach(v->{
            if(v.getId().equals(spouse.getSpouseId())){
                spouse.setSpouse(v);
                list.remove(v);
            }
        });
        return list;
    }

}
