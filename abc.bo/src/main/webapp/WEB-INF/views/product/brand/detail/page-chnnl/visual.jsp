<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

</script>


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
								<label class="title" for="page-count-brand-page-art-pc-${scope_chnnlNo}-top">목록개수</label>
								<select class="ui-sel" id="page-count-brand-page-art-pc-${scope_chnnlNo}-top">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
							<div class="btn-group">
								<a href="#" class="btn-sm btn-del" data-delete-item="delete-checked" data-type="mallBrandPageVisualPc${scope_chnnlNo}List">선택 항목 삭제</a>
								<a href="#" class="btn-sm btn-func" data-brand-page-item="apply-page-seq" data-site="10000" data-device="10000" data-chnnl="${scope_chnnlNo}">순서변경</a>
							</div>
						</div>
						<div class="fr">
							<a href="#" class="btn-sm btn-func" data-related-sheet="mallBrandPageVisualPc${scope_chnnlNo}List" data-set-type="I" data-chnnl="${scope_chnnlNo}">이미지 추가</a>
							<a href="#" class="btn-sm btn-func" data-related-sheet="mallBrandPageVisualPc${scope_chnnlNo}List" data-set-type="M" data-chnnl="${scope_chnnlNo}">동영상 추가</a>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="mall-brand-page-visual-pc-${scope_chnnlNo}-list" style="width:100%; height:200px;">
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
								<label class="title" for="page-count-brand-page-art-mo-${scope_chnnlNo}-top">목록개수</label>
								<select class="ui-sel" id="page-count-brand-page-art-mo-${scope_chnnlNo}-top">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
							<div class="btn-group">
								<a href="#" class="btn-sm btn-del" data-delete-item="delete-checked" data-type="mallBrandPageVisualMo${scope_chnnlNo}List">선택 항목 삭제</a>
								<a href="#" class="btn-sm btn-func" data-brand-page-item="apply-page-seq" data-site="10000" data-device="10001" data-chnnl="${scope_chnnlNo}">순서변경</a>
							</div>
						</div>
						<div class="fr">
							<a href="#" class="btn-sm btn-func" data-related-sheet="mallBrandPageVisualMo${scope_chnnlNo}List" data-set-type="I" data-chnnl="${scope_chnnlNo}">이미지 추가</a>
							<a href="#" class="btn-sm btn-func" data-related-sheet="mallBrandPageVisualMo${scope_chnnlNo}List" data-set-type="M" data-chnnl="${scope_chnnlNo}">동영상 추가</a>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<div class="ibsheet-wrap">
						<div id="mall-brand-page-visual-mo-${scope_chnnlNo}-list" style="width:100%; height:200px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->


