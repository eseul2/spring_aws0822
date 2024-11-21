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
            <img src="<%= request.getContextPath() %>/resources/images/main.jpg" alt="빵집 이미지" />
        </div>
        
    
        <!-- 문구 영역 -->
        <div class="banner-text">
            <h1>이달의 빵집 &#127838;</h1>
            <p>이곳은 전국의 빵집을 소개하고 리뷰를 공유하는 공간입니다.<br>
               당신만의 숨은 맛집을 찾고 공유해보세요!<br>
               뭘 적어야 할지 모르겠네영~~~~~ <br>
               하지만 언젠가 생각이 나겠죠~~~~</p>
              <!-- 버튼을 banner-text 아래에 위치시키기 -->
        <button onclick="window.location.href='/search'" class="btn">빵집 찾기</button> 
        </div>
    </div>
</section>


<!-- 부드럽고 얇은 선 추가 -->
<hr style="border: 1px solid #ddd; margin: 20px 0;">

<!-- 빵집 추천 미리보기 게시물 띄우기 -->
<section class="recommend-section">
    <h2>Bakery Review</h2>
    <div class="card-container">
        <div class="card" onclick="openModal('bakery1')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery1.jpg" alt="추천 빵집 1">
            <div class="card-content">
                <h3><strong>빵집 이름 1</strong></h3>
                <p>서울 강남구의 베이커리로, 바게트가 유명합니다!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery2')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery2.jpg" alt="추천 빵집 2">
            <div class="card-content">
                <h3><strong>빵집 이름 2</strong></h3>
                <p>부산 해운대에 위치한 달콤한 케이크로 유명한 곳!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery3')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery3.jpg" alt="추천 빵집 3">
            <div class="card-content">
                <h3><strong>빵집 이름 3</strong></h3>
                <p>대전에서 맛볼 수 있는 촉촉한 크로와상이 일품!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery4')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery3.jpg" alt="추천 빵집 3">
            <div class="card-content">
                <h3><strong>빵집 이름 3</strong></h3>
                <p>대전에서 맛볼 수 있는 촉촉한 크로와상이 일품!</p>
            </div>
        </div>
    </div>
    
        <div class="card-container">
        <div class="card" onclick="openModal('bakery1')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery1.jpg" alt="추천 빵집 1">
            <div class="card-content">
                <h3><strong>빵집 이름 1</strong></h3>
                <p>서울 강남구의 베이커리로, 바게트가 유명합니다!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery2')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery2.jpg" alt="추천 빵집 2">
            <div class="card-content">
                <h3><strong>빵집 이름 2</strong></h3>
                <p>부산 해운대에 위치한 달콤한 케이크로 유명한 곳!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery3')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery3.jpg" alt="추천 빵집 3">
            <div class="card-content">
                <h3><strong>빵집 이름 3</strong></h3>
                <p>대전에서 맛볼 수 있는 촉촉한 크로와상이 일품!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery4')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery3.jpg" alt="추천 빵집 3">
            <div class="card-content">
                <h3><strong>빵집 이름 3</strong></h3>
                <p>대전에서 맛볼 수 있는 촉촉한 크로와상이 일품!</p>
            </div>
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
    
    
<!-- 모달 팝업 -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 id="modalTitle"></h2>
        <img id="modalImage" src="" alt="모달 이미지" style="width: 100%;">
        <p id="modalDescription"></p>
    </div>
</div>

<!-- 자바스크립트 코드 -->
<script>
    function openModal(bakeryId) {
        const modal = document.getElementById("myModal");
        const modalTitle = document.getElementById("modalTitle");
        const modalImage = document.getElementById("modalImage");
        const modalDescription = document.getElementById("modalDescription");

        // 빵집에 대한 정보 설정
        if (bakeryId === 'bakery1') {
            modalTitle.innerText = "빵집 이름 1";
            modalImage.src = "<%= request.getContextPath() %>/resources/images/bakery1.jpg";
            modalDescription.innerText = "서울 강남구의 베이커리로, 바게트가 유명합니다!";
        } else if (bakeryId === 'bakery2') {
            modalTitle.innerText = "빵집 이름 2";
            modalImage.src = "<%= request.getContextPath() %>/resources/images/bakery2.jpg";
            modalDescription.innerText = "부산 해운대에 위치한 달콤한 케이크로 유명한 곳!";
        } else if (bakeryId === 'bakery3') {
            modalTitle.innerText = "빵집 이름 3";
            modalImage.src = "<%= request.getContextPath() %>/resources/images/bakery3.jpg";
            modalDescription.innerText = "대전에서 맛볼 수 있는 촉촉한 크로와상이 일품!";
        }

        // 모달 보이기
        modal.style.display = "block";
    }

    function closeModal() {
        const modal = document.getElementById("myModal");
        modal.style.display = "none";
    }

    // 모달이 외부를 클릭했을 때 닫기
    window.onclick = function(event) {
        const modal = document.getElementById("myModal");
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>    
   
</body>
</html>