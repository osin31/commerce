<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<form id="form-freeGift">
	<!-- S : container -->
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">사은품관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>상품관리</li>
							<li>상품관리</li>
							<li>사은품관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">사은품 설정정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>사은품 설정정보</caption>
				<colgroup>
					<col style="width:130px;">
					<col>
					<col style="width:130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row"><span class="th-required">사은품명</span></th>
						<td class="input">
							<input type="text" class="ui-input" title="사은품명 입력" name="prdtName" value="${freeGift.prdtName}">
							<input type="hidden" name="productOption.status" />
						</td>
						<th scope="row">사은품아이디</th>
						<td>
							${empty freeGift.prdtNo ? '사은품아이디 (등록 후 코드 채번)' : freeGift.prdtNo}
							<input type="hidden" id="prdt-no" name="prdtNo" value="${freeGift.prdtNo }"/>
							<input type="hidden" id="prdt-optn-no" name="productOption.prdtOptnNo" value="${freeGiftOption.prdtOptnNo }"/>
						</td>
					</tr>
					<tr>
						<th scope="row"><span class="th-required">전시여부</span></th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="disp-yn-y" name="dispYn" type="radio" value="Y" ${freeGift.dispYn eq 'Y' || empty freeGift.dispYn ? 'checked' : ''}>
										<label for="disp-yn-y">전시</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="disp-yn-n" name="dispYn" type="radio" value="N" ${freeGift.dispYn eq 'N' ? 'checked' : ''}>
										<label for="disp-yn-n">미전시</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<th scope="row"><span class="th-required">사용여부</span></th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse01" name="productOption.useYn" type="radio" value="Y" ${freeGiftOption.useYn eq 'Y' || empty freeGiftOption.useYn ? 'checked' : ''}>
										<label for="radioUse01">사용</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioUse02" name="productOption.useYn" type="radio" value="N" ${freeGiftOption.useYn eq 'N' ? 'checked' : ''}>
										<label for="radioUse02">사용안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row"><span class="th-required">재고수량</span></th>
						<td class="input">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" class="ui-input" title="재고수량 입력" name="productOption.totalStockQty" value="<fmt:formatNumber value="${freeGiftOption.totalStockQty}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>" maxlength="6">
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="chkTotalStockQty" type="checkbox" ${99999 <= freeGiftOption.totalStockQty ? 'checked' : ''}>
									<label for="chkTotalStockQty">무제한</label>
								</span>
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">사은품 배너 이미지(PC)</span>
							<div>권장사이즈 1200*100(최대 10MB까지 등록가능, 파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input" colspan="3">
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="hidden" name="productRelationFile.prdtRltnFileSeq" value="41"/>
											<input type="hidden" name="productRelationFile.imageName" id="pcBannerImgName" value="${pcBannerImg.imageName}">
											<input type="file" id="pcBannerImg" name="productRelationFile.uploadFileImage" title="첨부파일 추가">
											<label for="pcBannerImg">찾아보기</label>
										</span>
									</li>
									<li>
										<span class="subject">${pcBannerImg.imageName}</span>
										<button type="button" class="btn-file-del" style="${not empty pcBannerImg.imageName ? '' : 'display: none;' }">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<div class="alt-wrap">
									<input type="text" name="productRelationFile.altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${pcBannerImg.altrnText}">
								</div>
								<div class="img-wrap"><img alt="${pcBannerImg.altrnText}" src="${pcBannerImg.imageUrl}"></div>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">사은품 배너 이미지(Mobile)</span>
							<div>권장사이즈 660*200(최대 10MB까지 등록가능, 파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input" colspan="3">
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="hidden" name="productRelationFile.prdtRltnFileSeq" value="42"/>
											<input type="hidden" name="productRelationFile.imageName" id="moBannerImgName" value="${moBannerImg.imageName}">
											<input type="file" id="moBannerImg" name="productRelationFile.uploadFileImage" title="첨부파일 추가">
											<label for="moBannerImg">찾아보기</label>
										</span>
									</li>
									<li>
										<span class="subject">${moBannerImg.imageName}</span>
										<button type="button" class="btn-file-del" style="${not empty moBannerImg.imageName ? '' : 'display: none;' }">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<div class="alt-wrap">
									<input type="text" name="productRelationFile.altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" value="${moBannerImg.altrnText}">
								</div>
								<div class="img-wrap"><img alt="${moBannerImg.altrnText}" src="${moBannerImg.imageUrl}"></div>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row">수정자</th>
						<td><a href="javascript:abc.adminDetailPopup('${freeGiftOption.moderNo}');" class="link">${UtilsMasking.adminName(freeGiftOption.moderId, freeGiftOption.moderName) }</a></td>
						<th scope="row">수정일시</th>
						<td><fmt:formatDate value="${freeGiftOption.modDtm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<!--
				<div class="fl">
					<a href="#" class="btn-normal btn-del">삭제</a>
				</div>
				-->
				<div class="fr">
					<a href="/product/freeGift" class="btn-normal btn-link">목록</a>
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : content-bottom -->
			<div class="content-bottom">
				<a href="#" class="btn-lg btn-save" id="save">저장</a>
			</div>
			<!-- E : content-bottom -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">사은품 프로모션 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="page-count">목록개수</label>
						<select class="ui-sel" id="page-count">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="freeGift-promotion-list">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
	<!-- E : container -->
</form>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.freeGift.detail.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>