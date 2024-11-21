<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메인페이지</title>
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

<!-- 메인 콘텐츠 시작 -->
<section class="main-banner">
    <div class="banner-content">
        <!-- 배너 이미지 영역 -->
        <div class="banner-image">
            <img src="<%= request.getContextPath() %>/resources/images/test1121.png" alt="빵집 이미지" />
        </div>
        
    
        <!-- 문구 영역 -->
        <div class="banner-text">
            <h1>아~~~ 뭐라고 적어야 되냐~~~~~</h1>
            <p>이곳은 전국의 빵집을 소개하고 리뷰를 공유하는 공간입니다.<br>
               당신만의 숨은 맛집을 찾고 공유해보세요!</p>
              <!-- 버튼을 banner-text 아래에 위치시키기 -->
        <button onclick="window.location.href='/search'" class="btn">빵집 찾기</button> 
        </div>
    </div>
</section>
<!-- 메인 콘텐츠 끝 -->




    <!-- 푸터 영역 (페이지 끝부분에 추가) -->
    <footer class="custom-footer">
        <div class="footer-content">
            <p>&copy; 2024 빵지순례 웹사이트. 모든 권리 보유.</p>
        </div>
    </footer>
</body>
</html>