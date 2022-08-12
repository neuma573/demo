<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" type="text/css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	
<style type="text/css">
 .pageInfo{
      list-style : none;
      display: inline-block;
    margin: 50px 0 0 100px;      
  }
  .pageInfo li{
      float: left;
    font-size: 20px;
    margin-left: 30px;
    padding: 7px;
    font-weight: 500;
  }
 a:link {color:black; text-decoration: none;}
 a:visited {color:black; text-decoration: none;}
 a:hover {color:black; text-decoration: underline;}
 
 .active{
      background-color: #cdd5ec;
  }
 
 
</style>	
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2 style="text-align: center; margin-bottom: 50px; margin-top: 50px;"><a href="/list">게시글 목록</a></h2>

<div class="container">

	     <div class="search_wrap" style="text-align: right;">
        <div class="search_area">
        <select name="type">
        <option value="" <c:out value="${pageMaker.cri.type == null?'selected':'' }"/>>검색 종류 선택</option>
                <option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':'' }"/>>제목</option>
                <option value="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':'' }"/>>내용</option>
                <option value="W" <c:out value="${pageMaker.cri.type eq 'W'?'selected':'' }"/>>작성자</option>
                <option value="TC" <c:out value="${pageMaker.cri.type eq 'TC'?'selected':'' }"/>>제목 + 내용</option>
                <option value="TW" <c:out value="${pageMaker.cri.type eq 'TW'?'selected':'' }"/>>제목 + 작성자</option>
                <option value="TCW" <c:out value="${pageMaker.cri.type eq 'TCW'?'selected':'' }"/>>제목 + 내용 + 작성자</option>
        </select>
            <input type="text" name="keyword" value="${pageMaker.cri.keyword }">
            <button type="button" class="btn btn-dark">검색</button>
        </div>
    </div>  
    
	<table style="table-layout: fixed;" class="table table-hover" id="boardtable">
	<tbody>
	<tr style="text-align: center;">
		<th >번호</th>
		<th>제목</th>
		<th >작성자</th>
		<th>등록일</th>
		<th>수정일</th>
		<th>조회</th>
		<th>첨부파일</th>
	</tr>
		<c:forEach items="${list }" var="l"  >
			 <tr id="tab" onclick="location.href='/view?bno=${l.bno}&pageNum=${pageMaker.cri.pageNum }'">
				<td style="text-align: center; ">${l.bno }</td>
				<td width="100" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; text-align: center;">
				<c:choose>
				<c:when test="${l.is_private eq 'Y'}">
				[비밀 글입니다.]
				</c:when>
				<c:otherwise>
								${l.title }
				<c:if test="${l.comCount != 0}">
							<small><b>(&nbsp;<c:out value="${l.comCount}"/>&nbsp;)</b></small>
						</c:if>
				</c:otherwise>
				
				

						
				</c:choose>		
				</td>
				<td width="200" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; text-align: center;">${l.writer}</td>
				<td style="text-align: center;">
				<fmt:formatDate value="${l.reg_date }" pattern="yyyy.MM.dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
				<fmt:formatDate value="${l.update_time }" pattern="yyyy.MM.dd HH:mm:ss"/>
				</td>
				<td></td>
				<td style="text-align: center; ">
				<c:if test="${l.has_file eq 'Y'}"> 
				${l.has_file}
<!-- 				<img src="#"> -->
				</c:if>
				</td>
			 </tr> 
		</c:forEach>
		</tbody>
	</table>
	
	  
	
	
		<div class="pageInfo_wrap" >
		<div class="pageInfo_area">
			<ul id="pageInfo" class="pageInfo">
			
					<!-- 맨 처음 버튼 -->
					<li class="pageInfo_btn previous"><a href=1><<</a></li>
					
				<!-- 이전페이지 버튼 -->
				<c:if test="${pageMaker.prev}">
					<li class="pageInfo_btn previous"><a href="${pageMaker.startPage-1}"><</a></li>
				</c:if>
				
				
				<!-- 각 번호 페이지 버튼 -->
				<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
					<li class="pageInfo_btn ${pageMaker.cri.pageNum == num ? "active":"" }"><a href="${num}">${num}</a></li>
				</c:forEach>
				
				<!-- 다음페이지 버튼 -->
				<c:if test="${pageMaker.next}">
					<li class="pageInfo_btn next"><a href="${pageMaker.endPage + 1 }">></a></li>
				</c:if>	
				
				<!-- 마지막 버튼  -->
					<li class="pageInfo_btn next"><a href="${pageMaker.lastpage}">>></a></li>
				
			</ul>
		</div>
	</div>
	
	<form id="moveForm" method="get">
	<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
	<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
	<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
	<input type="hidden" name="type" value="${pageMaker.cri.type }">
	</form>
	
	<div class="form-group">
<button class="btn btn-dark" onclick="location.href='/insert'">글쓰기</button>

	
	</div>
	
	
</div>

<%@ include file ="listS.jsp" %>
</body>
</html>