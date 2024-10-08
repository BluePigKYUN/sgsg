package com.sgsg.dra.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sgsg.dra.admin.domain.ProductManage;
import com.sgsg.dra.admin.domain.ProductStockManage;
import com.sgsg.dra.admin.mapper.ProductManageMapper;
import com.sgsg.dra.common.FileManager;

@Service
public class ProductManageServiceImpl implements ProductManageService {
	@Autowired
	private ProductManageMapper mapper;
	
	@Autowired
	private FileManager fileManager;

	@Override
	public void insertProduct(ProductManage dto, String pathname) throws Exception {
		try {
			String filename = fileManager.doFileUpload(dto.getThumbnailFile(), pathname);
			dto.setThumbnail(filename);

			long productNum = mapper.productSeq();
			dto.setProductNum(productNum);

			mapper.insertProduct( dto);
			
			//추가적인 이미지
			if(! dto.getAddFiles().isEmpty()) {
				for(MultipartFile mf : dto.getAddFiles()) {
					filename = fileManager.doFileUpload(mf, pathname);
					if(filename == null) {
						continue;
					}
					dto.setFilename(filename);
					
					mapper.insertProductFile(dto);
				}
			}
			//옵션 추가
			if(dto.getOptionCount() > 0) {
				insertProductOption(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ProductManage findByCategory(long categoryNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductManage> listCategory() {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProductManage> listSubCategory(long parentNum) {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listSubCategory(parentNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	//옵션
	private void insertProductOption(ProductManage dto) throws Exception {
		try {
			long optionNum = 0, optionNum2 = 0;
			long detailNum;
			
			// 옵션1
			if(dto.getOptionCount() > 0) {
				optionNum = mapper.optionSeq();
				dto.setOptionNum(optionNum);
				dto.setParentOption(null);
				mapper.insertProductOption(dto);
				
				// 옵션1의 값 추가
				dto.setDetailNums(new ArrayList<Long>());
				for(String optionValue : dto.getOptionValues()) {
					detailNum = mapper.detailSeq(); 
					dto.setDetailNum(detailNum);
					dto.setOptionValue(optionValue);
	
					mapper.insertOptionDetail(dto);
					
					dto.getDetailNums().add(detailNum);
				}
			}
			//옵션 2
			if(dto.getOptionCount() > 1) {
				optionNum2 = mapper.optionSeq();
				dto.setOptionNum(optionNum2);
				dto.setOptionName(dto.getOptionName2());
				dto.setParentOption(optionNum);
				mapper.insertProductOption(dto);
				
				// 옵션 2 값 추가
				dto.setDetailNums2(new ArrayList<Long>());
				for(String optionValue2 : dto.getOptionValues2()) {
					detailNum = mapper.detailSeq(); 
					dto.setDetailNum(detailNum);
					dto.setOptionValue(optionValue2);
					mapper.insertOptionDetail(dto);
					
					dto.getDetailNums2().add(detailNum);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = mapper.dataCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ProductManage> listProduct(Map<String, Object> map) {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listProduct(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<ProductManage> listProductExcel(Map<String, Object> map) {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listProductExcel(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<ProductManage> listProductForStock(Map<String, Object> map) {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listProductForStock(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<ProductManage> listProductForStockAndExcel(Map<String, Object> map) {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listProductForStockAndExcel(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	@Override
	public List<ProductStockManage> listProductStock(Map<String, Object> map) {
		List<ProductStockManage> list = null;
		
		try {
			list = mapper.listProductStock(map);
		} catch (Exception e) {
		}
		
		return list;
	}
	
	@Override
	public void updateProductStock(ProductStockManage dto) throws Exception{
		try {
			// 상세 옵션별 재고 추가 또는 변경
			for(int idx = 0; idx < dto.getStockNums().size(); idx++) {
				dto.setStockNum(dto.getStockNums().get(idx));
				if(dto.getDetailNums() != null && dto.getDetailNums().get(idx) != 0) {
					dto.setDetailNum(dto.getDetailNums().get(idx));
				}
				if(dto.getDetailNums2() != null && dto.getDetailNums2().get(idx) != 0) {
					dto.setDetailNum2(dto.getDetailNums2().get(idx));
				}
				dto.setTotalStock(dto.getTotalStocks().get(idx));
				
				if(dto.getStockNum() == 0) {
					mapper.insertProductStock(dto);
				} else {
					mapper.updateProductStock(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void updateProduct(ProductManage dto, String pathname) throws Exception {
		try {
			String filename = fileManager.doFileUpload(dto.getThumbnailFile(), pathname);
			if(filename != null) {
				
				if(dto.getThumbnail().length() != 0) {
					fileManager.doFileDelete(dto.getThumbnail(), pathname);
				}
				
				dto.setThumbnail(filename);
			}
			
			//상품 수정
			mapper.updateProduct(dto);
			
			//추가 이미지 부분
			if(! dto.getAddFiles().isEmpty()) {
				for(MultipartFile mf : dto.getAddFiles()) {
					filename = fileManager.doFileUpload(mf, pathname);
					if(filename == null) {
						continue;
					}
					dto.setFilename(filename);
					
					mapper.insertProductFile(dto);
				}
			}
			
			updateProductOption(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void updateProductOption(ProductManage dto) throws Exception {
		try {
			if(dto.getOptionCount() == 0) {
				// 기존 옵션1, 옵션2 삭제
				if(dto.getPrevOptionNum2() != 0) {
					mapper.deleteOptionDetail2(dto.getPrevOptionNum2());
					mapper.deleteProductOption(dto.getPrevOptionNum2());
				}
				
				if(dto.getPrevOptionNum() != 0) {
					mapper.deleteOptionDetail2(dto.getPrevOptionNum());
					mapper.deleteProductOption(dto.getPrevOptionNum());
				}
				
				return;
			} else if(dto.getOptionCount() == 1) {
				// 기존 옵션 2 삭제
				if(dto.getPrevOptionNum2() != 0) {
					mapper.deleteOptionDetail2(dto.getPrevOptionNum2());
					mapper.deleteProductOption(dto.getPrevOptionNum2());
				}
			}
			
			long detailNum, parentNum;
			
			// 옵션1
			// 옵션1이 없는 상태에서 옵션1을 추가한 경우
			if(dto.getOptionNum() == 0) {
				insertProductOption(dto);
				return;
			}
			// 옵션1이 존재하는 경우 옵션1 수정
			mapper.updateProductOption(dto);
			
			// 기존 옵션1 옵션값 수정
			int size = dto.getDetailNums().size();
			for(int i = 0; i < size; i++) {
				dto.setDetailNum(dto.getDetailNums().get(i));
				dto.setOptionValue(dto.getOptionValues().get(i));
				mapper.updateOptionDetail(dto);
			}

			// 새로운 옵션1 옵션값 추가
			dto.setDetailNums(new ArrayList<Long>());
			for(int i = size; i < dto.getOptionValues().size(); i++) {
				detailNum = mapper.detailSeq(); 
				dto.setDetailNum(detailNum);
				dto.setOptionValue(dto.getOptionValues().get(i));
				mapper.insertOptionDetail(dto);
				
				dto.getDetailNums().add(detailNum);
			}

			// 옵션2
			if(dto.getOptionCount() > 1) {
				//  옵션2가 없는 상태에서 옵션2를 추가한 경우
				parentNum = dto.getOptionNum(); // 옵션1 옵션번호 
				if(dto.getOptionNum2() == 0) {
					long optionNum2 = mapper.optionSeq();
					dto.setOptionNum(optionNum2);
					dto.setOptionName(dto.getOptionName2());
					dto.setParentOption(parentNum);
					mapper.insertProductOption(dto);
					
					// 옵션 2 값 추가
					dto.setDetailNums2(new ArrayList<Long>());
					for(String optionValue2 : dto.getOptionValues2()) {
						detailNum = mapper.detailSeq(); 
						dto.setDetailNum(detailNum);
						dto.setOptionValue(optionValue2);
						mapper.insertOptionDetail(dto);
						
						dto.getDetailNums2().add(detailNum);
					}
					
					return;
				} 
				
				// 옵션2 가 존재하는 경우 옵션2 수정
				dto.setOptionNum(dto.getOptionNum2());
				dto.setOptionName(dto.getOptionName2());
				mapper.updateProductOption(dto);
				
				// 기존 옵션2 옵션값 수정
				int size2 = dto.getDetailNums2().size();
				for(int i = 0; i < size2; i++) {
					dto.setDetailNum(dto.getDetailNums2().get(i));
					dto.setOptionValue(dto.getOptionValues2().get(i));
					mapper.updateOptionDetail(dto);
				}
	
				// 새로운 옵션2 옵션값 추가
				dto.setDetailNums2(new ArrayList<Long>());
				for(int i = size2; i < dto.getOptionValues2().size(); i++) {
					detailNum = mapper.detailSeq(); 
					dto.setDetailNum(detailNum);
					dto.setOptionValue(dto.getOptionValues2().get(i));
					mapper.insertOptionDetail(dto);
					
					dto.getDetailNums2().add(detailNum);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void deleteProduct(long productNum, String pathname) throws Exception {
	    try {
	        // 추가 이미지의 리스트
	        List<ProductManage> moreImage = mapper.listProductFile(productNum);
	        ProductManage dto = mapper.findById(productNum);
	        if (dto == null) {
	            return;
	        }
	        // 파일 삭제(thumbnail)
	        fileManager.doFileDelete(dto.getFilename(), pathname);
	        mapper.deleteProductFile(dto.getFileNum());
	        
	        // 추가 파일 삭제
	        if (moreImage != null && !moreImage.isEmpty()) {
	            for (ProductManage item : moreImage) {
	                if (item.getFilename() != null && !item.getFilename().isEmpty()) {
	                    fileManager.doFileDelete(item.getFilename(), pathname);
	                    mapper.deleteProductFile(item.getFileNum());
	                }
	            }
	        }
	        
	        // 옵션 삭제
	        if (dto.getOptionCount() > 0) {
	            List<ProductManage> opDto = mapper.findByidOption(productNum);
	            for (ProductManage option : opDto) {
	                if (option.getOptionNum2() != null && option.getOptionNum2() != 0) {
	                    mapper.deleteOptionDetail2(option.getOptionNum2());
	                    mapper.deleteProductOption(option.getOptionNum2());
	                }
	                if (option.getOptionNum() != null && option.getOptionNum() != 0) {
	                    mapper.deleteOptionDetail2(option.getOptionNum());
	                    mapper.deleteProductOption(option.getOptionNum());
	                }
	            }
	        }
	        
	        // 재고 삭제 (추가 기능 구현 필요)
	        // TODO: 재고가 있으면 삭제되게끔 하면 안됨...
	        
	        // 상품 삭제
	        mapper.deleteProduct(productNum);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	@Override
	public void deleteProductFile(long fileNum, String pathname) throws Exception {
		try {
			if (pathname != null) {
				fileManager.doFileDelete(pathname);
			}

			mapper.deleteProductFile(fileNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	@Override
	public void deleteOptionDetail(long detailNum) throws Exception {
		try {
			mapper.deleteOptionDetail(detailNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<ProductManage> listProductFile(long productNum) {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listProductFile(productNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProductManage> listProductOption(long productNum) {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listProductOption(productNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProductManage> listOptionDetail(long optionNum) {
		List<ProductManage> list = null;
		
		try {
			list = mapper.listOptionDetail(optionNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public ProductManage findById(long productNum) {
		ProductManage dto = null;
		
		try {
			dto = mapper.findById(productNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public int dataCountStockList(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = mapper.dataCountStockList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//parseing 및 연산에 사용할 메서드
	@Override
    public String parseParentNum(long parentNum) {
        Map<Long, String> parentNumMap = new HashMap<>();
        parentNumMap.put((long) 1, "침구");
        parentNumMap.put((long) 2, "가전");
        parentNumMap.put((long) 3, "의류");
        parentNumMap.put((long) 4, "향초");
        parentNumMap.put((long) 5, "조명");
        parentNumMap.put((long) 6, "영양제");
        parentNumMap.put((long) 7, "수면용품");
        parentNumMap.put((long) 8, "졸음방지용품");
        
        return parentNumMap.getOrDefault(parentNum, "기타");
    }
	
	@Override
	public String parseProductShow(int showNum) {
		String parsedStr = null;
		
		switch (showNum) {
		case 0: parsedStr = "미진열"; break;
		case 1: parsedStr = "진열"; break;
		default : parsedStr = "오류값";
		
	}
		return parsedStr;
	}
	
	@Override
    public double getRoundedSalePrice(double price, double discountRate) {
        double discountedPrice = price * (1 - discountRate / 100.0);
        return Math.round(discountedPrice / 100) * 100;
	}

}
