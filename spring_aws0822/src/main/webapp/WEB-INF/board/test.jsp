<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메인페이지</title>
<link href= "<%=request.getContextPath()%>/resources/css/style3.css" type-"text/css" rel="stylesheet" >
</head>

<script>
//모달 열고 닫기
const modal = document.getElementById('modal-popup');
const closeBtn = document.querySelector('.close-btn');

function openModal() {
  modal.style.display = 'flex';
}

function closeModal() {
  modal.style.display = 'none';
}

closeBtn.addEventListener('click', closeModal);

// 이미지 슬라이더
let currentSlide = 0;
const slides = document.querySelectorAll('.slides img');
const totalSlides = slides.length;

document.querySelector('.prev-btn').addEventListener('click', () => {
  slides[currentSlide].style.display = 'none';
  currentSlide = (currentSlide - 1 + totalSlides) % totalSlides;
  slides[currentSlide].style.display = 'block';
});

document.querySelector('.next-btn').addEventListener('click', () => {
  slides[currentSlide].style.display = 'none';
  currentSlide = (currentSlide + 1) % totalSlides;
  slides[currentSlide].style.display = 'block';
});

// 초기화
slides.forEach((slide, index) => {
  slide.style.display = index === 0 ? 'block' : 'none';
});

</script>



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
                <!-- 가로 구분선 -->
				<hr class="preview"> <!-- 미리보기 구분선 -->
                <p>서울 강남구의 베이커리로, 바게트가 유명합니다!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery2')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery2.jpg" alt="추천 빵집 2">
            <div class="card-content">
                <h3><strong>빵집 이름 2</strong></h3>
				<!-- 가로 구분선 -->
				<hr class="preview"> <!-- 미리보기 구분선 -->
                <p>부산 해운대에 위치한 달콤한 케이크로 유명한 곳!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery3')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery3.jpg" alt="추천 빵집 3">
            <div class="card-content">
                <h3><strong>빵집 이름 3</strong></h3>
                <!-- 가로 구분선 -->
				<hr class="preview"> <!-- 미리보기 구분선 -->
                <p>대전에서 맛볼 수 있는 촉촉한 크로와상이 일품!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery4')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery3.jpg" alt="추천 빵집 4">
            <div class="card-content">
                <h3><strong>빵집 이름 4</strong></h3>
				<!-- 가로 구분선 -->
				<hr class="preview"> <!-- 미리보기 구분선 -->
                <p>대전에서 맛볼 수 있는 촉촉한 크로와상이 일품!</p>
            </div>
        </div>
    </div>
    
        <div class="card-container">
        <div class="card" onclick="openModal('bakery1')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery1.jpg" alt="추천 빵집 1">
            <div class="card-content">
                <h3><strong>빵집 이름 1</strong></h3>
				<!-- 가로 구분선 -->
				<hr class="preview"> <!-- 미리보기 구분선 -->
                <p>서울 강남구의 베이커리로, 바게트가 유명합니다!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery2')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery2.jpg" alt="추천 빵집 2">
            <div class="card-content">
                <h3><strong>빵집 이름 2</strong></h3>
				<!-- 가로 구분선 -->
				<hr class="preview"> <!-- 미리보기 구분선 -->
                <p>부산 해운대에 위치한 달콤한 케이크로 유명한 곳!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery3')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery3.jpg" alt="추천 빵집 3">
            <div class="card-content">
                <h3><strong>빵집 이름 3</strong></h3>
				<!-- 가로 구분선 -->
				<hr class="preview"> <!-- 미리보기 구분선 -->
                <p>대전에서 맛볼 수 있는 촉촉한 크로와상이 일품!</p>
            </div>
        </div>
        <div class="card" onclick="openModal('bakery4')">
            <img src="<%= request.getContextPath() %>/resources/images/bakery3.jpg" alt="추천 빵집 4">
            <div class="card-content">
                <h3><strong>빵집 이름 4</strong></h3>
				<!-- 가로 구분선 -->
				<hr class="preview"> <!-- 미리보기 구분선 -->
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
<div id="modal-popup" class="modal">
  <div class="modal-content">
    <!-- 닫기 버튼 -->
    <button class="close-btn" onclick="closeModal()">&times;</button>

    <!-- 이미지 슬라이드 -->
    <div class="image-slider">
      <div class="slides" id="modalSlides">
        <!-- 이미지 슬라이드는 JavaScript로 추가 -->
      </div>
      <button class="prev-btn" onclick="prevSlide()">&#10094;</button>
      <button class="next-btn" onclick="nextSlide()">&#10095;</button>
    </div>

    <!-- 가게 이름 -->
    <h2 id="modalTitle" class="store-name"></h2>

    <!-- 가로 구분선 -->
<hr class="modal-divider">

    <!-- 관리자 리뷰 -->
    <p id="modalReview" class="store-review"></p>

    <!-- 메뉴 정보 -->
    <div class="menu-info">
      <h3>메뉴 정보</h3>
      <p id="modalLunch"></p>
      <p id="modalDinner"></p>
    </div>

    <!-- 가게 세부 정보 -->
    <div class="store-details">
      <p id="modalAddress"><strong>주소:</strong></p>
      <p id="modalPhone"><strong>전화번호:</strong></p>
      <p id="modalParking"><strong>주차:</strong></p>
    </div>

    <!-- 지도 -->
    <div id="store-map" class="map">
      <iframe
        id="modalMap"
        src=""
        width="100%"
        height="300"
        frameborder="0"
        style="border:0"
        allowfullscreen
      ></iframe>
    </div>
  </div>
</div>



<!-- 자바스크립트 코드 -->
<script>
//빵집 데이터를 저장한 객체 배열 (예시로 사용)
const bakeryData = {
  bakery1: {
    title: "가게 이름 1",
    review: "깔끔한 분위기와 친절한 서비스가 인상적인 곳입니다.",
    images: [
      "<%= request.getContextPath() %>/resources/images/bakery1-1.jpg",
      "<%= request.getContextPath() %>/resources/images/bakery1-2.jpg",
      "<%= request.getContextPath() %>/resources/images/bakery1-3.jpg"
    ],
    lunch: "런치 코스: 75,000원",
    dinner: "디너 코스: 120,000원",
    address: "서울특별시 강남구 삼성동 123-45",
    phone: "02-1234-5678",
    parking: "가능 (발렛파킹)",
    mapSrc: "https://maps.google.com/maps?q=37.5172,127.0473&z=15&output=embed"
  },
  bakery2: {
    title: "가게 이름 2",
    review: "해운대의 바다를 보며 즐길 수 있는 맛있는 디저트 카페!",
    images: [
      "<%= request.getContextPath() %>/resources/images/bakery2-1.jpg",
      "<%= request.getContextPath() %>/resources/images/bakery2-2.jpg",
      "<%= request.getContextPath() %>/resources/images/bakery2-3.jpg"
    ],
    lunch: "런치 코스: 55,000원",
    dinner: "디너 코스: 95,000원",
    address: "부산 해운대구 우동 678-90",
    phone: "051-2345-6789",
    parking: "불가",
    mapSrc: "https://maps.google.com/maps?q=35.1587,129.1604&z=15&output=embed"
  }
};

// 현재 슬라이드 인덱스
let currentSlideIndex = 0;

// 모달 열기
function openModal(bakeryId) {
  const modal = document.getElementById("modal-popup");
  const bakeryInfo = bakeryData[bakeryId];

  if (bakeryInfo) {
    // 기본 정보 설정
    document.getElementById("modalTitle").innerText = bakeryInfo.title;
    document.getElementById("modalReview").innerText = `관리자 리뷰: ${bakeryInfo.review}`;
    document.getElementById("modalLunch").innerText = bakeryInfo.lunch;
    document.getElementById("modalDinner").innerText = bakeryInfo.dinner;
    document.getElementById("modalAddress").innerText = `주소: ${bakeryInfo.address}`;
    document.getElementById("modalPhone").innerText = `전화번호: ${bakeryInfo.phone}`;
    document.getElementById("modalParking").innerText = `주차: ${bakeryInfo.parking}`;
    document.getElementById("modalMap").src = bakeryInfo.mapSrc;

    // 이미지 슬라이드 추가
    const slidesContainer = document.getElementById("modalSlides");
    slidesContainer.innerHTML = ""; // 기존 슬라이드 초기화
    bakeryInfo.images.forEach((image, index) => {
      const imgElement = document.createElement("img");
      imgElement.src = image;
      imgElement.alt = `Slide ${index + 1}`;
      imgElement.style.display = index === 0 ? "block" : "none"; // 첫 슬라이드만 보이도록 설정
      slidesContainer.appendChild(imgElement);
    });

    // 모달 표시
    modal.style.display = "flex"; //중앙정렬
    currentSlideIndex = 0; // 초기화
  } else {
    console.error("유효하지 않은 bakeryId입니다.");
  }
}

// 모달 닫기
function closeModal() {
  const modal = document.getElementById("modal-popup");
  modal.style.display = "none";
}

// 이전 슬라이드로 이동
function prevSlide() {
  const slides = document.querySelectorAll("#modalSlides img");
  slides[currentSlideIndex].style.display = "none";
  currentSlideIndex = (currentSlideIndex - 1 + slides.length) % slides.length;
  slides[currentSlideIndex].style.display = "block";
}

// 다음 슬라이드로 이동
function nextSlide() {
  const slides = document.querySelectorAll("#modalSlides img");
  slides[currentSlideIndex].style.display = "none";
  currentSlideIndex = (currentSlideIndex + 1) % slides.length;
  slides[currentSlideIndex].style.display = "block";
}

// 모달 외부를 클릭했을 때 닫기
window.onclick = function (event) {
  const modal = document.getElementById("modal-popup");
  if (event.target === modal) {
    closeModal();
  }
};

</script>    
   
</body>
</html>