<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/tabHeader.jsp" %>
<%@page import="kr.co.abcmart.user.LoginManager"%>
<% pageContext.setAttribute("enter","\r\n"); %>

<script type="text/javascript">
	
</script>

				<ul class="msg-list">
				
				<c:if test="${claimMemoList eq null or empty claimMemoList}">
					<li>
						<p class="msg-desc">
							등록된 메모가 없습니다.
						</p>
					</li>
				</c:if>
				
				<c:forEach var="claimMemo" items="${claimMemoList}" varStatus="status">
				<input type="hidden" name="clmNo" value="${claimMemo.clmNo}">
				<input type="hidden" name="clmMemoSeq" value="${claimMemo.clmMemoSeq}">
					<li>
						<span class="msg-list-info">
							<span class="user-info">
								<!-- <span class="memo-type">옵션변경</span> --><!-- 2019.02.18 추가 | 메모 타입 추가 -->
								<span class="user-id">${claimMemo.clmMemoSeq} / ${claimMemo.loginId}</span>
								<span class="user-name">(${claimMemo.adminName})</span>
							</span>
							<span class="regist-date-wrap">
								<span class="regist-date"><fmt:formatDate value="${claimMemo.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></span>
								<c:if test="${LoginManager.getUserDetails().getAdminNo() eq claimMemo.rgsterNo}">
									<a href="#" class="btn-msg-list-del"><i class="ico ico-del"><span class="offscreen">메모 삭제</span></i></a>
								</c:if>
							</span>
						</span>
						<p class="msg-desc">
							${fn:replace(claimMemo.memoText, enter, "<br/>")}
						</p>
					</li>
				</c:forEach>
				
				</ul>
				
			