<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
</head>

<BODY>
<form action="${pageContext.request.contextPath}/admin/goods/saveNewGoods.do" method="post">
		<h1>새제품 등록창</h1>
<DIV class="clear"></DIV>
	<!-- 내용 들어 가는 곳 -->
	<DIV id="container">
		<UL class="tabs">
			<LI><A href="#tab1">제품정보</A></LI>
			<LI><A href="#tab2">제품목차</A></LI>
			<LI><A href="#tab3">제품저자소개</A></LI>
			<LI><A href="#tab4">제품소개</A></LI>
			<LI><A href="#tab5">출판사 제품 평가</A></LI>
			<LI><A href="#tab6">추천사</A></LI>
		</UL>
		<DIV class="tab_container">
			<DIV class="tab_content" id="tab1">
				<table >
			<tr >
				<td width=200 >제품분류</td>
				<td width=500><select name="goods_sort">
						<option value="컴퓨터와 인터넷" selected>컴퓨터와 인터넷
						<option value="디지털 기기">디지털 기기
					</select>
				</td>
			</tr>
			<tr >
				<td >제품이름</td>
				<td><input name="goods_title" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >저자</td>
				<td><input name="goods_writer" type="text" size="40" /></td>
			</tr>
			<tr>
				<td >출판사</td>
				<td><input name="goods_publisher" type="text" size="40" /></td>
			</tr>
			<tr>
				<td >제품정가</td>
				<td><input name="goods_price" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품판매가격</td>
				<td><input name="goods_sales_price" type="text" size="40" /></td>
			</tr>
			
			
			<tr>
				<td >제품 구매 포인트</td>
				<td><input name="goods_point" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품출판일</td>
				<td><input  name="goods_published_date" value="2015.05.05"  type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품 총 페이지수</td>
				<td><input name="goods_total_page" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >ISBN</td>
				<td><input name="goods_isbn" type="text" size="40" /></td>
			</tr>
			<tr>
				<td >제품 배송비</td>
				<td><input name="goods_delivery_price" type="text" size="40" /></td>
			</tr>
			<tr>
				<td >제품 도착 예정일</td>
				<td><input name="goods_delivery_date" value="2015.06.05"  type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품종류</td>
				<td>
				<select name="goods_type">
				  <option value="bestseller"  >베스트셀러</option>
				  <option value="steadyseller" >스테디셀러</option>
				  <option value="newbook" selected >신간</option>
				  <option value="on_sale" >판매중</option>
				  <option value="buy_out" >품절</option>
				  <option value="out_of_print" >절판</option>
				</select>
				</td>
			</tr>
			<tr>
			 <td>
			   <br>
			 </td>
			</tr>
				</table>	
			</DIV>
			<DIV class="tab_content" id="tab2">
				<H4>책목차</H4>
				<table>	
				 <tr>
					<td >책목차</td>
					<td><textarea  rows="100" cols="80" name="goods_contents_order"><pre> </pre></textarea></td>
				</tr>
				</table>	
			</DIV>
			<DIV class="tab_content" id="tab3">
				<H4>제품 저자 소개</H4>
				<P>
				 <table>
  				 <tr>
					<td>제품 저자 소개</td>
					<td><textarea  rows="100" cols="80" name="goods_writer_intro"><pre> </pre></textarea></td>
			    </tr>
			   </table>
				</P>
			</DIV>
			<DIV class="tab_content" id="tab4">
				<H4>제품소개</H4>
				<P>
				<table>
					<tr>
						<td >제품소개</td>
						<td><textarea  rows="100" cols="80" name="goods_intro"><pre> </pre></textarea></td>
				    </tr>
			    </table>
				</P>
			</DIV>
			<DIV class="tab_content" id="tab5">
				<H4>출판사 제품 평가</H4>
				<P>
				<table>
				 <tr>
					<td>출판사 제품 평가</td>
					<td><textarea  rows="100" cols="80" name="goods_publisher_comment"><pre> </pre></textarea></td>
			    </tr>
			</table>
				</P>
			</DIV>
			<DIV class="tab_content" id="tab6">
				<H4>추천사</H4>
				<p>
				 <table>
					 <tr>
					   <td>추천사</td>
					    <td><textarea  rows="100" cols="80" name="goods_recommendation"><pre> </pre></textarea></td>
				    </tr>
			    </table>
				</p>
			</DIV>
		</DIV>
		
	</DIV>
	<DIV class="clear"></DIV>
<center>	
	 <table>
	 <tr>
			  <td align=center>
				  <input  type="submit" value="제품 등록하기"> 
			  </td>
			</tr>
	 </table>
</center>	 
</form>	 