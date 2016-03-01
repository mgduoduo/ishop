package com.zerol.crm.service.web;

import com.zerol.crm.entry.Product;

import java.util.List;

public interface ProdService {

	public Product save(Product product);
	public void deleteProductByID(long prodID);
	public void updateProduct(Product product);
	public Product getProductByID(long prodID);
	public Product getProductByProdNO(String prodNO);
	public List<Product> getAllProductList();

	public void updateTotalBackupCountOfProduct(String prodNO, int currCount, int originalCount);
}

