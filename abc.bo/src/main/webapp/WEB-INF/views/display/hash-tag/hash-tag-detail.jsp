<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	<script type="text/javascript">
	$(function () {
		abc.biz.display.search.hashtag.sellStateCode = ${sellStateCode};
		abc.biz.display.search.hashtag.initHashTagProductSheet();
		abc.biz.display.search.hashtag.hashTagCreateInit();
		abc.biz.display.search.hashtag.doActionHashTag("productSearch");

		$('#excelUpLoad').click(function() {

	        if (! $('input:radio[name=siteNo]').is(':checked')) {
	        	alert("해쉬테그를 등록할 사이트를 선택하세요.");
	        	return false;
	        }

	        var totalRows = hashTagProductSheet.GetTotalRows();  //업로드 전체 건수
			var callPopupType = $('#callPopupType').val();

			//다시 엑셀 업로드 진행시
			if(totalRows >0 &&  callPopupType =="C"){
				if (!confirm("엑셀 업로드 후 저장 처리 하지않은 데잍가 존재합니다. \n\n다시 업로드시 기존 데이타는  유지 되지 않습니다. \n\n업로드를 진행 하시겠습니까?")) {
					return;
				}
			}

         	//엑셀 업로드시 매치 유형 선택 (기본값: 헤더 타이틀 기준 매치)
			var params = { Mode : "HeaderMatch",  StartRow: "1" ,  FileExt: "xls|xlsx"} ;
			hashTagProductSheet.LoadExcel(params);

	    });

		$('#excelSampleDownLoad').click(function() {
			location.href = "/display/hashtag/product/hashtag-product-excel-sample-down"
		});


	});

	function hashTagProductSheet_OnLoadExcel(result, code, msg) {
		if (result) {
			abc.biz.display.search.hashtag.doActionHashTag("excelUpLoadProductSearch");
			alert("엑셀 로딩이 완료되었습니다.");
		} else {
			alert("엑셀 로딩중 오류가 발생하였습니다.\n[ Error Code :" +Code+" \nError Massage : "+ msg+"]");
		}
	}

	</script>
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">해쉬태그 등록</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
										<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시관리</li>
								<li>해쉬태그관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">해쉬테그 등록</h3>
					</div>
				</div>
				<!-- E : content-header -->
			<form name="hashTagForm" id="hashTagForm">
			<input type="hidden" name="hshtgSeq" value="${dpHashTag.hshtgSeq}"/>
			<input type="hidden" name="prdtNoArr" value=""/>
			<input type="hidden" size="10"  name="callPopupType" id="callPopupType" value="C">
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>해쉬테그 등록</caption>
					<colgroup>
						<col style="width: 120px;">
						<col>
						<col style="width: 79px">
						<col style="width: 120px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><span class="th-required">사이트 구분</span></th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach var="siteList" items="${siteList}" varStatus="status">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="siteNo" id="siteNo<c:out value="${siteList.siteNo}" />"
												       value="<c:out value="${siteList.siteNo}" />"<c:if test="${siteList.siteNo eq dpHashTag.siteNo}"> checked</c:if>>
												<label for="siteNo<c:out value="${siteList.siteNo}" />" >
													<c:out value="${siteList.siteName}" />
												</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row"><span class="th-required">사용여부</span></th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useYn02" name="useYn" type="radio" value="Y"<c:if test="${Const.BOOLEAN_TRUE eq dpHashTag.useYn}"> checked</c:if>>
											<label for="useYn02">예</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useYn03" name="useYn" type="radio" value="N"<c:if test="${Const.BOOLEAN_FALSE eq dpHashTag.useYn}"> checked</c:if>>
											<label for="useYn03">아니오</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="th-required">해쉬태그명</span></th>
							<td class="input"><input type="text" class="ui-input" title="해쉬태그 입력" id="hshtgName" value="${dpHashTag.hshtgName}" name="hshtgName"></td>
							<td></td>
							<th scope="row"><span class="th-required">사용기간</span></th>
							<td class="input">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<span class="date-box">
										<input type="text" data-role="datepicker" name="dispStartYmd" id="fromDate" value="<fmt:formatDate value="${dpHashTag.dispStartYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>" class="ui-cal js-ui-cal" title="시작일 선택">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" data-role="datepicker" name="dispEndYmd" id="toDate" value="<fmt:formatDate value="${dpHashTag.dispEndYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>"class="ui-cal js-ui-cal" title="종료일 선택">
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
			</form>
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">관련상품 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCountProduct">목록개수</label>
							<select name="pageCount" id="pageCountProduct" class="ui-sel">
								<option value="30">30개 보기</option>
								<option value="50">50개 보기</option>
								<option value="70">70개 보기</option>
								<option value="100">100개 보기</option>
							</select>
						</span>
						<a href="javascript:void(0);" class="btn-sm btn-func" id="excelSampleDownLoad">엑셀폼 다운로드</a>
						<a href="javascript:void(0);" class="btn-sm btn-func" id="excelUpLoad">파일업로드</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-del productDelBtn"     id="hashTagProdtDel">선택삭제</a>
						<a href="javascript:void(0);" class="btn-sm btn-func productSearchPop" id="hashTagPrdtSearch">상품검색추가</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="hashTagProductGrid" style="width:100%; height:429px;">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fr">
						<a href="javascript:void(0);" id="hashTagList" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<div class="content-bottom">
					<a href="javascript:void(0);" id="hashTagSave" class="btn-lg btn-save">저장</a>
				</div>
			</div>
		</div>
		<!-- E : container -->
<script src="/static/common/js/biz/display/abcmart.display.search.hashtag.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>