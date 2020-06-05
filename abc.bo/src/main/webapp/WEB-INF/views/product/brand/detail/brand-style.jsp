<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

</script>

				<!-- S:tab_content -->
				<div id="tabBrandStlye" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">브랜드 스타일 정보 (통합몰에만 전시됩니다. )</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<!-- S : opt-group -->
							<span class="opt-group">
								<label class="title" for="page-count-brand-style">목록개수</label>
								<select class="ui-sel" id="page-count-brand-style">
									<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
								</select>
							</span>
							<!-- E : opt-group -->
							<div class="btn-group">
								<a href="#" class="btn-sm btn-del" data-delete-item="delete-checked" data-type="brandStyleList">선택 항목 삭제</a>
								<a href="#" class="btn-sm btn-save" data-brand-style-item="apply-sort-seq">순서저장</a>
							</div>
						</div>
						<div class="fr">
							<a href="#" class="btn-sm btn-func" data-related-sheet="brandStyleList">행 추가</a>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<form id="styleForm" name="styleForm">
						<input type="hidden" name="brandNo" id="sBrandNo" value="">
						<input type="hidden" name="brandStyleSeq" id="sBrandStyleSeq" value="">
						<input type="hidden" name="brandName" id="sBrandName" value="">
					</form>
					<div class="ibsheet-wrap">
						<div id="brand-style-list" style="width:100%; height:200px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
