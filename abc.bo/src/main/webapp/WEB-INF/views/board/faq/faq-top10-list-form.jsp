<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/common/subHeader.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){	
		abc.biz.board.faq.top10GridSheet();
		
	});
	
	function top10GridSheet_OnBeforeCheck(Row, Col) {
		if ( Row != 0) {
			if(top10GridSheet.GetCellValue(Row, "radio") == 0){
				abc.biz.board.faq.currentTop10Seq = Row;
			}else{
				abc.biz.board.faq.currentTop10Seq = null;
			}
		}
	}
	
	function top10GridSheet_OnRowSearchEnd(Row) {
		if ( Row <= 10) {
			top10GridSheet.SetRowBackColor(Row,"#FFFFFF");
		}
	}
	
	function top10GridSheet_OnSaveEnd(code, msg, StCode, StMsg, Response){
		if(StCode == "200"){
			alert("저장되었습니다.");
			opener.parent.abc.biz.board.faq.doAction("search");
			abc.biz.board.faq.top10Search();
		}else{
			alert("에러가 발생하였습니다. - " + msg);
		}
	}
	
	
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>TOP10 순위 설정</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">TOP10 순위 설정</h3>
				</div>
				<div class="fr">
					<span class="guide-text">1위 ~ 10위까지만 FAQ TOP 10으로 노출됩니다.</span> 
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : 20190228 추가 // 순서변경 버튼 추가 -->
			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<button type="button" class="btn-sm btn-only-ico" id="downBtn"><i class="fa fa-caret-down"></i></button>
					<button type="button" class="btn-sm btn-only-ico" id="upBtn"><i class="fa fa-caret-up"></i></button>
					<button type="button" class="btn-sm btn-save" id="top10SaveBtn">저장</button>
				</div>
				<!-- S : 20190830 수정 -->
				<div class="fr">
					<button id="top10cancel" class="btn-sm btn-del">삭제</button>
				</div>
				<!-- E : 20190830 수정 -->
			</div>
			<!-- E : tbl-controller -->
			<!-- E : 20190228 추가 // 순서변경 버튼 추가 -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="top10Grid">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- DESC : 20190228 삭제 // 저장버튼 위치 변경 -->
		</div>
	</div>
</body>
<script src="/static/common/js/biz/board/abcmart.board.faq.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/subFooter.jsp"%>
