<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	type="text/css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

function submitGoGo (){
	var url = $("#url"). text();
	var form = {
			 password: $("#password"). val(),
			 bno: ${bno},
			 fileDeleteYN: $("#fileDeleteYN"). val(),
			 fno: ${fno.fno}
	}
	 $.ajax({
        url: "/updateChk",
        type: "POST",
        data: form,
        success: function(data){
            if(data=='200'){
            	formSubmit();
        	}
            else if(data=='200del'){
            	fetch(url);
            	formSubmit();
            }
            else {
           	 alert("비밀번호가 일치하지 않습니다. 확인해보십시오");
           	 return 0;
            }
       	 
        },
        error: function(){
            alert("ajax err");
          
        }
    });
}

function formSubmit() {
	document.getElementById('form1').submit();
}

function fileDeleteYes() {
	$("#fileDeleteYN"). val('Y');
	  $('#hidden').remove();
}
</script>
</head>
<body>

	<h2 style="text-align: center; margin-bottom: 50px; margin-top: 50px;">게시글
		수정</h2>
	<div class="container">
		<form action="/updateProc" id="form1" name="form1" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="title">제목</label> <input type="text"
					class="form-control" id="title" name="title" maxlength='30'
					value="${view.title }">
			</div>


			<div class="form-group">
				<c:forEach var="file" items="${files }">
					<label for="content">첨부파일</label>
					<div id="hidden">
						<a href="/fileDown/${file.fno}">${file.fileOriName}</a>

						<div id="url" style="display: none;">
							/fdelete/${file.fno}?bno=${view.bno}
							
						</div>
						<div id="fno" style="display: none;">
							${file.fno}
						</div>
						
						<a href="#" onclick="fileDeleteYes()">X</a>
					</div>
				</c:forEach>


			</div>

			<div class="form-group">
				<label for="content">내용</label>
				<textarea class="form-control" id="content" name="content" rows="3">${view.content}</textarea>
			</div>

			<div class="form-group">
				<label for="content">비밀번호</label> <input type=password id="password"
					name="password" size=17 maxlength=15>
			</div>


			<input type="file" id="file" name="files"> <input
				type="hidden" id="fileDeleteYN" value="N" /> <input type="hidden"
				name="bno" value="${bno }" /> <a href="#" onclick="submitGoGo()"
				class="btn btn-dark">수정</a>
			<button type="button" class="btn btn-dark"
				onclick="location.href='/list?pageNum=${cri.pageNum }'">목록</button>
		</form>

	</div>
</body>

</html>