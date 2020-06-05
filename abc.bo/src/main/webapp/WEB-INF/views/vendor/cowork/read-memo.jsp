<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">

</script>
				<ul class="msg-list">
					<c:forEach var="memo" items="${memoList}">
						<li>
							<span class="msg-list-info">
								<span class="user-info">
									<span class="user-id"><c:out value="${memo.loginId}"/></span>
									<span class="user-name">(<c:out value="${memo.adminName}"/>)</span>
								</span>
								<span class="regist-date-wrap">
									<span class="regist-date">
										<fmt:formatDate value="${memo.rgstDtm}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</span>
									<c:if test="${memo.adminNo eq userDetails.adminNo }" >
		 								<a href="javascript:delMemo(<c:out value="${memo.corprBoardSeq}"/>, <c:out value="${memo.corprBoardMemoSeq}"/>);" class="btn-msg-list-del">
		 									<i class="ico ico-del"><span class="offscreen">메모 삭제</span></i>
		 								</a>
	 								</c:if>
								</span>
							</span>
							<p class="msg-desc">
								${memo.memoText}
							</p>
						</li>
					</c:forEach>
				</ul>

