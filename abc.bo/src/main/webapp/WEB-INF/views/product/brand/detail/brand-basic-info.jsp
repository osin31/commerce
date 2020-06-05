<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

</script>

				<!-- S:tab_content -->
				<div id="tabBrandInfo" class="tab-content">
					<form id="form-brand">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">브랜드  기본정보</h3>
						</div>
					</div>
					<!-- E : content-header -->
					<table class="tbl-row">
						<caption>브랜드 기본정보</caption>
						<colgroup>
							<col style="width:160px;">
							<col>
							<col style="width:160px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									<span class="th-required">브랜드 ID (ARS)</span>
								</th>
								<td>
								<c:choose>
									<c:when test="${brandInfoStatus != 'U'}">
										<input type="text" id="brand-no" name="brandNo" class="ui-input" title="브랜드ID" value="" maxlength="6" placeholder="브랜드ID"/>
									</c:when>
									<c:otherwise>
										${brand.brandNo}
										<input type="hidden" id="brand-no" name="brandNo" class="ui-input" title="브랜드ID" value="${brand.brandNo}"/>
									</c:otherwise>
								</c:choose>
								</td>
								<th scope="row">
									<span class="th-required">온라인코드</span>
								</th>
								<td class="input">
									<input type="text" id="brand-group-no-text" name="brandGroupNoText" class="ui-input" maxlength="10" title="온라인 코드 입력" value="${brand.brandGroupNoText}" placeholder="온라인 코드"/>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">브랜드명 (국문 / 영문)</span>
								</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<div class="ip-text-box">
										<input type="text" class="ui-input" title="국문 브랜드 명 입력" placeholder="브랜드명 (국문)" maxlength="50" name="brandName" value="${brand.brandName}"/>
										<input type="text" class="ui-input" title="영문 브랜드 명 입력" placeholder="브랜드명 (영문)" maxlength="100" name="brandEnName" value="${brand.brandEnName}"/>
									</div>
									<!-- E : ip-text-box -->
								</td>
								<th scope="row">
									<span class="th-required">사이트 구분</span>
								</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="site-mall-ots" name="siteNo" type="radio" value="" ${empty brand.siteNo ? 'checked' : '' }/>
												<label for="site-mall-ots">전체</label>
											</span>
										</li>
										<c:forEach var="item" items="${searchConditionSiteList }">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="site-no-${item.siteNo }" name="siteNo" type="radio"  value="${item.siteNo }" ${item.siteNo eq brand.siteNo ? 'checked' : '' }>
													<label for="site-no-${item.siteNo }">${item.siteName }</label>
												</span>
											</li>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">사용여부</span>
								</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="use-yn-n" type="radio" name="useYn" value="N" ${brand.useYn ne 'Y' ? 'checked' : '' }/>
												<label for="use-yn-n">사용안함</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="use-yn-y" type="radio" name="useYn" value="Y" ${brand.useYn eq 'Y' ? 'checked' : '' }/>
												<label for="use-yn-y">사용</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<th scope="row">
									<span>브랜드 초성 <br /> (한글/영문/숫자)</span>
								</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<div class="ip-text-box">
										<input type="text" class="ui-input num-unit1000000000" placeholder="ㄱ~ㅎ" title="한글 초성 입력" id="korInitialText" name="korInitialText" value="${brand.korInitialText}" maxlength="1">
										<input type="text" class="ui-input num-unit1000000000" placeholder="A~Z" title="영문 초성 입력" id="engInitialText" name="engInitialText" value="${brand.engInitialText}" maxlength="1">
										<input type="text" class="ui-input num-unit1000000000" placeholder="1~9" title="숫자 초성 입력" id="etcInitialText" name="etcInitialText" value="${brand.etcInitialText}" maxlength="1">
									</div>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">브랜드 키워드 정보</th>
								<td class="input" colspan="3">
									<input type="text" class="ui-input" title="브랜드 키워드 입력" name="brandKeyWordText" value="${brand.brandKeyWordText }" />
									<ul class="td-text-list">
										<li>* 키워드를 쉼표 (,)로 구분하여 주세요</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">비고</th>
								<td class="input" colspan="3">
									<input type="text" class="ui-input" title="비고 입력" name="noteText" value="${brand.noteText }" />
								</td>
							</tr>
							<tr>
								<th scope="row">메인 채널</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list" id="ul_dfltMvmnChnnl">
									<input type="hidden" id="orgDfltMvmnChnnl" value="${brand.dfltMvmnChnnl}" />
										<c:forEach items="${chnnlList}" var="chnnl" varStatus="status">
											<c:if test="${chnnl.chnnlNo ne '10000'}">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="dfltMvmnChnnl_${chnnl.chnnlNo}" type="radio" name="dfltMvmnChnnl" value="${chnnl.chnnlNo}" ${brand.dfltMvmnChnnl eq chnnl.chnnlNo ? 'checked' : '' }/>
														<label for="dfltMvmnChnnl_${chnnl.chnnlNo}">${chnnl.chnnlName}</label>
													</span>
												</li>
											</c:if>
										</c:forEach>

									</ul>
									<!-- E : ip-box-list -->
								</td>
								<th scope="row">채널별 배너 전시여부</th>
								<td class="input">
								<input type="hidden" id="orgBrandPrdtArtDispYn" name="orgBrandPrdtArtDispYn" value="${brand.brandPrdtArtDispYn}" />
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<input id="brand_prdt_art_disp_n" type="radio" name="brandPrdtArtDispYn" value="N" ${brand.brandPrdtArtDispYn eq 'N' ? 'checked' : '' }/>
												<label for="brand_prdt_art_disp_n">전시</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<input id="brand_prdt_art_disp_y" type="radio" name="brandPrdtArtDispYn" value="Y" ${brand.brandPrdtArtDispYn ne 'N' ? 'checked' : '' }/>
												<label for="brand_prdt_art_disp_y">전시안함</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>

							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td>
									<c:choose>
										<c:when test="${not empty brand.moderId and not empty brand.moderName }">
											<a href="javascript:void(0)" class="link" onclick="abc.adminDetailPopup('${brand.moderNo }')">${UtilsMasking.adminName(brand.moderId, brand.moderName) }</a>
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>
								<th scope="row">수정일시</th>
								<td>
									<c:choose>
										<c:when test="${not empty brand.modDtm }">
											<fmt:formatDate value="${brand.modDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">브랜드 대표이미지 (로고) 정보</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>브랜드 대표이미지 (로고) 정보</caption>
						<colgroup>
							<col style="width:160px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									<span class="th-required">대표이미지 등록</span>
									<div>(Color)<br />222*100px<br />(jpg, png) / 1MB 이내</div>
								</th>
								<td class="input">
									<div class="file-wrap">
										<ul class="file-list">
											<li>
												<span class="btn-box">
													<input type="hidden" id="brdPathText" title="첨부파일 추가" name="brdPathText" value="">
													<input type="file" id="file-brand-image-color" title="첨부파일 추가" name="fileBrandImageColor" data-brand-image-color="file" value="${brand.imageName}"/>
													<label for="file-brand-image-color">찾아보기</label>
												</span>
											</li>
											<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
											<li>
												<a href="#" class="subject" data-brand-image-color="file-name">${brand.imageName }</a>
												<button type="button" class="btn-file-del" data-brand-image-color="delete-button">
													<span class="ico ico-fdel" data-brand-image-color="remove"><span class="offscreen">첨부파일 삭제</span></span>
												</button>
											</li>
										</ul>
										<div class="alt-wrap">
											<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" name="altrnText" value="${brand.altrnText }"/>
										</div>
										<div class="img-wrap">
											 <c:if test="${brand.imageName != null and brand.imageName != ''}">
												<img src="${brand.imageUrl}" alt="${brand.imageName}"/>
											</c:if>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
					</form>
				</div>
				<!-- E:tab_content -->
