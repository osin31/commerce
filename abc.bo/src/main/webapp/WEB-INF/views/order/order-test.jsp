<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<%@include file="/WEB-INF/views/order/order-const.jsp"%>
<script type="text/javascript">
	$(function() {
		
		$("#readAuthority").click(function(){ 
			$.ajax({
				type:"post",
				url: "/orderTest/read-authority/"
			}).done(function(data, textStatus, jqXHR) {
				alert ( data.tid );
				console.log( data.tid ) ;
				$("#tid").val(data.tid);
				
				var win = abc.open.popup({
					url 	:	data.nextRedirectPcUrl,
					width 	:	700,
					height	:	700
				});
				checkPopUpClosed(win);
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		});	
		
		$("#cancel").click(function(){ 
			
			var tid = $("#tid").val();
			var param = {
					tid 	 : tid
				};
			
			$.ajax({
				type:"post",
				url: "/orderTest/kakao-cancel", 
				data 	: param
			}).done(function(data, textStatus, jqXHR) {
				alert("========== ");
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
			
		});	
		$("#order").click(function(){ 
			var tid = $("#tid").val();
			var param = {
					tid 	 : tid
				};
			
			$.ajax({
				type:"post",
				url: "/orderTest/kakao-order", 
				data 	: param
			}).done(function(data, textStatus, jqXHR) {
				alert("========== ");
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		});	
		$("#orders").click(function(){ 
			
			$.ajax({
				type:"post",
				url: "/orderTest/kakao-orders" 
			}).done(function(data, textStatus, jqXHR) {
				alert("========== ");
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		});	

	});	
	
	function checkPopUpClosed(win) {
	    var timer = setInterval(function() {
	        if(win.closed) {
	            clearInterval(timer);
	        }
	    }, 1000);
	}
</script>

<!-- S : container -->
<div class="container">
	<form id="orderForm" name="orderForm" method="post" onsubmit="return false;" >
	
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">주문정보관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>주문정보관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">주문정보 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		
		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ol class="tbl-desc-list">
				<input type="text" class="ui-input" title="" name="tid" id="tid" value="" readonly>
			</ol>
		</div>
		<!-- E : tbl-desc-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">주문정보 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
			</div>
			<div class="fr">
				<button type="button" id="readAuthority" class="btn-sm btn-func">인증페이지</button>
				<button type="button" id="cancel" class="btn-sm btn-func">취소</button>
				<button type="button" id="order" class="btn-sm btn-func">주문내역조회</button>
				<button type="button" id="orders" class="btn-sm btn-func">주문기간내역조회</button>
			</div>
		</div>
		<!-- E : tbl-controller -->

	</div>
	</form>
</div>
<!-- E : container -->

<%@include file="/WEB-INF/views/common/footer.jsp" %>