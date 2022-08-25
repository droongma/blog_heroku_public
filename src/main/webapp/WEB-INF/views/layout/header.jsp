<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Blog Website</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
	<!--  <h1>${principal}</h1> -->
	<%-- 
	bootstratp의 navbar 기능 사용
	navbar-expand-md를 통해  medium screen 이상에서는 링크를 펼치고, 작은 화면에서는 내비게이션 링크를 숨긴다. 
	--%>
	<nav class="navbar navbar-expand-md navbar-light" style="background-color: #add8e6;">
		<!-- Brand -->
		<a class="navbar-brand" href="/">Blog Website Home</a>
		<!-- localhost:8000/blog 으로 연결 -->

		<!-- Toggler/collapsibe Button(data-target이 id값을  통해 아래 navbar links를 가리켜야함) -->
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>

		<!-- Navbar links -->
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<c:choose>
				<c:when test="${empty principal }">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="/auth/loginForm">LOGIN</a></li>
						<li class="nav-item"><a class="nav-link" href="/auth/joinForm">Sign up</a></li>
					</ul>
				</c:when>

				<c:otherwise>
					<!-- 세션이 있을 경우 -->
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="/board/saveForm">Write</a></li>
						<li class="nav-item"><a class="nav-link" href="/user/updateForm">Account Information</a></li>
						<li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>

					</ul>
				</c:otherwise>
			</c:choose>

		</div>
	</nav>
	<br>