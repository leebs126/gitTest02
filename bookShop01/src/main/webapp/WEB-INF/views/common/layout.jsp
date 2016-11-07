<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/basic-jquery-slider.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/mobile.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.6.2.min.js" type="text/javascript"></SCRIPT>
<script src="${pageContext.request.contextPath}/resources/jquery/jquery.easing.1.3.js" type="text/javascript"></SCRIPT>
<script src="${pageContext.request.contextPath}/resources/jquery/stickysidebar.jquery.js" type="text/javascript"></SCRIPT>
<script src="${pageContext.request.contextPath}/resources/jquery/basic-jquery-slider.js" type="text/javascript"></SCRIPT>
<script src="${pageContext.request.contextPath}/resources/jquery/tabs.js" type="text/javascript"></SCRIPT>
<script src="${pageContext.request.contextPath}/resources/jquery/carousel.js" type="text/javascript"></SCRIPT>
<script>
	// 슬라이드 
	$(document).ready(function() {
		$('#ad_main_banner').bjqs({
			'width' : 775,
			'height' : 145,
			'showMarkers' : true,
			'showControls' : false,
			'centerMarkers' : false
		});
	});
	// 스티키 		
	$(function() {
		$("#sticky").stickySidebar({
			timer : 100,
			easing : "easeInBounce"
		});
	});
</script>
	<title><tiles:insertAttribute name="title" /></title>
</head>
<body>
	<div id="outer_wrap">
		<div id="wrap">
			<header>
				   <tiles:insertAttribute name="header" />
			</header>
			<div class="clear"></div>
			<aside>
				 <tiles:insertAttribute name="side" />
			</aside>
			<article>
			 	<tiles:insertAttribute name="body" />
			</article>
			<div class="clear"></div>
			<footer>
        		<tiles:insertAttribute name="footer" />
        	</footer>
		</div>
		 <tiles:insertAttribute name="sticky_menu" />
    </div>        	
</body>      
        
        