package com.pn.mapper;

import com.pn.entity.ProductType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductTypeMapper {

    public List<ProductType> findAllProductType();

    public ProductType findProductTypeByCode(String typeCode);

    public ProductType findProductTypeByName(String typeName);

    public Integer insertType(ProductType productType);

    public Integer removeTypeByTid(Integer typeId);

    public Integer updateTypeByTid(ProductType productType);
}
