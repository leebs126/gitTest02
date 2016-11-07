<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
 <title>도서 목록 페이지</title>
</head>
<BODY>
	<HGROUP>
		<H1>컴퓨터와 인터넷</H1>
		<H2>오늘의 책</H2>
	</HGROUP>
	<SECTION id="new_book">
		<H3>새로나온 책</H3>
		<DIV id="left_scroll">
			<A href='javascript:slide("left");'><IMG src="${pageContext.request.contextPath}/resources/image/left.gif"></A>
		</DIV>
		<DIV id="carousel_inner">
			<UL id="carousel_ul">
			<c:choose>
			   <c:when test="${ empty search_goods_list  }" >
			        <LI>
					<DIV id="book">
						<a><h1>제품이없습니다.</h1></a>
					  </DIV>
				</LI> 
			   </c:when>
			   <c:otherwise>
			    <c:forEach var="item" items="${search_goods_list }" >
			     <LI>
					<DIV id="book">
						<A href="#">
						<IMG width="75" alt="" src="${pageContext.request.contextPath}/fileDownload.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}">
						</A>
						<DIV class="sort">[컴퓨터 인터넷]</DIV>
						<DIV class="title">
							<A href="${pageContext.request.contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id }">
							  ${item.goods_title}
							</A>
						</DIV>
						<DIV class="writer">${item.goods_writer} | ${item.goods_publisher}</DIV>
						<DIV class="price">
							<SPAN>
							  <fmt:formatNumber  value="${item.goods_price}" type="number" var="goods_price" />
		                         ${goods_price}원
							</SPAN> <BR>
							 <fmt:formatNumber  value="${item.goods_price*0.9}" type="number" var="discounted_price" />
				               ${discounted_price}원(10%할인)
						</DIV>
					</DIV>
				</LI>
				</c:forEach> 
				<LI>
				</LI> 
			   </c:otherwise>
			 </c:choose>
			 
			</UL>
		</DIV>
		<DIV id="right_scroll">
			<A href='javascript:slide("right");'><IMG
				src="${pageContext.request.contextPath}/resources/image/right.gif"></A>
		</DIV>
		<INPUT id="hidden_auto_slide_seconds" type="hidden" value="0">

		<DIV class="clear"></DIV>
	</SECTION>
	<DIV class="clear"></DIV>
	<DIV id="sorting">
		<UL>
			<LI><A class="active" href="#">베스트 셀러</A></LI>
			<LI><A href="#">최신 출간</A></LI>
			<LI><A style="border: currentColor; border-image: none;"
				href="#">최근 등록</A></LI>
		</UL>
	</DIV>
	<TABLE id="list_view">
		<TBODY>
		  <c:forEach var="item" items="${search_goods_list }"> 
			<TR>
					<TD class="goods_image">
					<a href="<%=request.getContextPath()%>/goods/goodsDetail.do?goods_id=${item.goods_id }">
						<IMG width="75" alt="" src="<%=request.getContextPath() %>/fileDownload.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}""></TD>
					</a>
					<TD class="goods_description">
						<H2>
							<A href="<%=request.getContextPath()%>/goods/goodsDetail.do?goods_id=${item.goods_id }">${item.goods_title }</A>
						</H2>
						<c:set var="goods_pub_date" value="${item.goods_published_date }" />
					   <c:set var="arr" value="${fn:split(goods_pub_date,' ')}" />
						<div class="writer_press">${item.goods_writer }저
							|${item.goods_publisher }|<c:out value="${arr[0]}" /></div>
						<P>${item.goods_intro}</P>
					</TD>
					<TD class="price"><SPAN>${item.goods_price }원</SPAN><BR>
						<STRONG>
						 <fmt:formatNumber  value="${item.goods_price*0.9}" type="number" var="discounted_price" />
				               ${discounted_price}원
						</STRONG><BR>(10% 할인)</TD>
					<TD><INPUT type="checkbox" value=""></TD>
					<TD class="buy_btns">
						<UL>
							<LI><A href="#">장바구니</A></LI>
							<LI><A href="#">구매하기</A></LI>
							<LI><A href="#">비교하기</A></LI>
						</UL>
					</TD>
			</TR>
			</c:forEach>
		</TBODY>
	</TABLE>
	<DIV class="clear"></DIV>
	<DIV id="page_wrap">
		<UL id="page_control">
			<LI><A class="no_border" href="#">Prev</A></LI>
			<c:set var="page_num" value="0" />
			<c:forEach var="count" begin="1" end="10" step="1">
				<c:choose>
					<c:when test="${count==1 }">
						<LI><A class="page_contrl_active" href="#">${count+page_num*10 }</A></LI>
					</c:when>
					<c:otherwise>
						<LI><A href="#">${count+page_num*10 }</A></LI>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<LI><A class="no_border" href="#">Next</A></LI>
		</UL>
	</DIV>