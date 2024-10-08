<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><tiles:insertAttribute name="title"/></title>
	<link rel="icon" href="data:;base64,iVBORw0KGgo=">
	
	<!-- CSS: bootstrap -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/icon/bootstrap-icons.css" type="text/css">
	<!-- CSS: custom -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" type="text/css">
	<!-- CSS: swiper -->
	 <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
	 <!-- CSS: 페이지네이션  -->
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/paginate.css" type="text/css">
	
	<script>
	var contextPath = '${pageContext.request.contextPath}';
	window.isLoggedIn = ${not empty sessionScope.member};
	</script>
	
	<!-- JS: jquery -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/jquery/js/jquery.min.js"></script>
	 <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/util-jquery.js"></script>
	<!-- JS: bootstrap -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- JS: custom -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/menu.js"></script> 
	<!-- JS: 북마크 -->
	<script type="text/javascript"  charset="utf-8" src="${pageContext.request.contextPath}/resources/js/bookmark.js"></script> 
	<!-- JS: swiper -->
	 <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
	 
	<link type="image/png" sizes="16x16" rel="icon" href="${pageContext.request.contextPath}/uploads/etc/icons8-cart-16.png">
	<link type="image/png" sizes="32x32" rel="icon" href="${pageContext.request.contextPath}/uploads/etc/icons8-cart-32.png">
	 
</head>

<body>

<header>
    <tiles:insertAttribute name="header"/>

    
</header>
	
<main>
    <tiles:insertAttribute name="body"/>
    
</main>

<footer>
    <tiles:insertAttribute name="footer"/>
    
</footer>


</body>
</html>