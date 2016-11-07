<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<script type="text/javascript">
var loopSearch=true;
function keywordSearch(){
	if(loopSearch==false)
		return;
 var value=document.frmSearch.searchWord.value;
	$.ajax({
		type : "get",
		async : true, //false인 경우 동기식으로 처리한다.
		url : "http://localhost:8090/bookshop01/goods/keywordSearch.do",
		data : {keyword:value},
		success : function(data, textStatus) {
		//	alert("success");
			displayResult(data);
		},
		error : function(data, textStatus) {
			alert("에러가 발생했습니다."+data);
		},
		complete : function(data, textStatus) {
			//alert("작업을완료 했습니다");
			
		}
	}); //end ajax	
}

function displayResult(resultText){
	var result = resultText.split('|');
	var count = parseInt(result[0]);
	var keywordList = null;
	if(count>0) {
	    keywordList = result[1].split(',');
	    var html = '';
	    for (var i=0; i<keywordList.length; i++) {
	      html += "<a href=\"javascript:select('"+keywordList[i]+"')\">"+keywordList[i]+"</a><br/>";
	    }
	    var listView = document.getElementById("suggestList");
	    listView.innerHTML = html;
	    show('suggest');
	}else{
	    hide('suggest');
	}
}

function select(selectedKeyword) {
	 document.frmSearch.searchWord.value=selectedKeyword;
	 loopSearch = false;
	 hide('suggest');
}
	
function show(elementId) {
	 var element = document.getElementById(elementId);
	 if(element) {
	  element.style.display = 'block';
	 }
	}

function hide(elementId){
   var element = document.getElementById(elementId);
   if(element){
	  element.style.display = 'none';
   }
}



</script>
<body>
	<div id="logo">
	<a href="${pageContext.request.contextPath}/main/main.do">
		<img width="176" height="80" alt="booktopia" src="${pageContext.request.contextPath}/resources/image/Booktopia_Logo.jpg">
		</a>
	</div>
	<div id="head_link">
		<ul>
		   <c:choose>
		     <c:when test="${isLogOn==true and not empty member_info }">
			   <li><a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a></li>
			   <li><a href="${pageContext.request.contextPath}/mypage/myPageMain.do">마이페이지</a></li>
			   <li><a href="${pageContext.request.contextPath}/cart/myCartMain.do">장바구니</a></li>
			   <li><a href="#">주문배송</a></li>
			 </c:when>
			 <c:otherwise>
			   <li><a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a></li>
			   <li><a href="${pageContext.request.contextPath}/member/memberForm.do">회원가입</a></li> 
			 </c:otherwise>
			</c:choose>
			   <li><a href="#">고객센터</a></li>
			   <li class="no_line"><a href="${pageContext.request.contextPath}/admin/goods/adminGoodsMain.do">관리자</a></li>
		</ul>
	</div>
	<br>
	<div id="search" >
		<form name="frmSearch" action="${pageContext.request.contextPath}/goods/searchGoods.do" >
			<input name="searchWord" class="main_input" type="text"  onKeyUp="keywordSearch()"> 
			<input type="submit" name="search" class="btn1"  value="검 색" >
		</form>
	</div>
   <div id="suggest">
        <div id="suggestList"></div>
   </div>
</body>
</html>