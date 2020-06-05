<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

</script>

				<!-- S:tab_content -->
				<div id="tabBrandPageOts" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">브랜드 페이지 상단 비주얼 전시정보(PC - 1200*388px)</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<!-- S : opt-group -->
							<span class="opt-group">
								<label class="title" for="page-count-brand-page-ots-pc-top">목록개수</label>
								<select class="ui-sel" id="page-count-brand-page-ots-pc-top">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
							<div class="btn-group">
								<a href="#" class="btn-sm btn-del" data-delete-item="delete-checked" data-type="otsBrandPageVisualPcList">선택 항목 삭제</a>
								<a href="#" class="btn-sm btn-func" data-brand-page-item="apply-page-seq" data-site="10001" data-device="10000">순서변경</a>
							</div>
						</div>
						<div class="fr">
							<a href="#" class="btn-sm btn-func" data-related-sheet="otsBrandPageVisualPcList" data-set-type="I">이미지 추가</a>
							<a href="#" class="btn-sm btn-func" data-related-sheet="otsBrandPageVisualPcList" data-set-type="M">동영상 추가</a>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="ots-brand-page-visual-pc-list" style="width:100%; height:200px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->


					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">브랜드 페이지 상단 비주얼 전시정보(Mobile – 720*360px)</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<!-- S : opt-group -->
							<span class="opt-group">
								<label class="title" for="page-count-brand-page-ots-mo-top">목록개수</label>
								<select class="ui-sel" id="page-count-brand-page-ots-mo-top">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
							<div class="btn-group">
								<a href="#" class="btn-sm btn-del" data-delete-item="delete-checked" data-type="otsBrandPageVisualMoList">선택 항목 삭제</a>
								<a href="#" class="btn-sm btn-func" data-brand-page-item="apply-page-seq" data-site="10001" data-device="10001">순서변경</a>
							</div>
						</div>
						<div class="fr">
							<a href="#" class="btn-sm btn-func" data-related-sheet="otsBrandPageVisualMoList" data-set-type="I">이미지 추가</a>
							<a href="#" class="btn-sm btn-func" data-related-sheet="otsBrandPageVisualMoList" data-set-type="O">동영상 추가</a>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="ots-brand-page-visual-mo-list" style="width:100%; height:200px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
