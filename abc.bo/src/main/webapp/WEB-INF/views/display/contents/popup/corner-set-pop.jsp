<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>${param.contTypeCodeName }</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">${param.contTypeCodeName }</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<form name="frm">
			<input type="hidden" name="contTypeCode" value="${param.contTypeCode}" />
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>${param.contTypeCodeName }</caption>
				<colgroup>
					<col style="width: 135px;">
					<col>
					<col style="width: 135px;">
					<col>
				</colgroup>
				<tbody>
					<%-- S: 공통영역 --%>
					<tr>
						<th scope="row">
							<span class="th-required">전시여부</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioDisplay01" name="dispYn" type="radio" value="Y" ${(dpCategoryCornerSet.dispYn eq 'Y') or (empty dpCategoryCornerSet.dispYn) ? 'checked' : ''}>
										<label for="radioDisplay01">전시</label>
									</span>

									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<span class="date-box">
											<fmt:formatDate value="${dpCategoryCornerSet.dispStartYmd}" pattern="yyyy.MM.dd" var="dispStartYmdDot"/>
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" name="dispStartYmd" value="${dispStartYmdDot }">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<fmt:formatDate value="${dpCategoryCornerSet.dispEndYmd}" pattern="yyyy.MM.dd" var="dispEndYmdDot"/>
											<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" name="dispEndYmd" value="${dispEndYmdDot }">
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="radioDisplay02" name="dispYn" type="radio" value="N" ${dpCategoryCornerSet.dispYn eq 'N' ? 'checked' : ''}>
										<label for="radioDisplay02">전시안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">노출순서</span>
						</th>
						<td class="input" colspan="3">
							<input type="number" class="ui-input" title="노출순서 입력" name="sortSeq" value="${dpCategoryCornerSet.sortSeq}">
						</td>
					</tr>
					<%-- E: 공통영역 --%>
					
					<%-- 전시세트명 --%>
					<c:if test="${param.contTypeCode eq '10000'}">
					<tr>
						<th scope="row">
							<span class="th-required">전시세트명</span>
						</th>
						<td class="input">
							<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
							<input type="text" class="ui-input" title="전시세트명 입력" maxlength="100" placeholder="100자 이내로 입력" name="addInfo1" value="${dpCategoryCornerSet.addInfo1}">
						</td>
					</tr>
					</c:if>
					<%-- 이미지 --%>
					<c:if test="${param.contTypeCode eq '10002'}">
					<tr>
						<th scope="row">
							<span class="th-required">제목</span>
						</th>
						<td class="input" colspan="3">
							<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
							<input type="text" class="ui-input" title="제목 입력" maxlength="100" placeholder="100자 이내로 입력" name="addInfo1" value="${dpCategoryCornerSet.addInfo1}">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">이미지</span>
							<div>최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input" colspan="3">
							<!-- S : file-wrap -->
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="hidden" id="addInfo2" title="첨부파일 추가" name="addInfo2" value="${dpCategoryCornerSet.addInfo2}">
											<input type="file" id="imageUpload" title="첨부파일 추가" name="imageUpload" value="">
											<label for="imageUpload">찾아보기</label>
										</span>
									</li>
									<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
									<li>
										<a href="javascript:void(0);" class="subject">${dpCategoryCornerSet.addInfo2}</a>
										<button type="button" class="btn-file-del" style="${dpCategoryCornerSet.addInfo2 != null and dpCategoryCornerSet.addInfo2 != '' ? '' : 'display: none;' }" id="del-image">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
								<div class="alt-wrap">
									<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" name="altrnText" value="${dpCategoryCornerSet.altrnText }" />
								</div>
								<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
								<div class="img-wrap" id="area-image-preview">
									<c:if test="${dpCategoryCornerSet.addInfo2 != null and dpCategoryCornerSet.addInfo2 != ''}">
										<img alt="${dpCategoryCornerSet.altrnText}" src="${dpCategoryCornerSet.addInfo4}">
									</c:if>
								</div>
							</div>
							<!-- E : file-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">링크연결 방법</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioPCLinkType01" value="U" ${(dpCategoryCornerSet.addInfo5 eq 'U') or (empty dpCategoryCornerSet.addInfo5) ? 'checked' : ''}>
										<label for="radioPCLinkType01">URL 입력</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioPCLinkType03" value="E" ${dpCategoryCornerSet.addInfo5 eq 'E' ? 'checked' : ''} data-link-url="/promotion/event/detail?eventNo=" data-hint="이벤트번호">
										<label for="radioPCLinkType03">이벤트</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioPCLinkType04" value="P" ${dpCategoryCornerSet.addInfo5 eq 'P' ? 'checked' : ''} data-link-url="/promotion/planning-display/detail?plndpNo=" data-hint="기획전번호">
										<label for="radioPCLinkType04">기획전</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioPCLinkType05" value="B" ${dpCategoryCornerSet.addInfo5 eq 'B' ? 'checked' : ''} data-link-url="/product/brand/page?brandNo=" data-hint="브랜드번호">
										<label for="radioPCLinkType05">브랜드</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioPCLinkType06" value="N" ${dpCategoryCornerSet.addInfo5 eq 'N' ? 'checked' : ''}>
										<label for="radioPCLinkType06">연결안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<!-- S : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
					<tr class="link-type-url">
						<th scope="row">
							<span class="th-required">랜딩 URL</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<input type="text" class="ui-input url" placeholder="URL 입력 예시: https://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" title="url 입력" name="addInfo7" value="${dpCategoryCornerSet.addInfo5 eq 'U' ? dpCategoryCornerSet.addInfo7 : ''}">
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<!-- E : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
					<tr class="link-type-event">
						<th scope="row">
							<span class="th-required">랜딩 URL</span>
						</th>
						<td class="input" colspan="3">
							<span class="ip-text-box">
								<span class="text link-url"></span>
								<input type="text" class="ui-input" placeholder="" title=""name="addInfo7" value="${dpCategoryCornerSet.addInfo5 eq 'E' or dpCategoryCornerSet.addInfo5 eq 'P' or dpCategoryCornerSet.addInfo5 eq 'B' ? dpCategoryCornerSet.addInfo7 : ''}">
							</span>
						</td>
					</tr>
					<!-- S : 20190227 수정 // 텍스트 컬러 추가 -->
					<tr>
						<th scope="row">Target 설정</th>
						<td class="input">
							<<select class="ui-sel" title="랜딩 페이지 화면 유형 선택" name="addInfo6">
								<option value="S" ${dpCategoryCornerSet.addInfo6 eq 'S' ? 'selected' : ''}>현재창</option>
								<option value="N" ${dpCategoryCornerSet.addInfo6 eq 'N' ? 'selected' : ''}>새창</option>
							</select>
						</td>
						<th scope="row">
							<span class="th-required">텍스트 컬러</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo8" id="radioColorType01" value="N" ${(dpCategoryCornerSet.addInfo8 eq 'N') or (empty dpCategoryCornerSet.addInfo8) ? 'checked' : ''}>
										<label for="radioColorType01">선택안함</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo8" id="radioColorType02" value="B" ${dpCategoryCornerSet.addInfo8 eq 'B' ? 'checked' : ''}>
										<label for="radioColorType02">블랙</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo8" id="radioColorType03" value="W" ${dpCategoryCornerSet.addInfo8 eq 'W' ? 'checked' : ''}>
										<label for="radioColorType03">화이트</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">확장화면 배경컬러</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo9" id="radioBgColorType01" value="N"  ${(dpCategoryCornerSet.addInfo9 eq 'N') or (empty dpCategoryCornerSet.addInfo9) ? 'checked' : ''}>
										<label for="radioBgColorType01">선택안함</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo9" id="radioBgColorType02" value="Y" ${dpCategoryCornerSet.addInfo9 eq 'Y' ? 'checked' : ''}>
										<label for="radioBgColorType02">사용</label>
									</span>
									<input type="text" class="ui-input" title="컬러값 입력" name="addInfo10" placeholder="RGB 컬러값 입력 #000000">
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					</c:if>
					<%-- 동영상 --%>
					<c:if test="${param.contTypeCode eq '10003'}">
					<tr>
						<th scope="row">
							<span class="th-required">제목</span>
						</th>
						<td class="input" colspan="3">
							<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
							<input type="text" class="ui-input" title="제목 입력" maxlength="100"  placeholder="100자 이내로 입력" name="addInfo1" value="${dpCategoryCornerSet.addInfo1}">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">동영상 섬네일</span>
							<div>최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
						</th>
						<td class="input" colspan="3">
							<!-- S : file-wrap -->
							<div class="file-wrap">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="hidden" id="addInfo2" title="첨부파일 추가" name="addInfo2" value="${dpCategoryCornerSet.addInfo2}">
											<input type="file" id="imageUpload" title="첨부파일 추가" name="imageUpload" value="">
											<label for="imageUpload">찾아보기</label>
										</span>
									</li>
									<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
									<li>
										<a href="javascript:void(0);" class="subject">${dpCategoryCornerSet.addInfo2}</a>
										<button type="button" class="btn-file-del" style="${dpCategoryCornerSet.addInfo2 != null and dpCategoryCornerSet.addInfo2 != '' ? '' : 'display: none;' }" id="del-image">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
								<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
								<div class="alt-wrap">
									<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" name="altrnText" value="${dpCategoryCornerSet.altrnText }" />
								</div>
								<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
								<div class="img-wrap" id="area-image-preview">
									<c:if test="${dpCategoryCornerSet.addInfo2 != null and dpCategoryCornerSet.addInfo2 != ''}">
										<img alt="${dpCategoryCornerSet.altrnText}" src="${dpCategoryCornerSet.addInfo4}">
									</c:if>
								</div>
							</div>
							<!-- E : file-wrap -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">연결 동영상</span>
							<div>최대 10MB까지 등록 가능 <br />파일유형 : MP4)</div>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioVideoLink01" value="U" ${(dpCategoryCornerSet.addInfo5 eq 'U') or (empty dpCategoryCornerSet.addInfo5) ? 'checked' : ''}>
										<label for="radioVideoLink01">URL 입력</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioVideoLink02" value="D" ${dpCategoryCornerSet.addInfo5 eq 'D' ? 'checked' : ''}>
										<label for="radioVideoLink02">직접 업로드</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
							<!-- S : URL 입력 선택시 노출되는 영역 -->
							<!-- S : ip-text-box -->
							<span class="ip-text-box full-size layer-url-input">
								<!-- TODO : 동영상 노출 방식 기획 fix 후 수정 -->
								<select class="ui-sel" title="동영상 노출 방식 선택" name="addInfo6">
									<option value="A" ${dpCategoryCornerSet.addInfo6 eq 'S' ? 'selected' : ''}>영역 내 재생</option>
									<option value="P" ${dpCategoryCornerSet.addInfo6 eq 'N' ? 'selected' : ''}>라이트박스 팝업</option>
								</select>
								<input type="text" class="ui-input url" title="url 입력" placeholder="URL 입력 예시 : https://youtu.be/AnCFR0yIKfw" name="addInfo9" value="${dpCategoryCornerSet.addInfo9}">
							</span>
							<!-- E : ip-text-box -->
							<!-- E : URL 입력 선택시 노출되는 영역 -->

							<!-- S : 직접 업로드 선택시 노출되는 영역 -->
							<div class="file-wrap layer-movie-upload">
								<ul class="file-list">
									<li>
										<span class="btn-box">
											<input type="hidden" id="addInfo7" title="첨부파일 추가" name="addInfo7" value="${dpCategoryCornerSet.addInfo7}">
											<input type="file" id="movieUpload" title="첨부파일 추가" name="movieUpload" value="">
											<label for="movieUpload">찾아보기</label>
										</span>
									</li>
									<li><span class="subject">${dpCategoryCornerSet.addInfo7}</span>
										<button type="button" class="btn-file-del" style="${dpCategoryCornerSet.addInfo7 != null and dpCategoryCornerSet.addInfo7 != '' ? '' : 'display: none;' }">
											<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
										</button>
									</li>
								</ul>
							</div>
							<!-- E : 직접 업로드 선택시 노출되는 영역 -->
						</td>
					</tr>
					</c:if>
					<%-- 텍스트 --%>
					<c:if test="${param.contTypeCode eq '10004'}">
					<tr>
						<th scope="row">
							<span class="th-required">텍스트</span>
						</th>
						<td class="input" colspan="3">
							<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
							<input type="text" class="ui-input" title="텍스트 입력"  maxlength="100" placeholder="100자 이내로 입력" name="addInfo1" value="${dpCategoryCornerSet.addInfo1}" maxlength="20">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">링크연결 방법</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo2" id="radioPCLinkType01" value="U" ${(dpCategoryCornerSet.addInfo2 eq 'U') or (empty dpCategoryCornerSet.addInfo2) ? 'checked' : ''}>
										<label for="radioPCLinkType01">URL 입력</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo2" id="radioPCLinkType03" value="N" ${dpCategoryCornerSet.addInfo2 eq 'N' ? 'checked' : ''}>
										<label for="radioPCLinkType03">연결안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<!-- S : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
					<tr class="link-type-url">
						<th scope="row">
							<span class="th-required">랜딩 URL</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : ip-text-box -->
							<span class="ip-text-box">
								<select class="ui-sel" title="랜딩 페이지 화면 유형 선택" name="addInfo3">
									<option value="S" ${dpCategoryCornerSet.addInfo3 eq 'S' ? 'selected' : ''}>현재창</option>
									<option value="N" ${dpCategoryCornerSet.addInfo3 eq 'N' ? 'selected' : ''}>새창</option>
								</select>
								<input type="text" name="addInfo4" class="ui-input url" placeholder="URL 입력 예시: https://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004456" title="url 입력"  value="${dpCategoryCornerSet.addInfo4}">
							</span>
							<!-- E : ip-text-box -->
						</td>
					</tr>
					<!-- E : 링크연결 방법 > URL입력 선택시 노출되는 영역 -->
					<!-- S : 20190311 추가 // GNB 메뉴명 강조 컬럼 추가 -->
					<tr>
						<th scope="row">GNB 메뉴명 강조</th>
						<td class="input" colspan="3">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioPointType01" value="N" ${(dpCategoryCornerSet.addInfo5 eq 'N') or (empty dpCategoryCornerSet.addInfo5) ? 'checked' : ''}>
										<label for="radioPointType01">사용안함</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="addInfo5" id="radioPointType03" value="Y" ${dpCategoryCornerSet.addInfo5 eq 'Y' ? 'checked' : ''}>
										<label for="radioPointType03">사용</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
							<ul class="td-text-list">
								<li>* 페이지유형 ‘GNB’일 때만 관리하는 항목입니다.</li>
							</ul>
						</td>
					</tr>
					</c:if>
					<%-- HTML--%>
					<c:if test="${param.contTypeCode eq '10005'}">
					<tr>
						<th scope="row">
							<span class="th-required">제목</span>
						</th>
						<td class="input" colspan="3">
							<!-- TODO : 기획 fix 후 입력글자 placeholder 수정 -->
							<input type="text" class="ui-input" title="제목 입력" placeholder="100자 이내로 입력" maxlength="100" name="addInfo1" value="${dpCategoryCornerSet.addInfo1}">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">HTML</span>
						</th>
						<td class="input" colspan="3">
							<!-- S : editor-wrap -->
							<div class="editor-wrap">
								<textarea name="addInfo10" id="addInfo10" rows="10" cols="80">${dpCategoryCornerSet.addInfo10}</textarea>
							</div>
							<!-- E : editor-wrap -->
						</td>
					</tr>
					</c:if>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			</form>
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" class="btn-normal btn-save">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>

	
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.contents.corner.set.pop.js<%=_DP_REV%>"></script>
</body>
</html>