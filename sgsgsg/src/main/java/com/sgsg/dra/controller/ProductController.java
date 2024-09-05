package com.sgsg.dra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sgsg.dra.common.MyUtil;
import com.sgsg.dra.domain.Product;
import com.sgsg.dra.domain.Review;
import com.sgsg.dra.domain.SessionInfo;
import com.sgsg.dra.service.ProductService;
import com.sgsg.dra.service.ReviewService;
import com.sgsg.dra.service.WishlistService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	 @Autowired
	 private ReviewService reviewService;

	 @Autowired
	 private WishlistService wishlistService;
	 
	@Autowired
	private MyUtil myUtil;

	@GetMapping("listAllCategory")
	@ResponseBody
	public Map<String, Object> listAllCategory() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<Product> listMain = service.listCategory();
		List<Product> listAll = service.listAllCategory();

		model.put("listMain", listMain);
		model.put("listAll", listAll);

		return model;
	}

	@GetMapping("listCategory")
	@ResponseBody
	public List<Product> listCategory() {
		List<Product> list = service.listCategory();
		return list;
	}

	@GetMapping("listSubCategory")
	@ResponseBody
	public List<Product> listSubCategory(@RequestParam long parentNum) {
		List<Product> list = service.listSubCategory(parentNum);
		return list;
	}

	@GetMapping("listOptionDetail2")
	@ResponseBody
	public List<Product> listOptionDetail2(@RequestParam long optionNum, @RequestParam long optionNum2,
			@RequestParam long detailNum) {
		List<Product> list = service.listOptionDetail(optionNum2);
		return list;
	}

	@GetMapping("listOptionDetailStock")
	@ResponseBody
	public List<Product> listOptionDetailStock(@RequestParam Long productNum,
			@RequestParam(required = false) Long detailNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("productNum", productNum);
		if (detailNum != null) {
			map.put("detailNum", detailNum);
		}

		List<Product> list = service.listOptionDetailStock(map);
		return list;
	}
	
	
	@GetMapping("details")
	public String productDetail(@RequestParam Long productNum, Model model, HttpSession session) {
	    try {
	        // 상품 정보 조회
	        Product dto = service.findById(productNum);
	        if (dto == null) {
	            return "redirect:/home";
	        }
	        
	        // 상품 파일(이미지) 목록 조회
	        List<Product> listFile = service.listProductFile(productNum);
	        
	        // 상품 옵션 목록 조회
	        List<Product> listOption = service.listProductOption(productNum);
	        
	        List<Product> listOptionDetail = null;
	        if (listOption != null && !listOption.isEmpty()) {
	            listOptionDetail = service.listOptionDetail(listOption.get(0).getOptionNum());
	        }
	        
	        // 재고 정보 조회
	        Map<String, Object> map = new HashMap<>();
	        map.put("productNum", dto.getProductNum());
	        List<Product> listStock = service.listOptionDetailStock(map);
	        
	        if (dto.getOptionCount() == 0) {
	            // 단품 상품인 경우
	            if (listStock != null && !listStock.isEmpty()) {
	                dto.setStockNum(listStock.get(0).getStockNum());
	                dto.setTotalStock(listStock.get(0).getTotalStock());
	            }
	        } else if (dto.getOptionCount() > 0 && listOptionDetail != null && listStock != null) {
	            for (Product vo : listOptionDetail) {
	                for (Product ps : listStock) {
	                    if (vo.getDetailNum().equals(ps.getDetailNum())) {
	                        vo.setStockNum(ps.getStockNum());
	                        vo.setTotalStock(ps.getTotalStock());
	                        break;
	                    }
	                }
	            }
	        }
	       
	        // 리뷰 수와 평균 평점 조회
	        int reviewCount = reviewService.countReviews(productNum);
	        double avgScore = reviewService.getAvgScore(productNum);
	        
	        // 북마크 관련 정보 추가
	        int totalBookmarkCount = wishlistService.getProductWishlistCount(productNum);
	        
	        // 로그인한 사용자의 북마크 상태 확인
	        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("member");
	        boolean isBookmarked = false;
	        if (sessionInfo != null) {
	            isBookmarked = wishlistService.isInWishlist(sessionInfo.getUserId(), productNum);
	        }
	        
	        // Model에 데이터 추가
	        model.addAttribute("isBookmarked", isBookmarked);
	        model.addAttribute("dto", dto);
	        model.addAttribute("listFile", listFile);
	        model.addAttribute("listOption", listOption);
	        model.addAttribute("listOptionDetail", listOptionDetail);
	        model.addAttribute("reviewCount", reviewCount);
	        model.addAttribute("avgScore", avgScore);
	        model.addAttribute("totalBookmarkCount", totalBookmarkCount);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ".product.details";
	}
	@GetMapping("reviews")
	@ResponseBody
	public Map<String, Object> getReviews(
	        @RequestParam Long productNum,
	        @RequestParam(value = "pageNo", defaultValue = "1") int current_page,
	        HttpServletRequest request) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        int size = 5;
	        int offset = (current_page - 1) * size;
	        if (offset < 0) offset = 0;

	        Map<String, Object> map = new HashMap<>();
	        map.put("productNum", productNum);
	        map.put("offset", offset);
	        map.put("size", size);

	        List<Review> reviewList = reviewService.listReviews(map);

	        // 이미지 URL 설정
	        String contextPath = request.getContextPath();
	        for (Review review : reviewList) {
	            if (review.getFileName() != null && !review.getFileName().isEmpty()) {
	                String imageUrl = contextPath + "/uploads/review/" + review.getFileName();
	                if (review.getImageUrls() == null) {
	                    review.setImageUrls(new ArrayList<>());
	                }
	                review.getImageUrls().add(imageUrl);
	            }
	        }

	        int dataCount = reviewService.countReviews(productNum);
	        double avgScore = reviewService.getAvgScore(productNum);
	        int total_page = myUtil.pageCount(dataCount, size);
	        String paging = myUtil.pagingMethod(current_page, total_page, "loadReviews");

	        result.put("reviewList", reviewList);
	        result.put("dataCount", dataCount);
	        result.put("avgScore", avgScore);
	        result.put("total_page", total_page);
	        result.put("pageNo", current_page);
	        result.put("paging", paging);
	    } catch (Exception e) {
	        e.printStackTrace();
	        result.put("error", "리뷰를 불러오는 중 오류가 발생했습니다.");
	    }
	    return result;
	}

	@GetMapping("category")
	public String categoryView(@RequestParam Long categoryNum, 
			@RequestParam(required = false) Long subCategoryNum,
	        Model model) {
	    // 현재 카테고리 정보 가져오기
	    Product category = service.getCategoryById(categoryNum);
	    if (category == null) {
	        // 카테고리가 존재하지 않을 경우 에러 페이지로 리다이렉트
	    	return "redirect:/error";

	    }

	    // 메인 카테고리 목록 가져오기
	    List<Product> mainCategories = service.selectCategoryList();
	    // 서브 카테고리 목록 가져오기
	    List<Product> subCategories = service.listSubCategory(categoryNum);

	    // 해당 카테고리의 상품 목록 가져오기
	    List<Product> products;
	    if (subCategoryNum != null) {
	        Product subCategory = service.getCategoryById(subCategoryNum);
	        if (subCategory == null) {
	            return "redirect:/error/categoryNotFound";
	        }
	        products = service.getProductsByCategory(subCategoryNum);
	    } else {
	        products = service.getProductsByCategory(categoryNum);
	    }

	    model.addAttribute("category", category);
	    model.addAttribute("mainCategories", mainCategories);
	    model.addAttribute("subCategories", subCategories);
	    model.addAttribute("subCategoryNum", subCategoryNum);
	    model.addAttribute("products", products);
	    return ".product.category";
	}

	@GetMapping("search")
	public String searchProducts(@RequestParam String searchTerm,
			@RequestParam(value = "pageNo", defaultValue = "1") int current_page, Model model) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int size = 10; // 페이지당 표시할 상품 수

			map.put("searchTerm", searchTerm.trim());

			int dataCount = service.searchProductsCount(map);

			int total_page = myUtil.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int offset = (current_page - 1) * size;
			if (offset < 0)
				offset = 0;

			map.put("offset", offset);
			map.put("size", size);

			List<Product> list = service.searchProducts(map);

			String paging = myUtil.pagingFunc(current_page, total_page, "loadPage");

			model.addAttribute("list", list);
			model.addAttribute("dataCount", dataCount);
			model.addAttribute("size", size);
			model.addAttribute("pageNo", current_page);
			model.addAttribute("paging", paging);
			model.addAttribute("total_page", total_page);
			model.addAttribute("searchTerm", searchTerm);

		} catch (Exception e) {
			e.printStackTrace();
		
		}

		return ".product.searchResults";
	}
	
	@GetMapping("category/products")
	@ResponseBody
	public Map<String, Object> getCategoryProducts(
	        @RequestParam Long categoryNum,
	        @RequestParam(required = false) Long subCategoryNum) {
	    
	    Map<String, Object> response = new HashMap<>();

	    Product category = service.getCategoryById(categoryNum);
	    List<Product> subCategories = service.listSubCategory(categoryNum);
	    List<Product> products;

	    if (subCategoryNum != null) {
	        products = service.getProductsByCategory(subCategoryNum);
	    } else {
	        products = service.getProductsByCategory(categoryNum);
	    }

	    response.put("category", category);
	    response.put("subCategories", subCategories);
	    response.put("products", products);

	    return response;
	}
	
	@RequestMapping("soon")
	public String comingSoon() {
		return ".product.soon";
	}
}