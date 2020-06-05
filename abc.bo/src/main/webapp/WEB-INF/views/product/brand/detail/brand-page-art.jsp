<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

</script>


			<!-- S:tab_content -->
			<div id="tabBrandPageArt" class="tab-content">
				<div id="brandPrdtArtDispYTab" >
					<c:set value="ALL" var="scope_chnnlNo"/>
					<%@include file="/WEB-INF/views/product/brand/detail/page-chnnl/visual.jsp" %>
					<c:set value="${mallPcList['ALL']}" var="mallPc" scope="request" />
					<c:set value="${mallMoList['ALL']}" var="mallMo" scope="request" />
					<%@include file="/WEB-INF/views/product/brand/detail/page-chnnl/promotion-info.jsp" %>
				</div>

 				<div class="tab-wrap" id="brandPrdtArtDispNTab">
					<ul class="tabs">
						<li class="tab-item"><a href="#tabAbcMart" id="a_tabAbcMart" class="tab-link">ABC-MART</a></li>
						<li class="tab-item"><a href="#tabGs" id="a_tabGs" class="tab-link">ABC-MART GRAND STAGE</a></li>
						<li class="tab-item"><a href="#tabKids" id="a_tabKids" class="tab-link">ABC-MART KIDS</a></li>
					</ul>

					<div id="tabAbcMart" class="tab-content">
						<c:set value="${Const.CHNNL_NO_ABCMART}" var="scope_chnnlNo"/>
						<%@include file="/WEB-INF/views/product/brand/detail/page-chnnl/visual.jsp" %>
						<c:set value="${mallPcList[Const.CHNNL_NO_ABCMART]}" var="mallPc" scope="request" />
						<c:set value="${mallMoList[Const.CHNNL_NO_ABCMART]}" var="mallMo" scope="request" />
						<%@include file="/WEB-INF/views/product/brand/detail/page-chnnl/promotion-info.jsp" %>
					</div>
					<div id="tabGs" class="tab-content">
						<c:set value="${Const.CHNNL_NO_GRANDSTAGE}" var="scope_chnnlNo"/>
						<%@include file="/WEB-INF/views/product/brand/detail/page-chnnl/visual.jsp" %>
						<c:set value="${mallPcList[Const.CHNNL_NO_GRANDSTAGE]}" var="mallPc" scope="request" />
						<c:set value="${mallMoList[Const.CHNNL_NO_GRANDSTAGE]}" var="mallMo" scope="request" />
						<%@include file="/WEB-INF/views/product/brand/detail/page-chnnl/promotion-info.jsp" %>
					</div>
					<div id="tabKids" class="tab-content">
						<c:set value="${Const.CHNNL_NO_KIDS}" var="scope_chnnlNo"/>
						<%@include file="/WEB-INF/views/product/brand/detail/page-chnnl/visual.jsp" %>
						<c:set value="${mallPcList[Const.CHNNL_NO_KIDS]}" var="mallPc" scope="request" />
						<c:set value="${mallMoList[Const.CHNNL_NO_KIDS]}" var="mallMo" scope="request" />
						<%@include file="/WEB-INF/views/product/brand/detail/page-chnnl/promotion-info.jsp" %>
					</div>
				</div>

			</div>
		<!-- E:tab_content -->
