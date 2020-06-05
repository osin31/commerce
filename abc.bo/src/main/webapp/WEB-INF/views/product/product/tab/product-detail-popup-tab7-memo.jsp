<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="UtilsMasking" class="kr.co.abcmart.util.UtilsMasking"/>
<div class="tab-content">
	<!-- S : msg-full-content -->
	<div class="msg-full-content tab-size-fix">
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
				<textarea title="관리자 메모 입력" name="memoText" id="memoText" maxlength="500"></textarea>
			</div>
			<button type="button" class="btn-normal btn-save" data-button="async-save-admin-memo">저장</button>
		</div>
		<!-- E : manager-msg-wrap -->

		<!-- S : msg-list-wrap -->
		<div class="msg-list-wrap">
			<ul class="msg-list" id="append-product-memo">
				<c:choose>
					<c:when test="${not empty memoList}">
						<c:forEach items="${memoList }" var="item">
							<li>
								<span class="msg-list-info">
									<span class="user-info">
										<span class="user-id">
											<a href="javascript:void(0)" class="link" onclick="abc.adminDetailPopup('${item.rgsterNo }')">${UtilsMasking.adminName(item.rgsterId, item.rgsterName) }</a>
										</span>
										<%-- 
										<span class="user-id">${item.rgsterId }</span>
										<span class="user-name">(${item.rgsterName })</span>
										 --%>
									</span>
									<span class="regist-date-wrap">
										<fmt:formatDate var="memoRgstDtm" pattern="yyyy-MM-dd hh:mm:ss" value="${item.rgstDtm }"/>
										<span class="regist-date">${memoRgstDtm }</span>
										<c:if test="${userDetails.adminNo eq item.rgsterNo}">
										<a href="javascript:void(0)" class="btn-msg-list-del" data-button="remove-memo" data-prdt-memo-seq="${item.prdtMemoSeq }"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
										</c:if>
									</span>
								</span>
								<p class="msg-desc">${item.memoText }</p>
							</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li>등록된 관리자 메모가 없습니다.</li>
					</c:otherwise>
				</c:choose>
				<script type="text/x-template" id="product-memo-row-tmpl">
					<li>
						<span class="msg-list-info">
							<span class="user-info">
								<span class="user-id"></span>
							</span>
							<span class="regist-date-wrap">
								<span class="regist-date"></span>
								<a href="javascript:void(0);" class="btn-msg-list-del" data-button="remove-memo" data-prdt-memo-seq="" data-login-admin-no="${userDetails.adminNo}"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
							</span>
						</span>
						<p class="msg-desc"></p>
					</li>
				</script>
			</ul>
		</div>
		<!-- E : msg-list-wrap -->
	</div>
	<!-- E : msg-full-content -->
</div>
