<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!-- 본문, div는 block element임 -->
<div class="container">
	<h2>Edit User Profile</h2>
	<span class="text-primary font-weight-bold">You can only change your password and email address</span> 
	<br><br>
	<form action="/user/join" method="POST">
		<input type="hidden" id="id" value = "${principal.user.id}" />
		<div class="form-group">
			<label for="username">Your Username :  </label> 
			<input type="text" value = "${principal.user.username }" class=" form-control" id="username" placeholder="Enter username" name="username" readonly>
		</div>
		
		<c:if test="${empty principal.user.oauth }"> <!-- oauth 값이 있으면 패스워드 수정 못하게 막음 -->
			<div class="form-group">
				<label for="password">New Password :</label> 
				<input type="password" class="form-control" id="password" placeholder="Enter password" name="password" >
			</div>
		</c:if>
		
		<div class="form-group">
			<label for="email">New Email Address :</label> 
			<c:choose>
				<c:when test="${empty principal.user.oauth }">
					<input type="text" value = "${principal.user.email }" class="form-control" id="email" placeholder="Enter email" name="email" >
				</c:when>
				<c:otherwise>
					<input type="text" value = "${principal.user.email }" class="form-control" id="email" placeholder="Enter email" name="email"  readonly>
				</c:otherwise>
			</c:choose>
		</div>
		
		<%-- <%@ include file="test.html"%> --%>
	</form>
	<button id="btn-update" class="btn btn-primary">Update Profile</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


