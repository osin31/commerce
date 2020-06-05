<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>

<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
		
		<form name="frm">
		<input type="hidden" id="sellStatCodes" value='${sellStatCodes}' />
		<input  type="hidden" name="wbznNo" value="0" />
		<input  type="hidden" name="wbznSeq" id="wbznSeq" value="${dpWebzine.wbznSeq }" />
		
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">OTS 콘텐츠 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
<!-- 						<button type="button" class="btn-favorites"> -->
<!-- 							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button> -->
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시 관리</li>
								<li>OTS 콘텐츠 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">OTS 콘텐츠 등록</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<a href="javascript:void(0);" class="btn-sm btn-func" id="refresh-data"><i class="ico ico-refresh"></i>초기화</a>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>OTS 콘텐츠 등록</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">웹진 유형</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="wbznType" id="radioWebzineType01" ${dpWebzine.wbznType == 'O' or dpWebzine.wbznType == null ? 'checked' :''} value="O" >
											<label for="radioWebzineType01">MAGAZINE</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="wbznType" id="radioWebzineType02" ${dpWebzine.wbznType == 'S' ? 'checked' :''} value="S">
											<label for="radioWebzineType02">STYLING</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="wbznType" id="radioWebzineType03" ${dpWebzine.wbznType == 'E' ? 'checked' :''} value="E">
											<label for="radioWebzineType03">EXCLUSIVE</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<th scope="row">
								<span class="th-required">전시여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="dispYn" id="radioDisplay01" ${dpWebzine.dispYn == 'Y' or dpWebzine.dispYn == null ? 'checked' :''} value="Y">
											<label for="radioDisplay01">전시</label>
										</span>
										<span class="date-box">
										
											<!-- DESC : 전시안함 선택시, input영역 disabled="disabled" 속성 추가 -->
											<fmt:formatDate value="${dpWebzine.dispStartYmd}" pattern="yyyy.MM.dd" var="dispStartYmdDot"/>
											
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일" name="dispStartYmdDot" value="${dispStartYmdDot}" ${dpWebzine.dispYn == 'N' ? 'disabled' :''} />
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="dispYn" id="radioDisplay02" ${dpWebzine.dispYn == 'N' ? 'checked' :''} value="N">
											<label for="radioDisplay02">전시안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">제목</span>
							</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="제목 입력" name="wbznTitleText" value="${dpWebzine.wbznTitleText}">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">PC 목록썸네일</span>
								<div>권장사이즈 590*590 (최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td colspan="3" class="input">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="hidden" id="pcImageName" title="첨부파일 추가" name="pcImageName" value="${dpWebzine.pcImageName}">
												<input type="file" id="pcImageFile" title="첨부파일 추가" name="pcImageFile" value="">
												<label for="pcImageFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="javascript:void(0);" class="subject">${dpWebzine.pcImageName}</a>
											<button type="button" class="btn-file-del" style="${dpWebzine.pcImageName != null and dpWebzine.pcImageName != '' ? '' : 'display: none;' }">
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" name="pcAltrnText" value="${dpWebzine.pcAltrnText }" />
									</div>
									<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
									<div class="img-wrap" id="area-image-preview">
										<c:if test="${dpWebzine.pcImageName != null and dpWebzine.pcImageName != ''}">
											<img alt="${dpWebzine.pcAltrnText}" src="${dpWebzine.pcImageUrl}">
										</c:if>
									</div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">Mobile 목록썸네일</span>
								<div>권장사이즈 660*660 (10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td colspan="3" class="input">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="hidden" id="mobileImageName" title="첨부파일 추가" name="mobileImageName" value="${dpWebzine.pcImageName}">
												<input type="file" id="mobileImageFile" title="첨부파일 추가" name="mobileImageFile" value="">
												<label for="mobileImageFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="javascript:void(0);" class="subject">${dpWebzine.mobileImageName}</a>
											<button type="button" class="btn-file-del" style="${dpWebzine.mobileImageName != null and dpWebzine.mobileImageName != '' ? '' : 'display: none;' }">
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" name="mobileAltrnText" value="${dpWebzine.mobileAltrnText }" />
									</div>
									<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
									<div class="img-wrap">
										<c:if test="${dpWebzine.mobileImageName != null and dpWebzine.mobileImageName != ''}">
											<img alt="${dpWebzine.mobileAltrnText}" src="${dpWebzine.mobileImageUrl}">
										</c:if>
									</div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">콘텐츠 본문</span>
							</th>
							<td class="input" colspan="3">
								<!-- S : editor-wrap -->
								<div class="editor-wrap">
									<textarea name="wbznInfo" id="wbznInfo" rows="10" cols="80">${dpWebzine.wbznInfo}</textarea>
								</div>
								<!-- E : editor-wrap -->
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">연관상품 등록</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<a href="javascript:void(0);" class="btn-sm btn-del" id="del-webzine-product">선택 삭제</a>
							<a href="javascript:void(0);" class="btn-sm btn-link" id="add-webzine-product">등록</a>
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-col -->
				<table class="tbl-col">
					<caption>연관상품 등록</caption>
					<colgroup>
						<col style="width: 5%">
						<col style="width: 8%">
						<col>
						<col style="width: 50%">
						<col style="width: 15%">
						<col>
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="only-chk">
								<span class="ui-chk">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
									<input id="productCheckedAll" type="checkbox" name="chkProdModule">
									<label for="productCheckedAll"></label>
								</span>
							</th>
							<th scope="col">전시순서</th>
							<th scope="col">상품코드</th>
							<th scope="col">상품명</th>
							<th scope="col">카테고리</th>
							<th scope="col">판매상태</th>
						</tr>
					</thead>
					<tbody id="append-product">
					<c:forEach items="${dpWebzineProductList }" var="product" varStatus="status" >
						<tr class="product-layer">
							<td class="only-chk">
								<span class="ui-chk">
									<input id="productChecked${status.index }" type="checkbox" name="chkProdModule">
									<label for="productChecked${status.index }"></label>
								</span>
							</td>
							<td class="input text-center">
								<input type="hidden" name="dpWebzineProduct.prdtNo" value="${product.prdtNo }">
								<input type="text" class="ui-input text-center" value="${empty product.sortSeq ? '0' : product.sortSeq}" title="전시순서 입력" name="dpWebzineProduct.sortSeq">
							</td>
							<td class="text-center"><a href="javascript:void(0);" class="link prdtNo open-product-popup" data-prdt-no="${product.prdtNo }">${product.prdtNo }</a></td>
							<td class="input clear-float">
								<span class="prod-box">
									<span class="thumb-box prdtImage" style="background-image:url(${product.imageUrl});"></span>
									<span class="link prdtName">${product.prdtName }</span>
								</span>
							</td>
							<td class="text-center prdtCategory">${product.ctgrName }</td>
							<td class="text-center sellStatCodeName">${product.sellStatCodeName }</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<!-- E : tbl-col -->

				<table class="tbl-row">
					<caption>MAGAZINE 등록 작성 정보</caption>
					<colgroup>
						<col style="width:130px">
						<col>
						<col style="width:130px">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">작성자</th>
							<td><a href="javascript:abc.adminDetailPopup('${dpWebzine.rgsterNo}');">${UtilsMasking.adminName(dpWebzine.rgsterId, dpWebzine.rgsterName)}</a></td>
							<th scope="row">작성일시</th>
							<td><fmt:formatDate value="${dpWebzine.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td><a href="javascript:abc.adminDetailPopup('${dpWebzine.moderNo}');">${UtilsMasking.adminName(dpWebzine.moderId, dpWebzine.moderName)}</a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${dpWebzine.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
					</tbody>
				</table>
				
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<!-- <div class="fl">
						<a href="javascript:void(0);" class="btn-normal btn-del" id="del-webzine">삭제</a>
					</div> -->
					<div class="fr">
						<a href="/display/contents/ots/" class="btn-normal btn-link" id="go-list">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="javascript:void(0);" class="btn-lg btn-save" id="save-webzine">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->
		</form>
<script type="text/x-template" id="product-tmpl">
<tr>
	<td class="only-chk">
		<span class="ui-chk">
			<input id="productChecked" type="checkbox" name="chkProdModule">
			<label for="productChecked"></label>
		</span>
	</td>
	<td class="input text-center">
		<input type="hidden" name="dpWebzineProduct.prdtNo">
		<input type="text" class="ui-input text-center" value="0" title="전시순서 입력" name="dpWebzineProduct.sortSeq">
	</td>
	<td class="text-center"><a href="javascript:void(0);" class="link prdtNo open-product-popup" data-prdt-no=""></a></td>
	<td class="input clear-float">
		<span class="prod-box">
			<span class="thumb-box prdtImage" style="background-image:url();"></span>
			<span class="link prdtName"></span>
		</span>
	</td>
	<td class="text-center prdtCategory"></td>
	<td class="text-center sellStatCodeName"></td>
	<td class="text-center dispYn"></td>
</tr>
</script>		
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>		
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.contents.ots.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>