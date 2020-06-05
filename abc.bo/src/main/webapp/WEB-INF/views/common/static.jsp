<%@page import="kr.co.abcmart.common.request.Parameter"%>
<%@page import="kr.co.abcmart.common.config.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	// ※ 삭제 금지. 배포시  배포날짜+revision 경로로 취합하기 위함.
	String _DP_REV = "?20200602";
%>
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="180x180" href="/static/images/favicon/apple-touch-icon-180x180-precomposed.png">
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="167x167" href="/static/images/favicon/apple-touch-icon-167x167-precomposed.png">
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="152x152" href="/static/images/favicon/apple-touch-icon-152x152-precomposed.png">
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="144x144" href="/static/images/favicon/apple-touch-icon-144x144-precomposed.png">
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="120x120" href="/static/images/favicon/apple-touch-icon-120x120-precomposed.png">
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="114x114" href="/static/images/favicon/apple-touch-icon-114x114-precomposed.png">
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="76x76" href="/static/images/favicon/apple-touch-icon-76x76-precomposed.png">
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="72x72" href="/static/images/favicon/apple-touch-icon-72x72-precomposed.png">
	<link rel="apple-touch-icon-precomposed apple-touch-icon" sizes="60x60" href="/static/images/favicon/apple-touch-icon-60x60-precomposed.png">
	<link rel="apple-touch-icon-precomposed" href="/static/images/favicon/apple-touch-icon-57x57-precomposed.png">
	<link rel="shortcut icon" sizes="256x256" href="/static/images/favicon/favicon_256x256.png">
	<link rel="shortcut icon" sizes="196x196" href="/static/images/favicon/favicon_196x196.png">
	<link rel="shortcut icon" sizes="128x128" href="/static/images/favicon/favicon_128x128.png">
	<link rel="shortcut icon" sizes="48x48" href="/static/images/favicon/favicon_48x48.png">
	<link rel="shortcut icon" sizes="32x32" href="/static/images/favicon/favicon_32x32.png">
	<link rel="shortcut icon" href="/static/images/favicon/favicon_16x16.png">
	<link rel="icon" sizes="128x128" href="/static/images/favicon/favicon_128x128.png" type="image/png">
	<link rel="icon" sizes="64x64" href="/static/images/favicon/favicon_64x64.png" type="image/png">
	<link rel="icon" sizes="32x32" href="/static/images/favicon/favicon_32x32.png" type="image/png">
	<meta name="msapplication-TileImage" content="/static/images/favicon/apple-touch-icon-144x144-precomposed.png">
	<meta name="msapplication-TileColor" content="#222222">

	<%-- css가 js보다 먼저 선언되야 함. 위치 변경 절대 금지  --%>
	<link rel="stylesheet" href="/static/common/css/lib/jquery-ui.min.css<%=_DP_REV%>" />
	<link rel="stylesheet" href="/static/common/plugins/selectize.js/css/selectize.css<%=_DP_REV%>" />
	<link rel="stylesheet" href="/static/common/plugins/star-rating/css/star-rating.min.css<%=_DP_REV%>" />
	<link rel="stylesheet" href="/static/common/css/common.min.css<%=_DP_REV%>" />

	<script type="text/javascript" src="/static/common/js/jquery-3.3.1.min.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/jquery-ui.min.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/jquery.form.min.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/format.min.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/jquery.cookie.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/jquery.serialize-object.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/jquery.dot.param.js<%=_DP_REV%>"></script>

	<script type="text/javascript" src="/static/common/js/base/abcmart.util.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/base/abcmart.const.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/base/abcmart.init.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/base/abcmart.common.js<%=_DP_REV%>"></script>

	<script type="text/javascript" src="/static/common/ibsheet/ibleaders.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/ibsheet/ibsheet.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/ibsheet/ibsheetinfo.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/ibsheet/ibsheetcommon.js<%=_DP_REV%>"></script>

	<script type="text/javascript" src="/static/common/ckeditor/ckeditor.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/plugins/selectize.js/js/standalone/selectize.min.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/plugins/star-rating/js/star-rating.min.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/plugins/star-rating/js/locales/ko.js<%=_DP_REV%>"></script>

	<script type="text/javascript" src="/static/common/js/abc.front.js<%=_DP_REV%>"></script>

	<%-- 카카오 우편번호 서비스 가이드 https://spi.maps.daum.net/postcode/guidessl --%>
	<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js?autoload=false"></script>

	<script>
		abc.global = {
				scheme : "<%=request.getScheme()%>",
				host : "<%=request.getServerName()%>"
		};
	</script>