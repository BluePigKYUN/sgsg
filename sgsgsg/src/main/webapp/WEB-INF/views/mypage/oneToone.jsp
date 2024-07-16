<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

			<div class="css-ow22sl e1v2ggz01">
				
				<!-- 1:1 문의 탭 -->
					<div class="tab-pane fade show active" id="inquiry">
						<h4 class="mb-4" style="text-align: center;   font-size: 40px; margin: 50px;">1:1 문의 내역</h4>
					</div>
					
					
					<div class="col-md-6">
						<select class="form-select" style=" width: 200px; ">
							
							<option>전체</option>
							<option>답변완료</option>
							<option>답변대기</option>
						</select>
					</div>
					
				
					
					<div class="table-style6">
							<div>
								<span>
									<div class="table-header" >
										
											<div class="date">작성일</div>
											<div class="inquiry">문의구분</div>
											<div class="subject">제목</div>
											<div class="answer">답변여부</div>
										
									</div>
								</span>
							</div>
							<hr>
							<div>
								<span>
									<div class="table-content" >
										
										<c:forEach var="dto" items="${list}">
											<div class="cont" >
												<div class="date">${fn:substring(dto.created_date,0,10)}</div>
												<div class="inquiry">${dto.category}</div>
												<div class="subject" onclick="inquiryDetials('${dto.num}')" >${dto.title}</div>
												<div class="answer">
													${ empty dto.response_date ? "답변대기":"답변완료"}
												</div>
											</div>
										</c:forEach>
										
											<div class="page-navigation text-center" >${dataCount==0 ? "등록된 문의가 없습니다." : paging}</div>
										
									</div>
								</span>
							</div>
					</div>
					
					
					<input type="radio" id="option4" name="category" value="글 작성" class="radio-input" ${category == '글 작성' ? 'checked' : ''} onclick="inquiryModalOpen();">
				        <label for="option4" class="radio-label" style="width: 150px; text-align: center; font-size: 20px;">글 작성</label>
		
		
			</div>


<!-- 새 문의 작성 모달 -->
	<div class="modal fade  " id="inquiryModal" tabindex="-1"
		aria-labelledby="inquiryModalLabel" aria-hidden="true" style="z-index: 99999">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="inquiryModalLabel">새 문의 작성</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form name="inquiryForm">
						<div class="mb-3">
							<label for="inquiryType" class="form-label">문의 유형</label> <select
								class="form-select" id="inquiryType" name="category">
								<option>배송 관련</option>
								<option>상품 관련</option>
								<option>결제 관련</option>
								<option>기타</option>
							</select>
						</div>
						<div class="mb-3">
							<label for="inquiryTitle" class="form-label">제목</label> <input
								type="text" class="form-control" id="inquiryTitle" name="title">
						</div>
						<div class="mb-3">
							<label for="inquiryContent" class="form-label">문의 내용</label>
							<textarea class="form-control" id="inquiryContent" rows="3" name="content"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" onclick="inquirySave();" >문의 등록</button>
				</div>
			</div>
		</div>
	</div>
