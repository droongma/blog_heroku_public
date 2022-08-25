<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<!-- 본문, div는 block element임 -->
<div class="container">
	<c:forEach var="board" items="${boards.content}">
		<!-- boards에 담긴 각 board에 대해 반복 -->
		<div class="card m-4" style="width: 400px">
			<!--  <img class="card-img-top" src="img_avatar1.png" alt="Card image"> -->
			<div class="card-body">
				<h4 class="card-title">${board.title }</h4>
				<!-- <p class="card-text">Content</p> -->
				<a href="/board/${board.id}" class="btn btn-primary">Click Here for Details</a>
			</div>
		</div>
	</c:forEach>

	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${boards.first}"> <!-- 첫 페이지에서는 previous 버튼 비활성화! -->
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${boards.last}"> <!-- 첫 페이지에서는 previous 버튼 비활성화! -->
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
			</c:otherwise>
		</c:choose>
		
		
	</ul>
</div>

<%@ include file="layout/footer.jsp"%>


