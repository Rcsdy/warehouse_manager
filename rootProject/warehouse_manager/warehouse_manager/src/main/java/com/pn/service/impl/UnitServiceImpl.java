package com.pn.service.impl;

import com.pn.entity.Unit;
import com.pn.mapper.UnitMapper;
import com.pn.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
//@CacheConfig(cacheNames = "com.pn.service.impl.UnitServiceImpl")
@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitMapper unitMapper;

//    @Cacheable(key = "'all:unit'")
    @Override
    public List<Unit> queryAllUnit() {
        List<Unit> unitList = unitMapper.findAllUnit();
        return unitList;
    }
}
