<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!-- 본문, div는 block element임 -->
<div class="container">
	<button class="btn btn-secondary" onclick="history.back()">Back</button> <!-- 돌아가기 버튼 -->
	<c:if test="${board.user.id == principal.user.id}"> <!-- 현재 로그인한 id와 글쓴이 id가 같을 때만 삭제되게  -->
		 <button class="btn btn-warning" 
			onclick="window.location.href='/board/${board.id}/updateForm' "   type="button">Edit</button>
		<!--  <a href="/board/${board.id }/updateForm" class="btn btn-warning">Edit</a> -->
		<button id="btn-delete" class="btn btn-danger">Delete</button>
	</c:if>
	<br />
	<br />
	<div>
		Post ID : <span id="id"><i>${board.id}</i></span> Author : <span><i>${board.user.username}</i></span>
	</div>
	<br />
	<div class="form-group">
		<label for="title" class="font-weight-bold">Title</label>
		<h3>${board.title}</h3>
	</div>
	<hr />
	<div class="form-group">
		<label for="content" class="font-weight-bold">Content</label>
		<div>${board.content }</div>
	</div>
	<hr />
	
	<div class="card">
		<form>
			<input type="hidden" id="userId" value="${principal.user.id }"/>
			<input type="hidden" id="boardId" value="${board.id }"/>
			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save" class="btn btn-primary">Leave Comment</button>
			</div>
		</form>
	</div>
	<br/>
	<div class="card">
		<div class="card-header">댓글 리스트</div>
		<ul id="reply-box" class="list-group"> 
			<c:forEach var="reply" items="${board.replies }">
				<li id="reply-${reply.id }" class="list-group-item d-flex justify-content-between">
					<div>${reply.content }</div>
					<div class="d-flex" >
						<div class="font-italic">작성자 : ${reply.user.username} &nbsp;</div>
						<c:if test="${reply.user.id == principal.user.id}"> 
							<button onclick="index.replyDelete(${board.id}, ${reply.id })" class="badge">Delete</button>
						</c:if>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div> 

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>


