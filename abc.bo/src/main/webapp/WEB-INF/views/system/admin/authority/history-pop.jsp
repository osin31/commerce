<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){

		abc.biz.system.admin.authhistory.initHistorySheet();
		
		$("#authGroupHistoryRead").click(function(){
			if(!abc.date.searchValidate()){
				return;
			}
			
			abc.biz.system.admin.authhistory.doActionHistory("search");
		});
		
		$("#authGroupHistoryReset").click(function(){
			$('#frmSearch')[0].reset();
		});
		
		<%-- 캘린더 버튼 설정(일) --%>
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		<%-- 캘린더 버튼 설정(주)--%>
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		<%-- 캘린더 버튼 설정(월) --%>
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});
		
		<%-- 캘린더 버튼 설정(전체) --%>
		$("a[name^=perAll]").click(function(){
			abc.date.all(this);
		});
		
		$("a[name^=per1Month]").trigger("click");
		
		abc.biz.system.admin.authhistory.doActionHistory("search");
	});

</script>

<form id="frmSearch" name="frmSearch">
<body class="window-scroll-none">
	<div class="window-wrap">
		<div class="window-title">
			<h1>권한그룹 변경 이력</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">권한그룹 변경 이력 검색</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>권한그룹 변경 이력 검색</caption>
						<colgroup>
							<col style="width:15%;">
							<col>
							<col style="width:79px;">
							<col style="width:15%;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">권한그룹명</th>
								<td class="input">
									<select id="authNo" name="authNo"  class="ui-sel" title="권한 그룹명 선택">
										<option value="">전체</option>
										<c:forEach var="authGroup" items="${authGroup}">
											<option value="${authGroup.authNo}">${authGroup.authName}</option>
										</c:forEach>
									</select>
								</td>
								<td></td>
								<th scope="row">구분</th>
								<td class="input">
									<select class="ui-sel" title="구분 선택" id="chngField">
										<option value="">전체</option>
										<option value="authName">권한 그룹명 변경</option>
										<option value="authApplySystemType">권한 적용 시스템 변경</option>
										<option value="useYn">사용여부 변경</option>
										<option value="menuNo">권한 메뉴 변경</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">수정일</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<span class="date-box">
											<input type="text" id="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" id="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
										</span>
										<span class="btn-group">
											<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
											<a href="javascript:void(0);" name="perToday" class="btn-sm btn-func">오늘</a>
											<a href="javascript:void(0);" name="perWeek" class="btn-sm btn-func">일주일</a>
											<a href="javascript:void(0);" name="per1Month" class="btn-sm btn-func">한달</a>
											<a href="javascript:void(0);" name="perAll" class="btn-sm btn-func">전체</a>
											<!-- E : 20190114 수정 -->
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</td>
							</tr>
						</tbody>
					</table>

					<div class="confirm-box">
						<div class="fl">
							<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
							<a href="javascript:void(0);" id="authGroupHistoryReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							<!-- E : 20190114 수정 -->
						</div>
						<div class="fr">
							<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
							<a href="javascript:void(0);" id="authGroupHistoryRead" class="btn-normal btn-func">검색</a>
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
					<h2 class="content-title">권한그룹 변경 이력</h2>
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
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="historyGrid" style="width:100%; height:429px;"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
	
<script src="/static/common/js/biz/system/abcmart.system.admin.authgrouphistory.js<%=_DP_REV%>"></script>
</body>


</form>
