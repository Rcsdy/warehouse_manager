package com.pn.mapper;

import com.pn.entity.Place;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceMapper {

    public List<Place> findAllPlace();
}
