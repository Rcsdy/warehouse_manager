package com.pn.mapper;

import com.pn.entity.Product;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    public Integer findProductRowCount(Product product);

    public List<Product> findProductByPage(@Param("page") Page page, @Param("product") Product product);

    public Product findProductByNum(String productNum);

    public Integer insertProduct(Product product);

    public Integer setProductStateByPid(Integer productId, String upDownState);

    public Integer removeProductByPid(List<Integer> productIdList);

    public Integer updateProductById(Product product);

    public Integer setInventById(Integer productId, Integer invent);

    public Integer findInventByProductId(Integer productId);
}
