package com.zerol.crm.service;

import com.zerol.crm.entry.Product;
import com.zerol.crm.repository.ProdRepository;
import com.zerol.crm.service.web.ProdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProdServiceImpl implements ProdService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProdServiceImpl.class);

    @Autowired
    private ProdRepository prodRepository;

    public void setProdRepository(ProdRepository prodRepository) {
        this.prodRepository = prodRepository;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return prodRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductByID(long prodID) {
        prodRepository.deleteProductByID(prodID);
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        prodRepository.updateProduct(product);
    }

    @Override
    public Product getProductByID(long prodID) {
        return prodRepository.findOne(prodID);
    }

    @Override
    public Product getProductByProdNO(String prodNO) {
        return prodRepository.getProductByProdNO(prodNO);
    }

    @Override
    public List<Product> getAllProductList() {
        return prodRepository.findAll();
    }

    @Override
    @Transactional
    public void updateTotalBackupCountOfProduct(String prodNO, int currCount, int originalCount) {
        //re-calculate the new total count of backup count of the product.
        Product prod = this.getProductByProdNO(prodNO);

        if (prod != null) {

            if (originalCount > currCount) {
                prod.setBackupCount(prod.getBackupCount() + originalCount - currCount);
            } else if (currCount > originalCount) {
                prod.setBackupCount((prod.getBackupCount() + originalCount > currCount) ? (prod.getBackupCount() + originalCount - currCount) : 0);
            }
            this.updateProduct(prod);
        }
    }

}