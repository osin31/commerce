<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

</script>

				<!-- S:tab_content -->
				<div id="tabBrandProductDetail" class="tab-content">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">브랜드 상품상세 비주얼 정보 (통합몰에만 전시됩니다. )</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>브랜드 상품상세 비주얼 정보</caption>
						<colgroup>
							<col style="width:160px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">브랜드 상품상세 비주얼</th>
								<td>* 대상상품의 상품상세 설명 상단에 노출되는 정보를 이미지로 등록할 수 있습니다. (1200*자율px, jpg, jpeg, png, gif, bmp)</td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->

					<!-- S : tbl-controller -->
					<div class="tbl-controller">
						<div class="fl">
							<a href="#" class="btn-sm btn-del" data-delete-item="delete-checked" data-type="detailVisualList">선택 삭제</a>
						</div>
						<div class="fr">
							<a href="#" class="btn-sm btn-func" data-related-sheet="detailVisualList">행 추가</a>
						</div>
					</div>
					<!-- E : tbl-controller -->

					<!-- S : ibsheet-wrap -->
					<form id="visualForm" name="visualForm">
						<input type="hidden" name="brandNo" id="vBrandNo" value="">
						<input type="hidden" name="brandBannerSeq" id="vBrandBannerSeq" value="">
						<input type="hidden" name="brandName" id="vBrandName" value="">
					</form>
					<div class="ibsheet-wrap">
						<div id="detail-visual-list" style="width:100%; height:200px;">
						</div>
					</div>
					<!-- E : ibsheet-wrap -->
				</div>
				<!-- E:tab_content -->
