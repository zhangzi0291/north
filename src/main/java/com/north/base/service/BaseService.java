package com.north.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.PageList;
import com.north.base.dao.BaseDao;

import java.io.Serializable;
import java.util.List;

/**
 * 公用的Service接口，继承或实现该接口要传递2个泛型类型，第一个是实体Bean类型，第二个是查询Bean类型。
 */
public interface BaseService<T> {



    /**
     * 插入一条记录
     * @param record 实体Bean
     * @return 插入的记录数
     * @
     */
    int insert(T record) ;
    
    /**
     * 根据主键删除记录
     * @param id 主键
     * @return 删除的记录数
     * @
     */
    int deleteById(Serializable id) ;

    int deleteBatchIds(List<? extends Serializable> ids) ;

    /**
     * 根据自定义条件删除记录
     * @param wrapper 删除条件
     * @return 删除的记录数
     * @
     */
    int deleteByWrapper(QueryWrapper<T> wrapper) ;


    /**
     * 根据条件更新数据
     * @param record 实体Bean
     * @param wrapper 查询条件
     * @return 更新的记录数
     * @
     */
    int updateByWrapper(T record, UpdateWrapper<T> wrapper) ;

    /**
     * 根据主键更新数据
     * @param record 实体Bean
     * @return 更新的记录数
     * @
     */
    int updateById(T record) ;
    
    /**
     * 根据主键查询
     * @param id 主键
     * @return 查询到的记录
     * @
     */
    T selectById(Serializable id) ;

    /**
     * 根据自定义条件查询
     * @param wrapper 查询条件
     * @return 查询到的记录
     * @
     */
    List<T> selectByWrapper(QueryWrapper<T> wrapper) ;
    
    /**
     * 数据分页查询
     * @param page 分页条件
     * @param wrapper 查询条件
     * @return 分页数据
     * @
     */
    IPage<T> selectByWrapperAndPage(Page page,QueryWrapper<T> wrapper) ;
    
    /**
     * 查询记录数
     * @param wrapper 查询条件
     * @return 记录数
     * @
     */
    int countByWrapper(QueryWrapper<T> wrapper) ;

}
