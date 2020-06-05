<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="DEVICE_CODE_PC" value="10000"/>
<c:set var="DEVICE_CODE_MOBILE" value="10001"/>
<div class="tab-content" id="tab-description">
	<!-- S : 20190311 수정 // 상세설명 테이블 전면수정 -->
	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>상세설명</caption>
		<colgroup>
			<col style="width:130px;"/>
			<col/>
			<col style="width: 100px"/>
		</colgroup>
		<tbody>
			<tr data-brand-visual="none" style="display:none;">
				<th scope="row">브랜드 상세 이미지 설정 여부</th>
				<td colspan="2">설정된 브랜드 상세이미지가 없습니다.</td>
			</tr>
			<tr data-brand-visual="exist" style="display:none;">
				<th scope="row">브랜드 상세 이미지 설정 여부</th>
				<td class="input" colspan="2">
					<span class="prod-box">
						<span class="thumb-box" style="background-image:url(../../static/images/temp/temp_prod_img.jpg);" data-brand="image"></span>
						<span class="text"><span data-brand="title">제목</span>&nbsp;(<span data-brand="image-name">이미지명</span>)&nbsp;/&nbsp;<span data-brand="display-status">전시안함</span></span>
					</span>
				</td>
			</tr>
			<tr data-product-property="3d-view">
				<th scope="row">3D 이미지 등록</th>
				<td class="input">
					<c:set var="fileName3d" value=""/>
					<c:set var="fileUrl3d" value=""/>
					<c:set var="fileSortSeq" value=""/>
					<c:forEach items="${product.productRelationFile }" var="item">
						<c:if test="${item.prdtRltnFileSeq eq 31 }">
							<c:set var="fileName3d" value="${item.imageName }"/>
							<c:set var="fileUrl3d" value="${item.linkInfo }"/>
							<c:set var="fileSortSeq" value="${item.sortSeq }"/>
						</c:if>
					</c:forEach>
					<span class="ip-text-box">
						<input type="hidden" name="productRelationFile.prdtRltnFileSeq" value="31"/><%-- 이미지 등록 순서. 고정값. --%>
						<input type="hidden" name="productRelationFile.dispPostnType" value="D"/>
						<input type="hidden" name="productRelationFile.fileType" value="D"/><%-- 이미지 유형 --%>
						<span class="text">3D 이미지명 : </span>
						<input type="text" class="ui-input" title="3D 이미지명 입력" name="productRelationFile.imageName" placeholder="이미지명" value="${fileName3d }"/>
						<input type="hidden" name="productRelationFile.imagePathText" value=""/>
						<input type="hidden" name="productRelationFile.imageUrl" value=""/>
						<span class="text">3D 코드입력 : </span>
						<input type="text" class="ui-input url" title="3D 경로입력" name="productRelationFile.linkInfo" placeholder="18FS5NI001" value="${fileUrl3d }"/>
						<input type="hidden" name="productRelationFile.cnnctnType" value=""/>
						<input type="hidden" name="productRelationFile.linkTargetType" value=""/>
						<input type="hidden" name="productRelationFile.uploadYn" value=""/>
						<input type="hidden" name="productRelationFile.movieName" value=""/>
						<input type="hidden" name="productRelationFile.moviePathText" value=""/>
						<input type="hidden" name="productRelationFile.movieUrl" value=""/>
						<input type="file" name="productRelationFile.uploadFileImage" value="" style="visibility:hidden;"/>
						<input type="file" name="productRelationFile.uploadFileVideo" value="" style="visibility:hidden;"/>
						<input type="hidden" name="productRelationFile.altrnText" value=""/>
						<input type="hidden" name="productRelationFile.uploadFileNameImage" value=""/>
						<input type="hidden" name="productRelationFile.uploadFileNameVideo" value=""/>
					</span>
					<!-- E : ip-text-box -->
				</td>
				<td class="input vertical-middle">
					<input type="number" class="ui-input text-center" title="노출순서 입력" name="productRelationFile.sortSeq" placeholder="노출순서" value="${fileSortSeq }"/>
				</td>
			</tr>
			<tr>
				<th scope="row" rowspan="3">동영상 등록</th>
				<td class="input">
					<c:set var="fileSeq" value=""/>
					<c:set var="fileName" value=""/>
					<c:set var="fileNameImage" value=""/>
					<c:set var="fileUrlImage" value=""/>
					<c:set var="fileNameVideo" value=""/>
					<c:set var="fileUrlVideo" value=""/>
					<c:set var="fileUploadYn" value=""/>
					<c:set var="fileAltrnText" value=""/>
					<c:set var="fileSortSeq" value=""/>
					<c:forEach items="${product.productRelationFile }" var="item">
						<c:if test="${item.prdtRltnFileSeq eq 32 }">
							<c:set var="fileSeq" value="${item.prdtRltnFileSeq }"/>
							<c:set var="fileName" value="${item.linkInfo }"/>
							<c:set var="fileNameImage" value="${item.imageName }"/>
							<c:set var="fileUrlImage" value="${item.imageUrl }"/>
							<c:set var="fileNameVideo" value="${item.movieName }"/>
							<c:set var="fileUrlVideo" value="${item.movieUrl }"/>
							<c:set var="fileUploadYn" value="${item.uploadYn }"/>
							<c:set var="fileAltrnText" value="${item.altrnText }"/>
							<c:set var="fileSortSeq" value="${item.sortSeq }"/>
						</c:if>
					</c:forEach>
					<input type="hidden" id="product-description-upload-seq" name="productRelationFile.prdtRltnFileSeq" value="32"/><%-- 등록 순서. 고정값. --%>
					<input type="hidden" name="productRelationFile.dispPostnType" value="D"/>
					<input type="hidden" name="productRelationFile.fileType" value="M"/><%-- 이미지 유형 --%>
					<input type="hidden" name="productRelationFile.imageName" value=""/>
					<input type="hidden" name="productRelationFile.imagePathText" value=""/>
					<input type="hidden"name="productRelationFile.imageUrl" value=""/>
					<input type="hidden" name="productRelationFile.cnnctnType" value=""/>
					<input type="hidden" name="productRelationFile.linkTargetType" value=""/>
					<input type="hidden" id="product-description-upload-yn" name="productRelationFile.uploadYn" value="${fileUploadYn }"/>
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input id="product-description-upload-yn-n" name="productDescriptionUploadYn" type="radio" value="N" data-toggle="product-description-upload-n"${empty fileUploadYn or fileUploadYn ne 'Y' ? ' checked' : '' }/>
								<label for="product-description-upload-yn-n">URL 입력</label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input id="product-description-upload-yn-y" name="productDescriptionUploadYn" type="radio" value="Y" data-toggle="product-description-upload-y"${fileUploadYn eq 'Y' ? ' checked' : '' }/>
								<label for="product-description-upload-yn-y">직접 업로드</label>
							</span>
						</li>
					</ul>
				</td>
				<td class="input vertical-middle" rowspan="3">
					<input type="number" class="ui-input text-center" title="노출순서 입력" name="productRelationFile.sortSeq" placeholder="노출순서" value="${fileSortSeq }"/>
				</td>
			</tr>
			<tr>
				<td class="input">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<span class="text">동영상 명 : </span>
						<input type="text" class="ui-input" placeholder="동영상명" title="브랜드 동영상 이름 입력" name="productRelationFile.linkInfo" value="${fileName }"/>
						<input type="hidden" name="productRelationFile.moviePathText" value=""/>

						<%-- URL입력 시 노출 --%>
						<span class="text" data-toggle-item="product-description-upload-n">동영상 경로 : </span>
						<input type="text" class="ui-input url" data-toggle-item="product-description-upload-n" title="브랜드 동영상 URL 입력" name="productRelationFile.movieUrl" value="${fileUploadYn eq 'Y' ? '' : fileUrlVideo }" placeholder="URL 입력 예시 : https://youtu.be/AnCFR0yIKfw" />

						<%-- 직접업로드 시 노출 --%>
						<span class="text" data-toggle-item="product-description-upload-y">동영상 파일등록 : </span>
						<div class="file-wrap inline" data-toggle-item="product-description-upload-y">
							<ul class="file-list">
								<li>
									<span class="btn-box">
										<input type="file" id="product-description-upload-file-video" title="첨부파일 추가" name="productRelationFile.uploadFileVideo"/>
										<label for="product-description-upload-file-video">찾아보기</label>
									</span>
								</li>
								<li>
									<input type="hidden" class="subject" name="productRelationFile.uploadFileNameVideo" value="${fileNameVideo }"/>
									<span class="subject">${fileNameVideo }<%-- 파일명 --%></span>
									<button type="button" class="btn-file-del">
										<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
									</button>
								</li>
								<li>* 첨부가능한 확장자는 mp4, webm 입니다.</li>
							</ul>
						</div>
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
			<tr>
				<td class="input">
					<div class="file-wrap">
						<ul class="file-list">
							<li>
							<span class="btn-box">
								<input type="file" id="product-description-video-upload-image" title="첨부파일 추가" name="productRelationFile.uploadFileImage"/>
								<label for="product-description-video-upload-image">찾아보기</label>
							</span>
							</li>
							<li>
								<input type="hidden" class="subject" name="productRelationFile.uploadFileNameImage" value="${fileNameImage }"/>
								<a href="javascript:void(0);" class="subject">${fileNameImage }<%-- 파일명 --%></a>
								<button type="button" class="btn-file-del">
									<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
								</button>
							</li>
						</ul>
						<div class="alt-wrap">
							<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="productRelationFile.altrnText" value="${fileAltrnText }"/>
						</div>
						<div class="img-wrap" style="width: 150px;">
							<%-- 첨부 이미지 노출 영역 --%>
							<c:if test="${not empty fileUrlImage }">
								<img src="${fileUrlImage }" alt="${fileAltrnText }" onerror="javascript:abc.biz.product.utils.image.noImage(this);"/>
							</c:if>
						</div>
					</div>

					<ul class="td-text-list">
						<li>* 등록 가능한 확장자명은 jpg, jpeg, png, gif, bmp입니다.</li>
					</ul>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->
	<!-- E : 20190311 수정 // 상세설명 테이블 전면수정 -->

	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">상세설명</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>상세설명</caption>
		<colgroup>
			<col style="width: 130px;">
			<col>
			<col style="width: 100px"/>
		</colgroup>
		<tbody>
			<c:set var="productDetailPcPrdtDtlInfo" value=""/>
			<c:set var="productDetailPcSortSeq" value=""/>
			<c:set var="productDetailMcPrdtDtlInfo" value=""/>
			<c:set var="productDetailMcSortSeq" value=""/>
			<c:if test="${not empty product and not empty product.productDetail }">
				<c:forEach items="${product.productDetail }" var="item">
					<c:if test="${DEVICE_CODE_PC eq item.deviceCode }">
						<c:set var="productDetailPcPrdtDtlInfo" value="${item.prdtDtlInfo }"/>
						<c:set var="productDetailPcSortSeq" value="${item.sortSeq }"/>
					</c:if>
					<c:if test="${DEVICE_CODE_MOBILE eq item.deviceCode }">
						<c:set var="productDetailMcPrdtDtlInfo" value="${item.prdtDtlInfo }"/>
						<c:set var="productDetailMcSortSeq" value="${item.sortSeq }"/>
					</c:if>
				</c:forEach>
			</c:if>
			<tr>
				<th scope="row">상세설명 (PC)</th>
				<td class="input" colspan="2">
					<div class="editor-wrap">
						<input type="hidden" name="productDetail.divider" value=""/>
						<input type="hidden" name="productDetail.deviceCode" value="${DEVICE_CODE_PC }"/>
						<textarea id="prdt-dtl-info-pc" name="productDetail.prdtDtlInfo" rows="10" cols="80">${productDetailPcPrdtDtlInfo }</textarea>
						<input type="hidden" name="productDetail.sortSeq" placeholder="노출순서" value="1"/>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">상세설명 (Mobile)</th>
				<td class="input" colspan="2">
					<div class="editor-wrap">
						<input type="hidden" name="productDetail.divider" value=""/>
						<input type="hidden" name="productDetail.deviceCode" value="${DEVICE_CODE_MOBILE }"/>
						<textarea id="prdt-dtl-info-mobile" name="productDetail.prdtDtlInfo" rows="10" cols="80">${productDetailMcPrdtDtlInfo }</textarea>
						<input type="hidden" name="productDetail.sortSeq" value="1"/>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->

	<!-- S : content-header -->
<%-- 	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">상세설명 (Mobile)</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>상세설명 (Mobile)</caption>
		<colgroup>
			<col style="width: 130px;">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">내용</th>
				<td class="input">
					<!-- S : editor-wrap -->
					<div class="editor-wrap">
						<input type="hidden" name="productDetail.divider" value=""/>
						<input type="hidden" name="productDetail.deviceCode" value="${DEVICE_CODE_MOBILE }"/>
						<textarea id="prdt-dtl-info-mobile" name="productDetail.prdtDtlInfo" rows="10" cols="80">
							<c:if test="${not empty product and not empty product.productDetail }">
								<c:forEach items="${product.productDetail }" var="item">
									<c:if test="${DEVICE_CODE_MOBILE eq item.deviceCode }">
										${item.prdtDtlInfo }
									</c:if>
								</c:forEach>
							</c:if>
						</textarea>
					</div>
					<!-- E : editor-wrap -->
				</td>
			</tr>
		</tbody>
	</table> --%>
	<!-- E : tbl-row -->
</div>
