<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

		<script type="text/javascript">
			$(document).ready(function(){
				// 결과 목록 그리드 init
				abc.biz.cmm.counselscript.initCounselScriptSheet();

				// 상담스크립트 검색
				$("#searchBtn").click(function(f) {
					abc.biz.cmm.counselscript.doActionCounselScript("search");
				});
				
				//enter 검색 이벤트
				$("#frmSearch").on('submit', function(){
					$("#searchBtn").trigger("click");
					return false;
				});
				
				// 초기화
				$("#resetBtn").click(function(f) {
					$.form("#frmSearch").reset();
				});

				// 상담스크립트 등록
				$("#rgstBtn").click(function(f) {
					var _url = "/cmm/counselscript/read-detail?menuNo="+abc.param.getParams().menuNo;
					location.href = _url;
				});
				
				// 상담메뉴 코드 변경
				$("#cnslGbnCode").change(function(f) {
					var cnslGbnCode = $("#cnslGbnCode option:selected");		// 상담메뉴 코드
					if('' == cnslGbnCode.val()){
						$('#cnslTypeCode').hide();
						$('#cnslTypeDtlCode').hide();
					}else{
						$('#cnslTypeCode').show();
						$('#cnslTypeDtlCode').show();
					}
					
					abc.setLowerCodeList("CNSL_TYPE_CODE", cnslGbnCode.val(), "cnslTypeCode");

					$("#cnslTypeCode").trigger("change");
				});

				// 상담구분 코드 변경
				$("#cnslTypeCode").change(function(f) {
					var cnslTypeCode = $("#cnslTypeCode option:selected");		// 상담구분 코드
					abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "cnslTypeDtlCode");
				});

				// 목록 개수 변경
 				$("#pageCount").change(function(f) {
 					$("#searchBtn").trigger('click');
				}); 
				
				
 				<c:choose>
	 				<c:when test="${not empty param.listPageNum}">
		 				$("#cnslGbnCode").val('${param.cnslGbnCode}');
		 				if(abc.text.allNull('${param.cnslGbnCode}')){
		 					abc.biz.cmm.counselscript.initSearchForm();
		 				}else{
		 					abc.setLowerCodeList("CNSL_TYPE_CODE", $("#cnslGbnCode").val(), "cnslTypeCode");
			 				if((!abc.text.allNull('${param.cnslTypeCode}'))&& abc.text.allNull('${param.cnslTypeDtlCode}')){
				 				$("#cnslTypeCode").val('${param.cnslTypeCode}');
				 				abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $("#cnslTypeCode").val(), "cnslTypeDtlCode");
			 				}else if((!abc.text.allNull('${param.cnslTypeCode}')) && (!abc.text.allNull('${param.cnslTypeDtlCode}'))){
				 				$("#cnslTypeCode").val('${param.cnslTypeCode}');
				 				abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", $("#cnslTypeCode").val(), "cnslTypeDtlCode");
				 				$("#cnslTypeDtlCode").val('${param.cnslTypeDtlCode}');
			 				}
		 				}
						$("#searchKey").val('${param.searchKey}');
						$("#searchValue").val('${param.searchValue}');
						$("#listPageNum").val('${param.listPageNum}');
						$("input:radio[name='useYn'][value='${param.useYn}']").prop('checked', true);
	 				</c:when>
	 				<c:otherwise>
		 				//상담유형 선택전에 typecode와 typedtlcode부분 hide
						abc.biz.cmm.counselscript.initSearchForm();
	 				</c:otherwise>
	 			</c:choose>
 			
 				$("#searchBtn").trigger('click');
 				
			});

			// 상담스크립트 상세 페이지 이동
			function counselScriptSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
				if ( Row != 0) {
					if ( counselScriptSheet.ColSaveName(Col) == "cnslScriptTitleText" && Value != "" ) {
						$("#cnslScriptSeq").val(counselScriptSheet.GetCellValue(Row, "cnslScriptSeq"));
						$("#listPageNum").val(counselScriptSheet.GetCurrentPage());
						frmSearch.action = "/cmm/counselscript/read-detail"; 
						frmSearch.submit();
					}
				}
			}
		</script>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">상담스크립트관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>고객응대관리</li>
								<li>상담스크립트관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상담스크립트 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="frmSearch" name="frmSearch">
				<input type="hidden" name="menuNo" id="menuNo" value="${param.menuNo}">
				<input type="hidden" name="cnslScriptSeq" id="cnslScriptSeq" value="${param.cnslScriptSeq}">
				<input type="hidden" name="listPageNum" id="listPageNum" value="${param.listPageNum}">
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>상담스크립트 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">상담유형</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<select id="cnslGbnCode" name="cnslGbnCode" class="ui-sel" style="width:150px;" title="메뉴 선택">
												<option value="">전체</option>
												<c:forEach var="cnslGbnCode" items="${cnslGbnCode}">
													<option value="${cnslGbnCode.codeDtlNo}">${cnslGbnCode.codeDtlName}</option>
												</c:forEach>
											</select>
											<select id="cnslTypeCode" name="cnslTypeCode" class="ui-sel" style="width:150px;" title="상담구분 선택">
												<option value="">선택</option>
											</select>
											<select id="cnslTypeDtlCode" name="cnslTypeDtlCode" class="ui-sel" style="width:150px;" title="상담유형 선택">
												<option value="">선택</option>
											</select>
										</span>
										<!-- E : ip-text-box -->
									</td>
									<td></td>
									<th scope="row">사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="useYnAll" value="" checked>
													<label for="useYnAll">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="useYnY" value="Y">
													<label for="useYnY">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="useYnN" value="N">
													<label for="useYnN">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">검색어</th>
									<td class="input" colspan="4">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
												<option value="">선택</option>
												<option value="cnslScriptTitleText">제목/내용</option>
												<option value="rgsterName">작성자</option>
											</select>
											<input id="searchValue" name="searchValue" type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" maxlength="50">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="#" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="#" class="btn-normal btn-func" id="searchBtn">검색</a>
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
						<h3 class="content-title">상담스크립트 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount" name="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="#" class="btn-sm btn-link" id="rgstBtn">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="counselScriptGrid" style="width:100%; height:429px;">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->


<script src="/static/common/js/biz/cmm/abcmart.cmm.counselscript.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>