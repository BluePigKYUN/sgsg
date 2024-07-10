package com.sgsg.dra.admin.service;

import java.util.List;

import com.sgsg.dra.admin.domain.ProductManage;

public interface ProductManageService {
	public void insertProduct(ProductManage dto, String pathname) throws Exception;
	
	public ProductManage findByCategory(long categoryNum);
	public List<ProductManage> listCategory();
	public List<ProductManage> listSubCategory(long parentNum);
}
