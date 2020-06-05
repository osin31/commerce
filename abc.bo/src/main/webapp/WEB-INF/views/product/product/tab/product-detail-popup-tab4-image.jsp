<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="tab-content" id="tab-image">
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">상품이미지</h3>
		</div>
	</div>

	<%-- 상품 대표이미지 --%>
	<table class="tbl-row" id="product-rep-img-area">
		<caption>상품이미지</caption>
		<colgroup>
			<col style="width:130px;"/>
			<col/>
			<col style="width: 100px"/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">
					<span class="th-required">대표 이미지</span>
					<div>권장사이즈 590*590 (10MB까지 등록가능<br/>파일유형 : jpg, jpeg, png, gif, bmp)</div>
				</th>
				<td class="input">
					<c:set var="fileNameImage" value=""/>
					<c:set var="fileUrlImage" value=""/>
					<c:set var="fileAltrnText" value=""/>
					<c:set var="fileSortSeq" value=""/>
					<c:forEach items="${product.productRelationFile }" var="item">
						<c:if test="${item.prdtRltnFileSeq eq 1 }">
							<c:set var="fileNameImage" value="${item.imageName }"/>
							<c:set var="fileUrlImage" value="${item.imageUrl }"/>
							<c:set var="fileAltrnText" value="${item.altrnText }"/>
							<c:set var="fileSortSeq" value="${item.sortSeq }"/>
						</c:if>
					</c:forEach>
					<div class="file-wrap">
						<ul class="file-list">
							<li>
								<span class="btn-box">
									<input type="hidden" name="productRelationFile.prdtRltnFileSeq" value="1"/><%-- 등록 순서. 고정값. --%>
									<input type="hidden" name="productRelationFile.dispPostnType" value="P"/>
									<input type="hidden" name="productRelationFile.fileType" value="I"/><%-- 파일유형. 이미지유형 고정값. --%>
									<input type="file" id="image-file" title="첨부파일 추가" name="productRelationFile.uploadFileImage"/>
									<label for="image-file">찾아보기</label>
									<input type="hidden" name="productRelationFile.imageName" value=""/>
									<input type="hidden" name="productRelationFile.imagePathText" value=""/>
									<input type="hidden" name="productRelationFile.imageUrl" value=""/>
									<input type="hidden" name="productRelationFile.cnnctnType" value=""/>
									<input type="hidden" name="productRelationFile.linkTargetType" value=""/>
									<input type="hidden" name="productRelationFile.linkInfo" value=""/>
									<input type="hidden" name="productRelationFile.uploadYn" value=""/>
									<input type="hidden" name="productRelationFile.movieName" value=""/>
									<input type="hidden" name="productRelationFile.moviePathText" value=""/>
									<input type="hidden" name="productRelationFile.movieUrl" value=""/>
									<input type="file" name="productRelationFile.uploadFileVideo" value="" style="visibility:hidden;"/>
									<input type="hidden" name="productRelationFile.uploadFileNameVideo" value=""/>
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
							<input type="text" class="ui-input" title="이미지 대체 텍스트 입력" name="productRelationFile.altrnText" value="${not empty fileAltrnText ? fileAltrnText : 'MAIN' }" placeholder="이미지 대체 텍스트 입력"/>
						</div>
						<div class="img-wrap" style="width: 150px;">
							<%-- 첨부 이미지 노출 영역 --%>
							<c:if test="${not empty fileUrlImage }">
								<img src="${fileUrlImage }" alt="${fileAltrnTextImage }" onerror="javascript:abc.biz.product.utils.image.noImage(this);"/>
							</c:if>
						</div>
					</div>
				</td>
				<td class="input vertical-middle">
					<input type="number" class="ui-input text-center" title="노출순서 입력" name="productRelationFile.sortSeq" placeholder="노출순서" value="${not empty fileSortSeq ? fileSortSeq : '1' }"/>
				</td>
			</tr>
		</tbody>
	</table>

	<div class="content-header">
		<div class="fl">
			<a href="javascript:void(0);" class="btn-sm btn-del" id="remove-image-tmpl">삭제</a>
		</div>
		<div class="fr">
			<a href="javascript:void(0);" class="btn-sm btn-func" id="add-image-tmpl">추가</a>
		</div>
	</div>

	<%-- 상품 추가이미지 --%>
	<table class="tbl-row" id="product-add-img-area">
		<caption>상품 추가 이미지</caption>
		<colgroup>
			<col style="width:50px;"/>
			<col style="width:130px;"/>
			<col/>
			<col style="width: 100px"/>
		</colgroup>
		<tbody id="append-image-tmpl">
			<c:set var="productAddImageCounter" value="0"/>
			<c:forEach items="${product.productRelationFile }" var="item" varStatus="status">
				<c:if test="${item.prdtRltnFileSeq gt 10 and item.prdtRltnFileSeq lt 20 }">
					<c:set var="productAddImageCounter" value="${productAddImageCounter + 1 }"/><%-- 상품 추가이미지 번호 연산 --%>
					<tr class="add-image-layer">
						<th scope="row" class="only-chk">
							<span class="ui-chk">
								<input id="image-checkbox-${productAddImageCounter }" type="checkbox"/>
								<label for="image-checkbox-${productAddImageCounter }"></label>
							</span>
						</th>
						<th scope="row">추가 이미지 <span data-add-image-number="no">${productAddImageCounter }</span><div>권장사이즈 590*590 (10MB까지 등록가능<br/>파일유형 : jpg, jpeg, png, gif, bmp)</div></th>
						<td class="input">
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<input type="hidden" name="productRelationFile.prdtRltnFileSeq" value="${item.prdtRltnFileSeq }"/><%-- 등록 순서. 고정값. --%>
											<input type="hidden" name="productRelationFile.dispPostnType" value="P"/>
											<input type="hidden" name="productRelationFile.fileType" value="I"/><%-- 이미지 유형 --%>
											<input type="file" id="image-file-${status.index }" title="첨부파일 추가" name="productRelationFile.uploadFileImage"/>
											<label for="image-file-${status.index }">찾아보기</label>
											<input type="hidden" name="productRelationFile.imageName" value=""/>
											<input type="hidden" name="productRelationFile.imagePathText" value=""/>
											<input type="hidden" name="productRelationFile.imageUrl" value=""/>
											<input type="hidden" name="productRelationFile.cnnctnType" value=""/>
											<input type="hidden" name="productRelationFile.linkTargetType" value=""/>
											<input type="hidden" name="productRelationFile.linkInfo" value=""/>
											<input type="hidden" name="productRelationFile.uploadYn" value=""/>
											<input type="hidden" name="productRelationFile.movieName" value=""/>
											<input type="hidden" name="productRelationFile.moviePathText" value=""/>
											<input type="hidden" name="productRelationFile.movieUrl" value=""/>
											<input type="file" name="productRelationFile.uploadFileVideo" value="" style="visibility:hidden;"/>
											<input type="hidden" name="productRelationFile.uploadFileNameVideo" value=""/>
										</span>
									</li>
									<li>
										<input type="hidden" class="subject" name="productRelationFile.uploadFileNameImage" value="${item.imageName }"/>
										<a href="javascript:void(0);" class="subject">${item.imageName }<%-- 파일명 --%></a>
										<button type="button" class="btn-file-del">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<div class="alt-wrap">
									<input type="text" class="ui-input" title="이미지 대체 텍스트 입력" name="productRelationFile.altrnText" placeholder="이미지 대체 텍스트 입력" value="${item.altrnText }"/>
								</div>
								<div class="img-wrap" style="width: 150px;">
									<%-- 첨부 이미지 노출 영역 --%>
									<c:if test="${not empty item.imageUrl }">
										<img src="${item.imageUrl }" alt="${item.altrnText }" onerror="javascript:abc.biz.product.utils.image.noImage(this);"/>
									</c:if>
								</div>
							</div>
						</td>
						<td class="input vertical-middle">
							<input type="text" class="ui-input text-center" title="노출순서 입력" name="productRelationFile.sortSeq" placeholder="노출순서" value="${item.sortSeq }"/>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>

	<script type="text/x-template" id="image-tmpl">
		<tr class="add-image-layer">
			<th scope="row" class="only-chk">
				<span class="ui-chk">
					<input id="image-checkbox" type="checkbox"/>
					<label for="image-checkbox"></label>
				</span>
			</th>
			<th scope="row">추가 이미지 <span data-add-image-number="no"></span><div>권장사이즈 590*590 (10MB까지 등록가능<br/>파일유형 : jpg, jpeg, png, gif, bmp)</div></th>
			<td class="input">
				<div class="file-wrap">
					<ul class="file-list">
						<li>
							<span class="btn-box">
								<input type="hidden" name="productRelationFile.prdtRltnFileSeq" value=""/><%-- 등록 순서. 고정값. --%>
								<input type="hidden" name="productRelationFile.dispPostnType" value="P"/>
								<input type="hidden" name="productRelationFile.fileType" value="I"/><%-- 이미지 유형 --%>
								<input type="file" id="image-file-" title="첨부파일 추가" name="productRelationFile.uploadFileImage"/>
								<label for="image-file-">찾아보기</label>
								<input type="hidden" name="productRelationFile.imageName" value=""/>
								<input type="hidden" name="productRelationFile.imagePathText" value=""/>
								<input type="hidden" name="productRelationFile.imageUrl" value=""/>
								<input type="hidden" name="productRelationFile.cnnctnType" value=""/>
								<input type="hidden" name="productRelationFile.linkTargetType" value=""/>
								<input type="hidden" name="productRelationFile.linkInfo" value=""/>
								<input type="hidden" name="productRelationFile.uploadYn" value=""/>
								<input type="hidden" name="productRelationFile.movieName" value=""/>
								<input type="hidden" name="productRelationFile.moviePathText" value=""/>
								<input type="hidden" name="productRelationFile.movieUrl" value=""/>
								<input type="file" name="productRelationFile.uploadFileVideo" value="" style="visibility:hidden;"/>
								<input type="hidden" name="productRelationFile.uploadFileNameVideo" value=""/>
							</span>
						</li>
						<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
						<li>
							<input type="hidden" class="subject" name="productRelationFile.uploadFileNameImage" value=""/>
							<a href="javascript:void(0);" class="subject"><%-- 파일명 --%></a>
							<button type="button" class="btn-file-del">
								<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
							</button>
						</li>
					</ul>
					<div class="alt-wrap">
						<input type="text" class="ui-input" title="이미지 대체 텍스트 입력" name="productRelationFile.altrnText" placeholder="이미지 대체 텍스트 입력" value=""/>
					</div>
					<div class="img-wrap" style="width: 150px;">
						<%-- 첨부 이미지 노출 영역 --%>
					</div>
				</div>
			</td>
			<td class="input vertical-middle">
				<input type="text" class="ui-input text-center" title="노출순서 입력" name="productRelationFile.sortSeq" placeholder="노출순서" value=""/>
			</td>
		</tr>
	</script>

	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">효과이미지</h3>
		</div>
	</div>

	<table class="tbl-row">
		<caption>효과이미지</caption>
		<colgroup>
			<col style="width:130px;"/>
			<col/>
			<col style="width: 100px"/>
		</colgroup>
		<tbody>
			<tr data-product-property="3d-view">
				<th scope="row">3D 이미지 등록</th>
				<td class="input">
					<c:set var="fileName3d" value=""/>
					<c:set var="fileUrl3d" value=""/>
					<c:set var="fileSortSeq" value=""/>
					<c:forEach items="${product.productRelationFile }" var="item">
						<c:if test="${item.prdtRltnFileSeq eq 21 }">
							<c:set var="fileName3d" value="${item.imageName }"/>
							<c:set var="fileUrl3d" value="${item.linkInfo }"/>
							<c:set var="fileSortSeq" value="${item.sortSeq }"/>
						</c:if>
					</c:forEach>
					<span class="ip-text-box">
						<input type="hidden" name="productRelationFile.prdtRltnFileSeq" value="21"/><%-- 이미지 등록 순서. 고정값. --%>
						<input type="hidden" name="productRelationFile.dispPostnType" value="P"/>
						<input type="hidden" name="productRelationFile.fileType" value="D"/><%-- 이미지 유형 --%>
						<span class="text">3D 이미지명 : </span>
						<input type="text" class="ui-input" placeholder="이미지명" title="3D 이미지명 입력" name="productRelationFile.imageName" value="${fileName3d }"/>
						<input type="hidden" name="productRelationFile.imagePathText" value=""/>
						<input type="hidden" name="productRelationFile.imageUrl" value=""/>
						<input type="hidden" name="productRelationFile.altrnText" value=""/>
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
						<input type="hidden" name="productRelationFile.uploadFileNameImage" value=""/>
						<input type="hidden" name="productRelationFile.uploadFileNameVideo" value=""/>
					</span>
				</td>
				<td class="input vertical-middle">
					<input type="text" class="ui-input text-center" title="노출순서 입력" name="productRelationFile.sortSeq" placeholder="노출순서" value="${fileSortSeq }"/>
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
						<c:if test="${item.prdtRltnFileSeq eq 22 }">
							<c:set var="fileSeq" value="${item.prdtRltnFileSeq }"/>
							<c:set var="fileName" value="${item.linkInfo }"/><%-- 동영상명 --%>
							<c:set var="fileNameImage" value="${item.imageName }"/><%-- 첨부파일(이미지) 이름 --%>
							<c:set var="fileUrlImage" value="${item.imageUrl }"/><%-- 첨부파일(이미지) 경로 --%>
							<c:set var="fileNameVideo" value="${item.movieName }"/><%-- 첨부파일(동영상) 이름 --%>
							<c:set var="fileUrlVideo" value="${item.movieUrl }"/><%-- 첨부파일(동영상) 경로 --%>
							<c:set var="fileUploadYn" value="${item.uploadYn }"/>
							<c:set var="fileAltrnText" value="${item.altrnText }"/>
							<c:set var="fileSortSeq" value="${item.sortSeq }"/>
						</c:if>
					</c:forEach>
					<input type="hidden" id="product-image-upload-seq" name="productRelationFile.prdtRltnFileSeq" value="22"/><%-- 등록 순서. 고정값. --%>
					<input type="hidden" name="productRelationFile.dispPostnType" value="P"/>
					<input type="hidden" name="productRelationFile.fileType" value="M"/><%-- 이미지 유형 --%>
					<input type="hidden" name="productRelationFile.imageName" value=""/>
					<input type="hidden" name="productRelationFile.imagePathText" value=""/>
					<input type="hidden" name="productRelationFile.imageUrl" value=""/>
					<input type="hidden" name="productRelationFile.cnnctnType" value=""/>
					<input type="hidden" name="productRelationFile.linkTargetType" value=""/>
					<input type="hidden" id="product-image-upload-yn" name="productRelationFile.uploadYn" value="${fileUploadYn }"/>
					<ul class="ip-box-list">
						<li>
							<span class="ui-rdo">
								<input type="radio" id="product-image-upload-yn-n" name="productImageUploadYn" value="N" data-toggle="product-image-upload-n"${empty fileUploadYn or fileUploadYn ne 'Y' ? ' checked' : '' }/>
								<label for="product-image-upload-yn-n">URL 입력 </label>
							</span>
						</li>
						<li>
							<span class="ui-rdo">
								<input type="radio" id="product-image-upload-yn-y" name="productImageUploadYn" value="Y" data-toggle="product-image-upload-y"${fileUploadYn eq 'Y' ? ' checked' : '' }/>
								<label for="product-image-upload-yn-y">직접 업로드</label>
							</span>
						</li>
					</ul>
				</td>
				<td class="input vertical-middle" rowspan="3">
					<input type="text" class="ui-input text-center" placeholder="노출순서" title="노출순서 입력" name="productRelationFile.sortSeq" value="${fileSortSeq }"/>
				</td>
			</tr>
			<tr>
				<td class="input">
					<span class="ip-text-box">
						<span class="text">동영상 명 : </span>
						<input type="text" class="ui-input" placeholder="동영상명" title="브랜드 동영상 이름 입력" name="productRelationFile.linkInfo" value="${fileName }"/>
						<input type="hidden" name="productRelationFile.moviePathText" value=""/>

						<%-- URL입력 시 노출 --%>
						<span class="text" data-toggle-item="product-image-upload-n">동영상 경로 : </span>
						<input type="text" class="ui-input url" title="브랜드 동영상 URL 입력" name="productRelationFile.movieUrl" data-toggle-item="product-image-upload-n" value="${fileUploadYn eq 'Y' ? '' : fileUrlVideo }" placeholder="URL 입력 예시 : https://youtu.be/AnCFR0yIKfw"/>

						<%-- 직접업로드 시 노출 --%>
						<span class="text" data-toggle-item="product-image-upload-y">동영상 파일등록 : </span>
						<div class="file-wrap inline" data-toggle-item="product-image-upload-y">
							<ul class="file-list">
								<li>
									<span class="btn-box">
										<input type="file" id="product-image-upload-file-video" title="첨부파일 추가" name="productRelationFile.uploadFileVideo"/>
										<label for="product-image-upload-file-video">찾아보기</label>
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
				</td>
			</tr>
			<tr>
				<td class="input">
					<div class="file-wrap">
						<ul class="file-list">
							<li>
								<span class="btn-box">
									<input type="file" id="product-image-video-upload-image" title="첨부파일 추가" name="productRelationFile.uploadFileImage"/>
									<label for="product-image-video-upload-image">찾아보기</label>
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
							<input type="text" class="ui-input" title="이미지 대체 텍스트 입력" name="productRelationFile.altrnText" placeholder="이미지 대체 텍스트 입력" value="${fileAltrnText }"/>
						</div>
						<div class="img-wrap" style="width: 150px;">
							<%-- 첨부 이미지 노출 영역 --%>
							<c:if test="${not empty fileUrlImage }">
								<img src="${fileUrlImage }" alt="${fileAltrnText }" onerror="javascript:abc.biz.product.utils.image.noImage(this);"/>
							</c:if>
						</div>
					</div>

					<ul class="td-text-list">
						<li>* 등록 가능한 이미지 확장자명은 jpg, jpeg, png, gif, bmp입니다.</li>
					</ul>
				</td>
			</tr>
		</tbody>
	</table>
</div>
