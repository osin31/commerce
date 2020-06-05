<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 관리자 목록 초기 세팅. --%>
		abc.biz.vendor.admin.initAdminSheet();

		<%-- 검색어가 전화번호일 경우 숫자만 입력되는 이벤트 설정. --%>
		$("#searchKey").change(function(){
			abc.biz.vendor.admin.searchValueSet();
		});

		<%-- 관리자 목록 조회. --%>
		$("#searchBtn").click(function(){
			abc.biz.vendor.admin.adminDoAction('search');
		});

		<%-- 권한그룹 변경 팝업 호출. --%>
		$("#selAuthBtn").click(function(){
			abc.biz.vendor.admin.authGroupUpdate();
		});
		
		<%-- 초기화. --%>
		$("#resetBtn").click(function(){
			$('#frmSearch')[0].reset();
		});
		
		// enter 검색
		$(".search-wrap")
		.find(".ui-input")
		.filter("input[type=text]:not([readonly]):not([disabled])")
		.off().on("keyup", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
		    }
		});
	});

	<%-- 그리드 Click 이벤트 --%>
	function adminSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( adminSheet.ColSaveName(Col) == "listDisplayName" && Value != "" ) {
				var url = "/vendor/admin/admin-detail-pop";
				var params = {}
				params.adminNo = adminSheet.GetCellValue(Row, "adminNo");

				abc.open.popup({
					winname :	"detailPop",
					url 	:	url,
					width 	:	830,
					height	:	900,
					params	:	params
				});
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
				<h2 class="page-title">입점사 관리자 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>시스템 관리</li>
						<li>관리자 관리</li>
						<li>관리자</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">입점사 관리자 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>관리자 검색</caption>
					<colgroup>
						<col style="width:15%;">
						<col>
						<col style="width:79px;">
						<col style="width:15%;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<!-- <th scope="row">권한 적용 시스템</th>
							<td class="input">
								<select class="ui-sel" id="authApplySystemType" name="authApplySystemType" title="권한 적용 시스템 선택">
									<option value="">전체</option>
									<option value="B">BO(관리자시스템)</option>
									<option value="P">PO(파트너시스템)</option>
									<option value="">ALL(관리자+파트너)</option>
								</select>
							</td> -->
							<!-- <td></td> -->
							<th scope="row">권한 그룹명</th>
							<td class="input">
								<select id="authNo" name="authNo"  class="ui-sel" title="권한 그룹명 선택">
									<option value="">전체</option>
									<c:forEach var="authGroup" items="${authGroup}">
										<option value="${authGroup.authNo}">${authGroup.authName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">개인정보 취급여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="memberInfoMgmtAll" name="memberInfoMgmtYn" type="radio" value="" checked="checked">
											<label for="memberInfoMgmtAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="memberInfoMgmtY" name="memberInfoMgmtYn" type="radio" value="Y">
											<label for="memberInfoMgmtY">가능</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="memberInfoMgmtN" name="memberInfoMgmtYn" type="radio" value="N">
											<label for="memberInfoMgmtN">불가능</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">사용여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useAll" name="useYn" type="radio" value="" checked="checked">
											<label for="useAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useY" name="useYn" type="radio" value="Y">
											<label for="useY">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="useN" name="useYn" type="radio" value="N">
											<label for="useN">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">검색어</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 선택">
										<option value="">선택</option>
										<option value="loginId">관리자id</option>
										<option value="adminName">관리자이름</option>
										<option value="emailAddrText">이메일</option>
										<option value="hdphnNoText">휴대폰번호</option>
									</select>
									<input type="text" id="searchValue" name="searchValue" class="ui-input" title="검색어 입력" placeholder="검색어 입력">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
						</tr>
					</tbody>
				</table>

				<div class="confirm-box">
					<div class="fl">
						<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
						<a href="#" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						<!-- E : 20190114 수정 -->
					</div>
					<div class="fr">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<a href="#" id="searchBtn" class="btn-normal btn-func">검색</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		</form>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<!-- E : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">입점사 관리자 목록</h3>
			</div>
		</div>
		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<!-- S : opt-group -->
				<span class="opt-group">
					<label class="title" for="selView01">목록개수</label>
					<select class="ui-sel" id="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
				<!-- E : opt-group -->

				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<!-- DESC : html/common/BO-CM-15009.html 파일 확인 해주세요. -->
				<a href="#" id="selAuthBtn" class="btn-sm btn-link">선택 항목 권한그룹 변경</a>
				<!-- E : 20190114 수정 -->
				<span class="guide-text">* 권한 적용 시스템이 동일한 관리자 끼리 권한그룹 변경이 가능합니다.</span>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="adminGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/vendor/abcmart.vendor.admin.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>