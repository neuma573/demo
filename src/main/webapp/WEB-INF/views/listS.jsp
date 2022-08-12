<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">

let moveForm = $("#moveForm");

$(".move").on("click", function(e){
	e.preventDefault();
	
	moveForm.append("<input type='hidden' name='bno' value='"+ $(this).attr("href")+ "'>");
	moveForm.attr("action", "/view");
	moveForm.submit();
});

$(".pageInfo a").on("click", function(e){
	
	e.preventDefault();
	moveForm.find("input[name='pageNum']").val($(this).attr("href"));
	moveForm.attr("action", "/list");
	moveForm.submit();
});

$(".search_area button").on("click", function(e){
    e.preventDefault();

    let type = $(".search_area select").val();
    let keyword = $(".search_area input[name = 'keyword']").val();
    
    if(!type){
    	alert("검색 종류를 선택하세요.");
    	return false;
    }
    
    if(!keyword){
    	alert("키워드를 입력하세요.");
    	return false;
    }
    
    moveForm.find("input[name='type']").val(type);
    moveForm.find("input[name='keyword']").val(keyword);
    moveForm.find("input[name='pageNum']").val(1);
    moveForm.submit();
});


</script>
<body>
</body>
</html>