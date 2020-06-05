/**
 * PO 대시보드.
 */
(function(){

	var _poDashboard = abc.object.createNestedObject(window,"abc.biz.board.podashboard");

	_poDashboard.event = function(){

		// 더보기
		_poDashboard.moreView();
		// 상세팝업
		_poDashboard.detailViewPop();
		// 새로고침
		_poDashboard.refreshBtn();
		// 관리자 공지사항 제목이 줄을 넘어가는 경우 ...으로 표시.
		$('.noticeTitle').css('width', '400px')
						 .css('overflow', 'hidden')
						 .css('text-overflow', 'ellipsis')
						 .css('white-space', 'nowrap');
	};
	
	// 더보기 함수.
	_poDashboard.moreView = function(){
		//건수 클릭 
		$(".cnt").click(function(e){
			var targetId = $(this).attr('id');
			_poDashboard.aCntClick(targetId);
		});
		

		// 관리자 공지사항 목록 더보기 팝업.
		$('#adNoticeMoreView').off().on('click', function(){
			var url = '/po/dashboard/read-list-pop';

			abc.open.popup({
				url 	:	url,
				winname :	'admin-notice-read-list-pop',
				title 	:	'poAdminNoticeList',
				width 	:	1200,
				height	:	720
			});
		});
		
		$('#vendorInfo').off().on('click', function() {
			abc.vendorInfoPop($('#vndrNo').val());
			
		});
	}
	
	
	//건수 클릭
	_poDashboard.aCntClick = function(targetId){
		var _url = "/";
		
		if(abc.text.isBlank(targetId)) {
			alert("해당하는 경로가 없습니다.");
			return false;
		}
		switch(targetId){
			case "paymentCompleteVndr":	//결제완료 주문
				_url = '/delivery/vendor/delivery-order-vendor?fromDash=Y&selectBoxIndex=0';
				break;
			case "productIngVndr":		//상품 준비중 주문
				_url = '/delivery/vendor/delivery-order-vendor?fromDash=Y&selectBoxIndex=1';
				break;
			case "deveryIngVndr":		//배송중 주문
				_url = '/delivery/vendor/delivery-order-vendor?fromDash=Y&selectBoxIndex=2';
				break;
			case "deveryNotVndr":		//배송지연 주문
				_url = '/delivery/delivery-order-not?fromDash=Y';
				break;
			case "claimReceipt":		//클레임 접수
				_url = '/delivery/vendor/delivery-order-vendor-claim?fromDash=Y';
				break;
			case "recovering":		//회수중
				_url = '/delivery/vendor/delivery-order-vendor-claim?fromDash=Y';
				break;
			case "exchangeDlvyIng":		//교환품 배옷줌
				_url = '/delivery/vendor/delivery-order-vendor-claim?fromDash=Y';
				break;
			case "claimComplete":		//클레임 완료
				_url = '/delivery/vendor/delivery-order-vendor-claim?fromDash=Y';
				break;
			case "useplanningMore": // 진행중인 기획전
				_url = '/promotion/planning-display?progress=O&dispYn=Y&useYn=Y';
				break;
			case "usereadyplanningMore": // 승인요청중인 기획전
				_url = '/promotion/planning-display?plndpStatCode=10001';
				break;
			case "notuseplanningMore": // 승인반려 기획전
				_url = '/promotion/planning-display?plndpStatCode=10002';
				break;
			case "productsellMore": // 상품 판매/전시중
				_url = '/product/product?sellStatCode=10001&dispYn=Y';
				break;
			case "productsoldMore": // 상품 일시품절
				_url = '/product/product?sellStatCode=10002';
				break;
			case "productAprvMore": // 상품 승인요청
				_url = '/product/approval?aprvStatCode=10001';
				break;
			case "productDenyAprvMore": // 상품 승인반려
				_url = '/product/approval?aprvStatCode=10003';
				break;
			case "vndrProductInquiryCountMore": // 상품 Q&A
				_url = '/product/inquiry?aswrStatCode=10000';
				break;
			case "inquiryMoreView": // 1:1문의
				_url = '/vendor/individual-inquiry?fromDash=Y';
				break;
			case "coworkMoreView": // 협력 게시판
				_url = '/vendor/cowork?fromDash=Y';
				break;
			case "saleStatusOrderCnt": // 주문 건수 
				_url = '/delivery/vendor/delivery-order-vendor?fromDash=Y';
				break;
			case "saleStatusOrderAmt": // 주문 금액
				_url = '/delivery/vendor/delivery-order-vendor?fromDash=Y';
				break;
		}
		location.href = _url;
	}
	
	
	// 협력게시판 새로고침
	_poDashboard.refreshCowork = function(){
		$.ajax({
			url : '/po/dashboard/refreshCoworkBoard',
			method : 'post'
		}).done(function(data){
			$('#coworkCount').text(data.dashboardCount);
			_poDashboard.event();
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert('협력 게시판 대시보드에 에러가 발생했습니다. : '+jqXHR.responseJSON.message);
		});
	}
	
	// 클레임 새로고침
	_poDashboard.refreshClaim = function(){
		$.ajax({
			url : '/po/dashboard/refreshClaimBoard',
			method : 'get'
		}).done(function(data){
			$("#claimReceipt").text(data.orderPrdtStatCodeClaim);
			$("#recovering").text(data.orderPrdtStatCodeDeliveryReturn);
			$("#exchangeDlvyIng").text(data.orderPrdtStatCodeExchangeDelivery);
			$("#claimComplete").text(data.orderPrdtStatCodeClaimComplete);
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert('협력 게시판 대시보드에 에러가 발생했습니다. : '+jqXHR.responseJSON.message);
		});
	}

	// 주문/배송 현황 새로고침
	_poDashboard.refreshOrder = function(){
		$.ajax({
			url : '/po/dashboard/refreshOrderBoard',
			method : 'get'
		}).done(function(data){
			if(data != null){
				$("#paymentCompleteVndr").text(data.VNDR_COMPLETE_CNT);
				$("#productIngVndr").text(data.VNDR_PRODUCT_PREPARATION_CNT);
				$("#deveryIngVndr").text(data.VNDR_DLVYING_PICKUP_READY_CNT);
				$("#deveryNotVndr").text(data.VNDR_DELAYED_DELIVERY_CNT);
			}
			
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert('협력 게시판 대시보드에 에러가 발생했습니다. : '+jqXHR.responseJSON.message);
		});
	}


	// 전시 현황 대시보드 새로고침
	_poDashboard.refreshPlanning = function(){
		$.ajax({
			url : '/po/dashboard/refreshplanningDisplay',
			method : 'post'
		}).done(function(data){
			$('#useplanning').text(data.dashboardCount);
			_poDashboard.event();
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert('전시 현황 대시보드에 에러가 발생했습니다. : '+jqXHR.responseJSON.message);
		});
	}

	// 상품QNA 새로고침
	_poDashboard.refreshProductQnA = function(){
		$.ajax({
			url : '/po/dashboard/refreshProductQnA',
			method : 'post'
		}).done(function(data){
			$('#vndrProductInquiryCount').text(data.dashboardCount);
			_poDashboard.event();
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert('상품 QnA 게시판 대시보드에 에러가 발생했습니다. : '+jqXHR.responseJSON.message);
		});
	}

	// 1:1문의사항 게시판 새로고침
	_poDashboard.refreshInquiry = function(){
		$.ajax({
			url : '/po/dashboard/refreshInquiryBoard',
			method : 'post'
		}).done(function(data){
			$('#inquiryCount').text(data.dashboardCount);
			_poDashboard.event();
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert('1:1 문의사항 대시보드에 에러가 발생했습니다. : '+jqXHR.responseJSON.message);
		});
	}

	// 상품 현황 대시보드 새로고침
	_poDashboard.refreshProduct = function() {
		$.ajax({
			url : '/po/dashboard/refreshProduct',
			method : 'post'
		}).done(function(data){
			$('#productsell').text(data.dashboardCount);
			_poDashboard.event();
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert('상품현황 대시보드에 에러가 발생했습니다. : '+jqXHR.responseJSON.message);
		});
	}

	// 관리자 공지사항 새로고침.
	_poDashboard.refreshAdNotice = function(){
		var arr = '';

		$.ajax({
			url : '/po/dashboard/refreshAdNoticeBoard',
			method : 'post'
		}).done(function(data){
			if(data.dashboardCount == 0){
				arr += '<tr><td class="text-center" colspan="2"> 공지사항이 없습니다.</td></tr>';
			} else {
				$.each(data.dashboardList, function(index,data){
					arr += '<tr><td><a href="javascript:void(0);" name="'+data.adminNotcSeq+'" class="detailViewadNotice" title="제목">'
						+ data.notcTitleText+'</a></td>'
						+ '<td class="text-center">'+new Date(data.rgstDtm).format()+'</td></tr>';
				});
			}
			$('#adNoticeTbody').html(arr);
			_poDashboard.event();

		}).fail(function(jqXHR, textStatus, errorThrown){
			alert('관리자 공지사항 대시보드에 에러가 발생했습니다. : '+jqXHR.responseJSON.message);
		});
	}

	//새로고침 버튼 함수.
	_poDashboard.refreshBtn = function(){
		$('#aswrRefreshBtn').off().on('click', function(){
			_poDashboard.refreshCowork();
			_poDashboard.refreshInquiry();
			_poDashboard.refreshProductQnA();
		});

		$('#adNoticeRefreshBtn').off().on('click', function(){
			_poDashboard.refreshAdNotice();
		});

		$('#planningDisplaybtn').off().on('click', function(){
			_poDashboard.refreshPlanning();
		});

		$('#productRefreshBtn').off().on('click', function(){
			_poDashboard.refreshProduct();
		});
		
		$("#poClaimRefresh").click(function(){
			_poDashboard.refreshClaim();
		});

		$("#btnOrderRefresh").click(function(){
			_poDashboard.refreshOrder();
		});
	}

	//상세팝업 함수.
	_poDashboard.detailViewPop = function(){

		// 관리자 공지사항 상세 팝업.
		$('.detailViewadNotice').off().on('click', function(){
			var url = '/po/dashboard/read-detail-pop';
			
			var _params = {};
			_params.adminNotcSeq = this.name;

			abc.open.popup({
				url 	:	url,
				winname :	'admin-notice-detail-pop',
				title 	:	'adminNoticeDetail',
				width 	:	800,
				height	:	803,
				params	:	_params
			});
		});
	}
})();



