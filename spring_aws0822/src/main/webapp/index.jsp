<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링 학습하기</title>
</head>
<body>
<!-- 회원번호가 있으면 담아놓은 회원이름을 출력하고 로그아웃 버튼을 만들어놓는다. -->
<% if (session.getAttribute("midx") != null){
   out.println(session.getAttribute("memberName")+"<a href='"+request.getContextPath()+"/member/memberLogout.aws'>님, 로그아웃</a>");   
} %>


<a href ="<%=request.getContextPath()%>/member/memberJoin.aws">회원가입 페이지</a>
<br>
<a href ="<%=request.getContextPath()%>/member/memberLogin.aws">회원 로그인 페이지</a>
<br>
<a href ="<%=request.getContextPath()%>/member/memberList.aws">회원 목록 가기</a>
<br>
<a href ="<%=request.getContextPath()%>/board/boardList.aws">게시판 목록가기</a>
</body>
</html>



