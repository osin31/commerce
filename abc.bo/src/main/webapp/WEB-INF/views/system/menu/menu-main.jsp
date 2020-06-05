<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">

	$(function() {
		var prevVal;
		$("#selAuthApplySystemType").focus(function(){
			prevVal = $(this).val();
		}).change(function(){
			if (abc.biz.system.menu.data.newFlag){
				$(this).blur();
				var cfm = confirm('작성 중인 내용이 있습니다.\n삭제하고 이동할까요?');
				if (cfm) {
					abc.biz.system.menu.data.newFlag = false;
					menuSheet.RowDelete(abc.biz.system.menu.data.newRowIndex, 0);
				}else{
					$(this).val(prevVal);
					return;
				}
			}
			$("#authApplySystemType").val($(this).val());
			abc.biz.system.menu.doActionMenu("search");
			abc.biz.system.menu.data.focusRow = 1;
		});

		$("input:radio[name=radioUseModule]").change(function(){
			$("#useYn").val($(this).val());
		});

		$("input:radio[name=radioDisplayModule]").change(function(){
			$("#dispYn").val($(this).val());
		});

		$("#sortSeq").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});

		$("#menuName").on('input', function(event){
			abc.biz.system.menu.menuNameCount();
		});

		$("#menuSaveBtn").click(function(e) {
			abc.biz.system.menu.menuSaveBtn();
		});

		$("#menuRemoveBtn").click(function(e) {
			abc.biz.system.menu.menuRemoveBtn();
		});

		$("#menuRegist").click(function(e){
			if(abc.biz.system.menu.data.newFlag) {
				alert('이미 등록중인 메뉴가 있습니다.');
				return;
			}
			abc.biz.system.menu.menuRegistBtn();
		});

		$("#moderName").click(function(e){
			abc.adminDetailPopup($("#moderNo").val());
		});

		$("#rgsterName").click(function(e){
			abc.adminDetailPopup($("#rgsterNo").val());
		});

		// 메뉴 grid init
		abc.biz.system.menu.initMenuSheet();

	});

	<%-- 그리드 Click 이벤트 --%>
	function menuSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		//그리드 내 컬럼 클릭 시 이벤트 발생
		if ( Row != 0) {
			if (menuSheet.ColSaveName(Col) == "menuName") {
				if (!abc.biz.system.menu.data.newFlag){
					abc.biz.system.menu.setMenuData(Row);
					abc.biz.system.menu.data.focusRow = Row;

				} else if(abc.biz.system.menu.data.newFlag && Row != abc.biz.system.menu.data.newRowIndex){
					var cfm = confirm('작성 중인 내용이 있습니다.\n삭제하고 이동할까요?');
					if (cfm) {
						abc.biz.system.menu.data.newFlag = false;
						menuSheet.RowDelete(abc.biz.system.menu.data.focusRow, 0);
						abc.biz.system.menu.setMenuData(Row);
						abc.biz.system.menu.data.focusRow = Row;
					} else {
						menuSheet.SetSelectRow(abc.biz.system.menu.data.focusRow);
					}

				}
			}
			var parent = menuSheet.GetSelectRow();
			var child = menuSheet.GetLastChildRow(parent);
			if(child > 0){
				$("#btnRemoveDiv").hide();
			}
		}
	}

	<%-- Grid 검색 후 호출 --%>
	function menuSheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response){
		if(menuSheet.GetRowExpanded(1) == 0){
			menuSheet.SetRowExpanded(1, 1);
		}

		menuSheet.SetSelectRow(abc.biz.system.menu.data.focusRow);
		abc.biz.system.menu.setMenuData(abc.biz.system.menu.data.focusRow);
	}

</script>

	<!-- S : container -->
	<form id="menuForm" name="menuForm">
	<div class="container">
		<input type="hidden" id="authApplySystemType" name="authApplySystemType" value="B"/>
		<input type="hidden" id="allPathMenuNo" name="allPathMenuNo"/>
		<input type="hidden" id="allPathMenuName" name="allPathMenuName"/>
		<input type="hidden" id="menuNo" name="menuNo"/>
		<input type="hidden" id="upMenuNo" name="upMenuNo"/>
		<input type="hidden" id="menuGbnType" name="menuGbnType"/>
		<input type="hidden" id="menuLevel" name="menuLevel"/>
		<input type="hidden" id="status" name="status"/>
		<input type="hidden" id="useYn" name="useYn"/>
		<input type="hidden" id="dispYn" name="dispYn"/>

		<input type="hidden" id="moderNo"/>
		<input type="hidden" id="rgsterNo"/>

		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">메뉴관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites">
						<span class="ico ico-star">
							<span class="offscreen">즐겨찾기 등록</span>
						</span>
					</button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>시스템관리</li>
							<li>메뉴관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<div class="col-wrap col3-2by1 full-content">
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<!-- <h3 class="content-title">ALL(관리자+파트너)</h3> -->
							<select class="ui-sel" title="select 선택" id="selAuthApplySystemType">
								<option value="B">BO(관리자 시스템)</option>
								<option value="P">PO(업체 시스템)</option>
							</select>
						</div>
						<div class="fr">
							<div class="btn-group">
								<a href="javascript:void(0);" id="menuRegist" class="btn-sm btn-func">메뉴 등록</a>
							</div>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="menuGrid"></div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">메뉴정보</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row" id="menuTable">
						<caption>메뉴정보</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">메뉴경로</th>
								<td colspan="3" id="allPathMenuNameText"></td>
							</tr>
							<tr>
								<th scope="row">권한 적용 시스템</th>
								<td colspan="3" id="authApplySystemTypeName"></td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">메뉴명</span>
								</th>
								<td class="input" colspan="3">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<input type="text" id="menuName" name="menuName" class="ui-input text-unit20" title="메뉴명 입력" placeholder="공백 포함하여 입력">
										<span class="text" id="menuNameCnt">(0 / 20자)</span>
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">메뉴URL</th>
								<td class="input" colspan="3">
									<input type="text" id="menuUrl" name="menuUrl" class="ui-input" title="메뉴URL 입력"  maxlength="100">
								</td>
							</tr>
							<tr>
							<th scope="row">리소스URL</th>
								<td class="input" colspan="3">
									<input type="text" id="rsrcUrl" name="rsrcUrl" class="ui-input" title="리소스URL 입력"  maxlength="100">
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">메뉴순서</span>
								</th>
								<td class="input" colspan="3">
									<input type="text" id="sortSeq" name="sortSeq" class="ui-input" title="메뉴순서 입력" placeholder="숫자만 입력" maxlength="5">
								</td>
							</tr>
							<tr>
								<th scope="row">전시여부</th>
								<td class="input" colspan="3">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="radioDispY" name="radioDisplayModule" type="radio" value="Y">
												<label for="radioDispY">전시</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="radioDispN" name="radioDisplayModule" type="radio" value="N">
												<label for="radioDispN">전시안함</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">사용여부</th>
								<td class="input" colspan="3">
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="radioUseY" name="radioUseModule" type="radio" value="Y">
												<label for="radioUseY">사용</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="radioUseN" name="radioUseModule" type="radio" value="N">
												<label for="radioUseN">사용안함</label>
											</span>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td><a href="javascript:void(0);" id="moderName"></td>
								<th scope="row">수정일시</th>
								<td id="modDtm"></td>
							</tr>
							<tr>
								<th scope="row">작성자</th>
								<td><a href="javascript:void(0);" id="rgsterName"></a></td>
								<th scope="row">작성일시</th>
								<td id="rgstDtm"></td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->

					<!-- S : 20180117 추가 // 수정/상세페이지 삭제버튼 추가 -->
					<!-- S : tbl-desc-wrap -->
					<sec:authorize access="hasRole('ROLE_20000')">
						<div class="tbl-desc-wrap" id="btnRemoveDiv">
							<div class="fl">
								<a href="javascript:void(0);" id="menuRemoveBtn" class="btn-normal btn-del">삭제</a>
							</div>
						</div>
					</sec:authorize>
					<!-- E : tbl-desc-wrap -->
					<!-- E : 20190117 추가  -->


					<!-- S : content-bottom -->
					<div class="content-bottom" id="btnSaveDiv">
						<a href="javascript:void(0);" id="menuSaveBtn" class="btn-lg btn-save" >저장</a>
					</div>
					<!-- E : content-bottom -->
				</div>
			</div>
		</div>
	</div>
	</form>
	<!-- E : container -->

<script src="/static/common/js/biz/system/abcmart.system.menu.js<%=_DP_REV%>"></script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>
