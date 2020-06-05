<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">대량상품등록</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="javascript:void(0);"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>상품관리</li>
						<li>대량상품등록</li>
						<li>상품정보등록</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : tbl-desc-wrap -->
		<div class="tbl-desc-wrap border-box">
			<ol class="tbl-desc-list">
				<li>* 엑셀업로드로 일괄 상품 등록합니다. <span class="tc-red">"필독!!! 아래의 내용을 반드시 숙지하신 후 작업을 하십시오."</span></li>
				<li>1. 먼저 엑셀샘플파일을 다운로드 받으십시오.</li>
				<li>2. 다운로드 받은 엑셀 파일의 각 항목의 유의사항을 참조하여 주세요.</li>
				<li>3. 사이트구분/브랜드/판매상태/표준카테고리/전시채널/전시카테고리/사이즈조견표/상품아이콘(수동) Excel 파일안에 각 항목별 코드번호를 참조하여 주세요.</li>
				<li>4. 편집한 Excel 파일을 찾아보기 버튼을 클릭하여 선택한 후 “업로드”버튼을 클릭하여 시스템에 업로드 합니다.</li>
				<li>5. 상품등록은 200건 단위로 해주시면 됩니다.</li>
				<li>6. 등록하신 상품은 미승인처리되며 각 항목별 이미지등 추가정보를 입력한 후 승인하셔야 정상 판매처리됩니다</li>
			</ol>
			<div class="fr">
				<form action="/product/mass-product/download/excel" id="download-form" method="post">
					<button type="button" class="btn-sm btn-func" id="download-excel">엑셀폼 다운로드</button>
				</form>
			</div>
		</div>
		<!-- E : tbl-desc-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">EXCEL 파일 선택 : </h3>
				<div class="file-wrap">
					<form id="upload-form">
						<ul class="file-list">
							<li>
								<span class="btn-box">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input type="file" id="excel-file" title="첨부파일 추가" name="excelUpload" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
									<label for="excel-file">찾아보기</label>
									<!-- <button id="excel-file">
										<label for="excel-file">찾아보기</label>
									</button> -->
								</span>
							</li>
							<li><span class="subject">선택된 파일 없음</span>
							<!-- S : 선택한 파일이 있는 경우 -->
								<button type="button" class="btn-file-del" style="display: none;">
									<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
								</button>
							</li>
							<!-- E : 선택한 파일이 있는 경우  -->
						</ul>
					</form>
				</div>
				<a href="javascript:void(0);" class="btn-sm btn-func" id="upload-file">파일 업로드</a>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : white-box -->
		<div class="white-box">
			<div class="fl">
				<!-- S : tbl-result-list -->
				<ul class="tbl-result-list">
					<li><span class="opt-name">전체 <span class="cnt tc-red" id="cnt-total">0</span>건</span></li>
					<li><span class="opt-name">등록오류 <span class="cnt tc-red" id="cnt-error">0</span>건</span></li>
					<li><span class="opt-name">정상등록 <span class="cnt tc-red" id="cnt-normal">0</span>건</span></li>
				</ul>
				<!-- E : tbl-result-list -->
			</div>
			<div class="fr">
				<span class="text">정상등록 상품만 일괄등록 가능합니다. </span>
				<div class="btn-group">
					<a href="javascript:void(0);" class="btn-sm btn-func" id="batch-save">일괄등록</a>
				</div>
			</div>
		</div>
		<!-- E : white-box -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">대량상품 등록 대상 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<div class="tbl-controller">
			<div class="fl">
				<!-- S : opt-group -->
				<span class="opt-group">
					<button type="button" class="btn-sm btn-del" id="delete">선택삭제</button>
				</span>
				<!-- E : opt-group -->
			</div>
		</div>

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="mass-product-list"></div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<script type="text/javascript">
	var codes = ${gridCodes };<%-- 판매상태 코드 --%>
</script>
<!-- E : container -->
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.mass.product.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>