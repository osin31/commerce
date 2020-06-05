<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

</script>


					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">브랜드 프로모션 배너 정보(PC) (통합몰에만 전시됩니다)</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<form id="form-brand-mall-${scope_chnnlNo}">
					<table class="tbl-row" id="pc-${scope_chnnlNo}">
						<caption>브랜드 프로모션 배너 정보</caption>
						<colgroup>
							<col style="width:160px;">
							<col>
						</colgroup>
						<tbody id="pc-brand-page-tbody-${scope_chnnlNo}">
							<tr>
								<th scope="row">전시여부</th>
								<td class="input">
									<input type="hidden" name="deviceCode" value="10000"/> <!-- PC -->
									<input type="hidden" name="brandNo" value="${brand.brandNo }"/>
									<input type="hidden" name="brandPageSeq" value="${mallPc.brandPageSeq }"/>
									<input type="hidden" name="dispYn" value="${not empty mallPc.dispYn ? mallPc.dispYn : 'N'}"/>
									<input type="hidden" name="cnnctnType" value="${not empty mallPc.cnnctnType ? mallPc.cnnctnType : 'N' }"/>
									<input type="hidden" name="linkTargetType" value="${mallPc.linkTargetType }"/>
									<input type="hidden" name="linkInfo" value="${mallPc.linkInfo }"/>
									<input type="hidden" name="status" value="${empty mallPc ? 'I' : 'U' }"/>
									<input type="hidden" name="chnnlNo" value="${scope_chnnlNo}"/>
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="pc-disp-yn-y-${scope_chnnlNo}" class="disp-yn" name="pcDispYn" type="radio" value="Y" ${mallPc.dispYn eq 'Y' ? 'checked' : ''}/>
												<label for="pc-disp-yn-y-${scope_chnnlNo}">전시</label>
											</span>

											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" name="dispStartYmd" title="시작일 선택" value="<fmt:formatDate value="${mallPc.dispStartYmd }" pattern="yyyy.MM.dd" />" />
												</span>
												<span class="text">~</span>
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" name="dispEndYmd" title="종료일 선택" value="<fmt:formatDate value="${mallPc.dispEndYmd }" pattern="yyyy.MM.dd" />" />
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="pc-disp-yn-n-${scope_chnnlNo}" class="disp-yn" name="pcDispYn" type="radio" value="N" ${mallPc.dispYn ne 'Y' ? 'checked' : ''}/>
												<label for="pc-disp-yn-n-${scope_chnnlNo}">전시안함</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">
									브랜드페이지 프로모션 배너 이미지 등록
									<div>(color) <br />1200*227px<br />(jpg, jpeg, png, gif, bmp) / 1MB 이내</div>
								</th>
								<td class="input">
									<div class="file-wrap">
										<ul class="file-list">
											<li>
												<span class="btn-box">
													<input type="file" id="pc-upload-file-${scope_chnnlNo}" title="첨부파일 추가" name="uploadFile" value="${mallPc.imageName}"/>
													<label for="pc-upload-file-${scope_chnnlNo}">찾아보기</label>
												</span>
											</li>
											<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
											<li>
												<a href="#" class="subject" data-brand-mall-pc="file-name">${mallPc.imageName }</a>
												<button type="button" class="btn-file-del" data-brand-mall-pc="delete-button">
													<span class="ico ico-fdel" data-brand-mall-pc="remove"><span class="offscreen">첨부파일 삭제</span></span>
												</button>
											</li>
										</ul>
										<div class="alt-wrap">
											<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" id="pc-altrn-text-${scope_chnnlNo}" name="altrnText" value="${mallPc.altrnText }"/>
										</div>
										<div class="img-wrap">
											<c:if test="${not empty mallPc.imageName}">
												<img src="${mallPc.imageUrl}" alt="${mallPc.imageName}"/>
											</c:if>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">링크연결 방법</span>
								</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="pc-cnnctn-type-u-${scope_chnnlNo}" name="pcCnnctnType" type="radio" value="U" ${mallPc.cnnctnType eq 'U' ? 'checked' : '' }/>
												<label for="pc-cnnctn-type-u-${scope_chnnlNo}">URL 입력</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="pc-cnnctn-type-m-${scope_chnnlNo}" name="pcCnnctnType" type="radio" value="M" ${mallPc.cnnctnType eq 'M' ? 'checked' : '' }/>
												<label for="pc-cnnctn-type-m-${scope_chnnlNo}">이미지맵</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="pc-cnnctn-type-n-${scope_chnnlNo}" name="pcCnnctnType" type="radio" value="N" ${empty mallPc.cnnctnType or mallPc.cnnctnType eq 'N' ? 'checked' : '' }/>
												<label for="pc-cnnctn-type-n-${scope_chnnlNo}">연결안함</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<!-- S : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
							<tr id="pc-link-info-u-tr-${scope_chnnlNo}">
								<th scope="row">
									<span class="th-required">랜딩 URL</span>
								</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select class="ui-sel" title="랜딩 페이지 화면 유형 선택" id="pc-link-target-type-${scope_chnnlNo}">
											<option value="S" <c:if test="${mallPc.linkTargetType eq 'S' }">selected</c:if>>현재창</option>
											<option value="N" <c:if test="${mallPc.linkTargetType eq 'N' }">selected</c:if>>새창</option>
										</select>
										<input type="text" class="ui-input url" id="pc-link-info-u-${scope_chnnlNo}" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" title="url 입력" value="${mallPc.cnnctnType eq 'U' ? mallPc.linkInfo : ''}">
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<!-- E : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
							<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
							<tr id="pc-link-info-m-tr-${scope_chnnlNo}">
								<th scope="row">
									<span class="th-required">랜딩 URL</span>
								</th>
								<td class="input">
									<textarea class="ui-textarea" title="랜딩 url 입력" id="pc-link-info-m-${scope_chnnlNo}">
										${mallPc.cnnctnType eq 'M' ? mallPc.linkInfo : ''}
									</textarea>
								</td>
							</tr>
							<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
						</tbody>
					</table>
					<!-- E : tbl-row -->

					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">브랜드 프로모션 배너 정보(Mobile) (통합몰에만 전시됩니다) </h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row" id="mo-${scope_chnnlNo}">
						<caption>브랜드 프로모션 배너 정보</caption>
						<colgroup>
							<col style="width:160px;">
							<col>
						</colgroup>
						<tbody id="mo-brand-page-tbody-${scope_chnnlNo}">
							<tr>
								<th scope="row">전시여부</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<input type="hidden" name="deviceCode" value="10001" /> <!-- Mobile -->
									<input type="hidden" name="brandNo" value="${brand.brandNo}" />
									<input type="hidden" name="brandPageSeq" value="${mallMo.brandPageSeq }" />
									<input type="hidden" name="dispYn" value="${not empty mallMo.dispYn ? mallMo.dispYn : 'N'}"/>
									<input type="hidden" name="cnnctnType" value="${not empty mallMo.cnnctnType ? mallMo.cnnctnType : 'N' }"/>
									<input type="hidden" name="linkTargetType" value="${mallMo.linkTargetType }"/>
									<input type="hidden" name="linkInfo" value="${mallMo.linkInfo }"/>
									<input type="hidden" name="status" value="${empty mallMo ? 'I' : 'U' }"/>
									<input type="hidden" name="chnnlNo" value="${scope_chnnlNo}"/>
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="mo-disp-yn-y-${scope_chnnlNo}" class="disp-yn" name="moDispYn" type="radio" value="Y" ${mallMo.dispYn eq 'Y' ? 'checked' : ''}/>
												<label for="mo-disp-yn-y-${scope_chnnlNo}">전시</label>
											</span>

											<!-- S : term-date-wrap -->
											<span class="term-date-wrap">
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" name="dispStartYmd" title="시작일 선택" value="<fmt:formatDate value="${mallMo.dispStartYmd }" pattern="yyyy.MM.dd" />" />
												</span>
												<span class="text">~</span>
												<span class="date-box">
													<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" name="dispEndYmd" title="종료일 선택" value="<fmt:formatDate value="${mallMo.dispEndYmd }" pattern="yyyy.MM.dd" />">
												</span>
											</span>
											<!-- E : term-date-wrap -->
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="mo-disp-yn-n-${scope_chnnlNo}" class="disp-yn" name="moDispYn" type="radio" value="N" ${mallMo.dispYn ne 'Y' ? 'checked' : ''}/>
												<label for="mo-disp-yn-n-${scope_chnnlNo}">전시안함</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr class="mo-brand-page-tr">
								<th scope="row">
									브랜드페이지 프로모션 배너 이미지 등록
									<div>(color) <br />720*256px <br />(jpg, jpeg, png, gif, bmp) / 1MB 이내</div>
								</th>
								<td class="input">
									<div class="file-wrap">
										<ul class="file-list">
											<li>
												<span class="btn-box">
													<input type="file" id="mo-upload-file-${scope_chnnlNo}" title="첨부파일 추가" name="uploadFile" value="${mallMo.imageName}"/>
													<label for="mo-upload-file-${scope_chnnlNo}">찾아보기</label>
												</span>
											</li>
											<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
											<li>
												<a href="#" class="subject" data-brand-mall-mo="file-name">${mallMo.imageName }</a>
												<button type="button" class="btn-file-del" data-brand-mall-mo="delete-button">
													<span class="ico ico-fdel" data-brand-mall-mo="remove"><span class="offscreen">첨부파일 삭제</span></span>
												</button>
											</li>
										</ul>
										<div class="alt-wrap">
											<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" title="이미지 대체 텍스트 입력" id="mo-altrn-text-${scope_chnnlNo}" name="altrnText" value="${mallMo.altrnText }"/>
										</div>
										<div class="img-wrap">
											 <c:if test="${not empty mallMo.imageName }">
												<img src="${mallMo.imageUrl}" alt="${mallMo.imageName}"/>
											</c:if>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<span class="th-required">링크연결 방법</span>
								</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="mo-cnnctn-type-u-${scope_chnnlNo}" name="moCnnctnType" type="radio" value="U" ${mallMo.cnnctnType eq 'U' ? 'checked' : '' }/>
												<label for="mo-cnnctn-type-u-${scope_chnnlNo}">URL 입력</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="mo-cnnctn-type-m-${scope_chnnlNo}" name="moCnnctnType" type="radio" value="M" ${mallMo.cnnctnType eq 'M' ? 'checked' : '' }/>
												<label for="mo-cnnctn-type-m-${scope_chnnlNo}">이미지맵</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="mo-cnnctn-type-n-${scope_chnnlNo}" name="moCnnctnType" type="radio" value="N" ${empty mallMo.cnnctnType or mallMo.cnnctnType eq 'N' ? 'checked' : '' }/>
												<label for="mo-cnnctn-type-n-${scope_chnnlNo}">연결안함</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<!-- S : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
							<tr id="mo-link-info-u-tr-${scope_chnnlNo}">
								<th scope="row">
									<span class="th-required">랜딩 URL</span>
								</th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<select class="ui-sel" title="랜딩 페이지 화면 유형 선택" id="mo-link-target-type-${scope_chnnlNo}">
											<option value="S" <c:if test="${mallMo.linkTargetType eq 'S' }">selected</c:if>>현재창</option>
											<option value="N" <c:if test="${mallMo.linkTargetType eq 'N' }">selected</c:if>>새창</option>
										</select>
										<input type="text" class="ui-input url" id="mo-link-info-u-${scope_chnnlNo}" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" title="url 입력" value="${mallMo.cnnctnType eq 'U' ? mallMo.linkInfo : ''}">
									</span>
									<!-- E : ip-text-box -->
								</td>
							</tr>
							<!-- E : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
							<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
							<tr id="mo-link-info-m-tr-${scope_chnnlNo}">
								<th scope="row">
									<span class="th-required">랜딩 URL</span>
								</th>
								<td class="input">
									<textarea class="ui-textarea" title="랜딩 url 입력" id="mo-link-info-m-${scope_chnnlNo}">
										${mallMo.cnnctnType eq 'M' ? mallMo.linkInfo : ''}
									</textarea>
								</td>
							</tr>
							<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
						</tbody>
					</table>
					</form>
					<!-- E : tbl-row -->
