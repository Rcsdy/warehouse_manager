package com.pn.service;

import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.page.Page;

import java.util.List;

public interface ProductService {

    public Page queryProductPage(Page page, Product product);

    public Result insertProduct(Product product);

    public Result updateProductStateByPid(Product product);

    public Result deleteProductByPid(List<Integer> productIdList);

    public Result updateProductByPid(Product product);
}
