<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp"%>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>
				<c:choose>
					<c:when test="${param.contTypeCodeName eq 'I'}">
						이미지 등록
					</c:when>
					<c:otherwise>
						동영상 등록
					</c:otherwise>
				</c:choose>
			</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">
						<c:choose>
							<c:when test="${param.contTypeCodeName eq 'I'}">
								이미지 등록
							</c:when>
							<c:otherwise>
								동영상 등록
							</c:otherwise>
						</c:choose>
					</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<form name="frm" id="frm">
				<input type="hidden" name="brandNo" value="${param.brandNo}" />
				<input type="hidden" name="contTypeCode" value="${param.contTypeCode}" />
				<input type="hidden" name="deviceCode" value="${param.deviceCode}" />
				<input type="hidden" name="fileType" value="${param.setType}" />
				<input type="hidden" name="siteNo" value="${param.siteNo}" />
				<input type="hidden" name="sheetName" value="${param.sheetName}" />
				<input type="hidden" name="chnnlNo" value="${param.chnnlNo}" />
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>${param.contTypeCodeName }</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<%-- S: 공통영역 --%>
						<tr>
							<th scope="row"><span class="th-required">전시여부</span></th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay01" name="dispYn" type="radio" value="Y" checked />
											<label for="radioDisplay01">전시</label>
										</span>
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="dispStartYmd" value="" />
											</span>
											<span class="text">~</span> <span class="date-box">
												<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="dispEndYmd" value="" />
											</span>
										</span>
									<!-- E : term-date-wrap -->
									</li>
									<li>
									<span class="ui-rdo">
									<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioDisplay02" name="dispYn" type="radio"
											value="N" />
											<label for="radioDisplay02">전시안함</label>
									</span>
									</li>
								</ul> <!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="th-required">노출순서</span></th>
							<td class="input">
								<input type="number" class="ui-input" title="노출순서 입력" name="sortSeq" value="" />
							</td>
						</tr>
						<%-- E: 공통영역 --%>

						<%-- 이미지 --%>
						<c:if test="${param.contTypeCode eq '10002'}">
							<tr>
								<th scope="row"><span class="th-required">제목</span></th>
								<td class="input">
									<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
									<input type="text" class="ui-input" title="제목 입력" placeholder="20자 이내로 입력" name="addInfo1" value="" />
								</td>
							</tr>
							<tr>
								<th scope="row"><span class="th-required">이미지</span>
									<div>
										<c:choose>
											<c:when test="${param.deviceCode eq '10000'}">
												1200*388px<br />(jpg, jpeg, png, gif, bmp)
											</c:when>
											<c:otherwise>
												720*360px<br />(jpg, jpeg, png, gif, bmp)
											</c:otherwise>
										</c:choose>
									</div>
								</th>
								<td class="input">
									<!-- S : file-wrap -->
									<div class="file-wrap">
										<ul class="file-list">
											<li>
												<span class="btn-box"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="hidden" id="addInfo2" title="첨부파일 추가" name="addInfo2" value="" />
													<input type="file" id="imageUpload" title="첨부파일 추가" name="imageUpload" value="" />
													<label for="imageUpload">찾아보기</label>
												</span>
											</li>
											<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
											<li>
												<a href="javascript:void(0);" class="subject"></a>
												<button type="button" class="btn-file-del" id="del-image">
													<span class="ico ico-fdel">
														<span class="offscreen">첨부파일 삭제</span>
													</span>
												</button>
											</li>
										</ul>
										<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
										<div class="alt-wrap">
											<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" name="altrnText" value="" />
										</div>
										<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
										<div class="img-wrap" id="area-image-preview"></div>
									</div> <!-- E : file-wrap -->
								</td>
							</tr>
							<tr>
								<th scope="row"><span class="th-required">링크연결 방법</span></th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo5" id="radioPCLinkType01" value="U" checked />
												<label for="radioPCLinkType01">URL 입력</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo5" id="radioPCLinkType02" value="M" />
												<label for="radioPCLinkType02">이미지맵</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo5" id="radioPCLinkType03" value="E" data-link-url="/promotion/event/detail?eventNo=" data-hint="이벤트번호" />
												<label for="radioPCLinkType03">이벤트</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo5" id="radioPCLinkType04" value="P" data-link-url="/promotion/planning-display/detail?plndpNo=" data-hint="기획전번호" />
												<label for="radioPCLinkType04">기획전</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo5" id="radioPCLinkType05" value="B" data-link-url="/brand/detail?brndNo=" data-hint="브랜드번호" />
												<label for="radioPCLinkType05">브랜드</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo5" id="radioPCLinkType06" value="N" />
												<label for="radioPCLinkType06">연결안함</label>
											</span>
										</li>
									</ul> <!-- E : ip-box-list -->
								</td>
							</tr>
							<!-- S : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
							<tr class="link-type-url">
								<th scope="row"><span class="th-required">랜딩 URL</span></th>
								<td class="input">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
									<select class="ui-sel" title="랜딩 페이지 화면 유형 선택" name="addInfo6">
											<option value="S">현재창</option>
											<option value="N">새창</option>
									</select>
									<input type="text" class="ui-input url" placeholder="URL 입력 예시: http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" title="url 입력" name="addInfo7" value="" />
								</span> <!-- E : ip-text-box -->
								</td>
							</tr>
							<!-- E : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
							<!-- S : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
							<tr class="link-type-map">
								<th scope="row"><span class="th-required">랜딩 URL</span></th>
								<td class="input"><textarea class="ui-textarea"
										title="랜딩 url 입력" name="addInfo7" disabled="disabled"></textarea>
								</td>
							</tr>
							<!-- E : 링크연결 방법 > 이미지맵 선택시 노출되는 영역 -->
							<tr class="link-type-event">
								<th scope="row"><span class="th-required">랜딩 URL</span></th>
								<td class="input" colspan=""><span class="ip-text-box">
										<span class="text link-url"></span> <input type="text"
										class="ui-input" placeholder="" title="" name="addInfo7"
										value="">
								</span></td>
							</tr>
							<!-- S : 20190227 수정 // 텍스트 컬러 추가 -->
							<tr class="link-type-url">
								<th scope="row"><span class="th-required">텍스트 컬러</span></th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo8" id="radioColorType01" value="N" checked />
												<label for="radioColorType01">선택안함</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo8" id="radioColorType02" value="B" />
												<label for="radioColorType02">블랙</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo8" id="radioColorType03" value="W" />
												<label for="radioColorType03">화이트</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
						</c:if>
						<%-- 동영상 --%>
						<c:if test="${param.contTypeCode eq '10003'}">
							<tr>
								<th scope="row"><span class="th-required">제목</span></th>
								<td class="input">
									<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
									<input type="text" class="ui-input" title="제목 입력" placeholder="20자 이내로 입력" name="addInfo1" value="" />
								</td>
							</tr>
							<tr>
								<th scope="row"><span class="th-required">동영상 섬네일</span>
									<div>
										<c:choose>
											<c:when test="${param.deviceCode eq '10000'}">
												1200*388px<br />(jpg, jpeg, png, gif, bmp)
											</c:when>
											<c:otherwise>
												720*360px<br />(jpg, jpeg, png, gif, bmp)
											</c:otherwise>
										</c:choose>
									</div>
								</th>
								<td class="input">
									<!-- S : file-wrap -->
									<div class="file-wrap">
										<ul class="file-list">
											<li>
												<span class="btn-box">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="hidden" id="addInfo2" title="첨부파일 추가" name="addInfo2" value="" />
													<input type="file" id="imageUpload" title="첨부파일 추가" name="imageUpload" value="" data-brand-set-image="file" />
													<label for="imageUpload">찾아보기</label>
												</span>
											</li>
											<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
											<li>
												<a href="#" class="subject" data-brand-set-image="file-name"></a>
												<button type="button" class="btn-file-del" data-brand-set-image="delete-button">
													<span class="ico ico-fdel" data-brand-set-image="remove">
														<span class="offscreen">첨부파일 삭제</span>
													</span>
												</button>
											</li>
										</ul>
										<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
										<div class="alt-wrap">
											<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" name="altrnText" value="" />
										</div>
										<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
										<div class="img-wrap" id="area-image-preview"></div>
									</div> <!-- E : file-wrap -->
								</td>
							</tr>
							<tr>
								<th scope="row"><span class="th-required">연결 동영상</span></th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo5" id="radioVideoLink01" value="U" checked />
												<label for="radioVideoLink01">URL 입력</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="addInfo5" id="radioVideoLink02" value="D" />
												<label for="radioVideoLink02">직접 업로드</label>
											</span>
										</li>
									</ul> <!-- E : ip-box-list --> <!-- S : URL 입력 선택시 노출되는 영역 --> <!-- S : ip-text-box -->
									<span class="ip-text-box full-size layer-url-input"> <!-- TODO : 동영상 노출 방식 기획 fix 후 수정 -->
										<input type="text" class="ui-input url" title="url 입력" placeholder="URL 입력 예시 : https://youtu.be/AnCFR0yIKfw" name="addInfo9" value="" />
									</span>
									<!-- E : ip-text-box --> <!-- E : URL 입력 선택시 노출되는 영역 --> <!-- S : 직접 업로드 선택시 노출되는 영역 -->
									<div class="file-wrap layer-movie-upload">
										<ul class="file-list">
											<li>
												<span class="btn-box">
													<input type="hidden" id="addInfo17" title="첨부파일 추가" name="addInfo17" value="">
													<input type="file" id="movieUpload" title="첨부파일 추가" name="movieUpload" value="">
													<label for="movieUpload">찾아보기</label>
												</span>
											</li>
											<li>
												<span class="subject"></span>
												<button type="button" class="btn-file-del" />
													<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
												</button>
											</li>
										</ul>
									</div> <!-- E : 직접 업로드 선택시 노출되는 영역 -->
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</form>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" class="btn-normal btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.brand.set.popup.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>