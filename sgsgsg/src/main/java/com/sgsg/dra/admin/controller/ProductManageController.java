package com.sgsg.dra.admin.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.sgsg.dra.admin.domain.ProductManage;
import com.sgsg.dra.admin.domain.ProductStockManage;
import com.sgsg.dra.admin.service.ProductManageService;
import com.sgsg.dra.common.ExcelView;
import com.sgsg.dra.common.MyUtil;

@Controller
@RequestMapping("adminManagement/productManage/*")
public class ProductManageController {
	
	@Autowired
	private ProductManageService service;
	
	@Autowired
	private MyUtil myUtil;
	
	@Autowired
	private ExcelView excelView;
	
	@RequestMapping("productList")
	public String productManageList(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") long parentNum,
			@RequestParam(defaultValue = "0") long categoryNum,
			@RequestParam(defaultValue = "2") int productShow,
			@RequestParam(defaultValue = "") String kwd,
			@RequestParam(defaultValue = "0") int priceMin,
			@RequestParam(defaultValue = "0") int priceMax,
			HttpServletRequest req,
			Model model) throws Exception {
		
		String cp = req.getContextPath();
		
		int size = 10;
		int total_page;
		int dataCount;
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			kwd = URLDecoder.decode(kwd, "UTF-8");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentNum", parentNum);
		map.put("categoryNum", categoryNum);
		map.put("productShow", productShow);
		map.put("kwd", kwd);
	    map.put("priceMin", priceMin);
	    map.put("priceMax", priceMax);
		
		dataCount = service.dataCount(map);
		total_page = myUtil.pageCount(dataCount, size);
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page - 1) * size;
		if(offset < 0) {
			offset = 0;
		}
		
		map.put("offset", offset);
		map.put("size", size);
		
		List<ProductManage> list = service.listProduct(map);
		List<ProductManage> listCategory = service.listCategory();
		
	    for (ProductManage dto : list) {
	        double roundedSalePrice = service.getRoundedSalePrice(dto.getPrice(), dto.getDiscountRate());
	        dto.setSalePrice((int)roundedSalePrice);
	    }
		
		/*
		//listSubCategory 안써서 안넣어도 됨
		List<ProductManage> listSubCategory = null;
		if(parentNum == 0 && listCategory.size() !=0) {
			parentNum = listCategory.get(0).getCategoryNum();
		}
		listSubCategory = service.listSubCategory(parentNum);
		if(categoryNum == 0 && listSubCategory.size() != 0) {
			categoryNum = listSubCategory.get(0).getCategoryNum();
		}
		*/
		
		String listUrl = cp + "/adminManagement/productManage/productList";
		String query = "";

		if (kwd != null && !kwd.isEmpty()) {
		    query += "kwd=" + kwd + "&";
		}
		if (parentNum != 0) {
		    query += "parentNum=" + parentNum + "&";
		    System.out.println(parentNum);
		}
		if (categoryNum != 0) {
		    query += "categoryNum=" + categoryNum + "&";
		}
		if (priceMin != 0) {
		    query += "priceMin=" + priceMin + "&";
		}
		if (priceMax != 0) {
		    query += "priceMax=" + priceMax + "&";
		}
		if (productShow != -1) {
			query += "productShow=" + productShow;
		}
		
		listUrl += "?" + query;
		
		String paging = myUtil.pagingUrl(current_page, total_page, listUrl);
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("list", list);
		
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("size", size);
		model.addAttribute("paging", paging);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		
		return ".adminLeft.product.productList";
	}
	
	@GetMapping("productWrite")
	public String productWriteForm(Model model) {
		List<ProductManage> listCategory = service.listCategory();
		List<ProductManage> listSubCategory = null;
		long parentNum = 0;
		if(listCategory.size() != 0) {
			parentNum = listCategory.get(0).getCategoryNum();
		}
		listSubCategory = service.listSubCategory(parentNum);
		
		model.addAttribute("mode", "productWrite");
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("listSubCategory", listSubCategory);
		
		return ".adminLeft.product.write";
	}
	
	@PostMapping("productWrite")
	public String productSubmit(
			ProductManage dto,
			HttpSession session,
			Model model) {
		String root = session.getServletContext().getRealPath("/");
		String path = root + "uploads" + File.separator + "product";
		
		try {
			service.insertProduct(dto, path);
		} catch (Exception e) {
		}
		
		//String url = "redirect:/adminManagement/productManage/productList?";
		
		return "redirect:/adminManagement/productManage/productList";
	}
	
	@GetMapping("productUpdate")
	public String productUpdateForm(
			@RequestParam long productNum,
			@RequestParam(value= "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") long parentNum,
			@RequestParam(defaultValue = "0") long categoryNum,
			Model model) throws Exception {
		
		ProductManage dto = service.findById(productNum);
		if(dto == null) {
			String query = "page=" + current_page;
			return "redirect:/adminManagement/productManage/productList" + query;
		}
		
		// 카테고리
		List<ProductManage> listCategory = service.listCategory();
		List<ProductManage> listSubCategory = service.listSubCategory(parentNum);
		
		// 추가 이미지
		List<ProductManage> listFile = service.listProductFile(productNum);
		
		// 옵션1/옵션2 옵션명
		List<ProductManage> listOption = service.listProductOption(productNum);
		
		// 옵션1/옵션2 상세 옵션
		List<ProductManage> listOptionDetail = null;
		List<ProductManage> listOptionDetail2 = null;
		if(listOption.size() > 0) {
			dto.setOptionNum(listOption.get(0).getOptionNum());
			dto.setOptionName(listOption.get(0).getOptionName());
			listOptionDetail = service.listOptionDetail(listOption.get(0).getOptionNum());
		}
		if(listOption.size() > 1) {
			dto.setOptionNum2(listOption.get(1).getOptionNum());
			dto.setOptionName2(listOption.get(1).getOptionName());
			listOptionDetail2 = service.listOptionDetail(listOption.get(1).getOptionNum());
		}
		
		model.addAttribute("mode", "productUpdate");
		
		model.addAttribute("dto", dto);
		model.addAttribute("classify", dto.getClassify());
		model.addAttribute("listFile", listFile);
		model.addAttribute("listOptionDetail", listOptionDetail);
		model.addAttribute("listOptionDetail2", listOptionDetail2);
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("listSubCategory", listSubCategory);
		model.addAttribute("parentNum", parentNum);
		
		model.addAttribute("page", current_page);
		
		return ".adminLeft.product.write";
	}
	
	@PostMapping("productUpdate")
	public String productUpdateSubmit(
			ProductManage dto,
			HttpSession session,
			Model model
			) throws Exception {
		
		String root = session.getServletContext().getRealPath("/");
		String path = root + "uploads" + File.separator + "product";
		
		try {
			service.updateProduct(dto, path);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return "redirect:/adminManagement/productManage/productList";
	}
	
	@PostMapping("deleteFile")
	@ResponseBody
	public Map<String, Object> deleteFile(@RequestParam long fileNum, 
			@RequestParam String filename,
			HttpSession session) throws Exception {

		String state = "true";
		try {
			String root = session.getServletContext().getRealPath("/");
			String pathname = root + "uploads" + File.separator + "product" + File.separator + filename;

			service.deleteProductFile(fileNum, pathname);
		} catch (Exception e) {
			state = "false";
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		return model;
	}
	
	@PostMapping("deleteOptionDetail")
	@ResponseBody
	public Map<String, Object> deleteOptionDetail(@RequestParam long detailNum) throws Exception {
		
		String state = "true";
		try {
			service.deleteOptionDetail(detailNum);
		} catch (Exception e) {
			state = "false";
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		return model;
	}
	
	
	@GetMapping("listSubCategory")
	@ResponseBody
	public Map<String, Object> listSubCategory(
			@RequestParam long parentNum) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<ProductManage> listSubCategory = service.listSubCategory(parentNum);
		
		model.put("listSubCategory", listSubCategory);
		
		return model;
	}
	
	@GetMapping("productDelete")
	public String deleteProduct(
			@RequestParam long productNum,
			HttpSession session
			) throws Exception {
		try {
			String root = session.getServletContext().getRealPath("/");
			String path = root + "uploads" + File.separator + "product";
			
			service.deleteProduct(productNum, path);
		} catch (Exception e) {
		}
		
		return "redirect:/adminManagement/productManage/productList";
	}
	
	@RequestMapping("stockList")
	public String stockManageList(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") long parentNum,
			@RequestParam(defaultValue = "0") long categoryNum,
			@RequestParam(defaultValue = "") String kwd,
			@RequestParam(defaultValue = "0") int stockLess,
			@RequestParam(defaultValue = "0") int stockGrater,
			HttpServletRequest req,
			Model model) throws Exception {
		
		String cp = req.getContextPath();
		
		int size = 10;
		int total_page;
		int dataCount;
		
		List<ProductManage> listCategory = service.listCategory();
		/*
		//listCategory 안써서 안넣어도 됨
		List<ProductManage> listSubCategory = null;
		if(parentNum == 0 && listCategory.size() !=0) {
			parentNum = listCategory.get(0).getCategoryNum();
		}
		listSubCategory = service.listSubCategory(parentNum);
		if(categoryNum == 0 && listSubCategory.size() != 0) {
			categoryNum = listSubCategory.get(0).getCategoryNum();
		}
		*/
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("stockLess", stockLess);
		map.put("stockGrater", stockGrater);
		map.put("kwd", kwd);
		map.put("parentNum", parentNum);
		map.put("categoryNum", categoryNum);
		
		dataCount = service.dataCountStockList(map);
		
		total_page = myUtil.pageCount(dataCount, size);
		if(current_page > total_page) {
			current_page = total_page;
		}
		int offset = (current_page - 1) * size;
		if(offset < 0) {
			offset = 0;
		}
		
		map.put("offset", offset);
		map.put("size", size);
		
		List<ProductManage> list = service.listProductForStock(map);
		
		String listUrl = cp + "/adminManagement/productManage/stockList";
		String query = "";

		if (kwd != null && !kwd.isEmpty()) {
		    query += "kwd=" + kwd + "&";
		}
		if (parentNum != 0) {
		    query += "parentNum=" + parentNum + "&";
		    System.out.println(parentNum);
		}
		if (categoryNum != 0) {
		    query += "categoryNum=" + categoryNum + "&";
		}
		if (stockLess != 0) {
		    query += "stockLess=" + stockLess + "&";
		}
		if (stockGrater != 0) {
		    query += "stockGrater=" + stockGrater + "&";
		}
		if (!query.isEmpty() && query.endsWith("&")) {
		    query = query.substring(0, query.length() - 1);
		}
		
		listUrl += "?" + query;
		
		String paging = myUtil.pagingUrl(current_page, total_page, listUrl);
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("list", list);
		
		model.addAttribute("page", current_page);
		model.addAttribute("total_page", total_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("paging", paging);
		model.addAttribute("size", size);
		
		return ".adminLeft.product.stockList";
	}
	
	// AJAX-Text
	@GetMapping("listProductStock")
	public String listProductStock(@RequestParam Map<String, Object> paramMap, Model model) throws Exception {
		// 상세 옵션별 재고 -----
		try {
			List<ProductStockManage> list = service.listProductStock(paramMap); // productNum, optionCount
			
			if(list.size() >= 1) {
				String productName = list.get(0).getProductName();
				String title = list.get(0).getOptionName();
				String title2 = list.get(0).getOptionName2();
				
				model.addAttribute("productNum", paramMap.get("productNum"));
				model.addAttribute("productName", productName);
				model.addAttribute("title", title);
				model.addAttribute("title2", title2);
			}
			
			model.addAttribute("list", list);
		} catch (Exception e) {
		}
		
		return "adminLeft/product/listProductStock";
	}
	
	@PostMapping("updateProductStock")
	@ResponseBody
	public Map<String, Object> updateProductStock(ProductStockManage dto) throws Exception {
		// 상세 옵션별 재고 추가 또는 변경 -----
		String state = "true";
		try {
			service.updateProductStock(dto);
		} catch (Exception e) {
			state = "false";
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		return model;
	}
	
	@RequestMapping("excelProductAllDownload")
	public View downloadExcel(
			@RequestParam(defaultValue = "0") long parentNum,
			@RequestParam(defaultValue = "0") long categoryNum,
			@RequestParam(defaultValue = "") String kwd,
			@RequestParam(defaultValue = "0") int priceMin,
			@RequestParam(defaultValue = "0") int priceMax,
			Map<String, Object> model) throws Exception {
		String sheetName = "상품목록";
		
		List<String> columnLabels = new ArrayList<String>();
		
		columnLabels.add("상품코드");
		columnLabels.add("상품명");
		columnLabels.add("상위카테고리");
		columnLabels.add("하위카테고리");
		columnLabels.add("상품등록일");
		columnLabels.add("진열여부");
		columnLabels.add("정가");
		columnLabels.add("할인율");
		columnLabels.add("판매가");
		columnLabels.add("적립금");
		
		List<Object[]> columnValues = new ArrayList<Object[]>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("parentNum", parentNum);
		map.put("categoryNum", categoryNum);
		map.put("kwd", kwd);
	    map.put("priceMin", priceMin);
	    map.put("priceMax", priceMax);
		
		List<ProductManage> list = service.listProductExcel(map);
		
		for(ProductManage dto : list) {
			String parentCategory = service.parseParentNum(dto.getParentNum());
			String parsedShow = service.parseProductShow(dto.getProductShow());
			double salePrice = dto.getDiscountRate() == 0 ? dto.getPrice() : dto.getPrice() * (dto.getDiscountRate() * 0.01);
			columnValues.add(new Object[] {dto.getProductNum(), dto.getProductName(), 
					parentCategory, dto.getCategoryName(), dto.getReg_date(),
					parsedShow, dto.getPrice(), dto.getDiscountRate(), salePrice, dto.getSavedMoney()});
		}
		
		model.put("filename", "productList.xlsx");
		model.put("sheetName", sheetName);
		model.put("columnLabels", columnLabels);
		model.put("columnValues", columnValues);
		
		return excelView;
	}
	
	@RequestMapping("excelStockAllDownload")
	public View downloadExcelStock(
			@RequestParam(defaultValue = "0") long parentNum,
			@RequestParam(defaultValue = "0") long categoryNum,
			@RequestParam(defaultValue = "") String kwd,
			Map<String, Object> model) throws Exception {
		String sheetName = "상품목록";
		
		List<String> columnLabels = new ArrayList<String>();
		
		columnLabels.add("상품코드");
		columnLabels.add("상품명");
		columnLabels.add("상위카테고리");
		columnLabels.add("하위카테고리");
		columnLabels.add("상품등록일");
		columnLabels.add("진열여부");
		columnLabels.add("전체재고");
		
		List<Object[]> columnValues = new ArrayList<Object[]>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("parentNum", parentNum);
		map.put("categoryNum", categoryNum);
		map.put("kwd", kwd);
		
		List<ProductManage> list = service.listProductForStockAndExcel(map);
		
		for(ProductManage dto : list) {
			String parentCategory = service.parseParentNum(dto.getParentNum());
			String parsedShow = service.parseProductShow(dto.getProductShow());

			columnValues.add(new Object[] {dto.getProductNum(), dto.getProductName(), parentCategory,
					dto.getCategoryName(), dto.getReg_date(), parsedShow, dto.getTotalStock()});
		}
		
		model.put("filename", "stockList.xlsx");
		model.put("sheetName", sheetName);
		model.put("columnLabels", columnLabels);
		model.put("columnValues", columnValues);
		
		return excelView;
	}
	
	
}
