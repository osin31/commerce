<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
		
	<form id="frm" name="frm">
		<input  type="hidden" name="sizeChartSeq" id="sizeChartSeq" value="${dpSizeChart.sizeChartSeq}" />
		
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">사이즈 가이드 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites">
							<span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home">
									<a href="/"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>상품관리</li>
								<li>상품기준 정보관리</li>
								<li>사이즈 가이드 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">사이즈 가이드 상세</h3>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" id="clear-form" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
					</div>
				</div>
				<!-- E : content-header -->

				<table class="tbl-row">
					<caption>사이즈 가이드 상세</caption>
					<colgroup>
						<col style="width:160px;">
						<col>
						<col style="width:160px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">사이즈가이드명</span>
							</th>
							<td class="input">
								<input type="text" name="sizeChartName" class="ui-input" placeholder="사이즈 가이드명" title="사이즈가이드명 입력" value="${dpSizeChart.sizeChartName}">
							</td>
							<th scope="row">사용여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse01" name="useYn" type="radio" ${dpSizeChart.useYn == 'N' or dpSizeChart.useYn == null ? 'checked' :''} value="N">
											<label for="radioUse01">사용안함</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioUse02" name="useYn" type="radio" ${dpSizeChart.useYn == 'Y' ? 'checked' :''} value="Y">
											<label for="radioUse02">사용</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">표준 카테고리 선택 (대)</span>
							</th>
							<td class="input">
								<select name="stdCtgrNo" class="ui-sel" title="대카테고리 선택">
									<option value="">대카테고리 선택</option>
									<c:forEach var="row" items="${standardCategoryList}">
										<option value="${row.stdCtgrNo}" ${row.stdCtgrNo eq dpSizeChart.stdCtgrNo ? 'selected' : ''}>${row.stdCtgrName}</option>
									</c:forEach>
								</select>
							</td>
							<th scope="row">적용브랜드</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioBrand01" name="brandAssignYn" type="radio" ${dpSizeChart.brandAssignYn == 'N' or dpSizeChart.brandAssignYn == null ? 'checked' :''} value="N">
											<label for="radioBrand01">공통</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="radioBrand02" name="brandAssignYn" type="radio" value="Y" ${dpSizeChart.brandAssignYn == 'Y' ? 'checked' :''}>
											<label for="radioBrand02">브랜드</label>
										</span>

										<span class="ip-search-box" ${dpSizeChart.brandAssignYn == 'Y' ? '' : 'style="display:none;"'}>
											<input type="text" id="brandName" class="ui-input" title="검색어 입력" placeholder="브랜드명(국문/영문)" readonly value="${dpSizeChart.brandName}">
											<input type="hidden" id="brandNo" name="brandNo" value="${dpSizeChart.brandNo}" />
											<a href="javascript:void(0);" id="search-brand" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">테마</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<%-- <li>
										<span class="ui-rdo">
											<input id="themeRadio01" name="themeType" type="radio" ${dpSizeChart.themeType == null ? 'checked' :''} value="">
											<label for="themeRadio01">전체</label>
										</span>
									</li> --%>
									<li>                                                           
										<span class="ui-rdo">                                      
											<input id="themeRadio05" name="themeType" type="radio" ${dpSizeChart.themeType == 'A' or empty dpSizeChart.themeType ? 'checked' :''} value="A">
											<label for="themeRadio05">공용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="themeRadio02" name="themeType" type="radio" ${dpSizeChart.themeType == 'M' ? 'checked' :''} value="M">
											<label for="themeRadio02">남성</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="themeRadio03" name="themeType" type="radio" ${dpSizeChart.themeType == 'F' ? 'checked' :''} value="F">
											<label for="themeRadio03">여성</label>                 
										</span>                                                    
									</li>                                                          
									<li>                                                           
										<span class="ui-rdo">                                      
											<input id="themeRadio04" name="themeType" type="radio" ${dpSizeChart.themeType == 'C' ? 'checked' :''} value="C">
											<label for="themeRadio04">아동</label>                 
										</span>                                                    
									</li>                                                          
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">사이즈가이드 <br />이미지 등록</span>
								<div>권장사이즈 660*1052 (최대 10MB까지 등록가능 <br />파일유형 : jpg, jpeg, png, gif, bmp)</div>
							</th>
							<td class="input" colspan="3">
								<div class="file-wrap">
									<ul class="file-list">
										<li>
											<span class="btn-box">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="hidden" id="imageName" title="첨부파일 추가" name="imageName" value="${dpSizeChart.imageName}">
												<input type="file" id="inputFile" name="imageFile" title="첨부파일 추가">
												<label for="inputFile">찾아보기</label>
											</span>
										</li>
										<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
										<li>
											<a href="javascript:void(0);" id="image-name" class="subject">${dpSizeChart.imageName}</a>
											<button type="button" id="del-image" class="btn-file-del" style="${dpSizeChart.imageName != null and dpSizeChart.imageName != '' ? '' : 'display: none;' }">
												<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
											</button>
										</li>
									</ul>
									<!-- S : 190218 이미지 대체 텍스트 입력 필드 추가 -->
									<div class="alt-wrap">
										<input type="text" name="altrnText" class="ui-input" placeholder="이미지 대체 텍스트 입력" value="${dpSizeChart.altrnText}">
									</div>
									<!-- E : 190218 이미지 대체 텍스트 입력 필드 추가 -->
									<div id="area-image-preview" class="img-wrap">
										<c:if test="${dpSizeChart.imageName != null and dpSizeChart.imageName != ''}">
											<img id="imgArea" alt="${dpSizeChart.imageName}" src="${dpSizeChart.imageUrl}">
										</c:if>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->

				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap">
					<%-- <c:if test="${not empty dpSizeChart.sizeChartSeq}">
						<div class="fl">
							<a href="javascript:void(0);" id="del-sizechart" class="btn-normal btn-del">삭제</a>
						</div>
					</c:if> --%>
					<div class="fr">
						<a href="/product/sizechart" class="btn-normal btn-link">목록</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-bottom -->
				<div class="content-bottom">
					<a href="javascript:void(0);" id="save-sizechart" class="btn-lg btn-save">저장</a>
				</div>
				<!-- E : content-bottom -->
			</div>
		</div>
		<!-- E : container -->
	</form>

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.sizechart.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>