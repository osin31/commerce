<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 초기값 세팅 --%>
		abc.biz.member.member.point.safeKey = "${safeKey}";
		abc.biz.member.member.point.memberNo = "${memberNo}";
		abc.biz.member.member.point.safeKeySeq = "${safeKeySeq}";
		
		<%-- 회원 포인트 내역 그리드 세팅 --%>
		abc.biz.member.member.point.initPointSheet();
		abc.biz.member.member.point.memberPointDoAction('search');
		
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
		
		<%-- 캘린더 버튼 설정(1년) --%>
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});
		
		<%-- 포인트 관리자 적립/사용 팝업호출 --%>
		$("#savePopBtn").click(function(){
			abc.biz.member.member.point.savePop();
		});
		
		<%-- 포인트 관리자 사후적립 팝업호출 --%>
		$("#expostSavePopBtn").click(function(){
			abc.biz.member.member.point.expostSavePop();
		});
	})
	
	<%-- DataSearch(Row) End 이벤트--%>
	function pointSheet_OnRowSearchEnd(row){
		
		// 8800, 0072 : 온라인
		if(pointSheet.GetCellValue(row,"storeCd") == "8800" || pointSheet.GetCellValue(row,"storeCd") == "0072"){
			pointSheet.SetCellValue(row, "storeCd", "온라인");
		}else{
			pointSheet.SetCellValue(row, "storeCd", "오프라인");
		}
		
		if(pointSheet.GetCellValue(row,"savePoint") < 0){
			pointSheet.SetCellValue(row, "saveType", "사용");
		}else{
			pointSheet.SetCellValue(row, "saveType", "적립");
		}
	}
</script>

<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">포인트 현황</h3>
		<span class="guide-text">(사용가능 포인트 <fmt:formatNumber value="${pointInfo.possPoint + pointInfo.eventPoint}" pattern="#,###"/> Point)</span>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-row -->
<table class="tbl-row">
	<caption>포인트 현황</caption>
	<colgroup>
		<col style="width: 140px;">
		<col>
		<col style="width: 140px;">
		<col>
		<col style="width: 140px;">
		<col>
	</colgroup>
	<tbody>
		<tr>
			<th scope="row">적립예상포인트</th>
			<td><fmt:formatNumber value="${pointInfo.preSavePoint + pointInfo.savePoint}" pattern="#,###"/> Point</td>
			<th scope="row">소멸포인트</th>
			<td><fmt:formatNumber value="${pointInfo.termiPoint}" pattern="#,###"/> Point</td>
			<th scope="row">이벤트포인트</th>
			<td><fmt:formatNumber value="${pointInfo.eventPoint}" pattern="#,###"/> Point</td>
		</tr>
		<tr>
			<th scope="row">구매포인트</th>
			<td colspan="5"><fmt:formatNumber value="${pointInfo.possPoint}" pattern="#,###"/> Point</td>
		</tr>
	</tbody>
</table>
<!-- E : tbl-row -->

<!-- S : content-header -->
<div class="content-header">
	<div class="fl">
		<h3 class="content-title">포인트 내역 검색</h3>
	</div>
</div>
<!-- E : content-header -->

<!-- S : tbl-controller -->
<div class="tbl-controller">
	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
	</form>
	<div class="fl">
		<span class="opt-group">
			<label class="title" for="selView05">목록개수</label>
			<select class="ui-sel" id="pageCount">
				<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
			</select>
		</span>
	</div>
	<!-- DESC : 통합멤버십인 경우에만 노출되는 버튼 영역 -->
	<div class="fr">
		<c:if test="${memberTypeCode eq '10001'}">
			<!-- DESC : html/member/BO-MB-01021.html 파일 확인 해주세요 -->
			<c:if test="${pointBtnView}">
				<a href="javascript:void(0);" id="savePopBtn" class="btn-sm btn-link">포인트 관리자 적립/차감</a>
			</c:if>
			<!-- DESC : html/member/BO-MB-01022.html 파일 확인 해주세요 -->
			<a href="javascript:void(0);" id="expostSavePopBtn" class="btn-sm btn-link">포인트 사후적립</a>
		</c:if>
	</div>
</div>
<!-- E : tbl-controller -->

<!-- S : ibsheet-wrap -->
<div class="ibsheet-wrap">
	<div id="pointGrid" style="width:100%; height:429px;">
	</div>
</div>
<!-- E : ibsheet-wrap -->

<script src="/static/common/js/biz/member/abcmart.member.member.point.js<%=_DP_REV%>">
</script>