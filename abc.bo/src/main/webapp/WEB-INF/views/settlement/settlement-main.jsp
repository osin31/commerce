<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<jsp:useBean id="now" class="java.util.Date"/>
<script type="text/javascript">

	<%-- 그리드 Click 이벤트  및 재접수 버튼 이벤트 --%>
	function settleMentSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		abc.biz.settlement.main.settleMentSheetOnClick(Row, Col, Value, CellX, CellY, CellW, CellH);
	}
	
	<%-- 제정산  --%>
	function settleMentSheet_OnRowSearchEnd(row, col, value){
		var excclcDcsnYn = settleMentSheet.GetRowData(row).excclcDcsnYn;
		console.log(excclcDcsnYn);
		if(excclcDcsnYn == 'N'){
			settleMentSheet.InitCellProperty(row,11, {'Type': 'Button'});
			settleMentSheet.SetCellValue(row, "excclcDcsnYn", "재정산");
			settleMentSheet.SetCellEditable(row,"excclcDcsnYn",1);
		}else{
			settleMentSheet.InitCellProperty(row,11, {'Type': 'Text'});
			settleMentSheet.SetCellText(row, "excclcDcsnYn","-");	
			settleMentSheet.SetCellEditable(row,"excclcDcsnYn",0);
		}
	}
	

</script>
	<form id="search-form">
	<input type="hidden" id="excclcYm" name="excclcYm" value="">
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">입점사 정산관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>매출/정산/통계</li>
								<li>업체 정산관리</li>
								<li>입점사 정산관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">입점사 정산관리</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>입점사 정산관리</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">정산월</th>
									<td class="input">
										<span class="ip-text-box">
											<select class="ui-sel" id="year" name="year" title="년도 선택">
												<c:forEach var="i" begin="2019" end="2029" step="1" >
													<option value="${i}" <c:if test="${i == (now.year + 1900)}">selected</c:if> >${i}</option>
												</c:forEach>
											</select>
											<select class="ui-sel" id="month" name="month" title="월 선택">
												<c:forEach var="i" begin="1" end="12" step="1" >
													<option value="<fmt:formatNumber value="${i}" minIntegerDigits="2"/>" <c:if test="${i == now.month + 1 }">selected</c:if> > ${i}</option>
												</c:forEach>
											</select>
											<button type="button" id="preMonth" class="btn-sm btn-func">전월</button>
											<button type="button" id="currMonth" class="btn-sm btn-func">당월</button>
										</span>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0);" class="btn-normal btn-func" id="settlementmainSearch">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">목록</h3>
					</div>
				</div>
				<!-- E : content-header -->
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>목록</caption>
					<colgroup>
						<col style="width: 170px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">입점사 최종정산금액  합계</th>
							<td id="totalAmt"></td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">목록</h3>
					</div>
				</div>
				<!-- E : content-header -->
				<div class="tbl-controller">
					<div class="fl">
						<!-- S : opt-group -->
						<span class="opt-group">
							<label class="title" for="selTermsModule">목록개수</label>
							<select id="pageCount" class="ui-sel">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
<!-- 					<div class="fr"> -->
<!-- 						<a href="#" class="btn-sm btn-func">엑셀 다운로드</a> -->
<!-- 					</div> -->
				</div>

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div style="width:100%; height:429px;" id="settleMentGrid"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->
		</form>
<script type="text/javascript" src="/static/common/js/biz/settlement/abcmart.settlement.main.js<%=_DP_REV%>"></script>	
<%@include file="/WEB-INF/views/common/footer.jsp"%>
