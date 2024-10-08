<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">

.container {
	display: flex;
    justify-content: center;
    align-items: center; 
}

.body-container {
	width: 1000px;
}

.nav-tabs .nav-link {
	min-width: 170px;
	background: #f3f5f7;
	border-radius: 0;
	border-right: 1px solid #dbdddf;
	color: #333;
	font-weight: 600;
}
.nav-tabs .nav-link.active {
	background: #3d3d4f;
	color: #fff;
}
.tab-pane { min-height: 300px; }

.table img {
	max-width: 650px;
}


.event-style1 {
	border: 5px solid #35c5f0; 
    border-radius: 30px;
    padding: 20px;
    margin: 20px 0; 
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3); 
    background-color: #fff;
    width: 1000px; 
    height: 80px;
}


.event-style2 {
	border: 1px solid #ddd; 
    border-radius: 20px;
    padding: 20px;
    margin: 20px 0; 
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3); 
    background-color: #fff;
    width: 1000px;
}


.event-style3 {
	border: 1px solid #ddd; 
    border-radius: 20px;
    padding: 20px;
    margin: 20px 0; 
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3); 
    background-color: #fff;
    width: 1000px;
}


.event-style4 {
	border: 1px solid #ddd; 
    border-radius: 20px;
    padding: 20px;
    margin: 20px 0; 
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3); 
    background-color: #fff;
    width: 1000px;
}

.btn1 {
	width: 80px;
	height: 40px;
	background-color: white;
	border-radius: 10px;
	border: 2px solid #35c5f0;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3); 
}

.btn1:hover {
	animation: btncolor 0.3s forwards;
	background: #35c5f0;
	color: white;
	transform: translateY(2px);
	transition: 0.3s;
}


@keyframes color {
	0% {border: 2px solid #FFA2A2;}
	25% {border: 2px solid #FFFF36;}
	50% {border: 2px solid #65FF5E;}
	75% {border: 2px solid #368AFF;}
	100% {border: 2px solid #CB6CFF;}
}


.btn-12 {
  position: relative;
  right: 20px;
  bottom: 20px;
  border:none; 
  width: 170px;
  line-height: 40px;
  -webkit-perspective: 230px;
  perspective: 230px;
}
.btn-12 span {
  display: block;
  position: absolute;
  width: 170px;
  height: 40px;
  border: 2px solid #35c5f0;  
  margin: 0;
  border-radius: 5px;
  text-align: center;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: all .3s;
  transition: all .3s;
}
.btn-12 span:nth-child(1) {
  box-shadow:
   -7px -7px 20px 0px #fff9,
   -4px -4px 5px 0px #fff9,
   7px 7px 20px 0px #0002,
   4px 4px 5px 0px #0001;
  -webkit-transform: rotateX(90deg);
  -moz-transform: rotateX(90deg);
  transform: rotateX(90deg);
  -webkit-transform-origin: 50% 50% -20px;
  -moz-transform-origin: 50% 50% -20px;
  transform-origin: 50% 50% -20px;
}
.btn-12 span:nth-child(2) {
  -webkit-transform: rotateX(0deg);
  -moz-transform: rotateX(0deg);
  transform: rotateX(0deg);
  -webkit-transform-origin: 50% 50% -20px;
  -moz-transform-origin: 50% 50% -20px;
  transform-origin: 50% 50% -20px;
}
.btn-12:hover span:nth-child(1) {
  -webkit-transform: rotateX(0deg);
  -moz-transform: rotateX(0deg);
  transform: rotateX(0deg);
  animation: color 2s infinite; 
}
.btn-12:hover span:nth-child(2) {
  background: #e0e5ec;
  color: #e0e5ec;
  -webkit-transform: rotateX(-90deg);
  -moz-transform: rotateX(-90deg);
  transform: rotateX(-90deg);
  animation: color 2s infinite;
}


.center-text {
    text-align: center;
    position: relative;
    display: inline-block; /* 부모 요소의 크기를 기준으로 폭죽을 생성하기 위해 추가 */
}
.firework {
    position: absolute;
    width: 10px;
    height: 10px;
    background-color: red;
    border-radius: 50%;
    opacity: 0;
    animation: firework-animation 1s ease-out forwards;
}

@keyframes firework-animation {
    0% {
        transform: scale(1);
        opacity: 1;
        background: red;
    }
    50% {background: #FAED7D; opacity: 0.5;}
    100% {
        transform: scale(10);
        opacity: 0;
        background: purple;
    }
}



</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">

<script type="text/javascript">
$(function(){
	let menu = "${category}";
	$("#tab-"+menu).addClass("active");
	
    $("button[role='tab']").on("click", function(e){
		const tab = $(this).attr("data-tab");
		let url = "${pageContext.request.contextPath}/promotion/"+tab+"/list";
		location.href = url;
    });
});

$(function(){
	$(".btnApplyEvent").click(function(){
		if(! confirm("이벤트에 응모하시겠습니까 ? ")) {
			return false;
		}
		
		const $btn = $(this); 
		let num= "${dto.event_num}";
		let url = "${pageContext.request.contextPath}/promotion/progress/apply";
		
		$.post(url, {event_num:num}, function(data){
			let state = data.state;
			
			if(state === "true") {
				$btn.prop("disabled", true);
				$btn.text("이벤트 응모 완료");
			} else if(state === "timeout"){
				alert("이벤트 응모기간이 지났습니다.");
			} else {
				alert("이벤트 응모는 한번만 가능합니다.");
			}
			
		}, "json");
	});
});


document.addEventListener("DOMContentLoaded", function() {
    const textElement = document.querySelector('.center-text');
    const positions = [
        {top: '50%', left: '90%'}, // 오른쪽
        {top: '50%', left: '10%'}, // 왼쪽
        {top: '50%', left: '50%'}, // 가운데
    ];

    function createFirework(position) {
        const firework = document.createElement('div');
        firework.classList.add('firework');
        firework.style.top = position.top;
        firework.style.left = position.left;
        textElement.appendChild(firework);

        setTimeout(() => {
            firework.remove();
        }, 1000);
    }

    positions.forEach((position, index) => {
        setTimeout(() => {
            createFirework(position);
        }, index * 1000);
    });
});




</script>

<div class="container">
	<div class="body-container">	
		<br> 
		<div class="body-title">
			<h2 style="color: #35c5f0; font-weight: bold;">이벤트 목록</h2>  
		</div>
		<br>
		
		
		
		<div class="event-style1" style="text-align: center;">
			<span style="font-size: 20px;">${dto.title}</span>
		</div>
		
		<div class="event-style2">
			<div width="50%">
				이벤트 기간 : ${dto.startDate} ~ ${dto.endDate}
			</div>
			<c:if test="${dto.winnerNumber != 0}">
			<hr>
				<div>
					<span>
						당첨일자 :
						<c:choose>
							<c:when test="${listEventWinner.size() == 0 && (category == 'winner' || category == 'ended') }">
								당첨이 곧 진행 예정입니다.
							</c:when>
							<c:otherwise>
								${dto.winningDate}
							</c:otherwise>
						</c:choose>									
					</span>
					<span style="float: right;">
						<c:choose> 
							<c:when test="${listEventWinner.size() != 0 && (category == 'winner' || category == 'ended')}">
								당첨 인원 : ${listEventWinner.size()}명
							</c:when>
							<c:when test="${category == 'progress'}">
								당첨 인원 : ${dto.winnerNumber}
							</c:when>
						</c:choose>
					</span>
				</div>
			</c:if>
		</div>
		
		<c:if test="${listEventWinner.size() != 0 && category == 'winner' && not empty userWinner}">
			<div>
				<div class="center-text" style="font-size: 20px; margin-left: 270px;">
					<p class="form-control-plaintext">
						<span>축하합니다.</span>
						<span style="color: blue; font-weight: 600;">${sessionScope.member.userName}</span>님은
						<c:if test="${userWinner.rank != 0}">
							<span>이벤트에 <label style="color: tomato; font-weight: 500;">${userWinner.rank}</label>등으로 당첨되었습니다 </span>
						</c:if>
						<c:if test="${userWinner.rank == 0}">
							<span>이벤트에 당첨되었습니다.</span>
						</c:if>
					</p>
				</div>
			</div>
		</c:if>
		
		<div class="event-style3">
			<span colspan="2" valign="top" height="200" style="${dto.winnerNumber != 0 ? 'border-bottom: none;' : ''}">
				${dto.content}
			</span>
		</div>
		
		<c:if test="${dto.winnerNumber != 0 && category == 'progress'}">
			<div colspan="2" class="text-center p-3">
				<button type="button" class="btn btn-12 btnApplyEvent" ${userEventTakers? "disabled='disabled'" : "" }> <span>Go!</span><span>${userEventTakers ? "이벤트 응모 완료" : "이벤트 응모" }</span> </button>
			</div>
		</c:if>
								
		<c:if test="${dto.winnerNumber != 0 && (category == 'winner' || category == 'ended')}">
			<div colspan="2" class="text-center p-3">
				<button type="button" class="btn btn-12 btnEventWinnerList"> <span>Go!</span><span>이벤트 당첨자 확인</span> </button>
			</div>
		</c:if>
		
		<div class="event-style4">
			<div colspan="2">
				이전글 :
				<c:if test="${not empty prevDto}">
					<a href="${pageContext.request.contextPath}/promotion/${category}/article?${query}&num=${prevDto.event_num}">${prevDto.title}</a>
				</c:if>
			</div>
			<hr>
			<div colspan="2">
				다음글 :
				<c:if test="${not empty nextDto}">
					<a href="${pageContext.request.contextPath}/promotion/${category}/article?${query}&num=${nextDto.event_num}">${nextDto.title}</a>
				</c:if>
			</div>
		</div>
		
		<table class="table table-borderless">
			<tr>
				<td width="50%">&nbsp;</td>
				<td class="text-end">
					<button type="button" class="btn btn1" onclick="location.href='${pageContext.request.contextPath}/promotion/${category}/list?${query}';">리스트</button>
				</td>
			</tr>
		</table>
		
		
	</div>
</div>

<c:if test="${listEventWinner.size() != 0}">
	<script type="text/javascript">
		$(function(){
			$(".btnEventWinnerList").click(function(){
				$("#eventWinnerModal").modal("show");	
			});
		});
	</script>
	
	<div class="modal fade" id="eventWinnerModal" tabindex="-1" aria-labelledby="eventWinnerModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg">
			<div class="modal-content" style="border: 4px solid #35c5f0;">
				<div class="modal-header">
					<h5 class="modal-title" id="eventWinnerModalLabel">이벤트 당첨자 리스트</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body pt-1 text-center">
					
	                 <div class="row row-cols-4 g-1">
	                 	<c:forEach var="vo" items="${listEventWinner}" varStatus="status">
	                 		<div class="col">
	                 		<br>
	                 			<div style="border: 2px solid #D5D5D5; border-radius: 15px;">
		                 			<c:if test="${vo.rank != 0}">
										<span>
											${vo.rank}등 :
										</span>
									</c:if>
									<span style="font-weight: 500; font-size: 18px;">
										${vo.userName}(${vo.userId})
									</span>
								</div>
	                 		</div>
	                 	</c:forEach>
	                 </div>
	                 
				</div>
			</div>
		</div>
	</div>
</c:if>