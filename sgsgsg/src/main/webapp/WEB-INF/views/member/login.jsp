<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">

.container-login{
    max-width: 450px;
    width: 90%;
    margin: 60px auto;
    padding: 30px;
    background: white;
    box-shadow: 0 0 10px rgba(0,0,0,0.1);
    border-radius: 8px;
}

h1 {
    font-size: 40px;
    text-align: center;
    margin-bottom: 30px;
}

.links {
    list-style-type: none;
    text-align: center;
    padding: 0;
    margin-bottom: 30px;
}

.links li {
    display: inline-block;
    margin: 0 15px;
}

.links li a {
    text-decoration: none;
    color: #666;
    font-size: 16px;
}

.input__block {
    margin-bottom: 20px;
}

.input__block input {
    width: 100%;
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 16px;
}

.login__btn {
    background: #35c5f0;
    color: white;
    width: 100%;
    padding: 14px;
    border: none;
    border-radius: 4px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
    margin-top: 15px;
}

.divider {
    text-align: center;
    margin: 25px 0;
    color: #888;
    position: relative;
}

.divider::before,
.divider::after {
    content: "";
    position: absolute;
    top: 50%;
    width: 45%;
    height: 1px;
    background-color: #ddd;
}

.divider::before {
    left: 0;
}

.divider::after {
    right: 0;
}

.social-login__btn {
    width: 100%;
    padding: 12px;
    margin-top: 15px;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
}

.kakao__btn {
    background: #FEE500;
    color: #000000;
}

.google__btn {
    background: #4285F4;
    color: white;
}

.naver__btn {
    background: #03C75A;
    color: white;
}

.icon {
    margin-right: 10px;
    font-size: 20px;
    font-weight: bold;
}
</style>

<script type="text/javascript">
function sendLogin() {
    const f = document.loginForm;
    let str;

    str = f.userId.value.trim();
    if (!str) {
        alert("아이디를 입력하세요.");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value.trim();
    if (!str) {
        alert("비밀번호를 입력하세요.");
        f.userPwd.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/member/login";
    f.submit();
}

function sendSignup() {
    window.location.href = "${pageContext.request.contextPath}/member/member";
}

function resetForm() {
    document.loginForm.userId.value = "";
    document.loginForm.userPwd.value = "";
}

$(document).ready(function() {
    $("#signup").on("click", function(e) {
        e.preventDefault();
        sendSignup();
    });

    $("#reset").on("click", function(e) {
        e.preventDefault();
        resetForm();
    });
});

</script>

<div class="container-login">
    <h1>로그인</h1>
    <ul class="links">
        <li><a href="${pageContext.request.contextPath}/member/member" id="signup">회원가입</a></li>
        <li><a href="#" id="reset">초기화</a></li>
    </ul>

    <form name="loginForm" id="loginForm" action="" method="post">
        <div class="input__block">
            <input type="text" name="userId" placeholder="아이디" id="userId" value="">
        </div>
        <div class="input__block">
            <input type="password" name="userPwd" placeholder="비밀번호" id="userPwd" value="">
        </div>
        <p class="form-control-plaintext text-center">${message}</p>
        <button class="login__btn" type="button" onclick="sendLogin();">로그인</button>
    </form>

</div>
