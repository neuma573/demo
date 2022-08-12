<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" type="text/css" />
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="/js/main.js"></script>
<meta charset="UTF-8">

<title>Insert title here</title>
</head>
<body>

<h2 style="text-align: center; margin-bottom: 50px; margin-top: 50px;">게시글 작성</h2>

<div class="container">
	<form action="/insertProc" id="form1"  method="post" enctype="multipart/form-data">
	<div class="form-group">
	  <label for="title">제목</label>
	  <input type="text" class="form-control" id="title" name="title" maxlength='200' placeholder="제목을 입력하세요.">
	</div>
	
	<div class="form-group">
	  <label for="writer">작성자</label>
	  <input type="text" class="form-control" id="writer" name="writer" maxlength="100" placeholder="작성자를 입력하세요.">
	</div>
	
	<div class="form-group">
	  <label for="content">내용</label>
	  <textarea class="form-control" id="content" name="content" rows="3" placeholder="내용을 입력하세요."></textarea>
	</div>
	
	<div class="form-check form-check-inline mt-3">
    <input class="form-check-input" type="checkbox" name="secret" id="secret">
    <label class="form-check-label">비밀글 설정</label>
</div>
	
	<input type="password" id="password" name="password" maxlength='20'  value="" placeholder="비밀번호">
	
	<div style="text-align: right;">
	<button type="button" class="btn btn-dark" id="form2" >작성</button>
	<button type="button" class="btn btn-dark" onclick="location.href='/list'">목록</button>
	</div>
	
	<input multiple="multiple" type="file" id="file" name="files" style="margin-bottom: 20px;"><br/>
	

	
	</form>
</div>

<%@ include file ="insertS.jsp" %>

</body>
</html>