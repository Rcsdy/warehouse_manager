package com.pn.service.impl;

import com.pn.entity.Supply;
import com.pn.mapper.SupplyMapper;
import com.pn.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
//@CacheConfig(cacheNames = "com.pn.service.impl.SupplyServiceImpl")
@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    private SupplyMapper supplyMapper;

//    @Cacheable(key = "'all:supply'")
    @Override
    public List<Supply> queryAllSupply() {
        List<Supply> supplyList = supplyMapper.findAllSupply();

        return supplyList;
    }
}
