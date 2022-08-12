<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 삭제하기</title>
<style>
@import url(http://fonts.googleapis.com/earlyaccess/nanumgothic.css);

body {
	font-family: 'Nanum Gothic', sans-serif;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
function submitGoGo (){
	 var form = {
			 password: $("#password"). val(),
			 bno: ${bno}
	 }
	 $.ajax({
        url: "/chkpwds/${view.bno}",
        type: "POST",
        data: form,
        success: function(data){
            if(data=='200'){
           	 location.href='/view?bno=${bno}&pageNum=${pageMaker.cri.pageNum}';
            }
            else {
           	 $('#result').text("비밀번호가 일치하지 않습니다. 확인해보십시오");
            }
       	 
        },
        error: function(){
            alert("ajax err");
        }
    });
}
</script>
</head>

<body>
	<center>
	<br><br>
	<table width=50% cellspacing=0 cellpadding=3>
 		<tr>
			<td bgcolor=#dcdcdc height=21 align=center>
			${state} 작성자의 비밀번호를 입력해 주세요.</td>
			<div id="result"></div>
		</tr>
	</table>
	
	<table width=70% cellspacing=0 cellpadding=2>
		
		
<tr>
		<td align=center>
		<table align=center border=0 width=91%>
    <tr> 
     	<td align=center>  
	  	<input type=password id="password" name="password" size=17 maxlength=15>
	 	</td> 
    </tr>
    <tr>
		<td><hr size=1 color=#eeeeee></td>
	</tr>
    <tr>
		<td align=center>
		<input type="hidden" value="${pageMaker.cri.pageNum}">
		<button id="btn1" class="btn btn-dark" onClick="submitGoGo()">입장</button>
		<input type=button value="뒤로" onClick="history.go(-1)">
		</td>
	</tr> 
	</table>
	</td>
	</tr>
	</form> 
	</table>
	</center>
</body>