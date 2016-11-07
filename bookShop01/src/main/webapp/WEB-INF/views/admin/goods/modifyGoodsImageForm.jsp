<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"
	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
 function fn_delete_image(image_id,fileName,input_id){
	 alert(input_id);
	 input=document.getElementById(input_id);
	//return ;
	
	 $.ajax({
		type : "post",
		async : false, //false인 경우 동기식으로 처리한다.
		url : "http://localhost:8090/bookshop01/admin/goods/deleteImageFile.do",
		data : {
			command:"delete_image",
			image_id:image_id,
			fileName:fileName
		},
		success : function(data, textStatus) {
			if(data.trim()=='delete_success'){
				alert("파일을 삭제했습니다.");
				input.value="";
				
			}
		},
		error : function(data, textStatus) {
			alert("에러가 발생했습니다."+data);
		},
		complete : function(data, textStatus) {
			//alert("작업을완료 했습니다");
			
		}
	}); //end ajax	 
	 //window.location.reload();
}
</script>
</HEAD>
<BODY>
<form action="${pageContext.request.contextPath}/admin/goods/modifyGoodsImage.do" method="post"	enctype="multipart/form-data">
		<h1>제품 이미지 수정창</h1>
		<br>
<table  cellspacing="0" cellpadding="0">
		<tr>
		 <td>
		   <br> 
		 </td>
		</tr>
 <c:choose>
   <c:when test="${empty imageFileList }">
      <tr>
			<td>상세 이미지1</td>
			<td>
				<input type="file" name="detail_image1" >
				<input type="text" disabled  />
				<br>
			</td>
		</tr>
		<tr>
		 <td>
		   <br>
		 </td>
		</tr>
		<tr>
			<td>상세 이미지2</td>
			<td>
				<input type="file" name="detail_image2" >
				<input type="text" disabled  />
				<br>
			</td>
		</tr>
		<tr>
		 <td>
		   <br>
		 </td>
		</tr>
		<tr>
			<td>상세 이미지3</td>
			<td>
				<input type="file" name="detail_image3"> 
				<input type="text" disabled  />
				<br>
			</td>
		</tr>
		<tr>
		 <td>
		   <br>
		 </td>
		</tr>
   </c:when>		
   <c:otherwise>
     <c:forEach var="item" items="${imageFileList }"  varStatus="itemNum">
       <c:choose>
         <c:when test="${itemNum.count==1 }">
            <tr>
			<td>메인 이미지${itemNum.count }</td>
			<td>
				<input type="file" name="main_image" >
				<input type="text" id="image_id${itemNum.count }"  value="${item.fileName }" disabled  />
				<input type="hidden"  name="image_id" value="${item.image_id }"  />
				<br>
			</td>
			<td>
			  &nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			<td>
			 <input  type="button" value="이미지 삭제"  onClick="fn_delete_image('${item.image_id }','${item.fileName }','image_id${itemNum.count }')"/>
			</td>
		</tr>
		<tr>
		 <td>
		   <br>
		 </td>
		</tr>
         </c:when>
         <c:otherwise>
           <tr>
			<td>상세 이미지${itemNum.count }</td>
			<td>
				<input type="file" name="detail_image${itemNum.count-1 }" >
				<input type="text" id="image_id${itemNum.count }"  value="${item.fileName }" disabled  />
				<input type="hidden"  name="image_id" value="${item.image_id }"  />
				<br>
			</td>
			<td>
			  &nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			<td>
			 <input  type="button" value="이미지 삭제"  onClick="fn_delete_image('${item.image_id }','${item.fileName }','image_id${itemNum.count }')"/>
			</td>
		</tr>
		<tr>
		 <td>
		   <br>
		 </td>
		</tr> 
         </c:otherwise>
       </c:choose>		
		
    </c:forEach>
 </c:otherwise>
</c:choose>		
		<tr>
			<td align=center colspan=2>
		      <INPUT   type="submit"  value="제품 이미지 수정하기">
		      <INPUT type="button" onClick="fn_return_list()" value="리스트로 돌아가기"> 
			</td>
		</tr> 
	</table>		
		</form>
