<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(function(){
		abc.biz.system.batch.siteCombo = ${siteCombo};
		abc.biz.system.batch.codeCombo = ${codeCombo};

		abc.biz.system.batch.initBatch();
	});

	// main batch selectCell 이벤트
	function mainBatchSheet_OnSelectCell(oldRow, oldCol, newRow, newCol, isDelete) {
		var data = mainBatchSheet.GetRowData(newRow);
		abc.biz.system.batch.setMainBatchForm(data);
		historyLogSheet.RemoveAll();
		jobInstanceSheet.RemoveAll();
		stepExecutionSheet.RemoveAll();
	}

	// main batch change 이벤트
	function mainBatchSheet_OnChange(row, col, value, oldValue, raiseFlag) {
		if (mainBatchSheet.ColSaveName(col) != "checkbox") {
			mainBatchSheet.SetCellValue(row, 0, true);
		}
	}

	function mainBatchSheet_OnEditValidation(row, col, value) {
		if (mainBatchSheet.ColSaveName(col) == "jobName") {
	        if (abc.text.isBlank(value)) {
	        	alert("업무명을 입력해 주십시요.");
	        	mainBatchSheet.ValidateFail(1);
	        }
	        if (abc.text.isLimitLength(value, 100)) {
	        	alert("업무명이 최대값을 초과하였습니다.");
	        	mainBatchSheet.ValidateFail(1);
	        }
	    }

		if (mainBatchSheet.ColSaveName(col) == "triggerName") {
			if (abc.text.isBlank(value)) {
				alert("배치명을 입력해 주십시요.");
				mainBatchSheet.ValidateFail(1);
			}
			if (abc.text.isLimitLength(value, 100)) {
				alert("배치명이 최대값을 초과하였습니다.");
				mainBatchSheet.ValidateFail(1);
			}
	    }

		if (mainBatchSheet.ColSaveName(col) == "cronExpression") {
			if (abc.text.isBlank(value)) {
				alert("일정 표현식을 입력해 주십시요.");
				mainBatchSheet.ValidateFail(1);
			}
	    }
	}

	// history selectCell 이벤트
	function historySheet_OnSelectCell(oldRow, oldCol, newRow, newCol, isDelete) {
		stepExecutionSheet.RemoveAll();
		var data = historySheet.GetRowData(newRow);
		abc.biz.system.batch.setHistoryForm(data);
	}

	function jobInstanceSheet_OnSelectCell(oldRow, oldCol, newRow, newCol, isDelete) {
		var data = jobInstanceSheet.GetRowData(newRow);
		abc.biz.system.batch.setJobInstanceForm(data);
	}
</script>
		<form name="batchForm">
			<input type="hidden" id="siteId" name="siteId">
			<input type="hidden" id="triggerName" name="triggerName">
			<input type="hidden" id="triggerGroup" name="triggerGroup">
			<input type="hidden" id="schedId" name="schedId">
			<input type="hidden" id="schedulerHistoryId" name="schedulerHistoryId">
			<input type="hidden" id="jobExecutionId" name="jobExecutionId">
		</form>
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">배치관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>시스템 관리</li>
								<li>배치관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<a href="#" class="btn-sm btn-func" id="resumeBtn">시작</a>
						<a href="#" class="btn-sm btn-func" id="pauseBtn">중지</a>
						<a href="#" class="btn-sm btn-func" id="executeBtn">실행</a>
						<a href="#" class="btn-sm btn-del" id="deleteBtn">삭제</a>
						<a href="#" class="btn-sm btn-save" id="saveBtn">저장</a>
						<!-- E : 20190114 수정 -->
					</div>
					<div class="fr">
						<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
						<!-- DESC : html/common/BO-CM-21001.html 파일 확인 해주세요. -->
						<!-- 배치 업무는 코드화 하지 않고 입력으로 수정. -->
						<!-- <a href="#" class="btn-sm btn-link" id="saveJobBtn">배치 업무 등록</a> -->
						<!-- DESC : html/common/BO-CM-21002.html 파일 확인 해주세요. -->
						<a href="#" class="btn-sm btn-link" id="saveScheduleBtn">배치 업무별 일정 등록</a>
						<!-- E : 20190114 수정 -->
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">배치 실행 일정 정보</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="mainBatchGrid"></div>
				</div>
				<!-- E : ibsheet-wrap -->

				<!-- S : col-wrap -->
				<div class="col-wrap">
					<div class="col">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">일정 실행정보</h3>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : ibsheet-wrap -->
						<div class="ibsheet-wrap full-height">
							<div id="historyGrid"></div>
						</div>
						<!-- E : ibsheet-wrap -->
					</div>
					<div class="col">
						<!-- S : row-wrap -->
						<div class="row-wrap">
							<div class="row">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">일정 실행 로그</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : ibsheet-wrap -->
								<div class="ibsheet-wrap">
									<div id="historyLogGrid"></div>
								</div>
								<!-- E : ibsheet-wrap -->
							</div>
							<div class="row">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">배치 실행 정보</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : ibsheet-wrap -->
								<div class="ibsheet-wrap">
									<div id="jobInstanceGrid"></div>
								</div>
								<!-- E : ibsheet-wrap -->
							</div>
							<div class="row">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">배치 실행 단계별 상세 정보</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : ibsheet-wrap -->
								<div class="ibsheet-wrap">
									<div id="stepExecutionGrid"></div>
								</div>
								<!-- E : ibsheet-wrap -->
							</div>
						</div>
						<!-- E : row-wrap -->
					</div>
				</div>
				<!-- E : col-wrap -->


			</div>
		</div>
		<!-- E : container -->

<script src="/static/common/js/biz/system/abcmart.system.batch.js<%=_DP_REV%>"></script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>