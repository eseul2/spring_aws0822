<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링 학습하기</title>
</head>
<body>


<!-- 값이 비어있지 않으면 -->
<c:if test="${!empty midx}">
${memberName}&nbsp;
<a href='${pageContext.request.contextPath}/member/memberLogout.aws'>님, 로그아웃</a> 
</c:if>
<br>


<a href ="${pageContext.request.contextPath}/member/memberJoin.aws">회원가입 페이지</a>
<br>
<a href ="${pageContext.request.contextPath}/member/memberLogin.aws">회원 로그인 페이지</a>
<br>
<a href ="${pageContext.request.contextPath}/member/memberList.aws">회원 목록 가기</a>
<br>
<a href ="${pageContext.request.contextPath}/board/boardList.aws">게시판 목록가기</a>
<a href ="${pageContext.request.contextPath}/board/test.aws">메인페이지</a>
<a href ="${pageContext.request.contextPath}/board/login.aws">로그인페이지</a>
</body>
</html>



