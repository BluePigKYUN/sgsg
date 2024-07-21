<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


			<div class="css-ow22sl e1v2ggz01">
				
				<!-- 포인트 칸 -->
				<div class="css-77kc86 e1t6i3i25">
					<div class="css-0 e1t6i3i24">
						<h2 class="css-ojabq6 e1t6i3i23" style="font-size: 40px;">사용 가능한 포인트</h2>
						<p class="css-1irwz7g e1t6i3i22">${point.remain_points} P</p>
					</div>
					<div class="css-1yu4f1z e1t6i3i21">
						<p class="css-1yx9urm e1t6i3i20">30일 이내 소멸 예정 포인트<b> 0 P</b></p>
					</div>
				</div>
				
			
			<!-- 포인트 탭 -->
				<div class="review-item">
					<div class="row align-items-center">
						<c:forEach var="dto" items="${list}">
							<div class="col-md-2">
								<h5>${dto.change_date}</h5>
							</div>
							<div class="col-md-7">
									<div class ="css-s5xdrg e1rx7pum4"><h2 class="css-z57iji e1rx7pum2">${dto.reason}</h2><div class="accumulate css-1yj5lcs e1rx7pum3" style="width: 40px;">${dto.change_points >= 0 ? "적립" : "사용" }</div></div>
									<p class="text-muted">
										<c:choose>
											<c:when test="${not empty dto.productOrderName }">
												<c:if test="${dto.change_points > 0}">
													상품구매확정: ${dto.productOrderName}
												</c:if>
												<c:if test="${dto.change_points < 0}">
													${dto.productOrderName}
												</c:if>												
											</c:when>
											<c:otherwise>
											  기타
											 </c:otherwise>
										</c:choose>
								
									</p>
							</div>
							<div class="col-md-3 text-end">
								<button class="btn ${dto.change_points >= 0 ? 'btn-primary' : 'btn-danger' } ">
									${dto.change_points>=0 ? "+" :""}${dto.change_points}</button>
							</div>
						</c:forEach>
					</div>
				</div>
				
			</div>
					
