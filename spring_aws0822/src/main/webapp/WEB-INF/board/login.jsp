<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인</title>
<link href= "<%=request.getContextPath()%>/resources/css/style3.css" type-"text/css" rel="stylesheet" >
</head>
<body>

<header class="header">
	<div class="logo">
		빵지순례
	</div>
<div class="separator"></div>
        
        
<!-- 네비게이션 바 -->
	<nav class="navbar">
		<ul>
			<li><a href="/home">홈</a></li>
			<li><a href="/find-bakery">빵집찾기</a></li>
			<li><a href="/this-month-bread">이달의 빵</a></li>
			<li><a href="/free-board">자유게시판</a></li>
			<li><a href="/login">로그인</a></li>
		</ul>
	</nav>
<div class="separator"></div>
</header>

		<!-- 로그인 하기 -->
    <div class="container">
        <h1 class="site-name">MySite</h1>
        <div class="login-box">
            <form action="/main" method="post">
                <input type="text" name="memberid" placeholder="아이디" required>
                <input type="password" name="memberpw" placeholder="비밀번호" required>
                <button type="submit">로그인 하기</button>
            </form>
            <div class="links">
                <a href="/find-id">아이디찾기</a> |
                <a href="/find-password">비밀번호 찾기</a> |
                <a href="/signup">회원가입</a>
            </div>
        </div>
    </div>










    <!-- 푸터 영역 (페이지 끝부분에 추가) -->
    <footer class="custom-footer">
        <div class="footer-content">
            <p>&copy; 2024 빵지순례 웹사이트. 모든 권리 보유.</p>
        </div>
    </footer>

</body>
</html>