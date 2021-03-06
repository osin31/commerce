<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
	
		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">브랜드관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
<!-- 						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button> -->
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>A-Connect 관리</li>
								<li>브랜드관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">브랜드 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="searchForm" name="searchForm" onsubmit="return false;">
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>브랜드 검색</caption>
							<colgroup>
								<col style="width: 130px;">
								<col>
								<col style="width: 79px">
								<col style="width: 130px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<!-- <th scope="row">베스트 여부</th>
									<td class="input">
										S : ip-box-list
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input type="radio" name="radioBestModule" id="radioBest01" checked>
													<label for="radioBest01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="radioBestModule" id="radioBest02">
													<label for="radioBest02">Y</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="radioBestModule" id="radioBest03">
													<label for="radioBest03">N</label>
												</span>
											</li>
										</ul>
										E : ip-box-list
									</td>
									<td></td> -->
									<th scope="row">브랜드 사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioDisplay01" value checked>
													<label for="radioDisplay01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioDisplay02" value="Y">
													<label for="radioDisplay02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="useYn" id="radioDisplay03" value="N">
													<label for="radioDisplay03">사용안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">A-Connect 전시여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input type="radio" name="aconnectDispYn" id="radioDisplay02-01" value checked>
													<label for="radioDisplay02-01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="aconnectDispYn" id="radioDisplay02-02" value="Y">
													<label for="radioDisplay02-02">전시</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<input type="radio" name="aconnectDispYn" id="radioDisplay02-03" value="N">
													<label for="radioDisplay02-03">전시안함</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">검색어</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택" name="searchType">
												<option value>선택</option>
												<option value="E">영문브랜드명</option>
												<option value="K">국문브랜드명</option>
												<option value="I">브랜드 ID</option>
											</select>
											<input type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" name="searchWord">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="#" class="btn-sm btn-func" id="clearBtn"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="#" class="btn-normal btn-func" id="searchBtn">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				</form>
				<!-- E : search-wrap -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">브랜드 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="pageCount">목록개수</label>
							<select class="ui-sel" id="pageCount">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<span class="btn-group">
							<a href="javascript:void(0);" class="btn-sm btn-save" id="saveBtn">저장</a>
						</span>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="brandSheet"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.aconnect.brand.js<%=_DP_REV%>"></script
<%@include file="/WEB-INF/views/common/footer.jsp" %>