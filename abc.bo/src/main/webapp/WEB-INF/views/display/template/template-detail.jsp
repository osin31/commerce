<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>
		<form name="frm">
		<input type="hidden" name="dispTmplNo" id="dispTmplNo" value="${dpDisplayTemplate.dispTmplNo }" />
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">전시 템플릿 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시 관리</li>
								<li>전시 기준정보 관리</li>
								<li>전시 템플릿 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<!-- DESC : 등록페이지인경우, '템플릿 등록'으로 텍스트 변경 -->
						<h3 class="content-title">템플릿 기본정보</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>템플릿 기본정보</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">템플릿 코드</th>
							<td>${dpDisplayTemplate.dispTmplNo }</td>
							<th scope="row">
								<span class="th-required">템플릿명</span>
							</th>
							<td class="input">
								<!-- DESC : 전시 템플릿 관리 수정/상세 페이지인 경우 input영역 수정 불가. readonly 속성 추가해주세요 -->
								<input type="text" class="ui-input" title="템플릿명 입력" placeholder="20자 이내로 입력" name="tmplName" maxlength="20" value="${dpDisplayTemplate.tmplName }">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">사이트 / 채널</span>
							</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select class="ui-sel" title="사이트 선택" name="siteNo" id="siteNo">
										<option value="" ${empty dpDisplayTemplate.siteNo ? 'selected' : ''}>사이트 선택</option>
										<c:forEach var="site" items="${siteList}">
										<option value="${site.siteNo }" ${dpDisplayTemplate.siteNo eq site.siteNo ? 'selected' : ''}>${site.siteName }</option>
										</c:forEach>
									</select>
									<!-- S : 통합몰 선택시 option -->
									<select class="ui-sel" title="채널 선택" name="chnnlNo" id="chnnlNo" data-saving-chnnl-no="${dpDisplayTemplate.chnnlNo }">
										<option value="" ${empty dpDisplayTemplate.chnnlNo ? 'selected' : ''}>채널 선택</option>
									</select>
									<!-- E : 통합몰 선택시 select option -->

								</span>
								<!-- E : ip-text-box -->
							</td>
							<th scope="row">
								<span class="th-required">디바이스</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<c:forEach var="code" items="${codeList.DEVICE_CODE}" varStatus="status">
									<c:if test="${code.codeDtlNo ne '10002' }">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="deviceCode" id="radioDevice0${status.index }" value="${code.codeDtlNo }" ${(dpDisplayTemplate.deviceCode eq code.codeDtlNo) or (empty dpDisplayTemplate.deviceCode and status.index == 0) ? 'checked' : ''}/>
											<label for="radioDevice0${status.index }">${code.codeDtlName }</label>
										</span>
									</li>
									</c:if>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">템플릿 유형</span>
							</th>
							<td class="input">
								<select class="ui-sel" title="템플릿 유형 선택" name="tmplTypeCode">
									<option value=""${empty dpDisplayTemplate.tmplTypeCode ? 'selected' : ''}>템플릿 유형 선택</option>
									<c:forEach var="code" items="${codeList.TMPL_TYPE_CODE}" varStatus="status">
									<option value="${code.codeDtlNo }" ${dpDisplayTemplate.tmplTypeCode eq code.codeDtlNo ? 'selected' : ''} >${code.codeDtlName }</option>
									</c:forEach>
								</select>
							</td>
							<th scope="row">
								<span class="th-required">사용여부</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="useYn" id="radioUse01" value="Y" ${(dpDisplayTemplate.useYn eq 'Y') or (empty dpDisplayTemplate.useYn) ? 'checked' : ''} />
											<label for="radioUse01">사용</label>
										</span>
									</li>
									<li>
										<!-- DESC : 노출중인 템플릿인 경우, 사용안함 input영역 disabled 속성 추가 해주세요 -->
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="useYn" id="radioUse02" value="N" ${dpDisplayTemplate.useYn eq 'N' ? 'checked' : ''}>
											<label for="radioUse02">사용안함</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">템플릿 URL</span>
							</th>
							<td class="input" colspan="3">
								<input type="text" class="ui-input" title="템플릿 URL 입력" placeholder="템플릿 파일정보 입력" name="tmplUrl" value="${dpDisplayTemplate.tmplUrl}">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="">템플릿 미리보기</span>
								<div>(최대 10MB까지 등록가능, 파일 유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td colspan="3" class="input">
								<!-- S : file-wrap -->
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="hidden" id="imageName" title="첨부파일 추가" name="imageName" value="${dpDisplayTemplate.imageName}">
												<input type="file" id="imageFile" title="첨부파일 추가" name="imageFile" value="">
												<label for="imageFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="javascript:void(0);" class="subject">${dpDisplayTemplate.imageName}</a>
											<button type="button" class="btn-file-del" style="${dpDisplayTemplate.imageName != null and dpDisplayTemplate.imageName != '' ? '' : 'display: none;' }">
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
									<div class="alt-wrap">
										<input type="text" class="ui-input" placeholder="이미지 대체 텍스트 입력" name="altrnText" value="${dpDisplayTemplate.altrnText }" />
									</div>
									<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
									<div class="img-wrap" id="area-image-preview">
										<c:if test="${dpDisplayTemplate.imageName != null and dpDisplayTemplate.imageName != ''}">
											<img alt="${dpDisplayTemplate.altrnText}" src="${dpDisplayTemplate.imageUrl}">
										</c:if>
									</div>
								</div>
								<!-- E : file-wrap -->
							</td>
						</tr> 
						<!-- S : 전시 템플릿 수정/상세인 경우 노출되는 영역 -->
						<tr>
							<th scope="row">작성자</th>
							<td><a href="javascript:abc.adminDetailPopup('${dpDisplayTemplate.rgsterNo}');">${UtilsMasking.adminName(dpDisplayTemplate.rgsterId, dpDisplayTemplate.rgsterName)}</a></td>
							<th scope="row">작성일시</th>
							<td><fmt:formatDate value="${dpDisplayTemplate.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<tr>
							<th scope="row">수정자</th>
							<td><a href="javascript:abc.adminDetailPopup('${dpDisplayTemplate.moderNo}');">${UtilsMasking.adminName(dpDisplayTemplate.moderId, dpDisplayTemplate.moderName)}</a></td>
							<th scope="row">수정일시</th>
							<td><fmt:formatDate value="${dpDisplayTemplate.modDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						</tr>
						<!-- E : 전시 템플릿 수정/상세인 경우 노출되는 영역 -->
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">코너목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : 전시 템플릿 수정/상세인 경우 노출되는 영역 -->
				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<!-- S : opt-group -->
						<span class="opt-group">
							<label class="title" for="selView01">목록개수</label>
							<select class="ui-sel" id="selView01">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<!-- E : opt-group -->
					</div>
					<div class="fr">
						<div class="btn-group">
							<button type="button" class="btn-sm btn-del" id="del-corner">선택 삭제</button>
							<!-- DESC : html/display/BO-DP-01002.html 파일 확인 해주세요 -->
							<a href="javascript:void(0);" id="open-corner-popup" class="btn-sm btn-link">등록</a>
						</div>
					</div>
				</div>
				<!-- E : tbl-controller -->
				<!-- E : 전시 템플릿 수정/상세인 경우 노출되는 영역 -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="cornerSheet" style="width:100%; height:429px;">
						ibsheet grid영역(div 삭제 필요)
					</div>
				</div>
				<!-- E : ibsheet-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="javascript:void(0);" class="btn-lg btn-save" id="save-template">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->
		</form>

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>		
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.template.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>