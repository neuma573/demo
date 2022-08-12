<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h2 style="text-align: center; margin-bottom: 50px; margin-top: 50px;">게시글 상세</h2>
<div class="container">
	<form action="/insertProc" method="post">
	<a style="font-weight: bold;">게시글번호: ${view.bno }번  </a> <br/>
	<a style="font-weight: bold;">조회수: ${view.hit_cnt }번  </a>
	<table class="table table-bordered">
		<colgroup>
			<col style="width: 20%;">
			<col style="width: 80%">
		</colgroup>
		<tr>
	 	<th>제목</th>
	 	<td>${view.title }</td>
	 	</tr>
	 	
	 	<tr>
	 	<th>작성자</th>
	 	<td>${view.writer }</td>
	 	</tr>
	 	
	 	<tr>
	 	<th>작성날짜</th>
	 	<td>
	 		<fmt:formatDate value="${view.reg_date }" pattern="yyyy.MM.dd HH:mm:ss"/>
	 	</td>
	 </tr>
	 
	 	<tr>
	 	<th>수정날짜</th>
	 	<td>
	 		<fmt:formatDate value="${view.update_time }" pattern="yyyy.MM.dd HH:mm:ss"/>
	 	</td>
	 </tr>
	 
	 <tr>
	 	<th>첨부파일</th>
	 	<td><c:forEach var="file" items="${files }">
	 	<p>
	 	<a href="/fileDown/${file.fno}">${file.fileOriName}</a>
	 	</p>
	 	</c:forEach></td>
	 </tr>
	 
	 <tr>
	 	<th>내용</th>
	 	<td>${view.content }</td>
	 	</tr>
	 </table>
	 	
	</form>
	<div class="form-group" style="text-align: right;">
	 <div class="col-sm-12">	
	 <button type="button" class="btn btn-dark" onclick="location.href='/update/${view.bno}?pageNum=${cri.pageNum }'" >수정</button>
	 <button type="button"  class="btn btn-dark" onclick="location.href='/deletes/${view.bno}?pageNum=${cri.pageNum }'" >삭제</button>
	 <button type="button" class="btn btn-dark" id="list_btn">목록</button>
</div>
</div>

<form id="infoForm" action="/update" method="get">
	<input type="hidden" id="bno" name="bno" value="${view.bno }"/>
	<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }"/>'>
	<input type="hidden" name="amount" value='<c:out value="${cri.amount }"/>'>
	<input type="hidden" name="keyword" value='<c:out value="${keyword }"/>'>
	<input type="hidden" name="type" value='<c:out value="${type }"/>'>
</form>

<script>
	let form = $("#infoForm");
	
	$("#list_btn").on("click", function(e){
		form.find("#bno").remove();
		form.attr("action", "/list");
		form.submit();
	});
</script>


	<div class="container" style="margin-bottom: 30px;">
		<label for="content">comment</label>
		<form name="commentInsertForm">
		<div class="input-group">
			<input type="hidden" name="bno" value="${view.bno }"/>
			<input type="text" class="form-control" id="content" name="content" placeholder="내용을 입력하세요.">
			<span class="input-group-btn">
				<button class="btn btn-dark" type="button" name="commentInsertBtn">등록</button>
			</span>
		</div>
		</form>
	</div>
	
	<div class="container">
		<div class="commentList"></div>
	</div>
</div>

<%@ include file ="commentS.jsp" %>
</body>
</html>