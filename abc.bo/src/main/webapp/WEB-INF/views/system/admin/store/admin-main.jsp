<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

	<script type="text/javascript">
		$(function() {

			abc.biz.system.admin.admin.initAconnectAdminList();

			$("#rgstBtn").off().on('click', function() {
				location.href = "/system/admin/store/read-detail?menuNo=" + abc.param.getParams().menuNo;
			});

			$("#btnSearch").off().on('click', function() {
				abc.biz.system.admin.admin.doActionAconnectAdmin("search");
			});
			
			//enter 검색 이벤트
			$("#frmSearch").on('submit', function(){
				$("#btnSearch").trigger("click");
				return false;
			});
			
			$("#btnReset").click(function(){
				$.form("#frmSearch").reset();
			});
			
		});

		<%-- 그리드 Click 이벤트 --%>
		function aconnectAdminSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
			if ( Row != 0) {
				if ( aconnectAdminSheet.ColSaveName(Col) == "listAconnectName" && Value != "" ) {
					var _url = "/system/admin/store/read-detail";
					location.href = _url + "?adminNo=" + aconnectAdminSheet.GetCellValue(Row, "adminNo") + "&menuNo=" + abc.param.getParams().menuNo;
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
						<h2 class="page-title">A-Connect 사용자 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>A-Connect 관리</li>
								<li>A-Connect 사용자 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">A-Connect 사용자 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form name="frmSearch" id="frmSearch">
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>A-Connect 사용자 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse01" checked value="">
													<label for="radioUse01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse02" value="Y">
													<label for="radioUse02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse03" value="N">
													<label for="radioUse03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
												<option value="">선택</option>
												<option value="loginId">사용자ID</option>
												<option value="adminName">사용자명</option>
												<option value="storeName">사용자권한매장</option>
											</select>
											<input type="text" id="searchValue" name="searchValue" class="ui-input" placeholder="검색어 입력" title="검색어 입력" maxlength="20">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" id="btnReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0);" id="btnSearch" class="btn-normal btn-func">검색</a>
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
						<h3 class="content-title">A-Connect 사용자 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selTermsModule">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-link" id="rgstBtn">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="aconnectAdminGrid" style="width:100%; height:429px;"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->
<script src="/static/common/js/biz/system/abcmart.system.admin.admin.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>
