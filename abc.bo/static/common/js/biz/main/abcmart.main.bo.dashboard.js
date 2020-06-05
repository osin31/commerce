/**
*	BO 대시보드
*/

(function() {

	var _bodashboard = abc.object.createNestedObject(window,"abc.biz.main.bodashboard");

	// 미처리 답변 현황 새로고침 클릭시
	$("#unAswrRefreshBtn").off().on("click", function() {
		_bodashboard.refreshAjax("unAswrRefresh", function(data) {

			// 결과 data가 비어있지 않을때 => data를 가져올때 값이 없는 항목의 건수에 대해서는 0으로 처리
			if(!abc.text.isBlank(data)) {
				var unAswrList = data.refreshList;

				// 전체, 자사, 입점 건수 수정
				for(var i=0; i<3; i++) {
					// tabSeq기준 오름차순 정렬
					unAswrList.sort(function(a, b) {return a.tabSeq-b.tabSeq});
					
					for(var j=0; j<3; j++) {
						$("a[name=inquiryCnt]").eq(i).text(unAswrList[i].inquiryCnt);
						$("a[name=coworkCnt]").eq(i).text(unAswrList[i].coworkCnt);
						$("a[name=voiceCnt]").eq(i).text(unAswrList[i].voiceCnt);
						$("a[name=prdtQnaCnt").eq(i).text(unAswrList[i].prdtQnaCnt);
						$("a[name=reviewCnt").eq(i).text(unAswrList[i].reviewCnt);
					}
				}
			}
		});
	});
	
	// 기획전 현황 새로고침 클릭시
	$('#planningDisplaybtn').off().on('click', function() {
		_bodashboard.refreshAjax("plndpRefresh", function(data) {
			$('[name=plndpOngoing]').text(data.plndpList.useplanning); //진행중
			$('[name=plndpRequest]').text(data.plndpList.usereadyplanning); //요청
			$('[name=plndpRefuse]').text(data.notuseplanning); //반려
		});
	});

	// 새로고침 ajax 통신
	_bodashboard.refreshAjax = function(target, _replace) {
		$.ajax({
			type : "get",
			url  : "/bo/dashboard/refresh",
			data : {refresh : target}
		}).done(function(data, textStatus, jqXHR) {

			if(!abc.text.isBlank(data.message)) {
				alert(jqXHR.responseJSON.message);
			}else {
				_replace(data);
			}

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.\n" + jqXHR);
		});
	}


	// 건수 클릭시 해당 현황구분
	$(".cnt").off().on("click", function() {
		
		var lastIdChar = '';
		var lastChnnlIdChar = '';
		
		if (!$(this).hasClass('planning-display')) {
			var tabDivId   = $(this).closest(".tab-content").prop("id");
			lastIdChar = tabDivId.substr(tabDivId.length-1, 1);
		}
		if ($(this).hasClass('product-status')) {
			var chnnlTabId = $(this).parents('ul').siblings('.channel-list').find('input[id^=rdoChannel]:checked').prop('id');
			lastChnnlIdChar = chnnlTabId.substr(chnnlTabId.length-1, 1);
		}

		_bodashboard.moveMore(this.name, lastIdChar, lastChnnlIdChar);

		return false;
	});
	
//	메뉴권한 접근 여부 체크
//	_bodashboard.checkAuthMenu = function(url) {
//		var isAuthForMenu = false;
//		
//		if(!abc.text.isBlank(url)) {
//			menuUrl = url.split("?");
//			
//			for(var i=0; i<userMenuList.length; i++) {
//				if(menuUrl[0] === userMenuList[i].menuUrl) {
//					isAuthForMenu = true;
//				}
//			}
//		}
//		
//		return isAuthForMenu;
//	}

	// 더보기 이동 moveMore('스위치문 케이스 명'(필수), '현황 탭 index'(탭 존재여부에 따라 필요), '채널 탭 index')
	_bodashboard.moveMore = function(target, index, chnnlIndex) {
		var url = "/";
		
		if(abc.text.isBlank(target)) {
			alert("해당하는 경로가 없습니다.");
			return false;
		}
		
		switch(target) {
			case "inquiryCnt" :	// 1:1 문의 URL
				url = '/board/inquiry?fromDash=Y&tabIdx='+index;
				break;

			case "coworkCnt" :	// 협력게시판 URL
				url = '/vendor/cowork/?fromDash=Y&tabIdx='+index;
				break;

			case "contactCnt" :	// 입점/제휴 URL
				url = '/vendor/inquiry?fromDash=Y&tabIdx='+index;
				break;

			case "bulkBuyCnt" : // 단체주문 URL
				url = '/board/bulkbuy-inquiry?fromDash=Y&tabIdx='+index;
				break;

			case "prdtQnaCnt" : // 상품Q&A URL
				url = '/product/inquiry?mmnyPrdtYn='+ (index != 1 ? (index == 2 ? 'Y' : 'N') : '') + '&aswrStatCode='+abc.consts.ASWR_STAT_CODE_NO_ASWR;
				break;

			case "reviewCnt" : 	// 상품후기 URL
				url = '/product/review?mmnyPrdtYn='+ (index != 1 ? (index == 2 ? 'Y' : 'N') : '') + '&aswrStatCode='+abc.consts.ASWR_STAT_CODE_NO_ASWR;
				break;

			case "voiceCnt" : // 고객의 소리 URL
				url = '/board/voiceofcustomer?fromDash=Y&tabIdx='+index;
				break;
				
			case "clmReturnCnt" : // 반품접수/진행중 URL
				url = '/claim/claim?clmGbnCode=10002'
				break;
				
			case "clmExchangeCnt" : // 교환접수/진행중 URL
				url = '/claim/claim?clmGbnCode=10001'
				break;
				
			case "mmnyYclmReturnCnt" : // 자사 반품접수/진행중 URL
				url = '/claim/claim?clmGbnCode=10002&mmnyPrdtYn=Y'
				break;
				
			case "mmnyYclmExchangeCnt" : // 자사 교환접수/진행중 URL
				url = '/claim/claim?clmGbnCode=10001&mmnyPrdtYn=Y'
				break;
				
			case "mmnyNclmReturnCnt" : // 업체 반품접수/진행중 URL
				url = '/claim/claim?clmGbnCode=10002&mmnyPrdtYn=N'
				break;
				
			case "mmnyNclmExchangeCnt" : // 업체 교환접수/진행중 URL
				url = '/claim/claim?clmGbnCode=10001&mmnyPrdtYn=N'
				break;
				
			case "cancelSellRequestCnt" : // 판매취소요청 URL
				url = '/delivery/vendor/delivery-order-vendor-cancel?fromDash=Y'
				break;
				
			case "asCnt" : // AS접수/진행중 URL
				url = '/afterservice/afterservice?fromDash=Y'
				break;			
				
			case "paymentCompleteAll" : // 결제완료[전체]
				url = '/order?fromDash=Y&tabIdx='+index+"&orderPrdtStatCode=10002"
				break;
				
			case "paymentComplete" : // 결제완료[자사]
				url = '/order?fromDash=Y&tabIdx='+index+"&orderPrdtStatCode=10002"
				break;
				
			case "paymentCompleteVndr" : // 결제완료[입점]
				url = '/order?fromDash=Y&tabIdx='+index+"&orderPrdtStatCode=10002"
				break;
				
			case "productIngAll" : // 상품준비중[전체]
				url = '/order?fromDash=Y&tabIdx='+index+"&orderPrdtStatCode=10003"
				break;
				
			case "productIng" : // 상품준비중[자사]
				url = '/order?fromDash=Y&tabIdx='+index+"&orderPrdtStatCode=10003"
				break;
				
			case "productIngVndr" : // 상품준비중[입점]
				url = '/order?fromDash=Y&tabIdx='+index+"&orderPrdtStatCode=10003"
				break;
			
			case "deveryIngAll" : // 배송중[전체]
				url = '/delivery/delivery-order?fromDash=Y&tabIdx='+index + "&dlvyStatCode=10003"
				break;
				
			case "deveryIng" : // 배송중[자사]
				url = '/delivery/delivery-order?fromDash=Y&tabIdx='+index + "&dlvyStatCode=10003"
				break;
				
			case "deveryIngVndr" : // 배송중[입점]
				url = '/delivery/delivery-order?fromDash=Y&tabIdx='+index + "&dlvyStatCode=10003"
				break;
			
			case "deveryNotAll" : // 미출고 현황
				url = '/delivery/delivery-order-not'
				break;
			
			case "onSale" : // 상품 판매/전시중
				url = '/product/product?mmnyPrdtYn='+(index != 1 ? (index == 2 ? 'Y' : 'N') : '')
						+'&dispYn=Y&chnnlNo='+(chnnlIndex != 1 ? (chnnlIndex == 2 ? '10000' : chnnlIndex == 3 ? '10001' : chnnlIndex == 4 ? '10002' : '10003') : '')
						+'&sellStatCode=10001&productSearchStartDtm='+$('#productSearchStartDtm').text()+'&productSearchEndDtm='+$('#productSearchEndDtm').text();
				break;
			
			case "outOfStock" : // 상품 일시품절
				url = '/product/product?mmnyPrdtYn='+(index != 1 ? (index == 2 ? 'Y' : 'N') : '')
						+'&chnnlNo='+(chnnlIndex != 1 ? (chnnlIndex == 2 ? '10000' : chnnlIndex == 3 ? '10001' : chnnlIndex == 4 ? '10002' : '10003') : '')
						+'&sellStatCode=10002&productSearchStartDtm='+$('#productSearchStartDtm').text()+'&productSearchEndDtm='+$('#productSearchEndDtm').text();;
				break;
				
			case "request" : // 상품 승인요청
				url = '/product/vndrApproval?chnnlNo='+(chnnlIndex != 1 ? (chnnlIndex == 2 ? '10000' : chnnlIndex == 3 ? '10001' : chnnlIndex == 4 ? '10002' : '10003') : '')
					+ '&aprvStatCode=10001';
				break;
				
			case "refuse" : // 상품 승인반려
				url = '/product/vndrApproval?chnnlNo='+(chnnlIndex != 1 ? (chnnlIndex == 2 ? '10000' : chnnlIndex == 3 ? '10001' : chnnlIndex == 4 ? '10002' : '10003') : '')
					+ '&aprvStatCode=10003';
				break;
			
			case "plndpOngoing" : // 진행중인 기획전
				url = '/promotion/planning-display?progress=O&dispYn=Y&useYn=Y';
				break;
				
			case "plndpRequest" : // 승인요청중인 기획전
				url = '/promotion/planning-display/approval?plndpStatCode=10001';
				break;
				
			case "plndpRefuse" : // 승인반려 기획전
				url = '/promotion/planning-display/approval?plndpStatCode=10002';
				break;
			case "cntDashBoardOrder" : // 주문건수, 판매금액
				url = '/order?fromDash=Y&tabIdx='+index ;
				break;
				
				
			default :
				alert("해당하는 경로가 없습니다.");
		}
		
		
		// 더보기 이동
		location.href = url;
	}

	// 관리자 공지사항 목록 더보기 팝업
	$("#noticeMoreBtn").off().on("click", function() {
		var url = "/bo/dashboard/read-list-pop";

		abc.open.popup({
			url 	:	url,
			winname :	'admin-notice-list-pop',
			title 	:	'boAdminNoticeList',
			width 	:	1200,
			height	:	720
		});
		
		return false;
	});

	// 관리자 공지사항 상세 팝업
	$(".adminNoticeTitle").off().on("click", function() {
		var _url = "/bo/dashboard/read-detail-pop";
		var _params = {};
		
		_params.adminNotcSeq = this.name;

		abc.open.popup({
			winname :	"adminNoticeDetailPop",
			url 	:	_url,
			width 	:	500,
			height	:	600,
			params	:	_params
		});
	});
	
	// 주문/배송 개수 영역의 '새로고침' 클릭
	$("#btnOrderRefresh").click(function(){
		_bodashboard.refreshAjax("orderRefresh", function(data) {
			var newData = data.orderDlvyCountInfo;

			$("a[name='paymentCompleteAll']").text(newData.COMPLETE_CNT + newData.VNDR_COMPLETE_CNT);	// 전체 결제완료
			$("a[name='productIngAll']").text(newData.PRODUCT_PREPARATION_CNT + newData.VNDR_PRODUCT_PREPARATION_CNT);	// 전체 상품준비중
			$("a[name='deveryIngAll']").text(newData.DLVYING_PICKUP_READY_CNT + newData.VNDR_DLVYING_PICKUP_READY_CNT);	// 전체 배송중
			$("a[name='deveryNotAll']").text(newData.DELAYED_DELIVERY_CNT + newData.VNDR_DELAYED_DELIVERY_CNT);	// 전체 배송지연
			$("a[name='paymentComplete']").text(newData.COMPLETE_CNT);	// 자사 결제완료
			$("a[name='productIng']").text(newData.PRODUCT_PREPARATION_CNT);	// 자사 상품준비중
			$("a[name='deveryIng']").text(newData.DLVYING_PICKUP_READY_CNT);	// 자사 배송중
			$("a[name='paymentCompleteVndr']").text(newData.VNDR_COMPLETE_CNT);	// 입점 결제완료
			$("a[name='productIngVndr']").text(newData.VNDR_PRODUCT_PREPARATION_CNT);	// 입점 상품준비중
			$("a[name='deveryIngVndr']").text(newData.VNDR_DLVYING_PICKUP_READY_CNT);	// 입점 배송중
		});
	});
	
	// 클레임 현황 새로고침 ajax 통신
	_bodashboard.refreshClaimAjax = function() {
		$.ajax({
			type : "get",
			url  : "/bo/dashboard/claim/refresh",
			data : {}
		}).done(function(data, textStatus, jqXHR) {

			if(!abc.text.allNull(data)) {
				//console.log(data);
				$("#tabContent21 a[name='clmReturnCnt']").text( abc.text.isMakeComma(data.clmReturnCnt + data.vndrClmReturnCnt) );
				$("#tabContent21 a[name='clmExchangeCnt']").text( abc.text.isMakeComma(data.clmExchangeCnt + data.vndrClmExchangeCnt) );
				$("#tabContent21 a[name='asCnt']").text( abc.text.isMakeComma(data.asCnt) );
				$("#tabContent21 a[name='cancelSellRequestCnt']").text( abc.text.isMakeComma(data.cancelSellRequestCnt) );
				
				$("#tabContent22 a[name='mmnyYclmReturnCnt']").text( abc.text.isMakeComma(data.clmReturnCnt) );
				$("#tabContent22 a[name='mmnyYclmExchangeCnt']").text( abc.text.isMakeComma(data.clmExchangeCnt) );
				$("#tabContent22 a[name='asCnt']").text( abc.text.isMakeComma(data.asCnt) );
				
				$("#tabContent23 a[name='mmnyNclmReturnCnt']").text( abc.text.isMakeComma(data.vndrClmReturnCnt) );
				$("#tabContent23 a[name='mmnyNclmExchangeCnt']").text( abc.text.isMakeComma(data.vndrClmExchangeCnt) );
				$("#tabContent23 a[name='cancelSellRequestCnt']").text( abc.text.isMakeComma(data.cancelSellRequestCnt) );
			}

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다.\n" + jqXHR);
		});
	}
	
	$("#btnClaimRefresh").click(function(){
		_bodashboard.refreshClaimAjax();
	});
	
})();