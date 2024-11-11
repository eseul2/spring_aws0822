<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.myaws.myapp.domain.BoardVo" %>      
 <!--  request.getAttribute()는 서블릿이나 다른 JSP 페이지에서 전달된 객체를 가져올 때 사용하는 메서드 -->
 <% 
 BoardVo bv = (BoardVo)request.getAttribute("bv");   //강제형변환  양쪽형을 맞춰준다 
 
 String memberName="";
 if(session.getAttribute("memberName") != null) {
	 memberName = (String)session.getAttribute("memberName");
 }
 int midx =0;
 if(session.getAttribute("midx") != null) {
	 midx = Integer.parseInt(session.getAttribute("midx").toString());
 }
 %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<link href="<%=request.getContextPath() %>/resources/css/style2.css" rel="stylesheet">
<!-- 제이쿼리 cdn 주소 -->
<script src="https://code.jquery.com/jquery-latest.min.js"></script> 
<script> 

//파일 이름을 받아서 해당 파일이 이미지 파일인지 확인하는 역할
function checkImageType(fileName) {
	var pattern = /jpg$|gif$|png$|jpeg$/i;   // 파일 확장자가 jpg, gif, png, jpeg로 끝나는지 검사하는 자바 정규표현식
	return fileName.match(pattern); 		// 정규표현식과 일치하는지 확인
}

function getOriginalFileName(fileName) {  // 원본 파일이름 추출
	var idx = fileName.lastIndexOf("_")+1; // 마지막 "_" 문자의 위치를 찾고 그 다음 위치를 저장
	return fileName.substr(idx);   // idx 위치부터 문자열의 끝까지 반환
}

// 글자를 잘라내서 원본 파일을 다운받도록 한다. 
function getImageLink(fileName) {
	var front = fileName.substr(0,12); // 앞에서 12글자까지 가져온다.	
	var end = fileName.substr(14); // 14번째 인덱스부터 끝까지 가져온다.
	return front+end; // 두 부분을 이어 붙여 반환
}

function download(){
	// 주소사이에 s-는 빼고 
	
	var downloadImage = getImageLink("<%=bv.getFilename()%>");
	var downLink = "<%=request.getContextPath()%>/board/displayFile.aws?fileName="+downloadImage+"&down=1";
	return downLink;
}


function commentDel(cidx) {  // 버튼을 눌렀을때 삭제 기능
	
	let ans = confirm("삭제하시겠습니까?");
	
	if(ans == true) {
		
		$.ajax({	// ajax 형식
			type : "get",	//전송방식 겟방식
			url : "<%=request.getContextPath()%>/comment/commentDeleteAction.aws?cidx="+cidx, 
			dataType : "json",	// json타입은 문서에서 {"키값" : "value값","키값2" : "value값2"}
			success : function(result){	//결과가 넘어와서 성공했을 때 받는 영역
				
				//alert("전송 성공 테스트");
				//alert(+result.value);
			 	$.boardCommentList();
				
			},
			error : function() {	// 결과가 실패했을 때 받는 영역 
				alert("전송 실패");
			}	
		});
	}
	return;
}


// 제이쿼리로 함수 만들기 
$.boardCommentList = function(){  // 보드코멘트리스트 라는 이름의 함수다.
//alert("fff");
$.ajax({	// ajax 형식
	type : "get",	//전송방식 겟방식
	url : "<%=request.getContextPath()%>/comment/commentList.aws?bidx=<%=bv.getBidx()%>", 
	dataType : "json",	// json타입은 문서에서 {"키값" : "value값","키값2" : "value값2"}
	success : function(result){	//결과가 넘어와서 성공했을 때 받는 영역
		
		//alert("전송 성공 테스트");
	
		var strTr = "";
		$(result).each(function(){
			
			var btnn ="";
			
			 //현재로그인 사람과 댓글쓴 사람의 번호가 같을때만 나타내준다
			if (this.midx == "<%=midx%>") {
				if (this.delyn=="N"){
					btnn= "<button type='button' onclick='commentDel("+this.cidx+");'>삭제</button>";
				}			
			}
			strTr = strTr + "<tr>"
			+"<td>"+this.cidx+"</td>"
			+"<td>"+this.cwriter+"</td>"
			+"<td class='content'>"+this.ccontents+"</td>"
			+"<td>"+this.writeday+"</td>"
			+"<td>"+btnn+"</td>"
			+"</tr>";					
		});		       
		
		var str  = "<table class='replyTable'>"
			+"<tr>"
			+"<th>번호</th>"
			+"<th>작성자</th>"
			+"<th>내용</th>"
			+"<th>날짜</th>"
			+"<th>DEL</th>"
			+"</tr>"+strTr+"</table>";		
			
		$("#commentListView").html(str);
	
	},
	error : function() {	// 결과가 실패했을 때 받는 영역 
		alert("전송 실패");
	}	
 });
}

// 추천하기 
$(document).ready(function(){
	
	$("#dUrl").html(getOriginalFileName("<%=bv.getFilename()%>"));
	
	$("#dUrl").click(function(){
		$("#dUrl").attr("href",download());
		return;
	});
	
	//$.boardCommentList();
	
	$("#btn").click(function(){
	//alert("추천버튼 클릭");
	
	$.ajax({	// ajax 형식
		type : "get",	//전송방식 겟방식
		url : "<%=request.getContextPath()%>/board/boardRecom.aws?bidx=<%=bv.getBidx()%>", 
		dataType : "json",	// json타입은 문서에서 {"키값" : "value값","키값2" : "value값2"}
		success : function(result){	//결과가 넘어와서 성공했을 때 받는 영역
			
		//	alert("전송 성공 테스트");
		
			var str = "추천("+result.recom+")";
			$("#btn").val(str);
		},
		error : function() {	// 결과가 실패했을 때 받는 영역 
			alert("전송 실패");
		}	
	});
	});
	
	
	$("#cmtBtn").click(function(){   //댓글쓰기 유효성 검사 
		//alert("ddd");
		let loginCheck = "<%=midx%>";
		//alert(loginCheck);
		if (loginCheck == "" || loginCheck == "null" || loginCheck == null || loginCheck == 0) {
			alert("로그인을 해주세요");
			return;
		}
		
		let cwriter = $("#cwriter").val();
		let ccontents = $("#ccontents").val();
		
		if(ccontents =="") {
			alert("내용을 입력해주세요");
			$("#ccontents").focus();
			return;
		}
		
		$.ajax({	// ajax 형식
			type : "post",	//전송방식 겟방식
			url : "<%=request.getContextPath()%>/comment/commentWriteAction.aws", 
			data : {"cwriter" : cwriter, 
				    "ccontents" : ccontents, 
				    "bidx" :"<%=bv.getBidx()%>",
				    "midx" : "<%=midx%>"
				    },
			dataType : "json",	// json타입은 문서에서 {"키값" : "value값","키값2" : "value값2"}
			success : function(result){	//결과가 넘어와서 성공했을 때 받는 영역
				
				//alert("전송 성공 테스트");
				//var str = "("+result.value+")";
				//alert(str);
				
				if(result.value == 1) {
					$("#ccontents").val("");    // 입력이 성공하면 댓글창을 비워라 
				}
				$.boardCommentList();
			},
			error : function() {	// 결과가 실패했을 때 받는 영역 
				alert("전송 실패");
			}	
		});
	});
	
});	 

	
</script>
</head>
<body>
<header>
	<h2 class="mainTitle">글내용</h2>
</header>

  <article class="detailContents">
	<h2 class="contentTitle"><%=bv.getSubject()%> (조회수:<%=bv.getViewcnt() %>)
	<input type="button" id="btn" value="추천(<%=bv.getRecom()%>)">
	</h2>
	<p class="write"><%=bv.getWriter() %> (<%=bv.getWriteday() %>)</p>
	<hr>
	<div class="content">
		<%=bv.getContents() %>	
		
	</div> 
 	<% if (bv.getFilename() == null || bv.getFilename().equals("") ) {}else { %>
	<img src="<%=request.getContextPath()%>/board/displayFile.aws?fileName=<%=bv.getFilename()%>">
	<P>
	<a id="dUrl" href="#" class="fileDown">	
	첨부파일 다운로드
	</a>
	</P>
	<%} %> 
	
	
<!-- </article> -->
	
<div class="btnBox">
	<a class="btn aBtn" href="<%=request.getContextPath()%>/board/boardModify.aws?bidx=<%=bv.getBidx()%>">수정</a>
	<a class="btn aBtn" href="<%=request.getContextPath()%>/board/boardDelete.aws?bidx=<%=bv.getBidx()%>">삭제</a>
	<a class="btn aBtn" href="<%=request.getContextPath()%>/board/boardReply.aws?bidx=<%=bv.getBidx()%>">답변</a>
	<a class="btn aBtn" href="<%=request.getContextPath()%>/board/boardList.aws">목록</a>
</div>

<article class="commentContents">
	<form name="frm">
		<p class="commentWriter"><input type="text" id="cwriter" name="cwriter" value="<%=memberName%>" readonly="readonly" style="width:80px;border:0px;">
		</p>	
		<input type="text" id="ccontents" name="ccontents">
		<button type="button" id="cmtBtn" class="replyBtn">댓글쓰기</button>
	</form>
	
	
<div id = "commentListView"></div>
</article>

</body>
</html>