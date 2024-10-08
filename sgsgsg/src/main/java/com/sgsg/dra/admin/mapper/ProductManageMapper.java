package com.sgsg.dra.admin.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sgsg.dra.admin.domain.ProductManage;
import com.sgsg.dra.admin.domain.ProductStockManage;

@Mapper
public interface ProductManageMapper {
	public long productSeq();
	public long optionSeq();
	public long detailSeq();
	
	public void insertProduct(ProductManage dto) throws SQLException;
	public void insertProductFile(ProductManage dto) throws SQLException;
	public void insertProductOption(ProductManage dto) throws SQLException;
	public void insertOptionDetail(ProductManage dto) throws SQLException;

	public ProductManage findById(long productNum);
	public ProductManage findByCategory(long categoryNum);

	public List<ProductManage> listCategory();
	public List<ProductManage> listSubCategory(long parentNum);
	public List<ProductManage> listProduct(Map<String, Object> map);
	public List<ProductManage> listProductExcel(Map<String, Object> map);

	public int dataCount(Map<String, Object> map);
	public int dataCountStockList(Map<String, Object> map);

	public List<ProductManage> findByidOption(long productNum);
	public List<ProductManage> listProductFile(long productNum);
	public List<ProductManage> listProductOption(long productNum);
	public List<ProductManage> listOptionDetail(long optionNum);

	public void updateProduct(ProductManage dto) throws SQLException;
	public void updateProductOption(ProductManage dto) throws SQLException;
	public void updateOptionDetail(ProductManage dto) throws SQLException;
	public void deleteProduct(long productNum) throws SQLException;
	public void deleteProductFile(long fileNum) throws SQLException;
	public void deleteProductOption(long optionNum) throws SQLException;
	public void deleteOptionDetail(long detailNum) throws SQLException;
	public void deleteOptionDetail2(long optionNum) throws SQLException;

	// 상품 재고
	public void insertProductStock(ProductStockManage dto) throws SQLException;
	public void updateProductStock(ProductStockManage dto) throws SQLException;
	public List<ProductStockManage> listProductStock(Map<String, Object> map);
	public List<ProductManage> listProductForStock(Map<String, Object> map);
	public List<ProductManage> listProductForStockAndExcel(Map<String, Object> map);
	
	
}
