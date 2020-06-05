<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">전시 템플릿 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>전시관리</li>
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
						<h3 class="content-title">템플릿 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<form name="searchForm" id="searchForm">
				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>템플릿 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">사이트 / 채널</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<select class="ui-sel" title="사이트 선택" name="siteNo" id="siteNo">
												<option value="">사이트 전체</option>
												<c:forEach var="site" items="${siteList}">
												<option value="${site.siteNo }">${site.siteName }</option>
												</c:forEach>
											</select>
											<select class="ui-sel" title="채널 선택" name="chnnlNo" id="chnnlNo" >
												<option value="">채널 전체</option>
											</select>
										</span>
										<!-- E : ip-text-box -->
									</td>
									<td></td>
									<th scope="row">디바이스</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="deviceCode" id="radioDevice0" checked value="" />
													<label for="radioDevice0">전체</label>
												</span>
											</li>
											<c:forEach var="code" items="${codeList.DEVICE_CODE}" varStatus="status">
											<c:if test="${code.codeDtlNo ne '10002' }">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="deviceCode" id="radioDevice0${status.index }" value="${code.codeDtlNo }"/>
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
									<th scope="row">템플릿 유형</th>
									<td class="input" colspan="4">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="check-all-item" name="tmplTypeCodeAll" type="checkbox" checked value="Y">
													<label for="check-all-item">전체</label>
												</span>
											</li>
											<c:forEach var="code" items="${codeList.TMPL_TYPE_CODE}" varStatus="status">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input name="tmplTypeCodeArr" type="checkbox" id="chkTemplate0${status.index }" value="${code.codeDtlNo }" class="check-item" checked/>
													<label for="chkTemplate0${status.index }">${code.codeDtlName }</label>
												</span>
											</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse01" value="" checked />
													<label for="radioUse01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse02" value="Y" />
													<label for="radioUse02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioUse03" value="N" />
													<label for="radioUse03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- TODO : Search Drop down 기능 협의 후 수정 -->
										<!-- S : search-dropdown-box -->
										<!-- <span class="search-dropdown-box">
											<select class="search-dropdown" title="검색어 자동완성" data-placeholder="템플릿명 혹은 템플릿 코드 입력"></select>
										</span> -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택" name="keywordType">
												<option value="01">템플릿명</option>
												<option value="02">템플릿코드</option>
												<option value="03">작성자</option>
												<option value="04">수정자</option>
											</select>
											<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="keyword" maxlength="50">
										</div>
										<!-- E : search-dropdown-box -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0)" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<button type="submit" class="btn-normal btn-func" id="btn-search">검색</button>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->
				</form>
				
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">템플릿 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selTermsModule">목록개수</label>
							<select class="ui-sel" id="selTermsModule">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="/display/template/detail" class="btn-sm btn-link">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="templateSheet" style="width:100%; height:429px;">
						ibsheet grid영역(div 삭제 필요)
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.template.js<%=_DP_REV%>"></script>		
<%@include file="/WEB-INF/views/common/footer.jsp" %>