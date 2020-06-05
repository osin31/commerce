<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<div class="search-wrap">
	<form name="searchForm" id="searchForm">
		<input type="hidden" name="useYn" value="Y">
		<div class="search-inner">
			<table class="tbl-search">
				<caption>브랜드 검색</caption>
				<colgroup>
					<col style="width: 140px;"/>
					<col/>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">사이트</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="site-all" name="siteNo" type="radio" value="" checked/>
										<label for="site-all">전체</label>
									</span>
								</li>
								<c:forEach items="${searchConditionSiteList }" var="item">
									<li>
										<span class="ui-rdo">
											<input id="site-no-${item.siteNo }" name="siteNo" type="radio" value="${item.siteNo }"/>
											<label for="site-no-${item.siteNo }">${item.siteName }</label>
										</span>
									</li>
								</c:forEach>
								<%--
								<!-- DESC : 20190318 삭제 // 브랜드구분 > 공통 삭제 -->
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="site-mall" name="siteGubun" type="radio" value="MALL"/>
										<label for="site-mall">통합몰</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="site-ots" name="siteGubun" type="radio" value="OTS"/>
										<label for="site-ots">OTS</label>
									</span>
								</li>
								
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="site-mall-ots" name="siteGubun" type="radio" value="MALL-OTS"/>
										<label for="site-mall-ots">통합몰+OTS</label>
									</span>
								</li>
								--%>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">브랜드명 (국문 / 영문)</th>
						<td class="input">
							<input type="text" class="ui-input" placeholder="브랜드명 (국문 / 영문)" title="브랜드명 입력" name="brandName"/>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="confirm-box">
				<div class="fl">
					<a href="#" class="btn-sm btn-func" id="clear"><i class="ico ico-refresh"></i>초기화</a>
				</div>
				<div class="fr">
					<a href="#" class="btn-normal btn-func" id="search">검색</a>
				</div>
			</div>
		</div>
		<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
	</form>
</div>
<!-- E : search-wrap -->
