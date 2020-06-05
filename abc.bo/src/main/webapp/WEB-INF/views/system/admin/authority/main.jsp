<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		<%-- Grid 객체 생성 --%>
		abc.biz.system.admin.authority.initAuthSheet();

		$("#authGroupRead").click(function(){
			abc.biz.system.admin.authority.doActionAuthGroup("search");
		});

		$("#pageCount").change(function(){
			abc.biz.system.admin.authority.doActionAuthGroup("search");
		});

		$("#authGroupReset").click(function(){
			$('#adminAuthForm')[0].reset();
		});

		//권한그룹 변경이력
		$("#authGroupHistory").click(function(){
			var url = "/system/admin/authority/history-pop";
			var params = {};

			abc.open.popup({
						url 	:	url,
						winname :	"authority-history-pop",	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
						method	: 	"get",
						title 	:	"authority-history-pop",
						width 	:	700,
						height	:	750,
						params	:	params

			});
		});

		//권한그룹 등록
		$("#authGroupRegist").click(function(){
			var url = "/system/admin/authority/update-pop";
			var params = {};
			params.status = abc.consts.CRUD_C;

			abc.open.popup({
						url 	:	url,
						winname :	"authority-update-pop",	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
						method	: 	"get",
						title 	:	"authority-update-pop",
						width 	:	600,
						height	:	480,
						params	:	params

			});
		});

		// enter 검색
		$(".search-wrap")
		.find(".ui-input")
		.filter("input[type=text]:not([readonly]):not([disabled])")
		.off().on("keyup", function(e) {
			if(e.keyCode === 13) {
				$("#authGroupRead").trigger("click");
		    }
		});
		
	});

	<%-- DataSearch(Row) End 이벤트--%>
	function authSheet_OnRowSearchEnd(row){
		//조회 이후 이벤트
		if(authSheet.GetCellValue(row,"authMenuCount") > 0 ){
			authSheet.SetCellValue(row, "authMenuButton", "보기");
		}
		if(authSheet.GetCellValue(row,"authApplySystemType") == "B" ){
			authSheet.SetCellValue(row, "authApplySystemTypeText", "BO(관리자시스템)");
		}else if(authSheet.GetCellValue(row,"authApplySystemType") == "P" ){
			authSheet.SetCellValue(row, "authApplySystemTypeText", "PO(파트너시스템)");
		}

		if(authSheet.GetCellValue(row,"useYn") == "Y" ){
			authSheet.SetCellValue(row, "useYn", "사용");
		}else{
			authSheet.SetCellValue(row, "useYn", "사용안함");
		}
	}

	<%-- 그리드 ButtonClick 이벤트 --%>
	function authSheet_OnButtonClick(Row, Col) {
		if ( Row != 0) {
			//권한메뉴 등록/수정
			if ( authSheet.ColSaveName(Col) == "authMenuButton") {
				var url = "/system/admin/authority/menu-pop";
				var params = {};
				params.authApplySystemType = authSheet.GetRowData(Row).authApplySystemType;
				params.authNo = authSheet.GetRowData(Row).authNo;
				params.authName = authSheet.GetRowData(Row).authName;
				params.adminCount = authSheet.GetRowData(Row).adminCount;

				abc.open.popup({
							url 	:	url,
							winname :	"authority-menu-pop",	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
							method	: 	"post",
							title 	:	"권한그룹관리수정",
							width 	:	600,
							height	:	600,
							params	:	params

				});
			}
		}
	}

	<%-- 그리드 Click 이벤트 --%>
	function authSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			//권한그룹 수정
			if ( authSheet.ColSaveName(Col) == "authName") {
				var url = "/system/admin/authority/update-pop";
				var params = {};
				params.status = abc.consts.CRUD_U;
				params.authApplySystemType = authSheet.GetRowData(Row).authApplySystemType;
				params.authNo = authSheet.GetRowData(Row).authNo;

				abc.open.popup({
							url 	:	url,
							winname :	"authority-update-pop",	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
							method	: 	"get",
							title 	:	"권한그룹관리수정",
							width 	:	600,
							height	:	500,
							params	:	params

				});
			}
		}
	}


</script>

<!-- S : container -->
<form id="adminAuthForm" name="adminAuthForm">
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">권한그룹 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>시스템 관리</li>
						<li>관리자 관리</li>
						<li>권한그룹 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">권한그룹 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>권한그룹 검색</caption>
					<colgroup>
						<col style="width:15%;">
						<col>
						<col style="width:79px;">
						<col style="width:15%;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">권한 적용 시스템</th>
							<td class="input">
								<select class="ui-sel" title="권한 적용 시스템 선택" id="authApplySystemType">
									<option value="">전체</option>
									<option value="B">BO(관리자시스템)</option>
									<option value="P">PO(파트너시스템)</option>
								</select>
							</td>
							<td></td>
							<th scope="row">권한 그룹명</th>
							<td class="input">
								<input type="text" class="ui-input" title="권한 그룹명 입력" placeholder="권한 그룹이름 입력" id="authName">
							</td>
						</tr>
						<tr>
							<th scope="row">사용여부</th>
							<td class="input" colspan="4">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse01" name="useYn" type="radio" value="" checked="checked">
											<label for="radioUse01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse02" name="useYn" type="radio" value="Y">
											<label for="radioUse02">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse03" name="useYn" type="radio" value="N">
											<label for="radioUse03">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
					</tbody>
				</table>

				<div class="confirm-box">
					<div class="fl">
						<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
						<a href="javascript:void(0);" id="authGroupReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						<!-- E : 20190114 수정 -->
					</div>
					<div class="fr">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<a href="javascript:void(0);" id="authGroupRead" class="btn-normal btn-func">검색</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">권한그룹 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

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
			</div>
			<div class="fr">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<!-- DESC : html/common/BO-CM-15003.html 파일 확인 해주세요. -->
				<a href="javascript:void(0);" id="authGroupHistory" class="btn-sm btn-link">권한그룹 변경 이력</a>
				<span class="gap"></span>
				<!-- DESC : html/common/BO-CM-15001.html 파일 확인 해주세요. -->
				<a href="javascript:void(0);" id="authGroupRegist" class="btn-sm btn-link">등록</a>
				<!-- E : 20190114 수정 -->
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="adminAuthGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
</form>
<!-- E : container -->

<script src="/static/common/js/biz/system/abcmart.system.admin.authgroup.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>