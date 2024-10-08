<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<nav class="sidebar">
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link active" href="#">상품</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/adminManagement/productManage/productList">상품 관리</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/adminManagement/productManage/stockList">재고 관리</a>
        </li>	
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/adminManagement/productManage/specialList">오늘의 특가</a>
        </li>
		<!-- 		
		<li class="nav-item">
            <a class="nav-link toggleSubMenu" href="#">예시3 <span class="jArrow"></span></a>
            <ul class="nav flex-column submenu">
                <li class="nav-item">
                    <a class="nav-link" href="#">하위 예시3-1</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">하위 예시3-2</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">하위 예시3-3</a>
                </li>
            </ul>
        </li> 
        -->
    </ul>
</nav>

<script>
$(function() {
    $('.toggleSubMenu').click(function(e) {
        e.preventDefault();
        var $submenu = $(this).next('.submenu');

        // 현재 열린 하위 메뉴를 모두 닫음
        $('.submenu').not($submenu).slideUp();

        // 클릭된 하위 메뉴를 토글
        $submenu.slideToggle();
    });
});
</script>