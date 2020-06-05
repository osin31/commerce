<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>
<script type="text/javascript">
$(function() {
	abc.biz.product.inquiry.detail.adminNo = "${userDetails.adminNo}";
});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>상품 Q&amp;A 상세</h1>
		</div>
		<div class="window-content">

			<!-- S : col-wrap -->
			<div class="col-wrap">
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">게시물 조회</h3>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<table class="tbl-row">
						<caption>게시물 조회</caption>
						<colgroup>
							<col style="width:130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">문의유형</th>
								<td>${inquiry.cnslTypeCodeName } > ${inquiry.cnslTypeDtlCodeName }</td>
							</tr>
							<tr>
								<th scope="row">비밀글 여부</th>
								<td>${inquiry.pswdYn eq 'N' or empty inquiry.pswdYn ? '설정안함 ' : '설정'}</td>
							</tr>
							<tr>
								<th scope="row">제목</th>
								<td>${inquiry.inqryTitleText }</td>
							</tr>
							<tr>
								<th scope="row">내용</th>
								<td class="input">
									<textarea class="ui-textarea" title="내용 입력" onkeypress="return false;">${inquiry.inqryContText }</textarea>
								</td>
							</tr>
							<!-- DESC : 20190314 삭제 // 첨부파일 삭제 -->
							<tr>
								<th scope="row">상품 정보</th>
								<td class="input">
									<ul class="td-text-list">
										<li><span class="text">표준카테고리 : ${inquiry.stdCtgrName }</span></li>
										<li><span class="text">사이트 / 채널: ${inquiry.siteName } / ${inquiry.chnnlName } </span></li>
										<li><span class="text">상품코드 / 온라인상품코드: ${inquiry.vndrPrdtNoText } /</span> <a href="javascript:void(0)" class="link" onclick="abc.readonlyProductDetailPopup({ prdtNo : '${inquiry.prdtNo}',siteNo : '${inquiry.siteNo }',chnnlNo : '${inquiry.chnnlNo }',vndrPrdtNoText : '${inquiry.vndrPrdtNoText }' })">${inquiry.prdtNo }</a></li>
										<li><span class="text">브랜드 : ${inquiry.brandName }</span></li>
										<li><span class="text">상품명 : </span><a href="javascript:void(0)" class="link" id="product-link" onclick="abc.productFrontUrl('${inquiry.prdtNo}');">${inquiry.prdtName }</a></li>
										<li><span class="text">스타일 : ${inquiry.styleInfo }</span></li>
									</ul>
								</td>
							</tr>
							<!-- DESC : 20190314 삭제 // 주문정보 삭제 -->
							<tr>
								<th scope="row">회원</th>
								<td>
									<c:choose>
										<c:when test="${isAdmin }">
											<a href="javascript:void(0)" class="link" onclick="abc.memberDetailPopup('${inquiry.memberNo}')">${UtilsMasking.adminName(inquiry.memberId, inquiry.memberName) }</a> / ${inquiry.memberTypeCodeName }
										</c:when>
										<c:otherwise>
											${UtilsMasking.adminName(inquiry.memberId, inquiry.memberName) } / ${inquiry.memberTypeCodeName }
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row">문의일시</th>
								<td>
									<c:choose>
										<c:when test="${not empty inquiry.inqryDtm }">
											<fmt:formatDate value="${inquiry.inqryDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- E : tbl-row -->
				</div>
				<div class="col">
					<!-- S : content-header -->
					<div class="content-header">
						<div class="fl">
							<h3 class="content-title">답변관리</h3>
						</div>
						<div class="fr">
							<div class="btn-group">
								<a href="#" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
							</div>
						</div>
					</div>
					<!-- E : content-header -->

					<!-- S : tbl-row -->
					<form id="detail-form">
						<input type="hidden" name="prdtInqrySeq" value="${inquiry.prdtInqrySeq }">
						<table class="tbl-row">
							<caption>답변관리</caption>
							<colgroup>
								<col style="width:130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">담당업체</th>
									<td>
										<c:choose>
											<c:when test="${inquiry.mmnyPrdtYn eq 'Y' }">
												자사
											</c:when>
											<c:when test="${inquiry.mmnyPrdtYn eq 'N' }">
												<a href="javascript:void(0)" class="link" onclick="abc.vendorInfoPop('${inquiry.vndrNo }')">${inquiry.vndrName }</a>
											</c:when>
										</c:choose>
									 </td>
								</tr>
								<tr>
									<th scope="row"><span class="th-required">전시여부</span></th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioDisplay02" name="dispYn" type="radio" value="Y" ${inquiry.dispYn eq 'Y' or empty inquiry.dispYn ? 'checked' : ''}>
													<label for="radioDisplay02">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="radioDisplay03" name="dispYn" type="radio" value="N" ${inquiry.dispYn eq 'N' ? 'checked' : ''}>
													<label for="radioDisplay03">전시안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<th scope="row">
										<span class="th-required">답변상태</span>
									</th>
									<td>${inquiry.aswrStatCodeName }</td>
								<tr>
									<th scope="row">답변 스크립트 선택</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<select id="cnsl-type-code" name="cnslTypeCode" class="ui-sel" title="상담유형 선택">
												<option value="">직접입력</option>
												<c:forEach var="item" items="${cnslTypeCode}">
													<c:if test="${item.codeDtlNo eq inquiry.cnslTypeCode or CommonCode.CNSL_TYPE_CODE_PRODUCT_INFO }">
														<option value="${item.codeDtlNo}" selected>${item.codeDtlName}</option>
													</c:if>
												</c:forEach>
											</select>
											<select id="cnsl-type-dtl-code" name="cnslTypeDtlCode" class="ui-sel" title="상담분류 선택">
												<option value="">상담분류</option>
												<c:forEach var="item" items="${cnslTypeDtlCode}">
													<option value="${item.codeDtlNo}"<c:if test="${item.codeDtlNo eq inquiry.cnslTypeDtlCode}"> selected</c:if>>${item.codeDtlName}</option>
												</c:forEach>
											</select>
										</span>
										<!-- E : ip-text-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">
										<span class="th-required">답변내용</span>
									</th>
									<td class="input">
										<select id="counsel-script-list" name="counselScriptList" class="ui-sel" title="스크립트제목 선택">
											<option value="">스크립트제목</option>
										</select>
										<textarea class="ui-textarea" title="답변내용 입력" name="aswrContText" id="aswr-cont-text">${inquiry.aswrContText }</textarea>
									</td>
								</tr>
								<!-- DESC : 20190314 삭제 // 첨부파일 삭제 -->
								<tr>
									<th scope="row">답변보류</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="aswrStatCode" id="radioMsg01" value="${CommonCode.ASWR_STAT_CODE_HD }" ${inquiry.aswrStatCode eq CommonCode.ASWR_STAT_CODE_HD ? 'checked' : ''}>
													<label for="radioMsg01">답변보류</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">답변자</th>
									<td>
										<c:choose>
											<c:when test="${not empty inquiry.aswrId and not empty inquiry.aswrName}">
												<a href="javascript:void(0)" class="link" onclick="abc.adminDetailPopup('${inquiry.aswrNo }')">${UtilsMasking.adminName(inquiry.aswrId, inquiry.aswrName) }</a>
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th scope="row">답변일시</th>
									<td>
										<c:choose>
											<c:when test="${not empty inquiry.aswrDtm }">
												<fmt:formatDate value="${inquiry.aswrDtm }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
					<!-- E : tbl-row -->
				</div>
			</div>
			<!-- E : col-wrap -->

			<!-- S : tbl-desc-wrap -->
			<!--
			<div class="tbl-desc-wrap">
				<div class="fl">
					<a href="#" class="btn-normal btn-del" id="delete">삭제</a>
				</div>
			</div>
			 -->
			<!-- E : tbl-desc-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-save" id="save">저장</a>
			</div>
			<!-- E : window-btn-group -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">관리자 메모</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<!-- S : manager-msg-wrap -->
			<div class="manager-msg-wrap">
				<div class="msg-box">
					<textarea title="관리자 메모 입력" name="memoText"></textarea>
				</div>
				<button type="button" class="btn-normal btn-save" id="memo-save">저장</button>
			</div>
			<!-- E : manager-msg-wrap -->
			<!-- S : msg-list-wrap -->
			<div class="msg-list-wrap">
				<ul class="msg-list" id="memo-list">
					<c:forEach var="item" items="${memo }">
						<li>
							<span class="msg-list-info">
								<span class="user-info">
									<span class="user-id">
										<a href="javascript:void(0)" class="link" onclick="abc.adminDetailPopup('${item.rgsterNo }')">${UtilsMasking.adminName(item.rgsterId, item.rgsterName) }</a>
									</span>
								</span>
								<span class="regist-date-wrap">
									<span class="regist-date"><fmt:formatDate value="${item.rgstDtm }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
									<c:if test="${userDetails.adminNo eq item.rgsterNo }">
										<a href="javascript:void(0)" class="btn-msg-list-del" onclick="abc.biz.product.inquiry.detail.deleteMemo(${item.prdtInqryMemoSeq})"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
									</c:if>
								</span>
							</span>
							<p class="msg-desc">${item.memoText }</p>
						</li>
					</c:forEach>
				</ul>
			</div>
			<!-- E : msg-list-wrap -->
		</div>
	</div>
</body>
<script type="text/x-template" id="inquiry-memo-tmpl">
	<li>
		<span class="msg-list-info">
			<span class="user-info">
				<span class="user-id"></span>
			</span>
			<span class="regist-date-wrap">
				<span class="regist-date"></span>
				<a href="javascript:void(0)" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
			</span>
		</span>
		<p class="msg-desc"></p>
	</li>
</script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.inquiry.detail.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>