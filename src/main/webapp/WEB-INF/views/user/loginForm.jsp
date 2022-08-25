<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!-- 본문, div는 block element임 -->
<div class="container">
	<form action="/auth/loginProc" method="post" class="was-validated">
		<div class="form-group">
			<label for="username">Username :</label> 
			<input type="text" class="form-control" id="username" placeholder="Enter username" name="username" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group">
			<label for="password">Password :</label> 
			<input type="password" class="form-control" id="password" placeholder="Enter password" name="password" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group form-check">
			<label class="form-check-label"> 
			<input class="form-check-input" type="checkbox" name="remember"> Remember me
			</label>
		</div>
		<button id="btn-login"  class="btn btn-primary">Log in</button>
		<a  href="https://kauth.kakao.com/oauth/authorize?client_id=a1a9d5f6a73203a6a2b6af4dafcfec47&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code" > <img id = "kakao-login-button"  src = "/image/kakao_login_button.png" /></a>
		<%-- <%@ include file="test.html"%> --%>
	</form>
	
</div>

<script>
$('#kakao-login-button').on('load', function() {
	$('#kakao-login-button').outerWidth($('#btn-login').outerWidth() + "px");
	$('#kakao-login-button').outerHeight($('#btn-login').outerHeight() + "px");
});
</script>
<!--  <script src="/js/user.js"></script> -->
<%@ include file="../layout/footer.jsp"%>


