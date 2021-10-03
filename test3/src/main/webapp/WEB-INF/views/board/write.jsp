<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>
var cnt = 1;

//파일 추가를 클릭시 동적으로 파일 업로드를 추가할 것이라는 것
//name 속성의 값으로 'file'+cnt 를 설정함으로써 값을 다르게 해준다
function fn_addFile() {
	$("#d_file").append("<input  type='file'   onchange='fncFileCheck(this)' accept='image/gif,image/jpeg,image/png,image/jpg'   name='file"+cnt+"' id='file"+cnt+"' /><input type=button value=삭제 onclick=fncFileDelete(this)><br>");
	cnt++;
}
function fncFileDelete(fileBtn){
	$(fileBtn).prev().remove();
	$(fileBtn).next().remove();
	$(fileBtn).remove();
}




	$(function(){
		
		$("#wrtBtn").click(function(){
// 			$("#frm").attr("action","write").attr("method","post").submit();
			
			$("#frm").attr({"action":"insert", "method":"post"}).submit();
		})
		
		$("#uptBtn").click(function(){
// 			$("#frm").attr("action","write").attr("method","post").submit();
			
			$("#frm").attr({"action":"update", "method":"post"}).submit();
		})
		
	$("#delBtn").click(function(){
		
	
		$("#d_file").val();
		
	})
		
		
		
	})
	
	
</script>
<script type="text/javascript">
function fncFileCheck(fileInput){
	 var ext = $(fileInput).val().split(".").pop().toLowerCase();
  
	    if($.inArray(ext,["gif","jpg","jpeg","png","bmp"]) == -1) {
	        alert("gif, jpg, jpeg, png, bmp 파일만 업로드 해주세요.");
	        $(fileInput).val("");
	        return;
	    }

  
	    var file  = fileInput.files[0];
	    var _URL = window.URL || window.webkitURL;
	    var img = new Image();
  
	    img.src = _URL.createObjectURL(file);
  
  
	    img.onload = function() {
      
	        if(img.width > 500 || img.height > 500) {
	            alert("이미지 가로 500px, 세로 500px로 맞춰서 올려주세요.");
	            $(fileInput).val("");
      } 
	    }
}
</script>



</head>
<body>
<form name = "frm" id = "frm" enctype="multipart/form-data">
		<div>
			작성자 : <input type = "text" name = "name" id = "name" value = "${map.memName }"><br>
			아이디 : <input type = "text" name = "id" id = "id" value = "${map.memId }"><br>
			제   목 : <input type = "text" name = "subject" id = "subject" value = "${map.boardSubject }"><br>
			내   용 : <br>
			<textarea rows="5" cols="20" name = "content" id = "content">${map.boardContent }</textarea>
			<p>
		</div>
		<c:choose>
			<c:when test="${map==null }">
			<input type="button"  value="파일추가" onClick="fn_addFile()"/>	
		<div id="d_file">
  		
		
		</div>
				<input type = "button" name = "wrtBtn" id = "wrtBtn" value = "등록">
			</c:when>
			<c:otherwise>
			<c:forEach items="${files}" var="f">
			<a href="filedown?saveFile=${f.SAVE_NAME}&realFile=${f.REAL_NAME}">${f.REAL_NAME}</a>&nbsp;
			</c:forEach>
			<br>
				<input type = "hidden" name = "seq" id = "seq" value = "${map.seq }">
				<input type = "button" name = "uptBtn" id = "uptBtn" value = "수정">
			</c:otherwise>
		</c:choose>
	</form>
</body>
</html>