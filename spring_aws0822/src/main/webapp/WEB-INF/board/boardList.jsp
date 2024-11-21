<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import = "java.util.*" %>
<%@ page import="com.myaws.myapp.domain.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
<link href="/resources/css/style2.css" rel="stylesheet">
</head>
<body>
<header>
	<h2 class="mainTitle">글목록</h2>
	<form class="search" name="frm" action="${pageContext.request.contextPath}/board/boardList.aws" method="get">
		<select name = "searchType">  <!-- 어떤걸 선택할래? -->
			<option value = "subject">제목</option>
			<option value = "writer">작성자</option>
		</select>
		<input type="text" name ="keyword"> <!-- 나는 키워드로 입력할거야  -->
		<button type="submit" class="btn">검색</button>
	</form>
</header>

<section>	
	<table class="listTable">
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>조회</th>
			<th>추천</th>
			<th>날짜</th>
		</tr>   
		
		<c:forEach items="${blist}" var="bv" varStatus="status"> <!-- forEach문을 써서 반복문 실행 -->
		<tr>
			<td>${pm.totalCount-(status.index+(pm.scri.page-1)*pm.scri.perPageNum)}</td> <!-- status index는 0부터 시작한다. -->
			<td class="title">
			<c:forEach var="i" begin="1" end="${bv.level_}" step="1">
			&nbsp;&nbsp;
			<c:if test="${i==bv.level_}">
				ㄴ
			 </c:if>
			</c:forEach> 
			<a href="${pageContext.request.contextPath}/board/boardContents.aws?bidx=${bv.bidx}">${bv.subject}</a></td>
			<td>${bv.writer}</td>
			<td>${bv.viewcnt}</td>
			<td>${bv.recom}</td>
			<td>${bv.writeday}</td>
		</tr>		
		</c:forEach>
	
	</table>
	
	<div class="btnBox">
		<a class="btn aBtn" href="${pageContext.request.contextPath}/board/boardWrite.aws">글쓰기</a>
	</div>
	
	
	<c:set var="queryParam" value="keyword=${pm.scri.keyword}&searchType=${pm.scri.searchType}"></c:set>
	<div class="page">
	
		 <ul>	
		 
		<c:if test="${pm.prev == true}"> 
		<li><a href="${pageContext.request.contextPath}/board/boardList.aws?page=${pm.startPage-1}&${queryParam}">◀</a></li>
		</c:if>

		<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}" step="1">
		<li <c:if test="${i==pm.scri.page}"> class='on' </c:if> > 
		<a href="${pageContext.request.contextPath}/board/boardList.aws?page=${i}&${queryParam}">${i}</a>
		</li>
		</c:forEach>	
			
	 	<c:if test="${pm.next&&pm.endPage>0}">
	 	<li><a href="${pageContext.request.contextPath}/board/boardList.aws?page=${pm.endPage+1}&${queryParam}">▶</a></li>
	 	</c:if>
	 	
		</ul> 
	</div>
	 
</section>

</body>
</html>