<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>

<script type="text/javascript">
</script>

<a href="/sample/downloadExcel" class="btn-sm btn-func">엑셀 다운로드</a>

<a href="/sample/downloadExcelNoColumeNames" class="btn-sm btn-func">엑셀 다운로드 (칼럼 헤드 X)</a>

<a href="/sample/downloadExcelTopAndLeftPosExclude" class="btn-sm btn-func">엑셀 다운로드 (위치 지정 + 제외칼럼)</a>

<a href="/sample/downloadExcelTemplate" class="btn-sm btn-func">기존 엑셀에 붙여넣어 다운로드</a>

<%@include file="/WEB-INF/views/common/footer.jsp" %>