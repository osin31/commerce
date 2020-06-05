<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<jsp:useBean id="now" class="java.util.Date"/>
<script type="text/javascript">
$(function() {
	<%-- 회원 관리자 메모 저장 --%>
	$("#memoSaveBtn").click(function(){
		abc.biz.settlement.month.main.createMemo();
	});
	
	
	
	<%-- 회원 관리자 메모 삭제 --%>
	$(document).on("click", ".btn-msg-list-del", function(event){
		var idx = $(this).parent().parent().parent("li").index();
		var classNm = $(this).parent().parent().attr("class");
		
		abc.biz.settlement.month.main.removeMemo(idx, classNm);
	});
	
});

// 패널티 조정 금액, 정산 조정 금액 
function monthSheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
	  if(StCode =="200"){
		  alert("변경 처리되었습니다.");
		  abc.biz.settlement.month.main.getList();
	  }else{
		  alert("변경  처리가 실패되었습니다. \n 다시 시도해주세요.");
	  }
} 



</script>
		<!-- S : container -->
		<div class="container">
			<form id="gForm" name="gForm"  method="post" onsubmit="return false;">
			<input type='hidden' id="excclcSeq" name="excclcSeq" value="${excclcSeq}"/>
			<input type='hidden' id="excclcYm" name="excclcYm" value="${excclcYm}"/>
			<input type='hidden' id='vndrNo' name='vndrNo' value="${vndrNo}"/>
			<input type='hidden' id='vndrName' name='vndrName' value="${vndrName}"/>
			<input type='hidden' id='tabGubun' name='tabGubun' value=""/>
			<input type='hidden' id='salesCnclGbnType' name='salesCnclGbnType' value=""/>
			<input type='hidden' id='excclcDcsnYn' name='excclcDcsnYn' value=""/>
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
						<h3 class="content-title">입점사 정산관리(<span>${vndrName}</span>)</h3>
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
								<col style="width: 79px;">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">입점사</th>
									<td>${vndrName} (${vndrNo})</td>
									<td></td>
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
								<tr>
									<th scope="row">브랜드명</th>
									<td class="input">
										<span class="ip-search-box">
											<input type="hidden" id="brandNo" name="brandNo" value="">
											<input type="text" class="ui-input month" id="brandName" name="brandName" value="" title="검색어 입력" readonly>
											<a href="javascript:void(0);" class="btn-search" id="btnBrand"><i class="ico-search"><span class="offscreen">검색</span></i></a>
										</span>
									</td>
									<td></td>
									<th scope="row">주문/클레임 유형</th>
									<td class="input">
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkdeviceTypeAll" name="chkdeviceTypeAll" type="checkbox" value="all" checked>
													<label for="chkdeviceTypeAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input name="deviceTypeCodeArr" type="checkbox"  id="chkdeviceType01" value="order"  checked/>
													<label for="chkdeviceType01">주문</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input name="deviceTypeCodeArr" type="checkbox"  id="chkdeviceType02" value="return"  checked/>
													<label for="chkdeviceType02">반품</label>
												</span>
											</li>
										</ul>
									</td>
								</tr>
								<tr>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel month" id="orderNoSelect" title="검색어 타입 선택" >
												<option value="">선택</option>
												<option value="orderNo">주문번호</option>
												<option value="prdtNo">상품번호</option>
											</select>
											<input type="text" class="ui-input month" name="orderNo" id="orderNoInp" placeholder="검색어 입력" title="검색어 입력" >
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">정산확정여부</th>
									<td class="input">
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkCalceAll" type="checkbox" name="chkCalceAll" checked>
													<label for="chkCalceAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input name="chkCalceArr" id="chkCalce01" value="N" type="checkbox" checked/>
													<label for="chkCalce01">정산대기</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input name="chkCalceArr" id="chkCalce02" value="Y" type="checkbox" checked/>
													<label for="chkCalce02">정산확정</label>
												</span>
											</li>
										</ul>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" class="btn-sm btn-func" id="gFormReset"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0);" class="btn-normal btn-func" id="gFormSearch">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->

				<!-- S : tab-wrap -->
				<div class="tab-wrap">
					<ul class="tabs">
						<li class="tab-item"><a href="#tabContent1" id="month" class="tab-link">월 정산</a></li>
						<li class="tab-item"><a href="#tabContent2" id="sellAmt" class="tab-link">판매수수료</a></li>
						<li class="tab-item"><a href="#tabContent3" id="dlvyAmt" class="tab-link">배송비</a></li>
						<li class="tab-item"><a href="#tabContent4" id="promoAmt" class="tab-link">프로모션비용</a></li>
						<li class="tab-item"><a href="#tabContent5" id="penlty" class="tab-link">패널티산정</a></li>
					</ul>
					<!-- S:tab_content -->
					<div id="tabContent1" class="tab-content">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">정산 내역</h3>
							</div>
							<div class="fr">
								<div class="btn-group">
									<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel1">엑셀 다운로드</a>
								</div>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tbl-row -->
						<table class="tbl-row">
							<caption>정산 내역</caption>
							<colgroup>
								<col style="width: 220px;">
								<col>
								<col style="width: 220px;">
								<col>
								<col style="width: 220px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">패널티 수수료율</th>
									<td><span id="penltyLevyRate"></span>%</td>
									<th scope="row">평균 출고 기준일 (배송소요일)</th>
									<td><span id="dlvyDayCount"></span>일</td>
									<th scope="row">입점사 패널티발생율 (발생 / 기준)</th>
									<td><span id="penltyOcrncRate"></span>% / <span id="penltyLevyRateStandard"></span>%</td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-row -->

						<!-- S : tbl-col -->
						<table class="tbl-col">
							<caption>정산 내역</caption>
							<colgroup>
								<col>
								<col>
								<col>
								<col>
								<col>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" colspan="2">구분</th>
									<th scope="col">정산 확정 대상 건수</th>
									<th scope="col">정산 확정 대상 금액</th>
									<th scope="col">정산조정금액 </th>
								</tr>
							</thead>
							<tbody class="tab01">
								<tr>
									<th scope="rowgroup" rowspan="2">판매금액(+)</th>
									<th scope="row">결제금액</th>
									<td class="text-right"><span id="pymntCount"></span>건</td>
									<td class="text-right"><span class="pr" id="pymntAmt"></span>원</td>
									<td rowspan="4"></td>
								</tr>
								<tr>
									<th scope="row">배송비</th>
									<td class="text-right"><span id="dlvyAmtCount"></span>건</td>
									<td class="text-right"><span class="pr" id="dlvyMonthAmt"></span>원</td>
								</tr>
								<tr>
									<th scope="rowgroup" rowspan="2">공제금액(-)</th>
									<th scope="row">판매 수수료</th>
									<td class="text-right"><span id="sellCmsnCount"></span>건</td>
									<td class="text-right"><span class="pr" id="sellCmsnAmt"></span>원</td>
								</tr>
								<tr>
									<th>프로모션비용</th>
									<td class="text-right"><span id="promoCount"></span>건</td>
									<td class="text-right"><span class="pr" id="promoMonthAmt"></span>원</td>
								</tr>
								<tr>
									<th scope="rowgroup">패널티 금액 (-)</th>
									<th scope="row">패널티 산정금액</th>
									<td class="text-right"><span id="penltyCount"></span>건</td>
									<td class="text-right"><span class="pr" id="penltyAmt"></span>원</td>
									<td class="text-right"><span class="tc-bold pr" id="penltyMdatAmt"></span>원</td>
								</tr>
								<tr>
									<th scope="row" colspan="3">
										<span class="tc-bold">최종 정산 금액</span> (판매금액 – 공제금액)
									</th>
									<td class="text-right"><span class="tc-bold pr" id="excclcAmt"></span>원</td>
									<td class="text-right"><span class="tc-bold pr" id="excclcMdatAmt"></span>원</td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-col -->

						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">브랜드별 정산내역</h3>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tbl-col -->
						<!-- E : content-header -->
						<div class="tbl-controller">
							<div class="fl">
								<!-- S : opt-group -->
								<span class="opt-group">
									<label class="title" for="pageCount1">목록개수</label>
									<select class="ui-sel" id="pageCount1">
										<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
									</select>
								</span>
								<!-- E : opt-group -->
							</div>
						</div>

						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
<!-- 							<div style="width:100%; height:429px;"> -->
								<div id="monthGrid"></div>
<!-- 							</div> -->
						</div>
						<!-- E : ibsheet-wrap -->
					</div>
					<!-- E:tab_content -->
					
					<!-- S:tab_content -->
					<div id="tabContent2" class="tab-content">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">정산 내역</h3>
							</div>
						</div>
						<!-- E : content-header -->
						<!-- S : tbl-col -->
						<table class="tbl-col">
							<caption>정산 내역</caption>
							<colgroup>
								<col style="width: 10%">
								<col>
								<col>
								<col>
								<col>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" rowspan="2">구분</th>
									<th scope="colgroup" colspan="2">정산 대기</th>
									<th scope="colgroup" colspan="2">정산 확정</th>
								</tr>
								<tr>
									<th scope="col">정산 대기 건수</th>
									<th scope="col">정산 대기 금액</th>
									<th scope="col">정산 확정 대상 건수</th>
									<th scope="col">정산 확정 대상 금액</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th scope="row">판매 수수료</th>
									<td class="text-right" id="sellExcclcDcsnNCount"></td>
									<td class="text-right pr" id="sellExcclcDcsnNAmt"></td>
									<td class="text-right" id="sellExcclcDcsnYCount"></td>
									<td class="text-right pr" id="sellExcclcDcsnYAmt"</td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-col -->

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
									<label class="title" for="pageCount2">목록개수</label>
									<select class="ui-sel" id="pageCount2">
										<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
									</select>
								</span>
								<!-- E : opt-group -->
							</div>
							<div class="fr">
								<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel2">엑셀 다운로드</a>
							</div>
						</div>

						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
							<div id="sellAmtGrid"></div>
						</div>
						<!-- E : ibsheet-wrap -->
					</div>
					<!-- E:tab_content -->
					
					<!-- S:tab_content -->
					<div id="tabContent3" class="tab-content">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">정산 내역</h3>
							</div>
						</div>
						<!-- E : content-header -->
						<!-- S : tbl-col -->
						<table class="tbl-col">
							<caption>정산 내역</caption>
							<colgroup>
								<col style="width: 10%">
								<col>
								<col>
								<col>
								<col>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" rowspan="2">구분</th>
									<th scope="colgroup" colspan="2">정산 대기</th>
									<th scope="colgroup" colspan="2">정산 확정</th>
								</tr>
								<tr>
									<th scope="col">정산 대기 건수</th>
									<th scope="col">정산 대기 금액</th>
									<th scope="col">정산 확정 대상 건수</th>
									<th scope="col">정산 확정 대상 금액</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th scope="row">배송비</th>
									<td class="text-right" id="dlvyExcclcDcsnNCount"></td>
									<td class="text-right pr" id="dlvyExcclcDcsnNAmt"></td>
									<td class="text-right" id="dlvyExcclcDcsnYCount"></td>
									<td class="text-right pr" id="dlvyExcclcDcsnYAmt"</td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-col -->

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
									<label class="title" for="pageCount3">목록개수</label>
									<select class="ui-sel" id="pageCount3">
										<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
									</select>
								</span>
								<!-- E : opt-group -->
							</div>
							<div class="fr">
								<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel3">엑셀 다운로드</a>
							</div>
						</div>

						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
<!-- 							<div style="width:100%; height:429px;"> -->
								<div id="dlvyAmtGrid"></div>
<!-- 							</div> -->
						</div>
						<!-- E : ibsheet-wrap -->
					</div>
					<!-- E:tab_content -->
					<!-- S:tab_content -->
					<div id="tabContent4" class="tab-content">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">정산 내역</h3>
							</div>
						</div>
						<!-- E : content-header -->
						<!-- S : tbl-col -->
						<table class="tbl-col">
							<caption>정산 내역</caption>
							<colgroup>
								<col style="width: 10%">
								<col>
								<col>
								<col>
								<col>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" rowspan="2">구분</th>
									<th scope="colgroup" colspan="2">정산 대기</th>
									<th scope="colgroup" colspan="2">정산 확정</th>
								</tr>
								<tr>
									<th scope="col">정산 대기 건수</th>
									<th scope="col">정산 대기 금액</th>
									<th scope="col">정산 확정 대상 건수</th>
									<th scope="col">정산 확정 대상 금액</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th scope="row">프로모션 비용</th>
									<td class="text-right" id="promoExcclcDcsnNCount"></td>
									<td class="text-right pr" id="promoExcclcDcsnNAmt"></td>
									<td class="text-right" id="promoExcclcDcsnYCount"></td>
									<td class="text-right pr" id="promoExcclcDcsnYAmt"</td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-col -->

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
									<label class="title" for="pageCount4">목록개수</label>
									<select class="ui-sel" id="pageCount4">
										<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
									</select>
								</span>
								<!-- E : opt-group -->
							</div>
							<div class="fr">
								<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel4">엑셀 다운로드</a>
							</div>
						</div>

						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
<!-- 							<div style="width:100%; height:429px;"> -->
								<div id="promoAmtGrid"></div>
<!-- 							</div> -->
						</div>
						<!-- E : ibsheet-wrap -->
					</div>
					<!-- E:tab_content -->
					<!-- S:tab_content -->
					<div id="tabContent5" class="tab-content">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">정산 내역</h3>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tbl-row -->
						<table class="tbl-row">
							<caption>정산 내역</caption>
							<colgroup>
								<col style="width: 220px;">
								<col>
								<col style="width: 220px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">패널티 수수료율</th>
									<td class="text-right" id="penltyRate">4%</td>
									<th scope="row">평균 출고 기준일 (배송소요일)</th>
									<td class="text-right" id="dlvyDay">3일</td>
								</tr>
								<tr>
									<th scope="row">입점사 패널티발생율 (발생 / 기준)</th>
									<td colspan="3" class="text-right"><span id="penltyOccur">10%</span> / <span id="penltyStandard">87%</span></td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-row -->

						<!-- S : tbl-col -->
						<table class="tbl-col">
							<caption>정산 내역</caption>
							<colgroup>
								<col style="width: 10%">
								<col>
								<col>
								<col>
								<col>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" rowspan="2">구분</th>
									<th scope="colgroup" colspan="2">정산 대기</th>
									<th scope="colgroup" colspan="2">정산 확정</th>
								</tr>
								<tr>
									<th scope="col">정산 대기 건수</th>
									<th scope="col">정산 대기 금액</th>
									<th scope="col">정산 확정 대상 건수</th>
									<th scope="col">정산 확정 대상 금액</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th scope="row">패널티 산정</th>
									<td class="text-right" id="penltyExcclcDcsnNCount"></td>
									<td class="text-right pr" id="penltyExcclcDcsnNAmt"></td>
									<td class="text-right" id="penltyExcclcDcsnYCount"></td>
									<td class="text-right pr" id="penltyExcclcDcsnYAmt"</td>
								</tr>
							</tbody>
						</table>
						<!-- E : tbl-col -->

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
									<label class="title" for="pageCount5">목록개수</label>
									<select class="ui-sel" id="pageCount5">
										<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
									</select>
								</span>
								<!-- E : opt-group -->
							</div>
							<div class="fr">
								<a href="javascript:void(0);" class="btn-sm btn-func disabled" id="excel5">엑셀 다운로드</a>
							</div>
						</div>

						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap">
<!-- 							<div style="width:100%; height:429px;"> -->
								<div id="penltyGrid"></div>
<!-- 							</div> -->
						</div>
						<!-- E : ibsheet-wrap -->
					</div>
					<!-- E:tab_content -->
				</div>
				<!-- E : tab-wrap -->
			</div>
		</form>
	</div>
	<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/settlement/abcmart.settlement.month.main.vendor.js<%=_DP_REV%>"></script>	
<%@include file="/WEB-INF/views/common/footer.jsp"%>
