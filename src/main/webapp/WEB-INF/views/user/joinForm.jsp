<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!-- 본문, div는 block element임 -->
<div class="container">
	<form action="/user/join" class="was-validated" method="POST">
		<div class="form-group">
			<label for="username">Username :</label> <input type="text" class="form-control" id="username" placeholder="Enter username" name="username" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group">
			<label for="password">Password :</label> <input type="password" class="form-control" id="password" placeholder="Enter password" name="password" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group">
			<label for="email">Email Address :</label> <input type="text" class="form-control" id="email" placeholder="Enter email" name="email" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		
		<%-- <%@ include file="test.html"%> --%>
	</form>
	<button id="btn-save" class="btn btn-primary">Sign Up</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


