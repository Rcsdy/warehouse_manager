package com.pn.service.impl;

import com.pn.entity.Place;
import com.pn.mapper.PlaceMapper;
import com.pn.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
//@CacheConfig(cacheNames = "com.pn.service.impl.PlaceServiceImpl")
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceMapper placeMapper;

//    @Cacheable(key = "'all:place'")
    @Override
    public List<Place> queryAllPlace() {
        List<Place> placeList = placeMapper.findAllPlace();
        return placeList;
    }
}
