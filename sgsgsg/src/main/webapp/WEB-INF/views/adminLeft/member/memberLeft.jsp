<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<nav class="sidebar">
    <ul class="tabs nav flex-column">
    	<li class="nav-item" >
            <a class="nav-link active">회원 관리</a>
        </li>
    	<li class="nav-item" >
            <a class="nav-link" href="${pageContext.request.contextPath}/adminManagement/chat">채팅 문의</a>
        </li>
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