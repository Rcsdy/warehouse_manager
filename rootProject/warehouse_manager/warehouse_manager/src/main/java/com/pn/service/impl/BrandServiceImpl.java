package com.pn.service.impl;

import com.pn.entity.Brand;
import com.pn.mapper.BrandMapper;
import com.pn.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
//@CacheConfig(cacheNames = "com.pn.service.impl.BrandServiceImpl")
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

//    @Cacheable(key = "'all:brand'")
    @Override
    public List<Brand> queryAllBrand() {

        List<Brand> brandList = brandMapper.findAllBrand();

        return brandList;
    }
}
