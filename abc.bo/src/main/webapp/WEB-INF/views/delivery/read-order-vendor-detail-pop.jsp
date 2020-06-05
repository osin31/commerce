<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<%@include file="/WEB-INF/views/order/order-const.jsp"%>
<script type="text/javascript">
	/**
	 * jquery ui tab event
	 */
	$(function() {

		var param = new Object();

		param.memBankCode 		= '<c:out value="${orderDtail.memBankCode}"/>';				// 회원 은행 코드 정보
		param.memberTypeCode 	= '<c:out value="${orderDtail.memberTypeCode}"/>';			// 회원유형 코드


		$("#tabID").on( "tabsbeforeactivate", function( event, ui ) {
			ui.oldPanel.empty();
			tabPanelLoad(ui.newPanel.attr("id"));
		} );

		// 주문정보 탭 로드
		tabPanelLoad("tabOrderVendorArea");
		
		abc.biz.order.vendor.memo.init('${orderDtail.orderNo}');
		
	});

	/**
	 * tab panel에 필요한 화면 ajax 로드
	 * 추후 js 이동 예정
	 */
	function tabPanelLoad(tabid) {
		var callTabUrl;
		console.log(tabid);

		// 탭 url 설정
		if(tabid == "tabOrderVendorArea") {
			callTabUrl = "/delivery/vendor/order/read-order-vendor-info-tab";
		} else if(tabid == "tabMemoVendorArea") {
			callTabUrl = "/delivery/vendor/order/read-memo-vendor-info-tab";
		} else {
			callTabUrl = "";
		}

		//console.log("callTabUrl :::" + callTabUrl);
		// 탭 화면 로드
		$.ajax({
			type :"post",
			url : callTabUrl,
			data : { orgOrderNo : '${orderDtail.orgOrderNo}', orderNo : '${orderDtail.orderNo}'},
			dataType : "html"
		})
		.done(function(data){
			$("#" + tabid).html(data);
		})
		.fail(function(e){
			alert("화면 로드에 실패하였습니다.");
		});
	}

</script>

<body class="window-body">
	<input type="hidden" name="siteNo" id="siteNo" value="${orderDtail.siteNo}"/>
	<input type="hidden" name="memberNo" id="memberNo" value="${orderDtail.memberNo}"/>
	<input type="hidden" name="buyerEmailAddrText" id="buyerEmailAddrText" value="${orderDtail.buyerEmailAddrText}"/>
	<input type="hidden" name="buyerName" id="buyerName" value="${orderDtail.buyerName}"/>
	<input type="hidden" name="memberTypeCode" id="memberTypeCode" value="${orderDtail.memberTypeCode}"/>
	<input type="hidden" name="vndrNo" id="vndrNo" value="${vndrNo}"/>
	<div class="window-wrap">
		<div class="window-title">
			<h1>주문상세</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title"><a href="#" class="link" id="memberPop"><span>${orderDtail.loginId}</span> (<span>${orderDtail.buyerName}</span>)</a>님의 주문상세정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>주문상세정보</caption>
				<colgroup>
					<col style="width: 120px;">
					<col>
					<col style="width: 120px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">회원유형</th>
						<td class="input" >
							<span class="text">${orderDtail.memberTypeCodeName}</span>
						</td>
						<th scope="row">사이트</th>
						<td>${orderDtail.siteName}</td>
						<td class="input"></td>
					</tr>
					<tr>
						<th scope="row">주문번호</th>
						<td>
							${orderDtail.orderNo}
						</td>
						<th scope="row">주문일시</th>
						<td><fmt:formatDate value="${orderDtail.orderDtm}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td class="input"></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tab-wrap -->
			<div id="tabID" class="tab-wrap">
				<ul class="tabs">
					<li class="tab-item"><a href="#tabOrderVendorArea" class="tab-link">주문정보</a></li>
					<li class="tab-item"><a href="#tabMemoVendorArea" class="tab-link">관리자메모 <span id="totalMemoCnt">(<c:out value="${orderDtail.memoTotCnt}"/>)</span></a></li>
				</ul>
				<!-- S:tab_content 주문 정보  -->
				<div id="tabOrderVendorArea" class="tab-content"></div>
				<!-- S:tab_content 주문 메모 정보  -->
				<div id="tabMemoVendorArea" class="tab-content"></div>
			</div>
		</div>
	</div>
</body>
<script src="/static/common/js/biz/order/abcmart.order.vendor.info.tab.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/order/abcmart.order.const.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/order/abcmart.order.vendor.memo.tab.js<%=_DP_REV%>"></script>
