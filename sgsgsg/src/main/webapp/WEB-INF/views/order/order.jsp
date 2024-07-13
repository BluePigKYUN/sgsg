<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/cart.css" type="text/css">

<style type="text/css">
.body-container {
	max-width: 1100px;
	margin: 0 auto;
    padding-top: 30px;
}
.cart-item {
	border-top: 0;
}
.md-img {
    width: 80px;
    height: 80px;
}
.order_box{
    background: #fff;
    border-radius: 6px;
    border: 1px solid #ededed;
    margin-bottom: 12px;
}

.cart-box {
	border-top: 1px solid #dee2e6;
    margin-bottom: 12px;
    font-size: 14px;
}

.item-summary {
	color: rgb(107, 109, 110);
}
</style>

<script type="text/javascript">
function sendOk() {
	const f = document.paymentForm;
	
	/*
	if(! f.recipientName.value) {
		alert("먼저 배송지를 등록하세요..");
		return;
	}
	
	if(! /^\d+$/.test(f.usedSaved.value)) {
		alert("숫자만 입력 가능합니다.");
		return;
	}

	let balance = Number($('.btn-usedSaved').attr('data-balance')) || 0;
	let usedSaved = Number(f.usedSaved.value);

	if(usedSaved > balance) {
		alert("사용 가능 포인터는 보유 포인터를 초과 할수 없습니다.");
		return;
	}
	
	// 결제 금액 = 총금액 - 포인트사용금액
	let p = Number(f.payment.value) - usedSaved;
	f.payment.value = p;
	
	// 결제 API에서 응답 받을 파라미터
	let payMethod = "카드결제"; // 결제유형
	let cardName = "BC 카드";  // 카드 이름
	let authNumber = "1234567890"; // 승인번호
	let authDate = ""; // 승인 날짜
	// toISOString() : "YYYY-MM-DDTHH:mm:ss.sssZ" 형식
	authDate = new Date().toISOString().replace('T', ' ').slice(0, -5); // YYYY-MM-DD HH:mm:ss

	// 결제 API에 요청할 파라미터
	let payment = f.payment.value; // 결제할 금액
	let merchant_uid = "${productOrderNumber}";  // 고유 주문번호
	let productName = "${productOrderName}";  // 주문상품명
	let buyer_email = "${orderUser.email}";  // 구매자 이메일
	let buyer_name = "${orderUser.userName}";  // 구매자 이름
	let buyer_tel = "${orderUser.tel}";   // 구매자 전화번호(필수)
	let buyer_addr = "${orderUser.addr1}" + " " + "${orderUser.addr2}";  // 구매자 주소
	buyer_addr = buyer_addr.trim();
	let buyer_postcode = "${orderUser.zip}"; // 구매자 우편번호
	
	// 결제 API로 결제 진행
	
	
	
	// 결제가 성공한 경우 ------------------------
	
	// 결제 방식, 카드번호, 승인번호, 결제 날짜
	f.payMethod.value = payMethod;
	f.cardName.value = cardName;
	f.authNumber.value = authNumber;
	f.authDate.value = authDate;
	*/
	
	f.action = "${pageContext.request.contextPath}/order/paymentOk"
	f.submit();
}
</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-receipt-cutoff"></i> 주문/결제 </h3>
		</div>
		
		<div class="body-main">
			<form name="paymentForm" method="post">
				<div class="order-list p-3 border rounded order_box">
					<div class="fs-6 fw-semibold border-bottom pb-1">상품 정보</div>
					<c:forEach items="${productList}" var="dto">
						<div class="cart-box ps-2 pt-2">
							<div class="cart-item">
								<a class="item-link" href="${pageContext.request.contextPath}/product/details/${dto.productNum}">
									<span class="item-img">
										<img alt="상품 이미지" src="${pageContext.request.contextPath}/uploads/product/${dto.thumbnail}">
									</span>
									<span class="item-content">
										<h1 class="item-title">${dto.productName}</h1>
										<div class="option-price">
											<span class="number"><fmt:formatNumber value="${dto.salePrice}" pattern="#,###" />원</span>
											<c:if test="${dto.discountRate != 0}">
												<span class="text-decoration-line-through"><fmt:formatNumber value="${dto.price}" pattern="#,###" />원</span>
											</c:if>
										</div>
									</span>
								</a>
								<div class="item-summary">
									<div class="option">
										<c:if test="${dto.optionCount>=1}">${dto.optionName}: ${dto.optionValue}</c:if>
										<c:if test="${dto.optionCount>=2}">/ ${dto.optionName2}: ${dto.optionValue2}</c:if>
									</div>
									<div>수량: ${dto.qty}개 (<fmt:formatNumber value="${dto.price*(1-dto.discountRate/100)*dto.qty}" pattern="#,###" />원)</div>
								</div>
								<div>
									<span class=""><i class="bi bi-truck"></i> 
										<c:if test="${deliveryCharge==0}">
											무료
										</c:if>
										<c:if test="${deliveryCharge!=0}">
											${dto.delivery}원
										</c:if>
									</span>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<input type="hidden" name="orderNum" value="${orderNum}">
				<input type="hidden" name="totalMoney" value="${totalProduct - totalDiscountPrice}">
				<input type="hidden" name="deliveryCharge" value="${deliveryCharge}">
				<input type="hidden" name="payment" value="${totalPayment}">
				<input type="hidden" name="usedSaved" value="${totalSavedMoney}">

				<input type="hidden" name="mode" value="${mode}">

				<input type="hidden" name="payMethod" value="">
				<input type="hidden" name="cardName" value="">
				<input type="hidden" name="authNumber" value="">
				<input type="hidden" name="authDate" value="">

				<div class="p-3 border rounded order_box">
					<div class="fs-6 fw-semibold border-bottom pb-1">배송지 정보</div>
					<div class="row ps-2 pt-2">
						<div class="col-auto pe-2 mt-2">
							<label class="fw-semibold">${defaultDest.recipientName}</label>
							<label class="text-primary">${defaultDest.defaultDest==1? '기본배송지':''}</label>
						</div>
						<div class="col-auto">
							<button type="button" class="btn border changeDest"> 배송지변경 </button>
						</div>
					</div>
					<div class="ps-2 pt-2">
						<div class="pt-2">${defaultDest.addr1} ${defaultDest.addr2}</div>
						<div class="pt-2">${defaultDest.tel}</div>
						<div class="pt-2 w-50">
							<input type="hidden" name="recipientName" value="${defaultDest.recipientName}">
							<input type="hidden" name="tel" value="${defaultDest.tel}">
							<input type="hidden" name="zip" value="${defaultDest.zip}">
							<input type="hidden" name="addr1" value="${defaultDest.addr1}">
							<input type="hidden" name="addr2" value="${defaultDest.addr2}">
							<input type="text" name="destMeno" class="form-control" placeholder="요청사항을 입력합니다." value="${defaultDest.destMemo}">
						</div>
					</div>
				</div>
				
				<div class="p-3 border rounded order_box">
					<div class="fs-6 fw-semibold border-bottom pb-1">쿠폰 사용</div>
					<div class="row ps-2 pt-2 col-6">
						<select class="form-select" id="couponSelect">
							<option>사용 안함</option>
							<option value="">첫 결제 할인 쿠폰</option>
						</select>
					</div>
				</div>
				
				<div class="p-3 border mt-3 rounded order_box">
					<div class="fs-6 fw-semibold border-bottom pb-1">포인트</div>
					<div class="ps-2 pt-2">
						<span class="pt-2 fw-semibold">보유 <fmt:formatNumber value="${empty userPoint ? 0 : userPoint.balance}"/>원</span>
						<span class="pt-2">(${totalSavedMoney} 적립 예정)</span>
					</div>
					<div class="row ps-2 pt-2">
						<div class="col-6">
							<div class="input-group">
								<input type="number" class="form-control" name="usedSaved" value="0" min="0" max="${empty userPoint ? 0 : userPoint.balance}">
								<button type="button" class="input-group-text btn-usedSaved" data-balance="${empty userPoint ? 0 : userPoint.balance}">전액사용</button>
							</div>
						</div>
					</div>
				</div>				
				
				<div class="cart-summary">
					<dl class="productSummary">
						<dt>총 상품금액</dt>
						<dd>
							<span class="number"><fmt:formatNumber value="${totalProduct}" pattern="#,###" /></span>원
						</dd>
					</dl>
					<dl class="deliverySummary">
						<dt>총 배송비</dt>
						<dd>
							+ <span class="number"><fmt:formatNumber value="${deliveryCharge}" pattern="#,###" /></span>원
						</dd>
					</dl>
					<dl class="saleSummary">
						<dt>총 할인금액</dt>
						<dd>
							- <span class="number"><fmt:formatNumber value="${totalDiscountPrice}" pattern="#,###" /></span>원
						</dd>
					</dl>
					<dl class="">
						<dt>포인트사용액</dt>
						<dd>
							- <span class="number">0</span>원
						</dd>
					</dl>
					<dl class="totalSummary">
						<dt>결제금액</dt>
						<dd>
							<span class="number"><fmt:formatNumber value="${totalPayment}" pattern="#,###" /></span>원
						</dd>
					</dl>
				</div>
				
				<div class="pt-3 pb-3 text-center">
					<button type="button" class="btn btn-primary btn-lg" style="width: 250px;" onclick="sendOk()">결제하기</button>
					<button type="button" class="btn btn-light btn-lg" style="width: 250px;" onclick="location.href='${pageContext.request.contextPath}/';">결제취소</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
$('.changeDest').click(function() {
	
});
</script>