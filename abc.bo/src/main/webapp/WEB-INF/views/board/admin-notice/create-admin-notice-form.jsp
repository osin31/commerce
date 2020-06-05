<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {

		<%-- 대상시스템 선택박스 --%>
		$("#selectApplySystem").change(function(){
			var applySystemType = $("#selectApplySystem option:selected").val();

			if(applySystemType == 'P') {
				 $('input:radio[name=vndrAllChoiceYn]').filter('[value=Y]').prop('checked', true);
				$(".selectApplySystem").show();
			} else {
				$(".selectApplySystem").hide();
			}
		});
		
		<%-- 관리자 메인 팝업 --%>
		$("input[name=mainPopDtm]").on('change', function() {
			var mainPop = this.id;
			
			if(this.id === "chkUseMainNotice") {
				$("#popupStartYmd").attr('disabled', false);
				$("#popupEndYmd").attr('disabled', false);
				$("#popupYn").val('Y');
				$("#alwaysPopupYn").val('N');
				
			}else if(this.id === "chkUseMainNoticeAlways") {
				$("#popupStartYmd").attr('disabled', true);
				$("#popupEndYmd").attr('disabled', true);
				$("#popupYn").val('N');
				$("#alwaysPopupYn").val('Y');
				
			}else if(this.id === "noneMainPop") {
				$("#popupStartYmd").attr('disabled', true);
				$("#popupEndYmd").attr('disabled', true);
				$("#popupYn").val('N');
				$("#alwaysPopupYn").val('N');
			}
		});

		<%-- CKEDITOR 셋팅--%>
		CKEDITOR.replace("notcContText");

		<%-- 초기화 버튼 클릭 시 --%>
		$("#btnReset").click(function(){
			location.reload();
		});

		<%-- 관리자 공지 목록 화면으로 이동 --%>
		$("#btnAdminNotcList").click(function(){
			location.href = "/board/admin-notice";
		});
		
		// 저장버튼 클릭
		$("#saveAdminNoticeBtn").off().on('click', function(el) {
			abc.biz.board.adminnotice.createAdminNotice();
		});
		
		// 미리보기버튼 클릭		
		$("#previewBtn").off().on('click', function(el) {
			abc.biz.board.adminnotice.popupPreCreateAdminNotice();
		});

		$("input[name=vndrAllChoiceYn]").off().on("click", function() {
			if($("input[name=vndrAllChoiceYn]:checked").val() == "N") {
				$("#vendor-list").show();
			} else {
				$("#vendor-list").hide();
			}
		});

		$("#btnVendorSearch").click(function() {
			if($("input[name=vndrAllChoiceYn]:checked").val() == "N") {
				abc.vndrSearchPopup(true, "setSelectedVendors");
			}
		});

	});

	function setSelectedVendors(args) {
		var _vndrNo = args.arrayVndrNo;
		var _vndrName = args.arrayVndrName;

		var _html = "";

		for (var i = 0; i < _vndrNo.length; i++) {
			if ($("#vndrNo_" + _vndrNo[i]).length <= 0) {
				_html += "<li id=\"vndrNo_" + _vndrNo[i] + "\">";
				_html += "<input type=\"hidden\" name=\"vndrNo\" value=\"" + _vndrNo[i] + "\" />";
				_html += "<span class='subject'>" + _vndrName[i] + "</span>"
				_html += "<button type=\"button\" class=\"btn-item-del\" name=\"btnVndrRemove\">";
				_html += "<span class=\"ico ico-item-del\"><span class=\"offscreen\">해당 입점업체 삭제</span></span>";
				_html += "</button>";
				_html += "</li>";
			}
		}

		$("#vendor-list").append(_html);
		$("button[name=btnVndrRemove]").off().on('click', function(el) {
			$(this).parent().remove();
		});
	}

</script>

		<!-- S : container -->
		<form id="adminNoticeForm" name="adminNoticeForm" method="post" action="/board/admin-notice/create" enctype="multipart/form-data">
		<div class="container">
<!-- 			<input type="hidden" id="applySystemType" name="applySystemType" 	 value="A"/> -->
			<input type="hidden" id="popupYn" 		  name="popupYn" 			 value="N"/>
			<input type="hidden" id="alwaysPopupYn"   name="alwaysPopupYn" 		 value="N"/>
			<input type="hidden" id="dispYn" 		  name="dispYn" 			 value="Y"/>

			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">관리자 공지 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites">
							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a>
								</li>
								<li>시스템 관리</li>
								<li>관리자 공지 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">관리자 공지 등록</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
							<a href="#" class="btn-sm btn-func" id="btnReset"><i class="ico ico-refresh"></i>초기화</a>
							<!-- E : 20190114 수정 -->
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<table class="tbl-row">
					<caption>관리자 공지 등록 대상 시스템 선택</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" rowspan="2">대상 시스템</th>
							<td>
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<select class="ui-sel" title="대상 시스템 선택" id="selectApplySystem" name="applySystemType">
											<option value="A" selected>ALL(관리자+파트너)</option>
											<option value="B">BO(관리자시스템)</option>
											<option value="P">PO(파트너시스템)</option>
										</select>
									</li>
									<li class="selectApplySystem" style="display: none;">
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="rdoSystemRangeAll" name="vndrAllChoiceYn" type="radio" value="Y" checked="checked">
											<label for="rdoSystemRangeAll">전체</label>
										</span>
									</li>
									<li class="selectApplySystem" style="display: none;">
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="rdoSystemRangeVendor" name="vndrAllChoiceYn" value="N" type="radio">
											<label for="rdoSystemRangeVendor">입점업체 선택</label>
										</span>
									</li>
									<li class="selectApplySystem" style="display: none;">
										<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
										<button id="btnVendorSearch" type="button" class="btn-sm btn-link">검색</button>
										<!-- E : 20190114 수정 -->
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<td>
								<!-- S : item-list -->
								<ul class="item-list" id="vendor-list">

								</ul>
								<!-- E : item-list -->
							</td>
						</tr>
					</tbody>
				</table>
				<table class="tbl-row">
					<caption>관리자 공지</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">제목</th>
							<td colspan="3" class="input">
								<input type="text" class="ui-input" name="notcTitleText" id="notcTitleText" title="제목 입력">
							</td>
						</tr>
						<tr>
							<th scope="row">내용</th>
							<td colspan="3" class="input">
								<!-- S : editor-wrap -->
								<div class="editor-wrap">
									<textarea rows="10" cols="100" name="notcContText" id="notcContText" class="w100"></textarea>
								</div>
								<!-- E : editor-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">관리자 메인 팝업</th>
							<td colspan="3" class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" id="noneMainPop" name="mainPopDtm" checked>
											<label for="noneMainPop">적용안함</label>
										</span>
									</li>	
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" id="chkUseMainNotice" name="mainPopDtm">
											<label for="chkUseMainNotice">기간적용</label>
										</span>

										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<span class="date-box">
												<!-- DESC : 적용 체크박스 비활성화시 disabled="disabled" 속성 추가해주세요 -->
												<input type="text" data-role="datepicker" id="popupStartYmd" name="popupStartYmd" class="ui-cal js-ui-cal" title="시작일" disabled="disabled">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<!-- DESC : 적용 체크박스 비활성화시 disabled="disabled" 속성 추가해주세요 -->
												<input type="text" data-role="datepicker" id="popupEndYmd" name="popupEndYmd" class="ui-cal js-ui-cal" title="종료일" disabled="disabled">
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</li>	
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" id="chkUseMainNoticeAlways" name="mainPopDtm">
											<label for="chkUseMainNoticeAlways">상시</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">전시여부</th>
							<td colspan="3" class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="rdoUseDisplayY" name="rdoUseDisplay" type="radio" checked>
											<label for="rdoUseDisplayY">전시</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="rdoUseDisplayN" name="rdoUseDisplay" type="radio">
											<label for="rdoUseDisplayN">전시안함</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<div class="fl">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<a href="#" id="previewBtn" class="btn-normal btn-link">미리보기</a>
						<!-- E : 20190114 수정 -->
					</div>
					<div class="fr">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<a href="#" class="btn-normal btn-link" id="btnAdminNotcList">목록</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->
				<!-- S : content-bottom -->
				<div class="content-bottom">
					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<a href="javascript:void(0);" id="saveAdminNoticeBtn" class="btn-lg btn-save">저장</a>
					<!-- E : 20190114 수정 -->
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<input type="hidden" name="_ie_bug_prevent">
		</form>
		<!-- E : container -->

<script src="/static/common/js/biz/board/abcmart.board.adminnotice.js<%=_DP_REV%>">
</script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>