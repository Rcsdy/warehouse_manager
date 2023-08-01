package com.pn.service;

import com.pn.entity.ProductType;
import com.pn.entity.Result;

import java.util.List;

public interface ProductTypeService {

    public List<ProductType> queryProductTypeTree();

    public Result checkTypeCode(String typeCode);

    public Result insertType(ProductType productType);

    public Result removeTypeByTid(Integer typeId);

    public Result updateTypeByTid(ProductType productType);
}
