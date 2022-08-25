<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!-- 본문, div는 block element임 -->
<div class="container">
	<h3>Edit post</h3>
	<form>
		<div class="form-group">
			<label for="title">Title</label> 
			<input type="text" class="form-control" id="title" placeholder="Enter title" required>
		</div>
		<div class="form-group">
			<label for="content">Content:</label>
			<textarea class="form-control summernote" rows="5" id="content"></textarea> <%-- summernote 형식의 textarea 생성--%>
		</div>
		<%-- <%@ include file="test.html"%> --%>
	</form>
	<button id="btn-save" class="btn btn-primary">Save post</button>
</div>

<script> 
	$('.summernote').summernote({ 
		placeholder : 'Write your post',
		tabsize : 2,
		height : 300
	});
</script>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>


