<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- 메세지 출력 -->
<c:set var="msg" value="${requestScope.msg}" />
<c:if test="${!empty msg}">
    <script>alert('${msg}');</script>
</c:if>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글삭제</title>
<link href="${pageContext.request.contextPath}/resources/css/style2.css" rel="stylesheet">
<script> 

function check() {
	  
	  // 유효성 검사하기
	  let fm = document.frm;
	  
	  if (fm.password.value == "") {
		  alert("비밀번호를 입력해주세요");
		  fm.password.focus();
		  return;
	  }
	  
	  let ans = confirm("삭제하시겠습니까?");
	  
	  if (ans == true) {
		  fm.action="${pageContext.request.contextPath}/board/boardDeleteAction.aws";
		  fm.method="post";
		  fm.submit();
	  }	  
	  return;
}

</script>
</head>
<body>
<header>
	<h2 class="mainTitle">글삭제</h2>
</header>

<form name="frm">
<input type="hidden" name="bidx" value="${bidx}">
	<table class="writeTable">
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password"></td>
		</tr>
	</table>
	
	<div class="btnBox">
		<button type="button" class="btn" onclick="check();">저장</button>
		<a class="btn aBtn" href="${pageContext.request.contextPath}/board/boardContents.aws?bidx=${bidx}"  onclick="history.back();">취소</a>
	</div>	
</form>

</body>
</html>