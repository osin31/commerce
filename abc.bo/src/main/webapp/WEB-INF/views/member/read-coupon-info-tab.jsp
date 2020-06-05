<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 초기 값 세팅 --%>
		abc.biz.member.member.coupon.memberNo = "${memberNo}";
		abc.biz.member.member.coupon.codeCombo = ${codeCombo};
		
		<%-- 회원 쿠폰 그리드 세팅 --%>
		abc.biz.member.member.coupon.initCouponSheet();
		
		<%-- 회원 쿠폰 리스트 조회 --%>
		$("#searchBtn").click(function(){
			abc.biz.member.member.coupon.memberCouponDoAction('search');
		});
		
		<%-- 채널 전체 체크 --%> 
		$("#chkChannelAll").click(function(){
			if($(this).is(":checked")){
				$("input[name=chnnlNos]").prop("checked",true);
			} else {
				$("input[name=chnnlNos]").prop("checked",false);
			}
		});
		
		<%-- 캘린더 버튼 설정(일) --%>
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		<%-- 캘린더 버튼 설정(주)--%>
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		<%-- 캘린더 버튼 설정(월) --%>
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});
		
		<%-- 캘린더 버튼 설정(전체) --%>
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
		
		$("#couponBtn").click(function(){
			var params = {};
			params.callback = 'abc.biz.member.member.coupon.insertMemberCoupon';
			params.multiple = false;
			
			var url = "/promotion/coupon/popup";
			abc.open.popup({
				url 	:	url,
				method	: 	"get",
				title 	:	"쿠폰 검색",
				width 	:	'1240',
				height	:	'950',
				params	:	params
			});
		})
		
		$("a[name^=per1Month]").trigger("click");
		
		<%-- 초기화. --%>
		$("#resetBtn").click(function(){
			$('#frmSearch')[0].reset();
			$("#chkUse01").trigger("click");
			$("a[name^=per1Month]").trigger("click");
		});
		
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
		});
		
		//체크박스 이벤트
		abc.biz.member.member.coupon.checkBoxAll({allId: '#chkUse01', itemsClass: '[name=cpnUseYns]'});
		$("#chkUse01").trigger("click");
		
		<%-- 회웑벙보 탭에서 이동시 세탕 --%>
		abc.biz.member.memberDetail.moveTab("checkbox");
		
		$("#searchBtn").trigger('click');
	});
	
	
	
 	<%-- 그리드 Click 이벤트 --%> 
	function couponSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( couponSheet.ColSaveName(Col) == "cpnName" && Value != "" ) {
				var url = "/promotion/coupon/detail?cpnNo=" + couponSheet.GetCellValue(Row, "cpnNo");
				window.open(url);
				
				/* var params = {}
				params.memberNo = couponSheet.GetCellValue(Row, "cpnNo");
				
				abc.open.popup({
					winname :	"detailPop",
					url 	:	url,
					width 	:	1480,
					height	:	985,
					params	:	params
				}); */
			}
		}
	}
	
	//그리드 버튼 클릭 이벤트
	function couponSheet_OnButtonClick(row, col) {
		if (row != 0){
			if ( couponSheet.ColSaveName(col) == "cpnManage") {
				var cpnMemberData = new Object();
				var useYnVal = couponSheet.GetCellValue(row, "useYnVal");
				
				cpnMemberData.cpnNo = couponSheet.GetCellValue(row, "cpnNo");
				cpnMemberData.memberNo = couponSheet.GetCellValue(row, "memberNo");
				cpnMemberData.holdCpnSeq = couponSheet.GetCellValue(row, "holdCpnSeq");
				cpnMemberData.useYnVal = useYnVal;
				
				if(useYnVal == 'POSSIBLE_STOP') {
					cpnMemberData.cpnStatCode = '10004'
				} else if(useYnVal == 'RE_ISSUE') {
					cpnMemberData.cpnStatCode = '10001'
				} else if(useYnVal == 'UNPAUSE') {
					cpnMemberData.cpnStatCode = '10000'
				}
				
				$.ajax({
					type :"post",
					url : "/promotion/coupon/status/modify",
					data : cpnMemberData,
					async : false
				}).done(function(data){
					alert('저장 되었습니다.');
					abc.memberDetailTabReload();
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert(e.message);
					console.log(e);
				});
			}
		}
	}
	
</script>
<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">쿠폰 현황</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-col -->
<table class="tbl-col">
	<caption>쿠폰 현황</caption>
	<colgroup>
		<col>
		<col>
		<col>
		<col>
		<col><!-- 190805 추가 | 사용중지 필드 추가 -->
	</colgroup>
	<thead>
		<tr>
			<th scope="col">총 보유쿠폰</th>
			<th scope="col">사용가능 쿠폰</th>
			<th scope="col">사용 완료쿠폰</th>
			<th scope="col">기간만료</th>
			<th scope="col">사용중지</th><!-- 190805 추가 | 사용중지 필드 추가 -->
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="text-center"><c:out value="${couponData.getTCnt()}"/>건</td>
			<td class="text-center"><c:out value="${couponData.getACnt()}"/>건</td>
			<td class="text-center"><c:out value="${couponData.getCCnt()}"/>건</td>
			<td class="text-center"><c:out value="${couponData.getECnt()}"/>건</td>
			<td class="text-center"><c:out value="${couponData.getNCnt()}"/>건</td><!-- 190805 추가 | 사용중지 필드 추가 -->
		</tr>
	</tbody>
</table>
<!-- E : tbl-col -->

<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">쿠폰사용내역 검색</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : search-wrap -->
<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
<div class="search-wrap">
	<div class="search-inner">
		<table class="tbl-search">
			<caption>쿠폰사용내역 검색</caption>
			<colgroup>
				<col style="width:15%;">
				<col>
				<col style="width:79px;">
				<col style="width:15%;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">쿠폰유형</th>
					<td class="input">
						<select name="cpnTypeCode" class="ui-sel" title="쿠폰유형 선택">
							<option value="">전체</option>
							<c:forEach var="couponList" items="${codeList.CPN_TYPE_CODE}">
								<option value="${couponList.codeDtlNo}">${couponList.codeDtlName}</option>
							</c:forEach>
						</select>
					</td>
					<td></td>
					<th scope="row">채널</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="chkChannelAll" type="checkbox" checked="checked">
									<label for="chkChannelAll">전체</label>
								</span>
							</li>
							<c:forEach var="chanList" items="${chanList}" varStatus="status">
							<li>
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="chkChannel${status.count}" name="chnnlNos" type="checkbox" value="${chanList.chnnlNo}" checked="checked">
									<label for="chkChannel${status.count}">${chanList.chnnlName} </label>
								</span>
							</li>
							</c:forEach>
						</ul>
						<!-- E : ip-box-list -->
					</td>
				</tr>
				<tr>
					<th scope="row">사용여부</th>
					<td class="input">
						<!-- S : ip-box-list -->
						<ul class="ip-box-list">
							<li>
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="checkbox" id="chkUse01" vlaue="">
									<label for="chkUse01">전체</label>
								</span>
							</li>
							<li>
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="checkbox" name="cpnUseYns" id="chkUse02" value="Y">
									<label for="chkUse02">사용완료</label>
								</span>
							</li>
							<li>
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="checkbox" name="cpnUseYns" id="chkUse03" value="N">
									<label for="chkUse03">미사용</label>
								</span>
							</li>
							<li>
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="checkbox" name="cpnUseYns" id="chkUse04" value="S">
									<label for="chkUse04">사용중지</label>
								</span>
							</li>
							<li>
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="checkbox" name="cpnUseYns" id="chkUse05" value="E">
									<label for="chkUse05">기간만료</label>
								</span>
							</li>
						</ul>
						<!-- E : ip-box-list -->
					</td>
					<td></td>
					<th scope="row">검색어</th>
					<td class="input">
						<div class="opt-keyword-box">
							<select class="ui-sel" title="검색어 타입 선택" id="searchKey" name="searchKey">
								<option value="">선택</option>
								<option value="couponNo">쿠폰 번호</option>
								<option value="couponName">쿠폰명</option>
							</select>
							<input type="text" class="ui-input" id="searchValue" name="searchValue" placeholder="검색어 입력" title="검색어 입력">
						</div>
						<!-- E : opt-keyword-box -->
					</td>
				</tr>
				<tr>
					<th scope="row">기간검색</th>
					<td class="input" colspan="4">
						<!-- S : term-date-wrap -->
						<span class="term-date-wrap">
							<select class="ui-sel" title="기간 선택" id="searchDtm" name="searchDtm">
								<option value="cpnIssueDtm">쿠폰발급일</option>
								<option value="cpnUseDtm">쿠폰사용일</option>
							</select>
							<span class="date-box">
								<input type="text" data-role="datepicker" id="fromDate" name="fromDate" class="ui-cal js-ui-cal" title="시작일 선택">
							</span>
							<span class="text">~</span>
							<span class="date-box">
								<input type="text" data-role="datepicker" id="toDate" name="toDate" class="ui-cal js-ui-cal" title="종료일 선택">
							</span>
							<span class="btn-group">
								<a href="javascript:void(0);" name="perToday" class="btn-sm btn-func">오늘</a>
								<a href="javascript:void(0);" name="perWeek" class="btn-sm btn-func">일주일</a>
								<a href="javascript:void(0);" name="per1Month" class="btn-sm btn-func">한달</a>
								<a href="javascript:void(0);" name="perYear" class="btn-sm btn-func">1년</a>
							</span>
						</span>
						<!-- E : term-date-wrap -->
					</td>
				</tr>
			</tbody>
		</table>
		<div class="confirm-box">
			<div class="fl">
				<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
			</div>
			<div class="fr">
				<a href="#" id="searchBtn" class="btn-normal btn-func">검색</a>
			</div>
		</div>
	</div>
	<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
</div>
</form>
<!-- E : search-wrap -->

<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">쿠폰 사용내역</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-controller -->
<div class="tbl-controller">
	<div class="fl">
		<span class="opt-group">
			<label class="title" for="selView04">목록개수</label>
			<select class="ui-sel" id="pageCount">
				<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
			</select>
		</span>
	</div>
	<div class="fr">
	
		<%-- 
			 2020.05.13 :  CS 고객센터는 이 두아이디만 쿠폰발급 버튼 노출
			  	- csmizie109 / cs_j0202j70
		--%>
		<c:set var="substringNoCpnBtnLoinId" value="${fn:substring(noCpnBtnLoinId,0,2)}" />
		<c:choose>
			<c:when test="${substringNoCpnBtnLoinId eq 'cs'}">
				<c:if test="${noCpnBtnLoinId eq 'csmizie109' or noCpnBtnLoinId eq 'cs_j0202j70'}">
					<a href="javascript:void(0);" id="couponBtn" class="btn-sm btn-link">쿠폰발급</a>
				</c:if>
			</c:when>
			<c:otherwise>
				<a href="javascript:void(0);" id="couponBtn" class="btn-sm btn-link">쿠폰발급</a>
			</c:otherwise>
		</c:choose>
	
	</div>
</div>
<!-- E : tbl-controller -->

<!-- S : ibsheet-wrap -->
<div class="ibsheet-wrap">
	<div id="couponGrid" style="width:100%; height:429px;">
		
	</div>
</div>
<!-- E : ibsheet-wrap -->

<script src="/static/common/js/biz/member/abcmart.member.member.coupon.js<%=_DP_REV%>">
</script>