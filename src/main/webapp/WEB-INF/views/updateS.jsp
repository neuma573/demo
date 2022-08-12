<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<script type="text/javascript">
// 수정 유효성 검사
$("#form2").on("click", function() {
	var title = $("#title").val();
	var writer = $("#content").val();
	
	if(title=="") {
		alert("제목을 입력하세요.");
		$("#title").focus();		
	} else if(writer == ""){
		alert("내용을 입력하세요.");
		$("#content").focus();
		
	}else{
		$("#form1").submit();
		
	}	
	
});

</script>