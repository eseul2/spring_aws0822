<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myaws.myapp.domain.*" %>
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
<title>글 수정</title>

<style>

table {
	margin : auto; 
}

input[type="text"] {
    width: 500px;
    height: 30px;
}


textarea {
    width: 500px;
    height: 400px;
}

.header {
	height : 50px;
}


button {
    width: 50px;
    height: 30px;
    font-size: 15px;
    background-color: black;
    color: white;
}


.sub {
    width: 30px;
    height: 20px;
   
}

</style>

</head>
<body>

<script>
function check() {
	
	//유효성 검사하기
	var fm = document.frm;
	
	if(fm.subject.value == "") {
		alert("제목을 입력해주세요");
		fm.subject.focus();  // 커서가 입력안한 해당 자리로 갈수 있도록 
		return;
	} else if(fm.contents.value =="") {
		alert("내용을 입력해주세요");
		fm.contents.focus(); 
		return;
	}else if(fm.writer.value =="") {
		alert("작성자를 입력해주세요");
		fm.writer.focus(); 
		return;
	}else if(fm.password.value =="") {
		alert("비밀번호를 입력해주세요");
		fm.password.focus(); 
		return;
	}
	
	var ans = confirm("수정하시겠습니까?");
	
	
	if(ans == true) {	// 업데이트하고 처리를 하겠다. 
		fm.action="${pageContext.request.contextPath}/board/boardModifyAction.aws";
		fm.method="post";
		fm.enctype="multipart/form-data";   //파일을 올리기 위해서 지정해야한다. 
		fm.submit(); //파일 업로드를 포함한 폼 데이터를 전송할 때 필요한 인코딩 방식을 지정
	}
		return; 
}	
</script>


<form name="frm">
<input type="hidden" name="bidx" value="${bv.bidx}">  <!-- bidx값이 수정할때 필요해서 hidden으로 안보이게 한 input에 넣어서 controller로 보낸다. -->
<input type="hidden" name="midx" value="${bv.midx}">
<h2>글수정</h2>

<hr>

<table>
	<tr>
		<td class="header">제목</td>
	</tr>
	<tr>
		<td><input type="text" name= "subject" value="${bv.subject}"></td>
	</tr>
	<tr>
		<td>내용</td>
	</tr>
	<tr>
		<td><textarea name="contents">${bv.contents}</textarea></td>
	</tr>
	<tr>
		<td style="text-align:center">작성자<input type="text" name="writer" value="${bv.writer}" ></td>
	</tr>
	<tr>
		<td style="text-align:center">비밀번호<input type="password" name="password"></td>
	</tr>
	<tr>
		<td>첨부파일<input type="file" name="attachfile"></td>
	</tr>
	<tr>
		<td><button type ="button"  onclick="check()">저장</button></td>
		<td><button type ="button" onclick="history.back();">취소</button></td>  <!-- 이전화면으로 돌아가기 --> 
	</tr>
</table>
</form>

</body>
</html>