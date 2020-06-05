<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	<script type="text/javascript">
		$(function(){
			abc.biz.cmm.app.push.appPushGridSheet();

			abc.biz.cmm.app.push.mainEvent();

			// enter 검색
			$('.search-wrap').find('.ui-input, .js-ui-cal').on('keypress', function(e) {
				if(e.keyCode === 13) {
					abc.biz.cmm.app.push.doActionAppPush('search');
			    }
			});

			// 페이지 목록 개수 클릭시
			$("#pageCount").change(function(){
				abc.biz.cmm.app.push.doActionAppPush('search');
			});

			// 검색조건 유지를 위한
			<c:if test="${not empty param.pushMesgNo}">
			$("input:radio[name='siteNo'][value='<c:out value='${param.searchSiteNo}'/>']").prop('checked', true);
			$('#pushTitleText').val('<c:out value="${param.searchPushTitle}"/>');
			$("input:radio[name='sendType'][value='<c:out value='${param.searchSendType}'/>']").prop('checked', true);
			$("input:radio[name='pushIngStatCode'][value='<c:out value='${param.searchPushIngStatCode}'/>']").prop('checked', true);
			$('#fromDate').val('<c:out value="${param.fromDate}"/>');
			$('#toDate').val('<c:out value="${param.toDate}"/>');
			$('#pageNum').val('<c:out value="${param.pageCount}"/>');
			</c:if>

			abc.biz.cmm.app.push.doActionAppPush('search');
		});

		function appPushGridSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
			var url = '/cmm/msg/apppush';
			if ( Row != 0) {

				if (appPushGridSheet.ColSaveName(Col) == 'pushTitleText') {
					var pushMesgNo = appPushGridSheet.GetRowData(Row).pushMesgNo;
					var pageNum = appPushGridSheet.GetCurrentPage();

					$('#pushMesgNo').val(pushMesgNo);
					$('#listPageNum').val(pageNum);

					appPushForm.action = "/cmm/msg/apppush/read-detail";
					appPushForm.submit();
				}

				if(appPushGridSheet.ColSaveName(Col) == 'sendManagement'){
					var url = '/cmm/msg/apppush/send-managemnet';
					_params = {};
					_params.pushMesgNo = appPushGridSheet.GetRowData(Row).pushMesgNo;

					abc.open.popup({
						url 	:	url,
						winname :	'app-push-send-management',
						title 	:	'sendManegement',
						width 	:	1600,
						height	:	900,
						params : _params
					});
				}
			}
		};

	</script>
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">App Push 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">Push 메시지 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->
			<form name="appPushForm" id="appPushForm" method="post" onsubmit="return false;">
			<input type="hidden" id="pushMesgNo" name="pushMesgNo" >
			<input type="hidden" id="listPageNum" name="listPageNum" value="<c:out value="${param.listPageNum}" />">
				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>Push 메시지 검색</caption>
							<colgroup>
								<col style="width: 120px;">
								<col>
								<col style="width: 79px">
								<col style="width: 120px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">메시지 제목</th>
									<td class="input">
										<input type="text" id="pushTitleText" name="pushTitleText" class="ui-input" title="메시지 제목 입력" maxlength="60">
									</td>
									<td></td>
									<th scope="row">발송형태</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rdoSendType01" name="sendType" type="radio" value="" checked>
													<label for="rdoSendType01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rdoSendType02" name="sendType" type="radio" value="<%= kr.co.abcmart.constant.CommonCode.CM_PUSH_MESSAGE_SEND_TYPE_I%>">
													<label for="rdoSendType02">즉시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rdoSendType03" name="sendType" type="radio" value="<%= kr.co.abcmart.constant.CommonCode.CM_PUSH_MESSAGE_SEND_TYPE_R%>">
													<label for="rdoSendType03">예약</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">진행상태</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rdoProgress01" name="pushIngStatCode" type="radio" value="" checked>
													<label for="rdoProgress01">전체</label>
												</span>
											</li>
											<c:forEach var="ingCodeStat" items="${ingCode}">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="rdoProgress${ingCodeStat.codeDtlNo}" name="pushIngStatCode" type="radio" value="${ingCodeStat.codeDtlNo}">
														<label for="rdoProgress${ingCodeStat.codeDtlNo}">${ingCodeStat.codeDtlName}</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">사이트 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="siteNo0" name="siteNo" type="radio" value="" checked>
													<label for="siteNo0">전체</label>
												</span>
											</li>
											<c:forEach var="siteNoList" items="${siteNoList}" varStatus="status">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="radio" name="siteNo" id="siteNo<c:out value="${status.count}" />"
														       value="<c:out value="${siteNoList.siteNo}" />" >
														<label for="siteNo<c:out value="${status.count}" />" >
															<c:out value="${siteNoList.siteName}" />
														</label>
													</span>
												</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">기간검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select id="searchDateKey" name="searchDateKey" class="ui-sel" title="기간 선택">
												<option value="sendIrDtm">발송(예정)일</option>
												<option value="rgstDtm" selected>작성일</option>
												<option value="modDtm">수정일</option>
											</select>
											<span class="date-box">
												<input type="text" data-role="datepicker" id="fromDate" name="fromDate" class="ui-cal js-ui-cal" title="시작일 선택">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" data-role="datepicker" id="toDate" name="toDate" class="ui-cal js-ui-cal" title="종료일 선택">
											</span>
											<span class="btn-group">
												<a href="javascript:void(0);" name="toDay" id="toDay" class="btn-sm btn-func">오늘</a>
												<a href="javascript:void(0);" name="toWeek" id="toWeek" class="btn-sm btn-func">일주일</a>
												<a href="javascript:void(0);" name="toMonth" id="toMonth" class="btn-sm btn-func">한달</a>
												<a href="javascript:void(0);" name="toYear" id="toYear" class="btn-sm btn-func text-center">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" id="appPushReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0);" name="appPushSearch" id="appPushSearch" class="btn-normal btn-func">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->
			</form>
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">Push 메시지 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select name="pageCount" id="pageCount" class="ui-sel">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" class="btn-sm btn-link" id="appPushCreateForm">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="appPushGrid" style="width:100%; height:429px;">
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->
<script src="/static/common/js/biz/cmm/abcmart.cmm.apppush.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>