<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
$(function(){
	
	var str = $("#contText").val();
	var strByteLength = str.uniLength();
	$("#mesgContTextByteLength").val(strByteLength);
	
	$("#contText").off().on("keyup change", function(f) {
		var str = $("#contText").val();
		abc.biz.cmm.app.push.contTextLengthCheck(str);
	});

	abc.biz.cmm.app.push.saveFormEvent();

});
</script>
	<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">App Push 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">Push 메시지 정보</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-row -->
			<form name="appPushForm" id="appPushForm" enctype="multipart/form-data">
			<input type="hidden" name="pushMesgNo" id="pushMesgNo" value="${pushMesgValue.pushMesgNo}" />
			<input type="hidden" name="imagePathText" id="imagePathText" value="${pushMesgValue.imagePathText}" />
			<input type="hidden" name="isFileChange" id="isFileChange" value="<%= kr.co.abcmart.constant.Const.BOOLEAN_FALSE %>" />
			<%-- 검색조건 유지를 위한 --%>
			<input type="hidden" id="searchSiteNo" name="searchSiteNo" value="<c:out value="${param.siteNo}"/>">
			<input type="hidden" id="searchPushTitle" name="searchPushTitle" value="<c:out value="${param.pushTitleText}"/>">
			<input type="hidden" id="searchSendType" name="searchSendType" value="<c:out value="${param.sendType }" />" >
			<input type="hidden" id="searchPushIngStatCode" name="searchPushIngStatCode" value="<c:out value="${param.pushIngStatCode}"/>">
			<input type="hidden" id="fromDate" name="fromDate" value="<c:out value="${param.fromDate}"/>">
			<input type="hidden" id="toDate" name="toDate" value="<c:out value="${param.toDate}"/>">
			<input type="hidden" id="pageCount" name="pageCount" value="<c:out value="${param.pageCount}"/>">
			<input type="hidden" id="listPageNum" name="listPageNum" value="<c:out value="${param.listPageNum}"/>">
				<table class="tbl-row">
					<caption>Push 메시지 정보</caption>
					<colgroup>
						<col style="width: 140px;">
						<col>
						<col style="width: 140px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="th-required">메시지 제목</span>
							</th>
							<td class="input">
								<input type="text" name="pushTitleText" class="ui-input" id="pushTitleText" title="메시지 제목 입력" value="${pushMesgValue.pushTitleText}">
							</td>
							<!-- S : 20190522 수정 // OS 구분 > 사이트 구분으로 변경 -->
							<th scope="row">
								<span class="th-required">사이트구분</span>
							</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
<!-- 									<li> -->
<!-- 										<span class="ui-rdo"> -->
<!-- 											DESC : input id / label for 동일하게 맞춰주세요 -->
<%-- 											<input id="rdoSite01" name="siteNo" type="radio" value="<%= kr.co.abcmart.constant.Const.SITE_NO_ART %>"  --%>
<%-- 												<c:if test="${pushMesgValue.siteNo eq '10000'}">chekced</c:if> checked> --%>
<!-- 											<label for="rdoSite01">통합몰</label> -->
<!-- 										</span> -->
<!-- 									</li> -->
									<c:forEach var="site" items="${siteList}">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="rdoSite${site.siteNo}" name="siteNo" type="radio" value="${site.siteNo}" <c:if test="${site.siteNo eq pushMesgValue.siteNo }"> checked</c:if> />
												<label for="rdoSite${site.siteNo}">${site.siteName }</label>
											</span>
										</li>
									</c:forEach>
<!-- 									<li> -->
<!-- 										<span class="ui-rdo"> -->
<!-- 											DESC : input id / label for 동일하게 맞춰주세요 -->
<%-- 											<input id="rdoSite02" name="siteNo" type="radio" value="<%= kr.co.abcmart.constant.Const.SITE_NO_OTS %>"  --%>
<%-- 												<c:if test="${pushMesgValue.siteNo eq '10001'}">checked</c:if>> --%>
<!-- 											<label for="rdoSite02">OTS</label> -->
<!-- 										</span> -->
<!-- 									</li> -->
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
					</tbody>
				</table>
				<!-- E : tbl-row -->
				<!-- S : tbl-row -->
				<table class="tbl-row">
					<caption>Push 메시지 정보</caption>
					<colgroup>
						<col style="width: 130px;">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody id="androidTbody">
						<tr>
							<th scope="rowgroup" rowspan="3">메시지 내용</th>
							<th scope="row">
								<span class="th-required">내용</span>
							</th>
							<td class="input">
								<!-- S : msg-wrap -->
								<span class="msg-wrap">
									<span class="msg-box">
										<textarea rows="10" cols="100" name="contText" id="contText" class="ui-textarea" title="내용 입력">${pushMesgValue.contText}</textarea>
										<span class="text-limit">
											<span class="desc">(</span>
											<input type="text" id="mesgContTextByteLength" name="mesgContTextByteLength" class="ui-input num-unit100" title="입력 글자 Byte" readonly="true">
	
											<!-- DESC : SMS선택시 공백포함 80 byte, LMS선택시 공백포함 500 byte -->
											<span class="desc">Byte/<span id="mesgContTextMaxByte" class="text">500</span> Byte)</span>
										</span>
									</span>
								</span>
								<!-- E : msg-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="th-required">Target URL</span>
							</th>
							<td class="input">
								<input type="text" name="trgtUrl" class="ui-input" id="trgtUrl" title="Target URL 입력" value="${pushMesgValue.trgtUrl }">
							</td>
						</tr>
						<tr>
							<th scope="row">이미지
								<div>
									640*320 <br />
									(최대 10MB까지 등록가능,  파일 유형 : jpg, jpeg, png, gif, bmp)
								</div>
							</th>
							<td class="input">
								<div class="file-wrap">
									<ul class="file-list" id="ul-file-list">
										<c:choose>
											<c:when test="${!empty pushMesgValue.imageUrl}">
												<li id="imageLi" class="hidden">
													<span class="btn-box">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="file" name="imageFile" id="imageFile" title="첨부파일 추가">
														<label for="imageFile">찾아보기</label>
													</span>
												</li>
												<li id="dispImgNameLi">
													<input type="hidden" name="imageUrl" value="${pushMesgValue.imageUrl}">
													<span class="subject" id="imageName">${pushMesgValue.imageName}</span>
													<button type="button" class="btn-file-del" name="removeImageBtn" id="removeImageBtn">
														<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
													</button>
												</li>
											</c:when>
											<c:otherwise>
												<li id="imageLi">
													<span class="btn-box">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="file" name="imageFile" id="imageFile" title="첨부파일 추가">
														<label for="imageFile">찾아보기</label>
													</span>
												</li>
											</c:otherwise>
										</c:choose>
									</ul>
									<div class="img-wrap">
										<c:choose>
											<c:when test="${not empty pushMesgValue.imageUrl}">
												<img id="dispImage" alt="${pushMesgValue.imageName}" src="${pushMesgValue.imageUrl}" style="width: 620px; height: 320px">
											</c:when>
											<c:otherwise>
												<img id="dispImage" src="" style="display: none; width: 620px; height: 320px;">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<c:if test="${!empty pushMesgValue}">
					<table class="tbl-row">
						<caption>Push 메시지 정보</caption>
						<colgroup>
							<col style="width: 140px;">
							<col>
							<col style="width: 140px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">작성자</th>
								<td>
									<a href="javascript:void(0)" class="adminPopTag" data-admin="${pushMesgValue.rgsterNo}" style="text-decoration: underline;"><c:out value="${pushMesgValue.rgsterInfo }"/></a>
								</td>
								<th scope="row">작성일시</th>
								<td>${pushMesgValue.rgstDtm }</td>
							</tr>
							<tr>
								<th scope="row">수정자</th>
								<td>
									<a href="javascript:void(0)" class="adminPopTag" data-admin="${pushMesgValue.moderNo}" style="text-decoration: underline;"><c:out value="${pushMesgValue.moderInfo }"/></a>
								</td>
								<th scope="row">수정일시</th>
								<td>${pushMesgValue.modDtm}</td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</form>
			<!-- E : tbl-row -->

			<div class="tbl-desc-wrap">
			<!-- S : 20190228 추가 // 삭제 버튼 추가 -->
				<c:if test="${pushMesgValue ne null}">
					<div class="fl">
						<a href="#" id="sendManageBtn" class="btn-normal btn-link">발송관리</a>
					</div>
				</c:if>
				<!-- E : 20190228 추가 // 삭제 버튼 추가 -->
				<div class="fr">
					<a href="javascript:void(0);" class="btn-normal btn-link" id="appPushListBtn">목록</a>
				</div>
			</div>
				<!-- E : tbl-desc-wrap -->

			<div class="content-bottom">
				<a href="javascript:void(0);" class="btn-lg btn-save" id="saveAppPushBtn">저장</a>
			</div>
		</div>
	</div>
	<!-- E : container -->

<script src="/static/common/js/biz/cmm/abcmart.cmm.apppush.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>3