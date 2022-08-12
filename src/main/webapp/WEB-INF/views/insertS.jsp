<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<script type="text/javascript">
// 게시글 등록 유효성 검사
$("#form2").on("click", function() {
	var title = $("#title").val();
	var writer = $("#writer").val();
	
	if(title=="") {
		alert("제목을 입력하세요.");
		$("#title").focus();		
	} else if(writer == ""){
		alert("작성자를 입력하세요.");
		$("#writer").focus();
		
	}else if (password ==""){
		alert("비밀번호를 입력하세요.");
		$("#password").focus();
	}else{
		$("#form1").submit();
		
	}	
	
});

</script>