package com.zerol.crm.repository;

import com.zerol.crm.entry.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdRepository {


    public Product save(Product product);

    public void deleteProductByID(long prodID);

    public void updateProduct(Product product);

    public Product getProductByProdNO(String prodNO);

    public Product findOne(Long prodID);

    public List<Product> findAll();
}