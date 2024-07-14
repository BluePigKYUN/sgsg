<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<style type="text/css">
.container {
	display: flex;
    justify-content: center;
    align-items: center;
}


.expert-style {
    border-radius: 20px;
    padding: 20px;
    margin: 30px 0;
    background-color: #fff;
    height: 400px; 
    width: 1100px; 
}


.expert-semi-style {
	border: 5px solid #35c5f0;  
    border-radius: 20px;
    padding: 20px;
    margin: 30px 0; 
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);  
    background-color: #fff;
    height: 350px; 
    width: 300px; 
}



.expert-style2 {
	border: 1px solid #ddd; 
    border-radius: 20px;
    padding: 20px;
    margin: 30px 0; 
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);  
    background-color: #fff;
    height: 500px; 
    width: 1100px; 
}
</style>

<div class="container">
	<div class="body-container">
	
		<div class="body-title">
			<h2 style="color: #35c5f0; font-weight: bold;"> 전문가의 프로필 </h2>
		</div>
		
		<div class="expert-style" style="display: flex; align-items: flex-start;">
			<div class="expert-semi-style" style="text-align: center;">
				<img src="${pageContext.request.contextPath}/uploads/etc/expertprofile.png"
				style="width: 200px; height: 200px; border-radius: 100px;">
				<br>
				<div>
					<span style="font-size: 32px;">@sample</span> 
				</div>
			
			</div>
			<div class="expert-text" style="margin-left: 30px; margin-top: 30px;">
				<span style="font-size: 32px; display: block;">취득 자격증</span>
				<div class="certificates">
					<span style="font-size: 18px; display: block; border: 2px solid #B7F0B1; border-radius: 20px; background: #B7F0B1; width: 300px; padding-top: 5px; margin-top: 10px;">&nbsp;-- 자격증 1</span>
					<span style="font-size: 18px; display: block; border: 2px solid #B7F0B1; border-radius: 20px; background: #B7F0B1; width: 300px; padding-top: 5px; margin-top: 10px;">&nbsp;-- 자격증 2</span>
					<span style="font-size: 18px; display: block; border: 2px solid #B7F0B1; border-radius: 20px; background: #B7F0B1; width: 300px; padding-top: 5px; margin-top: 10px;">&nbsp;-- 자격증 3</span>
					<!-- 여기서 추가 텍스트를 작성할 수 있습니다 -->
				</div>
				
				<div class="additional-text" style="margin-top: 40px;">
		            <span style="font-size: 30px; display: block;">답변 갯수</span>
		        </div>
		        <div>
		        	<span style="font-size: 40px; display: block; color: #008000;">3개</span>
		        </div>
				
			</div>
			
			<div class="expert-text" style="margin-left: 30px; margin-top: 30px; padding-left: 30px;">
				<span style="font-size: 32px; display: block;">경력 사항</span>
				<div class="additional-info">
					<span style="font-size: 18px; display: block; border: 2px solid #ADD8E6; border-radius: 20px; background: #ADD8E6; width: 300px; padding-top: 5px; margin-top: 10px;">&nbsp;-- 경력 1</span>
					<span style="font-size: 18px; display: block; border: 2px solid #ADD8E6; border-radius: 20px; background: #ADD8E6; width: 300px; padding-top: 5px; margin-top: 10px;">&nbsp;-- 경력 2</span>
					<span style="font-size: 18px; display: block; border: 2px solid #ADD8E6; border-radius: 20px; background: #ADD8E6; width: 300px; padding-top: 5px; margin-top: 10px;">&nbsp;-- 경력 3</span>
					<!-- 여기서 추가 텍스트를 작성할 수 있습니다 -->
				</div>
				
				<div class="additional-text" style="margin-top: 40px;">
		            <span style="font-size: 30px; display: block;">답변 채택률</span>
		        </div>
		        <div>
		        	<span style="font-size: 40px; display: block; color: #B70000;">66.7%</span>
		        </div>
			</div>
			
		</div>
		
		<div class="expert-style2">
			<span style="font-size: 22px;">전문가 샘플 자기소개입니다.</span>
		</div>
		
	</div>
</div>

<br><br><hr>





  