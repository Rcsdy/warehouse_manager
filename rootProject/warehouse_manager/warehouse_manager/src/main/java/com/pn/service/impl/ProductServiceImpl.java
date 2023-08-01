package com.pn.service.impl;

import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page queryProductPage(Page page, Product product) {
        Integer count = productMapper.findProductRowCount(product);

        List<Product> productList = productMapper.findProductByPage(page, product);

        page.setTotalNum(count);
        page.setResultList(productList);
        return page;
    }

    @Override
    public Result insertProduct(Product product) {
        Product product1 = productMapper.findProductByNum(product.getProductNum());
        if(product1 == null) {
            Integer integer = productMapper.insertProduct(product);
            if (integer > 0) return Result.ok("添加成功");
            return Result.err(Result.CODE_ERR_BUSINESS, "添加失败");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品已存在");
    }

    @Override
    public Result updateProductStateByPid(Product product) {

        Integer integer = productMapper.setProductStateByPid(product.getProductId(), product.getUpDownState());

        if (integer > 0) return Result.ok("修改成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败");
    }

    @Override
    public Result deleteProductByPid(List<Integer> productIdList) {
        Integer integer = productMapper.removeProductByPid(productIdList);

        if (integer > 0) return Result.ok("删除成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "删除失败");
    }

    @Value("${file.access-path}")
    private String accessPath;
    @Override
    public Result updateProductByPid(Product product) {
        Product product1 = productMapper.findProductByNum(product.getProductNum());
        if(product1 != null && !product1.getProductId().equals(product.getProductId()))
            return Result.err(Result.CODE_ERR_BUSINESS, "商品编号已存在");

        if(!product.getImgs().contains(accessPath))
            product.setImgs(accessPath + "/" + product.getImgs());

        Integer integer = productMapper.updateProductById(product);

        if (integer > 0) return Result.ok("修改成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败");
    }
}
