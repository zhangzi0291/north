package com.north.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.north.base.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;


/**
 * 公用的Service实现类，继承实现类的要传递2个泛型类型，第一个是实体Bean类型，第二个是查询Bean类型。
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	private BaseMapper<T> _baseDao;

	/**
	 * 将继承该类的子类中的BaseDao赋值给该类中的_baseDao,获取子类的dao<BaseMapper>属性
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void setBaseDao()  throws Exception {
		try {
			if(null != _baseDao) return;
			Field[] fields = getClass().getDeclaredFields();
			for(Field f : fields) {
				f.setAccessible(true);
				Object obj = f.get(this);
				if(obj instanceof BaseMapper) {
					_baseDao = (BaseMapper<T>)obj;
					if("dao".equals(f.getName())) {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int insert(T record) {
		return _baseDao.insert(record);
	}

	@Override
	public int deleteById(Serializable id) {
		return _baseDao.deleteById(id);
	}

    @Override
    public int deleteBatchIds(List<? extends Serializable> ids) {
        return _baseDao.deleteBatchIds(ids);
    }

    @Override
	public int deleteByWrapper(QueryWrapper<T> wrapper) {
		return _baseDao.delete(wrapper);
	}

	@Override
	public int updateByWrapper(T record, UpdateWrapper<T> wrapper) {
		return _baseDao.update(record,wrapper);
	}

	@Override
	public int updateById(T record) {
		return _baseDao.updateById(record);
	}

	@Override
	public T selectById(Serializable id) {
		return _baseDao.selectById(id);
	}

	@Override
	public List<T> selectByWrapper(QueryWrapper<T> wrapper) {
		return _baseDao.selectList(wrapper);
	}

	@Override
	public IPage<T> selectByWrapperAndPage(Page page, QueryWrapper<T> wrapper) {
		return _baseDao.selectPage(page,wrapper);
	}

	@Override
	public int countByWrapper(QueryWrapper<T> wrapper) {
		return _baseDao.selectCount(wrapper);
	}
}
